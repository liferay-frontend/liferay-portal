/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Eduardo García
 */
@Component(service = DDMDisplayRegistry.class)
public class DDMDisplayRegistry {

	public DDMDisplay getDDMDisplay(String portletId) {
		return _serviceTrackerMap.getService(portletId);
	}

	public String[] getPortletIds() {
		Set<String> portletIds = _serviceTrackerMap.keySet();

		return portletIds.toArray(new String[0]);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, DDMDisplay.class, null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(ddmDisplay, emitter) -> emitter.emit(
					ddmDisplay.getPortletId())));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, DDMDisplay> _serviceTrackerMap;

}