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

<%@ include file="/custom_element_portlet_admin/init.jsp" %>

<%
CustomElementPortletAdminDisplayContext customElementPortletAdminDisplayContext = (CustomElementPortletAdminDisplayContext)renderRequest.getAttribute(RemoteAppAdminWebKeys.CUSTOM_ELEMENT_PORTLET_ADMIN_DISPLAY_CONTEXT);
%>

<clay:data-set-display
	actionParameterName="customElementPortletEntryId"
	creationMenu="<%= customElementPortletAdminDisplayContext.getCreationMenu() %>"
	dataProviderKey="<%= RemoteAppAdminConstants.CUSTOM_ELEMENT_PORTLET_ENTRY_DATA_SET_DISPLAY %>"
	formId='<%= liferayPortletResponse.getNamespace() + "fm" %>'
	id="<%= RemoteAppAdminConstants.CUSTOM_ELEMENT_PORTLET_ENTRY_DATA_SET_DISPLAY %>"
	itemsPerPage="<%= 10 %>"
	namespace="<%= liferayPortletResponse.getNamespace() %>"
	pageNumber="<%= 1 %>"
	portletURL="<%= customElementPortletAdminDisplayContext.getCurrentPortletURL() %>"
	selectedItemsKey="customElementPortletEntryId"
	selectionType="multiple"
	style="fluid"
/>