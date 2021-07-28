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

package com.liferay.remote.app.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.remote.app.model.CustomElementPortletEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the custom element portlet entry service. This utility wraps <code>com.liferay.remote.app.service.persistence.impl.CustomElementPortletEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CustomElementPortletEntryPersistence
 * @generated
 */
public class CustomElementPortletEntryUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(
		CustomElementPortletEntry customElementPortletEntry) {

		getPersistence().clearCache(customElementPortletEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, CustomElementPortletEntry>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CustomElementPortletEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CustomElementPortletEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CustomElementPortletEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CustomElementPortletEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CustomElementPortletEntry update(
		CustomElementPortletEntry customElementPortletEntry) {

		return getPersistence().update(customElementPortletEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CustomElementPortletEntry update(
		CustomElementPortletEntry customElementPortletEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(
			customElementPortletEntry, serviceContext);
	}

	/**
	 * Returns all the custom element portlet entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching custom element portlet entries
	 */
	public static List<CustomElementPortletEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
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
	public static List<CustomElementPortletEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
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
	public static List<CustomElementPortletEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CustomElementPortletEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
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
	public static List<CustomElementPortletEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CustomElementPortletEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first custom element portlet entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom element portlet entry
	 * @throws NoSuchCustomElementPortletEntryException if a matching custom element portlet entry could not be found
	 */
	public static CustomElementPortletEntry findByUuid_First(
			String uuid,
			OrderByComparator<CustomElementPortletEntry> orderByComparator)
		throws com.liferay.remote.app.exception.
			NoSuchCustomElementPortletEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first custom element portlet entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom element portlet entry, or <code>null</code> if a matching custom element portlet entry could not be found
	 */
	public static CustomElementPortletEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<CustomElementPortletEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last custom element portlet entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching custom element portlet entry
	 * @throws NoSuchCustomElementPortletEntryException if a matching custom element portlet entry could not be found
	 */
	public static CustomElementPortletEntry findByUuid_Last(
			String uuid,
			OrderByComparator<CustomElementPortletEntry> orderByComparator)
		throws com.liferay.remote.app.exception.
			NoSuchCustomElementPortletEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last custom element portlet entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching custom element portlet entry, or <code>null</code> if a matching custom element portlet entry could not be found
	 */
	public static CustomElementPortletEntry fetchByUuid_Last(
		String uuid,
		OrderByComparator<CustomElementPortletEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
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
	public static CustomElementPortletEntry[] findByUuid_PrevAndNext(
			long customElementPortletEntryId, String uuid,
			OrderByComparator<CustomElementPortletEntry> orderByComparator)
		throws com.liferay.remote.app.exception.
			NoSuchCustomElementPortletEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			customElementPortletEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the custom element portlet entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of custom element portlet entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching custom element portlet entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the custom element portlet entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching custom element portlet entries
	 */
	public static List<CustomElementPortletEntry> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
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
	public static List<CustomElementPortletEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
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
	public static List<CustomElementPortletEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CustomElementPortletEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
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
	public static List<CustomElementPortletEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CustomElementPortletEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
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
	public static CustomElementPortletEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CustomElementPortletEntry> orderByComparator)
		throws com.liferay.remote.app.exception.
			NoSuchCustomElementPortletEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first custom element portlet entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom element portlet entry, or <code>null</code> if a matching custom element portlet entry could not be found
	 */
	public static CustomElementPortletEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CustomElementPortletEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
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
	public static CustomElementPortletEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CustomElementPortletEntry> orderByComparator)
		throws com.liferay.remote.app.exception.
			NoSuchCustomElementPortletEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last custom element portlet entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching custom element portlet entry, or <code>null</code> if a matching custom element portlet entry could not be found
	 */
	public static CustomElementPortletEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CustomElementPortletEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
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
	public static CustomElementPortletEntry[] findByUuid_C_PrevAndNext(
			long customElementPortletEntryId, String uuid, long companyId,
			OrderByComparator<CustomElementPortletEntry> orderByComparator)
		throws com.liferay.remote.app.exception.
			NoSuchCustomElementPortletEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			customElementPortletEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the custom element portlet entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of custom element portlet entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching custom element portlet entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Caches the custom element portlet entry in the entity cache if it is enabled.
	 *
	 * @param customElementPortletEntry the custom element portlet entry
	 */
	public static void cacheResult(
		CustomElementPortletEntry customElementPortletEntry) {

		getPersistence().cacheResult(customElementPortletEntry);
	}

	/**
	 * Caches the custom element portlet entries in the entity cache if it is enabled.
	 *
	 * @param customElementPortletEntries the custom element portlet entries
	 */
	public static void cacheResult(
		List<CustomElementPortletEntry> customElementPortletEntries) {

		getPersistence().cacheResult(customElementPortletEntries);
	}

	/**
	 * Creates a new custom element portlet entry with the primary key. Does not add the custom element portlet entry to the database.
	 *
	 * @param customElementPortletEntryId the primary key for the new custom element portlet entry
	 * @return the new custom element portlet entry
	 */
	public static CustomElementPortletEntry create(
		long customElementPortletEntryId) {

		return getPersistence().create(customElementPortletEntryId);
	}

	/**
	 * Removes the custom element portlet entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param customElementPortletEntryId the primary key of the custom element portlet entry
	 * @return the custom element portlet entry that was removed
	 * @throws NoSuchCustomElementPortletEntryException if a custom element portlet entry with the primary key could not be found
	 */
	public static CustomElementPortletEntry remove(
			long customElementPortletEntryId)
		throws com.liferay.remote.app.exception.
			NoSuchCustomElementPortletEntryException {

		return getPersistence().remove(customElementPortletEntryId);
	}

	public static CustomElementPortletEntry updateImpl(
		CustomElementPortletEntry customElementPortletEntry) {

		return getPersistence().updateImpl(customElementPortletEntry);
	}

	/**
	 * Returns the custom element portlet entry with the primary key or throws a <code>NoSuchCustomElementPortletEntryException</code> if it could not be found.
	 *
	 * @param customElementPortletEntryId the primary key of the custom element portlet entry
	 * @return the custom element portlet entry
	 * @throws NoSuchCustomElementPortletEntryException if a custom element portlet entry with the primary key could not be found
	 */
	public static CustomElementPortletEntry findByPrimaryKey(
			long customElementPortletEntryId)
		throws com.liferay.remote.app.exception.
			NoSuchCustomElementPortletEntryException {

		return getPersistence().findByPrimaryKey(customElementPortletEntryId);
	}

	/**
	 * Returns the custom element portlet entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param customElementPortletEntryId the primary key of the custom element portlet entry
	 * @return the custom element portlet entry, or <code>null</code> if a custom element portlet entry with the primary key could not be found
	 */
	public static CustomElementPortletEntry fetchByPrimaryKey(
		long customElementPortletEntryId) {

		return getPersistence().fetchByPrimaryKey(customElementPortletEntryId);
	}

	/**
	 * Returns all the custom element portlet entries.
	 *
	 * @return the custom element portlet entries
	 */
	public static List<CustomElementPortletEntry> findAll() {
		return getPersistence().findAll();
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
	public static List<CustomElementPortletEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
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
	public static List<CustomElementPortletEntry> findAll(
		int start, int end,
		OrderByComparator<CustomElementPortletEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
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
	public static List<CustomElementPortletEntry> findAll(
		int start, int end,
		OrderByComparator<CustomElementPortletEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the custom element portlet entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of custom element portlet entries.
	 *
	 * @return the number of custom element portlet entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CustomElementPortletEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CustomElementPortletEntryPersistence,
		 CustomElementPortletEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CustomElementPortletEntryPersistence.class);

		ServiceTracker
			<CustomElementPortletEntryPersistence,
			 CustomElementPortletEntryPersistence> serviceTracker =
				new ServiceTracker
					<CustomElementPortletEntryPersistence,
					 CustomElementPortletEntryPersistence>(
						 bundle.getBundleContext(),
						 CustomElementPortletEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}