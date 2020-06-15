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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Iván Zaera Avellón
 */
public class ThemeCSSVariableDescriptionsServiceTrackerCustomizer
	implements ServiceTrackerCustomizer
		<ServletContext, Map<String, CSSVariableDescription>> {

	public ThemeCSSVariableDescriptionsServiceTrackerCustomizer(
		BundleContext bundleContext, JSONFactory jsonFactory) {

		_bundleContext = bundleContext;
		_jsonFactory = jsonFactory;
	}

	@Override
	public Map<String, CSSVariableDescription> addingService(
		ServiceReference<ServletContext> serviceReference) {

		Map<String, CSSVariableDescription> cssVariablesDescriptions =
			new ConcurrentHashMap<>();

		modifiedService(serviceReference, cssVariablesDescriptions);

		return cssVariablesDescriptions;
	}

	@Override
	public void modifiedService(
		ServiceReference<ServletContext> serviceReference,
		Map<String, CSSVariableDescription> cssVariablesDescriptions) {

		ServletContext servletContext = _bundleContext.getService(
			serviceReference);

		try {
			InputStream is = servletContext.getResourceAsStream(
				"/WEB-INF/css-variables.json");

			if (is == null) {
				return;
			}

			try {
				cssVariablesDescriptions.clear();

				cssVariablesDescriptions.putAll(_parse(StringUtil.read(is)));
			}
			catch (IllegalArgumentException illegalArgumentException) {
				_log.error(
					StringBundler.concat(
						"Unable to parse css-variables.json of servlet ",
						"context ", servletContext.getServletContextName()),
					illegalArgumentException);
			}
			catch (IOException ioException) {
				_log.error(
					"Unable to read css-variables.json of servlet context" +
						servletContext.getServletContextName(),
					ioException);
			}
			catch (JSONException jsonException) {
				_log.error(
					"Unable to parse css-variables.json of servlet context" +
						servletContext.getServletContextName(),
					jsonException);
			}
		}
		finally {
			_bundleContext.ungetService(serviceReference);
		}
	}

	@Override
	public void removedService(
		ServiceReference<ServletContext> serviceReference,
		Map<String, CSSVariableDescription> cssVariableDescriptions) {
	}

	private CSSVariableType _getCSSVariableType(JSONObject jsonObject) {
		String type = jsonObject.getString("type");

		if (type.equals("color")) {
			return CSSVariableType.COLOR;
		}

		return CSSVariableType.STRING;
	}

	private Map<String, String> _getLabelsMap(
		JSONObject cssVariableDefinitionJSONObject, String defaultLabel) {

		Map<String, String> labelsMap = new HashMap<>();

		JSONObject labelsMapJSONObject =
			cssVariableDefinitionJSONObject.getJSONObject("label");

		if (labelsMapJSONObject != null) {
			for (String localeKey : labelsMapJSONObject.keySet()) {
				labelsMap.put(
					localeKey, labelsMapJSONObject.getString(localeKey));
			}
		}
		else {
			String label = cssVariableDefinitionJSONObject.getString("label");

			if (label == null) {
				label = defaultLabel;
			}

			labelsMap.put(StringPool.BLANK, label);
		}

		return labelsMap;
	}

	private Map<String, CSSVariableDescription> _parse(String json)
		throws JSONException {

		JSONObject jsonObject = _jsonFactory.createJSONObject(json);

		JSONObject variablesJSONObject = jsonObject.getJSONObject("variables");

		if (variablesJSONObject == null) {
			throw new IllegalArgumentException(
				"Unable to read variables field");
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
					_getLabelsMap(cssVariableDefinitionJSONObject, name)));
		}

		return cssVariableDescriptions;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ThemeCSSVariableDescriptionsServiceTrackerCustomizer.class);

	private final BundleContext _bundleContext;
	private final JSONFactory _jsonFactory;

}