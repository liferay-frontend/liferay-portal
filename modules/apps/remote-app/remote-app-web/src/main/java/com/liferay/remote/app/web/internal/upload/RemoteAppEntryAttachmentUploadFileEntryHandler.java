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

package com.liferay.remote.app.web.internal.upload;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.remote.app.model.RemoteAppEntry;
import com.liferay.remote.app.service.RemoteAppEntryService;
import com.liferay.upload.UniqueFileNameProvider;
import com.liferay.upload.UploadFileEntryHandler;

import java.io.IOException;
import java.io.InputStream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier de Arcos
 */
@Component(service = RemoteAppEntryAttachmentUploadFileEntryHandler.class)
public class RemoteAppEntryAttachmentUploadFileEntryHandler
	implements UploadFileEntryHandler {

	@Override
	public FileEntry upload(UploadPortletRequest uploadPortletRequest)
		throws IOException, PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)uploadPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long remoteAppEntryId = ParamUtil.getLong(
			uploadPortletRequest, "remoteAppEntryId");

		RemoteAppEntry remoteAppEntry =
			_remoteAppEntryService.getRemoteAppEntry(remoteAppEntryId);

		String fileName = uploadPortletRequest.getFileName(_PARAMETER_NAME);
		String contentType = uploadPortletRequest.getContentType(
			_PARAMETER_NAME);

		try (InputStream inputStream = uploadPortletRequest.getFileAsStream(
				_PARAMETER_NAME)) {

			String uniqueFileName = _uniqueFileNameProvider.provide(
				fileName,
				curFileName -> _exists(
					themeDisplay, remoteAppEntry, curFileName));

			return _remoteAppEntryService.addAttachment(
				remoteAppEntryId, uniqueFileName, inputStream, contentType);
		}
	}

	private boolean _exists(
		ThemeDisplay themeDisplay, RemoteAppEntry remoteAppEntry,
		String fileName) {

		try {
			FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
				themeDisplay.getScopeGroupId(),
				remoteAppEntry.getAttachmentsFolderId(), fileName);

			if (fileEntry != null) {
				return true;
			}

			return false;
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}

			return false;
		}
	}

	private static final String _PARAMETER_NAME = "imageSelectorFileName";

	private static final Log _log = LogFactoryUtil.getLog(
		RemoteAppEntryAttachmentUploadFileEntryHandler.class);

	@Reference
	private RemoteAppEntryService _remoteAppEntryService;

	@Reference
	private UniqueFileNameProvider _uniqueFileNameProvider;

}