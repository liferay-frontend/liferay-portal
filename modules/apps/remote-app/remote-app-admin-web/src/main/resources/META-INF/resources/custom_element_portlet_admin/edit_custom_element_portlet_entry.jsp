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

<%@ include file="/custom_element_portlet_admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

CustomElementPortletEntry customElementPortletEntry = (CustomElementPortletEntry)request.getAttribute(RemoteAppAdminWebKeys.CUSTOM_ELEMENT_PORTLET_ENTRY);

long customElementPortletEntryId = BeanParamUtil.getLong(customElementPortletEntry, request, "customElementPortletEntryId");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((customElementPortletEntry == null) ? LanguageUtil.get(request, "new-custom-element-portlet") : customElementPortletEntry.getName(locale));
%>

<portlet:actionURL name="/custom_element_portlet_admin/edit_custom_element_portlet_entry" var="editCustomElementPortletEntryURL" />

<clay:container-fluid>
	<aui:form action="<%= editCustomElementPortletEntryURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveCustomElementPortletEntry();" %>'>
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="customElementPortletEntryId" type="hidden" value="<%= customElementPortletEntryId %>" />

		<aui:model-context bean="<%= customElementPortletEntry %>" model="<%= CustomElementPortletEntry.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:field-wrapper label="name">
					<liferay-ui:input-localized
						autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>"
						name="name"
						xml='<%= BeanPropertiesUtil.getString(customElementPortletEntry, "name") %>'
					/>
				</aui:field-wrapper>

				<aui:input label="portlet-display-category" name="portletDisplayCategory" type="text" value='<%= BeanPropertiesUtil.getString(customElementPortletEntry, "portletDisplayCategory", "category.sample") %>' />

				<aui:select label="html-tag-name" name="tagName" showEmptyOption="<%= false %>">

					<%
					for (String customElementTagName : customElementPortletAdminDisplayContext.getCustomElementTagNames()) {
					%>

						<aui:option label="<%= customElementTagName %>" selected='<%= customElementTagName.equals(BeanPropertiesUtil.getString(customElementPortletEntry, "tagName")) %>' value="<%= customElementTagName %>" />

					<%
					}
					%>

				</aui:select>

				<aui:input helpMessage="tag-attributes-help" label="tag-attributes" name="tagAttributes" type="textarea" />
				<aui:input helpMessage="css-urls-help" label="css-urls" name="cssURLs" type="textarea" />
			</aui:fieldset>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button type="submit" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</clay:container-fluid>

<aui:script>
	function <portlet:namespace />saveCustomElementPortletEntry() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value =
			'<%= (customElementPortletEntry == null) ? Constants.ADD : Constants.UPDATE %>';

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>