/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.editor.configuration.internal;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Daniel Sanz
 */
public class RichTextFragmentCKEditor5AMEditorConfigContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@FeatureFlag(enable = false, value = "LPD-11235")
	@Test
	public void testPopulateConfigJSONObject() throws Exception {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		_richTextFragmentCKEditor5AMEditorConfigContributor.
			populateConfigJSONObject(
				jsonObject, Collections.emptyMap(), _mockThemeDisplay(true),
				null);

		Assert.assertEquals(
			"data-fileentryid",
			jsonObject.getString("adaptiveMediaFileEntryAttributeName"));
		Assert.assertFalse(jsonObject.has("extraPlugins"));
	}

	@Test
	public void testPopulateConfigJSONObjectWhenScopeGroupIsNotCMS()
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		_richTextFragmentCKEditor5AMEditorConfigContributor.
			populateConfigJSONObject(
				jsonObject, Collections.emptyMap(), _mockThemeDisplay(false),
				null);

		Assert.assertEquals(0, jsonObject.length());
	}

	private ThemeDisplay _mockThemeDisplay(boolean cms) {
		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Group scopeGroup = Mockito.mock(Group.class);

		Mockito.when(
			scopeGroup.isCMS()
		).thenReturn(
			cms
		);

		Mockito.when(
			themeDisplay.getScopeGroup()
		).thenReturn(
			scopeGroup
		);

		return themeDisplay;
	}

	private final RichTextFragmentCKEditor5AMEditorConfigContributor
		_richTextFragmentCKEditor5AMEditorConfigContributor =
			new RichTextFragmentCKEditor5AMEditorConfigContributor();

}