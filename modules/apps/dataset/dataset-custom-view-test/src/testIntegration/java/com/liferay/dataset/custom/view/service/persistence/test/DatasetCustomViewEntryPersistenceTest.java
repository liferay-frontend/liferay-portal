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

package com.liferay.dataset.custom.view.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dataset.custom.view.exception.NoSuchEntryException;
import com.liferay.dataset.custom.view.model.DatasetCustomViewEntry;
import com.liferay.dataset.custom.view.service.DatasetCustomViewEntryLocalServiceUtil;
import com.liferay.dataset.custom.view.service.persistence.DatasetCustomViewEntryPersistence;
import com.liferay.dataset.custom.view.service.persistence.DatasetCustomViewEntryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
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
public class DatasetCustomViewEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.dataset.custom.view.service"));

	@Before
	public void setUp() {
		_persistence = DatasetCustomViewEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<DatasetCustomViewEntry> iterator =
			_datasetCustomViewEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DatasetCustomViewEntry datasetCustomViewEntry = _persistence.create(pk);

		Assert.assertNotNull(datasetCustomViewEntry);

		Assert.assertEquals(datasetCustomViewEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DatasetCustomViewEntry newDatasetCustomViewEntry =
			addDatasetCustomViewEntry();

		_persistence.remove(newDatasetCustomViewEntry);

		DatasetCustomViewEntry existingDatasetCustomViewEntry =
			_persistence.fetchByPrimaryKey(
				newDatasetCustomViewEntry.getPrimaryKey());

		Assert.assertNull(existingDatasetCustomViewEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDatasetCustomViewEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DatasetCustomViewEntry newDatasetCustomViewEntry = _persistence.create(
			pk);

		newDatasetCustomViewEntry.setMvccVersion(RandomTestUtil.nextLong());

		newDatasetCustomViewEntry.setUuid(RandomTestUtil.randomString());

		newDatasetCustomViewEntry.setCompanyId(RandomTestUtil.nextLong());

		newDatasetCustomViewEntry.setUserId(RandomTestUtil.nextLong());

		newDatasetCustomViewEntry.setUserName(RandomTestUtil.randomString());

		newDatasetCustomViewEntry.setCreateDate(RandomTestUtil.nextDate());

		newDatasetCustomViewEntry.setModifiedDate(RandomTestUtil.nextDate());

		newDatasetCustomViewEntry.setDatasetDisplayId(
			RandomTestUtil.randomString());

		newDatasetCustomViewEntry.setPlid(RandomTestUtil.nextLong());

		newDatasetCustomViewEntry.setPortletId(RandomTestUtil.randomString());

		newDatasetCustomViewEntry.setSettingsJSON(
			RandomTestUtil.randomString());

		_datasetCustomViewEntries.add(
			_persistence.update(newDatasetCustomViewEntry));

		DatasetCustomViewEntry existingDatasetCustomViewEntry =
			_persistence.findByPrimaryKey(
				newDatasetCustomViewEntry.getPrimaryKey());

		Assert.assertEquals(
			existingDatasetCustomViewEntry.getMvccVersion(),
			newDatasetCustomViewEntry.getMvccVersion());
		Assert.assertEquals(
			existingDatasetCustomViewEntry.getUuid(),
			newDatasetCustomViewEntry.getUuid());
		Assert.assertEquals(
			existingDatasetCustomViewEntry.getDatasetCustomViewEntryId(),
			newDatasetCustomViewEntry.getDatasetCustomViewEntryId());
		Assert.assertEquals(
			existingDatasetCustomViewEntry.getCompanyId(),
			newDatasetCustomViewEntry.getCompanyId());
		Assert.assertEquals(
			existingDatasetCustomViewEntry.getUserId(),
			newDatasetCustomViewEntry.getUserId());
		Assert.assertEquals(
			existingDatasetCustomViewEntry.getUserName(),
			newDatasetCustomViewEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingDatasetCustomViewEntry.getCreateDate()),
			Time.getShortTimestamp(newDatasetCustomViewEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingDatasetCustomViewEntry.getModifiedDate()),
			Time.getShortTimestamp(
				newDatasetCustomViewEntry.getModifiedDate()));
		Assert.assertEquals(
			existingDatasetCustomViewEntry.getDatasetDisplayId(),
			newDatasetCustomViewEntry.getDatasetDisplayId());
		Assert.assertEquals(
			existingDatasetCustomViewEntry.getPlid(),
			newDatasetCustomViewEntry.getPlid());
		Assert.assertEquals(
			existingDatasetCustomViewEntry.getPortletId(),
			newDatasetCustomViewEntry.getPortletId());
		Assert.assertEquals(
			existingDatasetCustomViewEntry.getSettingsJSON(),
			newDatasetCustomViewEntry.getSettingsJSON());
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
	public void testCountByU_D_P_P() throws Exception {
		_persistence.countByU_D_P_P(
			RandomTestUtil.nextLong(), "", RandomTestUtil.nextLong(), "");

		_persistence.countByU_D_P_P(0L, "null", 0L, "null");

		_persistence.countByU_D_P_P(0L, (String)null, 0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DatasetCustomViewEntry newDatasetCustomViewEntry =
			addDatasetCustomViewEntry();

		DatasetCustomViewEntry existingDatasetCustomViewEntry =
			_persistence.findByPrimaryKey(
				newDatasetCustomViewEntry.getPrimaryKey());

		Assert.assertEquals(
			existingDatasetCustomViewEntry, newDatasetCustomViewEntry);
	}

	@Test(expected = NoSuchEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<DatasetCustomViewEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"DatasetCustomViewEntry", "mvccVersion", true, "uuid", true,
			"datasetCustomViewEntryId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"datasetDisplayId", true, "plid", true, "portletId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DatasetCustomViewEntry newDatasetCustomViewEntry =
			addDatasetCustomViewEntry();

		DatasetCustomViewEntry existingDatasetCustomViewEntry =
			_persistence.fetchByPrimaryKey(
				newDatasetCustomViewEntry.getPrimaryKey());

		Assert.assertEquals(
			existingDatasetCustomViewEntry, newDatasetCustomViewEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DatasetCustomViewEntry missingDatasetCustomViewEntry =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDatasetCustomViewEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		DatasetCustomViewEntry newDatasetCustomViewEntry1 =
			addDatasetCustomViewEntry();
		DatasetCustomViewEntry newDatasetCustomViewEntry2 =
			addDatasetCustomViewEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDatasetCustomViewEntry1.getPrimaryKey());
		primaryKeys.add(newDatasetCustomViewEntry2.getPrimaryKey());

		Map<Serializable, DatasetCustomViewEntry> datasetCustomViewEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, datasetCustomViewEntries.size());
		Assert.assertEquals(
			newDatasetCustomViewEntry1,
			datasetCustomViewEntries.get(
				newDatasetCustomViewEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newDatasetCustomViewEntry2,
			datasetCustomViewEntries.get(
				newDatasetCustomViewEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, DatasetCustomViewEntry> datasetCustomViewEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(datasetCustomViewEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		DatasetCustomViewEntry newDatasetCustomViewEntry =
			addDatasetCustomViewEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDatasetCustomViewEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, DatasetCustomViewEntry> datasetCustomViewEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, datasetCustomViewEntries.size());
		Assert.assertEquals(
			newDatasetCustomViewEntry,
			datasetCustomViewEntries.get(
				newDatasetCustomViewEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, DatasetCustomViewEntry> datasetCustomViewEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(datasetCustomViewEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		DatasetCustomViewEntry newDatasetCustomViewEntry =
			addDatasetCustomViewEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDatasetCustomViewEntry.getPrimaryKey());

		Map<Serializable, DatasetCustomViewEntry> datasetCustomViewEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, datasetCustomViewEntries.size());
		Assert.assertEquals(
			newDatasetCustomViewEntry,
			datasetCustomViewEntries.get(
				newDatasetCustomViewEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			DatasetCustomViewEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<DatasetCustomViewEntry>() {

				@Override
				public void performAction(
					DatasetCustomViewEntry datasetCustomViewEntry) {

					Assert.assertNotNull(datasetCustomViewEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		DatasetCustomViewEntry newDatasetCustomViewEntry =
			addDatasetCustomViewEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DatasetCustomViewEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"datasetCustomViewEntryId",
				newDatasetCustomViewEntry.getDatasetCustomViewEntryId()));

		List<DatasetCustomViewEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		DatasetCustomViewEntry existingDatasetCustomViewEntry = result.get(0);

		Assert.assertEquals(
			existingDatasetCustomViewEntry, newDatasetCustomViewEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DatasetCustomViewEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"datasetCustomViewEntryId", RandomTestUtil.nextLong()));

		List<DatasetCustomViewEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		DatasetCustomViewEntry newDatasetCustomViewEntry =
			addDatasetCustomViewEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DatasetCustomViewEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("datasetCustomViewEntryId"));

		Object newDatasetCustomViewEntryId =
			newDatasetCustomViewEntry.getDatasetCustomViewEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"datasetCustomViewEntryId",
				new Object[] {newDatasetCustomViewEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingDatasetCustomViewEntryId = result.get(0);

		Assert.assertEquals(
			existingDatasetCustomViewEntryId, newDatasetCustomViewEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DatasetCustomViewEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("datasetCustomViewEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"datasetCustomViewEntryId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		DatasetCustomViewEntry newDatasetCustomViewEntry =
			addDatasetCustomViewEntry();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newDatasetCustomViewEntry.getPrimaryKey()));
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromDatabase()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(true);
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromSession()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(false);
	}

	private void _testResetOriginalValuesWithDynamicQuery(boolean clearSession)
		throws Exception {

		DatasetCustomViewEntry newDatasetCustomViewEntry =
			addDatasetCustomViewEntry();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DatasetCustomViewEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"datasetCustomViewEntryId",
				newDatasetCustomViewEntry.getDatasetCustomViewEntryId()));

		List<DatasetCustomViewEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		DatasetCustomViewEntry datasetCustomViewEntry) {

		Assert.assertEquals(
			Long.valueOf(datasetCustomViewEntry.getUserId()),
			ReflectionTestUtil.<Long>invoke(
				datasetCustomViewEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "userId"));
		Assert.assertEquals(
			datasetCustomViewEntry.getDatasetDisplayId(),
			ReflectionTestUtil.invoke(
				datasetCustomViewEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "datasetDisplayId"));
		Assert.assertEquals(
			Long.valueOf(datasetCustomViewEntry.getPlid()),
			ReflectionTestUtil.<Long>invoke(
				datasetCustomViewEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "plid"));
		Assert.assertEquals(
			datasetCustomViewEntry.getPortletId(),
			ReflectionTestUtil.invoke(
				datasetCustomViewEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "portletId"));
	}

	protected DatasetCustomViewEntry addDatasetCustomViewEntry()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		DatasetCustomViewEntry datasetCustomViewEntry = _persistence.create(pk);

		datasetCustomViewEntry.setMvccVersion(RandomTestUtil.nextLong());

		datasetCustomViewEntry.setUuid(RandomTestUtil.randomString());

		datasetCustomViewEntry.setCompanyId(RandomTestUtil.nextLong());

		datasetCustomViewEntry.setUserId(RandomTestUtil.nextLong());

		datasetCustomViewEntry.setUserName(RandomTestUtil.randomString());

		datasetCustomViewEntry.setCreateDate(RandomTestUtil.nextDate());

		datasetCustomViewEntry.setModifiedDate(RandomTestUtil.nextDate());

		datasetCustomViewEntry.setDatasetDisplayId(
			RandomTestUtil.randomString());

		datasetCustomViewEntry.setPlid(RandomTestUtil.nextLong());

		datasetCustomViewEntry.setPortletId(RandomTestUtil.randomString());

		datasetCustomViewEntry.setSettingsJSON(RandomTestUtil.randomString());

		_datasetCustomViewEntries.add(
			_persistence.update(datasetCustomViewEntry));

		return datasetCustomViewEntry;
	}

	private List<DatasetCustomViewEntry> _datasetCustomViewEntries =
		new ArrayList<DatasetCustomViewEntry>();
	private DatasetCustomViewEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}