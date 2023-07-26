/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.model;

/**
 * @author Alessio Antonio Rendina
 * @author Alec Sloan
 */
public class ShipmentItem {

	public ShipmentItem(
		String externalReferenceCode, long orderId, int orderedQuantity,
		long shipmentItemId, int shippedQuantity, String sku,
		int toSendQuantity, String warehouse) {

		_externalReferenceCode = externalReferenceCode;
		_orderId = orderId;
		_orderedQuantity = orderedQuantity;
		_shipmentItemId = shipmentItemId;
		_shippedQuantity = shippedQuantity;
		_sku = sku;
		_toSendQuantity = toSendQuantity;
		_warehouse = warehouse;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public int getOrderedQuantity() {
		return _orderedQuantity;
	}

	public long getOrderId() {
		return _orderId;
	}

	public long getShipmentItemId() {
		return _shipmentItemId;
	}

	public int getShippedQuantity() {
		return _shippedQuantity;
	}

	public String getSku() {
		return _sku;
	}

	public int getToSendQuantity() {
		return _toSendQuantity;
	}

	public String getWarehouse() {
		return _warehouse;
	}

	private final String _externalReferenceCode;
	private final int _orderedQuantity;
	private final long _orderId;
	private final long _shipmentItemId;
	private final int _shippedQuantity;
	private final String _sku;
	private final int _toSendQuantity;
	private final String _warehouse;

}