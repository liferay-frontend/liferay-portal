/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.expando.service.permission;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.service.ExpandoColumnLocalServiceUtil;
import com.liferay.expando.kernel.service.permission.ExpandoColumnPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Raymond Augé
 */
public class ExpandoColumnPermissionImpl implements ExpandoColumnPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, ExpandoColumn column,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, column, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, ExpandoColumn.class.getName(),
				column.getColumnId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long columnId, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, columnId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, ExpandoColumn.class.getName(), columnId,
				actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long companyId,
			String className, String tableName, String columnName,
			String actionId)
		throws PortalException {

		check(
			permissionChecker,
			ExpandoColumnLocalServiceUtil.getColumn(
				companyId, className, tableName, columnName),
			actionId);
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, ExpandoColumn column,
		String actionId) {

		return permissionChecker.hasPermission(
			null, ExpandoColumn.class.getName(), column.getColumnId(),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long columnId, String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			ExpandoColumnLocalServiceUtil.getColumn(columnId), actionId);
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long companyId, String className,
		String tableName, String columnName, String actionId) {

		return contains(
			permissionChecker,
			ExpandoColumnLocalServiceUtil.getColumn(
				companyId, className, tableName, columnName),
			actionId);
	}

}