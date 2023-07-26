/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.product.option.CommerceOptionValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Riccardo Alberti
 */
public class CommerceProductPriceRequest {

	public CommerceContext getCommerceContext() {
		return _commerceContext;
	}

	public List<CommerceOptionValue> getCommerceOptionValues() {
		return _commerceOptionValues;
	}

	public long getCpInstanceId() {
		return _cpInstanceId;
	}

	public int getQuantity() {
		return _quantity;
	}

	public boolean isCalculateTax() {
		return _calculateTax;
	}

	public boolean isSecure() {
		return _secure;
	}

	public void setCalculateTax(boolean calculateTax) {
		_calculateTax = calculateTax;
	}

	public void setCommerceContext(CommerceContext commerceContext) {
		_commerceContext = commerceContext;
	}

	public void setCommerceOptionValues(
		List<CommerceOptionValue> commerceOptionValues) {

		Objects.requireNonNull(commerceOptionValues);

		_commerceOptionValues = new ArrayList<>(commerceOptionValues);
	}

	public void setCpInstanceId(long cpInstanceId) {
		_cpInstanceId = cpInstanceId;
	}

	public void setQuantity(int quantity) {
		_quantity = quantity;
	}

	public void setSecure(boolean secure) {
		_secure = secure;
	}

	private boolean _calculateTax;
	private CommerceContext _commerceContext;
	private List<CommerceOptionValue> _commerceOptionValues =
		Collections.emptyList();
	private long _cpInstanceId;
	private int _quantity;
	private boolean _secure;

}