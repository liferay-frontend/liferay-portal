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

package com.liferay.remote.app.admin.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.remote.app.admin.web.internal.constants.RemoteAppAdminPortletKeys;
import com.liferay.remote.app.exception.NoSuchEntryException;
import com.liferay.remote.app.model.CustomElementPortletEntry;
import com.liferay.remote.app.service.CustomElementPortletEntryLocalService;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + RemoteAppAdminPortletKeys.CUSTOM_ELEMENT_PORTLET_ADMIN,
		"mvc.command.name=/custom_element_portlet_admin/edit_custom_element_portlet_entry"
	},
	service = MVCActionCommand.class
)
public class EditCustomElementPortletEntryMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		String tagName = ParamUtil.getString(actionRequest, "tagName");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CustomElementPortletEntry.class.getName(), actionRequest);

		try {
			if (cmd.equals(Constants.ADD)) {
				_customElementPortletEntryLocalService.
					addCustomElementPortletEntry(
						serviceContext.getUserId(), nameMap, tagName,
						serviceContext);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				long customElementPortletEntryId = ParamUtil.getLong(
					actionRequest, "customElementPortletEntryId");

				_customElementPortletEntryLocalService.
					updateCustomElementPortletEntry(
						customElementPortletEntryId, nameMap, tagName,
						serviceContext);
			}

			if (Validator.isNotNull(redirect)) {
				actionResponse.sendRedirect(redirect);
			}
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchEntryException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter(
					"mvcPath", "/custom_element_portlet_admin/error.jsp");
			}
			else {
				throw exception;
			}
		}
	}

	@Reference
	private CustomElementPortletEntryLocalService
		_customElementPortletEntryLocalService;

}