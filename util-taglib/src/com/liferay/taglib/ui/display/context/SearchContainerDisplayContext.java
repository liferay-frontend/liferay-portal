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

package com.liferay.taglib.ui.display.context;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.SessionClicks;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Marko Cikos
 */
public class SearchContainerDisplayContext {

	public static final String DEFAULT_DISPLAY_STYLE = "list";

	public SearchContainerDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<?> searchContainer) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_searchContainer = searchContainer;
	}

	public String getDisplayStyle(String instanceDefaultDisplayStyle) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		StringBundler sb = new StringBundler(5);

		sb.append(themeDisplay.getScopeGroupId());
		sb.append(StringPool.COLON);

		String portletId = (String)_httpServletRequest.getAttribute(
			WebKeys.PORTLET_ID);

		sb.append(portletId);

		sb.append(StringPool.COLON);

		String searchContainerId = _searchContainer.getId(
			_httpServletRequest, _liferayPortletResponse.getNamespace());

		sb.append(searchContainerId);

		String namespace = sb.toString();

		String displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle");

		HttpSession session = _httpServletRequest.getSession();

		if (Validator.isNull(displayStyle)) {
			displayStyle = SessionClicks.get(
				session, namespace, "displayStyle",
				Validator.isNotNull(instanceDefaultDisplayStyle) ?
					instanceDefaultDisplayStyle : DEFAULT_DISPLAY_STYLE);
		}

		SessionClicks.put(session, namespace, "displayStyle", displayStyle);

		return displayStyle;
	}

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final SearchContainer<?> _searchContainer;

}