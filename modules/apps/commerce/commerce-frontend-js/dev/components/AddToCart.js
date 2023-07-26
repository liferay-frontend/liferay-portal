/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AddToCart from '../../src/main/resources/META-INF/resources/components/add_to_cart/AddToCart';
import AddToCartButton from '../../src/main/resources/META-INF/resources/components/add_to_cart/AddToCartButton';
import launcher from '../../src/main/resources/META-INF/resources/utilities/launcher';

import '../../src/main/resources/META-INF/resources/styles/main.scss';

const commonProps = {
	accountId: '43879',
	cartId: '43882',
	cartUUID: 'a711bf49-a2d3-2c8d-23c9-abaff7d288a5',
	channel: {
		currencyCode: 'USD',
		groupId: '42398',
		id: '42397',
	},
	settings: {
		iconOnly: false,
		productConfiguration: {
			allowedOrderQuantities: [],
			maxOrderQuantity: 50,
			minOrderQuantity: 4,
			multipleOrderQuantity: 3,
		},
	},
	size: 'sm',
};

const addToCartProps = {
	...commonProps,
	cpInstance: {
		inCart: false,
		quantity: 3,
		skuId: 42633,
		skuOptions: [],
	},
};

const addToCartButtonProps = {
	...commonProps,
	cpInstances: [
		{
			inCart: false,
			quantity: 3,
			skuId: 42633,
			skuOptions: [],
		},
	],
};

launcher(AddToCart, 'add_to_cart', 'add-to-cart-card', {
	...addToCartProps,
	cpInstance: {
		...addToCartProps.cpInstance,
		inCart: true,
	},
	settings: {
		...addToCartProps.settings,
		alignment: 'full-width',
		inline: false,
		size: 'md',
	},
});

launcher(AddToCart, 'add_to_cart', 'add-to-cart-card-select', {
	...addToCartProps,
	settings: {
		...addToCartProps.settings,
		alignment: 'full-width',
		inline: false,
		productConfiguration: {
			...addToCartProps.settings.productConfiguration,
			allowedOrderQuantities: [3, 5, 10, 100],
		},
		size: 'md',
	},
});

launcher(AddToCart, 'add_to_cart', 'add-to-cart-product-detail', {
	...addToCartProps,
	settings: {
		...addToCartProps.settings,
		inline: true,
		size: 'lg',
	},
});

launcher(AddToCart, 'add_to_cart', 'add-to-cart-product-detail-select', {
	...addToCartProps,
	settings: {
		...addToCartProps.settings,
		inline: true,
		productConfiguration: {
			...addToCartProps.settings.productConfiguration,
			allowedOrderQuantities: [3, 5, 10, 100],
		},
		size: 'lg',
	},
});

launcher(AddToCart, 'add_to_cart', 'add-to-cart-sbd', {
	...addToCartProps,
	settings: {
		...addToCartProps.settings,
		alignment: 'full-width',
		iconOnly: true,
		inline: false,
		size: 'sm',
	},
});

launcher(AddToCart, 'add_to_cart', 'add-to-cart-sbd-select', {
	...addToCartProps,
	settings: {
		...addToCartProps.settings,
		alignment: 'full-width',
		iconOnly: true,
		inline: false,
		productConfiguration: {
			...addToCartProps.settings.productConfiguration,
			allowedOrderQuantities: [3, 5, 10, 100],
		},
		size: 'sm',
	},
});

launcher(AddToCart, 'add_to_cart', 'add-to-cart-sbd-select', {
	...addToCartProps,
	settings: {
		...addToCartProps.settings,
		alignment: 'full-width',
		iconOnly: true,
		inline: false,
		productConfiguration: {
			...addToCartProps.settings.productConfiguration,
			allowedOrderQuantities: [3, 5, 10, 100],
		},
		size: 'sm',
	},
});

launcher(AddToCartButton, 'add_to_cart_button', 'add-to-cart-button', {
	...addToCartButtonProps,
	settings: {
		...addToCartButtonProps.settings,
		alignment: 'full-width',
		iconOnly: true,
		inline: false,
		productConfiguration: {
			...addToCartButtonProps.settings.productConfiguration,
			allowedOrderQuantities: [3, 5, 10, 100],
		},
		size: 'sm',
	},
});
