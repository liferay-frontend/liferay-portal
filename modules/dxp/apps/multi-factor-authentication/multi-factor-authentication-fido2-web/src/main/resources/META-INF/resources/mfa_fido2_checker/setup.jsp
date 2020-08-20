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
String pkccOptions = (String)request.getAttribute("pkccOptions");
%>

<div id="<portlet:namespace/>messageContainer"></div>

<aui:button id="startRegistration" value="start" />

<aui:input name="responseJSON" showRequiredLabel="yes" type="hidden" />

<aui:button-row>
	<aui:button type="submit" value="submit" />
</aui:button-row>

<aui:script use="aui-base">
	Liferay.Loader.require(
		'<%=npmResolvedPackageName%>/js/yubico-webauthn/webauthn',
		function (webauthn) {
			A.one('#<portlet:namespace />startRegistration').on('click', function (
				event
			) {
				webauthn.createCredential(JSON.parse('<%=pkccOptions %>')).then(
					function (value) {
						var responseJSONInput = A.one(
							'#<portlet:namespace />responseJSON'
						);

						var publicKeyCredentialObject = webauthn.responseToObject(
							value
						);

						responseJSONInput._node.value = JSON.stringify(
							publicKeyCredentialObject
						);
					},
					function (reason) {
						var messageContainer = A.one(
							'#<portlet:namespace />messageContainer'
						);
						messageContainer.html(
							'<span class="alert alert-danger"><liferay-ui:message key="your-authenticator-was-unable-to-create-a-credential" /></span>'
						);
					}
				);
			});
		}
	);
</aui:script>