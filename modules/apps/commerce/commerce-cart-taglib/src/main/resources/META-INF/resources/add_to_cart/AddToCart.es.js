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

import {fetch, validateString} from 'frontend-js-web';
import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './AddToCart.soy';

class AddToCart extends Component {
	created() {
		Liferay.on(
			this.cpDefinitionId + 'CPInstance:change',
			this._instanceChange.bind(this)
		);
	}

	/**
	 * Makes an ajax request to submit the data.
	 * @param {Event} event
	 * @protected
	 */

	_addToCart() {
		var instance = this;

		var _quantity = this.quantity;
		var ddmFormValues = '[]';

		var productContent = this._getProductContent();

		if (productContent) {
			ddmFormValues = JSON.stringify(productContent.getFormValues());

			if (this.cpInstanceId == '0') {
				this.cpInstanceId = productContent.getCPInstanceId();
			}
		}

		var quantityNode = document.querySelector(
			'#' + this.taglibQuantityInputId
		);

		if (quantityNode) {
			_quantity = quantityNode.value;
		}

		const formData = new FormData();

		formData.append(
			this.portletNamespace + 'cpDefinitionId',
			this.cpDefinitionId
		);
		formData.append(
			this.portletNamespace + 'cpInstanceId',
			this.cpInstanceId
		);
		formData.append(this.portletNamespace + 'ddmFormValues', ddmFormValues);
		formData.append(this.portletNamespace + 'quantity', _quantity);

		fetch(this.uri, {
			body: formData,
			method: 'POST',
		})
			.then((response) => response.json())
			.then((jsonresponse) => {
				if (jsonresponse.success) {
					Liferay.fire('current-order-updated', jsonresponse);

					instance._showNotification(
						jsonresponse.successMessage,
						'success'
					);
				}
				else {
					var validatorErrors = jsonresponse.validatorErrors;

					if (validatorErrors) {
						validatorErrors.forEach((validatorError) => {
							instance._showNotification(
								validatorError.message,
								'danger'
							);
						});
					}
					else {
						instance._showNotification(
							jsonresponse.error,
							'danger'
						);
					}
				}
			});
	}

	_getProductContent() {
		return Liferay.component(this.productContentId);
	}

	_handleClick() {
		var instance = this;

		var productContent = this._getProductContent();

		if (productContent) {
			productContent.validateProduct((hasError) => {
				if (!hasError) {
					instance._addToCart();
				}
			});
		}
		else {
			this._addToCart();
		}
	}

	_instanceChange(event) {
		if (event.cpInstanceExist) {
			this.cpInstanceId = event.cpInstanceId;
		}
	}

	_showNotification(message, type) {
		AUI().use('liferay-notification', () => {
			new Liferay.Notification({
				closeable: true,
				delay: {
					hide: 5000,
					show: 0,
				},
				duration: 500,
				message,
				render: true,
				title: '',
				type,
			});
		});
	}
}

/**
 * State definition.
 * @ignore
 * @static
 * @type {!Object}
 */

AddToCart.STATE = {

	/**
	 * CPDefinitionId.
	 * @instance
	 * @memberof AddToCart
	 * @type {?number}
	 * @default undefined
	 */

	cpDefinitionId: {
		validator: validateString,
	},

	/**
	 * CPInstanceId.
	 * @instance
	 * @memberof AddToCart
	 * @type {?number}
	 * @default undefined
	 */

	cpInstanceId: {
		validator: validateString,
	},

	/**
	 * CSS classes to be applied to the element.
	 * @instance
	 * @memberof AddToCart
	 * @type {?string}
	 * @default undefined
	 */

	elementClasses: {
		validator: validateString,
	},

	/**
	 * Component id.
	 * @instance
	 * @memberof AddToCart
	 * @type {String}
	 */

	id: {
		required: true,
		validator: validateString,
	},

	/**
	 * Text to display inside the add to cart button.
	 * @instance
	 * @memberof AddToCart
	 * @type {String}
	 */

	label: {
		required: true,
		validator: validateString,
	},

	/**
	 * Portlet's namespace
	 * @instance
	 * @memberof AddToCart
	 * @type {String}
	 */

	portletNamespace: {
		required: true,
		validator: validateString,
	},

	/**
	 * Product content id
	 * @instance
	 * @memberof AddToCart
	 * @type {String}
	 */

	productContentId: {
		validator: validateString,
	},

	/**
	 * Default quantity to add to cart.
	 * @instance
	 * @memberof AddToCart
	 * @type {?string}
	 * @default undefined
	 */

	quantity: {
		validator: validateString,
	},

	/**
	 * Id of the html input to get the quantity.
	 * @instance
	 * @memberof AddToCart
	 * @type {?string}
	 * @default undefined
	 */

	taglibQuantityInputId: {
		validator: validateString,
	},

	/**
	 * Uri to add a cart item.
	 * @instance
	 * @memberof AddToCart
	 * @type {String}
	 */

	uri: {
		required: true,
		validator: validateString,
	},
};

// Register component

Soy.register(AddToCart, templates);

export default AddToCart;
