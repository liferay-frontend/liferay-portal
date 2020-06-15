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
import com.liferay.portal.kernel.util.LocaleUtil;

import java.net.URL;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Iván Zaera Avellón
 */
public class ThemeCSSVariableDescriptionsServiceTrackerCustomizerTest {

	@Test
	public void testModifiedService() throws Exception {
		BundleContext bundleContext = Mockito.mock(BundleContext.class);

		ServletContext servletContext = Mockito.mock(ServletContext.class);

		Mockito.when(
			servletContext.getResourceAsStream("/WEB-INF/css-variables.json")
		).thenReturn(
			_cssVariablesJsonURL.openStream()
		);

		ServiceReference<ServletContext> serviceReference = Mockito.mock(
			ServiceReference.class);

		Mockito.when(
			bundleContext.getService(serviceReference)
		).thenReturn(
			servletContext
		);

		ThemeCSSVariableDescriptionsServiceTrackerCustomizer
			themeCSSVariableDescriptionsServiceTrackerCustomizer =
				new ThemeCSSVariableDescriptionsServiceTrackerCustomizer(
					bundleContext, new JSONFactoryImpl());

		Map<String, CSSVariableDescription> cssVariablesDescriptions =
			new HashMap<>();

		themeCSSVariableDescriptionsServiceTrackerCustomizer.modifiedService(
			serviceReference, cssVariablesDescriptions);

		Assert.assertEquals(
			cssVariablesDescriptions.toString(), 2,
			cssVariablesDescriptions.size());

		CSSVariableDescription cssVariableDescription =
			cssVariablesDescriptions.get("background");

		Assert.assertEquals(
			CSSVariableType.COLOR, cssVariableDescription.getCSSVariableType());

		Assert.assertEquals(
			"Background Color",
			cssVariableDescription.getLabel(LocaleUtil.ITALIAN));

		Assert.assertEquals(
			"Background Color",
			cssVariableDescription.getLabel(new Locale("es")));

		cssVariableDescription = cssVariablesDescriptions.get("font");

		Assert.assertEquals(
			CSSVariableType.STRING,
			cssVariableDescription.getCSSVariableType());

		Assert.assertEquals(
			"Font", cssVariableDescription.getLabel(LocaleUtil.ITALIAN));

		Assert.assertEquals(
			"Tipo de letra", cssVariableDescription.getLabel(new Locale("es")));
	}

	private static final URL _cssVariablesJsonURL =
		ThemeCSSVariableDescriptionsServiceTrackerCustomizerTest.class.
			getResource("css-variables.json");

}