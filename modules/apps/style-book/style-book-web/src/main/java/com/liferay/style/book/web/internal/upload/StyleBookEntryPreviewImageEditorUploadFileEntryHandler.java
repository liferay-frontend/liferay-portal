/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.web.internal.upload;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.style.book.constants.StyleBookActionKeys;
import com.liferay.style.book.constants.StyleBookConstants;
import com.liferay.upload.BaseImageEditorUploadFileEntryHandler;
import com.liferay.upload.UniqueFileNameProvider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	service = StyleBookEntryPreviewImageEditorUploadFileEntryHandler.class
)
public class StyleBookEntryPreviewImageEditorUploadFileEntryHandler
	extends BaseImageEditorUploadFileEntryHandler {

	@Override
	protected void checkPermissions(UploadPortletRequest uploadPortletRequest)
		throws PrincipalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)uploadPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_portletResourcePermission.check(
			themeDisplay.getPermissionChecker(), themeDisplay.getScopeGroup(),
			StyleBookActionKeys.MANAGE_STYLE_BOOK_ENTRIES);
	}

	@Override
	protected DLAppService getDLAppService() {
		return _dlAppService;
	}

	@Override
	protected String getFolderName() {
		return StyleBookEntryPreviewImageEditorUploadFileEntryHandler.class.
			getName();
	}

	@Override
	protected UniqueFileNameProvider getUniqueFileNameProvider() {
		return _uniqueFileNameProvider;
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference(
		target = "(resource.name=" + StyleBookConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private UniqueFileNameProvider _uniqueFileNameProvider;

}