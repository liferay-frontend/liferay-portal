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

package com.liferay.users.admin.item.selector.web.internal.display.context;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.usersadmin.search.UserSearch;
import com.liferay.portlet.usersadmin.search.UserSearchTerms;
import com.liferay.users.admin.item.selector.UserOrganizationItemSelectorCriterion;
import com.liferay.users.admin.item.selector.web.internal.search.UserOrganizationChecker;

import java.util.LinkedHashMap;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class UserOrganizationItemSelectorViewDisplayContext {

	public UserOrganizationItemSelectorViewDisplayContext(
		HttpServletRequest httpServletRequest, PortletURL portletURL,
		RenderRequest renderRequest, RenderResponse renderResponse,
		UserOrganizationItemSelectorCriterion
			userOrganizationItemSelectorCriterion) {

		_httpServletRequest = httpServletRequest;
		_portletURL = portletURL;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_userOrganizationItemSelectorCriterion =
			userOrganizationItemSelectorCriterion;
	}

	public SearchContainer<User> getSearchContainer() {
		if (_userSearch != null) {
			return _userSearch;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		LinkedHashMap<String, Object> userParams = new LinkedHashMap<>();

		if (PropsValues.ORGANIZATIONS_ASSIGNMENT_STRICT &&
			!permissionChecker.isCompanyAdmin() &&
			!permissionChecker.hasPermission(
				themeDisplay.getScopeGroup(), User.class.getName(),
				User.class.getName(), ActionKeys.VIEW)) {

			User user = themeDisplay.getUser();

			try {
				userParams.put("usersOrgsTree", user.getOrganizations(true));
			}
			catch (PortalException portalException) {
				_log.error(portalException);
			}
		}

		UserSearch userSearch = new UserSearch(_renderRequest, _portletURL);

		UserSearchTerms searchTerms =
			(UserSearchTerms)userSearch.getSearchTerms();

		userSearch.setResultsAndTotal(
			() -> UserLocalServiceUtil.search(
				themeDisplay.getCompanyId(), searchTerms.getKeywords(),
				searchTerms.getStatus(), userParams, userSearch.getStart(),
				userSearch.getEnd(), userSearch.getOrderByComparator()),
			UserLocalServiceUtil.searchCount(
				themeDisplay.getCompanyId(), searchTerms.getKeywords(),
				searchTerms.getStatus(), userParams));

		userSearch.setRowChecker(
			new UserOrganizationChecker(
				_renderResponse,
				_userOrganizationItemSelectorCriterion.getOrganizationId()));

		_userSearch = userSearch;

		return _userSearch;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserOrganizationItemSelectorViewDisplayContext.class);

	private final HttpServletRequest _httpServletRequest;
	private final PortletURL _portletURL;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final UserOrganizationItemSelectorCriterion
		_userOrganizationItemSelectorCriterion;
	private UserSearch _userSearch;

}