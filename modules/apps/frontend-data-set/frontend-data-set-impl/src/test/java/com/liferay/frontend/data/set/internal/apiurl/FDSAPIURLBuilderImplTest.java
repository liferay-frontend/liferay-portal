/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.apiurl;

import com.liferay.frontend.data.set.apiurl.FDSAPIURLBuilder;
import com.liferay.frontend.data.set.internal.resolver.FDSAPIURLResolverRegistryImpl;
import com.liferay.frontend.data.set.resolver.FDSAPIURLResolver;
import com.liferay.frontend.data.set.resolver.FDSAPIURLResolverRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Daniel Sanz
 */
public class FDSAPIURLBuilderImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_bundleContext = SystemBundleUtil.getBundleContext();

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			_bundleContext, FDSAPIURLResolver.class, "fds.rest.application.key",
			ServiceTrackerCustomizerFactory.<FDSAPIURLResolver>serviceWrapper(
				_bundleContext));

		ReflectionTestUtil.setFieldValue(
			_fdsAPIURLResolverRegistry, "_serviceTrackerMap",
			_serviceTrackerMap);

		ReflectionTestUtil.setFieldValue(
			_fdsAPIURLBuilderFactoryImpl, "_fdsAPIURLResolverRegistry",
			_fdsAPIURLResolverRegistry);

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			themeDisplay.getScopeGroupId()
		).thenReturn(
			12345L
		);

		Mockito.when(
			themeDisplay.getUserId()
		).thenReturn(
			33333L
		);

		Mockito.when(
			_httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);
	}

	@After
	public void tearDown() {
		_serviceTrackerMap.close();
	}

	@Test
	public void testURLNotAffectedByRegisteredResolvers() throws Exception {
		ServiceRegistration<FDSAPIURLResolver> serviceRegistration1 =
			_registerResolver(
				"/app2", "schema", new String[] {"{foo}"},
				new String[] {"bar"});

		ServiceRegistration<FDSAPIURLResolver> serviceRegistration2 =
			_registerResolver(
				"/app", "schema2", new String[] {"{foo}"},
				new String[] {"bar"});

		FDSAPIURLBuilder fdsapiurlBuilder = _fdsAPIURLBuilderFactoryImpl.create(
			"/app", "/{foo}/endpoint", "schema", _httpServletRequest);

		Assert.assertEquals("/o/app/{foo}/endpoint", fdsapiurlBuilder.build());

		serviceRegistration1.unregister();

		serviceRegistration2.unregister();
	}

	@Test
	public void testURLSimple() throws Exception {
		FDSAPIURLBuilder fdsapiurlBuilder = _fdsAPIURLBuilderFactoryImpl.create(
			"/app", "/endpoint", "schema", _httpServletRequest);

		Assert.assertEquals("/o/app/endpoint", fdsapiurlBuilder.build());
	}

	@Test
	public void testURLSimpleWithParameters() throws Exception {
		FDSAPIURLBuilder fdsapiurlBuilder = _fdsAPIURLBuilderFactoryImpl.create(
			"/app", "/endpoint", "schema", _httpServletRequest);

		fdsapiurlBuilder.addParameter("param1", "value1");

		fdsapiurlBuilder.addParameter("param2", "value2");

		Assert.assertEquals(
			"/o/app/endpoint?param1=value1&param2=value2",
			fdsapiurlBuilder.build());
	}

	@Test
	public void testURLSimpleWithVersionDuplication() throws Exception {
		FDSAPIURLBuilder fdsapiurlBuilder = _fdsAPIURLBuilderFactoryImpl.create(
			"/app/v1.0", "/v1.0/endpoint", "schema", _httpServletRequest);

		Assert.assertEquals("/o/app/v1.0/endpoint", fdsapiurlBuilder.build());
	}

	@Test
	public void testURLSimpleWithVersionInRESTApplication() throws Exception {
		FDSAPIURLBuilder fdsapiurlBuilder = _fdsAPIURLBuilderFactoryImpl.create(
			"/app/v1.0", "/endpoint", "schema", _httpServletRequest);

		Assert.assertEquals("/o/app/endpoint", fdsapiurlBuilder.build());
	}

	@Test
	public void testURLSimpleWithVersionInRESTEndpoint() throws Exception {
		FDSAPIURLBuilder fdsapiurlBuilder = _fdsAPIURLBuilderFactoryImpl.create(
			"/app", "/v1.0/endpoint", "schema", _httpServletRequest);

		Assert.assertEquals("/o/app/v1.0/endpoint", fdsapiurlBuilder.build());
	}

	@Test
	public void testURLWithDefaultAndResolverInterpolations() throws Exception {
		ServiceRegistration<FDSAPIURLResolver> serviceRegistration =
			_registerResolver(
				"/app", "schema", new String[] {"{foo}"}, new String[] {"bar"});

		FDSAPIURLBuilder fdsapiurlBuilder = _fdsAPIURLBuilderFactoryImpl.create(
			"/app", "/{siteId}/{foo}/endpoint", "schema", _httpServletRequest);

		Assert.assertEquals(
			"/o/app/12345/bar/endpoint", fdsapiurlBuilder.build());

		serviceRegistration.unregister();
	}

	@Test
	public void testURLWithDefaultAndResolverInterpolationsSameToken()
		throws Exception {

		ServiceRegistration<FDSAPIURLResolver> serviceRegistration =
			_registerResolver(
				"/app", "schema", new String[] {"{foo}", "{userId}"},
				new String[] {"bar", "54321"});

		FDSAPIURLBuilder fdsapiurlBuilder = _fdsAPIURLBuilderFactoryImpl.create(
			"/app", "/{siteId}/{foo}/{userId}/endpoint", "schema",
			_httpServletRequest);

		Assert.assertEquals(
			"/o/app/12345/bar/54321/endpoint", fdsapiurlBuilder.build());

		serviceRegistration.unregister();
	}

	@Test
	public void testURLWithDefaultInterpolationOnly() throws Exception {
		FDSAPIURLBuilder fdsapiurlBuilder = _fdsAPIURLBuilderFactoryImpl.create(
			"/app", "/{userId}/endpoint", "schema", _httpServletRequest);

		Assert.assertEquals("/o/app/33333/endpoint", fdsapiurlBuilder.build());
	}

	@Test
	public void testURLWithParametersAndInterpolations() throws Exception {
		ServiceRegistration<FDSAPIURLResolver> serviceRegistration =
			_registerResolver(
				"/app", "schema", new String[] {"{foo}"}, new String[] {"bar"});

		FDSAPIURLBuilder fdsapiurlBuilder = _fdsAPIURLBuilderFactoryImpl.create(
			"/app", "/{foo}/endpoint", "schema", _httpServletRequest);

		fdsapiurlBuilder.addParameter("siteId", "{siteId}");

		fdsapiurlBuilder.addParameter("foo", "{foo}");

		fdsapiurlBuilder.addParameter("{foo}", "{userId}");

		Assert.assertEquals(
			"/o/app/bar/endpoint?siteId=12345&foo=bar&bar=33333",
			fdsapiurlBuilder.build());

		serviceRegistration.unregister();
	}

	@Test
	public void testURLWithResolverForNoTokens() throws Exception {
		ServiceRegistration<FDSAPIURLResolver> serviceRegistration =
			_registerResolver(
				"/app", "schema", new String[] {"{foo}"}, new String[] {"bar"});

		FDSAPIURLBuilder fdsapiurlBuilder = _fdsAPIURLBuilderFactoryImpl.create(
			"/app", "/{xyz}/endpoint", "schema", _httpServletRequest);

		Assert.assertEquals("/o/app/{xyz}/endpoint", fdsapiurlBuilder.build());

		serviceRegistration.unregister();
	}

	@Test
	public void testURLWithResolverForSeveralTokens() throws Exception {
		ServiceRegistration<FDSAPIURLResolver> serviceRegistration =
			_registerResolver(
				"/app", "schema", new String[] {"{foo}", "{userId}"},
				new String[] {"bar", "54321"});

		FDSAPIURLBuilder fdsapiurlBuilder = _fdsAPIURLBuilderFactoryImpl.create(
			"/app", "/{foo}/{userId}/endpoint", "schema", _httpServletRequest);

		Assert.assertEquals(
			"/o/app/bar/54321/endpoint", fdsapiurlBuilder.build());

		serviceRegistration.unregister();
	}

	private ServiceRegistration<FDSAPIURLResolver> _registerResolver(
		String restApplication, String restSchema, String[] tokens,
		String[] values) {

		return _bundleContext.registerService(
			FDSAPIURLResolver.class,
			new FDSAPIURLResolver() {

				@Override
				public String getSchema() {
					return restSchema;
				}

				@Override
				public String resolve(
						String baseURL, HttpServletRequest httpServletRequest)
					throws PortalException {

					return StringUtil.replace(baseURL, tokens, values);
				}

			},
			MapUtil.singletonDictionary(
				"fds.rest.application.key",
				restApplication + "/" + restSchema));
	}

	private static BundleContext _bundleContext;
	private static final FDSAPIURLBuilderFactoryImpl
		_fdsAPIURLBuilderFactoryImpl = new FDSAPIURLBuilderFactoryImpl();
	private static final FDSAPIURLResolverRegistry _fdsAPIURLResolverRegistry =
		new FDSAPIURLResolverRegistryImpl();
	private static final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private static ServiceTrackerMap<String, ServiceWrapper<FDSAPIURLResolver>>
		_serviceTrackerMap;

}