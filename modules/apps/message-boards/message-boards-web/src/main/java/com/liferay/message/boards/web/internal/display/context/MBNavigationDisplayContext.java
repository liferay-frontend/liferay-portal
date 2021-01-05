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

package com.liferay.message.boards.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.message.boards.constants.MBPortletKeys;
import com.liferay.message.boards.settings.MBGroupServiceSettings;
import com.liferay.message.boards.web.internal.security.permission.MBResourcePermission;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class MBNavigationDisplayContext {

	public MBNavigationDisplayContext(
		HttpServletRequest httpServletRequest,
		MBGroupServiceSettings mbGroupServiceSettings,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_mbGroupServiceSettings = mbGroupServiceSettings;
		_renderResponse = renderResponse;

		_mvcRenderCommandName = ParamUtil.getString(
			_httpServletRequest, "mvcRenderCommandName",
			"/message_boards/view");
		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<NavigationItem> getNavigationItems() {
		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(
					_isViewCategoriesNavigationItemActive());
				navigationItem.setHref(_getViewCategoriesPortletURL());

				if (_isMBPortlet()) {
					navigationItem.setLabel(
						LanguageUtil.get(_httpServletRequest, "categories"));
				}
				else {
					navigationItem.setLabel(
						LanguageUtil.get(_httpServletRequest, "content"));
				}
			}
		).add(
			this::_isMBPortlet,
			navigationItem -> {
				navigationItem.setActive(
					_isViewRecentPostsNavigationItemActive());
				navigationItem.setHref(_getViewRecentPostsPortletURL());
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "recent-posts"));
			}
		).add(
			() -> _isMBPortlet() && _themeDisplay.isSignedIn(),
			navigationItem -> {
				navigationItem.setActive(_isViewMyPostsNavigationItemActive());
				navigationItem.setHref(_getViewMyPostsPortletURL());
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "my-posts"));
			}
		).add(
			() ->
				_isMBPortlet() && _themeDisplay.isSignedIn() &&
				(_mbGroupServiceSettings.isEmailMessageAddedEnabled() ||
				 _mbGroupServiceSettings.isEmailMessageUpdatedEnabled()),
			navigationItem -> {
				navigationItem.setActive(
					_isViewMySubscriptionsNavigationItemActive());
				navigationItem.setHref(_getViewMySubscriptionsPortletURL());
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "my-subscriptions"));
			}
		).add(
			navigationItem -> {
				navigationItem.setActive(
					_isViewStatisticsNavigationItemActive());
				navigationItem.setHref(_getViewStatisticsPortletURL());
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "statistics"));
			}
		).add(
			() ->
				!_isMBPortlet() &&
				MBResourcePermission.contains(
					_themeDisplay.getPermissionChecker(),
					_themeDisplay.getScopeGroupId(), ActionKeys.BAN_USER),
			navigationItem -> {
				navigationItem.setActive(
					_isViewBannedUsersNavigationItemActive());
				navigationItem.setHref(_getViewBannedUsersPortletURL());
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "banned-users"));
			}
		).build();
	}

	public boolean isInverted() {
		return !_isMBPortlet();
	}

	public boolean isShowAlert() {
		return _isMBPortlet();
	}

	private PortletURL _getViewBannedUsersPortletURL() {
		PortletURL bannedUsersURL = _renderResponse.createRenderURL();

		bannedUsersURL.setParameter(
			"mvcRenderCommandName", "/message_boards_admin/view_banned_users");

		return bannedUsersURL;
	}

	private PortletURL _getViewCategoriesPortletURL() {
		PortletURL messageBoardsHomeURL = _renderResponse.createRenderURL();

		messageBoardsHomeURL.setParameter(
			"mvcRenderCommandName", "/message_boards/view");
		messageBoardsHomeURL.setParameter("tag", StringPool.BLANK);

		return messageBoardsHomeURL;
	}

	private PortletURL _getViewMyPostsPortletURL() {
		PortletURL viewMyPostsURL = _renderResponse.createRenderURL();

		viewMyPostsURL.setParameter(
			"mvcRenderCommandName", "/message_boards/view_my_posts");

		return viewMyPostsURL;
	}

	private PortletURL _getViewMySubscriptionsPortletURL() {
		PortletURL viewMySubscriptionsURL = _renderResponse.createRenderURL();

		viewMySubscriptionsURL.setParameter(
			"mvcRenderCommandName", "/message_boards/view_my_subscriptions");

		return viewMySubscriptionsURL;
	}

	private PortletURL _getViewRecentPostsPortletURL() {
		PortletURL viewRecentPostsURL = _renderResponse.createRenderURL();

		viewRecentPostsURL.setParameter(
			"mvcRenderCommandName", "/message_boards/view_recent_posts");

		return viewRecentPostsURL;
	}

	private PortletURL _getViewStatisticsPortletURL() {
		PortletURL viewStatisticsURL = _renderResponse.createRenderURL();

		viewStatisticsURL.setParameter(
			"mvcRenderCommandName", "/message_boards/view_statistics");

		return viewStatisticsURL;
	}

	private boolean _isMBPortlet() {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		if (Objects.equals(
				portletDisplay.getPortletName(),
				MBPortletKeys.MESSAGE_BOARDS)) {

			return true;
		}

		return false;
	}

	private boolean _isViewBannedUsersNavigationItemActive() {
		if (_mvcRenderCommandName.equals(
				"/message_boards_admin/view_banned_users")) {

			return true;
		}

		return false;
	}

	private boolean _isViewCategoriesNavigationItemActive() {
		if (_mvcRenderCommandName.equals("/message_boards/edit_category") ||
			_mvcRenderCommandName.equals("/message_boards/edit_message") ||
			_mvcRenderCommandName.equals("/message_boards/view") ||
			_mvcRenderCommandName.equals("/message_boards/view_category") ||
			_mvcRenderCommandName.equals("/message_boards/view_message")) {

			return true;
		}

		return false;
	}

	private boolean _isViewMyPostsNavigationItemActive() {
		if (_mvcRenderCommandName.equals("/message_boards/view_my_posts")) {
			return true;
		}

		return false;
	}

	private boolean _isViewMySubscriptionsNavigationItemActive() {
		if (_mvcRenderCommandName.equals(
				"/message_boards/view_my_subscriptions")) {

			return true;
		}

		return false;
	}

	private boolean _isViewRecentPostsNavigationItemActive() {
		if (_mvcRenderCommandName.equals("/message_boards/view_recent_posts")) {
			return true;
		}

		return false;
	}

	private boolean _isViewStatisticsNavigationItemActive() {
		if (_mvcRenderCommandName.equals("/message_boards/view_statistics")) {
			return true;
		}

		return false;
	}

	private final HttpServletRequest _httpServletRequest;
	private final MBGroupServiceSettings _mbGroupServiceSettings;
	private final String _mvcRenderCommandName;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}