/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.RolePermission;

import java.util.Objects;

/**
 * @author Charles May
 */
public class RolePermissionImpl implements RolePermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, long roleId, String actionId)
		throws PrincipalException {

		if (!contains(permissionChecker, roleId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, Role.class.getName(), roleId, actionId);
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long groupId, long roleId,
		String actionId) {

		if (Objects.equals(ActionKeys.ASSIGN_MEMBERS, actionId)) {
			Role role = RoleLocalServiceUtil.fetchRole(roleId);

			if ((role != null) &&
				Objects.equals(RoleConstants.ADMINISTRATOR, role.getName()) &&
				!permissionChecker.isCompanyAdmin()) {

				return false;
			}
		}

		return permissionChecker.hasPermission(
			groupId, Role.class.getName(), roleId, actionId);
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long roleId, String actionId) {

		return contains(permissionChecker, 0, roleId, actionId);
	}

}