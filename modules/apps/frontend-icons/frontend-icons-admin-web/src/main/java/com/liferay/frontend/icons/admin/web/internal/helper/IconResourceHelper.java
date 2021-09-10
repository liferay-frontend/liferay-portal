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
import com.liferay.frontend.icons.admin.web.internal.contributor.extender.IconResource;
import com.liferay.frontend.icons.admin.web.internal.contributor.extender.IconResourcePack;
import com.liferay.frontend.icons.admin.web.internal.contributor.extender.IconResourcePackImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

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
			long repositoryId, String iconName, String folderName,
			String contentType, InputStream inputStream, long size)
		throws PortalException, IOException {

		DLFolder companyIconsFolder = _getFolder(
			repositoryId, _ROOT_FOLDER_NAME, 0L);

		if (companyIconsFolder == null) {
			companyIconsFolder = _addFolder(
				repositoryId, _ROOT_FOLDER_NAME, 0L);
		}

		long companyIconsFolderId = companyIconsFolder.getFolderId();

		DLFolder folder = _getFolder(
			repositoryId, folderName, companyIconsFolderId);

		if (folder == null) {
			folder = _addFolder(repositoryId, folderName, companyIconsFolderId);
		}

		FileEntry fileEntry = _dlAppService.addFileEntry(
			null, repositoryId, folder.getFolderId(), iconName, contentType,
			iconName, "", null, inputStream, size, null, null,
			new ServiceContext());

		_addIconToResourceMap(repositoryId, iconName, folderName, StringUtil.read(inputStream));
	}

	public void deleteFileEntry(
			long repositoryId, String iconName, String folderName)
		throws PortalException {

		DLFolder companyIconsFolder = _getFolder(
			repositoryId, _ROOT_FOLDER_NAME, 0L);

		if (companyIconsFolder == null) {
			return;
		}

		DLFolder folder = _getFolder(
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

		long totalFileCount =  _dlFileEntryLocalService.getFileEntriesCount(repositoryId, folderId);

		if (totalFileCount == 0) {
			_dlFolderLocalService.deleteFolder(folderId);
		}
		_removeIconFromResourceMap(repositoryId, iconName, folderName);
	}

	public String getGlobalSpriteContent(long groupId) {
		StringBuilder sb = new StringBuilder();

		sb.append(_getPackSVGContent(_GLOBAL_ID));
		sb.append(_getPackSVGContent(groupId));

		return _generateXmlSvg(new String(sb));
	}

	public String getIconPackSpriteContent(long groupId, String iconPackName) {
		StringBuilder sb = new StringBuilder();

		HashMap<String, IconResourcePack> iconResourceMap = getIconResourceMaps(
			groupId);

		IconResourcePack iconResourcePack = iconResourceMap.get(iconPackName);

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

					DLFolder companyIconsFolder = _getFolder(
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

	private DLFolder _addFolder(
			long repositoryId, String folderName, long parentFolderId)
		throws PortalException {

		return _dlFolderService.addFolder(
			repositoryId, repositoryId, false, parentFolderId, folderName, "",
			new ServiceContext());
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

		iconResourcePack.addIconResource(iconName, svgContent);
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

	private DLFolder _getFolder(
		long repositoryId, String folderName, long parentFolderId) {

		try {
			return _dlFolderLocalService.getFolder(
				repositoryId, parentFolderId, folderName);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return null;
		}
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

	private void _removeIconFromResourceMap(
		long groupId, String iconName, String folderName) {

		HashMap<String, IconResourcePack> groupIconResourceMap =
			_iconResourcesMap.get(groupId);

		IconResourcePack iconResourcePack = groupIconResourceMap.get(
			folderName);

		iconResourcePack.removeIconResource(iconName);

		Collection<IconResource> icons = iconResourcePack.getIconResources();

		if (icons.size() == 0) {
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

	private static final long _GLOBAL_ID = 0L;

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
	private final ReadWriteLock _readWriteLock = new ReentrantReadWriteLock();

}