<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/definition_link/init.jsp" %>

<%
WorkflowDefinitionLinkDisplayTerms displayTerms = new WorkflowDefinitionLinkDisplayTerms(renderRequest);
%>

<liferay-ui:search-toggle
	buttonLabel="search"
	displayTerms="<%= displayTerms %>"
	id="toggle_id_workflow_definition_link_search"
	markupView="lexicon"
>
	<aui:fieldset>
		<aui:input inlineField="<%= true %>" name="<%= WorkflowDefinitionLinkDisplayTerms.RESOURCE %>" size="20" type="text" value="<%= displayTerms.getResource() %>" />

		<aui:input inlineField="<%= true %>" name="<%= WorkflowDefinitionLinkDisplayTerms.WORKFLOW %>" size="20" type="text" value="<%= displayTerms.getWorkflow() %>" />
	</aui:fieldset>
</liferay-ui:search-toggle>