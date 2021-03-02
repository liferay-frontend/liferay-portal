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
String[] senses = (String[])request.getAttribute("senses");
%>

<clay:container-fluid>
	<portlet:actionURL name="/braindance/start_simulation" var="startSimulationURL" />

	<aui:form action="<%= startSimulationURL %>" method="post" name="fm">
		<clay:row>
			<clay:col
				size="3"
			>
				<clay:vertical-card
					icon="page"
					inputName='<%= liferayPortletResponse.getNamespace() + "sense" %>'
					inputValue="see"
					propsTransformer="js/CardPropsTransformer"
					selectable="<%= true %>"
					title="see"
				/>
			</clay:col>

			<clay:col
				size="3"
			>
				<clay:vertical-card
					icon="page"
					inputName='<%= liferayPortletResponse.getNamespace() + "sense" %>'
					inputValue="hear"
					propsTransformer="js/CardPropsTransformer"
					selectable="<%= true %>"
					title="hear"
				/>
			</clay:col>

			<clay:col
				size="3"
			>
				<clay:vertical-card
					icon="page"
					inputName='<%= liferayPortletResponse.getNamespace() + "sense" %>'
					inputValue="smell"
					propsTransformer="js/CardPropsTransformer"
					selectable="<%= true %>"
					title="smell"
				/>
			</clay:col>

			<clay:col
				size="3"
			>
				<clay:vertical-card
					icon="page"
					inputName='<%= liferayPortletResponse.getNamespace() + "sense" %>'
					inputValue="touch"
					propsTransformer="js/CardPropsTransformer"
					selectable="<%= true %>"
					title="touch"
				/>
			</clay:col>
		</clay:row>

		<clay:row>
			<clay:button
				displayType="primary"
				label='<%= LanguageUtil.get(request, "start-simulation") %>'
				small="<%= false %>"
				type="submit"
			/>
		</clay:row>
	</aui:form>

	<clay:row>
		<clay:col
			size="12"
		>
			<react:component
				module="js/Braindance"
				props='<%=
					HashMapBuilder.<String, Object>put(
						"senses", senses
					).build()
				%>'
			/>
		</clay:col>
	</clay:row>
</clay:container-fluid>