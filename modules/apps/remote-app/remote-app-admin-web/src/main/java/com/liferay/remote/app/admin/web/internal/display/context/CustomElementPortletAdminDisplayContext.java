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

package com.liferay.remote.app.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.remote.app.model.RemoteCustomElementEntry;
import com.liferay.remote.app.service.CustomElementPortletEntryLocalService;
import com.liferay.remote.app.service.RemoteCustomElementEntryLocalService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class CustomElementPortletAdminDisplayContext {

	public CustomElementPortletAdminDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		CustomElementPortletEntryLocalService
			customElementPortletEntryLocalService,
		RemoteCustomElementEntryLocalService
			remoteCustomElementEntryLocalService) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_remoteCustomElementEntryLocalService =
			remoteCustomElementEntryLocalService;
	}

	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.createRenderURL(
						_renderResponse
					).setMVCRenderCommandName(
						"/custom_element_portlet_admin" +
							"/edit_custom_element_portlet_entry"
					).setRedirect(
						_getRedirect()
					).build());

				dropdownItem.setLabel(_getLabel("new-custom-element-portlet"));
			}
		).build();
	}

	public PortletURL getCurrentPortletURL() {
		return PortletURLUtil.getCurrent(_renderRequest, _renderResponse);
	}

	public Collection<String> getCustomElementTagNames() {
		List<RemoteCustomElementEntry> remoteCustomElementEntries =
			_remoteCustomElementEntryLocalService.
				getRemoteCustomElementEntries(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Stream<RemoteCustomElementEntry> stream =
			remoteCustomElementEntries.stream();

		return stream.map(
			remoteCustomElementEntry -> remoteCustomElementEntry.getTagName()
		).sorted(
		).collect(
			Collectors.toList()
		);
	}

	private HttpServletRequest _getHttpServletRequest() {
		return PortalUtil.getHttpServletRequest(_renderRequest);
	}

	private String _getLabel(String label) {
		return LanguageUtil.get(_getHttpServletRequest(), label);
	}

	private String _getRedirect() {
		return PortalUtil.getCurrentURL(_getHttpServletRequest());
	}

	private final RemoteCustomElementEntryLocalService
		_remoteCustomElementEntryLocalService;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}