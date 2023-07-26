/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.web.internal.model;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class Warehouse {

	public Warehouse(
		long commerceInventoryWarehouseItemId, String warehouse, int quantity,
		int reserved, long incoming) {

		_commerceInventoryWarehouseItemId = commerceInventoryWarehouseItemId;
		_warehouse = warehouse;
		_quantity = quantity;
		_reserved = reserved;

		if ((quantity > 0) && (reserved >= 0)) {
			_available = quantity - reserved;
		}
		else {
			_available = 0;
		}

		_incoming = incoming;
	}

	public int getAvailable() {
		return _available;
	}

	public long getCommerceInventoryWarehouseItemId() {
		return _commerceInventoryWarehouseItemId;
	}

	public long getIncoming() {
		return _incoming;
	}

	public int getQuantity() {
		return _quantity;
	}

	public int getReserved() {
		return _reserved;
	}

	public String getWarehouse() {
		return _warehouse;
	}

	private final int _available;
	private final long _commerceInventoryWarehouseItemId;
	private final long _incoming;
	private final int _quantity;
	private final int _reserved;
	private final String _warehouse;

}