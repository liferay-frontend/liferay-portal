/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipment.web.internal.model;

/**
 * @author Alec Sloan
 */
public class WarehouseItem {

	public WarehouseItem(
		String inputName, int maxQuantity, int minQuantity, int quantity) {

		_inputName = inputName;
		_maxQuantity = maxQuantity;
		_minQuantity = minQuantity;
		_quantity = quantity;
	}

	public String getInputName() {
		return _inputName;
	}

	public int getMaxQuantity() {
		return _maxQuantity;
	}

	public int getMinQuantity() {
		return _minQuantity;
	}

	public int getQuantity() {
		return _quantity;
	}

	private final String _inputName;
	private final int _maxQuantity;
	private final int _minQuantity;
	private final int _quantity;

}