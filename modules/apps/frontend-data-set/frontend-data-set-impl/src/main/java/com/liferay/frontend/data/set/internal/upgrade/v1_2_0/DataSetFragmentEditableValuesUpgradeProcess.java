/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.upgrade.v1_2_0;

import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Juanjo Fernandez
 */
public class DataSetFragmentEditableValuesUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select ctCollectionId, fragmentEntryLinkId, editableValues " +
					"from FragmentEntryLink where rendererKey in (?, ?)");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update FragmentEntryLink set editableValues = ? where " +
						"ctCollectionId = ? and fragmentEntryLinkId = ?")) {

			preparedStatement1.setString(1, _RENDERER_KEY);
			preparedStatement1.setString(2, _RENDERER_KEY_LEGACY);

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					JSONObject editableValuesJSONObject =
						JSONFactoryUtil.createJSONObject(
							resultSet.getString("editableValues"));

					JSONObject configurationJSONObject =
						editableValuesJSONObject.getJSONObject(
							FragmentEntryProcessorConstants.
								KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR);

					if (configurationJSONObject == null) {
						continue;
					}

					JSONObject itemSelectorJSONObject =
						configurationJSONObject.getJSONObject("itemSelector");

					if (itemSelectorJSONObject == null) {
						continue;
					}

					configurationJSONObject.remove("itemSelector");

					String externalReferenceCode =
						itemSelectorJSONObject.getString(
							"externalReferenceCode");

					if (Validator.isNotNull(externalReferenceCode)) {
						configurationJSONObject.put(
							"dataSet",
							JSONUtil.put(
								"externalReferenceCode",
								externalReferenceCode));
					}

					preparedStatement2.setString(
						1, editableValuesJSONObject.toString());
					preparedStatement2.setLong(
						2, resultSet.getLong("ctCollectionId"));
					preparedStatement2.setLong(
						3, resultSet.getLong("fragmentEntryLinkId"));

					preparedStatement2.addBatch();
				}
			}

			preparedStatement2.executeBatch();
		}
	}

	private static final String _RENDERER_KEY =
		"com.liferay.frontend.data.set.fragment.web.internal.fragment." +
			"renderer.FDSFragmentRenderer";

	private static final String _RENDERER_KEY_LEGACY =
		"com.liferay.frontend.data.set.admin.web.internal.fragment.renderer." +
			"FDSAdminFragmentRenderer";

}