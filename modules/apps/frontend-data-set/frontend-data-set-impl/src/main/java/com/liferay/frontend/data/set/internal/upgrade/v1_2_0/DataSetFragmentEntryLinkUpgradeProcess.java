/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.upgrade.v1_2_0;

import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Objects;

/**
 * @author Juanjo Fernandez
 */
public class DataSetFragmentEntryLinkUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select ctCollectionId, fragmentEntryLinkId, configuration, " +
					"editableValues, rendererKey from FragmentEntryLink " +
						"where rendererKey in (?, ?)");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update FragmentEntryLink set configuration = ?, " +
						"editableValues = ?, rendererKey = ? where " +
							"ctCollectionId = ? and fragmentEntryLinkId = ?")) {

			preparedStatement1.setString(1, _RENDERER_KEY);
			preparedStatement1.setString(2, _RENDERER_KEY_LEGACY);

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					boolean migrated = false;

					String configuration = resultSet.getString("configuration");

					JSONObject configurationJSONObject =
						JSONFactoryUtil.createJSONObject(configuration);

					if (_migrateConfiguration(configurationJSONObject)) {
						configuration = configurationJSONObject.toString();

						migrated = true;
					}

					JSONObject editableValuesJSONObject =
						JSONFactoryUtil.createJSONObject(
							resultSet.getString("editableValues"));

					if (_migrateEditableValues(editableValuesJSONObject)) {
						migrated = true;
					}

					if (!migrated &&
						Objects.equals(
							resultSet.getString("rendererKey"),
							_RENDERER_KEY)) {

						continue;
					}

					preparedStatement2.setString(1, configuration);
					preparedStatement2.setString(
						2, editableValuesJSONObject.toString());
					preparedStatement2.setString(3, _RENDERER_KEY);
					preparedStatement2.setLong(
						4, resultSet.getLong("ctCollectionId"));
					preparedStatement2.setLong(
						5, resultSet.getLong("fragmentEntryLinkId"));

					preparedStatement2.addBatch();
				}
			}

			preparedStatement2.executeBatch();
		}
	}

	private boolean _migrateConfiguration(JSONObject configurationJSONObject) {
		JSONArray fieldSetsJSONArray = configurationJSONObject.getJSONArray(
			"fieldSets");

		if (fieldSetsJSONArray == null) {
			return false;
		}

		boolean migrated = false;

		for (int i = 0; i < fieldSetsJSONArray.length(); i++) {
			JSONObject fieldSetJSONObject = fieldSetsJSONArray.getJSONObject(i);

			JSONArray fieldsJSONArray = fieldSetJSONObject.getJSONArray(
				"fields");

			if (fieldsJSONArray == null) {
				continue;
			}

			for (int j = 0; j < fieldsJSONArray.length(); j++) {
				JSONObject fieldJSONObject = fieldsJSONArray.getJSONObject(j);

				if (!Objects.equals(
						fieldJSONObject.getString("name"), "itemSelector")) {

					continue;
				}

				fieldJSONObject.put(
					"name", "dataSet"
				).put(
					"type", "dataSetSelector"
				);

				fieldJSONObject.remove("typeOptions");

				migrated = true;
			}
		}

		return migrated;
	}

	private boolean _migrateEditableValues(
		JSONObject editableValuesJSONObject) {

		JSONObject configurationValuesJSONObject =
			editableValuesJSONObject.getJSONObject(
				FragmentEntryProcessorConstants.
					KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR);

		if (configurationValuesJSONObject == null) {
			return false;
		}

		JSONObject itemSelectorJSONObject =
			configurationValuesJSONObject.getJSONObject("itemSelector");

		if (itemSelectorJSONObject == null) {
			return false;
		}

		configurationValuesJSONObject.remove("itemSelector");

		String externalReferenceCode = itemSelectorJSONObject.getString(
			"externalReferenceCode");

		if (Validator.isNotNull(externalReferenceCode)) {
			configurationValuesJSONObject.put(
				"dataSet",
				JSONUtil.put("externalReferenceCode", externalReferenceCode));
		}

		return true;
	}

	private static final String _RENDERER_KEY =
		"com.liferay.frontend.data.set.fragment.web.internal.fragment." +
			"renderer.FDSFragmentRenderer";

	private static final String _RENDERER_KEY_LEGACY =
		"com.liferay.frontend.data.set.admin.web.internal.fragment.renderer." +
			"FDSAdminFragmentRenderer";

}