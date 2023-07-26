<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/portal/layout/edit/init.jsp" %>

<%
LayoutTypePortlet selLayoutTypePortlet = null;

Theme selTheme = layout.getTheme();

if (selLayout != null) {
	selLayoutTypePortlet = (LayoutTypePortlet)selLayout.getLayoutType();

	selTheme = selLayout.getTheme();
}
%>

<liferay-ui:layout-templates-list
	layoutTemplateId="<%= (selLayoutTypePortlet != null) ? selLayoutTypePortlet.getLayoutTemplateId() : StringPool.BLANK %>"
	layoutTemplates="<%= LayoutTemplateLocalServiceUtil.getLayoutTemplates(selTheme.getThemeId()) %>"
/>