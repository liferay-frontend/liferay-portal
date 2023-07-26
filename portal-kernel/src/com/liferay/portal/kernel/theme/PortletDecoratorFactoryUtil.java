/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.theme;

import com.liferay.portal.kernel.model.PortletDecorator;

/**
 * @author Eduardo García
 */
public class PortletDecoratorFactoryUtil {

	public static PortletDecorator getDefaultPortletDecorator() {
		return _portletDecoratorFactory.getDefaultPortletDecorator();
	}

	public static String getDefaultPortletDecoratorCssClass() {
		return _portletDecoratorFactory.getDefaultPortletDecoratorCssClass();
	}

	public static String getDefaultPortletDecoratorId() {
		return _portletDecoratorFactory.getDefaultPortletDecoratorId();
	}

	public static PortletDecorator getPortletDecorator() {
		return _portletDecoratorFactory.getPortletDecorator();
	}

	public static PortletDecorator getPortletDecorator(
		String portletDecoratorId) {

		return _portletDecoratorFactory.getPortletDecorator(portletDecoratorId);
	}

	public static PortletDecorator getPortletDecorator(
		String portletDecoratorId, String name, String cssClass) {

		return _portletDecoratorFactory.getPortletDecorator(
			portletDecoratorId, name, cssClass);
	}

	public static PortletDecoratorFactory getPortletDecoratorFactory() {
		return _portletDecoratorFactory;
	}

	public void setPortletDecoratorFactory(
		PortletDecoratorFactory portletDecoratorFactory) {

		_portletDecoratorFactory = portletDecoratorFactory;
	}

	private static PortletDecoratorFactory _portletDecoratorFactory;

}