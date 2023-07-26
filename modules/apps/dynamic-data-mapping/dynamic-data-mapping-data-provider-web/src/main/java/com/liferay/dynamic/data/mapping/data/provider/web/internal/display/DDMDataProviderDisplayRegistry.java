/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.data.provider.web.internal.display;

import com.liferay.dynamic.data.mapping.data.provider.display.DDMDataProviderDisplay;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Lino Alves
 */
@Component(service = DDMDataProviderDisplayRegistry.class)
public class DDMDataProviderDisplayRegistry {

	public DDMDataProviderDisplay getDDMDataProviderDisplay(String portletId) {
		return _serviceTrackerMap.getService(portletId);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, DDMDataProviderDisplay.class, null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(ddmDataProviderDisplay, emitter) -> emitter.emit(
					ddmDataProviderDisplay.getPortletId())));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, DDMDataProviderDisplay>
		_serviceTrackerMap;

}