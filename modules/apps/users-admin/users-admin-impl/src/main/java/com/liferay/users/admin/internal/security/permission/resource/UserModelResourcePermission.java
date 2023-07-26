/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.internal.security.permission.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.permission.UserPermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luan Maoski
 */
@Component(
	property = "model.class.name=com.liferay.portal.kernel.model.User",
	service = ModelResourcePermission.class
)
public class UserModelResourcePermission
	implements ModelResourcePermission<User> {

	@Override
	public void check(
			PermissionChecker permissionChecker, long organizationId,
			String actionId)
		throws PortalException {

		userPermission.check(permissionChecker, organizationId, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, User user, String actionId)
		throws PortalException {

		userPermission.check(
			permissionChecker, user.getUserId(), user.getOrganizationIds(),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long organizationId,
			String actionId)
		throws PortalException {

		return userPermission.contains(
			permissionChecker, organizationId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, User user, String actionId)
		throws PortalException {

		return userPermission.contains(
			permissionChecker, user.getUserId(), user.getOrganizationIds(),
			actionId);
	}

	@Override
	public String getModelName() {
		return User.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	protected UserPermission userPermission;

}