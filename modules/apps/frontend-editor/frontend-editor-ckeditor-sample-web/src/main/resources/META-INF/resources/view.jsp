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
String tabs1 = ParamUtil.getString(request, "tabs1", "customized");
PortletURL portletURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setTabs1(
	tabs1
).buildPortletURL();
%>

<clay:container-fluid>
	<liferay-ui:tabs
		names="Balloon Editor,Alloy Editor,CKEditor Classic Editor,CKEditor BBCode,Editor React Component,minimum"
		url="<%= portletURL.toString() %>"
	>
		<liferay-ui:section>
			<c:if test='<%= tabs1.equals("Balloon Editor") %>'>
				<liferay-util:include page="/partials/ballooneditor.jsp" servletContext="<%= pageContext.getServletContext() %>" />
			</c:if>
		</liferay-ui:section>

		<liferay-ui:section>
			<c:if test='<%= tabs1.equals("Alloy Editor") %>'>
				<liferay-util:include page="/partials/alloyeditor.jsp" servletContext="<%= pageContext.getServletContext() %>" />
			</c:if>
		</liferay-ui:section>

		<liferay-ui:section>
			<c:if test='<%= tabs1.equals("CKEditor Classic Editor") %>'>
				<liferay-util:include page="/partials/ckeditor_classic.jsp" servletContext="<%= pageContext.getServletContext() %>" />
			</c:if>
		</liferay-ui:section>

		<liferay-ui:section>
			<c:if test='<%= tabs1.equals("CKEditor BBCode") %>'>
				<liferay-util:include page="/partials/ckeditor_bbcode.jsp" servletContext="<%= pageContext.getServletContext() %>" />
			</c:if>
		</liferay-ui:section>

		<liferay-ui:section>
			<c:if test='<%= tabs1.equals("Editor React Component") %>'>
				<liferay-util:include page="/partials/ckeditor_editor_react_component.jsp" servletContext="<%= pageContext.getServletContext() %>" />
			</c:if>
		</liferay-ui:section>
	</liferay-ui:tabs>
</clay:container-fluid>