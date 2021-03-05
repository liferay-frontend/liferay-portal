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
List<String> senses = (List<String>)request.getAttribute("senses");
%>

<clay:container-fluid>
	<portlet:actionURL name="/braindance/start_simulation" var="startSimulationURL" />

	<aui:form action="<%= startSimulationURL %>" method="post" name="fm">
		<clay:row>
			<clay:col
				size="3"
			>
				<clay:vertical-card
					icon="view"
					inputName='<%= liferayPortletResponse.getNamespace() + "sense" %>'
					inputValue="see"
					propsTransformer="js/CardPropsTransformer"
					selectable="<%= true %>"
					selected='<%= Validator.isNotNull(senses) && senses.contains("see") %>'
					title="see"
				/>
			</clay:col>

			<clay:col
				size="3"
			>
				<clay:vertical-card
					icon="audio"
					inputName='<%= liferayPortletResponse.getNamespace() + "sense" %>'
					inputValue="hear"
					propsTransformer="js/CardPropsTransformer"
					selectable="<%= true %>"
					selected='<%= Validator.isNotNull(senses) && senses.contains("hear") %>'
					title="hear"
				/>
			</clay:col>

			<clay:col
				size="3"
			>
				<clay:vertical-card
					icon="magic"
					inputName='<%= liferayPortletResponse.getNamespace() + "sense" %>'
					inputValue="smell"
					propsTransformer="js/CardPropsTransformer"
					selectable="<%= true %>"
					selected='<%= Validator.isNotNull(senses) && senses.contains("smell") %>'
					title="smell"
				/>
			</clay:col>

			<clay:col
				size="3"
			>
				<clay:vertical-card
					icon="move"
					inputName='<%= liferayPortletResponse.getNamespace() + "sense" %>'
					inputValue="touch"
					propsTransformer="js/CardPropsTransformer"
					selectable="<%= true %>"
					selected='<%= Validator.isNotNull(senses) && senses.contains("touch") %>'
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

	<c:if test="<%= Validator.isNotNull(senses) %>">
		<clay:row>
			<clay:col
				size="12"
			>
				<react:component
					module="js/Braindance"
					props='<%=
						HashMapBuilder.<String, Object>put(
							"activeSenses", senses
						).build()
					%>'
				/>
			</clay:col>
		</clay:row>
	</c:if>
</clay:container-fluid>