/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class TeamPermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, long teamId, String actionId)
		throws PortalException {

		_teamPermission.check(permissionChecker, teamId, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, Team team, String actionId)
		throws PortalException {

		_teamPermission.check(permissionChecker, team, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long teamId, String actionId)
		throws PortalException {

		return _teamPermission.contains(permissionChecker, teamId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, Team team, String actionId)
		throws PortalException {

		return _teamPermission.contains(permissionChecker, team, actionId);
	}

	public static TeamPermission getTeamPermission() {
		return _teamPermission;
	}

	public void setTeamPermission(TeamPermission teamPermission) {
		_teamPermission = teamPermission;
	}

	private static TeamPermission _teamPermission;

}