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

package com.liferay.remote.app.model.impl;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.util.comparator.RepositoryModelTitleComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.remote.app.constants.RemoteAppConstants;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class RemoteAppEntryImpl extends RemoteAppEntryBaseImpl {

	@Override
	public List<FileEntry> getAttachmentsFileEntries() throws PortalException {
		return PortletFileRepositoryUtil.getPortletFileEntries(
			0L, getAttachmentsFolderId(), WorkflowConstants.STATUS_APPROVED,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new RepositoryModelTitleComparator<>(true));
	}

	@Override
	public long getAttachmentsFolderId() throws PortalException {
		if (_attachmentsFolderId > 0) {
			return _attachmentsFolderId;
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Repository repository = PortletFileRepositoryUtil.addPortletRepository(
			0L, RemoteAppConstants.RESOURCE_NAME, serviceContext);

		Folder folder = PortletFileRepositoryUtil.addPortletFolder(
			PortalUtil.getValidUserId(getCompanyId(), getUserId()),
			repository.getRepositoryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			String.valueOf(getRemoteAppEntryId()), serviceContext);

		_attachmentsFolderId = folder.getFolderId();

		return _attachmentsFolderId;
	}

	private long _attachmentsFolderId;

}