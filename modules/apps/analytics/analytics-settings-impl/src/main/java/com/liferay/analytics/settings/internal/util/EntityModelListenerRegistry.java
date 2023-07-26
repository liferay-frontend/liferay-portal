/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.internal.util;

import com.liferay.analytics.message.sender.model.listener.EntityModelListener;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.model.BaseModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.Collection;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Rachael Koestartyo
 */
@Component(service = EntityModelListenerRegistry.class)
public class EntityModelListenerRegistry {

	public EntityModelListener<?> getEntityModelListener(String className) {
		return _serviceTrackerMap.getService(className);
	}

	public Collection<EntityModelListener<?>> getEntityModelListeners() {
		return _serviceTrackerMap.values();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext,
			(Class<EntityModelListener<?>>)(Class<?>)EntityModelListener.class,
			null, new EntityModelListenerServiceReferenceMapper());
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private BundleContext _bundleContext;
	private ServiceTrackerMap<String, EntityModelListener<?>>
		_serviceTrackerMap;

	private class EntityModelListenerServiceReferenceMapper
		<T extends BaseModel<T>>
			implements ServiceReferenceMapper<String, EntityModelListener<T>> {

		@Override
		public void map(
			ServiceReference<EntityModelListener<T>> serviceReference,
			Emitter<String> emitter) {

			EntityModelListener<?> entityModelListener =
				_bundleContext.getService(serviceReference);

			Class<?> clazz = _getParameterizedClass(
				entityModelListener.getClass());

			try {
				emitter.emit(clazz.getName());
			}
			finally {
				_bundleContext.ungetService(serviceReference);
			}
		}

		private Class<?> _getParameterizedClass(Class<?> clazz) {
			ParameterizedType parameterizedType =
				(ParameterizedType)clazz.getGenericSuperclass();

			Type[] types = parameterizedType.getActualTypeArguments();

			return (Class<?>)types[0];
		}

	}

}