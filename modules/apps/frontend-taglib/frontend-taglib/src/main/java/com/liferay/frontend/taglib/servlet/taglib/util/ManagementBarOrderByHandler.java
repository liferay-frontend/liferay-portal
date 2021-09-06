/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.frontend.taglib.servlet.taglib.util;

import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mariano Álvaro Sáiz
 */
public class ManagementBarOrderByHandler {

	public ManagementBarOrderByHandler(
		HttpServletRequest httpServletRequest, String namespace) {

		_httpServletRequest = httpServletRequest;
		_namespace = namespace;

		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			httpServletRequest);
	}

	public String getOrderByCol(String defaultOrderByCol) {
		return getOrderByCol("order-by-col", defaultOrderByCol);
	}

	public String getOrderByCol(
		String orderByColName, String defaultOrderByCol) {

		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		String orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol");

		if (Validator.isNotNull(orderByCol)) {
			_portalPreferences.setValue(_namespace, orderByColName, orderByCol);
		}
		else {
			orderByCol = _portalPreferences.getValue(
				_namespace, orderByColName, defaultOrderByCol);
		}

		_orderByCol = orderByCol;

		return _orderByCol;
	}

	public String getOrderByType(String defaultOrderByType) {
		return getOrderByType("order-by-type", defaultOrderByType);
	}

	public String getOrderByType(
		String orderByTypeName, String defaultOrderByType) {

		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		String orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType");

		if (Validator.isNotNull(orderByType)) {
			_portalPreferences.setValue(
				_namespace, orderByTypeName, orderByType);
		}
		else {
			orderByType = _portalPreferences.getValue(
				_namespace, orderByTypeName, defaultOrderByType);
		}

		_orderByType = orderByType;

		return _orderByType;
	}

	private final HttpServletRequest _httpServletRequest;
	private final String _namespace;
	private String _orderByCol;
	private String _orderByType;
	private final PortalPreferences _portalPreferences;

}