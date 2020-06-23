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

package com.liferay.frontend.css.variables.web.internal.theme;

import com.liferay.frontend.css.variables.CSSVariableDescription;
import com.liferay.frontend.css.variables.CSSVariableType;
import com.liferay.frontend.css.variables.web.internal.CSSVariableDescriptionImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Iván Zaera Avellón
 */
public class ThemeCSSVariableDescriptionsServiceTrackerCustomizer
	implements ServiceTrackerCustomizer
		<ServletContext, AtomicReference<Map<String, CSSVariableDescription>>> {

	public ThemeCSSVariableDescriptionsServiceTrackerCustomizer(
		BundleContext bundleContext, JSONFactory jsonFactory) {

		_bundleContext = bundleContext;
		_jsonFactory = jsonFactory;
	}

	@Override
	public AtomicReference<Map<String, CSSVariableDescription>> addingService(
		ServiceReference<ServletContext> serviceReference) {

		AtomicReference<Map<String, CSSVariableDescription>> atomicReference =
			new AtomicReference<>();

		modifiedService(serviceReference, atomicReference);

		return atomicReference;
	}

	@Override
	public void modifiedService(
		ServiceReference<ServletContext> serviceReference,
		AtomicReference<Map<String, CSSVariableDescription>> atomicReference) {

		ServletContext servletContext = _bundleContext.getService(
			serviceReference);

		try {
			String fileName = _getCSSVariableDescriptionsFileName(
				servletContext);

			atomicReference.set(
				_getCSSVariableDescriptions(servletContext, fileName));
		}
		catch (JSONException jsonException) {
			_log.error(
				"Unable to read css-variables.json of servlet context" +
					servletContext.getServletContextName(),
				jsonException);
		}
		finally {
			_bundleContext.ungetService(serviceReference);
		}
	}

	@Override
	public void removedService(
		ServiceReference<ServletContext> serviceReference,
		AtomicReference<Map<String, CSSVariableDescription>> atomicReference) {
	}

	private Map<String, CSSVariableDescription> _getCSSVariableDescriptions(
			ServletContext servletContext, String fileName)
		throws JSONException {

		JSONObject jsonObject = _parseJSONObject(servletContext, fileName);

		if (jsonObject == null) {
			return null;
		}

		JSONObject variablesJSONObject = jsonObject.getJSONObject("variables");

		if (variablesJSONObject == null) {
			throw new JSONException("Unable to read variables field");
		}

		Map<String, CSSVariableDescription> cssVariableDescriptions =
			new HashMap<>();

		for (String name : variablesJSONObject.keySet()) {
			JSONObject cssVariableDefinitionJSONObject =
				variablesJSONObject.getJSONObject(name);

			cssVariableDescriptions.put(
				name,
				new CSSVariableDescriptionImpl(
					_getCSSVariableType(cssVariableDefinitionJSONObject),
					name));
		}

		return cssVariableDescriptions;
	}

	private String _getCSSVariableDescriptionsFileName(
			ServletContext servletContext)
		throws JSONException {

		try (InputStream is = servletContext.getResourceAsStream(
				"/package.json")) {

			if (is == null) {
				return null;
			}

			JSONObject packageJSONObject = _jsonFactory.createJSONObject(
				StringUtil.read(is));

			String cssVariablesPath = StringPool.BLANK;

			JSONObject liferayThemeJSONObject = packageJSONObject.getJSONObject(
				"liferayTheme");

			if (liferayThemeJSONObject != null) {
				cssVariablesPath = liferayThemeJSONObject.getString(
					"cssVariablesPath");
			}

			if (Validator.isNull(cssVariablesPath)) {
				cssVariablesPath = "/WEB-INF/css-variables.json";
			}

			return cssVariablesPath;
		}
		catch (IOException ioException) {
			throw new JSONException(
				"Unable to parse package.json", ioException);
		}
	}

	private CSSVariableType _getCSSVariableType(JSONObject jsonObject) {
		String type = jsonObject.getString("type");

		if (type.equals("color")) {
			return CSSVariableType.COLOR;
		}

		return CSSVariableType.STRING;
	}

	private JSONObject _parseJSONObject(
			ServletContext servletContext, String fileName)
		throws JSONException {

		try (InputStream is = servletContext.getResourceAsStream(fileName)) {
			if (is == null) {
				return null;
			}

			return _jsonFactory.createJSONObject(StringUtil.read(is));
		}
		catch (IOException ioException) {
			throw new JSONException("Unable to parse " + fileName, ioException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ThemeCSSVariableDescriptionsServiceTrackerCustomizer.class);

	private final BundleContext _bundleContext;
	private final JSONFactory _jsonFactory;

}