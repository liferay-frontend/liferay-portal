/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.web.internal.model;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class BookedQuantity {

	public BookedQuantity(
		String account, long commerceOrderId, int quantity,
		String expirationDate) {

		_account = account;
		_commerceOrderId = commerceOrderId;
		_quantity = quantity;
		_expirationDate = expirationDate;
	}

	public String getAccount() {
		return _account;
	}

	public long getCommerceOrderId() {
		return _commerceOrderId;
	}

	public String getExpirationDate() {
		return _expirationDate;
	}

	public int getQuantity() {
		return _quantity;
	}

	private final String _account;
	private final long _commerceOrderId;
	private final String _expirationDate;
	private final int _quantity;

}