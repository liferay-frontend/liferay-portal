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

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.remote.app.admin.web.internal.constants.RemoteAppAdminPortletKeys;
import com.liferay.remote.app.admin.web.internal.constants.RemoteAppAdminWebKeys;
import com.liferay.remote.app.admin.web.internal.display.context.CustomElementPortletAdminDisplayContext;
import com.liferay.remote.app.service.CustomElementPortletEntryLocalService;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.remote.app.service.RemoteCustomElementEntryLocalService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + RemoteAppAdminPortletKeys.CUSTOM_ELEMENT_PORTLET_ADMIN,
		"mvc.command.name=/"
	},
	service = MVCRenderCommand.class
)
public class CustomElementPortletAdminViewMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		renderRequest.setAttribute(
			RemoteAppAdminWebKeys.CUSTOM_ELEMENT_PORTLET_ADMIN_DISPLAY_CONTEXT,
			new CustomElementPortletAdminDisplayContext(
				renderRequest, renderResponse,
				_customElementPortletEntryLocalService,
				_remoteCustomElementEntryLocalService
				));

		return "/custom_element_portlet_admin/view.jsp";
	}

	@Reference
	private CustomElementPortletEntryLocalService
		_customElementPortletEntryLocalService;

	@Reference
	private RemoteCustomElementEntryLocalService
		_remoteCustomElementEntryLocalService;

}