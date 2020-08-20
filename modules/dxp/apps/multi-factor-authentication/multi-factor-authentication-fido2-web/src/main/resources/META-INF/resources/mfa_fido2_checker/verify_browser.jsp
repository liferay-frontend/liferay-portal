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
%>

<div id="<portlet:namespace/>messageContainer"></div>

<aui:button id="startAuthentication" value="start" />

<aui:input name="responseJSON" showRequiredLabel="yes" type="hidden" />

<aui:button-row>
	<aui:button type="submit" value="submit" />
</aui:button-row>

<aui:script use="aui-base">
	Liferay.Loader.require(
		'<%=npmResolvedPackageName%>/js/yubico-webauthn/webauthn',
		function (webauthn) {
			A.one('#<portlet:namespace />startAuthentication').on(
				'click',
				function (event) {
					var assertionRequest = JSON.parse('<%=assertionReqest %>');
					webauthn
						.getAssertion(
							assertionRequest.publicKeyCredentialRequestOptions
						)
						.then(
							function (value) {
								var responseJSONInput = A.one(
									'#<portlet:namespace />responseJSON'
								);

								var publicKeyCredentialObject = webauthn.responseToObject(
									value
								);

								if (
									publicKeyCredentialObject.response.userHandle !=
										null &&
									publicKeyCredentialObject.response
										.userHandle === ''
								) {
									delete publicKeyCredentialObject.response
										.userHandle;
								}

								responseJSONInput._node.value = JSON.stringify(
									publicKeyCredentialObject
								);
							},
							function (reason) {
								var messageContainer = A.one(
									'#<portlet:namespace />messageContainer'
								);
								messageContainer.html(
									'<span class="alert alert-danger"><liferay-ui:message key="your-authenticator-was-unable-to-verify-your-credential" /></span>'
								);
							}
						);
				}
			);
		}
	);
</aui:script>