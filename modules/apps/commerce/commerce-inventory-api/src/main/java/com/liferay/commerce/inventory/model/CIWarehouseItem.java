/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.model;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class CIWarehouseItem {

	public CIWarehouseItem(
		String skuCode, int stockQuantity, int bookedQuantity,
		int replenishmentQuantity) {

		_skuCode = skuCode;
		_stockQuantity = stockQuantity;
		_bookedQuantity = bookedQuantity;
		_replenishmentQuantity = replenishmentQuantity;
	}

	public int getBookedQuantity() {
		return _bookedQuantity;
	}

	public int getReplenishmentQuantity() {
		return _replenishmentQuantity;
	}

	public String getSkuCode() {
		return _skuCode;
	}

	public int getStockQuantity() {
		return _stockQuantity;
	}

	public void setBookedQuantity(int bookedQuantity) {
		_bookedQuantity = bookedQuantity;
	}

	public void setReplenishmentQuantity(int replenishmentQuantity) {
		_replenishmentQuantity = replenishmentQuantity;
	}

	public void setSkuCode(String skuCode) {
		_skuCode = skuCode;
	}

	public void setStockQuantity(int stockQuantity) {
		_stockQuantity = stockQuantity;
	}

	private int _bookedQuantity;
	private int _replenishmentQuantity;
	private String _skuCode;
	private int _stockQuantity;

}