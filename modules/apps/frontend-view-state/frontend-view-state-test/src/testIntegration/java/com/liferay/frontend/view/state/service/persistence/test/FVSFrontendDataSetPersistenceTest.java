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

package com.liferay.frontend.view.state.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.frontend.view.state.exception.NoSuchFVSFrontendDataSetException;
import com.liferay.frontend.view.state.model.FVSFrontendDataSet;
import com.liferay.frontend.view.state.service.FVSFrontendDataSetLocalServiceUtil;
import com.liferay.frontend.view.state.service.persistence.FVSFrontendDataSetPersistence;
import com.liferay.frontend.view.state.service.persistence.FVSFrontendDataSetUtil;
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
public class FVSFrontendDataSetPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.frontend.view.state.service"));

	@Before
	public void setUp() {
		_persistence = FVSFrontendDataSetUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<FVSFrontendDataSet> iterator = _fvsFrontendDataSets.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FVSFrontendDataSet fvsFrontendDataSet = _persistence.create(pk);

		Assert.assertNotNull(fvsFrontendDataSet);

		Assert.assertEquals(fvsFrontendDataSet.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		FVSFrontendDataSet newFVSFrontendDataSet = addFVSFrontendDataSet();

		_persistence.remove(newFVSFrontendDataSet);

		FVSFrontendDataSet existingFVSFrontendDataSet =
			_persistence.fetchByPrimaryKey(
				newFVSFrontendDataSet.getPrimaryKey());

		Assert.assertNull(existingFVSFrontendDataSet);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addFVSFrontendDataSet();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FVSFrontendDataSet newFVSFrontendDataSet = _persistence.create(pk);

		newFVSFrontendDataSet.setMvccVersion(RandomTestUtil.nextLong());

		newFVSFrontendDataSet.setUuid(RandomTestUtil.randomString());

		newFVSFrontendDataSet.setCompanyId(RandomTestUtil.nextLong());

		newFVSFrontendDataSet.setUserId(RandomTestUtil.nextLong());

		newFVSFrontendDataSet.setUserName(RandomTestUtil.randomString());

		newFVSFrontendDataSet.setCreateDate(RandomTestUtil.nextDate());

		newFVSFrontendDataSet.setModifiedDate(RandomTestUtil.nextDate());

		newFVSFrontendDataSet.setFVSEntryId(RandomTestUtil.nextLong());

		newFVSFrontendDataSet.setFDSName(RandomTestUtil.randomString());

		newFVSFrontendDataSet.setName(RandomTestUtil.randomString());

		newFVSFrontendDataSet.setPlid(RandomTestUtil.nextLong());

		newFVSFrontendDataSet.setPortletId(RandomTestUtil.randomString());

		newFVSFrontendDataSet.setSystem(RandomTestUtil.randomBoolean());

		_fvsFrontendDataSets.add(_persistence.update(newFVSFrontendDataSet));

		FVSFrontendDataSet existingFVSFrontendDataSet =
			_persistence.findByPrimaryKey(
				newFVSFrontendDataSet.getPrimaryKey());

		Assert.assertEquals(
			existingFVSFrontendDataSet.getMvccVersion(),
			newFVSFrontendDataSet.getMvccVersion());
		Assert.assertEquals(
			existingFVSFrontendDataSet.getUuid(),
			newFVSFrontendDataSet.getUuid());
		Assert.assertEquals(
			existingFVSFrontendDataSet.getFVSFrontendDataSetId(),
			newFVSFrontendDataSet.getFVSFrontendDataSetId());
		Assert.assertEquals(
			existingFVSFrontendDataSet.getCompanyId(),
			newFVSFrontendDataSet.getCompanyId());
		Assert.assertEquals(
			existingFVSFrontendDataSet.getUserId(),
			newFVSFrontendDataSet.getUserId());
		Assert.assertEquals(
			existingFVSFrontendDataSet.getUserName(),
			newFVSFrontendDataSet.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingFVSFrontendDataSet.getCreateDate()),
			Time.getShortTimestamp(newFVSFrontendDataSet.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingFVSFrontendDataSet.getModifiedDate()),
			Time.getShortTimestamp(newFVSFrontendDataSet.getModifiedDate()));
		Assert.assertEquals(
			existingFVSFrontendDataSet.getFVSEntryId(),
			newFVSFrontendDataSet.getFVSEntryId());
		Assert.assertEquals(
			existingFVSFrontendDataSet.getFDSName(),
			newFVSFrontendDataSet.getFDSName());
		Assert.assertEquals(
			existingFVSFrontendDataSet.getName(),
			newFVSFrontendDataSet.getName());
		Assert.assertEquals(
			existingFVSFrontendDataSet.getPlid(),
			newFVSFrontendDataSet.getPlid());
		Assert.assertEquals(
			existingFVSFrontendDataSet.getPortletId(),
			newFVSFrontendDataSet.getPortletId());
		Assert.assertEquals(
			existingFVSFrontendDataSet.isSystem(),
			newFVSFrontendDataSet.isSystem());
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
	public void testCountByF_S() throws Exception {
		_persistence.countByF_S("", RandomTestUtil.randomBoolean());

		_persistence.countByF_S("null", RandomTestUtil.randomBoolean());

		_persistence.countByF_S((String)null, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByU_F_P_P_S() throws Exception {
		_persistence.countByU_F_P_P_S(
			RandomTestUtil.nextLong(), "", RandomTestUtil.nextLong(), "",
			RandomTestUtil.randomBoolean());

		_persistence.countByU_F_P_P_S(
			0L, "null", 0L, "null", RandomTestUtil.randomBoolean());

		_persistence.countByU_F_P_P_S(
			0L, (String)null, 0L, (String)null, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		FVSFrontendDataSet newFVSFrontendDataSet = addFVSFrontendDataSet();

		FVSFrontendDataSet existingFVSFrontendDataSet =
			_persistence.findByPrimaryKey(
				newFVSFrontendDataSet.getPrimaryKey());

		Assert.assertEquals(existingFVSFrontendDataSet, newFVSFrontendDataSet);
	}

	@Test(expected = NoSuchFVSFrontendDataSetException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<FVSFrontendDataSet> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"FVSFrontendDataSet", "mvccVersion", true, "uuid", true,
			"fvsFrontendDataSetId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"fvsEntryId", true, "fdsName", true, "name", true, "plid", true,
			"portletId", true, "system", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		FVSFrontendDataSet newFVSFrontendDataSet = addFVSFrontendDataSet();

		FVSFrontendDataSet existingFVSFrontendDataSet =
			_persistence.fetchByPrimaryKey(
				newFVSFrontendDataSet.getPrimaryKey());

		Assert.assertEquals(existingFVSFrontendDataSet, newFVSFrontendDataSet);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FVSFrontendDataSet missingFVSFrontendDataSet =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingFVSFrontendDataSet);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		FVSFrontendDataSet newFVSFrontendDataSet1 = addFVSFrontendDataSet();
		FVSFrontendDataSet newFVSFrontendDataSet2 = addFVSFrontendDataSet();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFVSFrontendDataSet1.getPrimaryKey());
		primaryKeys.add(newFVSFrontendDataSet2.getPrimaryKey());

		Map<Serializable, FVSFrontendDataSet> fvsFrontendDataSets =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, fvsFrontendDataSets.size());
		Assert.assertEquals(
			newFVSFrontendDataSet1,
			fvsFrontendDataSets.get(newFVSFrontendDataSet1.getPrimaryKey()));
		Assert.assertEquals(
			newFVSFrontendDataSet2,
			fvsFrontendDataSets.get(newFVSFrontendDataSet2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, FVSFrontendDataSet> fvsFrontendDataSets =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(fvsFrontendDataSets.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		FVSFrontendDataSet newFVSFrontendDataSet = addFVSFrontendDataSet();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFVSFrontendDataSet.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, FVSFrontendDataSet> fvsFrontendDataSets =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, fvsFrontendDataSets.size());
		Assert.assertEquals(
			newFVSFrontendDataSet,
			fvsFrontendDataSets.get(newFVSFrontendDataSet.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, FVSFrontendDataSet> fvsFrontendDataSets =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(fvsFrontendDataSets.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		FVSFrontendDataSet newFVSFrontendDataSet = addFVSFrontendDataSet();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFVSFrontendDataSet.getPrimaryKey());

		Map<Serializable, FVSFrontendDataSet> fvsFrontendDataSets =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, fvsFrontendDataSets.size());
		Assert.assertEquals(
			newFVSFrontendDataSet,
			fvsFrontendDataSets.get(newFVSFrontendDataSet.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			FVSFrontendDataSetLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<FVSFrontendDataSet>() {

				@Override
				public void performAction(
					FVSFrontendDataSet fvsFrontendDataSet) {

					Assert.assertNotNull(fvsFrontendDataSet);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		FVSFrontendDataSet newFVSFrontendDataSet = addFVSFrontendDataSet();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FVSFrontendDataSet.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"fvsFrontendDataSetId",
				newFVSFrontendDataSet.getFVSFrontendDataSetId()));

		List<FVSFrontendDataSet> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		FVSFrontendDataSet existingFVSFrontendDataSet = result.get(0);

		Assert.assertEquals(existingFVSFrontendDataSet, newFVSFrontendDataSet);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FVSFrontendDataSet.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"fvsFrontendDataSetId", RandomTestUtil.nextLong()));

		List<FVSFrontendDataSet> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		FVSFrontendDataSet newFVSFrontendDataSet = addFVSFrontendDataSet();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FVSFrontendDataSet.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("fvsFrontendDataSetId"));

		Object newFVSFrontendDataSetId =
			newFVSFrontendDataSet.getFVSFrontendDataSetId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"fvsFrontendDataSetId",
				new Object[] {newFVSFrontendDataSetId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFVSFrontendDataSetId = result.get(0);

		Assert.assertEquals(
			existingFVSFrontendDataSetId, newFVSFrontendDataSetId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FVSFrontendDataSet.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("fvsFrontendDataSetId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"fvsFrontendDataSetId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected FVSFrontendDataSet addFVSFrontendDataSet() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FVSFrontendDataSet fvsFrontendDataSet = _persistence.create(pk);

		fvsFrontendDataSet.setMvccVersion(RandomTestUtil.nextLong());

		fvsFrontendDataSet.setUuid(RandomTestUtil.randomString());

		fvsFrontendDataSet.setCompanyId(RandomTestUtil.nextLong());

		fvsFrontendDataSet.setUserId(RandomTestUtil.nextLong());

		fvsFrontendDataSet.setUserName(RandomTestUtil.randomString());

		fvsFrontendDataSet.setCreateDate(RandomTestUtil.nextDate());

		fvsFrontendDataSet.setModifiedDate(RandomTestUtil.nextDate());

		fvsFrontendDataSet.setFVSEntryId(RandomTestUtil.nextLong());

		fvsFrontendDataSet.setFDSName(RandomTestUtil.randomString());

		fvsFrontendDataSet.setName(RandomTestUtil.randomString());

		fvsFrontendDataSet.setPlid(RandomTestUtil.nextLong());

		fvsFrontendDataSet.setPortletId(RandomTestUtil.randomString());

		fvsFrontendDataSet.setSystem(RandomTestUtil.randomBoolean());

		_fvsFrontendDataSets.add(_persistence.update(fvsFrontendDataSet));

		return fvsFrontendDataSet;
	}

	private List<FVSFrontendDataSet> _fvsFrontendDataSets =
		new ArrayList<FVSFrontendDataSet>();
	private FVSFrontendDataSetPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}