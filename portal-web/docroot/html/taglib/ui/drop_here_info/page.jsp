<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/ui/drop_here_info/init.jsp" %>

<div class="drop-here-info">
	<div class="drop-here-indicator">
		<div class="drop-icons">
			<aui:icon cssClass="drop-icon" image="picture" markupView="lexicon" />

			<aui:icon cssClass="drop-icon" image="picture" markupView="lexicon" />

			<aui:icon cssClass="drop-icon" image="picture" markupView="lexicon" />
		</div>

		<div class="drop-text">
			<liferay-ui:message key='<%= GetterUtil.getString((String)request.getAttribute("liferay-ui:drop-here-info:message")) %>' />
		</div>
	</div>
</div>