<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

--%>

<%@ include file="/init.jsp" %>

<%
String className = GetterUtil.getString((String)request.getAttribute("liferay-ddm:template-selector:className"));
String defaultDisplayStyle = GetterUtil.getString((String)request.getAttribute("liferay-ddm:template-selector:defaultDisplayStyle"), com.liferay.petra.string.StringPool.BLANK);
String displayStyle = GetterUtil.getString((String)request.getAttribute("liferay-ddm:template-selector:displayStyle"));
long displayStyleGroupId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-ddm:template-selector:displayStyleGroupId")));
List<String> displayStyles = (List<String>)request.getAttribute("liferay-ddm:template-selector:displayStyles");
String icon = GetterUtil.getString((String)request.getAttribute("liferay-ddm:template-selector:icon"));
String label = GetterUtil.getString((String)request.getAttribute("liferay-ddm:template-selector:label"), "display-template");
String refreshURL = GetterUtil.getString((String)request.getAttribute("liferay-ddm:template-selector:refreshURL"));
boolean showEmptyOption = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-ddm:template-selector:showEmptyOption")));
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("liferay-ddm:template-selector:dynamicAttributes");
%>

<%@ include file="/template_selector/init-ext.jspf" %>