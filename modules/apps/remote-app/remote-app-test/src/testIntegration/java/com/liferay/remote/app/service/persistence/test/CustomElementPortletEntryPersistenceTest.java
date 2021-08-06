/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.remote.app.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.remote.app.exception.NoSuchCustomElementPortletEntryException;
import com.liferay.remote.app.model.CustomElementPortletEntry;
import com.liferay.remote.app.service.CustomElementPortletEntryLocalServiceUtil;
import com.liferay.remote.app.service.persistence.CustomElementPortletEntryPersistence;
import com.liferay.remote.app.service.persistence.CustomElementPortletEntryUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class CustomElementPortletEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.remote.app.service"));

	@Before
	public void setUp() {
		_persistence = CustomElementPortletEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CustomElementPortletEntry> iterator =
			_customElementPortletEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CustomElementPortletEntry customElementPortletEntry =
			_persistence.create(pk);

		Assert.assertNotNull(customElementPortletEntry);

		Assert.assertEquals(customElementPortletEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CustomElementPortletEntry newCustomElementPortletEntry =
			addCustomElementPortletEntry();

		_persistence.remove(newCustomElementPortletEntry);

		CustomElementPortletEntry existingCustomElementPortletEntry =
			_persistence.fetchByPrimaryKey(
				newCustomElementPortletEntry.getPrimaryKey());

		Assert.assertNull(existingCustomElementPortletEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCustomElementPortletEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CustomElementPortletEntry newCustomElementPortletEntry =
			_persistence.create(pk);

		newCustomElementPortletEntry.setMvccVersion(RandomTestUtil.nextLong());

		newCustomElementPortletEntry.setUuid(RandomTestUtil.randomString());

		newCustomElementPortletEntry.setCompanyId(RandomTestUtil.nextLong());

		newCustomElementPortletEntry.setUserId(RandomTestUtil.nextLong());

		newCustomElementPortletEntry.setUserName(RandomTestUtil.randomString());

		newCustomElementPortletEntry.setCreateDate(RandomTestUtil.nextDate());

		newCustomElementPortletEntry.setModifiedDate(RandomTestUtil.nextDate());

		newCustomElementPortletEntry.setCssURLs(RandomTestUtil.randomString());

		newCustomElementPortletEntry.setInstanceable(RandomTestUtil.randomBoolean());

		newCustomElementPortletEntry.setTagAttributes(
			RandomTestUtil.randomString());

		newCustomElementPortletEntry.setName(RandomTestUtil.randomString());

		newCustomElementPortletEntry.setPortletDisplayCategory(
			RandomTestUtil.randomString());

		newCustomElementPortletEntry.setTagName(RandomTestUtil.randomString());

		_customElementPortletEntries.add(
			_persistence.update(newCustomElementPortletEntry));

		CustomElementPortletEntry existingCustomElementPortletEntry =
			_persistence.findByPrimaryKey(
				newCustomElementPortletEntry.getPrimaryKey());

		Assert.assertEquals(
			existingCustomElementPortletEntry.getMvccVersion(),
			newCustomElementPortletEntry.getMvccVersion());
		Assert.assertEquals(
			existingCustomElementPortletEntry.getUuid(),
			newCustomElementPortletEntry.getUuid());
		Assert.assertEquals(
			existingCustomElementPortletEntry.getCustomElementPortletEntryId(),
			newCustomElementPortletEntry.getCustomElementPortletEntryId());
		Assert.assertEquals(
			existingCustomElementPortletEntry.getCompanyId(),
			newCustomElementPortletEntry.getCompanyId());
		Assert.assertEquals(
			existingCustomElementPortletEntry.getUserId(),
			newCustomElementPortletEntry.getUserId());
		Assert.assertEquals(
			existingCustomElementPortletEntry.getUserName(),
			newCustomElementPortletEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCustomElementPortletEntry.getCreateDate()),
			Time.getShortTimestamp(
				newCustomElementPortletEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCustomElementPortletEntry.getModifiedDate()),
			Time.getShortTimestamp(
				newCustomElementPortletEntry.getModifiedDate()));
		Assert.assertEquals(
			existingCustomElementPortletEntry.getCssURLs(),
			newCustomElementPortletEntry.getCssURLs());
		Assert.assertEquals(
			existingCustomElementPortletEntry.getInstanceable(),
			newCustomElementPortletEntry.getInstanceable());
		Assert.assertEquals(
			existingCustomElementPortletEntry.getTagAttributes(),
			newCustomElementPortletEntry.getTagAttributes());
		Assert.assertEquals(
			existingCustomElementPortletEntry.getName(),
			newCustomElementPortletEntry.getName());
		Assert.assertEquals(
			existingCustomElementPortletEntry.getPortletDisplayCategory(),
			newCustomElementPortletEntry.getPortletDisplayCategory());
		Assert.assertEquals(
			existingCustomElementPortletEntry.getTagName(),
			newCustomElementPortletEntry.getTagName());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CustomElementPortletEntry newCustomElementPortletEntry =
			addCustomElementPortletEntry();

		CustomElementPortletEntry existingCustomElementPortletEntry =
			_persistence.findByPrimaryKey(
				newCustomElementPortletEntry.getPrimaryKey());

		Assert.assertEquals(
			existingCustomElementPortletEntry, newCustomElementPortletEntry);
	}

	@Test(expected = NoSuchCustomElementPortletEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CustomElementPortletEntry>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"CustomElementPortletEntry", "mvccVersion", true, "uuid", true,
			"customElementPortletEntryId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"cssURLs", true, "instanceable", true, "tagAttributes", true,
			"name", true, "portletDisplayCategory", true, "tagName", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CustomElementPortletEntry newCustomElementPortletEntry =
			addCustomElementPortletEntry();

		CustomElementPortletEntry existingCustomElementPortletEntry =
			_persistence.fetchByPrimaryKey(
				newCustomElementPortletEntry.getPrimaryKey());

		Assert.assertEquals(
			existingCustomElementPortletEntry, newCustomElementPortletEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CustomElementPortletEntry missingCustomElementPortletEntry =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCustomElementPortletEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CustomElementPortletEntry newCustomElementPortletEntry1 =
			addCustomElementPortletEntry();
		CustomElementPortletEntry newCustomElementPortletEntry2 =
			addCustomElementPortletEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCustomElementPortletEntry1.getPrimaryKey());
		primaryKeys.add(newCustomElementPortletEntry2.getPrimaryKey());

		Map<Serializable, CustomElementPortletEntry>
			customElementPortletEntries = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, customElementPortletEntries.size());
		Assert.assertEquals(
			newCustomElementPortletEntry1,
			customElementPortletEntries.get(
				newCustomElementPortletEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newCustomElementPortletEntry2,
			customElementPortletEntries.get(
				newCustomElementPortletEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CustomElementPortletEntry>
			customElementPortletEntries = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(customElementPortletEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CustomElementPortletEntry newCustomElementPortletEntry =
			addCustomElementPortletEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCustomElementPortletEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CustomElementPortletEntry>
			customElementPortletEntries = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, customElementPortletEntries.size());
		Assert.assertEquals(
			newCustomElementPortletEntry,
			customElementPortletEntries.get(
				newCustomElementPortletEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CustomElementPortletEntry>
			customElementPortletEntries = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(customElementPortletEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CustomElementPortletEntry newCustomElementPortletEntry =
			addCustomElementPortletEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCustomElementPortletEntry.getPrimaryKey());

		Map<Serializable, CustomElementPortletEntry>
			customElementPortletEntries = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, customElementPortletEntries.size());
		Assert.assertEquals(
			newCustomElementPortletEntry,
			customElementPortletEntries.get(
				newCustomElementPortletEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CustomElementPortletEntryLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CustomElementPortletEntry>() {

				@Override
				public void performAction(
					CustomElementPortletEntry customElementPortletEntry) {

					Assert.assertNotNull(customElementPortletEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CustomElementPortletEntry newCustomElementPortletEntry =
			addCustomElementPortletEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CustomElementPortletEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"customElementPortletEntryId",
				newCustomElementPortletEntry.getCustomElementPortletEntryId()));

		List<CustomElementPortletEntry> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CustomElementPortletEntry existingCustomElementPortletEntry =
			result.get(0);

		Assert.assertEquals(
			existingCustomElementPortletEntry, newCustomElementPortletEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CustomElementPortletEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"customElementPortletEntryId", RandomTestUtil.nextLong()));

		List<CustomElementPortletEntry> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CustomElementPortletEntry newCustomElementPortletEntry =
			addCustomElementPortletEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CustomElementPortletEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("customElementPortletEntryId"));

		Object newCustomElementPortletEntryId =
			newCustomElementPortletEntry.getCustomElementPortletEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"customElementPortletEntryId",
				new Object[] {newCustomElementPortletEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCustomElementPortletEntryId = result.get(0);

		Assert.assertEquals(
			existingCustomElementPortletEntryId,
			newCustomElementPortletEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CustomElementPortletEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("customElementPortletEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"customElementPortletEntryId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CustomElementPortletEntry addCustomElementPortletEntry()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		CustomElementPortletEntry customElementPortletEntry =
			_persistence.create(pk);

		customElementPortletEntry.setMvccVersion(RandomTestUtil.nextLong());

		customElementPortletEntry.setUuid(RandomTestUtil.randomString());

		customElementPortletEntry.setCompanyId(RandomTestUtil.nextLong());

		customElementPortletEntry.setUserId(RandomTestUtil.nextLong());

		customElementPortletEntry.setUserName(RandomTestUtil.randomString());

		customElementPortletEntry.setCreateDate(RandomTestUtil.nextDate());

		customElementPortletEntry.setModifiedDate(RandomTestUtil.nextDate());

		customElementPortletEntry.setCssURLs(RandomTestUtil.randomString());

		customElementPortletEntry.setInstanceable();

		customElementPortletEntry.setTagAttributes(
			RandomTestUtil.randomString());

		customElementPortletEntry.setName(RandomTestUtil.randomString());

		customElementPortletEntry.setPortletDisplayCategory(
			RandomTestUtil.randomString());

		customElementPortletEntry.setTagName(RandomTestUtil.randomString());

		_customElementPortletEntries.add(
			_persistence.update(customElementPortletEntry));

		return customElementPortletEntry;
	}

	private List<CustomElementPortletEntry> _customElementPortletEntries =
		new ArrayList<CustomElementPortletEntry>();
	private CustomElementPortletEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}