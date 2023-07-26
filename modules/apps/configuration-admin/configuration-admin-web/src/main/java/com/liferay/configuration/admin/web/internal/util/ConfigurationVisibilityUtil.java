/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.web.internal.util;

import com.liferay.configuration.admin.display.ConfigurationVisibilityController;
import com.liferay.configuration.admin.web.internal.model.ConfigurationModel;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Drew Brokke
 */
public class ConfigurationVisibilityUtil {

	public static boolean isVisible(
		ConfigurationModel configurationModel,
		ExtendedObjectClassDefinition.Scope scope, Serializable scopePK) {

		if (configurationModel.isStrictScope() &&
			!scope.equals(configurationModel.getScope())) {

			return false;
		}

		ConfigurationVisibilityController configurationVisibilityController =
			_serviceTrackerMap.getService(
				configurationModel.getVisibilityControllerKey());

		if (configurationVisibilityController != null) {
			return configurationVisibilityController.isVisible(scope, scopePK);
		}

		return true;
	}

	public static boolean isVisible(
		String pid, ExtendedObjectClassDefinition.Scope scope,
		Serializable scopePK) {

		ConfigurationVisibilityController configurationVisibilityController =
			_serviceTrackerMap.getService(pid);

		if (configurationVisibilityController != null) {
			return configurationVisibilityController.isVisible(scope, scopePK);
		}

		configurationVisibilityController = _serviceTrackerMap.getService(
			_getVisibilityControllerKey(pid));

		if (configurationVisibilityController != null) {
			return configurationVisibilityController.isVisible(scope, scopePK);
		}

		return true;
	}

	public static boolean isVisibleByVisibilityControllerKey(
		String visibilityControllerKey,
		ExtendedObjectClassDefinition.Scope scope, Serializable scopePK) {

		if (Validator.isNull(visibilityControllerKey)) {
			return true;
		}

		ConfigurationVisibilityController configurationVisibilityController =
			_serviceTrackerMap.getService(visibilityControllerKey);

		if (configurationVisibilityController == null) {
			return true;
		}

		return configurationVisibilityController.isVisible(scope, scopePK);
	}

	private static Class<?> _getClass(Bundle bundle, String pid) {
		try {
			return bundle.loadClass(pid);
		}
		catch (ClassNotFoundException classNotFoundException) {
			if (_log.isDebugEnabled()) {
				_log.debug(classNotFoundException);
			}
		}

		return null;
	}

	private static Class<?> _getClass(String pid) {
		Class<?> clazz = null;

		for (Bundle bundle : _bundleContext.getBundles()) {
			if (pid.startsWith(bundle.getSymbolicName())) {
				clazz = _getClass(bundle, pid);
			}

			if (clazz != null) {
				return clazz;
			}
		}

		for (Bundle bundle : _bundleContext.getBundles()) {
			clazz = _getClass(bundle, pid);

			if (clazz != null) {
				return clazz;
			}
		}

		return null;
	}

	private static String _getVisibilityControllerKey(String pid) {
		Class<?> clazz = _getClass(pid);

		if (clazz == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No class found for pid " + pid);
			}

			return pid;
		}

		ExtendedObjectClassDefinition annotation = clazz.getAnnotation(
			ExtendedObjectClassDefinition.class);

		if (annotation == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"No ExtendedObjectClassDefinition annotation found on ",
						"class ", pid));
			}

			return pid;
		}

		String visibilityControllerKey = annotation.visibilityControllerKey();

		if (Validator.isBlank(visibilityControllerKey)) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No visibilityControllerKey attribute found on class " +
						pid);
			}

			return pid;
		}

		return visibilityControllerKey;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ConfigurationVisibilityUtil.class);

	private static final BundleContext _bundleContext;
	private static final ServiceTrackerMap
		<String, ConfigurationVisibilityController> _serviceTrackerMap;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ConfigurationVisibilityController.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_bundleContext = bundleContext;

		ServiceReferenceMapper<String, ConfigurationVisibilityController>
			configurationPidPropertyMapper =
				new PropertyServiceReferenceMapper<>("configuration.pid");
		ServiceReferenceMapper<String, ConfigurationVisibilityController>
			visibilityControllerKeyPropertyMapper =
				new PropertyServiceReferenceMapper<>(
					"visibility.controller.key");

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ConfigurationVisibilityController.class, null,
			(serviceReference, emitter) -> {
				configurationPidPropertyMapper.map(serviceReference, emitter);
				visibilityControllerKeyPropertyMapper.map(
					serviceReference, emitter);

				ConfigurationVisibilityController service =
					bundleContext.getService(serviceReference);

				String key = service.getKey();

				if (!Validator.isBlank(key)) {
					emitter.emit(key);
				}
			});
	}

}