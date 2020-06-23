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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.framework.Bundle;

/**
 * @author Iván Zaera Avellón
 */
public class ThemeBundleInspector {

	public static boolean isTheme(Bundle bundle) {
		boolean theme = false;

		URL url = bundle.getResource("WEB-INF/liferay-look-and-feel.xml");

		if (url != null) {
			try (InputStream inputStream = url.openStream()) {
				String xml = StringUtil.read(inputStream);

				if (xml.contains("</theme>")) {
					theme = true;
				}
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}
		}

		return theme;
	}

	/**
	 * @throws IllegalArgumentException if the bundle is not a theme bundle
	 */
	public ThemeBundleInspector(Bundle bundle, JSONFactory jsonFactory)
		throws IllegalArgumentException {

		_bundle = bundle;
		_jsonFactory = jsonFactory;

		if (!isTheme(bundle)) {
			throw new IllegalArgumentException(
				StringBundler.concat(
					"Bundle ", _bundle.getSymbolicName(), " is not a theme"));
		}
	}

	public Map<String, CSSVariableDescription> getCSSVariableDescriptions()
		throws JSONException {

		JSONObject jsonObject = _parseJSONObject(_getTokensPath());

		if (jsonObject == null) {
			return null;
		}

		JSONArray variablesJSONArray = jsonObject.getJSONArray("variables");

		if (variablesJSONArray == null) {
			return null;
		}

		Map<String, CSSVariableDescription> cssVariableDescriptions =
			new HashMap<>();

		for (int i = 0; i < variablesJSONArray.length(); i++) {
			JSONObject cssVariableDefinitionJSONObject =
				variablesJSONArray.getJSONObject(i);

			String name = cssVariableDefinitionJSONObject.getString("name");

			cssVariableDescriptions.put(
				name,
				new CSSVariableDescriptionImpl(
					_getCSSVariableType(cssVariableDefinitionJSONObject),
					name));
		}

		if (cssVariableDescriptions.isEmpty()) {
			return null;
		}

		return cssVariableDescriptions;
	}

	public String getSymbolicName() {
		return _bundle.getSymbolicName();
	}

	private CSSVariableType _getCSSVariableType(JSONObject jsonObject) {
		String type = jsonObject.getString("type");

		if (type.equals("color")) {
			return CSSVariableType.COLOR;
		}

		return CSSVariableType.STRING;
	}

	private String _getTokensPath() {
		Locale defaultLocale = LocaleUtil.getDefault();

		Dictionary<String, String> headers = _bundle.getHeaders(
			defaultLocale.toString());

		String tokensPath = headers.get("Tokens-Path");

		if (Validator.isNull(tokensPath)) {
			tokensPath = "WEB-INF/tokens.json";
		}

		if (tokensPath.charAt(0) == '/') {
			tokensPath = tokensPath.substring(1);
		}

		return tokensPath;
	}

	private JSONObject _parseJSONObject(String fileName) throws JSONException {
		URL resource = _bundle.getResource(fileName);

		if (resource == null) {
			return null;
		}

		try (InputStream is = resource.openStream()) {
			return _jsonFactory.createJSONObject(StringUtil.read(is));
		}
		catch (IOException ioException) {
			throw new JSONException("Unable to parse " + fileName, ioException);
		}
	}

	private final Bundle _bundle;
	private final JSONFactory _jsonFactory;

}