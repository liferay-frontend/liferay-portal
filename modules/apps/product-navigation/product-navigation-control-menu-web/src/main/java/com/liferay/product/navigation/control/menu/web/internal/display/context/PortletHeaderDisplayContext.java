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

package com.liferay.product.navigation.control.menu.web.internal.display.context;

import com.liferay.application.list.PanelApp;
import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.control.menu.web.internal.constants.ProductNavigationControlMenuWebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class PortletHeaderDisplayContext {

	public PortletHeaderDisplayContext(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;

		_panelAppRegistry = (PanelAppRegistry)_httpServletRequest.getAttribute(
			ApplicationListWebKeys.PANEL_APP_REGISTRY);
		_panelCategoryRegistry =
			(PanelCategoryRegistry)_httpServletRequest.getAttribute(
				ApplicationListWebKeys.PANEL_CATEGORY_REGISTRY);

		_panelCategoryHelper = new PanelCategoryHelper(
			_panelAppRegistry, _panelCategoryRegistry);

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<Map<String, String>> getApps() throws PortalException {
		List<Map<String, String>> apps = new ArrayList<>();

		PanelCategory curPanelCategory = getCurPanelCategory();

		List<PanelApp> panelApps = _panelCategoryHelper.getAllPanelApps(
			curPanelCategory.getKey());

		for (PanelApp panelApp : panelApps) {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				_themeDisplay.getCompanyId(), panelApp.getPortletId());

			apps.add(
				HashMapBuilder.put(
					"href",
					String.valueOf(panelApp.getPortletURL(_httpServletRequest))
				).put(
					"label",
					PortalUtil.getPortletTitle(
						portlet, _themeDisplay.getLocale())
				).build());
		}

		return apps;
	}

	public PanelCategory getCurPanelCategory() {
		if (_curPanelCategory != null) {
			return _curPanelCategory;
		}

		String rootPanelCategoryKey = PanelCategoryKeys.CONTROL_PANEL;

		if (_panelCategoryHelper.isApplicationsMenuApp(getPortletId())) {
			rootPanelCategoryKey =
				PanelCategoryKeys.APPLICATIONS_MENU_APPLICATIONS;
		}

		List<PanelCategory> panelCategories =
			_panelCategoryRegistry.getChildPanelCategories(
				rootPanelCategoryKey);

		for (PanelCategory panelCategory : panelCategories) {
			if (_panelCategoryHelper.containsPortlet(
					getPortletId(), panelCategory.getKey())) {

				_curPanelCategory = panelCategory;

				return _curPanelCategory;
			}
		}

		return null;
	}

	public List<PanelApp> getPanelApps() {
		PanelCategory curPanelCategory = getCurPanelCategory();

		return _panelCategoryHelper.getAllPanelApps(curPanelCategory.getKey());
	}

	public String getPortletId() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.getRootPortletId();
	}

	public String getPortletTitle() {
		return (String)_httpServletRequest.getAttribute(
			ProductNavigationControlMenuWebKeys.PORTLET_TITLE);
	}

	public Map<String, Object> getProps() throws PortalException {
		return HashMapBuilder.<String, Object>put(
			"apps", getApps()
		).put(
			"category",
			() -> {
				PanelCategory curPanelCategory = getCurPanelCategory();

				return curPanelCategory.getLabel(_themeDisplay.getLocale());
			}
		).put(
			"title", HtmlUtil.escape(getPortletTitle())
		).build();
	}

	private PanelCategory _curPanelCategory;
	private final HttpServletRequest _httpServletRequest;
	private final PanelAppRegistry _panelAppRegistry;
	private final PanelCategoryHelper _panelCategoryHelper;
	private final PanelCategoryRegistry _panelCategoryRegistry;
	private final ThemeDisplay _themeDisplay;

}