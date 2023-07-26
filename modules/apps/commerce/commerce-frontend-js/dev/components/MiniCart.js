/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import launcher from '../../src/main/resources/META-INF/resources/components/mini_cart/entry';

import '../../src/main/resources/META-INF/resources/styles/main.scss';

launcher('mini_cart', 'mini-cart-root-id', {
	cartActionURLs: {
		checkoutURL:
			'http://localhost:8080/group/minium/checkout?p_p_id=com_liferay_commerce_checkout_web_internal_portlet_CommerceCheckoutPortlet\x26p_p_lifecycle=0\x26_com_liferay_commerce_checkout_web_internal_portlet_CommerceCheckoutPortlet_mvcRenderCommandName=\x252Fcommerce_checkout\x252Fcheckout_redirect',
		orderDetailURL:
			'http://localhost:8080/group/minium/pending-orders?p_p_id=com_liferay_commerce_order_content_web_internal_portlet_CommerceOpenOrderContentPortlet\x26p_p_lifecycle=0\x26_com_liferay_commerce_order_content_web_internal_portlet_CommerceOpenOrderContentPortlet_mvcRenderCommandName=\x252Fcommerce_open_order_content\x252Fedit_commerce_order\x26_com_liferay_commerce_order_content_web_internal_portlet_CommerceOpenOrderContentPortlet_commerceOrderUuid=1c25ed61-0a41-b490-fb52-9df18f3f2f33',
		productURLSeparator: '/p/',
		siteDefaultURL: 'http://localhost:8080/group/minium',
	},
	displayDiscountLevels: false,
	displayTotalItemsQuantity: false,
	itemsQuantity: 3,
	orderId: 43621,
	spritemap: 'http://localhost:8080/o/minium-theme/images/clay/icons\x2esvg',
	toggleable: true,
});
