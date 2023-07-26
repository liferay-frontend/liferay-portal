<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceDiscountRuleDisplayContext commerceDiscountRuleDisplayContext = (CommerceDiscountRuleDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceDiscountRule commerceDiscountRule = commerceDiscountRuleDisplayContext.getCommerceDiscountRule();

String type = BeanParamUtil.getString(commerceDiscountRule, request, "type");
%>

<portlet:actionURL name="/commerce_discount/edit_commerce_discount_rule" var="editCommerceDiscountRuleActionURL" />

<liferay-frontend:side-panel-content
	title='<%= LanguageUtil.get(request, "edit-rule") %>'
>
	<aui:form action="<%= editCommerceDiscountRuleActionURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "apiSubmit();" %>'>
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceDiscountRule == null) ? Constants.ADD : Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commerceDiscountId" type="hidden" value="<%= commerceDiscountRule.getCommerceDiscountId() %>" />
		<aui:input name="commerceDiscountRuleId" type="hidden" value="<%= commerceDiscountRule.getCommerceDiscountRuleId() %>" />

		<aui:model-context bean="<%= commerceDiscountRule %>" model="<%= CommerceDiscountRule.class %>" />

		<div class="row">
			<div class="col-12">
				<commerce-ui:panel
					bodyClasses="flex-fill"
					title='<%= LanguageUtil.get(request, "details") %>'
				>
					<aui:input name="name" required="<%= true %>" />

					<aui:select disabled="<%= true %>" name="type" required="<%= true %>">

						<%
						for (CommerceDiscountRuleType commerceDiscountRuleType : commerceDiscountRuleDisplayContext.getCommerceDiscountRuleTypes()) {
							String key = commerceDiscountRuleType.getKey();
						%>

							<aui:option label="<%= commerceDiscountRuleType.getLabel(locale) %>" selected="<%= key.equals(commerceDiscountRule.getType()) %>" value="<%= key %>" />

						<%
						}
						%>

					</aui:select>
				</commerce-ui:panel>
			</div>
		</div>

		<div class="row">

			<%
			CommerceDiscountRuleTypeJSPContributor commerceDiscountRuleTypeJSPContributor = commerceDiscountRuleDisplayContext.getCommerceDiscountRuleTypeJSPContributor(type);
			%>

			<c:if test="<%= commerceDiscountRuleTypeJSPContributor != null %>">

				<%
				commerceDiscountRuleTypeJSPContributor.render(commerceDiscountRule.getCommerceDiscountId(), commerceDiscountRule.getCommerceDiscountRuleId(), request, PipingServletResponseFactory.createPipingServletResponse(pageContext));
				%>

			</c:if>
		</div>

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" value="save" />

			<aui:button cssClass="btn-lg" type="cancel" />
		</aui:button-row>
	</aui:form>
</liferay-frontend:side-panel-content>

<aui:script require="commerce-frontend-js/utilities/notifications as NotificationUtils, commerce-frontend-js/utilities/slugify as slugify, commerce-frontend-js/utilities/eventsDefinitions as events, commerce-frontend-js/ServiceProvider/index as ServiceProvider">
	var CommerceDiscountRuleResource = ServiceProvider.default.AdminPricingAPI(
		'v2'
	);

	Liferay.provide(
		window,
		'<portlet:namespace />apiSubmit',
		() => {
			var form = document.getElementById('<portlet:namespace />fm');
			var name = form.querySelector('#<portlet:namespace />name').value;

			var typeSettings = form.querySelector(
				'#<portlet:namespace />typeSettings'
			).value;

			var discountRuleData = {
				name: name,
				type: '<%= commerceDiscountRule.getType() %>',
				typeSettings: typeSettings,
			};

			return CommerceDiscountRuleResource.updateDiscountRule(
				'<%= commerceDiscountRule.getCommerceDiscountRuleId() %>',
				discountRuleData
			)
				.then(() => {
					NotificationUtils.showNotification(
						'<liferay-ui:message key="your-request-completed-successfully" />'
					);

					window.parent.Liferay.fire(events.FDS_UPDATE_DISPLAY, {
						id: '<%= CommercePricingFDSNames.DISCOUNT_RULES %>',
					});

					return;
				})
				.catch(() => {
					Liferay.Util.openAlertModal({
						message:
							'<liferay-ui:message key="your-request-failed-to-complete" />',
					});

					return;
				});
		},
		['liferay-portlet-url']
	);
</aui:script>