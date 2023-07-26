<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String externalReferenceCode = ParamUtil.getString(request, "externalReferenceCode");

ObjectEntryDisplayContext objectEntryDisplayContext = (ObjectEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ObjectLayoutTab objectLayoutTab = objectEntryDisplayContext.getObjectLayoutTab();
%>

<c:if test="<%= (objectEntryDisplayContext.getObjectEntry() != null) && (objectLayoutTab != null) %>">

	<%
	ObjectDefinition objectDefinition = objectEntryDisplayContext.getObjectDefinition1();
	%>

	<liferay-frontend:screen-navigation
		key="<%= objectDefinition.getClassName() %>"
		portletURL="<%= currentURLObj %>"
	/>
</c:if>

<c:choose>
	<c:when test="<%= (objectLayoutTab != null) && (objectLayoutTab.getObjectRelationshipId() > 0) %>">
		<liferay-util:include page="/object_entries/object_entry/relationship.jsp" servletContext="<%= application %>">
			<liferay-util:param name="externalReferenceCode" value="<%= externalReferenceCode %>" />
			<liferay-util:param name="objectLayoutTabId" value="<%= String.valueOf(objectLayoutTab.getObjectLayoutTabId()) %>" />
		</liferay-util:include>
	</c:when>
	<c:when test="<%= objectEntryDisplayContext.isShowObjectEntryForm() %>">
		<liferay-util:include page="/object_entries/object_entry/form.jsp" servletContext="<%= application %>">
			<liferay-util:param name="externalReferenceCode" value="<%= externalReferenceCode %>" />
		</liferay-util:include>
	</c:when>
</c:choose>