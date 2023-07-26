/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.web.internal.model;

/**
 * @author Alessio Antonio Rendina
 */
public class TierPriceEntry {

	public TierPriceEntry(
		String discountLevels, String endDate, String override, String price,
		int quantity, String startDate, long tierPriceEntryId) {

		_discountLevels = discountLevels;
		_endDate = endDate;
		_override = override;
		_price = price;
		_quantity = quantity;
		_startDate = startDate;
		_tierPriceEntryId = tierPriceEntryId;
	}

	public String getDiscountLevels() {
		return _discountLevels;
	}

	public String getEndDate() {
		return _endDate;
	}

	public String getOverride() {
		return _override;
	}

	public String getPrice() {
		return _price;
	}

	public int getQuantity() {
		return _quantity;
	}

	public String getStartDate() {
		return _startDate;
	}

	public long getTierPriceEntryId() {
		return _tierPriceEntryId;
	}

	private final String _discountLevels;
	private final String _endDate;
	private final String _override;
	private final String _price;
	private final int _quantity;
	private final String _startDate;
	private final long _tierPriceEntryId;

}