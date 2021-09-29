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

package com.liferay.frontend.icons.admin.web.internal.servlet.taglib;

import com.liferay.frontend.icons.constants.IconConstants;
import com.liferay.frontend.icons.util.IconsUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.util.ContentTypes;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bryce Osterhaus
 */
@Component(
	immediate = true, property = "service.ranking:Integer=" + Integer.MAX_VALUE,
	service = DynamicInclude.class
)
public class IconsTopHeadDynamicInclude extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		PrintWriter printWriter = httpServletResponse.getWriter();

		printWriter.write(
			"<script data-senna-track=\"permanent\" type=\"" +
				ContentTypes.TEXT_JAVASCRIPT + "\">");
		printWriter.write("var Liferay = Liferay || {};");
		printWriter.write("if (!Liferay.Icons) {");
		printWriter.write("Liferay.Icons = {");
		printWriter.write(
			"basePath: '" + IconConstants.SPRITEMAP_BASE_PATH + "',");
		printWriter.write(
			"getGlobalSpritemapPath: () => '" +
				IconsUtil.getGlobalSpritemapPath() + "',");
		printWriter.write(
			"getSpritemapPath: (iconPackName) => '" +
				IconConstants.SPRITEMAP_BASE_PATH +
					"' + '/' + iconPackName + '.svg',");
		printWriter.write("};");
		printWriter.write("}");
		printWriter.write("</script>");
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/html/common/themes/top_js.jspf#resources");
	}

}