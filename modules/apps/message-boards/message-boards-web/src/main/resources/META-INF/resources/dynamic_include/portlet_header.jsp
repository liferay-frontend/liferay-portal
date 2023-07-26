<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/dynamic_include/init.jsp" %>

<%
PortletResponse portletResponse = (PortletResponse)request.getAttribute(JavaConstants.JAVAX_PORTLET_RESPONSE);

MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_CATEGORY);

long categoryId = MBUtil.getCategoryId(request, category);
%>

<liferay-portlet:renderURL varImpl="searchURL">
	<portlet:param name="mvcRenderCommandName" value="/message_boards/search" />
</liferay-portlet:renderURL>

<aui:form action="<%= searchURL %>" method="get" name="searchFm">
	<liferay-portlet:renderURLParams varImpl="searchURL" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="breadcrumbsCategoryId" type="hidden" value="<%= categoryId %>" />
	<aui:input name="searchCategoryId" type="hidden" value="<%= categoryId %>" />

	<liferay-ui:input-search
		id='<%= (PortalUtil.getLiferayPortletResponse(portletResponse)).getNamespace() + "keywords1" %>'
		markupView="lexicon"
		name='<%= (PortalUtil.getLiferayPortletResponse(portletResponse)).getNamespace() + "keywords" %>'
		useNamespace="<%= false %>"
	/>
</aui:form>