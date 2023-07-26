<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/portal/init.jsp" %>

<%
if (themeDisplay.isStatePopUp() || themeDisplay.isWidget() || layoutTypePortlet.hasStateMax()) {
	String ppid = ParamUtil.getString(request, "p_p_id");

	String templateId = null;
	String templateContent = null;
	String langType = null;

	if (themeDisplay.isStatePopUp() || themeDisplay.isWidget()) {
		templateId = theme.getThemeId() + LayoutTemplateConstants.STANDARD_SEPARATOR + "pop_up";
		templateContent = LayoutTemplateLocalServiceUtil.getContent("pop_up", true, theme.getThemeId());
		langType = LayoutTemplateLocalServiceUtil.getLangType("pop_up", true, theme.getThemeId());
	}
	else {
		ppid = StringUtil.split(layoutTypePortlet.getStateMax())[0];

		templateId = theme.getThemeId() + LayoutTemplateConstants.STANDARD_SEPARATOR + "max";
		templateContent = LayoutTemplateLocalServiceUtil.getContent("max", true, theme.getThemeId());
		langType = LayoutTemplateLocalServiceUtil.getLangType("max", true, theme.getThemeId());
	}

	if (Validator.isNotNull(templateContent)) {
		RuntimePageUtil.processTemplate(request, response, ppid, templateId, templateContent, langType);
	}
}
else {
	String themeId = theme.getThemeId();

	String layoutTemplateId = layoutTypePortlet.getLayoutTemplateId();

	if (Validator.isNull(layoutTemplateId)) {
		layoutTemplateId = PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID;
	}

	LayoutTemplate layoutTemplate = LayoutTemplateLocalServiceUtil.getLayoutTemplate(layoutTemplateId, false, theme.getThemeId());

	if (layoutTemplate != null) {
		themeId = layoutTemplate.getThemeId();
	}

	String templateId = themeId + LayoutTemplateConstants.CUSTOM_SEPARATOR + layoutTypePortlet.getLayoutTemplateId();
	String templateContent = LayoutTemplateLocalServiceUtil.getContent(layoutTypePortlet.getLayoutTemplateId(), false, theme.getThemeId());

	if (Validator.isNotNull(templateContent)) {
		RuntimePageUtil.processTemplate(request, response, null, templateId, templateContent, LayoutTemplateLocalServiceUtil.getLangType(layoutTypePortlet.getLayoutTemplateId(), false, theme.getThemeId()));
	}
}
%>

<liferay-ui:layout-common />