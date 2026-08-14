/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.upgrade.v1_2_0;

import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Juanjo Fernandez
 */
public class DataSetFragmentEntryLinkClassNameUpgradeProcess
	extends UpgradeProcess {

	public DataSetFragmentEntryLinkClassNameUpgradeProcess(
		ObjectDefinitionLocalService objectDefinitionLocalService) {

		_objectDefinitionLocalService = objectDefinitionLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select companyId, ctCollectionId, editableValues, " +
					"fragmentEntryLinkId, rendererKey from FragmentEntryLink " +
						"where rendererKey in (?, ?)");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update FragmentEntryLink set editableValues = ?, " +
						"rendererKey = ? where ctCollectionId = ? and " +
							"fragmentEntryLinkId = ?")) {

			preparedStatement1.setString(1, _RENDERER_KEY);
			preparedStatement1.setString(2, _RENDERER_KEY_LEGACY);

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					JSONObject editableValuesJSONObject =
						JSONFactoryUtil.createJSONObject(
							resultSet.getString("editableValues"));

					boolean migrated = _migrateEditableValues(
						resultSet.getLong("companyId"),
						editableValuesJSONObject);

					if (!migrated &&
						Objects.equals(
							resultSet.getString("rendererKey"),
							_RENDERER_KEY)) {

						continue;
					}

					preparedStatement2.setString(
						1, editableValuesJSONObject.toString());
					preparedStatement2.setString(2, _RENDERER_KEY);
					preparedStatement2.setLong(
						3, resultSet.getLong("ctCollectionId"));
					preparedStatement2.setLong(
						4, resultSet.getLong("fragmentEntryLinkId"));

					preparedStatement2.addBatch();
				}
			}

			preparedStatement2.executeBatch();
		}
	}

	private String _getDataSetClassName(long companyId) {
		String className = _dataSetClassNames.get(companyId);

		if (className != null) {
			return className;
		}

		ObjectDefinition dataSetObjectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_DATA_SET", companyId);

		if (dataSetObjectDefinition == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"The L_DATA_SET object definition was not found for " +
						"company " + companyId);
			}

			return null;
		}

		className = dataSetObjectDefinition.getClassName();

		_dataSetClassNames.put(companyId, className);

		return className;
	}

	private boolean _migrateEditableValues(
		long companyId, JSONObject editableValuesJSONObject) {

		JSONObject configurationValuesJSONObject =
			editableValuesJSONObject.getJSONObject(
				FragmentEntryProcessorConstants.
					KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR);

		if (configurationValuesJSONObject == null) {
			return false;
		}

		JSONObject itemSelectorJSONObject =
			configurationValuesJSONObject.getJSONObject("itemSelector");

		if ((itemSelectorJSONObject == null) ||
			Validator.isNull(
				itemSelectorJSONObject.getString("externalReferenceCode")) ||
			Validator.isNotNull(
				itemSelectorJSONObject.getString("className"))) {

			return false;
		}

		String className = _getDataSetClassName(companyId);

		if (className == null) {
			return false;
		}

		itemSelectorJSONObject.put("className", className);

		long classPK = itemSelectorJSONObject.getLong("id");

		if (classPK != 0) {
			itemSelectorJSONObject.put("classPK", classPK);
		}

		return true;
	}

	private static final String _RENDERER_KEY =
		"com.liferay.frontend.data.set.fragment.web.internal.fragment." +
			"renderer.FDSFragmentRenderer";

	private static final String _RENDERER_KEY_LEGACY =
		"com.liferay.frontend.data.set.admin.web.internal.fragment.renderer." +
			"FDSAdminFragmentRenderer";

	private static final Log _log = LogFactoryUtil.getLog(
		DataSetFragmentEntryLinkClassNameUpgradeProcess.class);

	private final Map<Long, String> _dataSetClassNames = new HashMap<>();
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;

}