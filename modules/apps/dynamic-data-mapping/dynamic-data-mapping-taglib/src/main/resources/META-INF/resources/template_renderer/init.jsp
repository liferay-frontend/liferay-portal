<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

--%>

<%@ include file="/init.jsp" %>

<%
String className = GetterUtil.getString((String)request.getAttribute("liferay-ddm:template-renderer:className"));
Map<String, Object> contextObjects = (Map<String, Object>)request.getAttribute("liferay-ddm:template-renderer:contextObjects");
String displayStyle = GetterUtil.getString((String)request.getAttribute("liferay-ddm:template-renderer:displayStyle"));
long displayStyleGroupId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-ddm:template-renderer:displayStyleGroupId")));
List<?> entries = (List<?>)request.getAttribute("liferay-ddm:template-renderer:entries");
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("liferay-ddm:template-renderer:dynamicAttributes");
%>

<%@ include file="/template_renderer/init-ext.jspf" %>