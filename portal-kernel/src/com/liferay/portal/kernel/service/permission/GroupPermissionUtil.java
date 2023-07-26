/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Brian Wing Shun Chan
 */
public class GroupPermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, Group group, String actionId)
		throws PortalException {

		_groupPermission.check(permissionChecker, group, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException {

		_groupPermission.check(permissionChecker, groupId, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, String actionId)
		throws PortalException {

		_groupPermission.check(permissionChecker, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, Group group, String actionId)
		throws PortalException {

		return _groupPermission.contains(permissionChecker, group, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException {

		return _groupPermission.contains(permissionChecker, groupId, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, String actionId) {

		return _groupPermission.contains(permissionChecker, actionId);
	}

	public static GroupPermission getGroupPermission() {
		return _groupPermission;
	}

	public void setGroupPermission(GroupPermission groupPermission) {
		_groupPermission = groupPermission;
	}

	private static GroupPermission _groupPermission;

}