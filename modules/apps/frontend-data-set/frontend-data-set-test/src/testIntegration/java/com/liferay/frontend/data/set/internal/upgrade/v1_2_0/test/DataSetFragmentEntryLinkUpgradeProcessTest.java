/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.upgrade.v1_2_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Juanjo Fernandez
 */
@FeatureFlag("LPS-164563")
@RunWith(Arquillian.class)
public class DataSetFragmentEntryLinkUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypeContentLayout(_group);
	}

	@Test
	public void testUpgrade() throws Exception {
		FragmentEntryLink fragmentEntryLink = _addFragmentEntryLink(
			JSONUtil.put(
				"className", RandomTestUtil.randomString()
			).put(
				"externalReferenceCode", _EXTERNAL_REFERENCE_CODE
			).put(
				"title", RandomTestUtil.randomString()
			),
			_RENDERER_KEY);

		FragmentEntryLink legacyFragmentEntryLink = _addFragmentEntryLink(
			JSONUtil.put(
				"className", RandomTestUtil.randomString()
			).put(
				"externalReferenceCode", _EXTERNAL_REFERENCE_CODE
			),
			_RENDERER_KEY_LEGACY);

		FragmentEntryLink unconfiguredFragmentEntryLink = _addFragmentEntryLink(
			JSONUtil.put("externalReferenceCode", StringPool.BLANK),
			_RENDERER_KEY);
		FragmentEntryLink legacyUnconfiguredFragmentEntryLink =
			_addFragmentEntryLink(null, _RENDERER_KEY_LEGACY);

		_runUpgrade();

		_assertMigrated(fragmentEntryLink);
		_assertMigrated(legacyFragmentEntryLink);

		JSONObject configurationJSONObject = _getConfigurationJSONObject(
			unconfiguredFragmentEntryLink);

		Assert.assertFalse(configurationJSONObject.has("dataSet"));
		Assert.assertFalse(configurationJSONObject.has("itemSelector"));

		_assertRendererKey(unconfiguredFragmentEntryLink);
		_assertRendererKey(legacyUnconfiguredFragmentEntryLink);
	}

	private FragmentEntryLink _addFragmentEntryLink(
			JSONObject itemSelectorJSONObject, String rendererKey)
		throws Exception {

		return _fragmentEntryLinkLocalService.addFragmentEntryLink(
			null, TestPropsValues.getUserId(), _group.getGroupId(), null, null,
			null, 0, _layout.getPlid(), StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, _LEGACY_CONFIGURATION,
			JSONUtil.put(
				FragmentEntryProcessorConstants.
					KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR,
				JSONUtil.put(
					"apiURLTokenMappings", StringPool.BLANK
				).put(
					"itemSelector", itemSelectorJSONObject
				)
			).toString(),
			StringPool.BLANK, 0, rendererKey, FragmentConstants.TYPE_COMPONENT,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	private void _assertConfiguration(FragmentEntryLink fragmentEntryLink)
		throws Exception {

		fragmentEntryLink = _fragmentEntryLinkLocalService.getFragmentEntryLink(
			fragmentEntryLink.getFragmentEntryLinkId());

		JSONObject configurationJSONObject =
			fragmentEntryLink.getConfigurationJSONObject();

		JSONArray fieldSetsJSONArray = configurationJSONObject.getJSONArray(
			"fieldSets");

		JSONObject fieldSetJSONObject = fieldSetsJSONArray.getJSONObject(0);

		JSONArray fieldsJSONArray = fieldSetJSONObject.getJSONArray("fields");

		JSONObject fieldJSONObject = fieldsJSONArray.getJSONObject(0);

		Assert.assertEquals("dataSet", fieldJSONObject.getString("name"));
		Assert.assertEquals(
			"dataSetSelector", fieldJSONObject.getString("type"));
		Assert.assertFalse(fieldJSONObject.has("typeOptions"));
	}

	private void _assertMigrated(FragmentEntryLink fragmentEntryLink)
		throws Exception {

		JSONObject configurationJSONObject = _getConfigurationJSONObject(
			fragmentEntryLink);

		Assert.assertFalse(configurationJSONObject.has("itemSelector"));

		JSONObject dataSetJSONObject = configurationJSONObject.getJSONObject(
			"dataSet");

		Assert.assertEquals(
			_EXTERNAL_REFERENCE_CODE,
			dataSetJSONObject.getString("externalReferenceCode"));
		Assert.assertEquals(1, dataSetJSONObject.length());

		_assertRendererKey(fragmentEntryLink);
		_assertConfiguration(fragmentEntryLink);
	}

	private void _assertRendererKey(FragmentEntryLink fragmentEntryLink)
		throws Exception {

		fragmentEntryLink = _fragmentEntryLinkLocalService.getFragmentEntryLink(
			fragmentEntryLink.getFragmentEntryLinkId());

		Assert.assertEquals(_RENDERER_KEY, fragmentEntryLink.getRendererKey());
	}

	private JSONObject _getConfigurationJSONObject(
			FragmentEntryLink fragmentEntryLink)
		throws Exception {

		fragmentEntryLink = _fragmentEntryLinkLocalService.getFragmentEntryLink(
			fragmentEntryLink.getFragmentEntryLinkId());

		JSONObject editableValuesJSONObject =
			fragmentEntryLink.getEditableValuesJSONObject();

		return editableValuesJSONObject.getJSONObject(
			FragmentEntryProcessorConstants.
				KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR);
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.frontend.data.set.internal.upgrade.v1_2_0." +
				"DataSetFragmentEntryLinkUpgradeProcess");

		upgradeProcess.upgrade();

		_entityCache.clearCache();
		_multiVMPool.clear();
	}

	private static final String _EXTERNAL_REFERENCE_CODE =
		RandomTestUtil.randomString();

	private static final String _LEGACY_CONFIGURATION = StringBundler.concat(
		"{\"fieldSets\": [{\"customComponentModule\": ",
		"\"{DataSetConfigurationFields} from ",
		"@liferay/frontend-data-set-fragment-web\", \"fields\": [{\"label\": ",
		"\"Data Set View\", \"name\": \"itemSelector\", \"type\": ",
		"\"itemSelector\", \"typeOptions\": {\"itemType\": \"FDSView\"}}], ",
		"\"label\": \"Data Set Options\"}]}");

	private static final String _RENDERER_KEY =
		"com.liferay.frontend.data.set.fragment.web.internal.fragment." +
			"renderer.FDSFragmentRenderer";

	private static final String _RENDERER_KEY_LEGACY =
		"com.liferay.frontend.data.set.admin.web.internal.fragment.renderer." +
			"FDSAdminFragmentRenderer";

	@Inject
	private EntityCache _entityCache;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject(
		filter = "component.name=com.liferay.frontend.data.set.internal.upgrade.registry.FrontendDataSetImplUpgradeStepRegistrator"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}