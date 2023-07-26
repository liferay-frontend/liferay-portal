/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.upload;

import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.document.library.kernel.util.DLValidator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UniqueFileNameProvider;
import com.liferay.upload.UploadFileEntryHandler;

import java.io.IOException;
import java.io.InputStream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CSVUploadFileEntryHandler.class)
public class CSVUploadFileEntryHandler implements UploadFileEntryHandler {

	@Override
	public FileEntry upload(UploadPortletRequest uploadPortletRequest)
		throws IOException, PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)uploadPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String fileName = uploadPortletRequest.getFileName(_PARAMETER_NAME);

		_dlValidator.validateFileSize(
			themeDisplay.getScopeGroupId(), fileName,
			uploadPortletRequest.getContentType(_PARAMETER_NAME),
			uploadPortletRequest.getSize(_PARAMETER_NAME));

		String extension = _file.getExtension(fileName);

		if (!extension.equals("csv")) {
			throw new FileExtensionException(
				"Invalid extension for file name " + fileName);
		}

		try (InputStream inputStream = uploadPortletRequest.getFileAsStream(
				_PARAMETER_NAME)) {

			return _addFileEntry(
				fileName, uploadPortletRequest.getContentType(_PARAMETER_NAME),
				inputStream,
				(ThemeDisplay)uploadPortletRequest.getAttribute(
					WebKeys.THEME_DISPLAY));
		}
	}

	private FileEntry _addFileEntry(
			String fileName, String contentType, InputStream inputStream,
			ThemeDisplay themeDisplay)
		throws PortalException {

		String uniqueFileName = _uniqueFileNameProvider.provide(
			fileName, curFileName -> _exists(curFileName, themeDisplay));

		Company company = themeDisplay.getCompany();

		return TempFileEntryUtil.addTempFileEntry(
			company.getGroupId(), themeDisplay.getUserId(), _TEMP_FOLDER_NAME,
			uniqueFileName, inputStream, contentType);
	}

	private boolean _exists(String fileName, ThemeDisplay themeDisplay) {
		try {
			FileEntry tempFileEntry = TempFileEntryUtil.getTempFileEntry(
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
				_TEMP_FOLDER_NAME, fileName);

			if (tempFileEntry != null) {
				return true;
			}

			return false;
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return false;
		}
	}

	private static final String _PARAMETER_NAME = "imageSelectorFileName";

	private static final String _TEMP_FOLDER_NAME =
		CSVUploadFileEntryHandler.class.getName();

	private static final Log _log = LogFactoryUtil.getLog(
		CSVUploadFileEntryHandler.class);

	@Reference
	private DLValidator _dlValidator;

	@Reference
	private File _file;

	@Reference
	private UniqueFileNameProvider _uniqueFileNameProvider;

}