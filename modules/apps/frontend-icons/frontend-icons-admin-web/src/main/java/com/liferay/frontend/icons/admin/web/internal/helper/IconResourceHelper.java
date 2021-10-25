/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.frontend.icons.admin.web.internal.helper;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.document.library.kernel.service.DLFolderService;
import com.liferay.frontend.icons.admin.web.internal.model.IconResourceImpl;
import com.liferay.frontend.icons.admin.web.internal.model.IconResourcePackImpl;
import com.liferay.frontend.icons.model.IconResource;
import com.liferay.frontend.icons.model.IconResourcePack;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Bryce Osterhaus
 */
@Component(immediate = true, service = IconResourceHelper.class)
public class IconResourceHelper {

	public void addFileEntry(
			long companyId, long groupId, String iconName, String folderName,
			String contentType, InputStream inputStream, long size)
		throws IOException, PortalException {

		long repositoryId = _getRepositoryId(groupId);

		if (!_validateAddFileEntry(groupId, folderName, iconName)) {
			return;
		}

		Folder companyIconsFolder = _getFolder(
			repositoryId, _ROOT_FOLDER_NAME, 0L);

		if (companyIconsFolder == null) {
			companyIconsFolder = _addFolder(
				companyId, repositoryId, _ROOT_FOLDER_NAME, 0L);
		}

		long companyIconsFolderId = companyIconsFolder.getFolderId();

		Folder folder = _getFolder(
			repositoryId, folderName, companyIconsFolderId);

		if (folder == null) {
			folder = _addFolder(
				companyId, repositoryId, folderName, companyIconsFolderId);
		}

		long folderId = folder.getFolderId();

		_addIconToResourceMap(
			groupId, iconName, folderName, StringUtil.read(inputStream));

		_dlAppService.addFileEntry(
			null, repositoryId, folderId, iconName, contentType, iconName, "",
			null, inputStream, size, null, null, new ServiceContext());
	}

	public void deleteFileEntry(
			long repositoryId, String iconName, String folderName)
		throws PortalException {

		Folder companyIconsFolder = _getFolder(
			repositoryId, _ROOT_FOLDER_NAME, 0L);

		if (companyIconsFolder == null) {
			return;
		}

		Folder folder = _getFolder(
			repositoryId, folderName, companyIconsFolder.getFolderId());

		if (folder == null) {
			return;
		}

		long folderId = folder.getFolderId();

		DLFileEntry fileEntry = _dlFileEntryLocalService.getFileEntry(
			repositoryId, folderId, iconName);

		if (fileEntry == null) {
			return;
		}

		_dlFileEntryLocalService.deleteFileEntry(fileEntry.getFileEntryId());

		long totalFileCount = _dlFileEntryLocalService.getFileEntriesCount(
			repositoryId, folderId);

		if (totalFileCount == 0) {
			_dlFolderLocalService.deleteFolder(folderId);
		}

		_removeIconFromResourceMap(repositoryId, iconName, folderName);
	}

	public String getGlobalSpriteContent() {
		StringBuilder sb = new StringBuilder();

		sb.append(_getPackSVGContent(_GLOBAL_ID));

		return _generateXmlSvg(new String(sb));
	}

	public String getIconPackSpriteContent(long groupId, String iconPackName) {
		HashMap<String, IconResourcePack> iconResourceMap = getIconResourceMaps(
			groupId);

		if (iconResourceMap == null) {
			return null;
		}

		IconResourcePack iconResourcePack = iconResourceMap.get(iconPackName);

		if (iconResourcePack == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		for (IconResource iconResource : iconResourcePack.getIconResources()) {
			sb.append(iconResource.getInternalSVGContent());
		}

		return _generateXmlSvg(new String(sb));
	}

	public HashMap<String, IconResourcePack> getIconResourceMaps(long groupId) {
		return HashMapBuilder.<String, IconResourcePack>putAll(
			_iconResourcesMap.get(_GLOBAL_ID)
		).putAll(
			_iconResourcesMap.get(groupId)
		).build();
	}

	@Activate
	protected void activate() throws PortalException {
		List<Company> companies = _companyLocalService.getCompanies();

		companies.forEach(
			company -> {
				try {
					long groupId = company.getGroupId();

					Folder companyIconsFolder = _getFolder(
						groupId, _ROOT_FOLDER_NAME, 0L);

					if (companyIconsFolder != null) {
						List<DLFolder> folders =
							_dlFolderLocalService.getFolders(
								groupId, companyIconsFolder.getFolderId());

						folders.forEach(
							folder -> {
								List<DLFileEntry> fileEntries =
									_dlFileEntryLocalService.getFileEntries(
										groupId, folder.getFolderId());

								fileEntries.forEach(
									dlFileEntry -> {
										try {
											InputStream entryInputStream =
												_dlFileEntryLocalService.
													getFileAsStream(
														dlFileEntry.
															getFileEntryId(),
														dlFileEntry.
															getVersion());

											_addIconToResourceMap(
												groupId,
												dlFileEntry.getFileName(),
												folder.getName(),
												StringUtil.read(
													entryInputStream));
										}
										catch (IOException | PortalException
													exception) {

											if (_log.isDebugEnabled()) {
												_log.debug(
													exception, exception);
											}
										}
									});
							});
					}
				}
				catch (PortalException portalException) {
					if (_log.isDebugEnabled()) {
						_log.debug(portalException, portalException);
					}
				}
			});
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void addIconResourcePack(IconResourcePack iconResourcePack) {
		Lock lock = _readWriteLock.writeLock();

		lock.lock();

		try {
			_addIconResourcePack(iconResourcePack);
		}
		finally {
			lock.unlock();
		}
	}

	protected void removeIconResourcePack(IconResourcePack iconResourcePack) {
		Lock lock = _readWriteLock.writeLock();

		lock.lock();

		try {
			_removeIconResourcePack(iconResourcePack);
		}
		finally {
			lock.unlock();
		}
	}

	private Folder _addFolder(
			long companyId, long repositoryId, String folderName,
			long parentFolderId)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGuestPermissions(true);

		return _portletFileRepository.addPortletFolder(
			_userLocalService.getDefaultUserId(companyId), repositoryId,
			parentFolderId, folderName, serviceContext);
	}

	private void _addIconResourcePack(IconResourcePack iconResourcePack) {
		String name = iconResourcePack.getName();

		_iconResourcesMap.computeIfAbsent(
			_GLOBAL_ID, k -> new HashMap<String, IconResourcePack>());

		Map<String, IconResourcePack> iconResourcePackMap =
			_iconResourcesMap.get(_GLOBAL_ID);

		iconResourcePackMap.putIfAbsent(name, iconResourcePack);
	}

	private void _addIconToResourceMap(
		long groupId, String iconName, String folderName, String svgContent) {

		HashMap<String, IconResourcePack> groupIconResourceMap =
			_iconResourcesMap.computeIfAbsent(
				groupId, k -> new HashMap<String, IconResourcePack>());

		groupIconResourceMap.putIfAbsent(
			folderName, new IconResourcePackImpl(folderName));

		IconResourcePack iconResourcePack = groupIconResourceMap.get(
			folderName);

		List<Tuple> tuples = _getIconResources(svgContent);

		for (Tuple tuple : tuples) {
			iconResourcePack.addIconResource(
				new IconResourceImpl(
					String.valueOf(tuple.getObject(0)),
					String.valueOf(tuple.getObject(1))));
		}
	}

	private String _generateXmlSvg(String content) {
		StringBuilder sb = new StringBuilder();

		sb.append(
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE svg PUBLIC " +
				"\"-//W3C//DTD SVG 1.1//EN\" " +
					"\"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">");

		sb.append(
			"<svg xmlns=\"http://www.w3.org/2000/svg\" " +
				"xmlns:xlink=\"http://www.w3.org/1999/xlink\">");

		sb.append(content);

		sb.append("</svg>");

		return new String(sb);
	}

	private Folder _getFolder(
		long repositoryId, String folderName, long parentFolderId) {

		try {
			return _portletFileRepository.getPortletFolder(
				repositoryId, parentFolderId, folderName);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return null;
		}
	}

	private List<Tuple> _getIconResources(String svgContent) {
		List<Tuple> iconResources = new ArrayList<>();

		try {
			Document document = SAXReaderUtil.read(svgContent);

			Element rootElement = document.getRootElement();

			List<Element> symbols = rootElement.elements("symbol");

			for (Element symbol : symbols) {
				Attribute idAttribute = symbol.attribute("id");

				iconResources.add(
					new Tuple(idAttribute.getValue(), symbol.asXML()));
			}

			System.out.println(document);
		}
		catch (DocumentException e) {
			return iconResources;
		}

		return iconResources;
	}

	private String _getPackSVGContent(long groupId) {
		StringBuilder sb = new StringBuilder();

		Map<String, IconResourcePack> iconResourceMap = _iconResourcesMap.get(
			groupId);

		for (Map.Entry<String, IconResourcePack> entry :
				iconResourceMap.entrySet()) {

			IconResourcePack iconResourcePack = entry.getValue();

			for (IconResource iconResource :
					iconResourcePack.getIconResources()) {

				sb.append(iconResource.getInternalSVGContent());
			}
		}

		return new String(sb);
	}

	private long _getRepositoryId(long groupId) throws PortalException {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGuestPermissions(true);

		Repository repository = _portletFileRepository.addPortletRepository(
			groupId, _REPOSITORY_NAME, serviceContext);

		return repository.getRepositoryId();
	}

	private void _removeIconFromResourceMap(
		long groupId, String iconName, String folderName) {

		HashMap<String, IconResourcePack> groupIconResourceMap =
			_iconResourcesMap.get(groupId);

		IconResourcePack iconResourcePack = groupIconResourceMap.get(
			folderName);

		iconResourcePack.removeIconResource(iconName);

		Collection<IconResource> icons = iconResourcePack.getIconResources();

		if (icons.isEmpty()) {
			groupIconResourceMap.remove(folderName);
		}
	}

	private void _removeIconResourcePack(IconResourcePack iconResourcePack) {
		if (_iconResourcesMap.containsKey(_GLOBAL_ID)) {
			String name = iconResourcePack.getName();

			Map<String, IconResourcePack> iconResourceMap =
				_iconResourcesMap.get(_GLOBAL_ID);

			iconResourceMap.remove(name);
		}
	}

	private Boolean _validateAddFileEntry(
		long groupId, String folderName, String iconName) {

		HashMap<String, IconResourcePack> groupIconResourceMap =
			_iconResourcesMap.get(groupId);

		if (groupIconResourceMap == null) {
			return true;
		}

		IconResourcePack iconResourcePack = groupIconResourceMap.get(
			folderName);

		if (iconResourcePack == null) {
			return true;
		}

		for (IconResource iconResource : iconResourcePack.getIconResources()) {
			String iconResourceId = iconResource.getId();

			if (iconResourceId.equals(iconName)) {
				return false;
			}
		}

		return true;
	}

	private static final long _GLOBAL_ID = 0L;

	private static final String _REPOSITORY_NAME = "icons.admin.web";

	private static final String _ROOT_FOLDER_NAME =
		"icons.admin.web.icon.packs";

	private static final Log _log = LogFactoryUtil.getLog(
		IconResourceHelper.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLFolderLocalService _dlFolderLocalService;

	@Reference
	private DLFolderService _dlFolderService;

	private final Map<Long, HashMap<String, IconResourcePack>>
		_iconResourcesMap = new HashMap<>();

	@Reference
	private PortletFileRepository _portletFileRepository;

	private final ReadWriteLock _readWriteLock = new ReentrantReadWriteLock();

	@Reference
	private UserLocalService _userLocalService;

}