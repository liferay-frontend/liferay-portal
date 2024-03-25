<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/mini_cart/init.jsp" %>

<c:choose>
	<c:when test="<%= commerceChannelId == 0 %>">
		<div class="alert alert-info mx-auto">
			<liferay-ui:message key="this-site-does-not-have-a-channel" />
		</div>
	</c:when>
	<c:otherwise>
		<div class="cart-root" id="<%= miniCartId %>"></div>

		<%
		HashMapBuilder.HashMapWrapper<String, Object> cartViewsHashMapWrapper = new HashMapBuilder.HashMapWrapper<>();
		HashMapBuilder.HashMapWrapper<String, Object> labelsHashMapWrapper = new HashMapBuilder.HashMapWrapper<>();

		if (!cartViews.isEmpty()) {
			for (Map.Entry<String, String> cartView : cartViews.entrySet()) {
				cartViewsHashMapWrapper.put(cartView.getKey(), cartView.getValue());
			}
		}

		if (!labels.isEmpty()) {
			for (Map.Entry<String, String> label : labels.entrySet()) {
				labelsHashMapWrapper.put(label.getKey(), label.getValue());
			}
		}
		%>

		<liferay-frontend:component
			context='<%=
				HashMapBuilder.<String, Object>put(
					"accountId", accountEntryId
				).put(
					"cartViews", cartViewsHashMapWrapper.build()
				).put(
					"checkoutURL", HtmlUtil.escapeJS(checkoutURL)
				).put(
					"currencyCode", commerceCurrencyCode
				).put(
					"displayDiscountLevels", displayDiscountLevels
				).put(
					"displayTotalItemsQuantity", displayTotalItemsQuantity
				).put(
					"groupId", commerceChannelGroupId
				).put(
					"id", commerceChannelId
				).put(
					"itemsQuantity", itemsQuantity
				).put(
					"labels", labelsHashMapWrapper.build()
				).put(
					"miniCartId", miniCartId
				).put(
					"orderDetailURL", HtmlUtil.escapeJS(orderDetailURL)
				).put(
					"orderId", orderId
				).put(
					"productURLSeparator", HtmlUtil.escapeJS(productURLSeparator)
				).put(
					"requestQuoteEnabled", requestCodeEnabled
				).put(
					"siteDefaultURL", HtmlUtil.escapeJS(siteDefaultURL)
				).put(
					"toggleable", toggleable
				).build()
			%>'
			module="{cart} from commerce-frontend-taglib"
		/>
	</c:otherwise>
</c:choose>