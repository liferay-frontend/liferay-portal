/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.filter;

import com.liferay.frontend.data.set.SystemFDSEntry;
import com.liferay.frontend.data.set.constants.FDSEntityFieldTypes;
import com.liferay.frontend.data.set.filter.BaseClientExtensionFDSFilter;
import com.liferay.frontend.data.set.filter.BaseDateRangeFDSFilter;
import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;
import com.liferay.frontend.data.set.filter.DateFDSFilterItem;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilterContextContributor;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import com.liferay.frontend.data.set.internal.BaseSystemFDSSerializerTestCase;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.osgi.framework.ServiceRegistration;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Daniel Sanz
 */
public class SystemFDSFilterSerializerImplTest
	extends BaseSystemFDSSerializerTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_filterServiceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, FDSFilter.class, "frontend.data.set.name",
			ServiceTrackerCustomizerFactory.<FDSFilter>serviceWrapper(
				bundleContext));

		_filterContextContributorServiceTrackerMap =
			ServiceTrackerMapFactory.openMultiValueMap(
				bundleContext, FDSFilterContextContributor.class,
				"frontend.data.set.filter.type",
				ServiceTrackerCustomizerFactory.
					<FDSFilterContextContributor>serviceWrapper(bundleContext));

		ReflectionTestUtil.setFieldValue(
			_fdsFilterRegistryImpl, "_serviceTrackerMap",
			_filterServiceTrackerMap);

		ReflectionTestUtil.setFieldValue(
			_fdsFilterContextContributorRegistryImpl, "_serviceTrackerMap",
			_filterContextContributorServiceTrackerMap);

		ReflectionTestUtil.setFieldValue(
			_systemFDSFilterSerializerImpl, "_fdsFilterRegistry",
			_fdsFilterRegistryImpl);

		ReflectionTestUtil.setFieldValue(
			_systemFDSFilterSerializerImpl,
			"_fdsFilterContextContributorRegistry",
			_fdsFilterContextContributorRegistryImpl);

		ReflectionTestUtil.setFieldValue(
			_systemFDSFilterSerializerImpl, "_jsonFactory", _jsonFactory);

		ReflectionTestUtil.setFieldValue(
			_systemFDSFilterSerializerImpl, "_language", language);

		ReflectionTestUtil.setFieldValue(
			_systemFDSFilterSerializerImpl, "_portal", portal);

		ReflectionTestUtil.setFieldValue(
			_dateRangeFDSFilterContextContributor, "_jsonFactory",
			_jsonFactory);

		ReflectionTestUtil.setFieldValue(
			_selectionFDSFilterContextContributor, "_jsonFactory",
			_jsonFactory);

		ReflectionTestUtil.setFieldValue(
			_selectionFDSFilterContextContributor, "_language", language);

		_clientExtensionFDSFilterContextContributorServiceRegistration =
			bundleContext.registerService(
				FDSFilterContextContributor.class,
				_clientExtensionFDSFilterContextContributor,
				MapUtil.singletonDictionary(
					"frontend.data.set.filter.type", "clientExtension"));

		_dateRangeFDSFilterContextContributorServiceRegistration =
			bundleContext.registerService(
				FDSFilterContextContributor.class,
				_dateRangeFDSFilterContextContributor,
				MapUtil.singletonDictionary(
					"frontend.data.set.filter.type", "dateRange"));

		_selectionFDSFilterContextContributorServiceRegistration =
			bundleContext.registerService(
				FDSFilterContextContributor.class,
				_selectionFDSFilterContextContributor,
				MapUtil.singletonDictionary(
					"frontend.data.set.filter.type", "selection"));
	}

	@After
	public void tearDown() {
		super.tearDown();

		_clientExtensionFDSFilterContextContributorServiceRegistration.
			unregister();
		_dateRangeFDSFilterContextContributorServiceRegistration.unregister();
		_selectionFDSFilterContextContributorServiceRegistration.unregister();

		_filterServiceTrackerMap.close();
	}

	@Test
	public void testFDSClientExtensionFilterSerialization() throws Exception {
		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration =
			registerSystemFDSEntry("fdsName", "/app", "/endpoint", "schema");

		ServiceRegistration<FDSFilter>
			clientExtensionFilterServiceRegistration = _registerFilter(
				"fdsName",
				_createClientExtensionFilter(
					"fooField", "Foo label", "/o/foo-filter/bar.js",
					new HashMapBuilder<>().<String, Object>put(
						"fooParam1", "bar1"
					).put(
						"fooParam2", "bar2"
					).build()));

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"clientExtensionFilterURL", "/o/foo-filter/bar.js"
				).put(
					"id", "fooField"
				).put(
					"label", "Foo label"
				).put(
					"preloadedData",
					JSONUtil.put(
						"fooParam1", "bar1"
					).put(
						"fooParam2", "bar2"
					)
				).put(
					"type", "clientExtension"
				)
			).toString(),
			_systemFDSFilterSerializerImpl.serialize(
				"fdsName", httpServletRequest
			).toString(),
			JSONCompareMode.LENIENT);

		clientExtensionFilterServiceRegistration.unregister();

		systemFDSEntryServiceRegistration.unregister();
	}

	@Test
	public void testFDSDateRangeFilterSerialization() throws Exception {
		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration =
			registerSystemFDSEntry("fdsName", "/app", "/endpoint", "schema");

		ServiceRegistration<FDSFilter> dateRangeFilterServiceRegistration =
			_registerFilter(
				"fdsName",
				_createDateRangeFilter(
					"createDate", "By Creation Date", FDSEntityFieldTypes.DATE,
					new HashMapBuilder<>().<String, Object>put(
						"from", new DateFDSFilterItem(30, 11, 1985)
					).put(
						"to", new DateFDSFilterItem(27, 5, 1995)
					).build(),
					new DateFDSFilterItem(0, 0, 0),
					new DateFDSFilterItem(16, 3, 1977)));

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"entityFieldType", "date"
				).put(
					"id", "createDate"
				).put(
					"label", "By Creation Date"
				).put(
					"max",
					JSONUtil.put(
						"day", 16
					).put(
						"month", 3
					).put(
						"year", 1977
					)
				).put(
					"min",
					JSONUtil.put(
						"day", 0
					).put(
						"month", 0
					).put(
						"year", 0
					)
				).put(
					"preloadedData",
					JSONUtil.put(
						"from",
						JSONUtil.put(
							"day", 30
						).put(
							"month", 11
						).put(
							"year", 1985
						)
					).put(
						"to",
						JSONUtil.put(
							"day", 27
						).put(
							"month", 5
						).put(
							"year", 1995
						)
					)
				).put(
					"type", "dateRange"
				)
			).toString(),
			_systemFDSFilterSerializerImpl.serialize(
				"fdsName", httpServletRequest
			).toString(),
			JSONCompareMode.LENIENT);

		dateRangeFilterServiceRegistration.unregister();

		systemFDSEntryServiceRegistration.unregister();
	}

	@Test
	public void testFDSFilterSerializationDisabledFilter() throws Exception {
		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration =
			registerSystemFDSEntry("fdsName", "/app", "/endpoint", "schema");

		ServiceRegistration<FDSFilter> fdsFilterServiceRegistration =
			_registerFilter(
				"fdsName",
				new FDSFilter() {

					@Override
					public String getId() {
						return "id";
					}

					@Override
					public String getLabel() {
						return "label";
					}

					@Override
					public String getType() {
						return "type";
					}

					@Override
					public boolean isEnabled() {
						return false;
					}

				});

		JSONAssert.assertEquals(
			"[]",
			_systemFDSFilterSerializerImpl.serialize(
				"fdsName", httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		systemFDSEntryServiceRegistration.unregister();

		fdsFilterServiceRegistration.unregister();
	}

	@Test
	public void testFDSFilterSerializationNoFilter() throws Exception {
		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration =
			registerSystemFDSEntry("fdsName", "/app", "/endpoint", "schema");

		JSONAssert.assertEquals(
			"[]",
			_systemFDSFilterSerializerImpl.serialize(
				"fdsName", httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		systemFDSEntryServiceRegistration.unregister();
	}

	@Test
	public void testFDSFilterSerializationSeparateFilters() throws Exception {
		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration1 =
			registerSystemFDSEntry("fdsName1", "/app", "/endpoint", "schema");

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration2 =
			registerSystemFDSEntry("fdsName2", "/app", "/endpoint", "schema");

		ServiceRegistration<FDSFilter> dateRangeFilterServiceRegistration1 =
			_registerFilter(
				"fdsName1",
				_createDateRangeFilter(
					"createDate", "By Creation Date", FDSEntityFieldTypes.DATE,
					null, new DateFDSFilterItem(0, 0, 0),
					new DateFDSFilterItem(1, 1, 1980)));

		ServiceRegistration<FDSFilter> dateRangeFilterServiceRegistration2 =
			_registerFilter(
				"fdsName2",
				_createDateRangeFilter(
					"modifiedDate", "By Modification Date",
					FDSEntityFieldTypes.DATE, null,
					new DateFDSFilterItem(0, 0, 0),
					new DateFDSFilterItem(1, 1, 1980)));

		String dateRangeFilterSerialized1 =
			_systemFDSFilterSerializerImpl.serialize(
				"fdsName1", httpServletRequest
			).toString();

		String dateRangeFilterSerialized2 =
			_systemFDSFilterSerializerImpl.serialize(
				"fdsName2", httpServletRequest
			).toString();

		JSONAssert.assertNotEquals(
			dateRangeFilterSerialized1, dateRangeFilterSerialized2,
			JSONCompareMode.LENIENT);

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"entityFieldType", "date"
				).put(
					"id", "createDate"
				).put(
					"label", "By Creation Date"
				).put(
					"max",
					JSONUtil.put(
						"day", 1
					).put(
						"month", 1
					).put(
						"year", 1980
					)
				).put(
					"min",
					JSONUtil.put(
						"day", 0
					).put(
						"month", 0
					).put(
						"year", 0
					)
				).put(
					"type", "dateRange"
				)
			).toString(),
			dateRangeFilterSerialized1, JSONCompareMode.LENIENT);

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"entityFieldType", "date"
				).put(
					"id", "modifiedDate"
				).put(
					"label", "By Modification Date"
				).put(
					"max",
					JSONUtil.put(
						"day", 1
					).put(
						"month", 1
					).put(
						"year", 1980
					)
				).put(
					"min",
					JSONUtil.put(
						"day", 0
					).put(
						"month", 0
					).put(
						"year", 0
					)
				).put(
					"type", "dateRange"
				)
			).toString(),
			dateRangeFilterSerialized2, JSONCompareMode.LENIENT);

		dateRangeFilterServiceRegistration1.unregister();

		dateRangeFilterServiceRegistration2.unregister();

		systemFDSEntryServiceRegistration1.unregister();

		systemFDSEntryServiceRegistration2.unregister();
	}

	@Test
	public void testFDSFilterSerializationSharingFilter() throws Exception {
		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration1 =
			registerSystemFDSEntry("fdsName1", "/app", "/endpoint", "schema");

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration2 =
			registerSystemFDSEntry("fdsName2", "/app", "/endpoint", "schema");

		FDSFilter dateRangeFilter = _createDateRangeFilter(
			"createDate", "By Creation Date", FDSEntityFieldTypes.DATE, null,
			new DateFDSFilterItem(0, 0, 0), new DateFDSFilterItem(1, 1, 1980));

		ServiceRegistration<FDSFilter> dateRangeFilterServiceRegistration1 =
			_registerFilter("fdsName1", dateRangeFilter);

		ServiceRegistration<FDSFilter> dateRangeFilterServiceRegistration2 =
			_registerFilter("fdsName2", dateRangeFilter);

		JSONAssert.assertEquals(
			_systemFDSFilterSerializerImpl.serialize(
				"fdsName1", httpServletRequest
			).toString(),
			_systemFDSFilterSerializerImpl.serialize(
				"fdsName2", httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		dateRangeFilterServiceRegistration1.unregister();

		dateRangeFilterServiceRegistration2.unregister();

		systemFDSEntryServiceRegistration1.unregister();

		systemFDSEntryServiceRegistration2.unregister();
	}

	@Test
	public void testFDSSelectionFilterWithAPIURLSerialization()
		throws Exception {

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration =
			registerSystemFDSEntry("fdsName", "/app", "/endpoint", "schema");

		ServiceRegistration<FDSFilter> selectionFilterServiceRegistration =
			_registerFilter(
				"fdsName",
				_createSelectionFilter(
					"categoryIds", "By Category",
					FDSEntityFieldTypes.COLLECTION,
					new HashMapBuilder<>().<String, Object>put(
						"exclude", false
					).build(),
					"/o/headless-admin-taxonomy/v1.0/taxonomy-categories/0" +
						"/taxonomy-categories?sort=name:asc",
					"id", "label", false));

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"apiURL",
					"/o/headless-admin-taxonomy/v1.0/taxonomy-" +
						"categories/0/taxonomy-categories?sort=name:asc"
				).put(
					"autocompleteEnabled", true
				).put(
					"entityFieldType", "collection"
				).put(
					"id", "categoryIds"
				).put(
					"inputPlaceholder", "search"
				).put(
					"label", "By Category"
				).put(
					"multiple", false
				).put(
					"preloadedData", JSONUtil.put("exclude", false)
				).put(
					"type", "selection"
				)
			).toString(),
			_systemFDSFilterSerializerImpl.serialize(
				"fdsName", httpServletRequest
			).toString(),
			JSONCompareMode.LENIENT);

		selectionFilterServiceRegistration.unregister();

		systemFDSEntryServiceRegistration.unregister();
	}

	@Test
	public void testFDSSelectionFilterWithItemsSerialization()
		throws Exception {

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration =
			registerSystemFDSEntry("fdsName", "/app", "/endpoint", "schema");

		ServiceRegistration<FDSFilter> selectionFilterServiceRegistration =
			_registerFilter(
				"fdsName",
				_createSelectionFilter(
					"categoryIds", "By Category",
					FDSEntityFieldTypes.COLLECTION,
					new HashMapBuilder<>().<String, Object>put(
						"exclude", true
					).build(),
					ListUtil.fromArray(
						new SelectionFDSFilterItem("animal", 1),
						new SelectionFDSFilterItem("vegetable", 2)),
					"id", "label", false, true));

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"autocompleteEnabled", false
				).put(
					"entityFieldType", "collection"
				).put(
					"id", "categoryIds"
				).put(
					"items",
					JSONUtil.putAll(
						JSONUtil.put(
							"label", "animal"
						).put(
							"value", 1
						),
						JSONUtil.put(
							"label", "vegetable"
						).put(
							"value", 2
						))
				).put(
					"label", "By Category"
				).put(
					"multiple", true
				).put(
					"preloadedData", JSONUtil.put("exclude", true)
				).put(
					"type", "selection"
				)
			).toString(),
			_systemFDSFilterSerializerImpl.serialize(
				"fdsName", httpServletRequest
			).toString(),
			JSONCompareMode.LENIENT);

		selectionFilterServiceRegistration.unregister();

		systemFDSEntryServiceRegistration.unregister();
	}

	private FDSFilter _createClientExtensionFilter(
		String id, String label, String moduleURL,
		Map<String, Object> preloadedData) {

		return new BaseClientExtensionFDSFilter() {

			@Override
			public String getId() {
				return id;
			}

			@Override
			public String getLabel() {
				return label;
			}

			@Override
			public String getModuleURL() {
				return moduleURL;
			}

			@Override
			public Map<String, Object> getPreloadedData() {
				return preloadedData;
			}

		};
	}

	private FDSFilter _createDateRangeFilter(
		String id, String label, String entityFieldType,
		Map<String, Object> preloadedData, DateFDSFilterItem min,
		DateFDSFilterItem max) {

		return new BaseDateRangeFDSFilter() {

			@Override
			public String getEntityFieldType() {
				return entityFieldType;
			}

			@Override
			public String getId() {
				return id;
			}

			@Override
			public String getLabel() {
				return label;
			}

			@Override
			public DateFDSFilterItem getMaxDateFDSFilterItem() {
				return max;
			}

			@Override
			public DateFDSFilterItem getMinDateFDSFilterItem() {
				return min;
			}

			@Override
			public Map<String, Object> getPreloadedData() {
				return preloadedData;
			}

		};
	}

	private FDSFilter _createSelectionFilter(
		String id, String label, String entityFieldType,
		Map<String, Object> preloadedData,
		List<SelectionFDSFilterItem> selectionFDSFilterItems, String itemKey,
		String itemLabel, boolean autocompleteEnabled, boolean multiple) {

		return new BaseSelectionFDSFilter() {

			@Override
			public String getEntityFieldType() {
				return entityFieldType;
			}

			@Override
			public String getId() {
				return id;
			}

			@Override
			public String getItemKey() {
				return itemKey;
			}

			@Override
			public String getItemLabel() {
				return itemLabel;
			}

			@Override
			public String getLabel() {
				return label;
			}

			@Override
			public Map<String, Object> getPreloadedData() {
				return preloadedData;
			}

			@Override
			public List<SelectionFDSFilterItem> getSelectionFDSFilterItems(
				Locale locale) {

				return selectionFDSFilterItems;
			}

			@Override
			public boolean isAutocompleteEnabled() {
				return autocompleteEnabled;
			}

			@Override
			public boolean isMultiple() {
				return multiple;
			}

		};
	}

	private FDSFilter _createSelectionFilter(
		String id, String label, String entityFieldType,
		Map<String, Object> preloadedData, String apiURL, String itemKey,
		String itemLabel, boolean multiple) {

		return new BaseSelectionFDSFilter() {

			@Override
			public String getAPIURL() {
				return apiURL;
			}

			@Override
			public String getEntityFieldType() {
				return entityFieldType;
			}

			@Override
			public String getId() {
				return id;
			}

			@Override
			public String getItemKey() {
				return itemKey;
			}

			@Override
			public String getItemLabel() {
				return itemLabel;
			}

			@Override
			public String getLabel() {
				return label;
			}

			@Override
			public Map<String, Object> getPreloadedData() {
				return preloadedData;
			}

			@Override
			public boolean isAutocompleteEnabled() {
				return true;
			}

			@Override
			public boolean isMultiple() {
				return multiple;
			}

		};
	}

	private ServiceRegistration<FDSFilter> _registerFilter(
		String fdsName, FDSFilter fdsFilter) {

		return bundleContext.registerService(
			FDSFilter.class, fdsFilter,
			MapUtil.singletonDictionary("frontend.data.set.name", fdsName));
	}

	private static final ClientExtensionFDSFilterContextContributor
		_clientExtensionFDSFilterContextContributor =
			new ClientExtensionFDSFilterContextContributor();
	private static final DateRangeFDSFilterContextContributor
		_dateRangeFDSFilterContextContributor =
			new DateRangeFDSFilterContextContributor();
	private static final FDSFilterRegistryImpl _fdsFilterRegistryImpl =
		new FDSFilterRegistryImpl();
	private static ServiceTrackerMap
		<String,
		 List<ServiceTrackerCustomizerFactory.ServiceWrapper<FDSFilter>>>
			_filterServiceTrackerMap;
	private static final JSONFactory _jsonFactory = new JSONFactoryImpl();
	private static final SelectionFDSFilterContextContributor
		_selectionFDSFilterContextContributor =
			new SelectionFDSFilterContextContributor();

	private ServiceRegistration<FDSFilterContextContributor>
		_clientExtensionFDSFilterContextContributorServiceRegistration;
	private ServiceRegistration<FDSFilterContextContributor>
		_dateRangeFDSFilterContextContributorServiceRegistration;
	private final FDSFilterContextContributorRegistryImpl
		_fdsFilterContextContributorRegistryImpl =
			new FDSFilterContextContributorRegistryImpl();
	private ServiceTrackerMap
		<String,
		 List
			 <ServiceTrackerCustomizerFactory.ServiceWrapper
				 <FDSFilterContextContributor>>>
					_filterContextContributorServiceTrackerMap;
	private ServiceRegistration<FDSFilterContextContributor>
		_selectionFDSFilterContextContributorServiceRegistration;
	private final SystemFDSFilterSerializerImpl _systemFDSFilterSerializerImpl =
		new SystemFDSFilterSerializerImpl();

}