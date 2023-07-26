/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.spring.extender.internal.configuration;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ServiceComponentLocalService;

import java.util.Dictionary;

import org.apache.felix.dm.DependencyManager;
import org.apache.felix.dm.ServiceDependency;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Preston Crary
 */
@Component(service = {})
public class ServiceConfigurationExtender
	implements BundleTrackerCustomizer<org.apache.felix.dm.Component> {

	@Override
	public org.apache.felix.dm.Component addingBundle(
		Bundle bundle, BundleEvent bundleEvent) {

		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		if (headers.get("Liferay-Service") == null) {
			return null;
		}

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		ClassLoader classLoader = bundleWiring.getClassLoader();

		Configuration serviceConfiguration =
			ConfigurationFactoryUtil.getConfiguration(classLoader, "service");

		if (serviceConfiguration == null) {
			return null;
		}

		String requireSchemaVersion = headers.get(
			"Liferay-Require-SchemaVersion");

		ServiceConfigurationInitializer serviceConfigurationInitializer =
			new ServiceConfigurationInitializer(
				bundle, classLoader, serviceConfiguration,
				_serviceComponentLocalService);

		org.apache.felix.dm.Component component =
			_dependencyManager.createComponent();

		component.setImplementation(serviceConfigurationInitializer);

		if (requireSchemaVersion == null) {
			return null;
		}

		String versionRangeFilter = null;

		// See LPS-76926

		try {
			Version version = new Version(requireSchemaVersion);

			versionRangeFilter = _getVersionRangerFilter(version);
		}
		catch (IllegalArgumentException illegalArgumentException1) {
			try {
				VersionRange versionRange = new VersionRange(
					requireSchemaVersion);

				versionRangeFilter = versionRange.toFilterString(
					"release.schema.version");
			}
			catch (IllegalArgumentException illegalArgumentException2) {
				illegalArgumentException1.addSuppressed(
					illegalArgumentException2);

				if (_log.isWarnEnabled()) {
					_log.warn(
						"Invalid \"Liferay-Require-SchemaVersion\" header " +
							"for bundle: " + bundle.getBundleId(),
						illegalArgumentException1);
				}
			}
		}

		if (versionRangeFilter == null) {
			return null;
		}

		ServiceDependency serviceDependency =
			_dependencyManager.createServiceDependency();

		serviceDependency.setRequired(true);
		serviceDependency.setService(
			Release.class,
			StringBundler.concat(
				"(&(release.bundle.symbolic.name=", bundle.getSymbolicName(),
				")", versionRangeFilter, ")"));

		component.add(serviceDependency);

		_dependencyManager.add(component);

		return component;
	}

	@Override
	public void modifiedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		org.apache.felix.dm.Component component) {
	}

	@Override
	public void removedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		org.apache.felix.dm.Component component) {

		_dependencyManager.remove(component);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_dependencyManager = new DependencyManager(bundleContext);

		_bundleTracker = new BundleTracker<>(
			bundleContext, Bundle.ACTIVE, this);

		_bundleTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();
	}

	private String _getVersionRangerFilter(Version version) {
		return StringBundler.concat(
			"(&(release.schema.version>=", version.getMajor(), ".",
			version.getMinor(), ".0)(!(release.schema.version>=",
			version.getMajor() + 1, ".0.0)))");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServiceConfigurationExtender.class);

	private BundleTracker<?> _bundleTracker;
	private DependencyManager _dependencyManager;

	@Reference
	private ServiceComponentLocalService _serviceComponentLocalService;

}