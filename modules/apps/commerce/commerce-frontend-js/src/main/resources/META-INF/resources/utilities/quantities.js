/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function getMinQuantity(minQuantity = 1, multipleQuantity = 1) {
	if (multipleQuantity <= 1) {
		return minQuantity;
	}

	const minDifference = minQuantity % multipleQuantity;

	if (!minDifference) {
		return multipleQuantity;
	}

	return minQuantity + multipleQuantity - minDifference;
}

export function getProductMaxQuantity(maxQuantity, multipleQuantity = 1) {
	if (!maxQuantity) {
		return '';
	}

	if (multipleQuantity <= 1) {
		return maxQuantity;
	}

	const maxDifference = maxQuantity % multipleQuantity;

	return maxQuantity - maxDifference;
}

export function getProductMinQuantity({
	allowedOrderQuantities,
	minOrderQuantity,
	multipleOrderQuantity,
}) {
	let minQuantity;

	if (allowedOrderQuantities.length) {
		minQuantity = Math.min(...allowedOrderQuantities);
	}
	else {
		minQuantity = getMinQuantity(minOrderQuantity, multipleOrderQuantity);
	}

	return minQuantity;
}
