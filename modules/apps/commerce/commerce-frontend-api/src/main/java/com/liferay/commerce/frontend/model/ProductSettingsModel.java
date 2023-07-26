/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.model;

/**
 * @author Marco Leo
 */
public class ProductSettingsModel {

	public int[] getAllowedQuantities() {
		return _allowedQuantities;
	}

	public int getLowStockQuantity() {
		return _lowStockQuantity;
	}

	public int getMaxQuantity() {
		return _maxQuantity;
	}

	public int getMinQuantity() {
		return _minQuantity;
	}

	public int getMultipleQuantity() {
		return _multipleQuantity;
	}

	public boolean isBackOrders() {
		return _backOrders;
	}

	public boolean isShowAvailabilityDot() {
		return _showAvailabilityDot;
	}

	public void setAllowedQuantities(int[] allowedQuantities) {
		_allowedQuantities = allowedQuantities;
	}

	public void setBackOrders(boolean backOrders) {
		_backOrders = backOrders;
	}

	public void setLowStockQuantity(int lowStockQuantity) {
		_lowStockQuantity = lowStockQuantity;
	}

	public void setMaxQuantity(int maxQuantity) {
		_maxQuantity = maxQuantity;
	}

	public void setMinQuantity(int minQuantity) {
		_minQuantity = minQuantity;
	}

	public void setMultipleQuantity(int multipleQuantity) {
		_multipleQuantity = multipleQuantity;
	}

	public void setShowAvailabilityDot(boolean showAvailabilityDot) {
		_showAvailabilityDot = showAvailabilityDot;
	}

	private int[] _allowedQuantities;
	private boolean _backOrders;
	private int _lowStockQuantity;
	private int _maxQuantity;
	private int _minQuantity;
	private int _multipleQuantity;
	private boolean _showAvailabilityDot;

}