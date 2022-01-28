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

<liferay-ui:tabs
	names="Balloon Editor Tag,Alloy Editor Tag,Input Editor Tag,CKEditor Classic Editor Tag,CKEditor BBCode Tag,Editor React Component"
	refresh="<%= false %>"
>

	<%
	String[] sections = {"ballooneditor", "alloyeditor", "inputeditor", "ckeditor_classic", "ckeditor_bbcode", "ckeditor_editor_react_component"};

	for (int i = 0; i < sections.length; i++) {
	%>

		<liferay-ui:section>
			<clay:container-fluid>
				<liferay-util:include page='<%= "/partials/" + sections[i] + ".jsp" %>' servletContext="<%= application %>" />
			</clay:container-fluid>
		</liferay-ui:section>

	<%
	}
	%>

</liferay-ui:tabs>