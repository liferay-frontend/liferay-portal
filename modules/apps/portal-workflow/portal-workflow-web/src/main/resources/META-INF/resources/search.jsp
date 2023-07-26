<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<li>
	<aui:form action="<%= selectedWorkflowPortletTab.getSearchURL(renderRequest, renderResponse) %>" method="post" name="fm1">
		<liferay-util:include page="<%= selectedWorkflowPortletTab.getSearchJspPath() %>" servletContext="<%= selectedWorkflowPortletTab.getServletContext() %>" />
	</aui:form>
</li>