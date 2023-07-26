/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scr.component.test.configuration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.osgi.util.service.OSGiServiceUtil;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.scr.component.test.configuration.TestComponent;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.runtime.ServiceComponentRuntime;
import org.osgi.service.component.runtime.dto.ComponentDescriptionDTO;
import org.osgi.util.promise.Promise;

/**
 * @author Mariano Álvaro Sáiz
 */
@RunWith(Arquillian.class)
public class OptionalConfigurationsComponentTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(TestComponent.class);

		_bundleContext = bundle.getBundleContext();

		_componentDescriptionDTO =
			_serviceComponentRuntime.getComponentDescriptionDTO(
				bundle, TestComponent.class.getName());
	}

	@After
	public void tearDown() throws Exception {
		ConfigurationTestUtil.deleteConfiguration(_FIRST_CONFIGURATION_PID);
		ConfigurationTestUtil.deleteConfiguration(_SECOND_CONFIGURATION_PID);
	}

	@Test
	public void testApplyAllOptionalConfigurations() throws Exception {
		_enable();

		OSGiServiceUtil.callService(
			_bundleContext, TestComponent.class,
			testComponent -> {
				Assert.assertEquals("empty", testComponent.getFirst());

				Assert.assertEquals("empty", testComponent.getSecond());

				return null;
			});

		_disable();

		ConfigurationTestUtil.saveConfiguration(
			_FIRST_CONFIGURATION_PID,
			MapUtil.singletonDictionary("first", "first-value"));

		ConfigurationTestUtil.saveConfiguration(
			_SECOND_CONFIGURATION_PID,
			MapUtil.singletonDictionary("second", "second-value"));

		_enable();

		OSGiServiceUtil.callService(
			_bundleContext, TestComponent.class,
			testComponent -> {
				Assert.assertEquals("first-value", testComponent.getFirst());

				Assert.assertEquals("second-value", testComponent.getSecond());

				return null;
			});

		_disable();
	}

	@Test
	public void testApplyFirstOptionalConfiguration() throws Exception {
		_enable();

		OSGiServiceUtil.callService(
			_bundleContext, TestComponent.class,
			testComponent -> {
				Assert.assertEquals("empty", testComponent.getFirst());

				return null;
			});

		_disable();

		ConfigurationTestUtil.saveConfiguration(
			_FIRST_CONFIGURATION_PID,
			MapUtil.singletonDictionary("first", "first-value"));

		_enable();

		OSGiServiceUtil.callService(
			_bundleContext, TestComponent.class,
			testComponent -> {
				Assert.assertEquals("first-value", testComponent.getFirst());

				return null;
			});

		_disable();
	}

	@Test
	public void testApplySecondOptionalConfiguration() throws Exception {
		_enable();

		OSGiServiceUtil.callService(
			_bundleContext, TestComponent.class,
			testComponent -> {
				Assert.assertEquals("empty", testComponent.getSecond());

				return null;
			});

		_disable();

		ConfigurationTestUtil.saveConfiguration(
			_FIRST_CONFIGURATION_PID,
			MapUtil.singletonDictionary("second", "second-value"));

		_enable();

		OSGiServiceUtil.callService(
			_bundleContext, TestComponent.class,
			testComponent -> {
				Assert.assertEquals("second-value", testComponent.getSecond());

				return null;
			});

		_disable();
	}

	private void _disable() throws Exception {
		Promise<Void> promise = _serviceComponentRuntime.disableComponent(
			_componentDescriptionDTO);

		promise.getValue();
	}

	private void _enable() throws Exception {
		Promise<Void> promise = _serviceComponentRuntime.enableComponent(
			_componentDescriptionDTO);

		promise.getValue();
	}

	private static final String _FIRST_CONFIGURATION_PID =
		"com.liferay.scr.component.test.configuration.FirstConfiguration";

	private static final String _SECOND_CONFIGURATION_PID =
		"com.liferay.scr.component.test.configuration.SecondConfiguration";

	private static BundleContext _bundleContext;
	private static ComponentDescriptionDTO _componentDescriptionDTO;

	@Inject
	private static ServiceComponentRuntime _serviceComponentRuntime;

}