/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import template from './UserInputItem.soy';

class UserInputItem extends Component {
	_handleRemoveItem(event) {
		event.preventDefault();

		return this.emit('removeItem', {
			email: this.email,
		});
	}
}

Soy.register(UserInputItem, template);

UserInputItem.STATE = {
	email: Config.string().required(),
	name: Config.string(),
	spritemap: Config.string(),
	thumbnail: Config.string(),
};

export {UserInputItem};
export default UserInputItem;
