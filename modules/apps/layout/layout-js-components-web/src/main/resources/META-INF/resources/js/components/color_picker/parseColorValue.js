/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import convertRGBtoHex from '../../utils/convertRGBtoHex';
import getValidHexColor from '../../utils/getValidHexColor';

const ERROR_MESSAGES = {
	mutuallyReferenced: Liferay.Language.get(
		'tokens-cannot-be-mutually-referenced'
	),
	selfReferenced: Liferay.Language.get('tokens-cannot-reference-itself'),
};

export function parseColorValue({editedTokenValues, field, token, value}) {
	let validValue = token?.name;
	let tokenLabel = null;
	let pickerColor = null;

	if (token) {
		if (token.name === field.name) {
			return {error: ERROR_MESSAGES.selfReferenced};
		}

		if (editedTokenValues?.[token.name]?.name === field.name) {
			return {error: ERROR_MESSAGES.mutuallyReferenced};
		}

		tokenLabel = token.label;
	}
	else if (value.startsWith('#')) {
		validValue = getValidHexColor(value);

		if (!validValue) {
			return {};
		}

		pickerColor = validValue.replace('#', '');
	}
	else {
		const element = document.createElement('div');

		element.style.background = value;
		element.style.display = 'none';

		document.body.appendChild(element);

		validValue = element.style.background;

		if (!validValue) {
			return {};
		}

		pickerColor = convertRGBtoHex(
			window.getComputedStyle(element).backgroundColor
		).replace(/^#/, '');

		element.parentElement.removeChild(element);
	}

	return {
		label: tokenLabel,
		pickerColor,
		value: validValue,
		...(token && {color: token.value}),
	};
}
