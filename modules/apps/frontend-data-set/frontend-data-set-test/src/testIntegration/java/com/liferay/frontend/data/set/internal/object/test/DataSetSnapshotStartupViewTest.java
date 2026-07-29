/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.object.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.frontend.data.set.test.util.FrontendDataSetTestUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.Serializable;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Juanjo Fernandez
 */
@FeatureFlags(
	featureFlags = {@FeatureFlag("LPD-34594"), @FeatureFlag("LPS-164563")}
)
@RunWith(Arquillian.class)
public class DataSetSnapshotStartupViewTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		FrontendDataSetTestUtil.initialize(
			DataSetSnapshotStartupViewTest.class);

		_dataSetSnapshotObjectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_DATA_SET_SNAPSHOT", TestPropsValues.getCompanyId());

		Assert.assertNotNull(_dataSetSnapshotObjectDefinition);

		_dataSetSnapshotStartupViewObjectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_DATA_SET_SNAPSHOT_STARTUP_VIEW",
					TestPropsValues.getCompanyId());

		Assert.assertNotNull(_dataSetSnapshotStartupViewObjectDefinition);
	}

	@Test
	public void testDeleteDataSetSnapshotDeletesStartupView() throws Exception {
		String fdsName = RandomTestUtil.randomString();

		ObjectEntry dataSetSnapshotObjectEntry = _addDataSetSnapshotObjectEntry(
			fdsName);

		ObjectEntry dataSetSnapshotStartupViewObjectEntry =
			_addDataSetSnapshotStartupViewObjectEntry(
				dataSetSnapshotObjectEntry.getExternalReferenceCode(), fdsName);

		_objectEntryLocalService.deleteObjectEntry(
			dataSetSnapshotObjectEntry.getObjectEntryId());

		Assert.assertNull(
			_objectEntryLocalService.fetchObjectEntry(
				dataSetSnapshotStartupViewObjectEntry.getObjectEntryId()));
	}

	@Test
	public void testStoreDataSetSnapshotERC() throws Exception {
		String dataSetSnapshotERC = RandomTestUtil.randomString();
		String fdsName = RandomTestUtil.randomString();

		ObjectEntry objectEntry = _addDataSetSnapshotStartupViewObjectEntry(
			dataSetSnapshotERC, fdsName);

		try {
			Map<String, Serializable> values = objectEntry.getValues();

			Assert.assertEquals(
				dataSetSnapshotERC, values.get("dataSetSnapshotERC"));
			Assert.assertEquals(fdsName, values.get("fdsName"));
		}
		finally {
			_objectEntryLocalService.deleteObjectEntry(
				objectEntry.getObjectEntryId());
		}
	}

	private ObjectEntry _addDataSetSnapshotObjectEntry(String fdsName)
		throws Exception {

		return _objectEntryLocalService.addObjectEntry(
			0, TestPropsValues.getUserId(),
			_dataSetSnapshotObjectDefinition.getObjectDefinitionId(), 0, null,
			HashMapBuilder.<String, Serializable>put(
				"fdsName", fdsName
			).put(
				"label", RandomTestUtil.randomString()
			).build(),
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));
	}

	private ObjectEntry _addDataSetSnapshotStartupViewObjectEntry(
			String dataSetSnapshotERC, String fdsName)
		throws Exception {

		return _objectEntryLocalService.addObjectEntry(
			0, TestPropsValues.getUserId(),
			_dataSetSnapshotStartupViewObjectDefinition.getObjectDefinitionId(),
			0, null,
			HashMapBuilder.<String, Serializable>put(
				"dataSetSnapshotERC", dataSetSnapshotERC
			).put(
				"fdsName", fdsName
			).build(),
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));
	}

	private ObjectDefinition _dataSetSnapshotObjectDefinition;
	private ObjectDefinition _dataSetSnapshotStartupViewObjectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

}