/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
@ProviderType
public interface UserGroupRolePermission {

	public void check(
			PermissionChecker permissionChecker, Group group, Role role)
		throws PortalException;

	public void check(
			PermissionChecker permissionChecker, long groupId, long roleId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, Group group, Role role)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, long roleId)
		throws PortalException;

}