/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.push.notifications.web.internal.util;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.resource.bundle.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.push.notifications.sender.PushNotificationsSender;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Andrea Di Giorgi
 */
@Component(service = ResourceBundleLoaderProvider.class)
public class ResourceBundleLoaderProvider {

	public ResourceBundleLoader getResourceBundleLoader(String platform) {
		ResourceBundleLoader resourceBundleLoader =
			_serviceTrackerMap.getService(platform);

		if (resourceBundleLoader == null) {
			return ResourceBundleLoaderUtil.getPortalResourceBundleLoader();
		}

		return new AggregateResourceBundleLoader(
			resourceBundleLoader,
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, PushNotificationsSender.class, "platform",
			new ServiceTrackerCustomizer
				<PushNotificationsSender, ResourceBundleLoader>() {

				@Override
				public ResourceBundleLoader addingService(
					ServiceReference<PushNotificationsSender>
						serviceReference) {

					Bundle bundle = serviceReference.getBundle();

					return ResourceBundleLoaderUtil.
						getResourceBundleLoaderByBundleSymbolicName(
							bundle.getSymbolicName());
				}

				@Override
				public void modifiedService(
					ServiceReference<PushNotificationsSender> serviceReference,
					ResourceBundleLoader resourceBundleLoader) {
				}

				@Override
				public void removedService(
					ServiceReference<PushNotificationsSender> serviceReference,
					ResourceBundleLoader resourceBundleLoader) {
				}

			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, ResourceBundleLoader> _serviceTrackerMap;

}