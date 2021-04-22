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

package com.liferay.dataset.custom.view.service.persistence.impl;

import com.liferay.dataset.custom.view.exception.NoSuchEntryException;
import com.liferay.dataset.custom.view.model.DatasetCustomViewEntry;
import com.liferay.dataset.custom.view.model.DatasetCustomViewEntryTable;
import com.liferay.dataset.custom.view.model.impl.DatasetCustomViewEntryImpl;
import com.liferay.dataset.custom.view.model.impl.DatasetCustomViewEntryModelImpl;
import com.liferay.dataset.custom.view.service.persistence.DatasetCustomViewEntryPersistence;
import com.liferay.dataset.custom.view.service.persistence.impl.constants.DatasetCustomViewPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the dataset custom view entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	service = {DatasetCustomViewEntryPersistence.class, BasePersistence.class}
)
public class DatasetCustomViewEntryPersistenceImpl
	extends BasePersistenceImpl<DatasetCustomViewEntry>
	implements DatasetCustomViewEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DatasetCustomViewEntryUtil</code> to access the dataset custom view entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DatasetCustomViewEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the dataset custom view entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching dataset custom view entries
	 */
	@Override
	public List<DatasetCustomViewEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dataset custom view entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetCustomViewEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of dataset custom view entries
	 * @param end the upper bound of the range of dataset custom view entries (not inclusive)
	 * @return the range of matching dataset custom view entries
	 */
	@Override
	public List<DatasetCustomViewEntry> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dataset custom view entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetCustomViewEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of dataset custom view entries
	 * @param end the upper bound of the range of dataset custom view entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dataset custom view entries
	 */
	@Override
	public List<DatasetCustomViewEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DatasetCustomViewEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the dataset custom view entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetCustomViewEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of dataset custom view entries
	 * @param end the upper bound of the range of dataset custom view entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching dataset custom view entries
	 */
	@Override
	public List<DatasetCustomViewEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DatasetCustomViewEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<DatasetCustomViewEntry> list = null;

		if (useFinderCache) {
			list = (List<DatasetCustomViewEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (DatasetCustomViewEntry datasetCustomViewEntry : list) {
					if (!uuid.equals(datasetCustomViewEntry.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_DATASETCUSTOMVIEWENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DatasetCustomViewEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				list = (List<DatasetCustomViewEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first dataset custom view entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dataset custom view entry
	 * @throws NoSuchEntryException if a matching dataset custom view entry could not be found
	 */
	@Override
	public DatasetCustomViewEntry findByUuid_First(
			String uuid,
			OrderByComparator<DatasetCustomViewEntry> orderByComparator)
		throws NoSuchEntryException {

		DatasetCustomViewEntry datasetCustomViewEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (datasetCustomViewEntry != null) {
			return datasetCustomViewEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first dataset custom view entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dataset custom view entry, or <code>null</code> if a matching dataset custom view entry could not be found
	 */
	@Override
	public DatasetCustomViewEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<DatasetCustomViewEntry> orderByComparator) {

		List<DatasetCustomViewEntry> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last dataset custom view entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dataset custom view entry
	 * @throws NoSuchEntryException if a matching dataset custom view entry could not be found
	 */
	@Override
	public DatasetCustomViewEntry findByUuid_Last(
			String uuid,
			OrderByComparator<DatasetCustomViewEntry> orderByComparator)
		throws NoSuchEntryException {

		DatasetCustomViewEntry datasetCustomViewEntry = fetchByUuid_Last(
			uuid, orderByComparator);

		if (datasetCustomViewEntry != null) {
			return datasetCustomViewEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last dataset custom view entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dataset custom view entry, or <code>null</code> if a matching dataset custom view entry could not be found
	 */
	@Override
	public DatasetCustomViewEntry fetchByUuid_Last(
		String uuid,
		OrderByComparator<DatasetCustomViewEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<DatasetCustomViewEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the dataset custom view entries before and after the current dataset custom view entry in the ordered set where uuid = &#63;.
	 *
	 * @param datasetCustomViewEntryId the primary key of the current dataset custom view entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dataset custom view entry
	 * @throws NoSuchEntryException if a dataset custom view entry with the primary key could not be found
	 */
	@Override
	public DatasetCustomViewEntry[] findByUuid_PrevAndNext(
			long datasetCustomViewEntryId, String uuid,
			OrderByComparator<DatasetCustomViewEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		DatasetCustomViewEntry datasetCustomViewEntry = findByPrimaryKey(
			datasetCustomViewEntryId);

		Session session = null;

		try {
			session = openSession();

			DatasetCustomViewEntry[] array = new DatasetCustomViewEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, datasetCustomViewEntry, uuid, orderByComparator, true);

			array[1] = datasetCustomViewEntry;

			array[2] = getByUuid_PrevAndNext(
				session, datasetCustomViewEntry, uuid, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected DatasetCustomViewEntry getByUuid_PrevAndNext(
		Session session, DatasetCustomViewEntry datasetCustomViewEntry,
		String uuid,
		OrderByComparator<DatasetCustomViewEntry> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_DATASETCUSTOMVIEWENTRY_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(DatasetCustomViewEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						datasetCustomViewEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DatasetCustomViewEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the dataset custom view entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (DatasetCustomViewEntry datasetCustomViewEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(datasetCustomViewEntry);
		}
	}

	/**
	 * Returns the number of dataset custom view entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching dataset custom view entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_DATASETCUSTOMVIEWENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"datasetCustomViewEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(datasetCustomViewEntry.uuid IS NULL OR datasetCustomViewEntry.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the dataset custom view entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching dataset custom view entries
	 */
	@Override
	public List<DatasetCustomViewEntry> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dataset custom view entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetCustomViewEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of dataset custom view entries
	 * @param end the upper bound of the range of dataset custom view entries (not inclusive)
	 * @return the range of matching dataset custom view entries
	 */
	@Override
	public List<DatasetCustomViewEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dataset custom view entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetCustomViewEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of dataset custom view entries
	 * @param end the upper bound of the range of dataset custom view entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dataset custom view entries
	 */
	@Override
	public List<DatasetCustomViewEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DatasetCustomViewEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the dataset custom view entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetCustomViewEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of dataset custom view entries
	 * @param end the upper bound of the range of dataset custom view entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching dataset custom view entries
	 */
	@Override
	public List<DatasetCustomViewEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DatasetCustomViewEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<DatasetCustomViewEntry> list = null;

		if (useFinderCache) {
			list = (List<DatasetCustomViewEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (DatasetCustomViewEntry datasetCustomViewEntry : list) {
					if (!uuid.equals(datasetCustomViewEntry.getUuid()) ||
						(companyId != datasetCustomViewEntry.getCompanyId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_DATASETCUSTOMVIEWENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DatasetCustomViewEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				list = (List<DatasetCustomViewEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first dataset custom view entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dataset custom view entry
	 * @throws NoSuchEntryException if a matching dataset custom view entry could not be found
	 */
	@Override
	public DatasetCustomViewEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<DatasetCustomViewEntry> orderByComparator)
		throws NoSuchEntryException {

		DatasetCustomViewEntry datasetCustomViewEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (datasetCustomViewEntry != null) {
			return datasetCustomViewEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first dataset custom view entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dataset custom view entry, or <code>null</code> if a matching dataset custom view entry could not be found
	 */
	@Override
	public DatasetCustomViewEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<DatasetCustomViewEntry> orderByComparator) {

		List<DatasetCustomViewEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last dataset custom view entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dataset custom view entry
	 * @throws NoSuchEntryException if a matching dataset custom view entry could not be found
	 */
	@Override
	public DatasetCustomViewEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<DatasetCustomViewEntry> orderByComparator)
		throws NoSuchEntryException {

		DatasetCustomViewEntry datasetCustomViewEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (datasetCustomViewEntry != null) {
			return datasetCustomViewEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last dataset custom view entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dataset custom view entry, or <code>null</code> if a matching dataset custom view entry could not be found
	 */
	@Override
	public DatasetCustomViewEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<DatasetCustomViewEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<DatasetCustomViewEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the dataset custom view entries before and after the current dataset custom view entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param datasetCustomViewEntryId the primary key of the current dataset custom view entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dataset custom view entry
	 * @throws NoSuchEntryException if a dataset custom view entry with the primary key could not be found
	 */
	@Override
	public DatasetCustomViewEntry[] findByUuid_C_PrevAndNext(
			long datasetCustomViewEntryId, String uuid, long companyId,
			OrderByComparator<DatasetCustomViewEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		DatasetCustomViewEntry datasetCustomViewEntry = findByPrimaryKey(
			datasetCustomViewEntryId);

		Session session = null;

		try {
			session = openSession();

			DatasetCustomViewEntry[] array = new DatasetCustomViewEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, datasetCustomViewEntry, uuid, companyId,
				orderByComparator, true);

			array[1] = datasetCustomViewEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, datasetCustomViewEntry, uuid, companyId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected DatasetCustomViewEntry getByUuid_C_PrevAndNext(
		Session session, DatasetCustomViewEntry datasetCustomViewEntry,
		String uuid, long companyId,
		OrderByComparator<DatasetCustomViewEntry> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_DATASETCUSTOMVIEWENTRY_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(DatasetCustomViewEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						datasetCustomViewEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DatasetCustomViewEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the dataset custom view entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (DatasetCustomViewEntry datasetCustomViewEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(datasetCustomViewEntry);
		}
	}

	/**
	 * Returns the number of dataset custom view entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching dataset custom view entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DATASETCUSTOMVIEWENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"datasetCustomViewEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(datasetCustomViewEntry.uuid IS NULL OR datasetCustomViewEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"datasetCustomViewEntry.companyId = ?";

	private FinderPath _finderPathFetchByU_D_P_P;
	private FinderPath _finderPathCountByU_D_P_P;

	/**
	 * Returns the dataset custom view entry where userId = &#63; and datasetDisplayId = &#63; and plid = &#63; and portletId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param datasetDisplayId the dataset display ID
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the matching dataset custom view entry
	 * @throws NoSuchEntryException if a matching dataset custom view entry could not be found
	 */
	@Override
	public DatasetCustomViewEntry findByU_D_P_P(
			long userId, String datasetDisplayId, long plid, String portletId)
		throws NoSuchEntryException {

		DatasetCustomViewEntry datasetCustomViewEntry = fetchByU_D_P_P(
			userId, datasetDisplayId, plid, portletId);

		if (datasetCustomViewEntry == null) {
			StringBundler sb = new StringBundler(10);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("userId=");
			sb.append(userId);

			sb.append(", datasetDisplayId=");
			sb.append(datasetDisplayId);

			sb.append(", plid=");
			sb.append(plid);

			sb.append(", portletId=");
			sb.append(portletId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryException(sb.toString());
		}

		return datasetCustomViewEntry;
	}

	/**
	 * Returns the dataset custom view entry where userId = &#63; and datasetDisplayId = &#63; and plid = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param datasetDisplayId the dataset display ID
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the matching dataset custom view entry, or <code>null</code> if a matching dataset custom view entry could not be found
	 */
	@Override
	public DatasetCustomViewEntry fetchByU_D_P_P(
		long userId, String datasetDisplayId, long plid, String portletId) {

		return fetchByU_D_P_P(userId, datasetDisplayId, plid, portletId, true);
	}

	/**
	 * Returns the dataset custom view entry where userId = &#63; and datasetDisplayId = &#63; and plid = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param datasetDisplayId the dataset display ID
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching dataset custom view entry, or <code>null</code> if a matching dataset custom view entry could not be found
	 */
	@Override
	public DatasetCustomViewEntry fetchByU_D_P_P(
		long userId, String datasetDisplayId, long plid, String portletId,
		boolean useFinderCache) {

		datasetDisplayId = Objects.toString(datasetDisplayId, "");
		portletId = Objects.toString(portletId, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				userId, datasetDisplayId, plid, portletId
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByU_D_P_P, finderArgs);
		}

		if (result instanceof DatasetCustomViewEntry) {
			DatasetCustomViewEntry datasetCustomViewEntry =
				(DatasetCustomViewEntry)result;

			if ((userId != datasetCustomViewEntry.getUserId()) ||
				!Objects.equals(
					datasetDisplayId,
					datasetCustomViewEntry.getDatasetDisplayId()) ||
				(plid != datasetCustomViewEntry.getPlid()) ||
				!Objects.equals(
					portletId, datasetCustomViewEntry.getPortletId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_SQL_SELECT_DATASETCUSTOMVIEWENTRY_WHERE);

			sb.append(_FINDER_COLUMN_U_D_P_P_USERID_2);

			boolean bindDatasetDisplayId = false;

			if (datasetDisplayId.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_D_P_P_DATASETDISPLAYID_3);
			}
			else {
				bindDatasetDisplayId = true;

				sb.append(_FINDER_COLUMN_U_D_P_P_DATASETDISPLAYID_2);
			}

			sb.append(_FINDER_COLUMN_U_D_P_P_PLID_2);

			boolean bindPortletId = false;

			if (portletId.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_D_P_P_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				sb.append(_FINDER_COLUMN_U_D_P_P_PORTLETID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				if (bindDatasetDisplayId) {
					queryPos.add(datasetDisplayId);
				}

				queryPos.add(plid);

				if (bindPortletId) {
					queryPos.add(portletId);
				}

				List<DatasetCustomViewEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByU_D_P_P, finderArgs, list);
					}
				}
				else {
					DatasetCustomViewEntry datasetCustomViewEntry = list.get(0);

					result = datasetCustomViewEntry;

					cacheResult(datasetCustomViewEntry);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (DatasetCustomViewEntry)result;
		}
	}

	/**
	 * Removes the dataset custom view entry where userId = &#63; and datasetDisplayId = &#63; and plid = &#63; and portletId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param datasetDisplayId the dataset display ID
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the dataset custom view entry that was removed
	 */
	@Override
	public DatasetCustomViewEntry removeByU_D_P_P(
			long userId, String datasetDisplayId, long plid, String portletId)
		throws NoSuchEntryException {

		DatasetCustomViewEntry datasetCustomViewEntry = findByU_D_P_P(
			userId, datasetDisplayId, plid, portletId);

		return remove(datasetCustomViewEntry);
	}

	/**
	 * Returns the number of dataset custom view entries where userId = &#63; and datasetDisplayId = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param userId the user ID
	 * @param datasetDisplayId the dataset display ID
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the number of matching dataset custom view entries
	 */
	@Override
	public int countByU_D_P_P(
		long userId, String datasetDisplayId, long plid, String portletId) {

		datasetDisplayId = Objects.toString(datasetDisplayId, "");
		portletId = Objects.toString(portletId, "");

		FinderPath finderPath = _finderPathCountByU_D_P_P;

		Object[] finderArgs = new Object[] {
			userId, datasetDisplayId, plid, portletId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_DATASETCUSTOMVIEWENTRY_WHERE);

			sb.append(_FINDER_COLUMN_U_D_P_P_USERID_2);

			boolean bindDatasetDisplayId = false;

			if (datasetDisplayId.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_D_P_P_DATASETDISPLAYID_3);
			}
			else {
				bindDatasetDisplayId = true;

				sb.append(_FINDER_COLUMN_U_D_P_P_DATASETDISPLAYID_2);
			}

			sb.append(_FINDER_COLUMN_U_D_P_P_PLID_2);

			boolean bindPortletId = false;

			if (portletId.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_D_P_P_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				sb.append(_FINDER_COLUMN_U_D_P_P_PORTLETID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				if (bindDatasetDisplayId) {
					queryPos.add(datasetDisplayId);
				}

				queryPos.add(plid);

				if (bindPortletId) {
					queryPos.add(portletId);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_U_D_P_P_USERID_2 =
		"datasetCustomViewEntry.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_D_P_P_DATASETDISPLAYID_2 =
		"datasetCustomViewEntry.datasetDisplayId = ? AND ";

	private static final String _FINDER_COLUMN_U_D_P_P_DATASETDISPLAYID_3 =
		"(datasetCustomViewEntry.datasetDisplayId IS NULL OR datasetCustomViewEntry.datasetDisplayId = '') AND ";

	private static final String _FINDER_COLUMN_U_D_P_P_PLID_2 =
		"datasetCustomViewEntry.plid = ? AND ";

	private static final String _FINDER_COLUMN_U_D_P_P_PORTLETID_2 =
		"datasetCustomViewEntry.portletId = ?";

	private static final String _FINDER_COLUMN_U_D_P_P_PORTLETID_3 =
		"(datasetCustomViewEntry.portletId IS NULL OR datasetCustomViewEntry.portletId = '')";

	public DatasetCustomViewEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(DatasetCustomViewEntry.class);

		setModelImplClass(DatasetCustomViewEntryImpl.class);
		setModelPKClass(long.class);

		setTable(DatasetCustomViewEntryTable.INSTANCE);
	}

	/**
	 * Caches the dataset custom view entry in the entity cache if it is enabled.
	 *
	 * @param datasetCustomViewEntry the dataset custom view entry
	 */
	@Override
	public void cacheResult(DatasetCustomViewEntry datasetCustomViewEntry) {
		entityCache.putResult(
			DatasetCustomViewEntryImpl.class,
			datasetCustomViewEntry.getPrimaryKey(), datasetCustomViewEntry);

		finderCache.putResult(
			_finderPathFetchByU_D_P_P,
			new Object[] {
				datasetCustomViewEntry.getUserId(),
				datasetCustomViewEntry.getDatasetDisplayId(),
				datasetCustomViewEntry.getPlid(),
				datasetCustomViewEntry.getPortletId()
			},
			datasetCustomViewEntry);
	}

	/**
	 * Caches the dataset custom view entries in the entity cache if it is enabled.
	 *
	 * @param datasetCustomViewEntries the dataset custom view entries
	 */
	@Override
	public void cacheResult(
		List<DatasetCustomViewEntry> datasetCustomViewEntries) {

		for (DatasetCustomViewEntry datasetCustomViewEntry :
				datasetCustomViewEntries) {

			if (entityCache.getResult(
					DatasetCustomViewEntryImpl.class,
					datasetCustomViewEntry.getPrimaryKey()) == null) {

				cacheResult(datasetCustomViewEntry);
			}
		}
	}

	/**
	 * Clears the cache for all dataset custom view entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DatasetCustomViewEntryImpl.class);

		finderCache.clearCache(DatasetCustomViewEntryImpl.class);
	}

	/**
	 * Clears the cache for the dataset custom view entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DatasetCustomViewEntry datasetCustomViewEntry) {
		entityCache.removeResult(
			DatasetCustomViewEntryImpl.class, datasetCustomViewEntry);
	}

	@Override
	public void clearCache(
		List<DatasetCustomViewEntry> datasetCustomViewEntries) {

		for (DatasetCustomViewEntry datasetCustomViewEntry :
				datasetCustomViewEntries) {

			entityCache.removeResult(
				DatasetCustomViewEntryImpl.class, datasetCustomViewEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(DatasetCustomViewEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				DatasetCustomViewEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		DatasetCustomViewEntryModelImpl datasetCustomViewEntryModelImpl) {

		Object[] args = new Object[] {
			datasetCustomViewEntryModelImpl.getUserId(),
			datasetCustomViewEntryModelImpl.getDatasetDisplayId(),
			datasetCustomViewEntryModelImpl.getPlid(),
			datasetCustomViewEntryModelImpl.getPortletId()
		};

		finderCache.putResult(_finderPathCountByU_D_P_P, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByU_D_P_P, args, datasetCustomViewEntryModelImpl);
	}

	/**
	 * Creates a new dataset custom view entry with the primary key. Does not add the dataset custom view entry to the database.
	 *
	 * @param datasetCustomViewEntryId the primary key for the new dataset custom view entry
	 * @return the new dataset custom view entry
	 */
	@Override
	public DatasetCustomViewEntry create(long datasetCustomViewEntryId) {
		DatasetCustomViewEntry datasetCustomViewEntry =
			new DatasetCustomViewEntryImpl();

		datasetCustomViewEntry.setNew(true);
		datasetCustomViewEntry.setPrimaryKey(datasetCustomViewEntryId);

		String uuid = PortalUUIDUtil.generate();

		datasetCustomViewEntry.setUuid(uuid);

		datasetCustomViewEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return datasetCustomViewEntry;
	}

	/**
	 * Removes the dataset custom view entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param datasetCustomViewEntryId the primary key of the dataset custom view entry
	 * @return the dataset custom view entry that was removed
	 * @throws NoSuchEntryException if a dataset custom view entry with the primary key could not be found
	 */
	@Override
	public DatasetCustomViewEntry remove(long datasetCustomViewEntryId)
		throws NoSuchEntryException {

		return remove((Serializable)datasetCustomViewEntryId);
	}

	/**
	 * Removes the dataset custom view entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the dataset custom view entry
	 * @return the dataset custom view entry that was removed
	 * @throws NoSuchEntryException if a dataset custom view entry with the primary key could not be found
	 */
	@Override
	public DatasetCustomViewEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {

		Session session = null;

		try {
			session = openSession();

			DatasetCustomViewEntry datasetCustomViewEntry =
				(DatasetCustomViewEntry)session.get(
					DatasetCustomViewEntryImpl.class, primaryKey);

			if (datasetCustomViewEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(datasetCustomViewEntry);
		}
		catch (NoSuchEntryException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected DatasetCustomViewEntry removeImpl(
		DatasetCustomViewEntry datasetCustomViewEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(datasetCustomViewEntry)) {
				datasetCustomViewEntry = (DatasetCustomViewEntry)session.get(
					DatasetCustomViewEntryImpl.class,
					datasetCustomViewEntry.getPrimaryKeyObj());
			}

			if (datasetCustomViewEntry != null) {
				session.delete(datasetCustomViewEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (datasetCustomViewEntry != null) {
			clearCache(datasetCustomViewEntry);
		}

		return datasetCustomViewEntry;
	}

	@Override
	public DatasetCustomViewEntry updateImpl(
		DatasetCustomViewEntry datasetCustomViewEntry) {

		boolean isNew = datasetCustomViewEntry.isNew();

		if (!(datasetCustomViewEntry instanceof
				DatasetCustomViewEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(datasetCustomViewEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					datasetCustomViewEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in datasetCustomViewEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DatasetCustomViewEntry implementation " +
					datasetCustomViewEntry.getClass());
		}

		DatasetCustomViewEntryModelImpl datasetCustomViewEntryModelImpl =
			(DatasetCustomViewEntryModelImpl)datasetCustomViewEntry;

		if (Validator.isNull(datasetCustomViewEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			datasetCustomViewEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (datasetCustomViewEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				datasetCustomViewEntry.setCreateDate(now);
			}
			else {
				datasetCustomViewEntry.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!datasetCustomViewEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				datasetCustomViewEntry.setModifiedDate(now);
			}
			else {
				datasetCustomViewEntry.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(datasetCustomViewEntry);
			}
			else {
				datasetCustomViewEntry = (DatasetCustomViewEntry)session.merge(
					datasetCustomViewEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			DatasetCustomViewEntryImpl.class, datasetCustomViewEntryModelImpl,
			false, true);

		cacheUniqueFindersCache(datasetCustomViewEntryModelImpl);

		if (isNew) {
			datasetCustomViewEntry.setNew(false);
		}

		datasetCustomViewEntry.resetOriginalValues();

		return datasetCustomViewEntry;
	}

	/**
	 * Returns the dataset custom view entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the dataset custom view entry
	 * @return the dataset custom view entry
	 * @throws NoSuchEntryException if a dataset custom view entry with the primary key could not be found
	 */
	@Override
	public DatasetCustomViewEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {

		DatasetCustomViewEntry datasetCustomViewEntry = fetchByPrimaryKey(
			primaryKey);

		if (datasetCustomViewEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return datasetCustomViewEntry;
	}

	/**
	 * Returns the dataset custom view entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param datasetCustomViewEntryId the primary key of the dataset custom view entry
	 * @return the dataset custom view entry
	 * @throws NoSuchEntryException if a dataset custom view entry with the primary key could not be found
	 */
	@Override
	public DatasetCustomViewEntry findByPrimaryKey(
			long datasetCustomViewEntryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)datasetCustomViewEntryId);
	}

	/**
	 * Returns the dataset custom view entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param datasetCustomViewEntryId the primary key of the dataset custom view entry
	 * @return the dataset custom view entry, or <code>null</code> if a dataset custom view entry with the primary key could not be found
	 */
	@Override
	public DatasetCustomViewEntry fetchByPrimaryKey(
		long datasetCustomViewEntryId) {

		return fetchByPrimaryKey((Serializable)datasetCustomViewEntryId);
	}

	/**
	 * Returns all the dataset custom view entries.
	 *
	 * @return the dataset custom view entries
	 */
	@Override
	public List<DatasetCustomViewEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dataset custom view entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetCustomViewEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dataset custom view entries
	 * @param end the upper bound of the range of dataset custom view entries (not inclusive)
	 * @return the range of dataset custom view entries
	 */
	@Override
	public List<DatasetCustomViewEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the dataset custom view entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetCustomViewEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dataset custom view entries
	 * @param end the upper bound of the range of dataset custom view entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of dataset custom view entries
	 */
	@Override
	public List<DatasetCustomViewEntry> findAll(
		int start, int end,
		OrderByComparator<DatasetCustomViewEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the dataset custom view entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetCustomViewEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dataset custom view entries
	 * @param end the upper bound of the range of dataset custom view entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of dataset custom view entries
	 */
	@Override
	public List<DatasetCustomViewEntry> findAll(
		int start, int end,
		OrderByComparator<DatasetCustomViewEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<DatasetCustomViewEntry> list = null;

		if (useFinderCache) {
			list = (List<DatasetCustomViewEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_DATASETCUSTOMVIEWENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_DATASETCUSTOMVIEWENTRY;

				sql = sql.concat(DatasetCustomViewEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<DatasetCustomViewEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the dataset custom view entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DatasetCustomViewEntry datasetCustomViewEntry : findAll()) {
			remove(datasetCustomViewEntry);
		}
	}

	/**
	 * Returns the number of dataset custom view entries.
	 *
	 * @return the number of dataset custom view entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_DATASETCUSTOMVIEWENTRY);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "datasetCustomViewEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DATASETCUSTOMVIEWENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return DatasetCustomViewEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the dataset custom view entry persistence.
	 */
	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new DatasetCustomViewEntryModelArgumentsResolver(),
			new HashMapDictionary<>());

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_"}, true);

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			true);

		_finderPathCountByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			false);

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathCountByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, false);

		_finderPathFetchByU_D_P_P = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByU_D_P_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName(), String.class.getName()
			},
			new String[] {"userId", "datasetDisplayId", "plid", "portletId"},
			true);

		_finderPathCountByU_D_P_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_D_P_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName(), String.class.getName()
			},
			new String[] {"userId", "datasetDisplayId", "plid", "portletId"},
			false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(DatasetCustomViewEntryImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	@Override
	@Reference(
		target = DatasetCustomViewPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = DatasetCustomViewPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = DatasetCustomViewPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private BundleContext _bundleContext;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_DATASETCUSTOMVIEWENTRY =
		"SELECT datasetCustomViewEntry FROM DatasetCustomViewEntry datasetCustomViewEntry";

	private static final String _SQL_SELECT_DATASETCUSTOMVIEWENTRY_WHERE =
		"SELECT datasetCustomViewEntry FROM DatasetCustomViewEntry datasetCustomViewEntry WHERE ";

	private static final String _SQL_COUNT_DATASETCUSTOMVIEWENTRY =
		"SELECT COUNT(datasetCustomViewEntry) FROM DatasetCustomViewEntry datasetCustomViewEntry";

	private static final String _SQL_COUNT_DATASETCUSTOMVIEWENTRY_WHERE =
		"SELECT COUNT(datasetCustomViewEntry) FROM DatasetCustomViewEntry datasetCustomViewEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"datasetCustomViewEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DatasetCustomViewEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DatasetCustomViewEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DatasetCustomViewEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class DatasetCustomViewEntryModelArgumentsResolver
		implements ArgumentsResolver {

		@Override
		public Object[] getArguments(
			FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
			boolean original) {

			String[] columnNames = finderPath.getColumnNames();

			if ((columnNames == null) || (columnNames.length == 0)) {
				if (baseModel.isNew()) {
					return FINDER_ARGS_EMPTY;
				}

				return null;
			}

			DatasetCustomViewEntryModelImpl datasetCustomViewEntryModelImpl =
				(DatasetCustomViewEntryModelImpl)baseModel;

			long columnBitmask =
				datasetCustomViewEntryModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					datasetCustomViewEntryModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						datasetCustomViewEntryModelImpl.getColumnBitmask(
							columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					datasetCustomViewEntryModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return DatasetCustomViewEntryImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return DatasetCustomViewEntryTable.INSTANCE.getTableName();
		}

		private static Object[] _getValue(
			DatasetCustomViewEntryModelImpl datasetCustomViewEntryModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						datasetCustomViewEntryModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] =
						datasetCustomViewEntryModelImpl.getColumnValue(
							columnName);
				}
			}

			return arguments;
		}

		private static final Map<FinderPath, Long>
			_finderPathColumnBitmasksCache = new ConcurrentHashMap<>();

	}

}