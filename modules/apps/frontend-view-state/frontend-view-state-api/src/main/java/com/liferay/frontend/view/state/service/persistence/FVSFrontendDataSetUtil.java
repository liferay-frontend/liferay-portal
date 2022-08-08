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

package com.liferay.frontend.view.state.service.persistence;

import com.liferay.frontend.view.state.model.FVSFrontendDataSet;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the fvs frontend data set service. This utility wraps <code>com.liferay.frontend.view.state.service.persistence.impl.FVSFrontendDataSetPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FVSFrontendDataSetPersistence
 * @generated
 */
public class FVSFrontendDataSetUtil {

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
	public static void clearCache(FVSFrontendDataSet fvsFrontendDataSet) {
		getPersistence().clearCache(fvsFrontendDataSet);
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
	public static Map<Serializable, FVSFrontendDataSet> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<FVSFrontendDataSet> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<FVSFrontendDataSet> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<FVSFrontendDataSet> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<FVSFrontendDataSet> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static FVSFrontendDataSet update(
		FVSFrontendDataSet fvsFrontendDataSet) {

		return getPersistence().update(fvsFrontendDataSet);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static FVSFrontendDataSet update(
		FVSFrontendDataSet fvsFrontendDataSet, ServiceContext serviceContext) {

		return getPersistence().update(fvsFrontendDataSet, serviceContext);
	}

	/**
	 * Returns all the fvs frontend data sets where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching fvs frontend data sets
	 */
	public static List<FVSFrontendDataSet> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
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
	public static List<FVSFrontendDataSet> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
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
	public static List<FVSFrontendDataSet> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FVSFrontendDataSet> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
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
	public static List<FVSFrontendDataSet> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FVSFrontendDataSet> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first fvs frontend data set in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set
	 * @throws NoSuchFVSFrontendDataSetException if a matching fvs frontend data set could not be found
	 */
	public static FVSFrontendDataSet findByUuid_First(
			String uuid,
			OrderByComparator<FVSFrontendDataSet> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchFVSFrontendDataSetException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first fvs frontend data set in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set, or <code>null</code> if a matching fvs frontend data set could not be found
	 */
	public static FVSFrontendDataSet fetchByUuid_First(
		String uuid, OrderByComparator<FVSFrontendDataSet> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last fvs frontend data set in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set
	 * @throws NoSuchFVSFrontendDataSetException if a matching fvs frontend data set could not be found
	 */
	public static FVSFrontendDataSet findByUuid_Last(
			String uuid,
			OrderByComparator<FVSFrontendDataSet> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchFVSFrontendDataSetException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last fvs frontend data set in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set, or <code>null</code> if a matching fvs frontend data set could not be found
	 */
	public static FVSFrontendDataSet fetchByUuid_Last(
		String uuid, OrderByComparator<FVSFrontendDataSet> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
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
	public static FVSFrontendDataSet[] findByUuid_PrevAndNext(
			long fvsFrontendDataSetId, String uuid,
			OrderByComparator<FVSFrontendDataSet> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchFVSFrontendDataSetException {

		return getPersistence().findByUuid_PrevAndNext(
			fvsFrontendDataSetId, uuid, orderByComparator);
	}

	/**
	 * Removes all the fvs frontend data sets where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of fvs frontend data sets where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching fvs frontend data sets
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the fvs frontend data sets where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching fvs frontend data sets
	 */
	public static List<FVSFrontendDataSet> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
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
	public static List<FVSFrontendDataSet> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
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
	public static List<FVSFrontendDataSet> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FVSFrontendDataSet> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
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
	public static List<FVSFrontendDataSet> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FVSFrontendDataSet> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
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
	public static FVSFrontendDataSet findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<FVSFrontendDataSet> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchFVSFrontendDataSetException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first fvs frontend data set in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set, or <code>null</code> if a matching fvs frontend data set could not be found
	 */
	public static FVSFrontendDataSet fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<FVSFrontendDataSet> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
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
	public static FVSFrontendDataSet findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<FVSFrontendDataSet> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchFVSFrontendDataSetException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last fvs frontend data set in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set, or <code>null</code> if a matching fvs frontend data set could not be found
	 */
	public static FVSFrontendDataSet fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<FVSFrontendDataSet> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
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
	public static FVSFrontendDataSet[] findByUuid_C_PrevAndNext(
			long fvsFrontendDataSetId, String uuid, long companyId,
			OrderByComparator<FVSFrontendDataSet> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchFVSFrontendDataSetException {

		return getPersistence().findByUuid_C_PrevAndNext(
			fvsFrontendDataSetId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the fvs frontend data sets where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of fvs frontend data sets where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching fvs frontend data sets
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the fvs frontend data sets where fdsName = &#63; and system = &#63;.
	 *
	 * @param fdsName the fds name
	 * @param system the system
	 * @return the matching fvs frontend data sets
	 */
	public static List<FVSFrontendDataSet> findByF_S(
		String fdsName, boolean system) {

		return getPersistence().findByF_S(fdsName, system);
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
	public static List<FVSFrontendDataSet> findByF_S(
		String fdsName, boolean system, int start, int end) {

		return getPersistence().findByF_S(fdsName, system, start, end);
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
	public static List<FVSFrontendDataSet> findByF_S(
		String fdsName, boolean system, int start, int end,
		OrderByComparator<FVSFrontendDataSet> orderByComparator) {

		return getPersistence().findByF_S(
			fdsName, system, start, end, orderByComparator);
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
	public static List<FVSFrontendDataSet> findByF_S(
		String fdsName, boolean system, int start, int end,
		OrderByComparator<FVSFrontendDataSet> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByF_S(
			fdsName, system, start, end, orderByComparator, useFinderCache);
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
	public static FVSFrontendDataSet findByF_S_First(
			String fdsName, boolean system,
			OrderByComparator<FVSFrontendDataSet> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchFVSFrontendDataSetException {

		return getPersistence().findByF_S_First(
			fdsName, system, orderByComparator);
	}

	/**
	 * Returns the first fvs frontend data set in the ordered set where fdsName = &#63; and system = &#63;.
	 *
	 * @param fdsName the fds name
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set, or <code>null</code> if a matching fvs frontend data set could not be found
	 */
	public static FVSFrontendDataSet fetchByF_S_First(
		String fdsName, boolean system,
		OrderByComparator<FVSFrontendDataSet> orderByComparator) {

		return getPersistence().fetchByF_S_First(
			fdsName, system, orderByComparator);
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
	public static FVSFrontendDataSet findByF_S_Last(
			String fdsName, boolean system,
			OrderByComparator<FVSFrontendDataSet> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchFVSFrontendDataSetException {

		return getPersistence().findByF_S_Last(
			fdsName, system, orderByComparator);
	}

	/**
	 * Returns the last fvs frontend data set in the ordered set where fdsName = &#63; and system = &#63;.
	 *
	 * @param fdsName the fds name
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set, or <code>null</code> if a matching fvs frontend data set could not be found
	 */
	public static FVSFrontendDataSet fetchByF_S_Last(
		String fdsName, boolean system,
		OrderByComparator<FVSFrontendDataSet> orderByComparator) {

		return getPersistence().fetchByF_S_Last(
			fdsName, system, orderByComparator);
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
	public static FVSFrontendDataSet[] findByF_S_PrevAndNext(
			long fvsFrontendDataSetId, String fdsName, boolean system,
			OrderByComparator<FVSFrontendDataSet> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchFVSFrontendDataSetException {

		return getPersistence().findByF_S_PrevAndNext(
			fvsFrontendDataSetId, fdsName, system, orderByComparator);
	}

	/**
	 * Removes all the fvs frontend data sets where fdsName = &#63; and system = &#63; from the database.
	 *
	 * @param fdsName the fds name
	 * @param system the system
	 */
	public static void removeByF_S(String fdsName, boolean system) {
		getPersistence().removeByF_S(fdsName, system);
	}

	/**
	 * Returns the number of fvs frontend data sets where fdsName = &#63; and system = &#63;.
	 *
	 * @param fdsName the fds name
	 * @param system the system
	 * @return the number of matching fvs frontend data sets
	 */
	public static int countByF_S(String fdsName, boolean system) {
		return getPersistence().countByF_S(fdsName, system);
	}

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
	public static List<FVSFrontendDataSet> findByU_F_P_P_S(
		long userId, String fdsName, long plid, String portletId,
		boolean system) {

		return getPersistence().findByU_F_P_P_S(
			userId, fdsName, plid, portletId, system);
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
	public static List<FVSFrontendDataSet> findByU_F_P_P_S(
		long userId, String fdsName, long plid, String portletId,
		boolean system, int start, int end) {

		return getPersistence().findByU_F_P_P_S(
			userId, fdsName, plid, portletId, system, start, end);
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
	public static List<FVSFrontendDataSet> findByU_F_P_P_S(
		long userId, String fdsName, long plid, String portletId,
		boolean system, int start, int end,
		OrderByComparator<FVSFrontendDataSet> orderByComparator) {

		return getPersistence().findByU_F_P_P_S(
			userId, fdsName, plid, portletId, system, start, end,
			orderByComparator);
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
	public static List<FVSFrontendDataSet> findByU_F_P_P_S(
		long userId, String fdsName, long plid, String portletId,
		boolean system, int start, int end,
		OrderByComparator<FVSFrontendDataSet> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByU_F_P_P_S(
			userId, fdsName, plid, portletId, system, start, end,
			orderByComparator, useFinderCache);
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
	public static FVSFrontendDataSet findByU_F_P_P_S_First(
			long userId, String fdsName, long plid, String portletId,
			boolean system,
			OrderByComparator<FVSFrontendDataSet> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchFVSFrontendDataSetException {

		return getPersistence().findByU_F_P_P_S_First(
			userId, fdsName, plid, portletId, system, orderByComparator);
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
	public static FVSFrontendDataSet fetchByU_F_P_P_S_First(
		long userId, String fdsName, long plid, String portletId,
		boolean system,
		OrderByComparator<FVSFrontendDataSet> orderByComparator) {

		return getPersistence().fetchByU_F_P_P_S_First(
			userId, fdsName, plid, portletId, system, orderByComparator);
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
	public static FVSFrontendDataSet findByU_F_P_P_S_Last(
			long userId, String fdsName, long plid, String portletId,
			boolean system,
			OrderByComparator<FVSFrontendDataSet> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchFVSFrontendDataSetException {

		return getPersistence().findByU_F_P_P_S_Last(
			userId, fdsName, plid, portletId, system, orderByComparator);
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
	public static FVSFrontendDataSet fetchByU_F_P_P_S_Last(
		long userId, String fdsName, long plid, String portletId,
		boolean system,
		OrderByComparator<FVSFrontendDataSet> orderByComparator) {

		return getPersistence().fetchByU_F_P_P_S_Last(
			userId, fdsName, plid, portletId, system, orderByComparator);
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
	public static FVSFrontendDataSet[] findByU_F_P_P_S_PrevAndNext(
			long fvsFrontendDataSetId, long userId, String fdsName, long plid,
			String portletId, boolean system,
			OrderByComparator<FVSFrontendDataSet> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchFVSFrontendDataSetException {

		return getPersistence().findByU_F_P_P_S_PrevAndNext(
			fvsFrontendDataSetId, userId, fdsName, plid, portletId, system,
			orderByComparator);
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
	public static void removeByU_F_P_P_S(
		long userId, String fdsName, long plid, String portletId,
		boolean system) {

		getPersistence().removeByU_F_P_P_S(
			userId, fdsName, plid, portletId, system);
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
	public static int countByU_F_P_P_S(
		long userId, String fdsName, long plid, String portletId,
		boolean system) {

		return getPersistence().countByU_F_P_P_S(
			userId, fdsName, plid, portletId, system);
	}

	/**
	 * Caches the fvs frontend data set in the entity cache if it is enabled.
	 *
	 * @param fvsFrontendDataSet the fvs frontend data set
	 */
	public static void cacheResult(FVSFrontendDataSet fvsFrontendDataSet) {
		getPersistence().cacheResult(fvsFrontendDataSet);
	}

	/**
	 * Caches the fvs frontend data sets in the entity cache if it is enabled.
	 *
	 * @param fvsFrontendDataSets the fvs frontend data sets
	 */
	public static void cacheResult(
		List<FVSFrontendDataSet> fvsFrontendDataSets) {

		getPersistence().cacheResult(fvsFrontendDataSets);
	}

	/**
	 * Creates a new fvs frontend data set with the primary key. Does not add the fvs frontend data set to the database.
	 *
	 * @param fvsFrontendDataSetId the primary key for the new fvs frontend data set
	 * @return the new fvs frontend data set
	 */
	public static FVSFrontendDataSet create(long fvsFrontendDataSetId) {
		return getPersistence().create(fvsFrontendDataSetId);
	}

	/**
	 * Removes the fvs frontend data set with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fvsFrontendDataSetId the primary key of the fvs frontend data set
	 * @return the fvs frontend data set that was removed
	 * @throws NoSuchFVSFrontendDataSetException if a fvs frontend data set with the primary key could not be found
	 */
	public static FVSFrontendDataSet remove(long fvsFrontendDataSetId)
		throws com.liferay.frontend.view.state.exception.
			NoSuchFVSFrontendDataSetException {

		return getPersistence().remove(fvsFrontendDataSetId);
	}

	public static FVSFrontendDataSet updateImpl(
		FVSFrontendDataSet fvsFrontendDataSet) {

		return getPersistence().updateImpl(fvsFrontendDataSet);
	}

	/**
	 * Returns the fvs frontend data set with the primary key or throws a <code>NoSuchFVSFrontendDataSetException</code> if it could not be found.
	 *
	 * @param fvsFrontendDataSetId the primary key of the fvs frontend data set
	 * @return the fvs frontend data set
	 * @throws NoSuchFVSFrontendDataSetException if a fvs frontend data set with the primary key could not be found
	 */
	public static FVSFrontendDataSet findByPrimaryKey(long fvsFrontendDataSetId)
		throws com.liferay.frontend.view.state.exception.
			NoSuchFVSFrontendDataSetException {

		return getPersistence().findByPrimaryKey(fvsFrontendDataSetId);
	}

	/**
	 * Returns the fvs frontend data set with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fvsFrontendDataSetId the primary key of the fvs frontend data set
	 * @return the fvs frontend data set, or <code>null</code> if a fvs frontend data set with the primary key could not be found
	 */
	public static FVSFrontendDataSet fetchByPrimaryKey(
		long fvsFrontendDataSetId) {

		return getPersistence().fetchByPrimaryKey(fvsFrontendDataSetId);
	}

	/**
	 * Returns all the fvs frontend data sets.
	 *
	 * @return the fvs frontend data sets
	 */
	public static List<FVSFrontendDataSet> findAll() {
		return getPersistence().findAll();
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
	public static List<FVSFrontendDataSet> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
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
	public static List<FVSFrontendDataSet> findAll(
		int start, int end,
		OrderByComparator<FVSFrontendDataSet> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
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
	public static List<FVSFrontendDataSet> findAll(
		int start, int end,
		OrderByComparator<FVSFrontendDataSet> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the fvs frontend data sets from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of fvs frontend data sets.
	 *
	 * @return the number of fvs frontend data sets
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static FVSFrontendDataSetPersistence getPersistence() {
		return _persistence;
	}

	private static volatile FVSFrontendDataSetPersistence _persistence;

}