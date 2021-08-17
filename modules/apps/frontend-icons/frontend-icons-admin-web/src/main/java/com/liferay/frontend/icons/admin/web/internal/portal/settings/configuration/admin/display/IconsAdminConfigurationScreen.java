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

package com.liferay.frontend.icons.admin.web.internal.portal.settings.configuration.admin.display;

import com.liferay.frontend.icons.admin.web.internal.contributor.extender.IconResourcePack;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.configuration.admin.display.ConfigurationScreen;
import com.liferay.frontend.icons.admin.web.internal.contributor.extender.IconResource;
import com.liferay.frontend.icons.admin.web.internal.portal.settings.configuration.admin.category.IconsAdminConfiguration;
import com.liferay.frontend.icons.admin.web.internal.helper.IconResourceHelper;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryce Osterhaus
 */
@Component(service = ConfigurationScreen.class)
public class IconsAdminConfigurationScreen implements ConfigurationScreen {

	@Override
	public String getCategoryKey() {
		return "icons-admin";
	}

	@Override
	public String getKey() {
		return "icons-admin";
	}

	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(
			ResourceBundleUtil.getBundle(locale, getClass()),
			"icons-admin-configuration-name");
	}

	@Override
	public String getScope() {
		return ExtendedObjectClassDefinition.Scope.COMPANY.getValue();
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		IconsAdminConfiguration iconsAdminConfiguration = null;

		try {
			iconsAdminConfiguration =
				_configurationProvider.getCompanyConfiguration(
					IconsAdminConfiguration.class,
					CompanyThreadLocal.getCompanyId());
		}
		catch (PortalException portalException) {
			ReflectionUtil.throwException(portalException);
		}

		httpServletRequest.setAttribute(
			IconsAdminConfiguration.class.getName(), iconsAdminConfiguration);

		Map<String, IconResourcePack> iconResourcePacks =
			_iconResourceHelper.getIconResourceMaps();

		JSONObject json = JSONFactoryUtil.createJSONObject();

		for (Map.Entry<String, IconResourcePack> entry: iconResourcePacks.entrySet()) {
			String name = entry.getKey();
			IconResourcePack iconResourcePack = entry.getValue();

			List<String> iconNames = new ArrayList<>();

			for (IconResource iconResource : iconResourcePack.getIconResources()) {
				iconNames.add(iconResource.getId());
			}

			json.put(name, iconNames);
		}

		httpServletRequest.setAttribute("icons", json.toJSONString());

		try {
			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(
					"/portal_settings/icons_config.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new IOException(
				"Unable to render icons_config.jsp", exception);
		}
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private IconResourceHelper _iconResourceHelper;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.frontend.icons.admin.web)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}