/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {
	validateArray,
	validateArrayOf,
	validateBoolean,
	validateNumber,
	validateObject,
	validateShapeOf,
	validateString,
} from 'frontend-js-web';

import {focusedFieldStructure, pageStructure} from '../../util/config.es';

export default {

	/**
	 * @default
	 * @instance
	 * @memberof FormBuilder
	 * @type {?number}
	 */

	activePage: {
		validator: validateNumber,
		value: 0,
	},

	/**
	 * @default undefined
	 * @instance
	 * @memberof FormBuilder
	 * @type {?string}
	 */

	defaultLanguageId: {
		validator: validateString,
	},

	/**
	 * @default undefined
	 * @instance
	 * @memberof FormBuilder
	 * @type {?string}
	 */

	editingLanguageId: {
		validator: validateString,
	},

	/**
	 * @default []
	 * @instance
	 * @memberof FormBuilder
	 * @type {?(array|undefined)}
	 */

	fieldActions: {
		validator: validateArray,
		value: [],
	},

	/**
	 * @default undefined
	 * @instance
	 * @memberof FormBuilder
	 * @type {?string}
	 */

	fieldSetDefinitionURL: {
		validator: validateString,
	},

	/**
	 * @default []
	 * @instance
	 * @memberof FormBuilder
	 * @type {?(array|undefined)}
	 */

	fieldSets: {
		validator: validateArray,
		value: [],
	},

	/**
	 * @default []
	 * @instance
	 * @memberof FormBuilder
	 * @type {?(array|undefined)}
	 */

	fieldTypes: {
		validator: validateArray,
		value: [],
	},

	/**
	 * @default {}
	 * @instance
	 * @memberof FormBuilder
	 * @type {?object}
	 */

	focusedField: focusedFieldStructure.value({}),

	/**
	 * @default []
	 * @instance
	 * @memberof FormBuilder
	 * @type {?array<object>}
	 */

	pages: {
		validator: validateArrayOf(pageStructure),
		value: [],
	},

	/**
	 * @instance
	 * @memberof FormBuilder
	 * @type {string}
	 */

	paginationMode: {
		required: true,
		validator: validateString,
	},

	/**
	 * @instance
	 * @memberof FormBuilder
	 * @type {string}
	 */

	portletNamespace: {
		required: true,
		validator: validateString,
	},

	/**
	 * @default undefined
	 * @instance
	 * @memberof FormRenderer
	 * @type {!string}
	 */

	spritemap: {
		required: true,
		validator: validateString,
	},

	/**
	 * @instance
	 * @memberof FormBuilder
	 * @type {object}
	 */

	successPageSettings: {
		validator: validateShapeOf({
			body: {
				validator: validateObject,
			},
			enabled: {
				validator: validateBoolean,
			},
			title: {
				validator: validateObject,
			},
		}),
		value: {},
	},

	/**
	 * @default undefined
	 * @instance
	 * @memberof FormBuilder
	 * @type {?string}
	 */

	view: {
		validator: validateString,
	},
};
