/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.model;

/**
 * @author Alec Sloan
 */
public class OrderItem {

	public OrderItem(
		int available, Icon icon, long orderId, long orderItemId, int quantity,
		String shippingMethodAndOptionName, String sku) {

		_available = available;
		_icon = icon;
		_orderId = orderId;
		_orderItemId = orderItemId;
		_quantity = quantity;
		_shippingMethodAndOptionName = shippingMethodAndOptionName;
		_sku = sku;
	}

	public int getAvailable() {
		return _available;
	}

	public Icon getIcon() {
		return _icon;
	}

	public long getOrderId() {
		return _orderId;
	}

	public long getOrderItemId() {
		return _orderItemId;
	}

	public int getQuantity() {
		return _quantity;
	}

	public String getShippingMethodAndOptionName() {
		return _shippingMethodAndOptionName;
	}

	public String getSku() {
		return _sku;
	}

	private final int _available;
	private final Icon _icon;
	private final long _orderId;
	private final long _orderItemId;
	private final int _quantity;
	private final String _shippingMethodAndOptionName;
	private final String _sku;

}