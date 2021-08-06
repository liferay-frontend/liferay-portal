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
import com.liferay.remote.app.admin.web.internal.CustomElementPortletRegistrar;
import com.liferay.remote.app.admin.web.internal.constants.RemoteAppAdminPortletKeys;
import com.liferay.remote.app.exception.NoSuchEntryException;
import com.liferay.remote.app.model.CustomElementPortletEntry;
import com.liferay.remote.app.service.CustomElementPortletEntryLocalService;
import com.liferay.remote.app.util.CSSURLsParser;
import com.liferay.remote.app.util.TagAttributesParser;

import java.util.Collection;
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

		Collection<String> cssURLs = _cssURLsParser.parse(
			ParamUtil.getString(actionRequest, "cssURLs"));
		boolean instanceable = ParamUtil.getBoolean(
			actionRequest, "instanceable");
		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		String portletDisplayCategory = ParamUtil.getString(
			actionRequest, "portletDisplayCategory");
		Map<String, String> tagAttributes = _tagAttributesParser.parse(
			ParamUtil.getString(actionRequest, "tagAttributes"));
		String tagName = ParamUtil.getString(actionRequest, "tagName");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CustomElementPortletEntry.class.getName(), actionRequest);

		try {
			if (cmd.equals(Constants.ADD)) {
				CustomElementPortletEntry customElementPortletEntry =
					_customElementPortletEntryLocalService.
						addCustomElementPortletEntry(
							serviceContext.getUserId(), cssURLs, instanceable,
							nameMap, portletDisplayCategory, tagAttributes,
							tagName, serviceContext);

				_customElementPortletRegistrar.registerPortlet(
					customElementPortletEntry);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				long customElementPortletEntryId = ParamUtil.getLong(
					actionRequest, "customElementPortletEntryId");

				CustomElementPortletEntry customElementPortletEntry =
					_customElementPortletEntryLocalService.
						updateCustomElementPortletEntry(
							customElementPortletEntryId, cssURLs, instanceable,
							nameMap, portletDisplayCategory, tagAttributes,
							tagName, serviceContext);

				_customElementPortletRegistrar.unregisterPortlet(
					customElementPortletEntry);

				_customElementPortletRegistrar.registerPortlet(
					customElementPortletEntry);
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
	private CSSURLsParser _cssURLsParser;

	@Reference
	private CustomElementPortletEntryLocalService
		_customElementPortletEntryLocalService;

	@Reference
	private CustomElementPortletRegistrar _customElementPortletRegistrar;

	@Reference
	private TagAttributesParser _tagAttributesParser;

}