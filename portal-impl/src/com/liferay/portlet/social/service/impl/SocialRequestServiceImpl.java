/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.social.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portlet.social.service.base.SocialRequestServiceBaseImpl;
import com.liferay.social.kernel.model.SocialRequest;
import com.liferay.social.kernel.service.permission.SocialRequestPermissionUtil;

/**
 * Provides the remote service for updating social requests. Its methods include
 * permission checks.
 *
 * @author Brian Wing Shun Chan
 */
public class SocialRequestServiceImpl extends SocialRequestServiceBaseImpl {

	@Override
	public SocialRequest updateRequest(
			long requestId, int status, ThemeDisplay themeDisplay)
		throws PortalException {

		SocialRequestPermissionUtil.check(
			getPermissionChecker(), requestId, ActionKeys.UPDATE);

		return socialRequestLocalService.updateRequest(
			requestId, status, themeDisplay);
	}

}