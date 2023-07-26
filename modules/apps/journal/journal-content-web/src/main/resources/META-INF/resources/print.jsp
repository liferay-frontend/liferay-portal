<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
JournalArticleDisplay articleDisplay = (JournalArticleDisplay)request.getAttribute(WebKeys.JOURNAL_ARTICLE_DISPLAY);

String viewMode = ParamUtil.getString(request, "viewMode");
%>

<c:choose>
	<c:when test="<%= viewMode.equals(Constants.PRINT) %>">
		<aui:script>
			print();
		</aui:script>
	</c:when>
	<c:otherwise>
		<portlet:renderURL var="printPageURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="groupId" value="<%= String.valueOf(articleDisplay.getGroupId()) %>" />
			<portlet:param name="articleId" value="<%= articleDisplay.getArticleId() %>" />
			<portlet:param name="page" value="<%= String.valueOf(articleDisplay.getCurrentPage()) %>" />
			<portlet:param name="viewMode" value="<%= Constants.PRINT %>" />
		</portlet:renderURL>

		<clay:content-col
			cssClass="p-1 print-action user-tool-asset-addon-entry"
		>

			<%
			String title = LanguageUtil.format(request, "print-x", HtmlUtil.escape(articleDisplay.getTitle()));
			%>

			<clay:button
				aria-label="<%= title %>"
				borderless="<%= true %>"
				displayType="secondary"
				icon="print"
				onClick='<%= "javascript:" + liferayPortletResponse.getNamespace() + "printPage();" %>'
				small="<%= true %>"
				title="<%= title %>"
				type="button"
			/>
		</clay:content-col>

		<aui:script>
			function <portlet:namespace />printPage() {
				window.open(
					'<%= printPageURL %>',
					'',
					'directories=0,height=480,left=80,location=1,menubar=1,resizable=1,scrollbars=yes,status=0,toolbar=0,top=180,width=640'
				);
			}
		</aui:script>
	</c:otherwise>
</c:choose>