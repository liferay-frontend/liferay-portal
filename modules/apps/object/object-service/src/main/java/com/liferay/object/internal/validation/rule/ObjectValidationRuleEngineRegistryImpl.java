/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.validation.rule;

import com.liferay.object.validation.rule.ObjectValidationRuleEngine;
import com.liferay.object.validation.rule.ObjectValidationRuleEngineRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Marco Leo
 */
@Component(service = ObjectValidationRuleEngineRegistry.class)
public class ObjectValidationRuleEngineRegistryImpl
	implements ObjectValidationRuleEngineRegistry {

	@Override
	public ObjectValidationRuleEngine getObjectValidationRuleEngine(
		String key) {

		return _serviceTrackerMap.getService(key);
	}

	@Override
	public List<ObjectValidationRuleEngine> getObjectValidationRuleEngines() {
		return new ArrayList(_serviceTrackerMap.values());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ObjectValidationRuleEngine.class, null,
			new ServiceReferenceMapper<String, ObjectValidationRuleEngine>() {

				@Override
				public void map(
					ServiceReference<ObjectValidationRuleEngine>
						serviceReference,
					Emitter<String> emitter) {

					ObjectValidationRuleEngine objectValidationRuleEngine =
						bundleContext.getService(serviceReference);

					emitter.emit(objectValidationRuleEngine.getName());
				}

			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, ObjectValidationRuleEngine>
		_serviceTrackerMap;

}