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
ProductsMenuAppHeaderDisplayContext productsMenuAppHeaderDisplayContext = new ProductsMenuAppHeaderDisplayContext(request);

PanelCategory curPanelCategory = productsMenuAppHeaderDisplayContext.getCurPanelCategory();

List<PanelApp> panelApps = productsMenuAppHeaderDisplayContext.getPanelApps();
%>

<li class="control-menu-nav-item control-menu-nav-item-content">
	<div class="control-menu-level-3-heading"><%= curPanelCategory.getLabel(locale) %></div>

	<div class="control-menu-level-1-heading">
		<span class="inline-item inline-item-before text-truncate" data-qa-id="headerTitle"><%= HtmlUtil.escape(productsMenuAppHeaderDisplayContext.getPortletTitle()) %></span>

		<c:if test="<%= panelApps.size() > 1 %>">
			<clay:icon
				symbol="caret-double-l"
			/>
		</c:if>
	</div>

	<c:if test="<%= panelApps.size() > 1 %>">
		<react:component
			module="js/PortletHeader"
			props="<%= productsMenuAppHeaderDisplayContext.getProps() %>"
		/>
	</c:if>
</li>