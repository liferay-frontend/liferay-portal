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

<%@ include file="/init.jsp" %>

<%
String iconsMapJson = (String)request.getAttribute("icons");

String redirect = ParamUtil.getString(request, "redirect");

PortletURL portletURL = renderResponse.createRenderURL();

if (Validator.isNull(redirect)) {
	redirect = portletURL.toString();
}
%>

<portlet:actionURL name="/frontend_icons_admin/save_custom_icon" var="saveCustomIcon" />

<clay:sheet>
	<clay:content-row
		containerElement="h2"
		cssClass="mb-5"
	>
		<clay:content-col
			containerElement="span"
			expand="<%= true %>"
		>
			<liferay-ui:message key="icons-admin-configuration-name" />
		</clay:content-col>
	</clay:content-row>

	<aui:form action="<%= saveCustomIcon %>" data-senna-off="true" enctype="multipart/form-data" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<div>
			<react:component
				module="js/IconSearch"
				props='<%=
					HashMapBuilder.<String, Object>put(
						"icons", iconsMapJson
					).build()
				%>'
			/>
		</div>

		<clay:sheet-footer>
			<div class="btn-group mt-4">
				<div class="btn-group-item">
					<aui:button type="submit" value="save" />
				</div>

				<div class="btn-group-item">
					<aui:button href="<%= redirect %>" name="cancel" type="cancel" />
				</div>
			</div>
		</clay:sheet-footer>
	</aui:form>
</clay:sheet>