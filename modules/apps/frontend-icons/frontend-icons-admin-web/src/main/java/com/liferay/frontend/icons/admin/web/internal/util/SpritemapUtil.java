package com.liferay.frontend.icons.admin.web.internal.util;

import com.liferay.frontend.icons.admin.web.constants.IconConstants;

/**
 * @author Bryce Osterhaus
 */
public class SpritemapUtil {
	public static String getSpritemapPath(String iconPackName) {
		return IconConstants.SPRITEMAP_BASE_PATH + "/" + iconPackName + ".svg";
	}

	public static String getGlobalSpritemapPath() {
		return getSpritemapPath(IconConstants.GLOBAL_ICON_PACK_NAME);
	}
}