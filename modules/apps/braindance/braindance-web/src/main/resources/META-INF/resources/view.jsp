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

<clay:container-fluid>
	<clay:row>
		<clay:col
			size="3"
		>
			<clay:vertical-card
				imageSrc='<%= application.getContextPath() + "/images/see.jpg"%>'
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
				imageSrc='<%= application.getContextPath() + "/images/hear.jpg"%>'
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
				imageSrc='<%= application.getContextPath() + "/images/smell.jpg"%>'
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
				imageSrc='<%= application.getContextPath() + "/images/touch.jpg"%>'
				inputName='<%= liferayPortletResponse.getNamespace() + "sense" %>'
				inputValue="touch"
				propsTransformer="js/CardPropsTransformer"
				selectable="<%= true %>"
				title="touch"
			/>
		</clay:col>
	</clay:row>

	<clay:row>
		<clay:col
			cssClass="mt-4"
			size="12"
		>
			<react:component
				module="js/Braindance"
				props='<%=
					HashMapBuilder.<String, Object>put(
						"world", application.getContextPath() + "/images/world.gif"
					).build()
				%>'
			/>
		</clay:col>
	</clay:row>
</clay:container-fluid>