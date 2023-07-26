/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.product.navigation.control.menu.manager.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.product.navigation.control.menu.manager.ProductNavigationControlMenuManager;
import com.liferay.site.configuration.manager.MenuAccessConfigurationManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mikel Lorza
 */
@RunWith(Arquillian.class)
public class ProductNavigationControlMenuManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypeContentLayout(_group);
	}

	@Test
	public void testIsShowControlMenuWithAdministratorInContentLayout()
		throws Exception {

		_menuAccessConfigurationManager.updateMenuAccessConfiguration(
			_group.getGroupId(), new String[0], true);

		Assert.assertTrue(
			_productNavigationControlMenuManager.isShowControlMenu(
				_group, _layout, TestPropsValues.getUserId()));
	}

	@Test
	public void testIsShowControlMenuWithNormalUserWithoutRoleAccessInContentLayout()
		throws Exception {

		_menuAccessConfigurationManager.updateMenuAccessConfiguration(
			_group.getGroupId(), new String[0], true);

		User user = UserTestUtil.addUser();

		Assert.assertFalse(
			_productNavigationControlMenuManager.isShowControlMenu(
				_group, _layout, user.getUserId()));
	}

	@Test
	public void testIsShowControlMenuWithNormalUserWithRoleAccessInContentLayout()
		throws Exception {

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_SITE);

		_menuAccessConfigurationManager.updateMenuAccessConfiguration(
			_group.getGroupId(),
			new String[] {String.valueOf(role.getRoleId())}, true);

		User user = UserTestUtil.addUser();

		_roleLocalService.addUserRole(user.getUserId(), role);

		Assert.assertTrue(
			_productNavigationControlMenuManager.isShowControlMenu(
				_group, _layout, user.getUserId()));
	}

	@Test
	public void testIsShowControlMenuWithRandomUserInAdminLayout()
		throws Exception {

		_menuAccessConfigurationManager.updateMenuAccessConfiguration(
			_group.getGroupId(), new String[0], true);

		Assert.assertTrue(
			_productNavigationControlMenuManager.isShowControlMenu(
				_group, _layout, TestPropsValues.getUserId()));
	}

	@Test
	public void testIsShowControlMenuWithSiteAdministratorInContentLayout()
		throws Exception {

		_menuAccessConfigurationManager.updateMenuAccessConfiguration(
			_group.getGroupId(), new String[0], true);

		Assert.assertTrue(
			_productNavigationControlMenuManager.isShowControlMenu(
				_group, _layout, TestPropsValues.getUserId()));
	}

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private MenuAccessConfigurationManager _menuAccessConfigurationManager;

	@Inject
	private ProductNavigationControlMenuManager
		_productNavigationControlMenuManager;

	@Inject
	private RoleLocalService _roleLocalService;

}