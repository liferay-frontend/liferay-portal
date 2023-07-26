/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.permission;

import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Brian Wing Shun Chan
 */
public class RolePermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, long roleId, String actionId)
		throws PrincipalException {

		_rolePermission.check(permissionChecker, roleId, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long groupId, long roleId,
		String actionId) {

		return _rolePermission.contains(
			permissionChecker, groupId, roleId, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long roleId, String actionId) {

		return _rolePermission.contains(permissionChecker, roleId, actionId);
	}

	public static RolePermission getRolePermission() {
		return _rolePermission;
	}

	public void setRolePermission(RolePermission rolePermission) {
		_rolePermission = rolePermission;
	}

	private static RolePermission _rolePermission;

}