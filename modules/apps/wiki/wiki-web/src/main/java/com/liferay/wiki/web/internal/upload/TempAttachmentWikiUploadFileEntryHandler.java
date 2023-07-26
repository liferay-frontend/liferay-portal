/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.web.internal.upload;

import com.liferay.document.library.kernel.util.DLValidator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UploadFileEntryHandler;
import com.liferay.wiki.constants.WikiConstants;
import com.liferay.wiki.service.WikiPageService;

import java.io.IOException;
import java.io.InputStream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = TempAttachmentWikiUploadFileEntryHandler.class)
public class TempAttachmentWikiUploadFileEntryHandler
	implements UploadFileEntryHandler {

	@Override
	public FileEntry upload(UploadPortletRequest uploadPortletRequest)
		throws IOException, PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)uploadPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_dlValidator.validateFileSize(
			themeDisplay.getScopeGroupId(),
			uploadPortletRequest.getFileName(_PARAMETER_NAME),
			uploadPortletRequest.getContentType(_PARAMETER_NAME),
			uploadPortletRequest.getSize(_PARAMETER_NAME));

		long nodeId = ParamUtil.getLong(
			uploadPortletRequest.getPortletRequest(), "nodeId");

		try (InputStream inputStream = uploadPortletRequest.getFileAsStream(
				_PARAMETER_NAME)) {

			return _wikiPageService.addTempFileEntry(
				nodeId, WikiConstants.TEMP_FOLDER_NAME,
				TempFileEntryUtil.getTempFileName(
					uploadPortletRequest.getFileName(_PARAMETER_NAME)),
				inputStream,
				uploadPortletRequest.getContentType(_PARAMETER_NAME));
		}
	}

	private static final String _PARAMETER_NAME = "file";

	@Reference
	private DLValidator _dlValidator;

	@Reference
	private WikiPageService _wikiPageService;

}