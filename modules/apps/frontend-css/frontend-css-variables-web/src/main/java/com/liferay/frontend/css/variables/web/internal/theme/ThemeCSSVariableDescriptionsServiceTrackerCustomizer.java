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
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Iván Zaera Avellón
 */
public class ThemeCSSVariableDescriptionsServiceTrackerCustomizer
	implements ServiceTrackerCustomizer
		<ServletContext, AtomicReference<Map<String, CSSVariableDescription>>> {

	public ThemeCSSVariableDescriptionsServiceTrackerCustomizer(
		JSONFactory jsonFactory) {

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

		Bundle bundle = serviceReference.getBundle();

		if (!ThemeBundleInspector.isTheme(bundle)) {
			return;
		}

		ThemeBundleInspector themeBundleInspector = new ThemeBundleInspector(
			bundle, _jsonFactory);

		try {
			Map<String, CSSVariableDescription> cssVariableDescriptions =
				themeBundleInspector.getCSSVariableDescriptions();

			if (cssVariableDescriptions != null) {
				atomicReference.set(cssVariableDescriptions);
			}
		}
		catch (JSONException jsonException) {
			_log.error(
				"Unable to obtain CSS variable descriptions of theme " +
					themeBundleInspector.getSymbolicName(),
				jsonException);
		}
	}

	@Override
	public void removedService(
		ServiceReference<ServletContext> serviceReference,
		AtomicReference<Map<String, CSSVariableDescription>> atomicReference) {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ThemeCSSVariableDescriptionsServiceTrackerCustomizer.class);

	private final JSONFactory _jsonFactory;

}