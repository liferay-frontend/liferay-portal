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
import com.liferay.frontend.data.set.test.util.FrontendDataSetTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.orm.EntityCache;
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
public class DataSetFragmentEntryLinkClassNameUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		FrontendDataSetTestUtil.initialize(
			DataSetFragmentEntryLinkClassNameUpgradeProcessTest.class);

		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testUpgrade() throws Exception {
		String externalReferenceCode = RandomTestUtil.randomString();
		long id = RandomTestUtil.randomLong();

		FragmentEntryLink fragmentEntryLink = _addFragmentEntryLink(
			JSONUtil.put(
				"externalReferenceCode", externalReferenceCode
			).put(
				"id", id
			).put(
				"label", RandomTestUtil.randomString()
			),
			_RENDERER_KEY);

		FragmentEntryLink legacyRendererKeyFragmentEntryLink =
			_addFragmentEntryLink(
				JSONUtil.put(
					"externalReferenceCode", externalReferenceCode
				).put(
					"id", id
				),
				_RENDERER_KEY_LEGACY);

		String className = RandomTestUtil.randomString();

		FragmentEntryLink migratedFragmentEntryLink = _addFragmentEntryLink(
			JSONUtil.put(
				"className", className
			).put(
				"externalReferenceCode", externalReferenceCode
			),
			_RENDERER_KEY);

		FragmentEntryLink unconfiguredFragmentEntryLink = _addFragmentEntryLink(
			JSONUtil.put("externalReferenceCode", StringPool.BLANK),
			_RENDERER_KEY);

		_runUpgrade();

		_assertMigrated(externalReferenceCode, fragmentEntryLink, id);
		_assertMigrated(
			externalReferenceCode, legacyRendererKeyFragmentEntryLink, id);

		JSONObject itemSelectorJSONObject = _getItemSelectorJSONObject(
			migratedFragmentEntryLink);

		Assert.assertEquals(
			className, itemSelectorJSONObject.getString("className"));
		Assert.assertEquals(0, itemSelectorJSONObject.getLong("classPK"));

		itemSelectorJSONObject = _getItemSelectorJSONObject(
			unconfiguredFragmentEntryLink);

		Assert.assertNull(itemSelectorJSONObject.getString("className", null));

		_assertRendererKey(unconfiguredFragmentEntryLink);
	}

	private FragmentEntryLink _addFragmentEntryLink(
			JSONObject itemSelectorJSONObject, String rendererKey)
		throws Exception {

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		return _fragmentEntryLinkLocalService.addFragmentEntryLink(
			null, TestPropsValues.getUserId(), _group.getGroupId(), null, null,
			null, 0, layout.getPlid(), StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, StringPool.BLANK,
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

	private void _assertMigrated(
			String externalReferenceCode, FragmentEntryLink fragmentEntryLink,
			long id)
		throws Exception {

		JSONObject itemSelectorJSONObject = _getItemSelectorJSONObject(
			fragmentEntryLink);

		ObjectDefinition dataSetObjectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_DATA_SET", TestPropsValues.getCompanyId());

		Assert.assertEquals(
			dataSetObjectDefinition.getClassName(),
			itemSelectorJSONObject.getString("className"));

		Assert.assertEquals(id, itemSelectorJSONObject.getLong("classPK"));
		Assert.assertEquals(
			externalReferenceCode,
			itemSelectorJSONObject.getString("externalReferenceCode"));

		_assertRendererKey(fragmentEntryLink);
	}

	private void _assertRendererKey(FragmentEntryLink fragmentEntryLink)
		throws Exception {

		fragmentEntryLink = _fragmentEntryLinkLocalService.getFragmentEntryLink(
			fragmentEntryLink.getFragmentEntryLinkId());

		Assert.assertEquals(_RENDERER_KEY, fragmentEntryLink.getRendererKey());
	}

	private JSONObject _getItemSelectorJSONObject(
			FragmentEntryLink fragmentEntryLink)
		throws Exception {

		fragmentEntryLink = _fragmentEntryLinkLocalService.getFragmentEntryLink(
			fragmentEntryLink.getFragmentEntryLinkId());

		JSONObject editableValuesJSONObject =
			fragmentEntryLink.getEditableValuesJSONObject();

		JSONObject configurationValuesJSONObject =
			editableValuesJSONObject.getJSONObject(
				FragmentEntryProcessorConstants.
					KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR);

		return configurationValuesJSONObject.getJSONObject("itemSelector");
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.frontend.data.set.internal.upgrade.v1_2_0." +
				"DataSetFragmentEntryLinkClassNameUpgradeProcess");

		upgradeProcess.upgrade();

		_entityCache.clearCache();
		_multiVMPool.clear();
	}

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

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject(
		filter = "component.name=com.liferay.frontend.data.set.internal.upgrade.registry.FrontendDataSetImplUpgradeStepRegistrator"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}