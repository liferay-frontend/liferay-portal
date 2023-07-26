/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.workflow;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class WorkflowEngineManagerUtil {

	public static String getKey() {
		return _workflowEngineManager.getKey();
	}

	public static String getName() {
		return _workflowEngineManager.getName();
	}

	public static Map<String, Object> getOptionalAttributes() {
		return _workflowEngineManager.getOptionalAttributes();
	}

	public static String getVersion() {
		return _workflowEngineManager.getVersion();
	}

	public static boolean isDeployed() {
		return _workflowEngineManager.isDeployed();
	}

	private static volatile WorkflowEngineManager _workflowEngineManager =
		ServiceProxyFactory.newServiceTrackedInstance(
			WorkflowEngineManager.class, WorkflowEngineManagerUtil.class,
			"_workflowEngineManager", true);

}