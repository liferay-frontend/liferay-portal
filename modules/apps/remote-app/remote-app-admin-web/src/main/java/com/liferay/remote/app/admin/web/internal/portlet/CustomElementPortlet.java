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

package com.liferay.remote.app.admin.web.internal.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.remote.app.model.CustomElementPortletEntry;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.Portlet;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Iván Zaera Avellón
 */
public class CustomElementPortlet extends MVCPortlet {

	public CustomElementPortlet(
		CustomElementPortletEntry customElementPortletEntry) {

		_customElementPortletEntry = customElementPortletEntry;
	}

	public String getName() {
		return _customElementPortletEntry.getNameCurrentLanguageId();
	}

	public String getName(Locale locale) {
		return _customElementPortletEntry.getName(locale);
	}

	public synchronized void register(BundleContext bundleContext) {
		if (_serviceRegistration != null) {
			throw new IllegalStateException("Portlet is already registered");
		}

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put(
			"com.liferay.portlet.css-class-wrapper",
			"portlet-custom-element-portlet");
		properties.put(
			"com.liferay.portlet.display-category", "category.sample");
		properties.put("com.liferay.portlet.instanceable", true);
		properties.put("javax.portlet.name", _getPortletName());
		properties.put("javax.portlet.security-role-ref", "power-user,user");
		properties.put(
			"javax.portlet.resource-bundle", _getResourceBundleName());

		_serviceRegistration = bundleContext.registerService(
			Portlet.class, this, properties);

		properties = new Hashtable<>();

		properties.put("resource.bundle.base.name", _getResourceBundleName());
		properties.put("servlet.context.name", "remote-app-admin-web");

		_resourceBundleLoaderServiceRegistration =
			bundleContext.registerService(
				ResourceBundleLoader.class,
				locale -> _getResourceBundle(locale), properties);
	}

	@Override
	public void render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		try {
			PrintWriter printWriter = renderResponse.getWriter();

			printWriter.print(StringPool.LESS_THAN);
			printWriter.print(_customElementPortletEntry.getTagName());
			printWriter.print("></");
			printWriter.print(_customElementPortletEntry.getTagName());
			printWriter.print(StringPool.GREATER_THAN);

			printWriter.flush();
		}
		catch (IOException ioException) {
			_log.error("Unable to render HTML output", ioException);
		}
	}

	public synchronized void unregister() {
		if (_serviceRegistration == null) {
			throw new IllegalStateException("Portlet is not registered");
		}

		_resourceBundleLoaderServiceRegistration.unregister();
		_serviceRegistration.unregister();

		_resourceBundleLoaderServiceRegistration = null;
		_serviceRegistration = null;
	}

	private String _getPortletName() {
		return "custom_element_portlet_" +
			_customElementPortletEntry.getCustomElementPortletEntryId();
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		return new ResourceBundle() {

			@Override
			public Enumeration<String> getKeys() {
				return Collections.enumeration(_labels.keySet());
			}

			@Override
			protected Object handleGetObject(String key) {
				return _labels.get(key);
			}

			private final Map<String, String> _labels = HashMapBuilder.put(
				"javax.portlet.title." + _getPortletName(),
				_customElementPortletEntry.getName(locale)
			).build();

		};
	}

	private String _getResourceBundleName() {
		return _getPortletName() + ".Language";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CustomElementPortlet.class);

	private final CustomElementPortletEntry _customElementPortletEntry;
	private ServiceRegistration<ResourceBundleLoader>
		_resourceBundleLoaderServiceRegistration;
	private ServiceRegistration<Portlet> _serviceRegistration;

}