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

import com.liferay.frontend.icons.admin.web.internal.configuration.IconsAdminConfiguration;
import com.liferay.frontend.icons.admin.web.internal.contributor.extender.IconResource;
import com.liferay.frontend.icons.admin.web.internal.helper.IconResourceHelper;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.settings.configuration.admin.display.PortalSettingsConfigurationScreenContributor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryce Osterhaus
 */
@Component(service = PortalSettingsConfigurationScreenContributor.class)
public class IconsAdminPortalSettingsConfigurationScreenContributor
	implements PortalSettingsConfigurationScreenContributor {

	@Override
	public String getCategoryKey() {
		return "icons-admin";
	}

	@Override
	public String getJspPath() {
		return "/portal_settings/icons_config.jsp";
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
	public String getSaveMVCActionCommandName() {
		return "/frontend_icons_admin/save_custom_icon";
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public void setAttributes(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

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

		Map<String, List<IconResource>> iconResourceMaps =
			_iconResourceHelper.getIconResourceMaps();

		Set<Map.Entry<String, List<IconResource>>> entries =
			iconResourceMaps.entrySet();

		List<String> ids = new ArrayList<>();

		for (Map.Entry<String, List<IconResource>> entry : entries) {
			List<IconResource> iconResources = entry.getValue();

			IconResource iconResource = iconResources.get(0);

			ids.add(iconResource.getId());
		}

		httpServletRequest.setAttribute("iconIds", ids);
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