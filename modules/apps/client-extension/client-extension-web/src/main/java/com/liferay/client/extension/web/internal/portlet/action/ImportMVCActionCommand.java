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

package com.liferay.client.extension.web.internal.portlet.action;

import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.service.ClientExtensionEntryService;
import com.liferay.client.extension.type.factory.CETFactory;
import com.liferay.client.extension.web.internal.constants.ClientExtensionAdminPortletKeys;
import com.liferay.client.extension.web.internal.constants.ClientExtensionAdminWebConstants;
import com.liferay.client.extension.web.internal.constants.ClientExtensionAdminWebKeys;
import com.liferay.client.extension.web.internal.display.context.ClientExtensionAdminDisplayContext;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilderFactory;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.Part;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(
	property = {
		"javax.portlet.name=" + ClientExtensionAdminPortletKeys.CLIENT_EXTENSION_ADMIN,
		"mvc.command.name=/client_extension_admin/import"
	},
	service = MVCActionCommand.class
)
public class ImportMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			for (Part part : actionRequest.getParts()) {
				JSONObject jsonObject = _jsonFactory.createJSONObject(
					StringUtil.read(part.getInputStream()));

				if (jsonObject.getInt("version", -1) !=
						ClientExtensionAdminWebConstants.EXPORT_VERSION) {

					throw new JSONException("Invalid version number");
				}

				JSONObject clientExtensionEntriesJSONObject =
					jsonObject.getJSONObject("clientExtensionEntries");

				for (String externalReferenceCode :
						clientExtensionEntriesJSONObject.keySet()) {

					ClientExtensionEntry clientExtensionEntry =
						_clientExtensionEntryService.
							fetchClientExtensionEntryByExternalReferenceCode(
								_portal.getCompanyId(actionRequest),
								externalReferenceCode);

					jsonObject = clientExtensionEntriesJSONObject.getJSONObject(
						externalReferenceCode);

					if (clientExtensionEntry == null) {
						_clientExtensionEntryService.addClientExtensionEntry(
							externalReferenceCode,
							jsonObject.getString("description"),
							_localization.getLocalizationMap(
								jsonObject.getString("name")),
							jsonObject.getString("properties"),
							jsonObject.getString("sourceCodeURL"),
							jsonObject.getString("type"),
							jsonObject.getString("typeSettings"));
					}
					else {
						_clientExtensionEntryService.updateClientExtensionEntry(
							clientExtensionEntry.getClientExtensionEntryId(),
							jsonObject.getString("description"),
							_localization.getLocalizationMap(
								jsonObject.getString("name")),
							jsonObject.getString("properties"),
							jsonObject.getString("sourceCodeURL"),
							jsonObject.getString("typeSettings"));
					}
				}
			}

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			if (Validator.isNotNull(redirect)) {
				actionResponse.sendRedirect(redirect);
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			SessionErrors.add(actionRequest, exception.getClass(), exception);

			actionRequest.setAttribute(
				ClientExtensionAdminWebKeys.
					CLIENT_EXTENSION_ADMIN_DISPLAY_CONTEXT,
				new ClientExtensionAdminDisplayContext(
					_absolutePortalURLBuilderFactory, _cetFactory,
					actionRequest, actionResponse));

			actionResponse.setRenderParameter("mvcPath", "/admin/import.jsp");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImportMVCActionCommand.class);

	@Reference
	private AbsolutePortalURLBuilderFactory _absolutePortalURLBuilderFactory;

	@Reference
	private CETFactory _cetFactory;

	@Reference
	private ClientExtensionEntryService _clientExtensionEntryService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Localization _localization;

	@Reference
	private Portal _portal;

}