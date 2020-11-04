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
LayoutsTreeDisplayContext layoutsTreeDisplayContext = new LayoutsTreeDisplayContext(liferayPortletRequest);
%>

<div id="<%= liferayPortletResponse.getNamespace() + "-layout-finder" %>">
	<react:component
		module="js/LayoutFinder.es"
		props="<%= layoutsTreeDisplayContext.getLayoutFinderData() %>"
		servletContext="<%= application %>"
	/>
</div>

<div id="<%= liferayPortletResponse.getNamespace() + "layoutsTree" %>">
	<div id="<%= liferayPortletResponse.getNamespace() + "-page-type" %>">
		<react:component
			module="js/PageTypeSelector.es"
			props="<%= layoutsTreeDisplayContext.getPageTypeSelectorData() %>"
			servletContext="<%= application %>"
		/>
	</div>

	<liferay-util:buffer
		var="linkTemplate"
	>
		<clay:content-row
			containerElement="span"
			noGutters="true"
			verticalAlign="center"
		>
			<clay:content-col
				containerElement="span"
				cssClass="list-icon {type}-layout"
			>
				<aui:icon image="list" markupView="lexicon" />
			</clay:content-col>

			<clay:content-col
				containerElement="span"
				cssClass="page-icon {type}-layout"
			>
				<aui:icon image="page" markupView="lexicon" />
			</clay:content-col>

			<clay:content-col
				containerElement="span"
				expand="<%= true %>"
			>
				<span class="autofit-section">
					<a class="{cssClass}" data-regular-url="{regularURL}" data-url="{url}" data-uuid="{uuid}" href="{url}" id="{id}" title="{title}">
						<span class="c-inner" tabindex="-1">
							{label}
						</span>
					</a>
				</span>
			</clay:content-col>

			<clay:content-col
				containerElement="span"
				cssClass="pages-tree-dropdown"
			>
				<span class="dropdown">
					<button class="btn btn-monospaced-sm dropdown-toggle taglib-icon" data-toggle="liferay-dropdown">
						<span class="c-inner" tabindex="-1">
							<aui:icon image="ellipsis-v" markupView="lexicon" />

							<span class="sr-only">
								<liferay-ui:message key="options" />
							</span>
						</span>
					</button>

					<ul class="dropdown-menu dropdown-menu-left" role="menu">
						<c:if test="<%= (stagingGroup == null) || Objects.equals(scopeGroupId, stagingGroupId) %>">
							<li class="child-page-action-option type-{parentable}">
								<clay:content-row
									containerElement="a"
									cssClass="dropdown-item layout-action"
									href="<%= layoutsTreeDisplayContext.getAddChildURLTemplate() %>"
								>
									<clay:content-col
										containerElement="span"
										expand="<%= true %>"
									>
										<clay:content-section
											containerElement="span"
											cssClass="text-left"
										>
											<liferay-ui:message key="add-child-page" />
										</clay:content-section>
									</clay:content-col>
								</clay:content-row>
							</li>
							<li class="child-page-action-option type-{parentable}">
								<clay:content-row
									containerElement="a"
									cssClass="dropdown-item layout-action"
									href="<%= layoutsTreeDisplayContext.getAddChildCollectionURLTemplate() %>"
								>
									<clay:content-col
										containerElement="span"
										expand="<%= true %>"
									>
										<clay:content-section
											containerElement="span"
											cssClass="text-left"
										>
											<liferay-ui:message key="add-child-collection-page" />
										</clay:content-section>
									</clay:content-col>
								</clay:content-row>
							</li>
						</c:if>

						<li>
							<clay:content-row
								containerElement="a"
								cssClass="dropdown-item layout-action"
								href="<%= layoutsTreeDisplayContext.getConfigureLayoutURLTemplate() %>"
							>
								<clay:content-col
									containerElement="span"
									expand="<%= true %>"
								>
									<clay:content-section
										containerElement="span"
										cssClass="text-left"
									>
										<liferay-ui:message key="configure" />
									</clay:content-section>
								</clay:content-col>
							</clay:content-row>
						</li>
						<li class="view-collection-items-action-option {type}" data-view-collection-items-url="<%= layoutsTreeDisplayContext.getViewCollectionItemsURL() %>">
							<clay:content-row
								containerElement="a"
								cssClass="dropdown-item layout-action"
								href="javascript:;"
							>
								<clay:content-col
									containerElement="span"
									expand="<%= true %>"
								>
									<clay:content-section
										containerElement="span"
										cssClass="text-left"
									>
										<liferay-ui:message key="view-collection-items" />
									</clay:content-section>
								</clay:content-col>
							</clay:content-row>
						</li>
					</ul>
				</span>
			</clay:content-col>
		</clay:content-row>
	</liferay-util:buffer>

	<%
	Group siteGroup = themeDisplay.getSiteGroup();
	%>

	<liferay-layout:layouts-tree
		groupId="<%= layoutsTreeDisplayContext.getGroupId() %>"
		linkTemplate="<%= linkTemplate %>"
		privateLayout="<%= layoutsTreeDisplayContext.isPrivateLayout() %>"
		rootLinkTemplate='<a class="{cssClass}" href="javascript:void(0);" id="{id}" title="{title}">{label}</a>'
		rootNodeName="<%= siteGroup.getLayoutRootNodeName(layoutsTreeDisplayContext.isPrivateLayout(), locale) %>"
		selPlid="<%= plid %>"
		treeId="pagesTree"
	/>

	<div class="pages-administration">
		<aui:a cssClass="pages-administration-link" href="<%= layoutsTreeDisplayContext.getAdministrationPortletURL() %>"><span class="c-inner" tabindex="-1"><%= LanguageUtil.get(request, "go-to-pages-administration") %></span></aui:a>
	</div>
</div>

<liferay-frontend:component
	componentId="<%= ProductNavigationProductMenuWebKeys.PAGES_TREE_EVENT_HANDLER %>"
	module="js/PagesTreeEventHandler.es"
/>

<aui:script require="metal-dom/src/all/dom as dom">
	var layoutsTree = document.getElementById('<portlet:namespace />layoutsTree');

	var viewCollectionItemsActionOptionQueryClickHandler = dom.delegate(
		layoutsTree,
		'click',
		'.view-collection-items-action-option.collection',
		function (event) {
			Liferay.Util.openModal({
				id: '<portlet:namespace />viewCollectionItemsDialog',
				title: '<liferay-ui:message key="collection-items" />',
				url: event.delegateTarget.dataset.viewCollectionItemsUrl,
			});
		}
	);

	function handleDestroyPortlet() {
		Liferay.destroyComponent(
			'<%= liferayPortletResponse.getNamespace() %>pagesTree'
		);
		Liferay.destroyComponent(
			'<%= ProductNavigationProductMenuWebKeys.PAGES_TREE_EVENT_HANDLER %>'
		);

		viewCollectionItemsActionOptionQueryClickHandler.removeListener();

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>