/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export {
	AddToCart,
	AddToWishList,
	Autocomplete,
	AutocompletePureComponent,
	DropdownMenu,
	Gallery,
	ItemFinder,
	MiniCart,
	Price,
	QuantitySelector,
	StepTracker,
	Summary,
	TierPrice,
	UnitOfMeasureSelector,
	MiniCartContext,
} from './components/index';
export {default as CommerceServiceProvider} from './ServiceProvider/index';
export {default as CommerceFrontendUtils} from './utilities/interface/index';

// This is to provide a layer indirection for internal modules so that we are
// not directly relying on a global value and can import `CommerceContext`
// instead.

export const CommerceContext = Liferay.CommerceContext;

export {default as commerceEvents} from './utilities/eventsDefinitions';
export {default as FormUtils} from './utilities/forms/index';
export {default as frontendJsWeb} from 'frontend-js-web';
export {default as MiniCompare} from './components/mini_compare/entry';
export {default as slugify} from './utilities/slugify';
export {
	fetchHeaders,
	getData,
	liferayNavigate,
	getObjectFromPath,
	formatAutocompleteItem,
	getValueFromItem,
	getLabelFromItem,
	formatActionUrl,
	getRandomId,
	sortByKey,
	isProductPurchasable,
} from './utilities/index';
export {default as ModalUtils} from './utilities/modals/index';
