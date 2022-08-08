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

import com.liferay.frontend.view.state.exception.NoSuchFVSFrontendDataSetException;
import com.liferay.frontend.view.state.model.FVSFrontendDataSet;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the fvs frontend data set service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FVSFrontendDataSetUtil
 * @generated
 */
@ProviderType
public interface FVSFrontendDataSetPersistence
	extends BasePersistence<FVSFrontendDataSet> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FVSFrontendDataSetUtil} to access the fvs frontend data set persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the fvs frontend data sets where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching fvs frontend data sets
	 */
	public java.util.List<FVSFrontendDataSet> findByUuid(String uuid);

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
	public java.util.List<FVSFrontendDataSet> findByUuid(
		String uuid, int start, int end);

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
	public java.util.List<FVSFrontendDataSet> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FVSFrontendDataSet>
			orderByComparator);

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
	public java.util.List<FVSFrontendDataSet> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FVSFrontendDataSet>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fvs frontend data set in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set
	 * @throws NoSuchFVSFrontendDataSetException if a matching fvs frontend data set could not be found
	 */
	public FVSFrontendDataSet findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<FVSFrontendDataSet>
				orderByComparator)
		throws NoSuchFVSFrontendDataSetException;

	/**
	 * Returns the first fvs frontend data set in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set, or <code>null</code> if a matching fvs frontend data set could not be found
	 */
	public FVSFrontendDataSet fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<FVSFrontendDataSet>
			orderByComparator);

	/**
	 * Returns the last fvs frontend data set in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set
	 * @throws NoSuchFVSFrontendDataSetException if a matching fvs frontend data set could not be found
	 */
	public FVSFrontendDataSet findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<FVSFrontendDataSet>
				orderByComparator)
		throws NoSuchFVSFrontendDataSetException;

	/**
	 * Returns the last fvs frontend data set in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set, or <code>null</code> if a matching fvs frontend data set could not be found
	 */
	public FVSFrontendDataSet fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<FVSFrontendDataSet>
			orderByComparator);

	/**
	 * Returns the fvs frontend data sets before and after the current fvs frontend data set in the ordered set where uuid = &#63;.
	 *
	 * @param fvsFrontendDataSetId the primary key of the current fvs frontend data set
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fvs frontend data set
	 * @throws NoSuchFVSFrontendDataSetException if a fvs frontend data set with the primary key could not be found
	 */
	public FVSFrontendDataSet[] findByUuid_PrevAndNext(
			long fvsFrontendDataSetId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<FVSFrontendDataSet>
				orderByComparator)
		throws NoSuchFVSFrontendDataSetException;

	/**
	 * Removes all the fvs frontend data sets where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of fvs frontend data sets where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching fvs frontend data sets
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the fvs frontend data sets where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching fvs frontend data sets
	 */
	public java.util.List<FVSFrontendDataSet> findByUuid_C(
		String uuid, long companyId);

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
	public java.util.List<FVSFrontendDataSet> findByUuid_C(
		String uuid, long companyId, int start, int end);

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
	public java.util.List<FVSFrontendDataSet> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FVSFrontendDataSet>
			orderByComparator);

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
	public java.util.List<FVSFrontendDataSet> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FVSFrontendDataSet>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fvs frontend data set in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set
	 * @throws NoSuchFVSFrontendDataSetException if a matching fvs frontend data set could not be found
	 */
	public FVSFrontendDataSet findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<FVSFrontendDataSet>
				orderByComparator)
		throws NoSuchFVSFrontendDataSetException;

	/**
	 * Returns the first fvs frontend data set in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set, or <code>null</code> if a matching fvs frontend data set could not be found
	 */
	public FVSFrontendDataSet fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<FVSFrontendDataSet>
			orderByComparator);

	/**
	 * Returns the last fvs frontend data set in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set
	 * @throws NoSuchFVSFrontendDataSetException if a matching fvs frontend data set could not be found
	 */
	public FVSFrontendDataSet findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<FVSFrontendDataSet>
				orderByComparator)
		throws NoSuchFVSFrontendDataSetException;

	/**
	 * Returns the last fvs frontend data set in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set, or <code>null</code> if a matching fvs frontend data set could not be found
	 */
	public FVSFrontendDataSet fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<FVSFrontendDataSet>
			orderByComparator);

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
	public FVSFrontendDataSet[] findByUuid_C_PrevAndNext(
			long fvsFrontendDataSetId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<FVSFrontendDataSet>
				orderByComparator)
		throws NoSuchFVSFrontendDataSetException;

	/**
	 * Removes all the fvs frontend data sets where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of fvs frontend data sets where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching fvs frontend data sets
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the fvs frontend data sets where fdsName = &#63; and system = &#63;.
	 *
	 * @param fdsName the fds name
	 * @param system the system
	 * @return the matching fvs frontend data sets
	 */
	public java.util.List<FVSFrontendDataSet> findByF_S(
		String fdsName, boolean system);

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
	public java.util.List<FVSFrontendDataSet> findByF_S(
		String fdsName, boolean system, int start, int end);

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
	public java.util.List<FVSFrontendDataSet> findByF_S(
		String fdsName, boolean system, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FVSFrontendDataSet>
			orderByComparator);

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
	public java.util.List<FVSFrontendDataSet> findByF_S(
		String fdsName, boolean system, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FVSFrontendDataSet>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fvs frontend data set in the ordered set where fdsName = &#63; and system = &#63;.
	 *
	 * @param fdsName the fds name
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set
	 * @throws NoSuchFVSFrontendDataSetException if a matching fvs frontend data set could not be found
	 */
	public FVSFrontendDataSet findByF_S_First(
			String fdsName, boolean system,
			com.liferay.portal.kernel.util.OrderByComparator<FVSFrontendDataSet>
				orderByComparator)
		throws NoSuchFVSFrontendDataSetException;

	/**
	 * Returns the first fvs frontend data set in the ordered set where fdsName = &#63; and system = &#63;.
	 *
	 * @param fdsName the fds name
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set, or <code>null</code> if a matching fvs frontend data set could not be found
	 */
	public FVSFrontendDataSet fetchByF_S_First(
		String fdsName, boolean system,
		com.liferay.portal.kernel.util.OrderByComparator<FVSFrontendDataSet>
			orderByComparator);

	/**
	 * Returns the last fvs frontend data set in the ordered set where fdsName = &#63; and system = &#63;.
	 *
	 * @param fdsName the fds name
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set
	 * @throws NoSuchFVSFrontendDataSetException if a matching fvs frontend data set could not be found
	 */
	public FVSFrontendDataSet findByF_S_Last(
			String fdsName, boolean system,
			com.liferay.portal.kernel.util.OrderByComparator<FVSFrontendDataSet>
				orderByComparator)
		throws NoSuchFVSFrontendDataSetException;

	/**
	 * Returns the last fvs frontend data set in the ordered set where fdsName = &#63; and system = &#63;.
	 *
	 * @param fdsName the fds name
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set, or <code>null</code> if a matching fvs frontend data set could not be found
	 */
	public FVSFrontendDataSet fetchByF_S_Last(
		String fdsName, boolean system,
		com.liferay.portal.kernel.util.OrderByComparator<FVSFrontendDataSet>
			orderByComparator);

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
	public FVSFrontendDataSet[] findByF_S_PrevAndNext(
			long fvsFrontendDataSetId, String fdsName, boolean system,
			com.liferay.portal.kernel.util.OrderByComparator<FVSFrontendDataSet>
				orderByComparator)
		throws NoSuchFVSFrontendDataSetException;

	/**
	 * Removes all the fvs frontend data sets where fdsName = &#63; and system = &#63; from the database.
	 *
	 * @param fdsName the fds name
	 * @param system the system
	 */
	public void removeByF_S(String fdsName, boolean system);

	/**
	 * Returns the number of fvs frontend data sets where fdsName = &#63; and system = &#63;.
	 *
	 * @param fdsName the fds name
	 * @param system the system
	 * @return the number of matching fvs frontend data sets
	 */
	public int countByF_S(String fdsName, boolean system);

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
	public java.util.List<FVSFrontendDataSet> findByU_F_P_P_S(
		long userId, String fdsName, long plid, String portletId,
		boolean system);

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
	public java.util.List<FVSFrontendDataSet> findByU_F_P_P_S(
		long userId, String fdsName, long plid, String portletId,
		boolean system, int start, int end);

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
	public java.util.List<FVSFrontendDataSet> findByU_F_P_P_S(
		long userId, String fdsName, long plid, String portletId,
		boolean system, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FVSFrontendDataSet>
			orderByComparator);

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
	public java.util.List<FVSFrontendDataSet> findByU_F_P_P_S(
		long userId, String fdsName, long plid, String portletId,
		boolean system, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FVSFrontendDataSet>
			orderByComparator,
		boolean useFinderCache);

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
	public FVSFrontendDataSet findByU_F_P_P_S_First(
			long userId, String fdsName, long plid, String portletId,
			boolean system,
			com.liferay.portal.kernel.util.OrderByComparator<FVSFrontendDataSet>
				orderByComparator)
		throws NoSuchFVSFrontendDataSetException;

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
	public FVSFrontendDataSet fetchByU_F_P_P_S_First(
		long userId, String fdsName, long plid, String portletId,
		boolean system,
		com.liferay.portal.kernel.util.OrderByComparator<FVSFrontendDataSet>
			orderByComparator);

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
	public FVSFrontendDataSet findByU_F_P_P_S_Last(
			long userId, String fdsName, long plid, String portletId,
			boolean system,
			com.liferay.portal.kernel.util.OrderByComparator<FVSFrontendDataSet>
				orderByComparator)
		throws NoSuchFVSFrontendDataSetException;

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
	public FVSFrontendDataSet fetchByU_F_P_P_S_Last(
		long userId, String fdsName, long plid, String portletId,
		boolean system,
		com.liferay.portal.kernel.util.OrderByComparator<FVSFrontendDataSet>
			orderByComparator);

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
	public FVSFrontendDataSet[] findByU_F_P_P_S_PrevAndNext(
			long fvsFrontendDataSetId, long userId, String fdsName, long plid,
			String portletId, boolean system,
			com.liferay.portal.kernel.util.OrderByComparator<FVSFrontendDataSet>
				orderByComparator)
		throws NoSuchFVSFrontendDataSetException;

	/**
	 * Removes all the fvs frontend data sets where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63; and system = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param system the system
	 */
	public void removeByU_F_P_P_S(
		long userId, String fdsName, long plid, String portletId,
		boolean system);

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
	public int countByU_F_P_P_S(
		long userId, String fdsName, long plid, String portletId,
		boolean system);

	/**
	 * Caches the fvs frontend data set in the entity cache if it is enabled.
	 *
	 * @param fvsFrontendDataSet the fvs frontend data set
	 */
	public void cacheResult(FVSFrontendDataSet fvsFrontendDataSet);

	/**
	 * Caches the fvs frontend data sets in the entity cache if it is enabled.
	 *
	 * @param fvsFrontendDataSets the fvs frontend data sets
	 */
	public void cacheResult(
		java.util.List<FVSFrontendDataSet> fvsFrontendDataSets);

	/**
	 * Creates a new fvs frontend data set with the primary key. Does not add the fvs frontend data set to the database.
	 *
	 * @param fvsFrontendDataSetId the primary key for the new fvs frontend data set
	 * @return the new fvs frontend data set
	 */
	public FVSFrontendDataSet create(long fvsFrontendDataSetId);

	/**
	 * Removes the fvs frontend data set with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fvsFrontendDataSetId the primary key of the fvs frontend data set
	 * @return the fvs frontend data set that was removed
	 * @throws NoSuchFVSFrontendDataSetException if a fvs frontend data set with the primary key could not be found
	 */
	public FVSFrontendDataSet remove(long fvsFrontendDataSetId)
		throws NoSuchFVSFrontendDataSetException;

	public FVSFrontendDataSet updateImpl(FVSFrontendDataSet fvsFrontendDataSet);

	/**
	 * Returns the fvs frontend data set with the primary key or throws a <code>NoSuchFVSFrontendDataSetException</code> if it could not be found.
	 *
	 * @param fvsFrontendDataSetId the primary key of the fvs frontend data set
	 * @return the fvs frontend data set
	 * @throws NoSuchFVSFrontendDataSetException if a fvs frontend data set with the primary key could not be found
	 */
	public FVSFrontendDataSet findByPrimaryKey(long fvsFrontendDataSetId)
		throws NoSuchFVSFrontendDataSetException;

	/**
	 * Returns the fvs frontend data set with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fvsFrontendDataSetId the primary key of the fvs frontend data set
	 * @return the fvs frontend data set, or <code>null</code> if a fvs frontend data set with the primary key could not be found
	 */
	public FVSFrontendDataSet fetchByPrimaryKey(long fvsFrontendDataSetId);

	/**
	 * Returns all the fvs frontend data sets.
	 *
	 * @return the fvs frontend data sets
	 */
	public java.util.List<FVSFrontendDataSet> findAll();

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
	public java.util.List<FVSFrontendDataSet> findAll(int start, int end);

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
	public java.util.List<FVSFrontendDataSet> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FVSFrontendDataSet>
			orderByComparator);

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
	public java.util.List<FVSFrontendDataSet> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FVSFrontendDataSet>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the fvs frontend data sets from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of fvs frontend data sets.
	 *
	 * @return the number of fvs frontend data sets
	 */
	public int countAll();

}