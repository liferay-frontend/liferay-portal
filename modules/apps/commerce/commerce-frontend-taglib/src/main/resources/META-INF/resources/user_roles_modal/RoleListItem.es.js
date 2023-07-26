/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import template from './RoleListItem.soy';

import '../autocomplete_item/AutocompleteItem.es';

class RoleListItem extends Component {
	syncSelectedRoles() {
		this._selected = this.selectedRoles.reduce(
			(itemSelected, item) => itemSelected || item.id === this.id,
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

Soy.register(RoleListItem, template);

RoleListItem.STATE = {
	_selected: Config.bool().value(false),
	id: Config.oneOfType([Config.number(), Config.string()]),
	name: Config.string(),
	query: Config.string(),
	selectedRoles: Config.array(
		Config.shapeOf({
			id: Config.oneOfType([Config.number(), Config.string()]),
			name: Config.string(),
		})
	).value([]),
};

export {RoleListItem};
export default RoleListItem;
