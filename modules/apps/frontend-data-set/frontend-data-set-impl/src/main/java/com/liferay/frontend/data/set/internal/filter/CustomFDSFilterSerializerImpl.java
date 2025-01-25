/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.filter;

import com.liferay.client.extension.type.FDSFilterCET;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.frontend.data.set.constants.FDSEntityFieldTypes;
import com.liferay.frontend.data.set.filter.FDSFilterSerializer;
import com.liferay.frontend.data.set.internal.serializer.BaseCustomFDSSerializer;
import com.liferay.frontend.data.set.serializer.FDSSerializer;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.time.Instant;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Sanz
 * @author Marko Cikos
 */
@Component(
	property = "frontend.data.set.serializer.type=" + FDSSerializer.CUSTOM,
	service = FDSFilterSerializer.class
)
public class CustomFDSFilterSerializerImpl
	extends BaseCustomFDSSerializer implements FDSFilterSerializer {

	@Override
	public JSONArray serialize(
		String fdsName, HttpServletRequest httpServletRequest) {

		try {
			return _getFiltersJSONArray(fdsName, httpServletRequest);
		}
		catch (Exception exception) {
			_log.error("Failed to serialize filter", exception);

			return _jsonFactory.createJSONArray();
		}
	}

	private JSONObject _getDateJSONObject(Object isoDate) {
		if (isoDate == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(Date.from(Instant.parse(String.valueOf(isoDate))));

		return JSONUtil.put(
			"day", calendar.get(Calendar.DATE)
		).put(
			"month", calendar.get(Calendar.MONTH) + 1
		).put(
			"year", calendar.get(Calendar.YEAR)
		);
	}

	private JSONArray _getFiltersJSONArray(
			String fdsName, HttpServletRequest httpServletRequest)
		throws Exception {

		return JSONUtil.toJSONArray(
			getFilterObjectEntries(fdsName, httpServletRequest),
			(ObjectEntry objectEntry) -> {
				Map<String, Object> properties = objectEntry.getProperties();

				String fieldName = String.valueOf(properties.get("fieldName"));

				fieldName = fieldName.replaceAll(
					"(\\[\\]|\\.)", StringPool.FORWARD_SLASH);

				String type = MapUtil.getString(properties, "type");

				if (Objects.equals(type, "date") ||
					Objects.equals(type, "date-time")) {

					JSONObject fromJSONObject = _getDateJSONObject(
						properties.get("from"));
					JSONObject toJSONObject = _getDateJSONObject(
						properties.get("to"));

					boolean hasPreloadedData =
						(fromJSONObject != null) || (toJSONObject != null);

					return JSONUtil.put(
						"active", hasPreloadedData
					).put(
						"entityFieldType",
						Objects.equals(type, "date") ?
							FDSEntityFieldTypes.DATE :
								FDSEntityFieldTypes.DATE_TIME
					).put(
						"id", fieldName
					).put(
						"label", getLabelValue("label", "fieldName", properties)
					).put(
						"preloadedData",
						() -> {
							if (!hasPreloadedData) {
								return null;
							}

							return JSONUtil.put(
								"from", fromJSONObject
							).put(
								"to", toJSONObject
							);
						}
					).put(
						"type", "dateRange"
					);
				}

				String source = MapUtil.getString(properties, "source");

				if (Validator.isNotNull(source)) {
					String finalFieldName = fieldName;
					String sourceType = MapUtil.getString(
						properties, "sourceType");

					JSONObject selectionFilterJSONObject = JSONUtil.put(
						"autocompleteEnabled", true
					).put(
						"entityFieldType", FDSEntityFieldTypes.STRING
					).put(
						"id",
						() -> {
							if (Objects.equals(
									sourceType, "API_REST_APPLICATION")) {

								return finalFieldName;
							}

							int index = finalFieldName.lastIndexOf(
								StringPool.FORWARD_SLASH);

							if (index <= 0) {
								return finalFieldName;
							}

							return finalFieldName.substring(0, index);
						}
					).put(
						"label", getLabelValue("label", "fieldName", properties)
					).put(
						"multiple", properties.get("multiple")
					).put(
						"type", "selection"
					);

					if (Validator.isNotNull(sourceType) &&
						Objects.equals(sourceType, "API_REST_APPLICATION")) {

						return selectionFilterJSONObject.put(
							"apiURL", source
						).put(
							"itemKey", properties.get("itemKey")
						).put(
							"itemLabel", properties.get("itemLabel")
						).put(
							"preloadedData",
							() -> {
								JSONArray selectedItemsJSONArray =
									_jsonFactory.createJSONArray(
										MapUtil.getString(
											properties, "preselectedValues"));

								if (JSONUtil.isEmpty(selectedItemsJSONArray)) {
									return null;
								}

								return JSONUtil.put(
									"exclude",
									() -> Boolean.FALSE.equals(
										(Boolean)properties.get("include"))
								).put(
									"selectedItems", selectedItemsJSONArray
								);
							}
						);
					}

					ThemeDisplay themeDisplay =
						(ThemeDisplay)httpServletRequest.getAttribute(
							WebKeys.THEME_DISPLAY);

					ListTypeDefinition listTypeDefinition =
						_listTypeDefinitionLocalService.
							getListTypeDefinitionByExternalReferenceCode(
								source, themeDisplay.getCompanyId());

					List<ListTypeEntry> listTypeEntries =
						_listTypeEntryLocalService.getListTypeEntries(
							listTypeDefinition.getListTypeDefinitionId());

					return selectionFilterJSONObject.put(
						"items",
						JSONUtil.toJSONArray(
							listTypeEntries,
							listTypeEntry -> JSONUtil.put(
								"key", listTypeEntry.getKey()
							).put(
								"label",
								listTypeEntry.getName(themeDisplay.getLocale())
							).put(
								"value", listTypeEntry.getKey()
							))
					).put(
						"preloadedData",
						() -> {
							JSONArray selectedItemsJSONArray =
								_getSelectedItemsJSONArray(
									listTypeEntries, themeDisplay.getLocale(),
									MapUtil.getString(
										properties, "preselectedValues"));

							if (JSONUtil.isEmpty(selectedItemsJSONArray)) {
								return null;
							}

							return JSONUtil.put(
								"exclude",
								() -> Boolean.FALSE.equals(
									(Boolean)properties.get("include"))
							).put(
								"selectedItems", selectedItemsJSONArray
							);
						}
					);
				}

				String clientExtensionEntryERC = MapUtil.getString(
					properties, "clientExtensionEntryERC");

				if (Validator.isNotNull(clientExtensionEntryERC)) {
					ThemeDisplay themeDisplay =
						(ThemeDisplay)httpServletRequest.getAttribute(
							WebKeys.THEME_DISPLAY);

					FDSFilterCET fdsFilterCET =
						(FDSFilterCET)_cetManager.getCET(
							themeDisplay.getCompanyId(),
							clientExtensionEntryERC);

					if (fdsFilterCET == null) {
						_log.error(
							StringBundler.concat(
								"No frontend data set filter client extension ",
								"exists with the external reference code ",
								clientExtensionEntryERC));

						return null;
					}

					return JSONUtil.put(
						"clientExtensionFilterURL", fdsFilterCET.getURL()
					).put(
						"entityFieldType", FDSEntityFieldTypes.STRING
					).put(
						"id", fieldName
					).put(
						"label", getLabelValue("label", "fieldName", properties)
					).put(
						"type", "clientExtension"
					);
				}

				return null;
			});
	}

	private JSONArray _getSelectedItemsJSONArray(
			List<ListTypeEntry> listTypeEntries, Locale locale,
			String preselectedValues)
		throws JSONException {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		JSONArray preselectedValuesJSONArray = _jsonFactory.createJSONArray(
			preselectedValues);

		for (int i = 0; i < preselectedValuesJSONArray.length(); i++) {
			JSONObject jsonObject = preselectedValuesJSONArray.getJSONObject(i);

			for (ListTypeEntry listTypeEntry : listTypeEntries) {
				if (Objects.equals(
						listTypeEntry.getExternalReferenceCode(),
						jsonObject.getString("value"))) {

					jsonArray.put(
						JSONUtil.put(
							"label", listTypeEntry.getName(locale)
						).put(
							"value", listTypeEntry.getKey()
						));

					break;
				}
			}
		}

		return jsonArray;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CustomFDSFilterSerializerImpl.class);

	@Reference
	private CETManager _cetManager;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

}