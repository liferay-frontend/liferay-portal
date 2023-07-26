/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.index;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author André de Oliveira
 */
public class IndexStatusManagerImplRequireIndexReadWriteTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testBookendsLikeSetupAndTeardown() {
		indexStatusManagerImpl.requireIndexReadWrite(true);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());

		indexStatusManagerImpl.setIndexReadOnly(false);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());

		indexStatusManagerImpl.requireIndexReadWrite(false);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());
	}

	@Test
	public void testReadOnlySetAfterBookends() {
		indexStatusManagerImpl.requireIndexReadWrite(true);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());

		indexStatusManagerImpl.requireIndexReadWrite(false);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());

		indexStatusManagerImpl.setIndexReadOnly(true);

		Assert.assertTrue(indexStatusManagerImpl.isIndexReadOnly());
	}

	@Test
	public void testReadOnlySetBeforeBookends() {
		indexStatusManagerImpl.setIndexReadOnly(true);

		Assert.assertTrue(indexStatusManagerImpl.isIndexReadOnly());

		indexStatusManagerImpl.requireIndexReadWrite(true);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());

		indexStatusManagerImpl.requireIndexReadWrite(false);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());
	}

	@Test
	public void testReadOnlySetBetweenBookends() {
		indexStatusManagerImpl.requireIndexReadWrite(true);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());

		indexStatusManagerImpl.setIndexReadOnly(true);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());

		indexStatusManagerImpl.requireIndexReadWrite(false);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	protected IndexStatusManagerImpl indexStatusManagerImpl =
		new IndexStatusManagerImpl();

}