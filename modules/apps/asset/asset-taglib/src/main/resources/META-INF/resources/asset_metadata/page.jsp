<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/asset_metadata/init.jsp" %>

<%
AssetEntry assetEntry = (AssetEntry)request.getAttribute("liferay-asset:asset-metadata:assetEntry");
String[] metadataFields = (String[])request.getAttribute("liferay-asset:asset-metadata:metadataFields");
%>

<dl class="taglib-asset-metadata">
	<c:choose>
		<c:when test="<%= metadataFields.length == 1 %>">

			<%
			request.setAttribute("liferay-asset:asset-metadata:metadataField", metadataFields[0]);
			%>

			<liferay-util:include page="/asset_metadata/metadata_entry.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:otherwise>
			<c:if test='<%= ArrayUtil.contains(metadataFields, String.valueOf("author")) %>'>

				<%
				request.setAttribute("liferay-asset:asset-metadata:metadataField", "author");

				metadataFields = ArrayUtil.remove(metadataFields, String.valueOf("author"));
				%>

				<liferay-util:include page="/asset_metadata/metadata_entry.jsp" servletContext="<%= application %>" />
			</c:if>

			<liferay-util:buffer
				var="metadataPanelContent"
			>

				<%
				for (String metadataField : metadataFields) {
					request.setAttribute("liferay-asset:asset-metadata:metadataField", metadataField);
				%>

					<liferay-util:include page="/asset_metadata/metadata_entry.jsp" servletContext="<%= application %>" />

				<%
				}
				%>

			</liferay-util:buffer>

			<c:if test="<%= Validator.isNotNull(metadataPanelContent) %>">
				<liferay-ui:panel
					collapsible="<%= true %>"
					cssClass="asset-metadata-panel mb-0"
					defaultState="closed"
					extended="<%= false %>"
					id='<%= "metadataPanel" + assetEntry.getEntryId() %>'
					persistState="<%= false %>"
					title="more-details"
				>
					<%= metadataPanelContent %>
				</liferay-ui:panel>
			</c:if>
		</c:otherwise>
	</c:choose>
</dl>