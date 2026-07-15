/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.content.security.policy.internal.servlet.filter;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.permission.LayoutPermission;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.content.security.policy.internal.configuration.ContentSecurityPolicyConfiguration;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Antonio Ortega
 */
public class ContentSecurityPolicyFilterTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_contentSecurityPolicyFilter = new ContentSecurityPolicyFilter();
	}

	@Test
	public void testIsAuthorizedLayoutEditMode() throws Exception {
		Layout layout = Mockito.mock(Layout.class);

		LayoutPermission layoutPermission = Mockito.mock(
			LayoutPermission.class);

		ReflectionTestUtil.setFieldValue(
			_contentSecurityPolicyFilter, "_layoutPermission",
			layoutPermission);

		PermissionChecker permissionChecker = Mockito.mock(
			PermissionChecker.class);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		try {

			// Not an edit mode render

			Assert.assertFalse(
				_isAuthorizedLayoutEditMode(
					_createHttpServletRequest(
						RandomTestUtil.randomString(), layout)));

			// Edit mode render of a layout the user can update

			Mockito.when(
				layoutPermission.containsLayoutUpdatePermission(
					permissionChecker, layout)
			).thenReturn(
				true
			);

			Assert.assertTrue(
				_isAuthorizedLayoutEditMode(
					_createHttpServletRequest(Constants.EDIT, layout)));

			// Edit mode render of a layout the user cannot update

			Mockito.when(
				layoutPermission.containsLayoutUpdatePermission(
					permissionChecker, layout)
			).thenReturn(
				false
			);

			Assert.assertFalse(
				_isAuthorizedLayoutEditMode(
					_createHttpServletRequest(Constants.EDIT, layout)));

			// Edit mode request whose layout is not resolvable yet

			Assert.assertTrue(
				_isAuthorizedLayoutEditMode(
					_createHttpServletRequest(Constants.EDIT, null)));
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(null);
		}
	}

	@Test
	public void testIsExcludedURIPath() {
		_testIsExcludedURIPath(
			new String[] {"/group"}, false, null, "/c/portal/layout");
		_testIsExcludedURIPath(
			new String[] {"/group"}, false, "/web/mysite/home",
			"/c/portal/layout");
		_testIsExcludedURIPath(
			new String[] {"/group"}, true, "/group/guest/home",
			"/c/portal/layout");
		_testIsExcludedURIPath(
			new String[] {"/group"}, true, null, "/group/guest/home");
		_testIsExcludedURIPath(new String[0], true, null, "/GROUP/guest/home");
	}

	private HttpServletRequest _createHttpServletRequest(
		String layoutMode, Layout layout) {

		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Mockito.when(
			httpServletRequest.getParameter("p_l_mode")
		).thenReturn(
			layoutMode
		);

		Mockito.when(
			httpServletRequest.getAttribute(WebKeys.LAYOUT)
		).thenReturn(
			layout
		);

		return httpServletRequest;
	}

	private boolean _isAuthorizedLayoutEditMode(
		HttpServletRequest httpServletRequest) {

		return (boolean)ReflectionTestUtil.invoke(
			_contentSecurityPolicyFilter, "_isAuthorizedLayoutEditMode",
			new Class<?>[] {HttpServletRequest.class}, httpServletRequest);
	}

	private void _testIsExcludedURIPath(
		String[] excludedPaths, boolean excludedURIPath,
		String forwardRequestURI, String requestURI) {

		ContentSecurityPolicyConfiguration contentSecurityPolicyConfiguration =
			Mockito.mock(ContentSecurityPolicyConfiguration.class);

		Mockito.when(
			contentSecurityPolicyConfiguration.excludedPaths()
		).thenReturn(
			excludedPaths
		);

		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Mockito.when(
			httpServletRequest.getAttribute(
				JavaConstants.JAKARTA_SERVLET_FORWARD_REQUEST_URI)
		).thenReturn(
			forwardRequestURI
		);

		Mockito.when(
			httpServletRequest.getRequestURI()
		).thenReturn(
			requestURI
		);

		Assert.assertEquals(
			excludedURIPath,
			ReflectionTestUtil.invoke(
				_contentSecurityPolicyFilter, "_isExcludedURIPath",
				new Class<?>[] {
					ContentSecurityPolicyConfiguration.class,
					HttpServletRequest.class
				},
				contentSecurityPolicyConfiguration, httpServletRequest));
	}

	private ContentSecurityPolicyFilter _contentSecurityPolicyFilter;

}