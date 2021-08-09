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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.remote.app.admin.web.internal.route.CustomElementPortletEntryFriendlyURLMapper;
import com.liferay.remote.app.model.CustomElementPortletEntry;
import com.liferay.remote.app.util.CSSURLsParser;
import com.liferay.remote.app.util.TagAttributesParser;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
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

	public static String getPortletName(
		CustomElementPortletEntry customElementPortletEntry) {

		String escapedTagName = customElementPortletEntry.getTagName();

		escapedTagName = escapedTagName.replaceAll(
			StringPool.DASH, StringPool.UNDERLINE);

		return StringBundler.concat(
			escapedTagName, StringPool.UNDERLINE,
			customElementPortletEntry.getCustomElementPortletEntryId());
	}

	public CustomElementPortlet(
		CustomElementPortletEntry customElementPortletEntry,
		CSSURLsParser cssURLsParser, TagAttributesParser tagAttributesParser) {

		_customElementPortletEntry = customElementPortletEntry;
		_cssURLsParser = cssURLsParser;
		_tagAttributesParser = tagAttributesParser;

		_portletName = getPortletName(customElementPortletEntry);
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

		Collection<String> cssURLs = _cssURLsParser.parse(
			_customElementPortletEntry.getCssURLs());

		HashMapDictionary<String, Object> properties =
			HashMapDictionaryBuilder.<String, Object>put(
				"com.liferay.portlet.css-class-wrapper",
				"portlet-custom-element-portlet"
			).put(
				"com.liferay.portlet.header-portal-css",
				cssURLs.toArray(new String[0])
			).put(
				"com.liferay.portlet.display-category",
				_customElementPortletEntry.getPortletDisplayCategory()
			).put(
				"com.liferay.portlet.instanceable",
				_customElementPortletEntry.getInstanceable()
			).put(
				"javax.portlet.name", _portletName
			).put(
				"javax.portlet.security-role-ref", "power-user,user"
			).put(
				"javax.portlet.resource-bundle", _getResourceBundleName()
			).build();

		_serviceRegistration = bundleContext.registerService(
			Portlet.class, this, properties);

		properties = HashMapDictionaryBuilder.<String, Object>put(
			"resource.bundle.base.name", _getResourceBundleName()
		).put(
			"servlet.context.name", "remote-app-admin-web"
		).build();

		_resourceBundleLoaderServiceRegistration =
			bundleContext.registerService(
				ResourceBundleLoader.class,
				locale -> _getResourceBundle(locale), properties);

		HashMapDictionaryBuilder.<String, Object>put(
			"javax.portlet.name", _portletName
		).build();

		_friendlyURLMapperServiceRegistration = bundleContext.registerService(
			FriendlyURLMapper.class,
			new CustomElementPortletEntryFriendlyURLMapper(
				_customElementPortletEntry),
			properties);
	}

	@Override
	public void render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		try {
			PrintWriter printWriter = renderResponse.getWriter();

			printWriter.print(StringPool.LESS_THAN);
			printWriter.print(_customElementPortletEntry.getTagName());

			Map<String, String> tagAttributes = _tagAttributesParser.parse(
				_customElementPortletEntry.getTagAttributes());

			for (Map.Entry<String, String> entry : tagAttributes.entrySet()) {
				printWriter.print(StringPool.SPACE);
				printWriter.print(entry.getKey());
				printWriter.print("=\"");

				String value = entry.getValue();

				printWriter.print(value.replaceAll(StringPool.QUOTE, "&quot;"));

				printWriter.print(StringPool.QUOTE);
			}

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

		_friendlyURLMapperServiceRegistration.unregister();
		_resourceBundleLoaderServiceRegistration.unregister();
		_serviceRegistration.unregister();

		_friendlyURLMapperServiceRegistration = null;
		_resourceBundleLoaderServiceRegistration = null;
		_serviceRegistration = null;
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
				"javax.portlet.title." + _portletName,
				_customElementPortletEntry.getName(locale)
			).build();

		};
	}

	private String _getResourceBundleName() {
		return _portletName + ".Language";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CustomElementPortlet.class);

	private final CSSURLsParser _cssURLsParser;
	private final CustomElementPortletEntry _customElementPortletEntry;
	private ServiceRegistration<FriendlyURLMapper>
		_friendlyURLMapperServiceRegistration;
	private final String _portletName;
	private ServiceRegistration<ResourceBundleLoader>
		_resourceBundleLoaderServiceRegistration;
	private ServiceRegistration<Portlet> _serviceRegistration;
	private final TagAttributesParser _tagAttributesParser;

}