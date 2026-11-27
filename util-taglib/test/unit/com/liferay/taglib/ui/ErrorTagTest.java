/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.servlet.MultiSessionErrors;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.portlet.PortletRequest;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockPageContext;

/**
 * @author David Aragonés
 */
public class ErrorTagTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetAlertIconWithKey() {
		ErrorTag errorTag = new ErrorTag();

		errorTag.setKey(RandomTestUtil.randomString());

		Assert.assertEquals("times-circle-full", _getAlertIcon(errorTag));
	}

	@Test
	public void testGetAlertIconWithoutKey() {
		_testGetAlertIconWithoutKey("times-circle-full", false);
		_testGetAlertIconWithoutKey("warning-full", true);
	}

	private String _getAlertIcon(ErrorTag errorTag) {
		return ReflectionTestUtil.invoke(
			errorTag, "_getAlertIcon", new Class<?>[0]);
	}

	private void _testGetAlertIconWithoutKey(
		String expectedIcon, boolean warning) {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();
		PortletRequest portletRequest = Mockito.mock(PortletRequest.class);

		mockHttpServletRequest.setAttribute(
			JavaConstants.JAKARTA_PORTLET_REQUEST, portletRequest);

		ErrorTag errorTag = new ErrorTag();

		errorTag.setPageContext(
			new MockPageContext(null, mockHttpServletRequest));

		try (MockedStatic<MultiSessionErrors> multiSessionErrorsMockedStatic =
				Mockito.mockStatic(MultiSessionErrors.class)) {

			multiSessionErrorsMockedStatic.when(
				() -> MultiSessionErrors.contains(portletRequest, "warning")
			).thenReturn(
				warning
			);

			Assert.assertEquals(expectedIcon, _getAlertIcon(errorTag));
		}
	}

}