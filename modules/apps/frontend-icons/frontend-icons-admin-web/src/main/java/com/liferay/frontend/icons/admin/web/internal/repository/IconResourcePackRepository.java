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

package com.liferay.frontend.icons.admin.web.internal.repository;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.frontend.icons.admin.web.internal.model.IconResourcePackImpl;
import com.liferay.frontend.icons.admin.web.internal.util.SVGUtil;
import com.liferay.frontend.icons.model.IconResource;
import com.liferay.frontend.icons.model.IconResourcePack;
import com.liferay.petra.string.StringPool;
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
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(service = IconResourcePackRepository.class)
public class IconResourcePackRepository {

	public void addIconResourcePack(
			long companyId, IconResourcePack iconResourcePack)
		throws PortalException {

		Company company = _companyLocalService.getCompany(companyId);

		long repositoryId = _getRepositoryId(company.getGroupId());

		Folder companyIconsFolder = _getFolder(
			companyId, _ROOT_FOLDER_NAME, 0L, repositoryId);

		String svgSpritemap = SVGUtil.getSVGSpritemap(iconResourcePack);

		_dlAppService.addFileEntry(
			null, repositoryId, companyIconsFolder.getFolderId(),
			iconResourcePack.getName(), ContentTypes.IMAGE_SVG_XML,
			iconResourcePack.getName(), "", null, svgSpritemap.getBytes(), null,
			null, new ServiceContext());
	}

	public void deleteIconResourcePack(long companyId, String iconPackName)
		throws PortalException {

		Company company = _companyLocalService.getCompany(companyId);

		long repositoryId = _getRepositoryId(company.getGroupId());

		Folder companyIconsFolder = _getFolder(
			companyId, _ROOT_FOLDER_NAME, 0L, repositoryId);

		_dlAppService.deleteFileEntryByTitle(
			repositoryId, companyIconsFolder.getFolderId(), iconPackName);
	}

	public Map<Long, Map<String, IconResourcePack>> getIconResourcePacks()
		throws Exception {

		Map<Long, Map<String, IconResourcePack>> map = new HashMap<>();

		_companyLocalService.forEachCompany(
			company -> {
				Map<String, IconResourcePack> iconResourcePacks =
					new HashMap<>();

				Folder companyIconsFolder = _getFolder(
					company.getCompanyId(), _ROOT_FOLDER_NAME, 0L,
					_getRepositoryId(company.getGroupId()));

				List<DLFileEntry> dlFileEntries =
					_dlFileEntryLocalService.getFileEntries(
						company.getGroupId(), companyIconsFolder.getFolderId());

				for (DLFileEntry dlFileEntry : dlFileEntries) {
					IconResourcePack iconResourcePack =
						new IconResourcePackImpl(dlFileEntry.getTitle());

					List<IconResource> iconResources = SVGUtil.getIconResources(
						StringUtil.read(
							_dlFileEntryLocalService.getFileAsStream(
								dlFileEntry.getFileEntryId(),
								dlFileEntry.getVersion())),
						StringPool.BLANK);

					iconResourcePack.addIconResources(iconResources);

					iconResourcePacks.put(
						iconResourcePack.getName(), iconResourcePack);
				}

				if (MapUtil.isNotEmpty(iconResourcePacks)) {
					map.put(company.getCompanyId(), iconResourcePacks);
				}
			});

		return map;
	}

	private Folder _getFolder(
		long companyId, String folderName, long parentFolderId,
		long repositoryId) {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGuestPermissions(true);

			return _portletFileRepository.addPortletFolder(
				_userLocalService.getDefaultUserId(companyId), repositoryId,
				parentFolderId, folderName, serviceContext);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return null;
		}
	}

	private long _getRepositoryId(long groupId) throws PortalException {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGuestPermissions(true);

		Repository repository = _portletFileRepository.addPortletRepository(
			groupId, _REPOSITORY_NAME, serviceContext);

		return repository.getRepositoryId();
	}

	private static final String _REPOSITORY_NAME = "icons.admin.web";

	private static final String _ROOT_FOLDER_NAME =
		"icons.admin.web.icon.packs";

	private static final Log _log = LogFactoryUtil.getLog(
		IconResourcePackRepository.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private PortletFileRepository _portletFileRepository;

	@Reference
	private UserLocalService _userLocalService;

}