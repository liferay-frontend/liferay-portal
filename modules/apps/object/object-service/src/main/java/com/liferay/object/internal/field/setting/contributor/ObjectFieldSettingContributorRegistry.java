/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.field.setting.contributor;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Feliphe Marinho
 */
@Component(service = ObjectFieldSettingContributorRegistry.class)
public class ObjectFieldSettingContributorRegistry {

	public ObjectFieldSettingContributor getObjectFieldSettingContributor(
		String key) {

		ObjectFieldSettingContributor objectFieldSettingContributor =
			_serviceTrackerMap.getService(key);

		if (objectFieldSettingContributor != null) {
			return objectFieldSettingContributor;
		}

		return _serviceTrackerMap.getService("default");
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ObjectFieldSettingContributor.class,
			"object.field.setting.type.key");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, ObjectFieldSettingContributor>
		_serviceTrackerMap;

}