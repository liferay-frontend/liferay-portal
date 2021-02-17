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
	validateNumber,
	validateOneOfType,
	validateString,
} from 'frontend-js-web';
import Component from 'metal-component';
import Soy from 'metal-soy';

import template from './OrganizationInputItem.soy';

class OrganizationInputItem extends Component {
	_handleRemoveItem(e) {
		e.preventDefault();

		return this.emit('removeItem', {
			id: this.id,
		});
	}
}

Soy.register(OrganizationInputItem, template);

OrganizationInputItem.STATE = {
	id: {
		required: true,
		validator: validateOneOfType([
			{
				validator: validateNumber,
			},
			{
				validator: validateString,
			},
		]),
	},
	name: {
		required: true,
		validator: validateString,
	},
	spritemap: {
		validator: validateString,
	},
};

export {OrganizationInputItem};
export default OrganizationInputItem;
