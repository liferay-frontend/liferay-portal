/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	AddToCart,
	AddToWishList,
	Cart,
	Modal,
	Price,
	RequestQuote,
	accountSelector,
	compareCheckbox,
	dropdown,
	stepTracker,
} from 'commerce-frontend-js';

export {default as SearchBar} from './search_bar/SearchBar';
export {default as SearchResults} from './search_results/SearchResults';
export {default as DiscontinuedLabelCPInstanceChangeHandler} from './discontinued_label/DiscontinuedLabelCPInstanceChangeHandler';

export function accountSelectorTag({
	accountEntryAllowedTypes,
	accountSelectorId,
	commerceChannelId,
	createNewOrderURL,
	currentCommerceAccount,
	currentCommerceOrder,
	refreshPageOnAccountSelected,
	selectOrderURL,
	setCurrentAccountURL,
	showOrderTypeModal,
}) {
	accountSelector(accountSelectorId, accountSelectorId, {
		accountEntryAllowedTypes,
		commerceChannelId,
		createNewOrderURL,
		currentCommerceAccount,
		currentCommerceOrder,
		namespace: accountSelectorId,
		refreshPageOnAccountSelected,
		selectOrderURL,
		setCurrentAccountURL,
		showOrderTypeModal,
	});
}

export function addToListWish({
	accountId,
	addToWishListId,
	cpDefinitionId,
	isInWishList,
	large,
	skuId,
}) {
	AddToWishList(addToWishListId, addToWishListId, {
		accountId,
		cpDefinitionId,
		isInWishList,
		large,
		skuId,
	});
}

export function compareCheckboxTag({
	commerceChannelGroupId,
	disabled,
	inCompare,
	itemId,
	label,
	pictureUrl,
	refreshOnRemove,
	rootId,
}) {
	compareCheckbox(rootId, rootId, {
		commerceChannelGroupId,
		disabled,
		inCompare,
		itemId,
		label,
		pictureUrl,
		refreshOnRemove,
	});
}

export function dropdownMain({items, spritemap}) {
	dropdown('dropdown-header', 'dropdown-header-container', {
		items,
		spritemap,
	});
}

export function modal({
	containerId,
	id,
	portletId,
	refreshPageOnClose,
	size,
	spritemap,
	title,
	url,
}) {
	Modal(id, containerId, {
		id,
		onClose: refreshPageOnClose
			? function () {
					window.location.reload();
			  }
			: null,
		portletId,
		size,
		spritemap,
		title,
		url,
	});
}

export function stepTrackerMain({portletId, spritemap, stepTrackerId, steps}) {
	stepTracker(stepTrackerId, stepTrackerId, {
		portletId,
		spritemap,
		steps,
	});
}

export function price({
	containerId,
	displayDiscountLevels,
	namespace,
	netPrice,
	price,
	standalone,
}) {
	Price(containerId, containerId, {
		displayDiscountLevels,
		namespace,
		netPrice,
		price,
		standalone,
	});
}

export function addToCart({
	accountId,
	addToCartId,
	cartId,
	channelCurrencyCode,
	channelGroupId,
	channelId,
	cpInstanceIncrementalOrderQuantity,
	cpInstanceKey,
	cpInstanceName,
	cpInstancePrecision,
	cpInstancePrimary,
	cpInstancePriority,
	cpInstanceRate,
	cpInstanceUnitOfMeasure,
	disabled,
	inCart,
	productConfiguration,
	productId,
	productSettingsModel,
	published,
	purchasable,
	settingsAlignment,
	settingsIconOnly,
	settingsInline,
	settingsNamespace,
	settingsShowUnitOfMeasureSelector,
	settingsSize,
	showOrderTypeModal,
	showOrderTypeModalURL,
	skuId,
	skuOptions = [],
	stockQuantity,
}) {
	const props = {
		accountId,
		cartId,
		channel: {
			currencyCode: channelCurrencyCode,
			groupId: channelGroupId,
			id: channelId,
		},
		cpInstance: {
			availability: {
				stockQuantity,
			},
			backOrderAllowed: productConfiguration
				? productConfiguration.backOrders
				: null,
			inCart,
			published,
			purchasable,
			skuId,
			skuOptions,
			stockQuantity,
		},
		disabled,
		productId,
		settings: {
			alignment: settingsAlignment,
			iconOnly: settingsIconOnly,
			inline: settingsInline,
			namespace: settingsNamespace,
			showUnitOfMeasureSelector: settingsShowUnitOfMeasureSelector,
			size: settingsSize,
		},
		showOrderTypeModal,
		showOrderTypeModalURL,
	};

	if (cpInstanceUnitOfMeasure) {
		props.cpInstance.skuUnitOfMeasure = {
			incrementalOrderQuantity: cpInstanceIncrementalOrderQuantity,
			key: cpInstanceKey,
			name: cpInstanceName,
			precision: cpInstancePrecision,
			primary: cpInstancePrimary,
			priority: cpInstancePriority,
			rate: cpInstanceRate,
		};
	}

	if (productSettingsModel) {
		props.settings.productConfiguration = {
			allowBackOrder: productConfiguration.backOrders,
			allowedOrderQuantities: productConfiguration.allowedQuantities,
			maxOrderQuantity: productConfiguration.maxQuantity,
			minOrderQuantity: productConfiguration.minQuantity,
			multipleOrderQuantity: productConfiguration.multipleQuantity,
		};
	}

	AddToCart(addToCartId, addToCartId, props);
}

export function requestQuote({
	accountId,
	channel,
	cpDefinitionId,
	cpInstance,
	disabled,
	namespace,
	orderDetailURL,
	requestQuoteElementId,
}) {
	RequestQuote(requestQuoteElementId, requestQuoteElementId, {
		accountId,
		channel,
		cpDefinitionId,
		cpInstance,
		disabled,
		namespace,
		orderDetailURL,
	});
}

export function cart({
	accountId,
	cartViews,
	checkoutURL,
	currencyCode,
	displayDiscountLevels,
	displayTotalItemsQuantity,
	groupId,
	id,
	itemsQuantity,
	labels,
	miniCartId,
	orderDetailURL,
	orderId,
	productURLSeparator,
	requestQuoteEnabled,
	siteDefaultURL,
	toggleable,
}) {
	Cart(miniCartId, miniCartId, {
		accountId,
		cartActionURLs: {
			checkoutURL,
			orderDetailURL,
			productURLSeparator,
			siteDefaultURL,
		},
		cartViews,
		channel: {
			currencyCode,
			groupId,
			id,
		},
		displayDiscountLevels,
		displayTotalItemsQuantity,
		itemsQuantity,
		labels,
		orderId,
		requestQuoteEnabled,
		toggleable,
	});
}
