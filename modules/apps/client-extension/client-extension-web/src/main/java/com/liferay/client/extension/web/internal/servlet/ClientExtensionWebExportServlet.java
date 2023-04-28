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

package com.liferay.client.extension.web.internal.servlet;

import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.service.ClientExtensionEntryService;
import com.liferay.client.extension.web.internal.constants.ClientExtensionAdminWebConstants;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;
import java.io.PrintWriter;

import java.text.DateFormat;

import java.util.Date;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.client.extension.web.internal.servlet.ClientExtensionWebExportAllServlet",
		"osgi.http.whiteboard.servlet.pattern=/export-client-extensions-entries/*",
		"service.ranking:Integer=" + (Integer.MAX_VALUE - 1000)
	},
	service = Servlet.class
)
public class ClientExtensionWebExportServlet extends HttpServlet {

	@Override
	protected void service(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		try {
			Company company = _portal.getCompany(httpServletRequest);

			DateFormat dateFormat = DateUtil.getISO8601Format();

			jsonObject.put(
				"clientExtensionEntries",
				_getJSONObject(
					_clientExtensionEntryService.getClientExtensionEntries(
						company.getCompanyId(), QueryUtil.ALL_POS,
						QueryUtil.ALL_POS))
			).put(
				"company", _getJSONObject(company)
			).put(
				"exportDate", dateFormat.format(new Date())
			).put(
				"user", _getJSONObject(_portal.getUser(httpServletRequest))
			).put(
				"version", ClientExtensionAdminWebConstants.EXPORT_VERSION
			);
		}
		catch (PortalException portalException) {
			throw new IOException(portalException);
		}

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

		String fileName = "client-extension-entries-export.json";

		httpServletResponse.setHeader(
			"Content-Disposition", "attachment; filename=\"" + fileName + "\"");

		PrintWriter printWriter = httpServletResponse.getWriter();

		printWriter.write(_jsonFactory.looseSerialize(jsonObject));
	}

	private JSONObject _getJSONObject(
		ClientExtensionEntry clientExtensionEntry) {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put(
			"description", clientExtensionEntry.getDescription()
		).put(
			"name", clientExtensionEntry.getName()
		).put(
			"properties", clientExtensionEntry.getProperties()
		).put(
			"sourceCodeURL", clientExtensionEntry.getSourceCodeURL()
		).put(
			"type", clientExtensionEntry.getType()
		).put(
			"typeSettings", clientExtensionEntry.getTypeSettings()
		);

		return jsonObject;
	}

	private JSONObject _getJSONObject(Company company) {
		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put(
			"id", company.getCompanyId()
		).put(
			"name", company.getName()
		).put(
			"virtualHostName", company.getVirtualHostname()
		).put(
			"webId", company.getWebId()
		);

		return jsonObject;
	}

	private JSONObject _getJSONObject(
		List<ClientExtensionEntry> clientExtensionEntries) {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		for (ClientExtensionEntry clientExtensionEntry :
				clientExtensionEntries) {

			jsonObject.put(
				clientExtensionEntry.getExternalReferenceCode(),
				_getJSONObject(clientExtensionEntry));
		}

		return jsonObject;
	}

	private JSONObject _getJSONObject(User user) {
		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put(
			"fullName", user.getFullName()
		).put(
			"id", user.getUserId()
		).put(
			"screenName", user.getScreenName()
		);

		return jsonObject;
	}

	// TODO: we must use DB because CET framework makes transformations to data
	// that may be local to the export machine (eg: it only returns the
	// name in the locale the user has configured).
	// 	@Reference

	//	private CETManager _cetManager;

	@Reference
	private ClientExtensionEntryService _clientExtensionEntryService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}