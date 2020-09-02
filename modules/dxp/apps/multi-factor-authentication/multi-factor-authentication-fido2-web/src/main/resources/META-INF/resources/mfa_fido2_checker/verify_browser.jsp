<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
String assertionReqest = (String)request.getAttribute("assertionReqest");

Map<String, Object> additionalProps = HashMapBuilder.<String, Object>put(
	"assertionReqest", assertionReqest
).build();
%>

<div id="<portlet:namespace/>messageContainer"></div>

<clay:button
	additionalProps="<%= additionalProps %>"
	displayType="secondary"
	id='<%= liferayPortletResponse.getNamespace() + "startAuthentication" %>'
	label="start"
	propsTransformer="js/AuthenticationTransformer"
/>

<aui:input name="responseJSON" showRequiredLabel="yes" type="hidden" />

<aui:button-row>
	<aui:button type="submit" value="submit" />
</aui:button-row>