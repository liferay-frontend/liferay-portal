/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import template from './OrganizationInputItem.soy';

class OrganizationInputItem extends Component {
	_handleRemoveItem(event) {
		event.preventDefault();

		return this.emit('removeItem', {
			id: this.id,
		});
	}
}

Soy.register(OrganizationInputItem, template);

OrganizationInputItem.STATE = {
	id: Config.oneOfType([Config.number(), Config.string()]).required(),
	name: Config.string().required(),
	spritemap: Config.string(),
};

export {OrganizationInputItem};
export default OrganizationInputItem;
