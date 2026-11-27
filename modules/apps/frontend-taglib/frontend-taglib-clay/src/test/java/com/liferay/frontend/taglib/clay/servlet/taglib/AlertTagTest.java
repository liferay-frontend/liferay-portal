/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.servlet.taglib;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author David Aragonés
 */
public class AlertTagTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetIcon() {
		_assertIcon("danger", "times-circle-full");
		_assertIcon("info", "info-circle");
		_assertIcon("secondary", "password-policies");
		_assertIcon("success", "check-circle-full");
		_assertIcon("warning", "warning-full");
	}

	private void _assertIcon(String displayType, String expectedIcon) {
		Assert.assertEquals(
			displayType, expectedIcon,
			ReflectionTestUtil.invoke(
				new AlertTag(), "_getIcon", new Class<?>[] {String.class},
				displayType));
	}

}