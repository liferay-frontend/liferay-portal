<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String innerNavigation = ParamUtil.getString(request, "innerNavigation", "react-basic");
%>

				<!-- add(
					navigationItem -> {
						navigationItem.setActive(innerNavigation.equals("classic"));
						navigationItem.setHref(renderResponse.createRenderURL(), "navigation", "ckeditor5");
						navigationItem.setLabel("Classic");
					});
				add(
					navigationItem -> {
						navigationItem.setActive(innerNavigation.equals("react"));
						navigationItem.setHref(renderResponse.createRenderURL(), "navigation", "ckeditor5", "innerNavigation", "react");
						navigationItem.setLabel("React");
					}); -->

<clay:navigation-bar
	navigationItems='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(innerNavigation.equals("react-basic"));
						navigationItem.setHref(renderResponse.createRenderURL(), "navigation", "ckeditor5", "innerNavigation", "react-basic");
						navigationItem.setLabel("Basic");
					});
				add(
					navigationItem -> {
						navigationItem.setActive(innerNavigation.equals("react-advanced"));
						navigationItem.setHref(renderResponse.createRenderURL(), "navigation", "ckeditor5", "innerNavigation", "react-advanced");
						navigationItem.setLabel("Advanced");
					});
			}
		}
	%>'
/>

<clay:container-fluid
	cssClass="mt-3"
>
	<c:choose>
		<c:when test='<%= StringUtil.equals(innerNavigation, "classic") %>'>
			<liferay-util:include page="/ckeditor5/partials/classic.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test='<%= StringUtil.equals(innerNavigation, "react") %>'>
			<liferay-util:include page="/ckeditor5/partials/react.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test='<%= StringUtil.equals(innerNavigation, "react-basic") %>'>
			<liferay-util:include page="/ckeditor5/partials/react-basic.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:otherwise>
			<liferay-util:include page="/ckeditor5/partials/react-advanced.jsp" servletContext="<%= application %>" />
		</c:otherwise>
	</c:choose>
</clay:container-fluid>