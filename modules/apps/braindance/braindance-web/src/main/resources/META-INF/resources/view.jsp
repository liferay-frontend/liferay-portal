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
			<clay:navigation-card
				data-feature='<%= LanguageUtil.get(request, "see") %>'
				icon="page"
				propsTransformer="js/CardPropsTransformer"
				title="see"
			/>
		</clay:col>

		<clay:col
			size="3"
		>
			<clay:navigation-card
				data-feature='<%= LanguageUtil.get(request, "hear") %>'
				icon="page"
				propsTransformer="js/CardPropsTransformer"
				title="hear"
			/>
		</clay:col>

		<clay:col
			size="3"
		>
			<clay:navigation-card
				data-feature='<%= LanguageUtil.get(request, "smell") %>'
				icon="page"
				propsTransformer="js/CardPropsTransformer"
				title="smell"
			/>
		</clay:col>

		<clay:col
			size="3"
		>
			<clay:navigation-card
				data-feature='<%= LanguageUtil.get(request, "touch") %>'
				icon="page"
				propsTransformer="js/CardPropsTransformer"
				title="touch"
			/>
		</clay:col>
	</clay:row>

	<clay:row>
		<clay:col
			size="12"
		>
			<react:component
				module="js/Braindance"
			/>
		</clay:col>
	</clay:row>
</clay:container-fluid>