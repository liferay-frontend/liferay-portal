/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.editor.configuration.internal;

import com.liferay.adaptive.media.image.item.selector.AMImageFileEntryItemSelectorReturnType;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.audio.criterion.AudioItemSelectorCriterion;
import com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.item.selector.criteria.upload.criterion.UploadItemSelectorCriterion;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.test.rule.FeatureFlag;

import jakarta.portlet.PortletURL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseAMEditorConfigContributorTestCase {

	@Before
	public void setUp() {
		_inputEditorTaglibAttributes.put(
			"liferay-ui:input-editor:name", "testEditor");
	}

	@Test
	public void testAMReturnTypeIsAddedToAllItemSelectorCriteria()
		throws Exception {

		ItemSelectorCriterion[] itemSelectorCriteria = {
			_initializeItemSelectorCriterion(getItemSelectorCriterion()),
			_initializeItemSelectorCriterion(new FileItemSelectorCriterion()),
			_initializeItemSelectorCriterion(new ImageItemSelectorCriterion()),
			_initializeItemSelectorCriterion(new UploadItemSelectorCriterion())
		};

		String itemSelectorURL = RandomTestUtil.randomString();

		_mockItemSelector(Arrays.asList(itemSelectorCriteria), itemSelectorURL);

		JSONObject jsonObject = JSONUtil.put(
			"filebrowserImageBrowseLinkUrl", itemSelectorURL);

		BaseAMEditorConfigContributor baseAMEditorConfigContributor =
			getBaseAMEditorConfigContributor();

		ReflectionTestUtil.setFieldValue(
			baseAMEditorConfigContributor, "itemSelector", _itemSelector);

		baseAMEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		for (ItemSelectorCriterion itemSelectorCriterion :
				itemSelectorCriteria) {

			for (ItemSelectorReturnType itemSelectorReturnType :
					itemSelectorCriterion.getDesiredItemSelectorReturnTypes()) {

				Assert.assertTrue(
					itemSelectorReturnType instanceof
						AMImageFileEntryItemSelectorReturnType);
			}
		}
	}

	@Test
	public void testAdaptiveMediaFileEntryAttributeNameIsAdded()
		throws Exception {

		_mockItemSelector(
			_getItemSelectorCriterionFileEntryItemSelectorReturnType(),
			getItemSelectorCriterionFileEntryItemSelectorReturnTypeName());

		JSONObject originalJSONObject = JSONUtil.put(
			"filebrowserImageBrowseLinkUrl",
			getItemSelectorCriterionFileEntryItemSelectorReturnTypeName());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toString());

		BaseAMEditorConfigContributor baseAMEditorConfigContributor =
			getBaseAMEditorConfigContributor();

		ReflectionTestUtil.setFieldValue(
			baseAMEditorConfigContributor, "itemSelector", _itemSelector);

		baseAMEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		Assert.assertEquals(
			"data-fileentryid",
			jsonObject.get("adaptiveMediaFileEntryAttributeName"));
	}

	@FeatureFlag("LPD-11235")
	@Test
	public void testAdaptiveMediaIsAddedToExtraPlugins() throws Exception {
		_mockItemSelector(
			_getItemSelectorCriterionFileEntryItemSelectorReturnType(),
			getItemSelectorCriterionFileEntryItemSelectorReturnTypeName());

		JSONObject originalJSONObject = JSONUtil.put(
			"filebrowserImageBrowseLinkUrl",
			getItemSelectorCriterionFileEntryItemSelectorReturnTypeName());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toString());

		BaseAMEditorConfigContributor baseAMEditorConfigContributor =
			getBaseAMEditorConfigContributor();

		ReflectionTestUtil.setFieldValue(
			baseAMEditorConfigContributor, "itemSelector", _itemSelector);

		baseAMEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		Assert.assertEquals(
			"adaptivemedia", jsonObject.getString("extraPlugins"));
	}

	@FeatureFlag("LPD-11235")
	@Test
	public void testAdaptiveMediaIsExtraPlugins() throws Exception {
		_mockItemSelector(
			_getItemSelectorCriterionFileEntryItemSelectorReturnType(),
			getItemSelectorCriterionFileEntryItemSelectorReturnTypeName());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			JSONUtil.put(
				"extraPlugins", "ae_placeholder,ae_selectionregion,ae_uicore"
			).put(
				"filebrowserImageBrowseLinkUrl",
				getItemSelectorCriterionFileEntryItemSelectorReturnTypeName()
			).toString());

		BaseAMEditorConfigContributor baseAMEditorConfigContributor =
			getBaseAMEditorConfigContributor();

		ReflectionTestUtil.setFieldValue(
			baseAMEditorConfigContributor, "itemSelector", _itemSelector);

		baseAMEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		Assert.assertEquals(
			"ae_placeholder,ae_selectionregion,ae_uicore,adaptivemedia",
			jsonObject.getString("extraPlugins"));
	}

	@Test
	public void testAdaptiveMediaIsNotExtraPluginsForCKEditor5()
		throws Exception {

		_mockItemSelector(
			_getItemSelectorCriterionFileEntryItemSelectorReturnType(),
			getItemSelectorCriterionFileEntryItemSelectorReturnTypeName());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			JSONUtil.put(
				"extraPlugins", "ae_placeholder"
			).put(
				"filebrowserImageBrowseLinkUrl",
				getItemSelectorCriterionFileEntryItemSelectorReturnTypeName()
			).toString());

		BaseAMEditorConfigContributor baseAMEditorConfigContributor =
			Mockito.spy(getBaseAMEditorConfigContributor());

		Mockito.doReturn(
			false
		).when(
			baseAMEditorConfigContributor
		).isCKEditor4(
			Mockito.any(ThemeDisplay.class)
		);

		ReflectionTestUtil.setFieldValue(
			baseAMEditorConfigContributor, "itemSelector", _itemSelector);

		baseAMEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		Assert.assertEquals(
			"data-fileentryid",
			jsonObject.getString("adaptiveMediaFileEntryAttributeName"));
		Assert.assertEquals(
			"ae_placeholder", jsonObject.getString("extraPlugins"));
	}

	@Test
	public void testAddAMImageFileEntryItemSelectorReturnType() {
		BaseAMEditorConfigContributor baseAMEditorConfigContributor =
			getBaseAMEditorConfigContributor();

		ItemSelectorCriterion itemSelectorCriterion =
			getItemSelectorCriterion();

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FileEntryItemSelectorReturnType());

		baseAMEditorConfigContributor.addAMImageFileEntryItemSelectorReturnType(
			itemSelectorCriterion);

		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			itemSelectorCriterion.getDesiredItemSelectorReturnTypes();

		Assert.assertEquals(
			desiredItemSelectorReturnTypes.toString(), 2,
			desiredItemSelectorReturnTypes.size());

		_assertContains(
			desiredItemSelectorReturnTypes,
			itemSelectorReturnType -> itemSelectorReturnType instanceof
				AMImageFileEntryItemSelectorReturnType);
		_assertContains(
			desiredItemSelectorReturnTypes,
			itemSelectorReturnType ->
				itemSelectorReturnType instanceof
					FileEntryItemSelectorReturnType);
	}

	@Test
	public void testImgIsAddedToAllowedContent() throws Exception {
		JSONObject originalJSONObject = JSONUtil.put(
			"allowedContent", "a[*](*); div(*);");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toString());

		BaseAMEditorConfigContributor baseAMEditorConfigContributor =
			getBaseAMEditorConfigContributor();

		baseAMEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"adaptiveMediaFileEntryAttributeName", "data-fileentryid"
			).put(
				"allowedContent", "a[*](*); div(*); img[*](*){*};"
			).toString(),
			jsonObject.toString(), true);
	}

	@Test
	public void testImgIsAllowedContent() throws Exception {
		JSONObject originalJSONObject = JSONFactoryUtil.createJSONObject();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toString());

		BaseAMEditorConfigContributor baseAMEditorConfigContributor =
			getBaseAMEditorConfigContributor();

		baseAMEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"adaptiveMediaFileEntryAttributeName", "data-fileentryid"
			).toString(),
			jsonObject.toString(), true);
	}

	@Test
	public void testImgIsNotAddedToAllowedContentIfAlreadyPresent()
		throws Exception {

		JSONObject originalJSONObject = JSONUtil.put(
			"allowedContent", "a[*](*); div(*); img[*](*){*};");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toString());

		BaseAMEditorConfigContributor baseAMEditorConfigContributor =
			getBaseAMEditorConfigContributor();

		baseAMEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"adaptiveMediaFileEntryAttributeName", "data-fileentryid"
			).put(
				"allowedContent", "a[*](*); div(*); img[*](*){*};"
			).toString(),
			jsonObject.toString(), true);
	}

	@Test
	public void testImgIsNotAddedToAllowedContentIfAnyContentAllowed()
		throws Exception {

		JSONObject originalJSONObject = JSONUtil.put(
			"allowedContent", Boolean.TRUE.toString());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toString());

		BaseAMEditorConfigContributor baseAMEditorConfigContributor =
			getBaseAMEditorConfigContributor();

		baseAMEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"adaptiveMediaFileEntryAttributeName", "data-fileentryid"
			).put(
				"allowedContent", Boolean.TRUE.toString()
			).toString(),
			jsonObject.toString(), true);
	}

	@Test
	public void testImgIsNotAddedToAllowedContentIfEverythingWasAlreadyAllowed()
		throws Exception {

		JSONObject originalJSONObject = JSONUtil.put("allowedContent", true);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toString());

		BaseAMEditorConfigContributor baseAMEditorConfigContributor =
			getBaseAMEditorConfigContributor();

		baseAMEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"adaptiveMediaFileEntryAttributeName", "data-fileentryid"
			).put(
				"allowedContent", true
			).toString(),
			jsonObject.toString(), true);
	}

	@Test
	public void testItemSelectorURLWhenNoFileBrowserImageBrowseLinkUrl()
		throws Exception {

		JSONObject originalJSONObject = JSONUtil.put(
			"filebrowserImageBrowseLinkUrl", StringPool.BLANK);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toString());

		BaseAMEditorConfigContributor baseAMEditorConfigContributor =
			getBaseAMEditorConfigContributor();

		ReflectionTestUtil.setFieldValue(
			baseAMEditorConfigContributor, "itemSelector", _itemSelector);

		baseAMEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		Mockito.verify(
			_itemSelector, Mockito.never()
		).getItemSelectorURL(
			Mockito.any(RequestBackedPortletURLFactory.class),
			Mockito.anyString(), Mockito.any(ItemSelectorCriterion.class)
		);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"adaptiveMediaFileEntryAttributeName", "data-fileentryid"
			).put(
				"filebrowserImageBrowseLinkUrl", StringPool.BLANK
			).toString(),
			jsonObject.toString(), true);
	}

	@Test
	public void testItemSelectorURLWithAudioItemSelectorCriterion()
		throws Exception {

		Mockito.when(
			_itemSelector.getItemSelectorCriteria(
				"audioItemSelectorCriterionFileEntryItemSelectorReturnType")
		).thenReturn(
			_getAudioItemSelectorCriterionFileEntryItemSelectorReturnType()
		);

		JSONObject originalJSONObject = JSONUtil.put(
			"filebrowserImageBrowseLinkUrl",
			"audioItemSelectorCriterionFileEntryItemSelectorReturnType");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toString());

		BaseAMEditorConfigContributor baseAMEditorConfigContributor =
			getBaseAMEditorConfigContributor();

		ReflectionTestUtil.setFieldValue(
			baseAMEditorConfigContributor, "itemSelector", _itemSelector);

		baseAMEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		Mockito.verify(
			_itemSelector, Mockito.never()
		).getItemSelectorURL(
			Mockito.any(RequestBackedPortletURLFactory.class),
			Mockito.anyString(), Mockito.any(ItemSelectorCriterion.class)
		);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"adaptiveMediaFileEntryAttributeName", "data-fileentryid"
			).put(
				"filebrowserImageBrowseLinkUrl",
				"audioItemSelectorCriterionFileEntryItemSelectorReturnType"
			).toString(),
			jsonObject.toString(), true);
	}

	@Test
	public void testItemSelectorURLWithFileItemSelectorCriterion()
		throws Exception {

		_mockItemSelector(
			_getFileItemSelectorCriterionFileEntryItemSelectorReturnType(),
			"fileItemSelectorCriterionFileEntryItemSelectorReturnType");

		JSONObject originalJSONObject = JSONUtil.put(
			"filebrowserImageBrowseLinkUrl",
			"fileItemSelectorCriterionFileEntryItemSelectorReturnType");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toString());

		BaseAMEditorConfigContributor baseAMEditorConfigContributor =
			getBaseAMEditorConfigContributor();

		ReflectionTestUtil.setFieldValue(
			baseAMEditorConfigContributor, "itemSelector", _itemSelector);

		baseAMEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		Mockito.verify(
			_itemSelector
		).getItemSelectorURL(
			Mockito.any(RequestBackedPortletURLFactory.class),
			Mockito.anyString(), Mockito.any(ItemSelectorCriterion.class)
		);
	}

	@Test
	public void testItemSelectorURLWithItemSelectorCriterion()
		throws Exception {

		_mockItemSelector(
			_getItemSelectorCriterionFileEntryItemSelectorReturnType(),
			getItemSelectorCriterionFileEntryItemSelectorReturnTypeName());

		JSONObject originalJSONObject = JSONUtil.put(
			"filebrowserImageBrowseLinkUrl",
			getItemSelectorCriterionFileEntryItemSelectorReturnTypeName());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			originalJSONObject.toString());

		BaseAMEditorConfigContributor baseAMEditorConfigContributor =
			getBaseAMEditorConfigContributor();

		ReflectionTestUtil.setFieldValue(
			baseAMEditorConfigContributor, "itemSelector", _itemSelector);

		baseAMEditorConfigContributor.populateConfigJSONObject(
			jsonObject, _inputEditorTaglibAttributes, _themeDisplay,
			_requestBackedPortletURLFactory);

		Mockito.verify(
			_itemSelector
		).getItemSelectorURL(
			Mockito.any(RequestBackedPortletURLFactory.class),
			Mockito.anyString(), Mockito.any(ItemSelectorCriterion.class)
		);
	}

	protected abstract BaseAMEditorConfigContributor
		getBaseAMEditorConfigContributor();

	protected abstract ItemSelectorCriterion getItemSelectorCriterion();

	protected abstract String
		getItemSelectorCriterionFileEntryItemSelectorReturnTypeName();

	private <T> void _assertContains(
		Collection<T> collection, Predicate<T> predicate) {

		boolean match = false;

		for (T t : collection) {
			if (predicate.test(t)) {
				match = true;

				break;
			}
		}

		Assert.assertTrue(match);
	}

	private List<ItemSelectorCriterion>
		_getAudioItemSelectorCriterionFileEntryItemSelectorReturnType() {

		AudioItemSelectorCriterion audioItemSelectorCriterion =
			new AudioItemSelectorCriterion();

		audioItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FileEntryItemSelectorReturnType());

		return Collections.singletonList(audioItemSelectorCriterion);
	}

	private List<ItemSelectorCriterion>
		_getFileItemSelectorCriterionFileEntryItemSelectorReturnType() {

		FileItemSelectorCriterion fileItemSelectorCriterion =
			new FileItemSelectorCriterion();

		fileItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FileEntryItemSelectorReturnType());

		return Collections.singletonList(fileItemSelectorCriterion);
	}

	private List<ItemSelectorCriterion>
		_getItemSelectorCriterionFileEntryItemSelectorReturnType() {

		ItemSelectorCriterion itemSelectorCriterion =
			getItemSelectorCriterion();

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FileEntryItemSelectorReturnType());

		return Collections.singletonList(itemSelectorCriterion);
	}

	private ItemSelectorCriterion _initializeItemSelectorCriterion(
		ItemSelectorCriterion itemSelectorCriterion) {

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new ArrayList<ItemSelectorReturnType>());

		return itemSelectorCriterion;
	}

	private void _mockItemSelector(
		List<ItemSelectorCriterion> itemSelectorCriteria,
		String itemSelectorURL) {

		Mockito.when(
			_itemSelector.getItemSelectedEventName(Mockito.anyString())
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_itemSelector.getItemSelectorCriteria(itemSelectorURL)
		).thenReturn(
			itemSelectorCriteria
		);

		Mockito.when(
			_itemSelector.getItemSelectorURL(
				Mockito.any(RequestBackedPortletURLFactory.class),
				Mockito.anyString(), Mockito.any(ItemSelectorCriterion[].class))
		).thenReturn(
			_portletURL
		);
	}

	private final Map<String, Object> _inputEditorTaglibAttributes =
		new HashMap<>();
	private final ItemSelector _itemSelector = Mockito.mock(ItemSelector.class);
	private final PortletURL _portletURL = Mockito.mock(PortletURL.class);
	private final RequestBackedPortletURLFactory
		_requestBackedPortletURLFactory = Mockito.mock(
			RequestBackedPortletURLFactory.class);
	private final ThemeDisplay _themeDisplay = Mockito.mock(ThemeDisplay.class);

}