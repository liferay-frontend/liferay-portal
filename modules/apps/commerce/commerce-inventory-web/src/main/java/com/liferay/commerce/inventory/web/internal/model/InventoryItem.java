/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.web.internal.model;

/**
 * @author Luca Pellizzon
 */
public class InventoryItem {

	public InventoryItem(String sku, int stock, int booked, int incoming) {
		_sku = sku;
		_stock = stock;

		if ((stock > 0) && (booked >= 0)) {
			_available = stock - booked;
		}
		else {
			_available = 0;
		}

		_booked = booked;
		_incoming = incoming;
	}

	public int getAvailable() {
		return _available;
	}

	public int getBooked() {
		return _booked;
	}

	public int getIncoming() {
		return _incoming;
	}

	public String getSku() {
		return _sku;
	}

	public int getStock() {
		return _stock;
	}

	private final int _available;
	private final int _booked;
	private final int _incoming;
	private final String _sku;
	private final int _stock;

}