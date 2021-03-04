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

import Component from 'metal-component';
import Soy from 'metal-soy';

import template from './UserInputItem.soy';

function isString(value) {
	return typeof value === 'string';
}

class UserInputItem extends Component {
	_handleRemoveItem(evt) {
		evt.preventDefault();

		return this.emit('removeItem', {
			email: this.email,
		});
	}
}

Soy.register(UserInputItem, template);

UserInputItem.STATE = {
	email: {
		required: true,
		validator: isString,
	},
	name: {
		validator: isString,
	},
	spritemap: {
		validator: isString,
	},
	thumbnail: {
		validator: isString,
	},
};

export {UserInputItem};
export default UserInputItem;
