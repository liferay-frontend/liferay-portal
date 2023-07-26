/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import launcher from '../../src/main/resources/META-INF/resources/components/global_search/entry';

import '../../src/main/resources/META-INF/resources/styles/main.scss';

launcher('global-search', 'global-search-root', {
	accountId: 42811,
	accountURLTemplate: '/account-page/{id}',
	accountsSearchURLTemplate: '/accounts?search={query}',
	cartURLTemplate: '/cart-page/{id}',
	cartsSearchURLTemplate: '/carts?search={query}',
	channelId: 42627,
	globalSearchURLTemplate: '/global?search={query}',
	productURLTemplate: '/product-page/{id}',
	productsSearchURLTemplate: '/products?search={query}',
});
