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

package com.liferay.frontend.view.state.service.persistence.impl;

import com.liferay.frontend.view.state.exception.NoSuchFVSFrontendDataSetException;
import com.liferay.frontend.view.state.model.FVSFrontendDataSet;
import com.liferay.frontend.view.state.model.FVSFrontendDataSetTable;
import com.liferay.frontend.view.state.model.impl.FVSFrontendDataSetImpl;
import com.liferay.frontend.view.state.model.impl.FVSFrontendDataSetModelImpl;
import com.liferay.frontend.view.state.service.persistence.FVSFrontendDataSetPersistence;
import com.liferay.frontend.view.state.service.persistence.FVSFrontendDataSetUtil;
import com.liferay.frontend.view.state.service.persistence.impl.constants.FVSPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
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
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUID;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the fvs frontend data set service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	service = {FVSFrontendDataSetPersistence.class, BasePersistence.class}
)
public class FVSFrontendDataSetPersistenceImpl
	extends BasePersistenceImpl<FVSFrontendDataSet>
	implements FVSFrontendDataSetPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>FVSFrontendDataSetUtil</code> to access the fvs frontend data set persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		FVSFrontendDataSetImpl.class.getName();

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
	 * Returns all the fvs frontend data sets where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching fvs frontend data sets
	 */
	@Override
	public List<FVSFrontendDataSet> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fvs frontend data sets where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs frontend data sets
	 * @param end the upper bound of the range of fvs frontend data sets (not inclusive)
	 * @return the range of matching fvs frontend data sets
	 */
	@Override
	public List<FVSFrontendDataSet> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fvs frontend data sets where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs frontend data sets
	 * @param end the upper bound of the range of fvs frontend data sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fvs frontend data sets
	 */
	@Override
	public List<FVSFrontendDataSet> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FVSFrontendDataSet> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fvs frontend data sets where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs frontend data sets
	 * @param end the upper bound of the range of fvs frontend data sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fvs frontend data sets
	 */
	@Override
	public List<FVSFrontendDataSet> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FVSFrontendDataSet> orderByComparator,
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

		List<FVSFrontendDataSet> list = null;

		if (useFinderCache) {
			list = (List<FVSFrontendDataSet>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (FVSFrontendDataSet fvsFrontendDataSet : list) {
					if (!uuid.equals(fvsFrontendDataSet.getUuid())) {
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

			sb.append(_SQL_SELECT_FVSFRONTENDDATASET_WHERE);

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
				sb.append(FVSFrontendDataSetModelImpl.ORDER_BY_JPQL);
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

				list = (List<FVSFrontendDataSet>)QueryUtil.list(
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
	 * Returns the first fvs frontend data set in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set
	 * @throws NoSuchFVSFrontendDataSetException if a matching fvs frontend data set could not be found
	 */
	@Override
	public FVSFrontendDataSet findByUuid_First(
			String uuid,
			OrderByComparator<FVSFrontendDataSet> orderByComparator)
		throws NoSuchFVSFrontendDataSetException {

		FVSFrontendDataSet fvsFrontendDataSet = fetchByUuid_First(
			uuid, orderByComparator);

		if (fvsFrontendDataSet != null) {
			return fvsFrontendDataSet;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchFVSFrontendDataSetException(sb.toString());
	}

	/**
	 * Returns the first fvs frontend data set in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set, or <code>null</code> if a matching fvs frontend data set could not be found
	 */
	@Override
	public FVSFrontendDataSet fetchByUuid_First(
		String uuid, OrderByComparator<FVSFrontendDataSet> orderByComparator) {

		List<FVSFrontendDataSet> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fvs frontend data set in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set
	 * @throws NoSuchFVSFrontendDataSetException if a matching fvs frontend data set could not be found
	 */
	@Override
	public FVSFrontendDataSet findByUuid_Last(
			String uuid,
			OrderByComparator<FVSFrontendDataSet> orderByComparator)
		throws NoSuchFVSFrontendDataSetException {

		FVSFrontendDataSet fvsFrontendDataSet = fetchByUuid_Last(
			uuid, orderByComparator);

		if (fvsFrontendDataSet != null) {
			return fvsFrontendDataSet;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchFVSFrontendDataSetException(sb.toString());
	}

	/**
	 * Returns the last fvs frontend data set in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set, or <code>null</code> if a matching fvs frontend data set could not be found
	 */
	@Override
	public FVSFrontendDataSet fetchByUuid_Last(
		String uuid, OrderByComparator<FVSFrontendDataSet> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<FVSFrontendDataSet> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fvs frontend data sets before and after the current fvs frontend data set in the ordered set where uuid = &#63;.
	 *
	 * @param fvsFrontendDataSetId the primary key of the current fvs frontend data set
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fvs frontend data set
	 * @throws NoSuchFVSFrontendDataSetException if a fvs frontend data set with the primary key could not be found
	 */
	@Override
	public FVSFrontendDataSet[] findByUuid_PrevAndNext(
			long fvsFrontendDataSetId, String uuid,
			OrderByComparator<FVSFrontendDataSet> orderByComparator)
		throws NoSuchFVSFrontendDataSetException {

		uuid = Objects.toString(uuid, "");

		FVSFrontendDataSet fvsFrontendDataSet = findByPrimaryKey(
			fvsFrontendDataSetId);

		Session session = null;

		try {
			session = openSession();

			FVSFrontendDataSet[] array = new FVSFrontendDataSetImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, fvsFrontendDataSet, uuid, orderByComparator, true);

			array[1] = fvsFrontendDataSet;

			array[2] = getByUuid_PrevAndNext(
				session, fvsFrontendDataSet, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FVSFrontendDataSet getByUuid_PrevAndNext(
		Session session, FVSFrontendDataSet fvsFrontendDataSet, String uuid,
		OrderByComparator<FVSFrontendDataSet> orderByComparator,
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

		sb.append(_SQL_SELECT_FVSFRONTENDDATASET_WHERE);

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
			sb.append(FVSFrontendDataSetModelImpl.ORDER_BY_JPQL);
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
						fvsFrontendDataSet)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FVSFrontendDataSet> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fvs frontend data sets where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (FVSFrontendDataSet fvsFrontendDataSet :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(fvsFrontendDataSet);
		}
	}

	/**
	 * Returns the number of fvs frontend data sets where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching fvs frontend data sets
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FVSFRONTENDDATASET_WHERE);

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
		"fvsFrontendDataSet.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(fvsFrontendDataSet.uuid IS NULL OR fvsFrontendDataSet.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the fvs frontend data sets where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching fvs frontend data sets
	 */
	@Override
	public List<FVSFrontendDataSet> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fvs frontend data sets where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs frontend data sets
	 * @param end the upper bound of the range of fvs frontend data sets (not inclusive)
	 * @return the range of matching fvs frontend data sets
	 */
	@Override
	public List<FVSFrontendDataSet> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fvs frontend data sets where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs frontend data sets
	 * @param end the upper bound of the range of fvs frontend data sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fvs frontend data sets
	 */
	@Override
	public List<FVSFrontendDataSet> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FVSFrontendDataSet> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fvs frontend data sets where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs frontend data sets
	 * @param end the upper bound of the range of fvs frontend data sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fvs frontend data sets
	 */
	@Override
	public List<FVSFrontendDataSet> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FVSFrontendDataSet> orderByComparator,
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

		List<FVSFrontendDataSet> list = null;

		if (useFinderCache) {
			list = (List<FVSFrontendDataSet>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (FVSFrontendDataSet fvsFrontendDataSet : list) {
					if (!uuid.equals(fvsFrontendDataSet.getUuid()) ||
						(companyId != fvsFrontendDataSet.getCompanyId())) {

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

			sb.append(_SQL_SELECT_FVSFRONTENDDATASET_WHERE);

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
				sb.append(FVSFrontendDataSetModelImpl.ORDER_BY_JPQL);
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

				list = (List<FVSFrontendDataSet>)QueryUtil.list(
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
	 * Returns the first fvs frontend data set in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set
	 * @throws NoSuchFVSFrontendDataSetException if a matching fvs frontend data set could not be found
	 */
	@Override
	public FVSFrontendDataSet findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<FVSFrontendDataSet> orderByComparator)
		throws NoSuchFVSFrontendDataSetException {

		FVSFrontendDataSet fvsFrontendDataSet = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (fvsFrontendDataSet != null) {
			return fvsFrontendDataSet;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchFVSFrontendDataSetException(sb.toString());
	}

	/**
	 * Returns the first fvs frontend data set in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set, or <code>null</code> if a matching fvs frontend data set could not be found
	 */
	@Override
	public FVSFrontendDataSet fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<FVSFrontendDataSet> orderByComparator) {

		List<FVSFrontendDataSet> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fvs frontend data set in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set
	 * @throws NoSuchFVSFrontendDataSetException if a matching fvs frontend data set could not be found
	 */
	@Override
	public FVSFrontendDataSet findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<FVSFrontendDataSet> orderByComparator)
		throws NoSuchFVSFrontendDataSetException {

		FVSFrontendDataSet fvsFrontendDataSet = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (fvsFrontendDataSet != null) {
			return fvsFrontendDataSet;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchFVSFrontendDataSetException(sb.toString());
	}

	/**
	 * Returns the last fvs frontend data set in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set, or <code>null</code> if a matching fvs frontend data set could not be found
	 */
	@Override
	public FVSFrontendDataSet fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<FVSFrontendDataSet> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<FVSFrontendDataSet> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fvs frontend data sets before and after the current fvs frontend data set in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param fvsFrontendDataSetId the primary key of the current fvs frontend data set
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fvs frontend data set
	 * @throws NoSuchFVSFrontendDataSetException if a fvs frontend data set with the primary key could not be found
	 */
	@Override
	public FVSFrontendDataSet[] findByUuid_C_PrevAndNext(
			long fvsFrontendDataSetId, String uuid, long companyId,
			OrderByComparator<FVSFrontendDataSet> orderByComparator)
		throws NoSuchFVSFrontendDataSetException {

		uuid = Objects.toString(uuid, "");

		FVSFrontendDataSet fvsFrontendDataSet = findByPrimaryKey(
			fvsFrontendDataSetId);

		Session session = null;

		try {
			session = openSession();

			FVSFrontendDataSet[] array = new FVSFrontendDataSetImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, fvsFrontendDataSet, uuid, companyId, orderByComparator,
				true);

			array[1] = fvsFrontendDataSet;

			array[2] = getByUuid_C_PrevAndNext(
				session, fvsFrontendDataSet, uuid, companyId, orderByComparator,
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

	protected FVSFrontendDataSet getByUuid_C_PrevAndNext(
		Session session, FVSFrontendDataSet fvsFrontendDataSet, String uuid,
		long companyId, OrderByComparator<FVSFrontendDataSet> orderByComparator,
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

		sb.append(_SQL_SELECT_FVSFRONTENDDATASET_WHERE);

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
			sb.append(FVSFrontendDataSetModelImpl.ORDER_BY_JPQL);
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
						fvsFrontendDataSet)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FVSFrontendDataSet> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fvs frontend data sets where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (FVSFrontendDataSet fvsFrontendDataSet :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(fvsFrontendDataSet);
		}
	}

	/**
	 * Returns the number of fvs frontend data sets where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching fvs frontend data sets
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FVSFRONTENDDATASET_WHERE);

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
		"fvsFrontendDataSet.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(fvsFrontendDataSet.uuid IS NULL OR fvsFrontendDataSet.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"fvsFrontendDataSet.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByF_S;
	private FinderPath _finderPathWithoutPaginationFindByF_S;
	private FinderPath _finderPathCountByF_S;

	/**
	 * Returns all the fvs frontend data sets where fdsName = &#63; and system = &#63;.
	 *
	 * @param fdsName the fds name
	 * @param system the system
	 * @return the matching fvs frontend data sets
	 */
	@Override
	public List<FVSFrontendDataSet> findByF_S(String fdsName, boolean system) {
		return findByF_S(
			fdsName, system, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fvs frontend data sets where fdsName = &#63; and system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetModelImpl</code>.
	 * </p>
	 *
	 * @param fdsName the fds name
	 * @param system the system
	 * @param start the lower bound of the range of fvs frontend data sets
	 * @param end the upper bound of the range of fvs frontend data sets (not inclusive)
	 * @return the range of matching fvs frontend data sets
	 */
	@Override
	public List<FVSFrontendDataSet> findByF_S(
		String fdsName, boolean system, int start, int end) {

		return findByF_S(fdsName, system, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fvs frontend data sets where fdsName = &#63; and system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetModelImpl</code>.
	 * </p>
	 *
	 * @param fdsName the fds name
	 * @param system the system
	 * @param start the lower bound of the range of fvs frontend data sets
	 * @param end the upper bound of the range of fvs frontend data sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fvs frontend data sets
	 */
	@Override
	public List<FVSFrontendDataSet> findByF_S(
		String fdsName, boolean system, int start, int end,
		OrderByComparator<FVSFrontendDataSet> orderByComparator) {

		return findByF_S(fdsName, system, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fvs frontend data sets where fdsName = &#63; and system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetModelImpl</code>.
	 * </p>
	 *
	 * @param fdsName the fds name
	 * @param system the system
	 * @param start the lower bound of the range of fvs frontend data sets
	 * @param end the upper bound of the range of fvs frontend data sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fvs frontend data sets
	 */
	@Override
	public List<FVSFrontendDataSet> findByF_S(
		String fdsName, boolean system, int start, int end,
		OrderByComparator<FVSFrontendDataSet> orderByComparator,
		boolean useFinderCache) {

		fdsName = Objects.toString(fdsName, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByF_S;
				finderArgs = new Object[] {fdsName, system};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByF_S;
			finderArgs = new Object[] {
				fdsName, system, start, end, orderByComparator
			};
		}

		List<FVSFrontendDataSet> list = null;

		if (useFinderCache) {
			list = (List<FVSFrontendDataSet>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (FVSFrontendDataSet fvsFrontendDataSet : list) {
					if (!fdsName.equals(fvsFrontendDataSet.getFDSName()) ||
						(system != fvsFrontendDataSet.isSystem())) {

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

			sb.append(_SQL_SELECT_FVSFRONTENDDATASET_WHERE);

			boolean bindFDSName = false;

			if (fdsName.isEmpty()) {
				sb.append(_FINDER_COLUMN_F_S_FDSNAME_3);
			}
			else {
				bindFDSName = true;

				sb.append(_FINDER_COLUMN_F_S_FDSNAME_2);
			}

			sb.append(_FINDER_COLUMN_F_S_SYSTEM_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FVSFrontendDataSetModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindFDSName) {
					queryPos.add(fdsName);
				}

				queryPos.add(system);

				list = (List<FVSFrontendDataSet>)QueryUtil.list(
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
	 * Returns the first fvs frontend data set in the ordered set where fdsName = &#63; and system = &#63;.
	 *
	 * @param fdsName the fds name
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set
	 * @throws NoSuchFVSFrontendDataSetException if a matching fvs frontend data set could not be found
	 */
	@Override
	public FVSFrontendDataSet findByF_S_First(
			String fdsName, boolean system,
			OrderByComparator<FVSFrontendDataSet> orderByComparator)
		throws NoSuchFVSFrontendDataSetException {

		FVSFrontendDataSet fvsFrontendDataSet = fetchByF_S_First(
			fdsName, system, orderByComparator);

		if (fvsFrontendDataSet != null) {
			return fvsFrontendDataSet;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("fdsName=");
		sb.append(fdsName);

		sb.append(", system=");
		sb.append(system);

		sb.append("}");

		throw new NoSuchFVSFrontendDataSetException(sb.toString());
	}

	/**
	 * Returns the first fvs frontend data set in the ordered set where fdsName = &#63; and system = &#63;.
	 *
	 * @param fdsName the fds name
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set, or <code>null</code> if a matching fvs frontend data set could not be found
	 */
	@Override
	public FVSFrontendDataSet fetchByF_S_First(
		String fdsName, boolean system,
		OrderByComparator<FVSFrontendDataSet> orderByComparator) {

		List<FVSFrontendDataSet> list = findByF_S(
			fdsName, system, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fvs frontend data set in the ordered set where fdsName = &#63; and system = &#63;.
	 *
	 * @param fdsName the fds name
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set
	 * @throws NoSuchFVSFrontendDataSetException if a matching fvs frontend data set could not be found
	 */
	@Override
	public FVSFrontendDataSet findByF_S_Last(
			String fdsName, boolean system,
			OrderByComparator<FVSFrontendDataSet> orderByComparator)
		throws NoSuchFVSFrontendDataSetException {

		FVSFrontendDataSet fvsFrontendDataSet = fetchByF_S_Last(
			fdsName, system, orderByComparator);

		if (fvsFrontendDataSet != null) {
			return fvsFrontendDataSet;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("fdsName=");
		sb.append(fdsName);

		sb.append(", system=");
		sb.append(system);

		sb.append("}");

		throw new NoSuchFVSFrontendDataSetException(sb.toString());
	}

	/**
	 * Returns the last fvs frontend data set in the ordered set where fdsName = &#63; and system = &#63;.
	 *
	 * @param fdsName the fds name
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set, or <code>null</code> if a matching fvs frontend data set could not be found
	 */
	@Override
	public FVSFrontendDataSet fetchByF_S_Last(
		String fdsName, boolean system,
		OrderByComparator<FVSFrontendDataSet> orderByComparator) {

		int count = countByF_S(fdsName, system);

		if (count == 0) {
			return null;
		}

		List<FVSFrontendDataSet> list = findByF_S(
			fdsName, system, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fvs frontend data sets before and after the current fvs frontend data set in the ordered set where fdsName = &#63; and system = &#63;.
	 *
	 * @param fvsFrontendDataSetId the primary key of the current fvs frontend data set
	 * @param fdsName the fds name
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fvs frontend data set
	 * @throws NoSuchFVSFrontendDataSetException if a fvs frontend data set with the primary key could not be found
	 */
	@Override
	public FVSFrontendDataSet[] findByF_S_PrevAndNext(
			long fvsFrontendDataSetId, String fdsName, boolean system,
			OrderByComparator<FVSFrontendDataSet> orderByComparator)
		throws NoSuchFVSFrontendDataSetException {

		fdsName = Objects.toString(fdsName, "");

		FVSFrontendDataSet fvsFrontendDataSet = findByPrimaryKey(
			fvsFrontendDataSetId);

		Session session = null;

		try {
			session = openSession();

			FVSFrontendDataSet[] array = new FVSFrontendDataSetImpl[3];

			array[0] = getByF_S_PrevAndNext(
				session, fvsFrontendDataSet, fdsName, system, orderByComparator,
				true);

			array[1] = fvsFrontendDataSet;

			array[2] = getByF_S_PrevAndNext(
				session, fvsFrontendDataSet, fdsName, system, orderByComparator,
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

	protected FVSFrontendDataSet getByF_S_PrevAndNext(
		Session session, FVSFrontendDataSet fvsFrontendDataSet, String fdsName,
		boolean system, OrderByComparator<FVSFrontendDataSet> orderByComparator,
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

		sb.append(_SQL_SELECT_FVSFRONTENDDATASET_WHERE);

		boolean bindFDSName = false;

		if (fdsName.isEmpty()) {
			sb.append(_FINDER_COLUMN_F_S_FDSNAME_3);
		}
		else {
			bindFDSName = true;

			sb.append(_FINDER_COLUMN_F_S_FDSNAME_2);
		}

		sb.append(_FINDER_COLUMN_F_S_SYSTEM_2);

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
			sb.append(FVSFrontendDataSetModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindFDSName) {
			queryPos.add(fdsName);
		}

		queryPos.add(system);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fvsFrontendDataSet)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FVSFrontendDataSet> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fvs frontend data sets where fdsName = &#63; and system = &#63; from the database.
	 *
	 * @param fdsName the fds name
	 * @param system the system
	 */
	@Override
	public void removeByF_S(String fdsName, boolean system) {
		for (FVSFrontendDataSet fvsFrontendDataSet :
				findByF_S(
					fdsName, system, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(fvsFrontendDataSet);
		}
	}

	/**
	 * Returns the number of fvs frontend data sets where fdsName = &#63; and system = &#63;.
	 *
	 * @param fdsName the fds name
	 * @param system the system
	 * @return the number of matching fvs frontend data sets
	 */
	@Override
	public int countByF_S(String fdsName, boolean system) {
		fdsName = Objects.toString(fdsName, "");

		FinderPath finderPath = _finderPathCountByF_S;

		Object[] finderArgs = new Object[] {fdsName, system};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FVSFRONTENDDATASET_WHERE);

			boolean bindFDSName = false;

			if (fdsName.isEmpty()) {
				sb.append(_FINDER_COLUMN_F_S_FDSNAME_3);
			}
			else {
				bindFDSName = true;

				sb.append(_FINDER_COLUMN_F_S_FDSNAME_2);
			}

			sb.append(_FINDER_COLUMN_F_S_SYSTEM_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindFDSName) {
					queryPos.add(fdsName);
				}

				queryPos.add(system);

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

	private static final String _FINDER_COLUMN_F_S_FDSNAME_2 =
		"fvsFrontendDataSet.fdsName = ? AND ";

	private static final String _FINDER_COLUMN_F_S_FDSNAME_3 =
		"(fvsFrontendDataSet.fdsName IS NULL OR fvsFrontendDataSet.fdsName = '') AND ";

	private static final String _FINDER_COLUMN_F_S_SYSTEM_2 =
		"fvsFrontendDataSet.system = ?";

	private FinderPath _finderPathWithPaginationFindByU_F_P_P_S;
	private FinderPath _finderPathWithoutPaginationFindByU_F_P_P_S;
	private FinderPath _finderPathCountByU_F_P_P_S;

	/**
	 * Returns all the fvs frontend data sets where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63; and system = &#63;.
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param system the system
	 * @return the matching fvs frontend data sets
	 */
	@Override
	public List<FVSFrontendDataSet> findByU_F_P_P_S(
		long userId, String fdsName, long plid, String portletId,
		boolean system) {

		return findByU_F_P_P_S(
			userId, fdsName, plid, portletId, system, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fvs frontend data sets where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63; and system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param system the system
	 * @param start the lower bound of the range of fvs frontend data sets
	 * @param end the upper bound of the range of fvs frontend data sets (not inclusive)
	 * @return the range of matching fvs frontend data sets
	 */
	@Override
	public List<FVSFrontendDataSet> findByU_F_P_P_S(
		long userId, String fdsName, long plid, String portletId,
		boolean system, int start, int end) {

		return findByU_F_P_P_S(
			userId, fdsName, plid, portletId, system, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fvs frontend data sets where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63; and system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param system the system
	 * @param start the lower bound of the range of fvs frontend data sets
	 * @param end the upper bound of the range of fvs frontend data sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fvs frontend data sets
	 */
	@Override
	public List<FVSFrontendDataSet> findByU_F_P_P_S(
		long userId, String fdsName, long plid, String portletId,
		boolean system, int start, int end,
		OrderByComparator<FVSFrontendDataSet> orderByComparator) {

		return findByU_F_P_P_S(
			userId, fdsName, plid, portletId, system, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fvs frontend data sets where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63; and system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param system the system
	 * @param start the lower bound of the range of fvs frontend data sets
	 * @param end the upper bound of the range of fvs frontend data sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fvs frontend data sets
	 */
	@Override
	public List<FVSFrontendDataSet> findByU_F_P_P_S(
		long userId, String fdsName, long plid, String portletId,
		boolean system, int start, int end,
		OrderByComparator<FVSFrontendDataSet> orderByComparator,
		boolean useFinderCache) {

		fdsName = Objects.toString(fdsName, "");
		portletId = Objects.toString(portletId, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByU_F_P_P_S;
				finderArgs = new Object[] {
					userId, fdsName, plid, portletId, system
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByU_F_P_P_S;
			finderArgs = new Object[] {
				userId, fdsName, plid, portletId, system, start, end,
				orderByComparator
			};
		}

		List<FVSFrontendDataSet> list = null;

		if (useFinderCache) {
			list = (List<FVSFrontendDataSet>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (FVSFrontendDataSet fvsFrontendDataSet : list) {
					if ((userId != fvsFrontendDataSet.getUserId()) ||
						!fdsName.equals(fvsFrontendDataSet.getFDSName()) ||
						(plid != fvsFrontendDataSet.getPlid()) ||
						!portletId.equals(fvsFrontendDataSet.getPortletId()) ||
						(system != fvsFrontendDataSet.isSystem())) {

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
					7 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(7);
			}

			sb.append(_SQL_SELECT_FVSFRONTENDDATASET_WHERE);

			sb.append(_FINDER_COLUMN_U_F_P_P_S_USERID_2);

			boolean bindFDSName = false;

			if (fdsName.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_F_P_P_S_FDSNAME_3);
			}
			else {
				bindFDSName = true;

				sb.append(_FINDER_COLUMN_U_F_P_P_S_FDSNAME_2);
			}

			sb.append(_FINDER_COLUMN_U_F_P_P_S_PLID_2);

			boolean bindPortletId = false;

			if (portletId.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_F_P_P_S_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				sb.append(_FINDER_COLUMN_U_F_P_P_S_PORTLETID_2);
			}

			sb.append(_FINDER_COLUMN_U_F_P_P_S_SYSTEM_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FVSFrontendDataSetModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				if (bindFDSName) {
					queryPos.add(fdsName);
				}

				queryPos.add(plid);

				if (bindPortletId) {
					queryPos.add(portletId);
				}

				queryPos.add(system);

				list = (List<FVSFrontendDataSet>)QueryUtil.list(
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
	 * Returns the first fvs frontend data set in the ordered set where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63; and system = &#63;.
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set
	 * @throws NoSuchFVSFrontendDataSetException if a matching fvs frontend data set could not be found
	 */
	@Override
	public FVSFrontendDataSet findByU_F_P_P_S_First(
			long userId, String fdsName, long plid, String portletId,
			boolean system,
			OrderByComparator<FVSFrontendDataSet> orderByComparator)
		throws NoSuchFVSFrontendDataSetException {

		FVSFrontendDataSet fvsFrontendDataSet = fetchByU_F_P_P_S_First(
			userId, fdsName, plid, portletId, system, orderByComparator);

		if (fvsFrontendDataSet != null) {
			return fvsFrontendDataSet;
		}

		StringBundler sb = new StringBundler(12);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", fdsName=");
		sb.append(fdsName);

		sb.append(", plid=");
		sb.append(plid);

		sb.append(", portletId=");
		sb.append(portletId);

		sb.append(", system=");
		sb.append(system);

		sb.append("}");

		throw new NoSuchFVSFrontendDataSetException(sb.toString());
	}

	/**
	 * Returns the first fvs frontend data set in the ordered set where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63; and system = &#63;.
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set, or <code>null</code> if a matching fvs frontend data set could not be found
	 */
	@Override
	public FVSFrontendDataSet fetchByU_F_P_P_S_First(
		long userId, String fdsName, long plid, String portletId,
		boolean system,
		OrderByComparator<FVSFrontendDataSet> orderByComparator) {

		List<FVSFrontendDataSet> list = findByU_F_P_P_S(
			userId, fdsName, plid, portletId, system, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fvs frontend data set in the ordered set where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63; and system = &#63;.
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set
	 * @throws NoSuchFVSFrontendDataSetException if a matching fvs frontend data set could not be found
	 */
	@Override
	public FVSFrontendDataSet findByU_F_P_P_S_Last(
			long userId, String fdsName, long plid, String portletId,
			boolean system,
			OrderByComparator<FVSFrontendDataSet> orderByComparator)
		throws NoSuchFVSFrontendDataSetException {

		FVSFrontendDataSet fvsFrontendDataSet = fetchByU_F_P_P_S_Last(
			userId, fdsName, plid, portletId, system, orderByComparator);

		if (fvsFrontendDataSet != null) {
			return fvsFrontendDataSet;
		}

		StringBundler sb = new StringBundler(12);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", fdsName=");
		sb.append(fdsName);

		sb.append(", plid=");
		sb.append(plid);

		sb.append(", portletId=");
		sb.append(portletId);

		sb.append(", system=");
		sb.append(system);

		sb.append("}");

		throw new NoSuchFVSFrontendDataSetException(sb.toString());
	}

	/**
	 * Returns the last fvs frontend data set in the ordered set where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63; and system = &#63;.
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set, or <code>null</code> if a matching fvs frontend data set could not be found
	 */
	@Override
	public FVSFrontendDataSet fetchByU_F_P_P_S_Last(
		long userId, String fdsName, long plid, String portletId,
		boolean system,
		OrderByComparator<FVSFrontendDataSet> orderByComparator) {

		int count = countByU_F_P_P_S(userId, fdsName, plid, portletId, system);

		if (count == 0) {
			return null;
		}

		List<FVSFrontendDataSet> list = findByU_F_P_P_S(
			userId, fdsName, plid, portletId, system, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fvs frontend data sets before and after the current fvs frontend data set in the ordered set where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63; and system = &#63;.
	 *
	 * @param fvsFrontendDataSetId the primary key of the current fvs frontend data set
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fvs frontend data set
	 * @throws NoSuchFVSFrontendDataSetException if a fvs frontend data set with the primary key could not be found
	 */
	@Override
	public FVSFrontendDataSet[] findByU_F_P_P_S_PrevAndNext(
			long fvsFrontendDataSetId, long userId, String fdsName, long plid,
			String portletId, boolean system,
			OrderByComparator<FVSFrontendDataSet> orderByComparator)
		throws NoSuchFVSFrontendDataSetException {

		fdsName = Objects.toString(fdsName, "");
		portletId = Objects.toString(portletId, "");

		FVSFrontendDataSet fvsFrontendDataSet = findByPrimaryKey(
			fvsFrontendDataSetId);

		Session session = null;

		try {
			session = openSession();

			FVSFrontendDataSet[] array = new FVSFrontendDataSetImpl[3];

			array[0] = getByU_F_P_P_S_PrevAndNext(
				session, fvsFrontendDataSet, userId, fdsName, plid, portletId,
				system, orderByComparator, true);

			array[1] = fvsFrontendDataSet;

			array[2] = getByU_F_P_P_S_PrevAndNext(
				session, fvsFrontendDataSet, userId, fdsName, plid, portletId,
				system, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FVSFrontendDataSet getByU_F_P_P_S_PrevAndNext(
		Session session, FVSFrontendDataSet fvsFrontendDataSet, long userId,
		String fdsName, long plid, String portletId, boolean system,
		OrderByComparator<FVSFrontendDataSet> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				8 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(7);
		}

		sb.append(_SQL_SELECT_FVSFRONTENDDATASET_WHERE);

		sb.append(_FINDER_COLUMN_U_F_P_P_S_USERID_2);

		boolean bindFDSName = false;

		if (fdsName.isEmpty()) {
			sb.append(_FINDER_COLUMN_U_F_P_P_S_FDSNAME_3);
		}
		else {
			bindFDSName = true;

			sb.append(_FINDER_COLUMN_U_F_P_P_S_FDSNAME_2);
		}

		sb.append(_FINDER_COLUMN_U_F_P_P_S_PLID_2);

		boolean bindPortletId = false;

		if (portletId.isEmpty()) {
			sb.append(_FINDER_COLUMN_U_F_P_P_S_PORTLETID_3);
		}
		else {
			bindPortletId = true;

			sb.append(_FINDER_COLUMN_U_F_P_P_S_PORTLETID_2);
		}

		sb.append(_FINDER_COLUMN_U_F_P_P_S_SYSTEM_2);

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
			sb.append(FVSFrontendDataSetModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId);

		if (bindFDSName) {
			queryPos.add(fdsName);
		}

		queryPos.add(plid);

		if (bindPortletId) {
			queryPos.add(portletId);
		}

		queryPos.add(system);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fvsFrontendDataSet)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FVSFrontendDataSet> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fvs frontend data sets where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63; and system = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param system the system
	 */
	@Override
	public void removeByU_F_P_P_S(
		long userId, String fdsName, long plid, String portletId,
		boolean system) {

		for (FVSFrontendDataSet fvsFrontendDataSet :
				findByU_F_P_P_S(
					userId, fdsName, plid, portletId, system, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(fvsFrontendDataSet);
		}
	}

	/**
	 * Returns the number of fvs frontend data sets where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63; and system = &#63;.
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param system the system
	 * @return the number of matching fvs frontend data sets
	 */
	@Override
	public int countByU_F_P_P_S(
		long userId, String fdsName, long plid, String portletId,
		boolean system) {

		fdsName = Objects.toString(fdsName, "");
		portletId = Objects.toString(portletId, "");

		FinderPath finderPath = _finderPathCountByU_F_P_P_S;

		Object[] finderArgs = new Object[] {
			userId, fdsName, plid, portletId, system
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_SQL_COUNT_FVSFRONTENDDATASET_WHERE);

			sb.append(_FINDER_COLUMN_U_F_P_P_S_USERID_2);

			boolean bindFDSName = false;

			if (fdsName.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_F_P_P_S_FDSNAME_3);
			}
			else {
				bindFDSName = true;

				sb.append(_FINDER_COLUMN_U_F_P_P_S_FDSNAME_2);
			}

			sb.append(_FINDER_COLUMN_U_F_P_P_S_PLID_2);

			boolean bindPortletId = false;

			if (portletId.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_F_P_P_S_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				sb.append(_FINDER_COLUMN_U_F_P_P_S_PORTLETID_2);
			}

			sb.append(_FINDER_COLUMN_U_F_P_P_S_SYSTEM_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				if (bindFDSName) {
					queryPos.add(fdsName);
				}

				queryPos.add(plid);

				if (bindPortletId) {
					queryPos.add(portletId);
				}

				queryPos.add(system);

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

	private static final String _FINDER_COLUMN_U_F_P_P_S_USERID_2 =
		"fvsFrontendDataSet.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_F_P_P_S_FDSNAME_2 =
		"fvsFrontendDataSet.fdsName = ? AND ";

	private static final String _FINDER_COLUMN_U_F_P_P_S_FDSNAME_3 =
		"(fvsFrontendDataSet.fdsName IS NULL OR fvsFrontendDataSet.fdsName = '') AND ";

	private static final String _FINDER_COLUMN_U_F_P_P_S_PLID_2 =
		"fvsFrontendDataSet.plid = ? AND ";

	private static final String _FINDER_COLUMN_U_F_P_P_S_PORTLETID_2 =
		"fvsFrontendDataSet.portletId = ? AND ";

	private static final String _FINDER_COLUMN_U_F_P_P_S_PORTLETID_3 =
		"(fvsFrontendDataSet.portletId IS NULL OR fvsFrontendDataSet.portletId = '') AND ";

	private static final String _FINDER_COLUMN_U_F_P_P_S_SYSTEM_2 =
		"fvsFrontendDataSet.system = ?";

	public FVSFrontendDataSetPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("system", "system_");

		setDBColumnNames(dbColumnNames);

		setModelClass(FVSFrontendDataSet.class);

		setModelImplClass(FVSFrontendDataSetImpl.class);
		setModelPKClass(long.class);

		setTable(FVSFrontendDataSetTable.INSTANCE);
	}

	/**
	 * Caches the fvs frontend data set in the entity cache if it is enabled.
	 *
	 * @param fvsFrontendDataSet the fvs frontend data set
	 */
	@Override
	public void cacheResult(FVSFrontendDataSet fvsFrontendDataSet) {
		entityCache.putResult(
			FVSFrontendDataSetImpl.class, fvsFrontendDataSet.getPrimaryKey(),
			fvsFrontendDataSet);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the fvs frontend data sets in the entity cache if it is enabled.
	 *
	 * @param fvsFrontendDataSets the fvs frontend data sets
	 */
	@Override
	public void cacheResult(List<FVSFrontendDataSet> fvsFrontendDataSets) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (fvsFrontendDataSets.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (FVSFrontendDataSet fvsFrontendDataSet : fvsFrontendDataSets) {
			if (entityCache.getResult(
					FVSFrontendDataSetImpl.class,
					fvsFrontendDataSet.getPrimaryKey()) == null) {

				cacheResult(fvsFrontendDataSet);
			}
		}
	}

	/**
	 * Clears the cache for all fvs frontend data sets.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FVSFrontendDataSetImpl.class);

		finderCache.clearCache(FVSFrontendDataSetImpl.class);
	}

	/**
	 * Clears the cache for the fvs frontend data set.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(FVSFrontendDataSet fvsFrontendDataSet) {
		entityCache.removeResult(
			FVSFrontendDataSetImpl.class, fvsFrontendDataSet);
	}

	@Override
	public void clearCache(List<FVSFrontendDataSet> fvsFrontendDataSets) {
		for (FVSFrontendDataSet fvsFrontendDataSet : fvsFrontendDataSets) {
			entityCache.removeResult(
				FVSFrontendDataSetImpl.class, fvsFrontendDataSet);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FVSFrontendDataSetImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(FVSFrontendDataSetImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new fvs frontend data set with the primary key. Does not add the fvs frontend data set to the database.
	 *
	 * @param fvsFrontendDataSetId the primary key for the new fvs frontend data set
	 * @return the new fvs frontend data set
	 */
	@Override
	public FVSFrontendDataSet create(long fvsFrontendDataSetId) {
		FVSFrontendDataSet fvsFrontendDataSet = new FVSFrontendDataSetImpl();

		fvsFrontendDataSet.setNew(true);
		fvsFrontendDataSet.setPrimaryKey(fvsFrontendDataSetId);

		String uuid = _portalUUID.generate();

		fvsFrontendDataSet.setUuid(uuid);

		fvsFrontendDataSet.setCompanyId(CompanyThreadLocal.getCompanyId());

		return fvsFrontendDataSet;
	}

	/**
	 * Removes the fvs frontend data set with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fvsFrontendDataSetId the primary key of the fvs frontend data set
	 * @return the fvs frontend data set that was removed
	 * @throws NoSuchFVSFrontendDataSetException if a fvs frontend data set with the primary key could not be found
	 */
	@Override
	public FVSFrontendDataSet remove(long fvsFrontendDataSetId)
		throws NoSuchFVSFrontendDataSetException {

		return remove((Serializable)fvsFrontendDataSetId);
	}

	/**
	 * Removes the fvs frontend data set with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the fvs frontend data set
	 * @return the fvs frontend data set that was removed
	 * @throws NoSuchFVSFrontendDataSetException if a fvs frontend data set with the primary key could not be found
	 */
	@Override
	public FVSFrontendDataSet remove(Serializable primaryKey)
		throws NoSuchFVSFrontendDataSetException {

		Session session = null;

		try {
			session = openSession();

			FVSFrontendDataSet fvsFrontendDataSet =
				(FVSFrontendDataSet)session.get(
					FVSFrontendDataSetImpl.class, primaryKey);

			if (fvsFrontendDataSet == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFVSFrontendDataSetException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(fvsFrontendDataSet);
		}
		catch (NoSuchFVSFrontendDataSetException noSuchEntityException) {
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
	protected FVSFrontendDataSet removeImpl(
		FVSFrontendDataSet fvsFrontendDataSet) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(fvsFrontendDataSet)) {
				fvsFrontendDataSet = (FVSFrontendDataSet)session.get(
					FVSFrontendDataSetImpl.class,
					fvsFrontendDataSet.getPrimaryKeyObj());
			}

			if (fvsFrontendDataSet != null) {
				session.delete(fvsFrontendDataSet);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (fvsFrontendDataSet != null) {
			clearCache(fvsFrontendDataSet);
		}

		return fvsFrontendDataSet;
	}

	@Override
	public FVSFrontendDataSet updateImpl(
		FVSFrontendDataSet fvsFrontendDataSet) {

		boolean isNew = fvsFrontendDataSet.isNew();

		if (!(fvsFrontendDataSet instanceof FVSFrontendDataSetModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(fvsFrontendDataSet.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					fvsFrontendDataSet);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in fvsFrontendDataSet proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom FVSFrontendDataSet implementation " +
					fvsFrontendDataSet.getClass());
		}

		FVSFrontendDataSetModelImpl fvsFrontendDataSetModelImpl =
			(FVSFrontendDataSetModelImpl)fvsFrontendDataSet;

		if (Validator.isNull(fvsFrontendDataSet.getUuid())) {
			String uuid = _portalUUID.generate();

			fvsFrontendDataSet.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (fvsFrontendDataSet.getCreateDate() == null)) {
			if (serviceContext == null) {
				fvsFrontendDataSet.setCreateDate(date);
			}
			else {
				fvsFrontendDataSet.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!fvsFrontendDataSetModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				fvsFrontendDataSet.setModifiedDate(date);
			}
			else {
				fvsFrontendDataSet.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(fvsFrontendDataSet);
			}
			else {
				fvsFrontendDataSet = (FVSFrontendDataSet)session.merge(
					fvsFrontendDataSet);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			FVSFrontendDataSetImpl.class, fvsFrontendDataSetModelImpl, false,
			true);

		if (isNew) {
			fvsFrontendDataSet.setNew(false);
		}

		fvsFrontendDataSet.resetOriginalValues();

		return fvsFrontendDataSet;
	}

	/**
	 * Returns the fvs frontend data set with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the fvs frontend data set
	 * @return the fvs frontend data set
	 * @throws NoSuchFVSFrontendDataSetException if a fvs frontend data set with the primary key could not be found
	 */
	@Override
	public FVSFrontendDataSet findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFVSFrontendDataSetException {

		FVSFrontendDataSet fvsFrontendDataSet = fetchByPrimaryKey(primaryKey);

		if (fvsFrontendDataSet == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFVSFrontendDataSetException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return fvsFrontendDataSet;
	}

	/**
	 * Returns the fvs frontend data set with the primary key or throws a <code>NoSuchFVSFrontendDataSetException</code> if it could not be found.
	 *
	 * @param fvsFrontendDataSetId the primary key of the fvs frontend data set
	 * @return the fvs frontend data set
	 * @throws NoSuchFVSFrontendDataSetException if a fvs frontend data set with the primary key could not be found
	 */
	@Override
	public FVSFrontendDataSet findByPrimaryKey(long fvsFrontendDataSetId)
		throws NoSuchFVSFrontendDataSetException {

		return findByPrimaryKey((Serializable)fvsFrontendDataSetId);
	}

	/**
	 * Returns the fvs frontend data set with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fvsFrontendDataSetId the primary key of the fvs frontend data set
	 * @return the fvs frontend data set, or <code>null</code> if a fvs frontend data set with the primary key could not be found
	 */
	@Override
	public FVSFrontendDataSet fetchByPrimaryKey(long fvsFrontendDataSetId) {
		return fetchByPrimaryKey((Serializable)fvsFrontendDataSetId);
	}

	/**
	 * Returns all the fvs frontend data sets.
	 *
	 * @return the fvs frontend data sets
	 */
	@Override
	public List<FVSFrontendDataSet> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fvs frontend data sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs frontend data sets
	 * @param end the upper bound of the range of fvs frontend data sets (not inclusive)
	 * @return the range of fvs frontend data sets
	 */
	@Override
	public List<FVSFrontendDataSet> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the fvs frontend data sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs frontend data sets
	 * @param end the upper bound of the range of fvs frontend data sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of fvs frontend data sets
	 */
	@Override
	public List<FVSFrontendDataSet> findAll(
		int start, int end,
		OrderByComparator<FVSFrontendDataSet> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fvs frontend data sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs frontend data sets
	 * @param end the upper bound of the range of fvs frontend data sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of fvs frontend data sets
	 */
	@Override
	public List<FVSFrontendDataSet> findAll(
		int start, int end,
		OrderByComparator<FVSFrontendDataSet> orderByComparator,
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

		List<FVSFrontendDataSet> list = null;

		if (useFinderCache) {
			list = (List<FVSFrontendDataSet>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_FVSFRONTENDDATASET);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_FVSFRONTENDDATASET;

				sql = sql.concat(FVSFrontendDataSetModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<FVSFrontendDataSet>)QueryUtil.list(
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
	 * Removes all the fvs frontend data sets from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FVSFrontendDataSet fvsFrontendDataSet : findAll()) {
			remove(fvsFrontendDataSet);
		}
	}

	/**
	 * Returns the number of fvs frontend data sets.
	 *
	 * @return the number of fvs frontend data sets
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
					_SQL_COUNT_FVSFRONTENDDATASET);

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
		return "fvsFrontendDataSetId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_FVSFRONTENDDATASET;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return FVSFrontendDataSetModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the fvs frontend data set persistence.
	 */
	@Activate
	public void activate() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

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

		_finderPathWithPaginationFindByF_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByF_S",
			new String[] {
				String.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"fdsName", "system_"}, true);

		_finderPathWithoutPaginationFindByF_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByF_S",
			new String[] {String.class.getName(), Boolean.class.getName()},
			new String[] {"fdsName", "system_"}, true);

		_finderPathCountByF_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByF_S",
			new String[] {String.class.getName(), Boolean.class.getName()},
			new String[] {"fdsName", "system_"}, false);

		_finderPathWithPaginationFindByU_F_P_P_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_F_P_P_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"userId", "fdsName", "plid", "portletId", "system_"},
			true);

		_finderPathWithoutPaginationFindByU_F_P_P_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_F_P_P_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"userId", "fdsName", "plid", "portletId", "system_"},
			true);

		_finderPathCountByU_F_P_P_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_F_P_P_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"userId", "fdsName", "plid", "portletId", "system_"},
			false);

		_setFVSFrontendDataSetUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setFVSFrontendDataSetUtilPersistence(null);

		entityCache.removeCache(FVSFrontendDataSetImpl.class.getName());
	}

	private void _setFVSFrontendDataSetUtilPersistence(
		FVSFrontendDataSetPersistence fvsFrontendDataSetPersistence) {

		try {
			Field field = FVSFrontendDataSetUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, fvsFrontendDataSetPersistence);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@Override
	@Reference(
		target = FVSPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = FVSPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = FVSPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_FVSFRONTENDDATASET =
		"SELECT fvsFrontendDataSet FROM FVSFrontendDataSet fvsFrontendDataSet";

	private static final String _SQL_SELECT_FVSFRONTENDDATASET_WHERE =
		"SELECT fvsFrontendDataSet FROM FVSFrontendDataSet fvsFrontendDataSet WHERE ";

	private static final String _SQL_COUNT_FVSFRONTENDDATASET =
		"SELECT COUNT(fvsFrontendDataSet) FROM FVSFrontendDataSet fvsFrontendDataSet";

	private static final String _SQL_COUNT_FVSFRONTENDDATASET_WHERE =
		"SELECT COUNT(fvsFrontendDataSet) FROM FVSFrontendDataSet fvsFrontendDataSet WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "fvsFrontendDataSet.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No FVSFrontendDataSet exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No FVSFrontendDataSet exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		FVSFrontendDataSetPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "system"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private PortalUUID _portalUUID;

	@Reference
	private FVSFrontendDataSetModelArgumentsResolver
		_fvsFrontendDataSetModelArgumentsResolver;

}