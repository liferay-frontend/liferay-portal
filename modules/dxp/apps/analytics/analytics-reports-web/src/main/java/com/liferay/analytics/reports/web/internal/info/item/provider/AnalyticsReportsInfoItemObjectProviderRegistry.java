/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.web.internal.info.item.provider;

import com.liferay.analytics.reports.info.item.provider.AnalyticsReportsInfoItemObjectProvider;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Cristina González
 */
@Component(service = AnalyticsReportsInfoItemObjectProviderRegistry.class)
public class AnalyticsReportsInfoItemObjectProviderRegistry {

	public AnalyticsReportsInfoItemObjectProvider<?>
		getAnalyticsReportsInfoItemObjectProvider(String className) {

		return _analyticsReportsInfoItemObjectProviderServiceTrackerMap.
			getService(className);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_analyticsReportsInfoItemObjectProviderServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext,
				(Class<AnalyticsReportsInfoItemObjectProvider<?>>)
					(Class<?>)AnalyticsReportsInfoItemObjectProvider.class,
				null,
				(serviceReference, emitter) -> {
					AnalyticsReportsInfoItemObjectProvider<?>
						analyticsReportsInfoItemObjectProvider =
							bundleContext.getService(serviceReference);

					try {
						emitter.emit(
							analyticsReportsInfoItemObjectProvider.
								getClassName());
					}
					finally {
						bundleContext.ungetService(serviceReference);
					}
				},
				new PropertyServiceReferenceComparator<>("service.ranking"));
	}

	private ServiceTrackerMap<String, AnalyticsReportsInfoItemObjectProvider<?>>
		_analyticsReportsInfoItemObjectProviderServiceTrackerMap;

}