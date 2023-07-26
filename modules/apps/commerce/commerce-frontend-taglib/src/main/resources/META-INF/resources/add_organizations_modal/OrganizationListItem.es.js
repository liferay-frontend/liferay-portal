/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

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

	_handleToggleItem(event) {
		event.preventDefault();

		return this.emit('toggleItem', {
			id: this.id,
			name: this.name,
		});
	}
}

Soy.register(OrganizationListItem, template);

OrganizationListItem.STATE = {
	_selected: Config.bool().value(false),
	colorId: Config.number(),
	id: Config.oneOfType([Config.number(), Config.string()]),
	name: Config.string().required(),
	query: Config.string(),
	selectedOrganizations: Config.array(
		Config.shapeOf({
			id: Config.oneOfType([Config.number(), Config.string()]),
			name: Config.string(),
		})
	).value([]),
};

export {OrganizationListItem};
export default OrganizationListItem;
