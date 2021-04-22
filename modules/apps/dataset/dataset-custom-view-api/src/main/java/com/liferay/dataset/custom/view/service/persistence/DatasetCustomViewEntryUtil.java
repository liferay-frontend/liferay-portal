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

package com.liferay.dataset.custom.view.service.persistence;

import com.liferay.dataset.custom.view.model.DatasetCustomViewEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the dataset custom view entry service. This utility wraps <code>com.liferay.dataset.custom.view.service.persistence.impl.DatasetCustomViewEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DatasetCustomViewEntryPersistence
 * @generated
 */
public class DatasetCustomViewEntryUtil {

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
		DatasetCustomViewEntry datasetCustomViewEntry) {

		getPersistence().clearCache(datasetCustomViewEntry);
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
	public static Map<Serializable, DatasetCustomViewEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DatasetCustomViewEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DatasetCustomViewEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DatasetCustomViewEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DatasetCustomViewEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DatasetCustomViewEntry update(
		DatasetCustomViewEntry datasetCustomViewEntry) {

		return getPersistence().update(datasetCustomViewEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DatasetCustomViewEntry update(
		DatasetCustomViewEntry datasetCustomViewEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(datasetCustomViewEntry, serviceContext);
	}

	/**
	 * Returns all the dataset custom view entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching dataset custom view entries
	 */
	public static List<DatasetCustomViewEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
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
	public static List<DatasetCustomViewEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
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
	public static List<DatasetCustomViewEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DatasetCustomViewEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
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
	public static List<DatasetCustomViewEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DatasetCustomViewEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first dataset custom view entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dataset custom view entry
	 * @throws NoSuchEntryException if a matching dataset custom view entry could not be found
	 */
	public static DatasetCustomViewEntry findByUuid_First(
			String uuid,
			OrderByComparator<DatasetCustomViewEntry> orderByComparator)
		throws com.liferay.dataset.custom.view.exception.NoSuchEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first dataset custom view entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dataset custom view entry, or <code>null</code> if a matching dataset custom view entry could not be found
	 */
	public static DatasetCustomViewEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<DatasetCustomViewEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last dataset custom view entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dataset custom view entry
	 * @throws NoSuchEntryException if a matching dataset custom view entry could not be found
	 */
	public static DatasetCustomViewEntry findByUuid_Last(
			String uuid,
			OrderByComparator<DatasetCustomViewEntry> orderByComparator)
		throws com.liferay.dataset.custom.view.exception.NoSuchEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last dataset custom view entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dataset custom view entry, or <code>null</code> if a matching dataset custom view entry could not be found
	 */
	public static DatasetCustomViewEntry fetchByUuid_Last(
		String uuid,
		OrderByComparator<DatasetCustomViewEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
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
	public static DatasetCustomViewEntry[] findByUuid_PrevAndNext(
			long datasetCustomViewEntryId, String uuid,
			OrderByComparator<DatasetCustomViewEntry> orderByComparator)
		throws com.liferay.dataset.custom.view.exception.NoSuchEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			datasetCustomViewEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the dataset custom view entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of dataset custom view entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching dataset custom view entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the dataset custom view entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching dataset custom view entries
	 */
	public static List<DatasetCustomViewEntry> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
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
	public static List<DatasetCustomViewEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
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
	public static List<DatasetCustomViewEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DatasetCustomViewEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
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
	public static List<DatasetCustomViewEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DatasetCustomViewEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
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
	public static DatasetCustomViewEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<DatasetCustomViewEntry> orderByComparator)
		throws com.liferay.dataset.custom.view.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first dataset custom view entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dataset custom view entry, or <code>null</code> if a matching dataset custom view entry could not be found
	 */
	public static DatasetCustomViewEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<DatasetCustomViewEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
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
	public static DatasetCustomViewEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<DatasetCustomViewEntry> orderByComparator)
		throws com.liferay.dataset.custom.view.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last dataset custom view entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dataset custom view entry, or <code>null</code> if a matching dataset custom view entry could not be found
	 */
	public static DatasetCustomViewEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<DatasetCustomViewEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
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
	public static DatasetCustomViewEntry[] findByUuid_C_PrevAndNext(
			long datasetCustomViewEntryId, String uuid, long companyId,
			OrderByComparator<DatasetCustomViewEntry> orderByComparator)
		throws com.liferay.dataset.custom.view.exception.NoSuchEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			datasetCustomViewEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the dataset custom view entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of dataset custom view entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching dataset custom view entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

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
	public static DatasetCustomViewEntry findByU_D_P_P(
			long userId, String datasetDisplayId, long plid, String portletId)
		throws com.liferay.dataset.custom.view.exception.NoSuchEntryException {

		return getPersistence().findByU_D_P_P(
			userId, datasetDisplayId, plid, portletId);
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
	public static DatasetCustomViewEntry fetchByU_D_P_P(
		long userId, String datasetDisplayId, long plid, String portletId) {

		return getPersistence().fetchByU_D_P_P(
			userId, datasetDisplayId, plid, portletId);
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
	public static DatasetCustomViewEntry fetchByU_D_P_P(
		long userId, String datasetDisplayId, long plid, String portletId,
		boolean useFinderCache) {

		return getPersistence().fetchByU_D_P_P(
			userId, datasetDisplayId, plid, portletId, useFinderCache);
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
	public static DatasetCustomViewEntry removeByU_D_P_P(
			long userId, String datasetDisplayId, long plid, String portletId)
		throws com.liferay.dataset.custom.view.exception.NoSuchEntryException {

		return getPersistence().removeByU_D_P_P(
			userId, datasetDisplayId, plid, portletId);
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
	public static int countByU_D_P_P(
		long userId, String datasetDisplayId, long plid, String portletId) {

		return getPersistence().countByU_D_P_P(
			userId, datasetDisplayId, plid, portletId);
	}

	/**
	 * Caches the dataset custom view entry in the entity cache if it is enabled.
	 *
	 * @param datasetCustomViewEntry the dataset custom view entry
	 */
	public static void cacheResult(
		DatasetCustomViewEntry datasetCustomViewEntry) {

		getPersistence().cacheResult(datasetCustomViewEntry);
	}

	/**
	 * Caches the dataset custom view entries in the entity cache if it is enabled.
	 *
	 * @param datasetCustomViewEntries the dataset custom view entries
	 */
	public static void cacheResult(
		List<DatasetCustomViewEntry> datasetCustomViewEntries) {

		getPersistence().cacheResult(datasetCustomViewEntries);
	}

	/**
	 * Creates a new dataset custom view entry with the primary key. Does not add the dataset custom view entry to the database.
	 *
	 * @param datasetCustomViewEntryId the primary key for the new dataset custom view entry
	 * @return the new dataset custom view entry
	 */
	public static DatasetCustomViewEntry create(long datasetCustomViewEntryId) {
		return getPersistence().create(datasetCustomViewEntryId);
	}

	/**
	 * Removes the dataset custom view entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param datasetCustomViewEntryId the primary key of the dataset custom view entry
	 * @return the dataset custom view entry that was removed
	 * @throws NoSuchEntryException if a dataset custom view entry with the primary key could not be found
	 */
	public static DatasetCustomViewEntry remove(long datasetCustomViewEntryId)
		throws com.liferay.dataset.custom.view.exception.NoSuchEntryException {

		return getPersistence().remove(datasetCustomViewEntryId);
	}

	public static DatasetCustomViewEntry updateImpl(
		DatasetCustomViewEntry datasetCustomViewEntry) {

		return getPersistence().updateImpl(datasetCustomViewEntry);
	}

	/**
	 * Returns the dataset custom view entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param datasetCustomViewEntryId the primary key of the dataset custom view entry
	 * @return the dataset custom view entry
	 * @throws NoSuchEntryException if a dataset custom view entry with the primary key could not be found
	 */
	public static DatasetCustomViewEntry findByPrimaryKey(
			long datasetCustomViewEntryId)
		throws com.liferay.dataset.custom.view.exception.NoSuchEntryException {

		return getPersistence().findByPrimaryKey(datasetCustomViewEntryId);
	}

	/**
	 * Returns the dataset custom view entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param datasetCustomViewEntryId the primary key of the dataset custom view entry
	 * @return the dataset custom view entry, or <code>null</code> if a dataset custom view entry with the primary key could not be found
	 */
	public static DatasetCustomViewEntry fetchByPrimaryKey(
		long datasetCustomViewEntryId) {

		return getPersistence().fetchByPrimaryKey(datasetCustomViewEntryId);
	}

	/**
	 * Returns all the dataset custom view entries.
	 *
	 * @return the dataset custom view entries
	 */
	public static List<DatasetCustomViewEntry> findAll() {
		return getPersistence().findAll();
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
	public static List<DatasetCustomViewEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
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
	public static List<DatasetCustomViewEntry> findAll(
		int start, int end,
		OrderByComparator<DatasetCustomViewEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
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
	public static List<DatasetCustomViewEntry> findAll(
		int start, int end,
		OrderByComparator<DatasetCustomViewEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the dataset custom view entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of dataset custom view entries.
	 *
	 * @return the number of dataset custom view entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DatasetCustomViewEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DatasetCustomViewEntryPersistence, DatasetCustomViewEntryPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DatasetCustomViewEntryPersistence.class);

		ServiceTracker
			<DatasetCustomViewEntryPersistence,
			 DatasetCustomViewEntryPersistence> serviceTracker =
				new ServiceTracker
					<DatasetCustomViewEntryPersistence,
					 DatasetCustomViewEntryPersistence>(
						 bundle.getBundleContext(),
						 DatasetCustomViewEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}