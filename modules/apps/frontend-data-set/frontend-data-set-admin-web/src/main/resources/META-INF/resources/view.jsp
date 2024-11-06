<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
portletDisplay.setBeta(true);
%>

<c:choose>
	<c:when test='<%= FeatureFlagManagerUtil.isEnabled("LPD-37531") %>'>
		<clay:navigation-bar
			navigationItems='<%= fdsAdminDisplayContext.getNavigationItems("custom-data-sets") %>'
		/>
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/custom_data_sets.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>