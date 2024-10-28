<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String navigation = ParamUtil.getString(request, "navigation", "react");
%>

<clay:navigation-bar
	navigationItems='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(navigation.equals("react"));
						navigationItem.setHref(renderResponse.createRenderURL());
						navigationItem.setLabel("React");
					});
				add(
					navigationItem -> {
						navigationItem.setActive(navigation.equals("classic"));
						navigationItem.setHref(renderResponse.createRenderURL(), "navigation", "classic");
						navigationItem.setLabel("Classic");
					});
			}
		}
	%>'
/>

<c:choose>
	<c:when test='<%= StringUtil.equals(navigation, "react") %>'>
		<liferay-util:include page="/partials/react.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/partials/classic.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>