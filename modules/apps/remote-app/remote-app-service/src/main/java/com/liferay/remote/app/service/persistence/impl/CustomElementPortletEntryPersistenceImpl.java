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

package com.liferay.remote.app.service.persistence.impl;

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
import com.liferay.remote.app.exception.NoSuchCustomElementPortletEntryException;
import com.liferay.remote.app.model.CustomElementPortletEntry;
import com.liferay.remote.app.model.CustomElementPortletEntryTable;
import com.liferay.remote.app.model.impl.CustomElementPortletEntryImpl;
import com.liferay.remote.app.model.impl.CustomElementPortletEntryModelImpl;
import com.liferay.remote.app.service.persistence.CustomElementPortletEntryPersistence;
import com.liferay.remote.app.service.persistence.impl.constants.RemoteAppPersistenceConstants;

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
 * The persistence implementation for the custom element portlet entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	service = {
		CustomElementPortletEntryPersistence.class, BasePersistence.class
	}
)
public class CustomElementPortletEntryPersistenceImpl
	extends BasePersistenceImpl<CustomElementPortletEntry>
	implements CustomElementPortletEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CustomElementPortletEntryUtil</code> to access the custom element portlet entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CustomElementPortletEntryImpl.class.getName();

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
	 * Returns all the custom element portlet entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching custom element portlet entries
	 */
	@Override
	public List<CustomElementPortletEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the custom element portlet entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementPortletEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of custom element portlet entries
	 * @param end the upper bound of the range of custom element portlet entries (not inclusive)
	 * @return the range of matching custom element portlet entries
	 */
	@Override
	public List<CustomElementPortletEntry> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the custom element portlet entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementPortletEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of custom element portlet entries
	 * @param end the upper bound of the range of custom element portlet entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching custom element portlet entries
	 */
	@Override
	public List<CustomElementPortletEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CustomElementPortletEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the custom element portlet entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementPortletEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of custom element portlet entries
	 * @param end the upper bound of the range of custom element portlet entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching custom element portlet entries
	 */
	@Override
	public List<CustomElementPortletEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CustomElementPortletEntry> orderByComparator,
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

		List<CustomElementPortletEntry> list = null;

		if (useFinderCache) {
			list = (List<CustomElementPortletEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CustomElementPortletEntry customElementPortletEntry :
						list) {

					if (!uuid.equals(customElementPortletEntry.getUuid())) {
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

			sb.append(_SQL_SELECT_CUSTOMELEMENTPORTLETENTRY_WHERE);

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
				sb.append(CustomElementPortletEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<CustomElementPortletEntry>)QueryUtil.list(
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
	 * Returns the first custom element portlet entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom element portlet entry
	 * @throws NoSuchCustomElementPortletEntryException if a matching custom element portlet entry could not be found
	 */
	@Override
	public CustomElementPortletEntry findByUuid_First(
			String uuid,
			OrderByComparator<CustomElementPortletEntry> orderByComparator)
		throws NoSuchCustomElementPortletEntryException {

		CustomElementPortletEntry customElementPortletEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (customElementPortletEntry != null) {
			return customElementPortletEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCustomElementPortletEntryException(sb.toString());
	}

	/**
	 * Returns the first custom element portlet entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom element portlet entry, or <code>null</code> if a matching custom element portlet entry could not be found
	 */
	@Override
	public CustomElementPortletEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<CustomElementPortletEntry> orderByComparator) {

		List<CustomElementPortletEntry> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last custom element portlet entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching custom element portlet entry
	 * @throws NoSuchCustomElementPortletEntryException if a matching custom element portlet entry could not be found
	 */
	@Override
	public CustomElementPortletEntry findByUuid_Last(
			String uuid,
			OrderByComparator<CustomElementPortletEntry> orderByComparator)
		throws NoSuchCustomElementPortletEntryException {

		CustomElementPortletEntry customElementPortletEntry = fetchByUuid_Last(
			uuid, orderByComparator);

		if (customElementPortletEntry != null) {
			return customElementPortletEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCustomElementPortletEntryException(sb.toString());
	}

	/**
	 * Returns the last custom element portlet entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching custom element portlet entry, or <code>null</code> if a matching custom element portlet entry could not be found
	 */
	@Override
	public CustomElementPortletEntry fetchByUuid_Last(
		String uuid,
		OrderByComparator<CustomElementPortletEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CustomElementPortletEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the custom element portlet entries before and after the current custom element portlet entry in the ordered set where uuid = &#63;.
	 *
	 * @param customElementPortletEntryId the primary key of the current custom element portlet entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next custom element portlet entry
	 * @throws NoSuchCustomElementPortletEntryException if a custom element portlet entry with the primary key could not be found
	 */
	@Override
	public CustomElementPortletEntry[] findByUuid_PrevAndNext(
			long customElementPortletEntryId, String uuid,
			OrderByComparator<CustomElementPortletEntry> orderByComparator)
		throws NoSuchCustomElementPortletEntryException {

		uuid = Objects.toString(uuid, "");

		CustomElementPortletEntry customElementPortletEntry = findByPrimaryKey(
			customElementPortletEntryId);

		Session session = null;

		try {
			session = openSession();

			CustomElementPortletEntry[] array =
				new CustomElementPortletEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, customElementPortletEntry, uuid, orderByComparator,
				true);

			array[1] = customElementPortletEntry;

			array[2] = getByUuid_PrevAndNext(
				session, customElementPortletEntry, uuid, orderByComparator,
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

	protected CustomElementPortletEntry getByUuid_PrevAndNext(
		Session session, CustomElementPortletEntry customElementPortletEntry,
		String uuid,
		OrderByComparator<CustomElementPortletEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_CUSTOMELEMENTPORTLETENTRY_WHERE);

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
			sb.append(CustomElementPortletEntryModelImpl.ORDER_BY_JPQL);
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
						customElementPortletEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CustomElementPortletEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the custom element portlet entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CustomElementPortletEntry customElementPortletEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(customElementPortletEntry);
		}
	}

	/**
	 * Returns the number of custom element portlet entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching custom element portlet entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CUSTOMELEMENTPORTLETENTRY_WHERE);

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
		"customElementPortletEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(customElementPortletEntry.uuid IS NULL OR customElementPortletEntry.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the custom element portlet entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching custom element portlet entries
	 */
	@Override
	public List<CustomElementPortletEntry> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the custom element portlet entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementPortletEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of custom element portlet entries
	 * @param end the upper bound of the range of custom element portlet entries (not inclusive)
	 * @return the range of matching custom element portlet entries
	 */
	@Override
	public List<CustomElementPortletEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the custom element portlet entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementPortletEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of custom element portlet entries
	 * @param end the upper bound of the range of custom element portlet entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching custom element portlet entries
	 */
	@Override
	public List<CustomElementPortletEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CustomElementPortletEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the custom element portlet entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementPortletEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of custom element portlet entries
	 * @param end the upper bound of the range of custom element portlet entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching custom element portlet entries
	 */
	@Override
	public List<CustomElementPortletEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CustomElementPortletEntry> orderByComparator,
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

		List<CustomElementPortletEntry> list = null;

		if (useFinderCache) {
			list = (List<CustomElementPortletEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CustomElementPortletEntry customElementPortletEntry :
						list) {

					if (!uuid.equals(customElementPortletEntry.getUuid()) ||
						(companyId !=
							customElementPortletEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_CUSTOMELEMENTPORTLETENTRY_WHERE);

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
				sb.append(CustomElementPortletEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<CustomElementPortletEntry>)QueryUtil.list(
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
	 * Returns the first custom element portlet entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom element portlet entry
	 * @throws NoSuchCustomElementPortletEntryException if a matching custom element portlet entry could not be found
	 */
	@Override
	public CustomElementPortletEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CustomElementPortletEntry> orderByComparator)
		throws NoSuchCustomElementPortletEntryException {

		CustomElementPortletEntry customElementPortletEntry =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (customElementPortletEntry != null) {
			return customElementPortletEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCustomElementPortletEntryException(sb.toString());
	}

	/**
	 * Returns the first custom element portlet entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom element portlet entry, or <code>null</code> if a matching custom element portlet entry could not be found
	 */
	@Override
	public CustomElementPortletEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CustomElementPortletEntry> orderByComparator) {

		List<CustomElementPortletEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last custom element portlet entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching custom element portlet entry
	 * @throws NoSuchCustomElementPortletEntryException if a matching custom element portlet entry could not be found
	 */
	@Override
	public CustomElementPortletEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CustomElementPortletEntry> orderByComparator)
		throws NoSuchCustomElementPortletEntryException {

		CustomElementPortletEntry customElementPortletEntry =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (customElementPortletEntry != null) {
			return customElementPortletEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCustomElementPortletEntryException(sb.toString());
	}

	/**
	 * Returns the last custom element portlet entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching custom element portlet entry, or <code>null</code> if a matching custom element portlet entry could not be found
	 */
	@Override
	public CustomElementPortletEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CustomElementPortletEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CustomElementPortletEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the custom element portlet entries before and after the current custom element portlet entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param customElementPortletEntryId the primary key of the current custom element portlet entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next custom element portlet entry
	 * @throws NoSuchCustomElementPortletEntryException if a custom element portlet entry with the primary key could not be found
	 */
	@Override
	public CustomElementPortletEntry[] findByUuid_C_PrevAndNext(
			long customElementPortletEntryId, String uuid, long companyId,
			OrderByComparator<CustomElementPortletEntry> orderByComparator)
		throws NoSuchCustomElementPortletEntryException {

		uuid = Objects.toString(uuid, "");

		CustomElementPortletEntry customElementPortletEntry = findByPrimaryKey(
			customElementPortletEntryId);

		Session session = null;

		try {
			session = openSession();

			CustomElementPortletEntry[] array =
				new CustomElementPortletEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, customElementPortletEntry, uuid, companyId,
				orderByComparator, true);

			array[1] = customElementPortletEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, customElementPortletEntry, uuid, companyId,
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

	protected CustomElementPortletEntry getByUuid_C_PrevAndNext(
		Session session, CustomElementPortletEntry customElementPortletEntry,
		String uuid, long companyId,
		OrderByComparator<CustomElementPortletEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_CUSTOMELEMENTPORTLETENTRY_WHERE);

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
			sb.append(CustomElementPortletEntryModelImpl.ORDER_BY_JPQL);
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
						customElementPortletEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CustomElementPortletEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the custom element portlet entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CustomElementPortletEntry customElementPortletEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(customElementPortletEntry);
		}
	}

	/**
	 * Returns the number of custom element portlet entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching custom element portlet entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CUSTOMELEMENTPORTLETENTRY_WHERE);

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
		"customElementPortletEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(customElementPortletEntry.uuid IS NULL OR customElementPortletEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"customElementPortletEntry.companyId = ?";

	public CustomElementPortletEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CustomElementPortletEntry.class);

		setModelImplClass(CustomElementPortletEntryImpl.class);
		setModelPKClass(long.class);

		setTable(CustomElementPortletEntryTable.INSTANCE);
	}

	/**
	 * Caches the custom element portlet entry in the entity cache if it is enabled.
	 *
	 * @param customElementPortletEntry the custom element portlet entry
	 */
	@Override
	public void cacheResult(
		CustomElementPortletEntry customElementPortletEntry) {

		entityCache.putResult(
			CustomElementPortletEntryImpl.class,
			customElementPortletEntry.getPrimaryKey(),
			customElementPortletEntry);
	}

	/**
	 * Caches the custom element portlet entries in the entity cache if it is enabled.
	 *
	 * @param customElementPortletEntries the custom element portlet entries
	 */
	@Override
	public void cacheResult(
		List<CustomElementPortletEntry> customElementPortletEntries) {

		for (CustomElementPortletEntry customElementPortletEntry :
				customElementPortletEntries) {

			if (entityCache.getResult(
					CustomElementPortletEntryImpl.class,
					customElementPortletEntry.getPrimaryKey()) == null) {

				cacheResult(customElementPortletEntry);
			}
		}
	}

	/**
	 * Clears the cache for all custom element portlet entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CustomElementPortletEntryImpl.class);

		finderCache.clearCache(CustomElementPortletEntryImpl.class);
	}

	/**
	 * Clears the cache for the custom element portlet entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CustomElementPortletEntry customElementPortletEntry) {

		entityCache.removeResult(
			CustomElementPortletEntryImpl.class, customElementPortletEntry);
	}

	@Override
	public void clearCache(
		List<CustomElementPortletEntry> customElementPortletEntries) {

		for (CustomElementPortletEntry customElementPortletEntry :
				customElementPortletEntries) {

			entityCache.removeResult(
				CustomElementPortletEntryImpl.class, customElementPortletEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CustomElementPortletEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CustomElementPortletEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new custom element portlet entry with the primary key. Does not add the custom element portlet entry to the database.
	 *
	 * @param customElementPortletEntryId the primary key for the new custom element portlet entry
	 * @return the new custom element portlet entry
	 */
	@Override
	public CustomElementPortletEntry create(long customElementPortletEntryId) {
		CustomElementPortletEntry customElementPortletEntry =
			new CustomElementPortletEntryImpl();

		customElementPortletEntry.setNew(true);
		customElementPortletEntry.setPrimaryKey(customElementPortletEntryId);

		String uuid = PortalUUIDUtil.generate();

		customElementPortletEntry.setUuid(uuid);

		customElementPortletEntry.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return customElementPortletEntry;
	}

	/**
	 * Removes the custom element portlet entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param customElementPortletEntryId the primary key of the custom element portlet entry
	 * @return the custom element portlet entry that was removed
	 * @throws NoSuchCustomElementPortletEntryException if a custom element portlet entry with the primary key could not be found
	 */
	@Override
	public CustomElementPortletEntry remove(long customElementPortletEntryId)
		throws NoSuchCustomElementPortletEntryException {

		return remove((Serializable)customElementPortletEntryId);
	}

	/**
	 * Removes the custom element portlet entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the custom element portlet entry
	 * @return the custom element portlet entry that was removed
	 * @throws NoSuchCustomElementPortletEntryException if a custom element portlet entry with the primary key could not be found
	 */
	@Override
	public CustomElementPortletEntry remove(Serializable primaryKey)
		throws NoSuchCustomElementPortletEntryException {

		Session session = null;

		try {
			session = openSession();

			CustomElementPortletEntry customElementPortletEntry =
				(CustomElementPortletEntry)session.get(
					CustomElementPortletEntryImpl.class, primaryKey);

			if (customElementPortletEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCustomElementPortletEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(customElementPortletEntry);
		}
		catch (NoSuchCustomElementPortletEntryException noSuchEntityException) {
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
	protected CustomElementPortletEntry removeImpl(
		CustomElementPortletEntry customElementPortletEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(customElementPortletEntry)) {
				customElementPortletEntry =
					(CustomElementPortletEntry)session.get(
						CustomElementPortletEntryImpl.class,
						customElementPortletEntry.getPrimaryKeyObj());
			}

			if (customElementPortletEntry != null) {
				session.delete(customElementPortletEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (customElementPortletEntry != null) {
			clearCache(customElementPortletEntry);
		}

		return customElementPortletEntry;
	}

	@Override
	public CustomElementPortletEntry updateImpl(
		CustomElementPortletEntry customElementPortletEntry) {

		boolean isNew = customElementPortletEntry.isNew();

		if (!(customElementPortletEntry instanceof
				CustomElementPortletEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(customElementPortletEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					customElementPortletEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in customElementPortletEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CustomElementPortletEntry implementation " +
					customElementPortletEntry.getClass());
		}

		CustomElementPortletEntryModelImpl customElementPortletEntryModelImpl =
			(CustomElementPortletEntryModelImpl)customElementPortletEntry;

		if (Validator.isNull(customElementPortletEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			customElementPortletEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (customElementPortletEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				customElementPortletEntry.setCreateDate(date);
			}
			else {
				customElementPortletEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!customElementPortletEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				customElementPortletEntry.setModifiedDate(date);
			}
			else {
				customElementPortletEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(customElementPortletEntry);
			}
			else {
				customElementPortletEntry =
					(CustomElementPortletEntry)session.merge(
						customElementPortletEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CustomElementPortletEntryImpl.class,
			customElementPortletEntryModelImpl, false, true);

		if (isNew) {
			customElementPortletEntry.setNew(false);
		}

		customElementPortletEntry.resetOriginalValues();

		return customElementPortletEntry;
	}

	/**
	 * Returns the custom element portlet entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the custom element portlet entry
	 * @return the custom element portlet entry
	 * @throws NoSuchCustomElementPortletEntryException if a custom element portlet entry with the primary key could not be found
	 */
	@Override
	public CustomElementPortletEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCustomElementPortletEntryException {

		CustomElementPortletEntry customElementPortletEntry = fetchByPrimaryKey(
			primaryKey);

		if (customElementPortletEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCustomElementPortletEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return customElementPortletEntry;
	}

	/**
	 * Returns the custom element portlet entry with the primary key or throws a <code>NoSuchCustomElementPortletEntryException</code> if it could not be found.
	 *
	 * @param customElementPortletEntryId the primary key of the custom element portlet entry
	 * @return the custom element portlet entry
	 * @throws NoSuchCustomElementPortletEntryException if a custom element portlet entry with the primary key could not be found
	 */
	@Override
	public CustomElementPortletEntry findByPrimaryKey(
			long customElementPortletEntryId)
		throws NoSuchCustomElementPortletEntryException {

		return findByPrimaryKey((Serializable)customElementPortletEntryId);
	}

	/**
	 * Returns the custom element portlet entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param customElementPortletEntryId the primary key of the custom element portlet entry
	 * @return the custom element portlet entry, or <code>null</code> if a custom element portlet entry with the primary key could not be found
	 */
	@Override
	public CustomElementPortletEntry fetchByPrimaryKey(
		long customElementPortletEntryId) {

		return fetchByPrimaryKey((Serializable)customElementPortletEntryId);
	}

	/**
	 * Returns all the custom element portlet entries.
	 *
	 * @return the custom element portlet entries
	 */
	@Override
	public List<CustomElementPortletEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the custom element portlet entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementPortletEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of custom element portlet entries
	 * @param end the upper bound of the range of custom element portlet entries (not inclusive)
	 * @return the range of custom element portlet entries
	 */
	@Override
	public List<CustomElementPortletEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the custom element portlet entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementPortletEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of custom element portlet entries
	 * @param end the upper bound of the range of custom element portlet entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of custom element portlet entries
	 */
	@Override
	public List<CustomElementPortletEntry> findAll(
		int start, int end,
		OrderByComparator<CustomElementPortletEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the custom element portlet entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementPortletEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of custom element portlet entries
	 * @param end the upper bound of the range of custom element portlet entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of custom element portlet entries
	 */
	@Override
	public List<CustomElementPortletEntry> findAll(
		int start, int end,
		OrderByComparator<CustomElementPortletEntry> orderByComparator,
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

		List<CustomElementPortletEntry> list = null;

		if (useFinderCache) {
			list = (List<CustomElementPortletEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CUSTOMELEMENTPORTLETENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CUSTOMELEMENTPORTLETENTRY;

				sql = sql.concat(
					CustomElementPortletEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CustomElementPortletEntry>)QueryUtil.list(
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
	 * Removes all the custom element portlet entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CustomElementPortletEntry customElementPortletEntry : findAll()) {
			remove(customElementPortletEntry);
		}
	}

	/**
	 * Returns the number of custom element portlet entries.
	 *
	 * @return the number of custom element portlet entries
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
					_SQL_COUNT_CUSTOMELEMENTPORTLETENTRY);

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
		return "customElementPortletEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CUSTOMELEMENTPORTLETENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CustomElementPortletEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the custom element portlet entry persistence.
	 */
	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new CustomElementPortletEntryModelArgumentsResolver(),
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
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(CustomElementPortletEntryImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	@Override
	@Reference(
		target = RemoteAppPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = RemoteAppPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = RemoteAppPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_CUSTOMELEMENTPORTLETENTRY =
		"SELECT customElementPortletEntry FROM CustomElementPortletEntry customElementPortletEntry";

	private static final String _SQL_SELECT_CUSTOMELEMENTPORTLETENTRY_WHERE =
		"SELECT customElementPortletEntry FROM CustomElementPortletEntry customElementPortletEntry WHERE ";

	private static final String _SQL_COUNT_CUSTOMELEMENTPORTLETENTRY =
		"SELECT COUNT(customElementPortletEntry) FROM CustomElementPortletEntry customElementPortletEntry";

	private static final String _SQL_COUNT_CUSTOMELEMENTPORTLETENTRY_WHERE =
		"SELECT COUNT(customElementPortletEntry) FROM CustomElementPortletEntry customElementPortletEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"customElementPortletEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CustomElementPortletEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CustomElementPortletEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CustomElementPortletEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class CustomElementPortletEntryModelArgumentsResolver
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

			CustomElementPortletEntryModelImpl
				customElementPortletEntryModelImpl =
					(CustomElementPortletEntryModelImpl)baseModel;

			long columnBitmask =
				customElementPortletEntryModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					customElementPortletEntryModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						customElementPortletEntryModelImpl.getColumnBitmask(
							columnName);
				}

				if (finderPath.isBaseModelResult() &&
					(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION ==
						finderPath.getCacheName())) {

					finderPathColumnBitmask |= _ORDER_BY_COLUMNS_BITMASK;
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					customElementPortletEntryModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return CustomElementPortletEntryImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return CustomElementPortletEntryTable.INSTANCE.getTableName();
		}

		private static Object[] _getValue(
			CustomElementPortletEntryModelImpl
				customElementPortletEntryModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						customElementPortletEntryModelImpl.
							getColumnOriginalValue(columnName);
				}
				else {
					arguments[i] =
						customElementPortletEntryModelImpl.getColumnValue(
							columnName);
				}
			}

			return arguments;
		}

		private static final Map<FinderPath, Long>
			_finderPathColumnBitmasksCache = new ConcurrentHashMap<>();

		private static final long _ORDER_BY_COLUMNS_BITMASK;

		static {
			long orderByColumnsBitmask = 0;

			orderByColumnsBitmask |=
				CustomElementPortletEntryModelImpl.getColumnBitmask("name");

			_ORDER_BY_COLUMNS_BITMASK = orderByColumnsBitmask;
		}

	}

}