/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.workflow.permission;

import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Jorge Ferrer
 */
public class WorkflowPermissionUtil {

	public static WorkflowPermission getWorkflowPermission() {
		return _workflowPermission;
	}

	public static Boolean hasPermission(
		PermissionChecker permissionChecker, long groupId, String className,
		long classPK, String actionId) {

		return _workflowPermission.hasPermission(
			permissionChecker, groupId, className, classPK, actionId);
	}

	public void setWorkflowPermission(WorkflowPermission workflowPermission) {
		_workflowPermission = workflowPermission;
	}

	private static WorkflowPermission _workflowPermission;

}