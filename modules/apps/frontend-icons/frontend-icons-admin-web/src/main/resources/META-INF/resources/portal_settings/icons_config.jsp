<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/init.jsp" %>

<%
String iconsMapJSON = (String)request.getAttribute("icons");

String redirect = ParamUtil.getString(request, "redirect");

PortletURL portletURL = renderResponse.createRenderURL();

if (Validator.isNull(redirect)) {
	redirect = portletURL.toString();
}
%>

<portlet:actionURL name="/frontend_icons_admin/save_custom_icon" var="saveActionURL" />
<portlet:actionURL name="/frontend_icons_admin/delete_custom_icon" var="deleteActionURL" />

<div>
	<react:component
		module="js/IconSearch"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"deleteURL", deleteActionURL
			).put(
				"initialIcons", iconsMapJSON
			).put(
				"redirectURL", currentURL
			).put(
				"submitURL", saveActionURL
			).build()
		%>'
	/>
</div>