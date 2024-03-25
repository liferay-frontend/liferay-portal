<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/add_to_cart/init.jsp" %>

<%
String spaceDirection = GetterUtil.getBoolean(inline) ? "ml" : "mt";
String spacer = size.equals("sm") ? "1" : "3";

String buttonCssClasses = "btn btn-add-to-cart btn-" + size + " " + spaceDirection + "-" + spacer;

String selectorCssClasses = "form-control quantity-selector form-control-" + size;
String wrapperCssClasses = "add-to-cart-wrapper align-items-center d-flex";

if (GetterUtil.getBoolean(iconOnly)) {
	buttonCssClasses = buttonCssClasses.concat(" icon-only");
}

if (!GetterUtil.getBoolean(inline)) {
	wrapperCssClasses = wrapperCssClasses.concat(" flex-column");
}

if (alignment.equals("center")) {
	wrapperCssClasses = wrapperCssClasses.concat(" align-items-center");
}

if (alignment.equals("full-width")) {
	buttonCssClasses = buttonCssClasses.concat(" btn-block");
	wrapperCssClasses = wrapperCssClasses.concat(" align-items-center");
}
%>

<div class="add-to-cart mb-2" id="<%= addToCartId %>">
	<div class="<%= wrapperCssClasses %>">
		<div class="<%= selectorCssClasses %> skeleton"></div>

		<button class="<%= buttonCssClasses %> skeleton">
			<liferay-ui:message key="add-to-cart" />
		</button>
	</div>
</div>

<%
JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();
%>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"accountId", commerceAccountId
		).put(
			"addToCartId", addToCartId
		).put(
			"cartId", commerceOrderId
		).put(
			"channelCurrencyCode", commerceCurrencyCode
		).put(
			"channelGroupId", commerceChannelGroupId
		).put(
			"channelId", commerceChannelId
		).put(
			"cpInstanceIncrementalOrderQuantity", cpInstanceUnitOfMeasure.getIncrementalOrderQuantity()
		).put(
			"cpInstanceKey", HtmlUtil.escapeJS(cpInstanceUnitOfMeasure.getKey())
		).put(
			"cpInstanceName", HtmlUtil.escapeJS(cpInstanceUnitOfMeasure.getName())
		).put(
			"cpInstancePrecision", cpInstanceUnitOfMeasure.getPrecision()
		).put(
			"cpInstancePrimary", cpInstanceUnitOfMeasure.isPrimary()
		).put(
			"cpInstancePriority", cpInstanceUnitOfMeasure.getPriority()
		).put(
			"cpInstanceRate", cpInstanceUnitOfMeasure.getRate()
		).put(
			"cpInstanceUnitOfMeasure", cpInstanceUnitOfMeasure
		).put(
			"disabled", disabled
		).put(
			"inCart", inCart
		).put(
			"productConfiguration", jsonSerializer.serializeDeep(productSettingsModel)
		).put(
			"productId", productId
		).put(
			"productSettingsModel", productSettingsModel != null
		).put(
			"published", published
		).put(
			"purchasable", purchasable
		).put(
			"settingsAlignment", alignment
		).put(
			"settingsIconOnly", iconOnly
		).put(
			"settingsInline", inline
		).put(
			"settingsNamespace", namespace
		).put(
			"settingsShowUnitOfMeasureSelector", showUnitOfMeasureSelector
		).put(
			"settingsSize", size
		).put(
			"showOrderTypeModal", showOrderTypeModal
		).put(
			"showOrderTypeModalURL", showOrderTypeModalURL
		).put(
			"skuId", cpInstanceId
		).put(
			"skuOptions", skuOptions
		).put(
			"stockQuantity", stockQuantity
		).build()
	%>'
	module="{addToCart} from commerce-frontend-taglib"
/>