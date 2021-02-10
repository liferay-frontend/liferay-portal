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
	validateAny,
	validateArrayOf,
	validateObject,
	validateShapeOf,
	validateString,
} from 'frontend-js-web';

export default (Component) => {
	class WithLocale extends Component {
		prepareStateForRender(states) {
			const {editingLanguageId} = this.context.store;
			const languageValues = [];

			Object.keys(this.localizedValue).forEach((key) => {
				if (key !== editingLanguageId) {
					languageValues.push({
						name: this.name.replace(editingLanguageId, key),
						value: this.localizedValue[key],
					});
				}
			});

			return {
				...super.prepareStateForRender(states),
				_localizedValue: languageValues,
			};
		}
	}

	WithLocale.STATE = {
		_localizedValue: {
			validator: validateArrayOf({
				validator: validateShapeOf({
					name: {
						validator: validateString,
					},
					value: {
						validator: validateAny,
					},
				}),
			}),
			value: [],
		},

		localizedValue: {
			validator: validateObject,
			value: {},
		},
	};

	return WithLocale;
};
