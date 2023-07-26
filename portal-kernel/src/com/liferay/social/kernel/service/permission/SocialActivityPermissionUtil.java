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
public class SocialActivityPermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException {

		_socialActivityPermission.check(permissionChecker, groupId, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long groupId, String actionId) {

		return _socialActivityPermission.contains(
			permissionChecker, groupId, actionId);
	}

	public static SocialActivityPermission getSocialActivityPermission() {
		return _socialActivityPermission;
	}

	public void setSocialActivityPermission(
		SocialActivityPermission socialActivityPermission) {

		_socialActivityPermission = socialActivityPermission;
	}

	private static SocialActivityPermission _socialActivityPermission;

}