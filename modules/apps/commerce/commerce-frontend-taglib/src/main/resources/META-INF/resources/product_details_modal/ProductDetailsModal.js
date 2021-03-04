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
import Soy, {Config} from 'metal-soy';

import template from './ProductDetailsModal.soy';

import 'clay-modal';

function isString(value) {
	return typeof value === 'string';
}

class ProductDetailsModal extends Component {
	created() {
		setTimeout(() => {
			this._isVisible = !this._isVisible;
		}, 2000);
	}

	_handleCloseModal(evt) {
		evt.preventDefault();

		return this.refs.modal.show();
	}
}

Soy.register(ProductDetailsModal, template);

ProductDetailsModal.STATE = {
	_modalVisible: Config.bool().value(false),
	addToOrderLink: {
		validator: isString,
	},
	availability: {
		validator: isString,
	}
		.oneOf(['available', 'inStock', 'notAvailable'])
		.value('inStock'),
	categories: Config.array(
		Config.shapeOf({
			link: {
				required: true,
				validator: isString,
			},
			name: {
				required: true,
				validator: isString,
			},
		})
	).value([]),
	description: {
		validator: isString,
	},
	detailsLink: {
		validator: isString,
	},
	name: {
		required: true,
		validator: isString,
	},
	pictureUrl: {
		required: true,
		validator: isString,
	},
	settings: Config.shapeOf({
		minQuantity: Config.number(),
	}).value(),
	sku: {
		required: true,
		validator: isString,
	},
	spritemap: {
		validator: isString,
	},
};

export {ProductDetailsModal};
export default ProductDetailsModal;
