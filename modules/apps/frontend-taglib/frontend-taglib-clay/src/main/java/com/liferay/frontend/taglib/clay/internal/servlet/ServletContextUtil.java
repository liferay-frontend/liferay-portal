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

package com.liferay.frontend.taglib.clay.internal.servlet;

import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayViewSerializer;
import com.liferay.frontend.taglib.clay.data.set.filter.ClayDataSetFilterSerializer;
import com.liferay.portal.kernel.util.Portal;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Chema Balsas
 */
@Component(service = {})
public class ServletContextUtil {

	public static ClayDataSetDisplayViewSerializer
		getClayDataSetDisplayViewSerializer() {

		return _clayDataSetDisplayViewSerializer;
	}

	public static ClayDataSetFilterSerializer getClayDataSetFilterSerializer() {
		return _clayDataSetFilterSerializer;
	}

	public static String getContextPath() {
		return _servletContext.getContextPath();
	}

	public static ServletContext getServletContext() {
		return _servletContext;
	}

	@Reference(unbind = "-")
	protected void setClayDataSetDisplayViewSerializer(
		ClayDataSetDisplayViewSerializer clayDataSetDisplayViewSerializer) {

		_clayDataSetDisplayViewSerializer = clayDataSetDisplayViewSerializer;
	}

	@Reference(unbind = "-")
	protected void setClayDataSetFilterSerializer(
		ClayDataSetFilterSerializer clayDataSetFilterSerializer) {

		_clayDataSetFilterSerializer = clayDataSetFilterSerializer;
	}

	@Reference(unbind = "-")
	protected void setPortal(Portal portal) {
		_portal = portal;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.frontend.taglib.clay)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private static ClayDataSetDisplayViewSerializer
		_clayDataSetDisplayViewSerializer;
	private static ClayDataSetFilterSerializer _clayDataSetFilterSerializer;
	private static Portal _portal;
	private static ServletContext _servletContext;

}