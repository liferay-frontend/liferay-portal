<%@ page import="java.util.Enumeration" %><%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/theme/portlet_messages/init.jsp" %>

<%
Group group = (Group)request.getAttribute("liferay-theme:portlet-messages:group");
Portlet portlet = (Portlet)request.getAttribute("liferay-theme:portlet-messages:portlet");
%>

<c:if test="<%= !layout.isTypeControlPanel() %>">

	<%
	Group liveGroup = group;

	boolean inStaging = false;

	if (group.isControlPanel()) {
		long doAsGroupId = ParamUtil.getLong(request, "doAsGroupId");

		if (doAsGroupId > 0) {
			try {
				liveGroup = GroupLocalServiceUtil.getGroup(doAsGroupId);

				if (liveGroup.isStagingGroup()) {
					liveGroup = liveGroup.getLiveGroup();

					inStaging = true;
				}
			}
			catch (Exception e) {
			}
		}
	}
	else if (group.isStagingGroup()) {
		liveGroup = group.getLiveGroup();

		inStaging = true;
	}
	%>

	<c:if test="<%= liveGroup.isStaged() && !liveGroup.isStagedPortlet(portlet.getRootPortletId()) %>">
		<c:choose>
			<c:when test="<%= !liveGroup.isStagedRemotely() && inStaging %>">
				<div class="alert alert-warning lfr-portlet-message-staging-alert">
					<liferay-ui:message key="this-portlet-is-not-staged-local-alert" />
				</div>
			</c:when>
			<c:when test="<%= liveGroup.isStagedRemotely() && themeDisplay.isSignedIn() %>">
				<div class="alert alert-warning lfr-portlet-message-staging-alert">
					<liferay-ui:message key="this-portlet-is-not-staged-remote-alert" />
				</div>
			</c:when>
		</c:choose>
	</c:if>
</c:if>

<%
	System.err.println("renderRequest: " + renderRequest);
	if (renderRequest == null) {
		System.err.println("<<<< RENDER REQUEST IS NULL !!!!");

		System.err.println("request = " + request);
		System.err.println("requestURL = " + request.getRequestURL());
		System.err.println("getRequestURI = " + request.getRequestURI());
		System.err.println("getQueryString = " + request.getQueryString());

		HttpServletRequest originalServletRequest =
			PortalUtil.getOriginalServletRequest(request);

		System.err.println("original.request = " + originalServletRequest);
		System.err.println("original.requestURL = " + originalServletRequest.getRequestURL());
		System.err.println("original.getRequestURI = " + originalServletRequest.getRequestURI());
		System.err.println("original.getQueryString = " + originalServletRequest.getQueryString());


		System.err.println("request attributes");
		Enumeration<String> attributeNames = request.getAttributeNames();
		while(attributeNames.hasMoreElements()) {
			String attributeName = attributeNames.nextElement();

			System.err.println(attributeName + " = " + request.getAttribute(attributeName));
		}

		System.err.println("stack trace");
		StackTraceElement[] stackTraceElements = new Exception("stack trace exception").getStackTrace();
		for (StackTraceElement stackTraceElement : stackTraceElements) {
			System.err.println(stackTraceElement.toString());
		}

		System.err.println(">>>>");
	}
%>

<c:if test='<%= MultiSessionMessages.contains(renderRequest, "requestProcessed") && !MultiSessionMessages.contains(renderRequest, portlet.getPortletId() + SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE) %>'>

	<%
	String successMessage = (String)MultiSessionMessages.get(renderRequest, "requestProcessed");
	%>

	<liferay-util:buffer
		var="successHTML"
	>
		<c:choose>
			<c:when test='<%= Validator.isNotNull(successMessage) && !successMessage.equals("request_processed") %>'>
				<%= HtmlUtil.escape(successMessage) %>
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="your-request-completed-successfully" />
			</c:otherwise>
		</c:choose>

		<c:if test="<%= themeDisplay.isStatePopUp() && MultiSessionMessages.contains(renderRequest, portlet.getPortletId() + SessionMessages.KEY_SUFFIX_CLOSE_REDIRECT) %>">

			<%
			String taglibMessage = "class=\"lfr-hide-dialog\" href=\"javascript:void(0);\"";
			%>

			<liferay-ui:message arguments="<%= taglibMessage %>" key="the-page-will-be-refreshed-when-you-close-this-dialog.alternatively-you-can-hide-this-dialog-x" translateArguments="<%= false %>" />
		</c:if>
	</liferay-util:buffer>

	<liferay-ui:success key="requestProcessed" message="<%= successHTML %>" />
</c:if>

<liferay-ui:success key="<%= portlet.getPortletId() + SessionMessages.KEY_SUFFIX_UPDATED_CONFIGURATION %>" message="you-have-successfully-updated-the-setup" />
<liferay-ui:success key="<%= portlet.getPortletId() + SessionMessages.KEY_SUFFIX_UPDATED_PREFERENCES %>" message="you-have-successfully-updated-your-preferences" />

<c:if test="<%= !MultiSessionErrors.isHideDefaultErrorMessage(renderRequest, portlet.getPortletId()) %>">
	<liferay-ui:error embed="<%= false %>" />
</c:if>