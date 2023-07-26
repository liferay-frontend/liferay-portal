/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.web.internal.util;

import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.liferay.portal.workflow.constants.WorkflowPortletKeys;
import com.liferay.portal.workflow.web.internal.configuration.WorkflowInstanceWebConfiguration;

import javax.portlet.PortletRequest;

/**
 * @author Marcellus Tavares
 */
public class WorkflowInstancePortletUtil {

	public static String getDisplayStyle(
		PortletRequest portletRequest, String[] displayViews) {

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(portletRequest);

		String displayStyle = ParamUtil.getString(
			portletRequest, "displayStyle");

		if (Validator.isNull(displayStyle)) {
			WorkflowInstanceWebConfiguration workflowInstanceWebConfiguration =
				(WorkflowInstanceWebConfiguration)portletRequest.getAttribute(
					WorkflowInstanceWebConfiguration.class.getName());

			displayStyle = portalPreferences.getValue(
				WorkflowPortletKeys.USER_WORKFLOW, "instance-display-style",
				workflowInstanceWebConfiguration.defaultDisplayView());
		}
		else if (ArrayUtil.contains(displayViews, displayStyle)) {
			portalPreferences.setValue(
				WorkflowPortletKeys.USER_WORKFLOW, "instance-display-style",
				displayStyle);
		}

		if (!ArrayUtil.contains(displayViews, displayStyle)) {
			displayStyle = displayViews[0];
		}

		return displayStyle;
	}

	public static OrderByComparator<WorkflowInstance>
		getWorkflowInstanceOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<WorkflowInstance> orderByComparator = null;

		if (orderByCol.equals("last-activity-date")) {
			orderByComparator =
				WorkflowComparatorFactoryUtil.getInstanceStartDateComparator(
					orderByAsc);
		}
		else {
			orderByComparator =
				WorkflowComparatorFactoryUtil.getInstanceEndDateComparator(
					orderByAsc);
		}

		return orderByComparator;
	}

}