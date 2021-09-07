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

	public String getOrderByCol() {
		return getOrderByCol("order-by-col");
	}

	public String getOrderByCol(String orderByColName) {
		return getOrderByCol(
			orderByColName,
			ParamUtil.getString(_httpServletRequest, "orderByCol"));
	}

	public String getOrderByCol(String orderByColName, String orderByColValue) {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		String orderByCol = orderByColValue;

		if (Validator.isNotNull(orderByCol)) {
			_portalPreferences.setValue(_namespace, orderByColName, orderByCol);
		}
		else {
			orderByCol = _portalPreferences.getValue(
				_namespace, orderByColName, _defaultOrderByCol);
		}

		_orderByCol = orderByCol;

		return _orderByCol;
	}

	public String getOrderByType() {
		return getOrderByType("order-by-type");
	}

	public String getOrderByType(String orderByTypeName) {
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
				_namespace, orderByTypeName, _defaultOrderByType);
		}

		_orderByType = orderByType;

		return _orderByType;
	}

	public static class ManagementBarOrderByHandlerBuilder {

		public ManagementBarOrderByHandlerBuilder(
			HttpServletRequest httpServletRequest, String namespace) {

			_httpServletRequest = httpServletRequest;
			_namespace = namespace;
		}

		public ManagementBarOrderByHandler build() {
			ManagementBarOrderByHandler managementBarOrderByHandler =
				new ManagementBarOrderByHandler(
					_httpServletRequest, _namespace);

			managementBarOrderByHandler._defaultOrderByCol = _defaultOrderByCol;
			managementBarOrderByHandler._defaultOrderByType =
				_defaultOrderByType;

			return managementBarOrderByHandler;
		}

		public ManagementBarOrderByHandlerBuilder defaultOrderByCol(
			String defaultOrderByCol) {

			_defaultOrderByCol = defaultOrderByCol;

			return this;
		}

		public ManagementBarOrderByHandlerBuilder defaultOrderByType(
			String defaultOrderByType) {

			_defaultOrderByType = defaultOrderByType;

			return this;
		}

		private String _defaultOrderByCol;
		private String _defaultOrderByType;
		private HttpServletRequest _httpServletRequest;
		private String _namespace;

	}

	private ManagementBarOrderByHandler(
		HttpServletRequest httpServletRequest, String namespace) {

		_httpServletRequest = httpServletRequest;
		_namespace = namespace;

		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			httpServletRequest);
	}

	private String _defaultOrderByCol;
	private String _defaultOrderByType;
	private final HttpServletRequest _httpServletRequest;
	private final String _namespace;
	private String _orderByCol;
	private String _orderByType;
	private final PortalPreferences _portalPreferences;

}