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

package com.liferay.frontend.icons.admin.web.internal.servlet;

import com.liferay.frontend.icons.admin.web.internal.constants.IconAdminKeys;
import com.liferay.frontend.icons.admin.web.internal.helper.IconResourceHelper;
import com.liferay.portal.kernel.util.ContentTypes;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryce Osterhaus
 */
@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.context.path=/",
		"osgi.http.whiteboard.servlet.pattern=/" + IconAdminKeys.GLOBAL_SPRITE_MAP
	},
	service = Servlet.class
)
public class GlobalSVGServlet extends HttpServlet {

	@Override
	protected void doGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		try {
			httpServletResponse.setCharacterEncoding("UTF-8");
			httpServletResponse.setContentType(ContentTypes.IMAGE_SVG_XML);
			httpServletResponse.setStatus(HttpServletResponse.SC_OK);

			PrintWriter printWriter = httpServletResponse.getWriter();

			printWriter.write(_iconResourceHelper.getGlobalSpriteContent());
		}
		catch (Exception exception) {
			httpServletResponse.setStatus(
				HttpServletResponse.SC_PRECONDITION_FAILED);
		}
	}

	@Reference
	private IconResourceHelper _iconResourceHelper;

}