/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.server.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class ServerDisplayContext {

	public ServerDisplayContext(
		HttpServletRequest httpServletRequest, RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderResponse = renderResponse;
	}

	public List<NavigationItem> getServerNavigationItems() {
		String tabs1 = ParamUtil.getString(
			_httpServletRequest, "tabs1", "resources");
		String tabs2 = ParamUtil.getString(_httpServletRequest, "tabs2");

		return new NavigationItemList() {
			{
				for (String tabs1Name : _TABS1_NAMES) {
					add(
						navigationItem -> {
							navigationItem.setActive(tabs1.equals(tabs1Name));
							navigationItem.setHref(
								_renderResponse.createRenderURL(),
								"mvcRenderCommandName", "/server_admin/view",
								"tabs1", tabs1Name, "tabs2", tabs2);
							navigationItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, tabs1Name));
						});
				}
			}
		};
	}

	private static final String[] _TABS1_NAMES = {
		"resources", "log-levels", "properties", "data-migration", "mail",
		"external-services", "script", "shutdown"
	};

	private final HttpServletRequest _httpServletRequest;
	private final RenderResponse _renderResponse;

}