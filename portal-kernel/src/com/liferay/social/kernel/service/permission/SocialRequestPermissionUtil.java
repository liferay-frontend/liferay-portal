/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.kernel.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Zsolt Berentey
 */
public class SocialRequestPermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, long requestId,
			String actionId)
		throws PortalException {

		_socialRequestPermission.check(permissionChecker, requestId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long requestId,
			String actionId)
		throws PortalException {

		return _socialRequestPermission.contains(
			permissionChecker, requestId, actionId);
	}

	public static SocialRequestPermission getSocialRequestPermission() {
		return _socialRequestPermission;
	}

	public void setSocialRequestPermission(
		SocialRequestPermission socialRequestPermission) {

		_socialRequestPermission = socialRequestPermission;
	}

	private static SocialRequestPermission _socialRequestPermission;

}