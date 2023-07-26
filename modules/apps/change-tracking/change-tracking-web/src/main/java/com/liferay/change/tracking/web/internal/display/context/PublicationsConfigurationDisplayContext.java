/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.display.context;

import com.liferay.change.tracking.configuration.CTSettingsConfiguration;
import com.liferay.change.tracking.web.internal.configuration.helper.CTSettingsConfigurationHelper;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Máté Thurzó
 * @author Samuel Trong Tran
 */
public class PublicationsConfigurationDisplayContext {

	public PublicationsConfigurationDisplayContext(
		CTSettingsConfigurationHelper ctSettingsConfigurationHelper,
		HttpServletRequest httpServletRequest, RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderResponse = renderResponse;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		CTSettingsConfiguration ctSettingsConfiguration =
			ctSettingsConfigurationHelper.getCTSettingsConfiguration(
				themeDisplay.getCompanyId());

		_publicationsEnabled = ctSettingsConfiguration.enabled();
		_sandboxOnlyEnabled = ctSettingsConfiguration.sandboxEnabled();
		_unapprovedChangesAllowed =
			ctSettingsConfiguration.unapprovedChangesAllowed();
	}

	public String getActionURL() {
		return PortletURLBuilder.createActionURL(
			_renderResponse
		).setActionName(
			"/change_tracking/update_global_publications_configuration"
		).buildString();
	}

	public String getNavigation() {
		if (_navigation != null) {
			return _navigation;
		}

		if (isPublicationsEnabled()) {
			_navigation = ParamUtil.getString(
				_httpServletRequest, "navigation", "global-settings");
		}
		else {
			_navigation = "global-settings";
		}

		return _navigation;
	}

	public boolean isPublicationsEnabled() {
		return _publicationsEnabled;
	}

	public boolean isSandboxOnlyEnabled() {
		return _sandboxOnlyEnabled;
	}

	public boolean isUnapprovedChangesAllowed() {
		return _unapprovedChangesAllowed;
	}

	private final HttpServletRequest _httpServletRequest;
	private String _navigation;
	private final boolean _publicationsEnabled;
	private final RenderResponse _renderResponse;
	private final boolean _sandboxOnlyEnabled;
	private final boolean _unapprovedChangesAllowed;

}