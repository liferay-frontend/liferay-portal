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
String portletId = portletDisplay.getRootPortletId();

PanelAppRegistry panelAppRegistry = (PanelAppRegistry)request.getAttribute(ApplicationListWebKeys.PANEL_APP_REGISTRY);
PanelCategoryRegistry panelCategoryRegistry = (PanelCategoryRegistry)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY_REGISTRY);

PanelCategoryHelper panelCategoryHelper = new PanelCategoryHelper(panelAppRegistry, panelCategoryRegistry);

String rootPanelCategoryKey = panelCategoryHelper.containsPortlet(portletId, "applications_menu.applications") ? "applications_menu.applications" : "control_panel";

List<PanelCategory> panelCategories = panelCategoryRegistry.getChildPanelCategories(rootPanelCategoryKey);

PanelCategory curPanelCategory = null;

for (PanelCategory panelCategory : panelCategories) {
	curPanelCategory = panelCategory;

	if (panelCategoryHelper.containsPortlet(portletId, panelCategory.getKey())) {
		break;
	}
}

List<PanelApp> panelApps = panelCategoryHelper.getAllPanelApps(curPanelCategory.getKey());

String portletTitle = (String)request.getAttribute(ProductNavigationControlMenuWebKeys.PORTLET_TITLE);
%>

<li class="control-menu-nav-item control-menu-nav-item-content d-inline">
	<span class="small"><%= curPanelCategory.getLabel(locale) %></span>

	<div>
		<span class="control-menu-level-1-heading inline-item inline-item-before text-truncate" data-qa-id="headerTitle"><%= HtmlUtil.escape(portletTitle) %></span>
	</div>
</li>