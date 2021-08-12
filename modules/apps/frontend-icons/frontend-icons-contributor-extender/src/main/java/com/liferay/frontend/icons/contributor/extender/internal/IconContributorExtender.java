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

package com.liferay.frontend.icons.contributor.extender.internal;

import com.liferay.frontend.icons.contributor.extender.IconResourcesContributor;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Iván Zaera Avellón
 */
@Component(immediate = true, service = {})
public class IconContributorExtender
	implements ServiceTrackerCustomizer
		<ServletContext, ServiceRegistration<IconResourcesContributor>> {

	@Override
	public ServiceRegistration<IconResourcesContributor> addingService(
		ServiceReference<ServletContext> serviceReference) {

		IconResourcesContributor iconResourcesContributor =
			_getIconResourcesContributor(serviceReference.getBundle());

		if (iconResourcesContributor == null) {
			return null;
		}

		return _bundleContext.registerService(
			IconResourcesContributor.class, iconResourcesContributor,
			MapUtil.singletonDictionary("service.ranking", 0));
	}

	@Override
	public void modifiedService(
		ServiceReference<ServletContext> serviceReference,
		ServiceRegistration<IconResourcesContributor> serviceRegistration) {
	}

	@Override
	public void removedService(
		ServiceReference<ServletContext> serviceReference,
		ServiceRegistration<IconResourcesContributor> serviceRegistration) {

		serviceRegistration.unregister();

		_bundleContext.ungetService(serviceReference);
	}

	@Activate
	protected void activate(BundleContext bundleContext)
		throws InvalidSyntaxException {

		_bundleContext = bundleContext;

		_serviceTracker = new ServiceTracker<>(
			bundleContext,
			bundleContext.createFilter(
				StringBundler.concat(
					"(&(objectClass=", ServletContext.class.getName(),
					")(osgi.web.symbolicname=*))")),
			this);

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private static String _getSymbolContent(String name, URL url) throws IOException {
		try (InputStream is = url.openStream()) {
			String symbolContent = StringUtil.read(is);

			String viewBox = "";

			Pattern pattern = Pattern.compile("(?i)(viewBox.*\"(?=[\\s, >]))");
			Matcher matcher = pattern.matcher(symbolContent);
			if (matcher.find()) {
				viewBox = matcher.group(1);
			}

			symbolContent = symbolContent.replaceFirst(
				"(?i)<svg[^>]*>", StringPool.BLANK);

			symbolContent = symbolContent.replaceFirst(
				"(?i)</svg[^>]*>", StringPool.BLANK);


			symbolContent = "<symbol id=" + StringPool.QUOTE +name+ StringPool.QUOTE + StringPool.SPACE + viewBox + ">" + symbolContent + "</symbol>";

			return symbolContent;
		}
	}

	private IconResourcesContributor _getIconResourcesContributor(
		Bundle bundle) {

		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		if (headers == null) {
			return null;
		}

		String liferayIconsPath = headers.get("Liferay-Icons-Path");
		String liferayIconsNamespace= headers.get("Liferay-Icons-Namespace");

		if (Validator.isBlank(liferayIconsPath)) {
			return null;
		}

		liferayIconsPath = "META-INF/resources/" + liferayIconsPath;

		Enumeration<URL> entries = bundle.findEntries(
			liferayIconsPath, "*.svg", true);

		if (entries == null) {
			return null;
		}

		IconResourcesContributorImpl iconResourcesContributorImpl =
			new IconResourcesContributorImpl();

		int stripPathPrefixLength = liferayIconsPath.length() + 2;

		while (entries.hasMoreElements()) {
			URL url = entries.nextElement();

			String path = url.getPath();

			String name = path.substring(stripPathPrefixLength);

			name = FileUtil.stripExtension(name);

			if (!Validator.isBlank(liferayIconsNamespace)) {
				name = liferayIconsNamespace + "_" + name;
			}

			try {
				iconResourcesContributorImpl.addIconResource(
					new IconResourceImpl(name, _getSymbolContent(name, url)));
			}
			catch (IOException ioe) {
				_log.error(
					StringBundler.concat(
						"Unable to read icon resource ", path, " in bundle ",
						bundle.getSymbolicName()),
					ioe);
			}
		}

		return iconResourcesContributorImpl;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IconContributorExtender.class);

	private BundleContext _bundleContext;
	private ServiceTracker
		<ServletContext, ServiceRegistration<IconResourcesContributor>>
			_serviceTracker;

}