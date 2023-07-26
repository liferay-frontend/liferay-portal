<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/search_input/init.jsp" %>

<%
PortletURL actionURL = (PortletURL)request.getAttribute("liferay-commerce:search-input:actionURL");
Map<String, Object> data = (Map<String, Object>)request.getAttribute("liferay-commerce:search-input:data");
%>

<aui:form action="<%= String.valueOf(actionURL) %>" method="get" name='<%= (String)request.getAttribute("liferay-commerce:search-input:formName") %>'>

	<%
	for (Map.Entry<String, Object> entry : data.entrySet()) {
	%>

		<aui:input name="<%= HtmlUtil.escape(entry.getKey()) %>" type="hidden" useNamespace="<%= false %>" value="<%= entry.getValue() %>" />

	<%
	}
	%>

	<liferay-ui:input-search
		markupView="lexicon"
	/>
</aui:form>