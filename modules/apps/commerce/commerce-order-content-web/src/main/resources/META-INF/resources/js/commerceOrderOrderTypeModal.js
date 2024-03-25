/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CommerceServiceProvider, commerceEvents} from 'commerce-frontend-js';

export default function main({
	accountId,
	addToCart,
	commerceChannelId,
	currencyCode,
	ppState,
}) {
	Liferay.provide(window, '<portlet:namespace />addOrder', () => {
		window.parent.Liferay.fire(commerceEvents.IS_LOADING_MODAL, {
			isLoading: true,
		});

		const form = document.getElementById('<portlet:namespace />fm');

		const orderTypeId = form.querySelector(
			'#<portlet:namespace />commerceOrderTypeId'
		).value;

		if (addToCart) {
			window.parent.Liferay.fire(commerceEvents.ADD_ITEM_TO_CART, {
				orderTypeId,
			});

			window.parent.Liferay.fire(commerceEvents.CLOSE_MODAL, {
				successNotification: {
					message: Liferay.Language.get(
						'your-request-completed-successfully'
					),
					showSuccessNotification: true,
				},
			});
		}
		else {
			const CartResource = CommerceServiceProvider.default.DeliveryCartAPI(
				'v1'
			);

			CartResource.createCartByChannelId(commerceChannelId, {
				accountId,
				currencyCode,
				orderTypeId,
			})
				.then((order) => {
					Liferay.fire(commerceEvents.CURRENT_ORDER_UPDATED, {
						...order,
					});

					const redirectURL = new Liferay.Util.PortletURL.createPortletURL(
						'<%= editCommerceOrderRenderURL.toString() %>',
						{
							commerceOrderId: order.id,
							p_auth: Liferay.authToken,
							p_p_state: ppState,
						}
					);
					window.parent.Liferay.fire(commerceEvents.CLOSE_MODAL, {
						redirectURL: redirectURL.toString(),
						successNotification: {
							message: Liferay.Language.get(
								'your-request-completed-successfully'
							),
							showSuccessNotification: true,
						},
					});
				})
				.catch((error) => {
					return Promise.reject(error);
				});
		}
	});
}
