<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/publications/init.jsp" %>

<%
ViewDiscardDisplayContext viewDiscardDisplayContext = (ViewDiscardDisplayContext)request.getAttribute(CTWebKeys.VIEW_DISCARD_DISPLAY_CONTEXT);

portletDisplay.setURLBack(viewDiscardDisplayContext.getRedirectURL());

portletDisplay.setShowBackIcon(true);

renderResponse.setTitle(LanguageUtil.get(request, "discard-changes"));
%>

<clay:container-fluid
	cssClass="publications-discard-container"
>
	<div class="sheet">
		<clay:sheet-section>
			<h2 class="sheet-title"><liferay-ui:message key="discarded-changes" /></h2>

			<div class="sheet-text">
				<liferay-ui:message key="the-following-changes-will-be-discarded" />
			</div>

			<div>
				<react:component
					data="<%= viewDiscardDisplayContext.getReactData() %>"
					module="publications/js/views/ChangeTrackingDiscardView"
				/>
			</div>
		</clay:sheet-section>

		<clay:sheet-footer>
			<aui:button href="<%= viewDiscardDisplayContext.getSubmitURL() %>" primary="true" value="discard" />

			<aui:button href="<%= viewDiscardDisplayContext.getRedirectURL() %>" type="cancel" />
		</clay:sheet-footer>
	</div>
</clay:container-fluid>