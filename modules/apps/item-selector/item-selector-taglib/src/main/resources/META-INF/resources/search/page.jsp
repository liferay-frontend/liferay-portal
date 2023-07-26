<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/search/init.jsp" %>

<%
PortletURL searchURL = PortletURLBuilder.create(
	PortletURLUtil.clone(currentURLObj, liferayPortletResponse)
).setParameter(
	"resetCur", true
).buildPortletURL();
%>

<aui:form action='<%= HttpComponentsUtil.removeParameter(searchURL.toString(), liferayPortletResponse.getNamespace() + "keywords") %>' name="searchFm">
	<liferay-ui:input-search
		markupView="lexicon"
	/>
</aui:form>