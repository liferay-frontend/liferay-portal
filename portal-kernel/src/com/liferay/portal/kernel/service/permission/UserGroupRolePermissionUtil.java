/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class UserGroupRolePermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, Group group, Role role)
		throws PortalException {

		_userGroupRolePermission.check(permissionChecker, group, role);
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId, long roleId)
		throws PortalException {

		_userGroupRolePermission.check(permissionChecker, groupId, roleId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, Group group, Role role)
		throws PortalException {

		return _userGroupRolePermission.contains(
			permissionChecker, group, role);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, long roleId)
		throws PortalException {

		return _userGroupRolePermission.contains(
			permissionChecker, groupId, roleId);
	}

	public static UserGroupRolePermission getUserGroupRolePermission() {
		return _userGroupRolePermission;
	}

	public void setUserGroupRolePermission(
		UserGroupRolePermission userGroupRolePermission) {

		_userGroupRolePermission = userGroupRolePermission;
	}

	private static UserGroupRolePermission _userGroupRolePermission;

}