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

package com.liferay.frontend.icons.util;

import com.liferay.frontend.icons.constants.IconConstants;

/**
 * @author Bryce Osterhaus
 */
public class IconsUtil {

	public static String getGlobalSpritemapPath() {
		return getSpritemapPath(IconConstants.GLOBAL_ICON_PACK_NAME);
	}

	public static String getSpritemapPath(String iconPackName) {
		return IconConstants.SPRITEMAP_BASE_PATH + "/" + iconPackName + ".svg";
	}

}