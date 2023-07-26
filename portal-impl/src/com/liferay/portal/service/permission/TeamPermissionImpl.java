/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.permission;

import com.liferay.exportimport.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.TeamLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.TeamPermission;

/**
 * @author Brian Wing Shun Chan
 */
public class TeamPermissionImpl implements TeamPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, long teamId, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, teamId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, Team.class.getName(), teamId, actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, Team team, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, team, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, Team.class.getName(), team.getTeamId(),
				actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long teamId, String actionId)
		throws PortalException {

		return contains(
			permissionChecker, TeamLocalServiceUtil.getTeam(teamId), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, Team team, String actionId)
		throws PortalException {

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, team.getGroupId(), Team.class.getName(),
			team.getTeamId(), StringPool.BLANK, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (GroupPermissionUtil.contains(
				permissionChecker, team.getGroupId(),
				ActionKeys.MANAGE_TEAMS) ||
			permissionChecker.hasOwnerPermission(
				team.getCompanyId(), Team.class.getName(), team.getTeamId(),
				team.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			team.getGroupId(), Team.class.getName(), team.getTeamId(),
			actionId);
	}

}