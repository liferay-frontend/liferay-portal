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

<%@ include file="/portlet/init.jsp" %>

<%
PanelAppRegistry panelAppRegistry = (PanelAppRegistry)request.getAttribute(ApplicationListWebKeys.PANEL_APP_REGISTRY);
PanelCategoryRegistry panelCategoryRegistry = (PanelCategoryRegistry)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY_REGISTRY);

PanelCategory panelCategory = panelCategoryRegistry.getPanelCategory(ProductNavigationSimulationConstants.SIMULATION_PANEL_CATEGORY_KEY);
PanelCategoryHelper panelCategoryHelper = new PanelCategoryHelper(panelAppRegistry, panelCategoryRegistry);
%>

<div class="simulation-menu" data-qa-id="simulationMenuBody" id="<portlet:namespace />simulationPanelContainer">
	<div aria-multiselectable="true" class="product-menu-panel-group" role="tablist">

		<%
		for (PanelApp panelApp : panelCategoryHelper.getAllPanelApps(panelCategory.getKey())) {
		%>

			<div class="product-menu-panel">
				<div class="product-menu-panel-header" id="<portlet:namespace /><%= AUIUtil.normalizeId(panelApp.getKey()) %>Header" role="tab">
					<a aria-controls="<portlet:namespace /><%= AUIUtil.normalizeId(panelApp.getKey()) %>Collapse" aria-expanded="<%= true %>" class="collapse-icon collapse-icon-middle product-menu-link" data-toggle="liferay-collapse" href="#<portlet:namespace /><%= AUIUtil.normalizeId(panelApp.getKey()) %>Collapse" role="button">
						<span class="c-inner" tabindex="-1">
							<span class="category-name text-truncate"><%= panelApp.getLabel(locale) %></span>

							<aui:icon cssClass="collapse-icon-closed" image="angle-right" markupView="lexicon" />

							<aui:icon cssClass="collapse-icon-open" image="angle-down" markupView="lexicon" />
						</span>
					</a>
				</div>

				<div aria-expanded="<%= true %>" aria-labelledby="<portlet:namespace /><%= AUIUtil.normalizeId(panelApp.getKey()) %>Header" class="collapse product-menu-panel-collapse show" id="<portlet:namespace /><%= AUIUtil.normalizeId(panelApp.getKey()) %>Collapse" role="tabpanel">
					<div class="product-menu-panel-body">
						<liferay-application-list:panel-app
							panelApp="<%= panelApp %>"
						/>
					</div>
				</div>
			</div>

		<%
		}
		%>

	</div>
</div>