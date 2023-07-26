/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import template from './ProductDetailsModal.soy';

import 'clay-modal';

class ProductDetailsModal extends Component {
	created() {
		setTimeout(() => {
			this._isVisible = !this._isVisible;
		}, 2000);
	}

	_handleCloseModal(event) {
		event.preventDefault();

		return this.refs.modal.show();
	}
}

Soy.register(ProductDetailsModal, template);

ProductDetailsModal.STATE = {
	_modalVisible: Config.bool().value(false),
	addToOrderLink: Config.string(),
	availability: Config.string()
		.oneOf(['available', 'inStock', 'notAvailable'])
		.value('inStock'),
	categories: Config.array(
		Config.shapeOf({
			link: Config.string().required(),
			name: Config.string().required(),
		})
	).value([]),
	description: Config.string(),
	detailsLink: Config.string(),
	name: Config.string().required(),
	pictureUrl: Config.string().required(),
	settings: Config.shapeOf({
		minQuantity: Config.number(),
	}).value(),
	sku: Config.string().required(),
	spritemap: Config.string(),
};

export {ProductDetailsModal};
export default ProductDetailsModal;
