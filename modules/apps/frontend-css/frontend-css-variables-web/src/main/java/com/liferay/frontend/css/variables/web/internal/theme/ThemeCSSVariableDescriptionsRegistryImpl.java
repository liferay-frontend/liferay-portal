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
import com.liferay.frontend.css.variables.theme.ThemeCSSVariableDescriptionsRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.model.Theme;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	immediate = true, service = ThemeCSSVariableDescriptionsRegistry.class
)
public class ThemeCSSVariableDescriptionsRegistryImpl
	implements ThemeCSSVariableDescriptionsRegistry {

	@Override
	public Map<String, CSSVariableDescription> getCSSVariableDescriptions(
		Theme theme) {

		AtomicReference<Map<String, CSSVariableDescription>> atomicReference =
			_serviceTrackerMap.getService(theme.getServletContextName());

		return atomicReference.get();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ServletContext.class, "osgi.web.symbolicname",
			new ThemeCSSVariableDescriptionsServiceTrackerCustomizer(
				_bundleContext, _jsonFactory));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private BundleContext _bundleContext;

	@Reference
	private JSONFactory _jsonFactory;

	private ServiceTrackerMap
		<String, AtomicReference<Map<String, CSSVariableDescription>>>
			_serviceTrackerMap;

}