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

/* eslint-disable react/no-string-refs */

import '../FormPortal/FormPortal.es';

import 'clay-icon';
import {
	ALIGN_POSITIONS,
	align,
	validateBoolean,
	validateString,
} from 'frontend-js-web';
import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './Tooltip.soy';

const POSITIONS = ['top', 'left', 'right', 'bottom'];

class Tooltip extends Component {
	created() {
		this._handleTooltipHovered = this._handleTooltipHovered.bind(this);
		this._handleTooltipRendered = this._handleTooltipRendered.bind(this);
	}

	_handleTooltipHovered() {
		this.showContent = true;
	}

	_handleTooltipLeaved() {
		this.showContent = false;
	}

	_handleTooltipRendered() {
		const {tooltipSource, tooltipTarget} = this.refs;
		const {element} = tooltipSource;
		const suggestedPosition = align(
			element,
			tooltipTarget,
			ALIGN_POSITIONS.Right
		);

		this.position = POSITIONS[suggestedPosition];
	}
}

Soy.register(Tooltip, templates);

Tooltip.STATE = {

	/**
	 * @default undefined
	 * @instance
	 * @memberof Tooltip
	 * @type {?(string|undefined)}
	 */

	icon: {
		required: true,
		validator: validateString,
	},

	/**
	 * @default undefined
	 * @instance
	 * @memberof Tooltip
	 * @type {?(number|undefined)}
	 */

	position: {
		validator: validateString,
	},

	/**
	 * @default undefined
	 * @instance
	 * @memberof Tooltip
	 * @type {?(string|undefined)}
	 */

	showContent: {
		validator: validateBoolean,
	},

	/**
	 * @default undefined
	 * @instance
	 * @memberof Tooltip
	 * @type {?(string|undefined)}
	 */

	spritemap: {
		required: true,
		validator: validateString,
	},

	/**
	 * @default undefined
	 * @instance
	 * @memberof Tooltip
	 * @type {?(string|undefined)}
	 */

	text: {
		required: true,
		validator: validateString,
	},
};

export default Tooltip;
