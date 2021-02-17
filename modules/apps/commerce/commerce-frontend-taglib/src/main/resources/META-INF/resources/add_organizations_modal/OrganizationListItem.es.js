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

'use strict';

import {
	validateArray,
	validateBoolean,
	validateNumber,
	validateOneOfType,
	validateShapeOf,
	validateString,
} from 'frontend-js-web';
import Component from 'metal-component';
import Soy from 'metal-soy';

import template from './OrganizationListItem.soy';

import '../add_to_tick_item/AddToTickItem.es';

import '../autocomplete_item/AutocompleteItem.es';

class OrganizationListItem extends Component {
	syncSelectedOrganizations() {
		this._selected = this.selectedOrganizations.reduce(
			(hasItemBeenSelected, item) =>
				hasItemBeenSelected || item.id === this.id,
			false
		);

		return this._selected;
	}

	_handleToggleItem(e) {
		e.preventDefault();

		return this.emit('toggleItem', {
			id: this.id,
			name: this.name,
		});
	}
}

Soy.register(OrganizationListItem, template);

OrganizationListItem.STATE = {
	_selected: {
		validator: validateBoolean,
		value: false,
	},
	colorId: {
		validator: validateNumber,
	},
	id: {
		validator: validateOneOfType([
			{
				validator: validateNumber,
			},
			{validator: validateString},
		]),
	},
	name: {
		required: true,
		validator: validateString,
	},
	query: {
		validator: validateString,
	},
	selectedOrganizations: {
		validator: validateArray({
			validator: validateShapeOf({
				id: {
					validator: validateOneOfType([
						{
							validator: validateNumber,
						},
						{validator: validateString},
					]),
				},
				name: {validator: validateString},
			}),
		}),
		value: [],
	},
};

export {OrganizationListItem};
export default OrganizationListItem;
