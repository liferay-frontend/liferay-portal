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

import com.liferay.frontend.icons.admin.web.contributor.extender.IconResource;
import com.liferay.frontend.icons.admin.web.contributor.extender.internal.IconResourceImpl;
import com.liferay.frontend.icons.admin.web.contributor.extender.internal.IconResourcesContributorImpl;
import com.liferay.frontend.icons.admin.web.internal.configuration.IconsAdminConfiguration;
import com.liferay.frontend.icons.admin.web.contributor.extender.IconResourcesContributor;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.settings.configuration.admin.display.PortalSettingsConfigurationScreenContributor;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

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
		return "/icons_admin/save_custom_icon";
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public void setAttributes(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		IconsAdminConfiguration IconsAdminConfiguration = null;

		try {
			IconsAdminConfiguration =
				_configurationProvider.getCompanyConfiguration(
					IconsAdminConfiguration.class,
					CompanyThreadLocal.getCompanyId());
		}
		catch (PortalException portalException) {
			ReflectionUtil.throwException(portalException);
		}

		httpServletRequest.setAttribute(
			IconsAdminConfiguration.class.getName(), IconsAdminConfiguration);


		Set<Map.Entry<String, List<IconResource>>> entries =
			_iconResourcesMap.entrySet();


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

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.frontend.icons.admin.web)",
		unbind = "-"
	)
	private ServletContext _servletContext;


	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void addIconResourcesContributor(
		IconResourcesContributor iconResourcesContributor) {

		Lock lock = _readWriteLock.writeLock();

		lock.lock();

		try {
			for (IconResource iconResource :
					iconResourcesContributor.getIconResources()) {

				_addIconResource(iconResource);
			}
		}
		finally {
			lock.unlock();
		}
	}

	protected void removeIconResourcesContributor(
		IconResourcesContributor iconResourcesContributor) {

		Lock lock = _readWriteLock.writeLock();

		try {
			lock.lock();

			for (IconResource iconResource :
					iconResourcesContributor.getIconResources()) {

				_removeIconResource(iconResource);
			}
		}
		finally {
			lock.unlock();
		}
	}

	private void _addIconResource(IconResource iconResource) {
		String id = iconResource.getId();

		List<IconResource> iconResources = _iconResourcesMap.get(id);

		if (iconResources == null) {
			iconResources = new ArrayList<>();

			_iconResourcesMap.put(id, iconResources);
		}

		int i;

		for (i = 0; i < iconResources.size(); i++) {
			IconResource existingIconResource = iconResources.get(i);

			if (existingIconResource.getPriority() >
					iconResource.getPriority()) {

				break;
			}
		}

		iconResources.add(i, iconResource);
	}

	private void _removeIconResource(IconResource iconResource) {
		List<IconResource> iconResources = _iconResourcesMap.get(
			iconResource.getId());

		if (iconResources == null) {
			return;
		}

		iconResources.remove(iconResource);
	}

	private final Map<String, List<IconResource>> _iconResourcesMap =
		new HashMap<>();
	private final ReadWriteLock _readWriteLock = new ReentrantReadWriteLock();
	private final IconResourcesContributorImpl _customIconResourcesContributor = new IconResourcesContributorImpl();
}