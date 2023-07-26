/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.theme;

import com.liferay.portal.kernel.model.PortletDecorator;

/**
 * @author Eduardo García
 */
public interface PortletDecoratorFactory {

	public PortletDecorator getDefaultPortletDecorator();

	public String getDefaultPortletDecoratorCssClass();

	public String getDefaultPortletDecoratorId();

	public PortletDecorator getPortletDecorator();

	public PortletDecorator getPortletDecorator(String portletDecoratorId);

	public PortletDecorator getPortletDecorator(
		String portletDecoratorId, String name, String cssClass);

}