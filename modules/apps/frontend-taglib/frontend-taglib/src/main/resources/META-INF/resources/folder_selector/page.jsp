<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/folder_selector/init.jsp" %>

<%
boolean folderInTrash = (boolean)request.getAttribute("liferay-frontend:folder-selector:folderInTrash");
String folderName = (String)request.getAttribute("liferay-frontend:folder-selector:folderName");
boolean folderNotFound = (boolean)request.getAttribute("liferay-frontend:folder-selector:folderNotFound");
long folderValue = (long)request.getAttribute("liferay-frontend:folder-selector:folderValue");
String label = (String)request.getAttribute("liferay-frontend:folder-selector:label");
String selectEventName = (String)request.getAttribute("liferay-frontend:folder-selector:selectEventName");
String selectFolderURL = (String)request.getAttribute("liferay-frontend:folder-selector:selectFolderURL");
boolean showRemoveButton = (boolean)request.getAttribute("liferay-frontend:folder-selector:showRemoveButton");
%>

<div>
	<react:component
		module="folder_selector/FolderSelector"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"folderInTrash", folderInTrash
			).put(
				"folderName", folderName
			).put(
				"folderNotFound", folderNotFound
			).put(
				"folderValue", folderValue
			).put(
				"label", label
			).put(
				"selectEventName", selectEventName
			).put(
				"selectFolderURL", selectFolderURL
			).put(
				"showRemoveButton", showRemoveButton
			).build()
		%>'
	/>
</div>