/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.servlet.taglib;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Marcos Castro
 */
public class ProductExperienceUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetPortletKey() {
		Assert.assertEquals(
			"account_entries_admin",
			ProductExperienceUtil.getPortletKey(
				"com_liferay_account_admin_web_internal_portlet_" +
					"AccountEntriesAdminPortlet"));
		Assert.assertEquals(
			"server_admin",
			ProductExperienceUtil.getPortletKey(
				"com_liferay_server_admin_web_portlet_ServerAdminPortlet"));
		Assert.assertEquals(
			"cpdefinitions",
			ProductExperienceUtil.getPortletKey(
				"com_liferay_commerce_product_definitions_web_internal_" +
					"portlet_CPDefinitionsPortlet"));
		Assert.assertEquals(
			"portlet", ProductExperienceUtil.getPortletKey("Portlet"));
		Assert.assertEquals("20", ProductExperienceUtil.getPortletKey("20"));
		Assert.assertNull(ProductExperienceUtil.getPortletKey(null));
	}

	@Test
	public void testGetProduct() {
		Assert.assertEquals(
			ProductExperienceUtil.PRODUCT_AI_HUB,
			ProductExperienceUtil.getProduct(true, "/cms", null));
		Assert.assertEquals(
			ProductExperienceUtil.PRODUCT_CMS,
			ProductExperienceUtil.getProduct(false, "/cms", null));
		Assert.assertEquals(
			ProductExperienceUtil.PRODUCT_DIGITAL_SALES_ROOM,
			ProductExperienceUtil.getProduct(false, "/dsr", null));
		Assert.assertEquals(
			ProductExperienceUtil.PRODUCT_COMMERCE,
			ProductExperienceUtil.getProduct(
				false, "/guest",
				"com_liferay_commerce_product_definitions_web_internal_" +
					"portlet_CPDefinitionsPortlet"));
		Assert.assertEquals(
			ProductExperienceUtil.PRODUCT_DXP,
			ProductExperienceUtil.getProduct(
				false, "/guest",
				"com_liferay_server_admin_web_portlet_ServerAdminPortlet"));
		Assert.assertEquals(
			ProductExperienceUtil.PRODUCT_DXP,
			ProductExperienceUtil.getProduct(false, null, null));
	}

	@Test
	public void testGetScreen() {
		String portletId =
			"com_liferay_account_admin_web_internal_portlet_" +
				"AccountEntriesAdminPortlet";

		Assert.assertEquals(
			"account_entries_admin",
			ProductExperienceUtil.getScreen(
				portletId, _parameters(Collections.emptyMap())));
		Assert.assertEquals(
			"account_entries_admin/account_admin/edit_account_entry",
			ProductExperienceUtil.getScreen(
				portletId,
				_parameters(
					HashMapBuilder.put(
						"mvcRenderCommandName",
						"/account_admin/edit_account_entry"
					).build())));
		Assert.assertEquals(
			"account_entries_admin/account_admin/edit_account_entry/addresses",
			ProductExperienceUtil.getScreen(
				portletId,
				_parameters(
					HashMapBuilder.put(
						"mvcRenderCommandName",
						"/account_admin/edit_account_entry"
					).put(
						"screenNavigationEntryKey", "addresses"
					).build())));
		Assert.assertEquals(
			"account_entries_admin/edit_entry",
			ProductExperienceUtil.getScreen(
				portletId,
				_parameters(
					HashMapBuilder.put(
						"mvcPath", "/account_entries_admin/edit_entry.jsp"
					).build())));

		Assert.assertEquals(
			"system_settings/edit_configuration/com.liferay.foo." +
				"FooConfiguration",
			ProductExperienceUtil.getScreen(
				"com_liferay_configuration_admin_web_portlet_" +
					"SystemSettingsPortlet",
				_parameters(
					HashMapBuilder.put(
						"factoryPid", "com.liferay.foo.FooConfiguration"
					).put(
						"mvcRenderCommandName", "/edit_configuration"
					).build())));
		Assert.assertNull(
			ProductExperienceUtil.getScreen(
				null, _parameters(Collections.emptyMap())));
	}

	@Test
	public void testGetScreenDropsUnsafeCharacters() {
		String screen = ProductExperienceUtil.getScreen(
			"com_liferay_server_admin_web_portlet_ServerAdminPortlet",
			_parameters(
				HashMapBuilder.put(
					"mvcRenderCommandName",
					"/view');alert(document.cookie);//<script> ../../x"
				).build()));

		Assert.assertEquals(
			"server_admin/viewalertdocument.cookie/script/x", screen);

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < 500; i++) {
			sb.append('a');
		}

		screen = ProductExperienceUtil.getScreen(
			"com_liferay_server_admin_web_portlet_ServerAdminPortlet",
			_parameters(
				HashMapBuilder.put(
					"mvcRenderCommandName", sb.toString()
				).build()));

		Assert.assertEquals(200, screen.length());
	}

	@Test
	public void testIsAdmin() {
		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Assert.assertFalse(ProductExperienceUtil.isAdmin(themeDisplay));

		PermissionChecker permissionChecker = Mockito.mock(
			PermissionChecker.class);

		Mockito.when(
			themeDisplay.getPermissionChecker()
		).thenReturn(
			permissionChecker
		);

		Mockito.when(
			themeDisplay.getScopeGroupId()
		).thenReturn(
			20L
		);

		Mockito.when(
			themeDisplay.isSignedIn()
		).thenReturn(
			true
		);

		Assert.assertFalse(ProductExperienceUtil.isAdmin(themeDisplay));

		Mockito.when(
			permissionChecker.isGroupAdmin(20L)
		).thenReturn(
			true
		);

		Assert.assertTrue(ProductExperienceUtil.isAdmin(themeDisplay));
	}

	@Test
	public void testIsBackOffice() {
		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		Mockito.when(
			themeDisplay.isSignedIn()
		).thenReturn(
			true
		);

		Assert.assertFalse(
			ProductExperienceUtil.isBackOffice(
				mockHttpServletRequest, themeDisplay));

		mockHttpServletRequest.setParameter("p_l_mode", "edit");

		Assert.assertTrue(
			ProductExperienceUtil.isBackOffice(
				mockHttpServletRequest, themeDisplay));

		mockHttpServletRequest.removeParameter("p_l_mode");

		Layout layout = Mockito.mock(Layout.class);

		Mockito.when(
			layout.isTypeControlPanel()
		).thenReturn(
			true
		);

		Mockito.when(
			themeDisplay.getLayout()
		).thenReturn(
			layout
		);

		Assert.assertTrue(
			ProductExperienceUtil.isBackOffice(
				mockHttpServletRequest, themeDisplay));

		Mockito.when(
			layout.isTypeControlPanel()
		).thenReturn(
			false
		);

		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getFriendlyURL()
		).thenReturn(
			"/dsr"
		);

		Mockito.when(
			themeDisplay.getSiteGroup()
		).thenReturn(
			group
		);

		Assert.assertTrue(
			ProductExperienceUtil.isBackOffice(
				mockHttpServletRequest, themeDisplay));

		Mockito.when(
			group.getFriendlyURL()
		).thenReturn(
			"/guest"
		);

		Mockito.when(
			themeDisplay.getTheme()
		).thenReturn(
			Mockito.mock(Theme.class)
		);

		Mockito.when(
			themeDisplay.getThemeId()
		).thenReturn(
			"cms_WAR_cmstheme"
		);

		Assert.assertTrue(
			ProductExperienceUtil.isBackOffice(
				mockHttpServletRequest, themeDisplay));

		Mockito.when(
			themeDisplay.getThemeId()
		).thenReturn(
			"classic_WAR_classictheme"
		);

		Assert.assertFalse(
			ProductExperienceUtil.isBackOffice(
				mockHttpServletRequest, themeDisplay));

		Mockito.when(
			themeDisplay.isSignedIn()
		).thenReturn(
			false
		);

		mockHttpServletRequest.setParameter("p_l_mode", "edit");

		Assert.assertFalse(
			ProductExperienceUtil.isBackOffice(
				mockHttpServletRequest, themeDisplay));
	}

	private Function<String, String> _parameters(
		Map<String, String> parameters) {

		return parameters::get;
	}

}