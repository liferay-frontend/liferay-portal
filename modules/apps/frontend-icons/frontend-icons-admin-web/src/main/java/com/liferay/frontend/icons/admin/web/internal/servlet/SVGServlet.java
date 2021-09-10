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

import com.liferay.frontend.icons.admin.web.internal.helper.IconResourceHelper;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		"osgi.http.whiteboard.servlet.pattern=/icons/*"
	},
	service = Servlet.class
)
public class SVGServlet extends HttpServlet {

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

			String path = httpServletRequest.getPathInfo();

			Matcher matcher = _pattern.matcher(path);

			if (!matcher.matches()) {
				httpServletResponse.setStatus(
					HttpServletResponse.SC_PRECONDITION_FAILED);
			}

			String packName = matcher.group(1);

			Company company = _portal.getCompany(httpServletRequest);

			long groupId = company.getGroupId();

			if (packName.equals("global")) {
				printWriter.write(
					_iconResourceHelper.getGlobalSpriteContent(groupId));
			}
			else {
				printWriter.write(
					_iconResourceHelper.getIconPackSpriteContent(
						groupId, packName));
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			exception.printStackTrace();

			httpServletResponse.setStatus(
				HttpServletResponse.SC_PRECONDITION_FAILED);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(SVGServlet.class);

	private static final Pattern _pattern = Pattern.compile("^/(.*).svg");

	@Reference
	private IconResourceHelper _iconResourceHelper;

	@Reference
	private Portal _portal;

}