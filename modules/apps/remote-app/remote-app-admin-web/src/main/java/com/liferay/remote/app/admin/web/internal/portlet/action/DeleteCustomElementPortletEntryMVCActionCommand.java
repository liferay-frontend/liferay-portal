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
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.remote.app.admin.web.internal.CustomElementPortletRegistrar;
import com.liferay.remote.app.admin.web.internal.constants.RemoteAppAdminPortletKeys;
import com.liferay.remote.app.exception.NoSuchEntryException;
import com.liferay.remote.app.model.CustomElementPortletEntry;
import com.liferay.remote.app.service.CustomElementPortletEntryLocalService;

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
		"mvc.command.name=/custom_element_portlet_admin/delete_custom_element_portlet_entry"
	},
	service = MVCActionCommand.class
)
public class DeleteCustomElementPortletEntryMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		long customElementPortletEntryId = ParamUtil.getLong(
			actionRequest, "customElementPortletEntryId");

		try {
			CustomElementPortletEntry customElementPortletEntry =
				_customElementPortletEntryLocalService.
					deleteCustomElementPortletEntry(
						customElementPortletEntryId);

			_customElementPortletRegistrar.unregisterPortlet(
				customElementPortletEntry);

			if (Validator.isNotNull(redirect)) {
				actionResponse.sendRedirect(redirect);
			}
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchEntryException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());
			}
			else {
				throw exception;
			}
		}
	}

	@Reference
	private CustomElementPortletEntryLocalService
		_customElementPortletEntryLocalService;

	@Reference
	private CustomElementPortletRegistrar _customElementPortletRegistrar;

}