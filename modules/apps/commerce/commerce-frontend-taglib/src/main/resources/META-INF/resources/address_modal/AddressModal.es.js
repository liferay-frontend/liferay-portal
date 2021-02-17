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
	fetch,
	validateArray,
	validateBoolean,
	validateNumber,
	validateOneOf,
	validateOneOfType,
	validateShapeOf,
	validateString,
} from 'frontend-js-web';
import Component from 'metal-component';
import Soy from 'metal-soy';

import template from './AddressModal.soy';

import 'clay-modal';

import '../input_utils/CommerceInputText';

class AddressModal extends Component {
	attached() {
		return this._fetchCountries();
	}

	sync_formData() {
		return this._validateForms();
	}

	_handleFirstDotClick(e) {
		e.preventDefault();
		this._stage = 1;

		return this._stage;
	}

	_handleSecondDotClick(e) {
		return this._handleNextButton(e);
	}

	_handleNextButton(e) {
		e.preventDefault();
		this._firstFormValid = this.refs.modal.refs.firstForm.checkValidity();
		if (this._firstFormValid) {
			this._stage = 2;
		}

		return e;
	}

	_handleCloseModal(e) {
		e.preventDefault();
		this._modalVisible = false;

		return e;
	}

	_handleSelectBox(e) {
		const value = e.target.value;
		if (e.target.name === 'commerceCountry') {
			this._formData = {
				...this._formData,
				country: value,
			};

			const country = this._countries.filter(
				(country) => country.id == value
			);

			if (country.length === 1) {
				this._isBillingAllowed = country[0].billingAllowed;
				this._isShippingAllowed = country[0].shippingAllowed;

				this._fetchRegions();
			}
			else {
				this._regions = [];
			}
		}
		else if (e.target.name === 'addressType') {
			this._formData = {
				...this._formData,
				addressType: value,
			};
		}
		else {
			this._formData = {
				...this._formData,
				region: value,
			};
		}

		return value;
	}

	_handleInputBox(evt) {
		this._formData = {
			...this._formData,
			[evt.target.name]: evt.target.value,
		};

		return evt.target.value;
	}

	_validateForms() {
		const firstFormValid = !!(
			this._formData.address &&
			this._formData.address.length &&
			this._formData.city &&
			this._formData.city.length &&
			this._formData.zipCode &&
			this._formData.zipCode.length &&
			this._formData.country &&
			this._formData.country.length &&
			this._formData.region &&
			this._formData.region.length
		);
		this._firstFormValid = firstFormValid;

		const secondFormValid = !!(
			this._formData.referent && this._formData.referent.length
		);
		this._secondFormValid = secondFormValid;

		return this._firstFormValid && this._secondFormValid;
	}

	fetchExistingAddress(id) {
		fetch('/o/commerce-ui/address/' + id)
			.then((response) => response.json())
			.then((jsonResponse) => {
				const data = JSON.parse(jsonResponse);

				this._formData = {
					...this._formData,
					address: data.street1,
					addressType: data.type,
					city: data.city,
					country: data.commerceCountryId,
					id,
					referent: data.name,
					region: data.commerceRegionId,
					telephone: data.phoneNumber,
					zipCode: data.zip,
				};

				this._fetchRegions();
			});
	}

	_fetchCountries() {
		return fetch(this.countriesAPI)
			.then((response) => response.json())
			.then((countries) => {
				this._countries = countries;

				return this._countries;
			});
	}

	_fetchRegions() {
		return fetch(this.regionsAPI + this._formData.country)
			.then((response) => response.json())
			.then((regions) => {
				this._regions = regions;

				return this._regions;
			});
	}

	_handleFormSubmit(e) {
		e.preventDefault();
		if (this._firstFormValid && this._secondFormValid) {
			this._addAddress(e);
		}

		return e;
	}

	_addAddress(_e) {
		return this.emit('addressModalSave', this._formData);
	}

	resetForm() {
		this._formData = {
			address: null,
			addressType: 2,
			city: null,
			country: null,
			id: null,
			referent: null,
			region: null,
			telephone: null,
			zipCode: null,
		};

		this._stage = 1;
	}

	toggle() {
		this._modalVisible = !this._modalVisible;

		return this._modalVisible;
	}

	open() {
		this._modalVisible = true;

		return this._modalVisible;
	}

	close() {
		this._modalVisible = false;

		return this._modalVisible;
	}
}

Soy.register(AddressModal, template);

AddressModal.STATE = {
	_countries: {
		validator: validateArray({
			validator: validateShapeOf({
				billingAllowed: {
					required: true,
					validator: validateBoolean,
				},
				id: {
					required: true,
					validator: validateNumber,
				},
				name: {
					required: true,
					validator: validateString,
				},
				shippingAllowed: {
					required: true,
					validator: validateBoolean,
				},
			}),
		}),
		value: [],
	},
	_firstFormValid: {
		validator: validateBoolean,
		value: false,
	},
	_formData: {
		validator: validateShapeOf({
			address: {
				validator: validateString,
			},
			addressType: {
				validator: validateOneOfType([
					{
						validator: validateString,
					},
					{
						validator: validateNumber,
					},
				]),
			},
			city: {
				validator: validateString,
			},
			country: {
				validator: validateOneOfType([
					{
						validator: validateString,
					},
					{
						validator: validateNumber,
					},
				]),
			},
			id: {
				validator: validateOneOfType([
					{
						validator: validateString,
					},
					{
						validator: validateNumber,
					},
				]),
			},
			referent: {
				validator: validateString,
			},
			region: {
				validator: validateOneOfType([
					{
						validator: validateString,
					},
					{
						validator: validateNumber,
					},
				]),
			},
			telephone: {
				validator: validateString,
			},
			zipCode: {
				validator: validateString,
			},
		}),
		value: {
			address: null,
			addressType: 2,
			city: null,
			country: null,
			id: null,
			referent: null,
			region: null,
			telephone: null,
			zipCode: null,
		},
	},
	_isBillingAllowed: {
		validator: validateBoolean,
		value: true,
	},
	_isShippingAllowed: {
		validator: validateBoolean,
		value: true,
	},
	_modalVisible: {
		internal: true,
		validator: validateBoolean,
		value: false,
	},
	_regions: {
		validator: validateArray({
			validator: validateShapeOf({
				id: {
					required: true,
					validator: validateNumber,
				},
				name: {
					required: true,
					validator: validateString,
				},
			}),
		}),
		value: [],
	},
	_secondFormValid: {
		validator: validateBoolean,
		value: false,
	},
	_stage: {
		validator: validateNumber({
			validator: validateOneOf([1, 2]),
		}),
		value: 1,
	},
	countriesAPI: {
		required: true,
		validator: validateString,
	},
	regionsAPI: {
		required: true,
		validator: validateString,
	},
	spritemap: {
		validator: validateString,
	},
};

export {AddressModal};
export default AddressModal;
