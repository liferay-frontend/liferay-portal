<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

--%>

<%@ include file="/init.jsp" %>

<%
String containerId = GetterUtil.getString((String)request.getAttribute("liferay-data-engine:data-layout-renderer:containerId"));
String contentType = GetterUtil.getString((String)request.getAttribute("liferay-data-engine:data-layout-renderer:contentType"));
Long dataDefinitionId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-data-engine:data-layout-renderer:dataDefinitionId")));
Long dataLayoutId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-data-engine:data-layout-renderer:dataLayoutId")));
Long dataRecordId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-data-engine:data-layout-renderer:dataRecordId")));
Map<String, Object> dataRecordValues = (Map<String, Object>)request.getAttribute("liferay-data-engine:data-layout-renderer:dataRecordValues");
String defaultLanguageId = GetterUtil.getString((String)request.getAttribute("liferay-data-engine:data-layout-renderer:defaultLanguageId"));
boolean disableFieldRepetition = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-data-engine:data-layout-builder:disableFieldRepetition")));
String displayType = GetterUtil.getString((String)request.getAttribute("liferay-data-engine:data-layout-renderer:displayType"));
String languageId = GetterUtil.getString((String)request.getAttribute("liferay-data-engine:data-layout-renderer:languageId"));
String namespace = GetterUtil.getString((String)request.getAttribute("liferay-data-engine:data-layout-renderer:namespace"));
boolean persistDefaultValues = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-data-engine:data-layout-renderer:persistDefaultValues")));
boolean persisted = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-data-engine:data-layout-renderer:persisted")));
boolean readOnly = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-data-engine:data-layout-renderer:readOnly")));
boolean submittable = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-data-engine:data-layout-renderer:submittable")), true);
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("liferay-data-engine:data-layout-renderer:dynamicAttributes");
%>

<%@ include file="/data_layout_renderer/init-ext.jspf" %>