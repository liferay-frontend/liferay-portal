/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.servlet;

import javax.servlet.Servlet;

/**
 * @author Shuyang Zhou
 */
public class DirectServletRegistryUtil {

	public static void clearServlets() {
		_directServletRegistry.clearServlets();
	}

	public static DirectServletRegistry getDirectServletRegistry() {
		return _directServletRegistry;
	}

	public static Servlet getServlet(String path) {
		return _directServletRegistry.getServlet(path);
	}

	public static void putServlet(String path, Servlet servlet) {
		_directServletRegistry.putServlet(path, servlet);
	}

	public void setDirectServletRegistry(
		DirectServletRegistry directServletRegistry) {

		_directServletRegistry = directServletRegistry;
	}

	private static DirectServletRegistry _directServletRegistry;

}