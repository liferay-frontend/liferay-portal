/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.storage;

import com.liferay.dynamic.data.mapping.storage.StorageAdapter;
import com.liferay.dynamic.data.mapping.storage.StorageAdapterRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(service = StorageAdapterRegistry.class)
public class StorageAdapterRegistryImpl implements StorageAdapterRegistry {

	@Override
	public StorageAdapter getDefaultStorageAdapter() {
		return _defaultStorageAdapter;
	}

	@Override
	public StorageAdapter getStorageAdapter(String storageType) {
		return _serviceTrackerMap.getService(storageType);
	}

	@Override
	public Set<String> getStorageTypes() {
		return _serviceTrackerMap.keySet();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, StorageAdapter.class, null,
			ServiceReferenceMapperFactory.createFromFunction(
				bundleContext, StorageAdapter::getStorageType));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	@Reference(
		target = "(component.name=com.liferay.dynamic.data.mapping.internal.storage.DefaultStorageAdapter)"
	)
	private StorageAdapter _defaultStorageAdapter;

	private ServiceTrackerMap<String, StorageAdapter> _serviceTrackerMap;

}