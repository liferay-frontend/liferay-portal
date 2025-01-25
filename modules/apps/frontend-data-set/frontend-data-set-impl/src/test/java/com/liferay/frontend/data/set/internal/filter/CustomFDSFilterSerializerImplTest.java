/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.filter;

import com.liferay.client.extension.type.FDSFilterCET;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.frontend.data.set.constants.FDSEntityFieldTypes;
import com.liferay.frontend.data.set.internal.serializer.BaseCustomFDSSerializer;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Daniel Sanz
 */
public class CustomFDSFilterSerializerImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_customFDSFilterSerializerImpl = Mockito.mock(
			CustomFDSFilterSerializerImpl.class);

		ReflectionTestUtil.setFieldValue(
			_customFDSFilterSerializerImpl, "_jsonFactory", _jsonFactory);

		ReflectionTestUtil.setFieldValue(
			_customFDSFilterSerializerImpl, "_cetManager", _cetManager);

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			themeDisplay.getCompanyId()
		).thenReturn(
			0L
		);

		Mockito.when(
			_httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);
	}

	@Test
	public void testFDSClientExtensionFilterSerialization() throws Exception {
		_mockFDSClientExtensionFilterObjectEntry(
			"fdsName", "channelId", "By Channel CX",
			"LXC:filter-client-extension");

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"clientExtensionFilterURL",
					"/o/LXC:filter-client-extension/index.js"
				).put(
					"entityFieldType", "string"
				).put(
					"id", "channelId"
				).put(
					"label", "By Channel CX"
				).put(
					"type", "clientExtension"
				)
			).toString(),
			_customFDSFilterSerializerImpl.serialize(
				"fdsName", _httpServletRequest
			).toString(),
			JSONCompareMode.LENIENT);
	}

	@Test
	public void testFDSDateRangeFilterSerialization() throws Exception {
		_mockFDSDateRangeFilterObjectEntry(
			"fdsName", "createDate", "By Creation Date",
			FDSEntityFieldTypes.DATE, "2000-12-31T00:00:00.000Z",
			"2025-10-03T00:00:00.000Z");

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"active", true
				).put(
					"entityFieldType", "date"
				).put(
					"id", "createDate"
				).put(
					"label", "By Creation Date"
				).put(
					"preloadedData",
					JSONUtil.put(
						"from",
						JSONUtil.put(
							"day", 31
						).put(
							"month", 12
						).put(
							"year", 2000
						)
					).put(
						"to",
						JSONUtil.put(
							"day", 3
						).put(
							"month", 10
						).put(
							"year", 2025
						)
					)
				).put(
					"type", "dateRange"
				)
			).toString(),
			_customFDSFilterSerializerImpl.serialize(
				"fdsName", _httpServletRequest
			).toString(),
			JSONCompareMode.LENIENT);
	}

	@Test
	public void testFDSFilterSerializationNoFilter() throws Exception {
		_mockFDSEmptyFilterObjectEntry("fdsName");

		JSONAssert.assertEquals(
			"[]",
			_customFDSFilterSerializerImpl.serialize(
				"fdsName", _httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);
	}

	@Test
	public void testFDSFilterSerializationSeparateFilters() throws Exception {
		_mockFDSDateRangeFilterObjectEntry(
			"fdsName1", "createDate", "By Creation Date",
			FDSEntityFieldTypes.DATE, "2000-12-31T00:00:00.000Z", null);

		_mockFDSDateRangeFilterObjectEntry(
			"fdsName2", "modifiedDate", "By Modification Date",
			FDSEntityFieldTypes.DATE, null, "2025-10-03T00:00:00.000Z");

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"active", true
				).put(
					"entityFieldType", "date"
				).put(
					"id", "createDate"
				).put(
					"label", "By Creation Date"
				).put(
					"preloadedData",
					JSONUtil.put(
						"from",
						JSONUtil.put(
							"day", 31
						).put(
							"month", 12
						).put(
							"year", 2000
						))
				).put(
					"type", "dateRange"
				)
			).toString(),
			_customFDSFilterSerializerImpl.serialize(
				"fdsName1", _httpServletRequest
			).toString(),
			JSONCompareMode.LENIENT);

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"active", true
				).put(
					"entityFieldType", "date"
				).put(
					"id", "modifiedDate"
				).put(
					"label", "By Modification Date"
				).put(
					"preloadedData",
					JSONUtil.put(
						"to",
						JSONUtil.put(
							"day", 3
						).put(
							"month", 10
						).put(
							"year", 2025
						))
				).put(
					"type", "dateRange"
				)
			).toString(),
			_customFDSFilterSerializerImpl.serialize(
				"fdsName2", _httpServletRequest
			).toString(),
			JSONCompareMode.LENIENT);
	}

	@Test
	public void testFDSSelectionFilterSerialization() throws Exception {
		_mockFDSSelectionFilterObjectEntry(
			"fdsName", "channelId", true, "channelId", "name", "By Channel",
			true, "[{\"label\":\"site 1\",\"value\":\"20192\"}]",
			"/analytics-settings-rest/v1.0", "/v1.0/channels", "Channel",
			"/o/analytics-settings-rest/v1.0/channels", "API_REST_APPLICATION");

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"apiURL", "/o/analytics-settings-rest/v1.0/channels"
				).put(
					"autocompleteEnabled", true
				).put(
					"entityFieldType", "string"
				).put(
					"id", "channelId"
				).put(
					"itemKey", "channelId"
				).put(
					"itemLabel", "name"
				).put(
					"label", "By Channel"
				).put(
					"multiple", true
				).put(
					"preloadedData",
					JSONUtil.put(
						"exclude", false
					).put(
						"selectedItems",
						JSONUtil.putAll(
							JSONUtil.put(
								"label", "site 1"
							).put(
								"value", "20192"
							))
					)
				).put(
					"type", "selection"
				)
			).toString(),
			_customFDSFilterSerializerImpl.serialize(
				"fdsName", _httpServletRequest
			).toString(),
			JSONCompareMode.LENIENT);
	}

	private void _mockFDSClientExtensionFilterObjectEntry(
		String fdsName, String fieldName, String label,
		String clientExtensionEntryERC) {

		Mockito.when(
			_customFDSFilterSerializerImpl.serialize(
				fdsName, _httpServletRequest)
		).thenCallRealMethod();

		BaseCustomFDSSerializer baseCustomFDSSerializer =
			(BaseCustomFDSSerializer)_customFDSFilterSerializerImpl;

		Mockito.when(
			baseCustomFDSSerializer.getLabelValue(
				Mockito.eq("label"), Mockito.eq("fieldName"), Mockito.anyMap())
		).thenCallRealMethod();

		Set<ObjectEntry> objectEntries = new HashSet<>();

		ObjectEntry objectEntry = new ObjectEntry();

		objectEntry.setProperties(
			HashMapBuilder.put(
				"clientExtensionEntryERC", (Object)clientExtensionEntryERC
			).put(
				"fieldName", (Object)fieldName
			).put(
				"label", (Object)label
			).build());

		objectEntries.add(objectEntry);

		Mockito.when(
			baseCustomFDSSerializer.getFilterObjectEntries(
				fdsName, _httpServletRequest)
		).thenReturn(
			objectEntries
		);

		Mockito.when(
			_cetManager.getCET(
				Mockito.anyLong(), Mockito.eq(clientExtensionEntryERC))
		).thenAnswer(
			invocation -> new FDSFilterCET() {

				@Override
				public String getBaseURL() {
					return "";
				}

				@Override
				public long getCompanyId() {
					return invocation.getArgument(0, long.class);
				}

				@Override
				public Date getCreateDate() {
					return null;
				}

				@Override
				public String getDescription() {
					return "";
				}

				@Override
				public String getEditJSP() {
					return "";
				}

				@Override
				public String getExternalReferenceCode() {
					return clientExtensionEntryERC;
				}

				@Override
				public Date getModifiedDate() {
					return null;
				}

				@Override
				public String getName() {
					return "";
				}

				@Override
				public String getName(Locale locale) {
					return "";
				}

				@Override
				public Properties getProperties() {
					return null;
				}

				@Override
				public String getSourceCodeURL() {
					return "";
				}

				@Override
				public int getStatus() {
					return 0;
				}

				@Override
				public String getType() {
					return "";
				}

				@Override
				public String getTypeSettings() {
					return "";
				}

				@Override
				public String getURL() {
					return "/o/" + clientExtensionEntryERC + "/index.js";
				}

				@Override
				public boolean hasProperties() {
					return false;
				}

				@Override
				public boolean isReadOnly() {
					return false;
				}

			}
		);
	}

	private void _mockFDSDateRangeFilterObjectEntry(
		String fdsName, String fieldName, String label, String type,
		String from, String to) {

		Mockito.when(
			_customFDSFilterSerializerImpl.serialize(
				fdsName, _httpServletRequest)
		).thenCallRealMethod();

		BaseCustomFDSSerializer baseCustomFDSSerializer =
			(BaseCustomFDSSerializer)_customFDSFilterSerializerImpl;

		Mockito.when(
			baseCustomFDSSerializer.getLabelValue(
				Mockito.eq("label"), Mockito.eq("fieldName"), Mockito.anyMap())
		).thenCallRealMethod();

		Set<ObjectEntry> objectEntries = new HashSet<>();

		ObjectEntry objectEntry = new ObjectEntry();

		objectEntry.setProperties(
			HashMapBuilder.put(
				"fieldName", (Object)fieldName
			).put(
				"from", (Object)from
			).put(
				"label", (Object)label
			).put(
				"to", (Object)to
			).put(
				"type", (Object)type
			).build());

		objectEntries.add(objectEntry);

		Mockito.when(
			baseCustomFDSSerializer.getFilterObjectEntries(
				fdsName, _httpServletRequest)
		).thenReturn(
			objectEntries
		);
	}

	private void _mockFDSEmptyFilterObjectEntry(String fdsName) {
		Mockito.when(
			_customFDSFilterSerializerImpl.serialize(
				fdsName, _httpServletRequest)
		).thenCallRealMethod();

		BaseCustomFDSSerializer baseCustomFDSSerializer =
			(BaseCustomFDSSerializer)_customFDSFilterSerializerImpl;

		Mockito.when(
			baseCustomFDSSerializer.getFilterObjectEntries(
				fdsName, _httpServletRequest)
		).thenReturn(
			Collections.emptySet()
		);
	}

	private void _mockFDSSelectionFilterObjectEntry(
		String fdsName, String fieldName, boolean include, String itemKey,
		String itemLabel, String label, boolean multiple,
		String preselectedValues, String restApplication, String restEndpoint,
		String restSchema, String source, String sourceType) {

		Mockito.when(
			_customFDSFilterSerializerImpl.serialize(
				fdsName, _httpServletRequest)
		).thenCallRealMethod();

		BaseCustomFDSSerializer baseCustomFDSSerializer =
			(BaseCustomFDSSerializer)_customFDSFilterSerializerImpl;

		Mockito.when(
			baseCustomFDSSerializer.getLabelValue(
				Mockito.eq("label"), Mockito.eq("fieldName"), Mockito.anyMap())
		).thenCallRealMethod();

		Set<ObjectEntry> objectEntries = new HashSet<>();

		ObjectEntry objectEntry = new ObjectEntry();

		objectEntry.setProperties(
			HashMapBuilder.put(
				"fieldName", (Object)fieldName
			).put(
				"include", (Object)include
			).put(
				"itemKey", (Object)itemKey
			).put(
				"itemLabel", (Object)itemLabel
			).put(
				"label", (Object)label
			).put(
				"multiple", (Object)multiple
			).put(
				"preselectedValues", (Object)preselectedValues
			).put(
				"restApplication", (Object)restApplication
			).put(
				"restEndpoint", (Object)restEndpoint
			).put(
				"restSchema", (Object)restSchema
			).put(
				"source", (Object)source
			).put(
				"sourceType", (Object)sourceType
			).build());

		objectEntries.add(objectEntry);

		Mockito.when(
			baseCustomFDSSerializer.getFilterObjectEntries(
				fdsName, _httpServletRequest)
		).thenReturn(
			objectEntries
		);
	}

	private static final CETManager _cetManager = Mockito.mock(
		CETManager.class);
	private static CustomFDSFilterSerializerImpl _customFDSFilterSerializerImpl;
	private static final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private static final JSONFactory _jsonFactory = new JSONFactoryImpl();

}