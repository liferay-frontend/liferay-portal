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
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.net.URL;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.framework.Bundle;

/**
 * @author Iván Zaera Avellón
 */
public class ThemeBundleInspectorTest {

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorThrowsWhenNotAThemeBundle() {
		Bundle bundle = Mockito.mock(Bundle.class);

		new ThemeBundleInspector(bundle, new JSONFactoryImpl());
	}

	@Test
	public void testGetCSSVariableDescriptions() throws JSONException {
		Bundle bundle = Mockito.mock(Bundle.class);

		Mockito.when(
			bundle.getHeaders(Mockito.anyString())
		).thenReturn(
			new HashMapDictionary<>()
		);

		Mockito.when(
			bundle.getResource("WEB-INF/liferay-look-and-feel.xml")
		).thenReturn(
			_liferayLookAndFeelXmlURL
		);

		Mockito.when(
			bundle.getResource("WEB-INF/tokens.json")
		).thenReturn(
			_tokensJsonURL
		);

		Mockito.when(
			bundle.getSymbolicName()
		).thenReturn(
			"A theme"
		);

		ThemeBundleInspector themeBundleInspector = new ThemeBundleInspector(
			bundle, new JSONFactoryImpl());

		Map<String, CSSVariableDescription> cssVariableDescriptions =
			themeBundleInspector.getCSSVariableDescriptions();

		Assert.assertEquals(
			cssVariableDescriptions.toString(), 2,
			cssVariableDescriptions.size());

		CSSVariableDescription cssVariableDescription =
			cssVariableDescriptions.get("background");

		Assert.assertEquals("background", cssVariableDescription.getName());

		Assert.assertEquals(
			CSSVariableType.COLOR, cssVariableDescription.getCSSVariableType());

		cssVariableDescription = cssVariableDescriptions.get("font");

		Assert.assertEquals("font", cssVariableDescription.getName());

		Assert.assertEquals(
			CSSVariableType.STRING,
			cssVariableDescription.getCSSVariableType());
	}

	@Test
	public void testGetSymbolicName() {
		Bundle bundle = Mockito.mock(Bundle.class);

		Mockito.when(
			bundle.getResource("WEB-INF/liferay-look-and-feel.xml")
		).thenReturn(
			_liferayLookAndFeelXmlURL
		);

		Mockito.when(
			bundle.getSymbolicName()
		).thenReturn(
			"A theme"
		);

		ThemeBundleInspector themeBundleInspector = new ThemeBundleInspector(
			bundle, new JSONFactoryImpl());

		Assert.assertEquals("A theme", themeBundleInspector.getSymbolicName());
	}

	private static final URL _liferayLookAndFeelXmlURL =
		ThemeBundleInspectorTest.class.getResource("liferay-look-and-feel.xml");
	private static final URL _tokensJsonURL =
		ThemeBundleInspectorTest.class.getResource("tokens.json");

}