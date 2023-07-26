/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.model.ColorScheme;

/**
 * @author Vilmos Papp
 */
public class ColorSchemeFactoryUtil {

	public static ColorScheme getColorScheme() {
		return _colorSchemeFactory.getColorScheme();
	}

	public static ColorScheme getColorScheme(String colorSchemeId) {
		return _colorSchemeFactory.getColorScheme(colorSchemeId);
	}

	public static ColorScheme getColorScheme(
		String colorSchemeId, String name, String cssClass) {

		return _colorSchemeFactory.getColorScheme(
			colorSchemeId, name, cssClass);
	}

	public static ColorSchemeFactory getColorSchemeFactory() {
		return _colorSchemeFactory;
	}

	public static ColorScheme getDefaultRegularColorScheme() {
		return _colorSchemeFactory.getDefaultRegularColorScheme();
	}

	public static String getDefaultRegularColorSchemeId() {
		return _colorSchemeFactory.getDefaultRegularColorSchemeId();
	}

	public void setColorSchemeFactory(ColorSchemeFactory colorSchemeFactory) {
		_colorSchemeFactory = colorSchemeFactory;
	}

	private static ColorSchemeFactory _colorSchemeFactory;

}