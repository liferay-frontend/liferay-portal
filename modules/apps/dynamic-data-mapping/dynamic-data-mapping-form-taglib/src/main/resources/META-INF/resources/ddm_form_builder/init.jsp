<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

--%>

<%@ include file="/init.jsp" %>

<%
Long ddmStructureId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-form:ddm-form-builder:ddmStructureId")));
Long ddmStructureVersionId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-form:ddm-form-builder:ddmStructureVersionId")));
String defaultLanguageId = GetterUtil.getString((String)request.getAttribute("liferay-form:ddm-form-builder:defaultLanguageId"));
String editingLanguageId = GetterUtil.getString((String)request.getAttribute("liferay-form:ddm-form-builder:editingLanguageId"));
long fieldSetClassNameId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-form:ddm-form-builder:fieldSetClassNameId")));
String refererPortletNamespace = GetterUtil.getString((String)request.getAttribute("liferay-form:ddm-form-builder:refererPortletNamespace"));
boolean showPagination = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-form:ddm-form-builder:showPagination")), true);
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("liferay-form:ddm-form-builder:dynamicAttributes");
%>

<%@ include file="/ddm_form_builder/init-ext.jspf" %>