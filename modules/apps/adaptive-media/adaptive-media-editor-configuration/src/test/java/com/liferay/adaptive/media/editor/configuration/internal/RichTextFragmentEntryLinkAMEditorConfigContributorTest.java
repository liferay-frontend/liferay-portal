/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.editor.configuration.internal;

import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.item.selector.criteria.url.criterion.URLItemSelectorCriterion;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Daniel Sanz
 */
public class RichTextFragmentEntryLinkAMEditorConfigContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@FeatureFlag(enable = false, value = "LPD-11235")
	@Test
	public void testIsCKEditor4WhenFeatureFlagIsDisabled() {
		Assert.assertFalse(
			_richTextFragmentEntryLinkAMEditorConfigContributor.isCKEditor4(
				_themeDisplay));
	}

	@FeatureFlag("LPD-11235")
	@Test
	public void testIsCKEditor4WhenFeatureFlagIsEnabled() {
		Assert.assertTrue(
			_richTextFragmentEntryLinkAMEditorConfigContributor.isCKEditor4(
				_themeDisplay));
	}

	@Test
	public void testIsItemSelectorCriterionOverridable() {
		Assert.assertTrue(
			_richTextFragmentEntryLinkAMEditorConfigContributor.
				isItemSelectorCriterionOverridable(
					new ImageItemSelectorCriterion()));
		Assert.assertFalse(
			_richTextFragmentEntryLinkAMEditorConfigContributor.
				isItemSelectorCriterionOverridable(
					new URLItemSelectorCriterion()));
	}

	private final RichTextFragmentEntryLinkAMEditorConfigContributor
		_richTextFragmentEntryLinkAMEditorConfigContributor =
			new RichTextFragmentEntryLinkAMEditorConfigContributor();
	private final ThemeDisplay _themeDisplay = Mockito.mock(ThemeDisplay.class);

}