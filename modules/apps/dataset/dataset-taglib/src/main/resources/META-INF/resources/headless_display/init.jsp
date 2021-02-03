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

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.dataset.taglib.clay.data.set.model.ClayPaginationEntry" %><%@
page import="com.liferay.dataset.taglib.clay.data.set.servlet.taglib.util.ClayDataSetActionDropdownItem" %><%@
page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu" %><%@
page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem" %><%@
page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.SortItemList" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.json.JSONFactoryUtil" %><%@
page import="com.liferay.portal.kernel.json.JSONSerializer" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %>

<%@ page import="java.util.List" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-theme:defineObjects />

<%
String actionParameterName = (String)request.getAttribute("dataset:headless-display:actionParameterName");
String activeViewSettingsJSON = GetterUtil.getString(request.getAttribute("dataset:headless-display:activeViewSettingsJSON"), "{}");
String apiURL = (String)request.getAttribute("dataset:headless-display:apiURL");
String appURL = (String)request.getAttribute("dataset:headless-display:appURL");
List<DropdownItem> bulkActionDropdownItems = (List<DropdownItem>)request.getAttribute("dataset:headless-display:bulkActionDropdownItems");
Object clayDataSetDisplayViewsContext = request.getAttribute("dataset:headless-display:clayDataSetDisplayViewsContext");
Object clayDataSetFiltersContext = request.getAttribute("dataset:headless-display:clayDataSetFiltersContext");
List<ClayDataSetActionDropdownItem> clayDataSetActionDropdownItems = (List<ClayDataSetActionDropdownItem>)request.getAttribute("dataset:headless-display:clayDataSetActionDropdownItems");
List<ClayPaginationEntry> clayPaginationEntries = (List<ClayPaginationEntry>)request.getAttribute("dataset:headless-display:clayPaginationEntries");
CreationMenu creationMenu = (CreationMenu)request.getAttribute("dataset:headless-display:creationMenu");
String formId = (String)request.getAttribute("dataset:headless-display:formId");
String id = (String)request.getAttribute("dataset:headless-display:id");
int itemsPerPage = (int)request.getAttribute("dataset:headless-display:itemsPerPage");
String module = (String)request.getAttribute("dataset:headless-display:module");
String namespace = (String)request.getAttribute("dataset:headless-display:namespace");
String nestedItemsKey = (String)request.getAttribute("dataset:headless-display:nestedItemsKey");
String nestedItemsReferenceKey = (String)request.getAttribute("dataset:headless-display:nestedItemsReferenceKey");
int pageNumber = (int)request.getAttribute("dataset:headless-display:pageNumber");
PortletURL portletURL = (PortletURL)request.getAttribute("dataset:headless-display:portletURL");
List<String> selectedItems = (List<String>)request.getAttribute("dataset:headless-display:selectedItems");
String selectedItemsKey = (String)request.getAttribute("dataset:headless-display:selectedItemsKey");
String selectionType = (String)request.getAttribute("dataset:headless-display:selectionType");
boolean showManagementBar = (boolean)request.getAttribute("dataset:headless-display:showManagementBar");
boolean showPagination = (boolean)request.getAttribute("dataset:headless-display:showPagination");
boolean showSearch = (boolean)request.getAttribute("dataset:headless-display:showSearch");
SortItemList sortItemList = (SortItemList)request.getAttribute("dataset:headless-display:sortItemList");
String style = (String)request.getAttribute("dataset:headless-display:style");
%>