/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.admin.web.internal.reindexer;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.search.spi.reindexer.IndexReindexer;

import java.util.Collection;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Jiaxu Wei
 */
@Component(service = IndexReindexerRegistry.class)
public class IndexReindexerRegistry {

	public IndexReindexer getIndexReindexer(String className) {
		return _serviceTrackerMap.getService(className);
	}

	public Set<String> getIndexReindexerClassNames() {
		return _serviceTrackerMap.keySet();
	}

	public Collection<IndexReindexer> getIndexReindexers() {
		return _serviceTrackerMap.values();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, IndexReindexer.class, null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(indexReindexer, emitter) -> {
					Class<? extends IndexReindexer> clazz =
						indexReindexer.getClass();

					emitter.emit(clazz.getName());
				}));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, IndexReindexer> _serviceTrackerMap;

}