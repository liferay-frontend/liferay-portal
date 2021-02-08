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

<%@ include file="/display/init.jsp" %>

<div class="table-root" id="<%= containerId %>">
	<span aria-hidden="true" class="loading-animation my-7"></span>

	<react:component
		module="display/entry"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"actionParameterName", GetterUtil.getString(actionParameterName)
			).put(
				"activeViewSettings", activeViewSettingsJSON
			).put(
				"apiURL", apiURL
			).put(
				"appURL", appURL
			).put(
				"bulkActions", bulkActionDropdownItems
			).put(
				"componentId", containerId
			).put(
				"creationMenu", creationMenu
			).put(
				"currentURL", PortalUtil.getCurrentURL(request)
			).put(
				"dataProviderKey", dataProviderKey
			).put(
				"formId", GetterUtil.getString(formId)
			).put(
				"id", id
			).put(
				"nestedItemsKey", GetterUtil.getString(nestedItemsKey)
			).put(
				"nestedItemsReferenceKey", GetterUtil.getString(nestedItemsReferenceKey)
			).put(
				"pagination", HashMapBuilder.<String, Object>put(
						"deltas", clayPaginationEntries
					).put(
						"initialDelta", itemsPerPage
					).put(
						"initialPageNumber", pageNumber
					).build()
			).put(
				"showManagementBar", showManagementBar
			).put(
				"showPagination", showPagination
			).put(
				"showSearch", showSearch
			).put(
				"namespace", namespace
			).put(
				"portletId", portletDisplay.getRootPortletId()
			).put(
				"portletURL", portletURL
			).put(
				"selectedItems", selectedItems
			).put(
				"selectedItemsKey", GetterUtil.getString(selectedItemsKey)
			).put(
				"selectionType", GetterUtil.getString(selectionType)
			).put(
				"sorting", sortItemList
			).put(
				"style", style
			).put(
				"views", clayDataSetDisplayViewsContext
			).build()
		%>'
	/>
</div>