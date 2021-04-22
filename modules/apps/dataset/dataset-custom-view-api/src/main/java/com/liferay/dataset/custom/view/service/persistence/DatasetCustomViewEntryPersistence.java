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

import com.liferay.dataset.custom.view.exception.NoSuchEntryException;
import com.liferay.dataset.custom.view.model.DatasetCustomViewEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the dataset custom view entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DatasetCustomViewEntryUtil
 * @generated
 */
@ProviderType
public interface DatasetCustomViewEntryPersistence
	extends BasePersistence<DatasetCustomViewEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DatasetCustomViewEntryUtil} to access the dataset custom view entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the dataset custom view entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching dataset custom view entries
	 */
	public java.util.List<DatasetCustomViewEntry> findByUuid(String uuid);

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
	public java.util.List<DatasetCustomViewEntry> findByUuid(
		String uuid, int start, int end);

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
	public java.util.List<DatasetCustomViewEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DatasetCustomViewEntry>
			orderByComparator);

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
	public java.util.List<DatasetCustomViewEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DatasetCustomViewEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first dataset custom view entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dataset custom view entry
	 * @throws NoSuchEntryException if a matching dataset custom view entry could not be found
	 */
	public DatasetCustomViewEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<DatasetCustomViewEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first dataset custom view entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dataset custom view entry, or <code>null</code> if a matching dataset custom view entry could not be found
	 */
	public DatasetCustomViewEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DatasetCustomViewEntry>
			orderByComparator);

	/**
	 * Returns the last dataset custom view entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dataset custom view entry
	 * @throws NoSuchEntryException if a matching dataset custom view entry could not be found
	 */
	public DatasetCustomViewEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<DatasetCustomViewEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last dataset custom view entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dataset custom view entry, or <code>null</code> if a matching dataset custom view entry could not be found
	 */
	public DatasetCustomViewEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DatasetCustomViewEntry>
			orderByComparator);

	/**
	 * Returns the dataset custom view entries before and after the current dataset custom view entry in the ordered set where uuid = &#63;.
	 *
	 * @param datasetCustomViewEntryId the primary key of the current dataset custom view entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dataset custom view entry
	 * @throws NoSuchEntryException if a dataset custom view entry with the primary key could not be found
	 */
	public DatasetCustomViewEntry[] findByUuid_PrevAndNext(
			long datasetCustomViewEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<DatasetCustomViewEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the dataset custom view entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of dataset custom view entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching dataset custom view entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the dataset custom view entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching dataset custom view entries
	 */
	public java.util.List<DatasetCustomViewEntry> findByUuid_C(
		String uuid, long companyId);

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
	public java.util.List<DatasetCustomViewEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

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
	public java.util.List<DatasetCustomViewEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DatasetCustomViewEntry>
			orderByComparator);

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
	public java.util.List<DatasetCustomViewEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DatasetCustomViewEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first dataset custom view entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dataset custom view entry
	 * @throws NoSuchEntryException if a matching dataset custom view entry could not be found
	 */
	public DatasetCustomViewEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<DatasetCustomViewEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first dataset custom view entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dataset custom view entry, or <code>null</code> if a matching dataset custom view entry could not be found
	 */
	public DatasetCustomViewEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DatasetCustomViewEntry>
			orderByComparator);

	/**
	 * Returns the last dataset custom view entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dataset custom view entry
	 * @throws NoSuchEntryException if a matching dataset custom view entry could not be found
	 */
	public DatasetCustomViewEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<DatasetCustomViewEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last dataset custom view entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dataset custom view entry, or <code>null</code> if a matching dataset custom view entry could not be found
	 */
	public DatasetCustomViewEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DatasetCustomViewEntry>
			orderByComparator);

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
	public DatasetCustomViewEntry[] findByUuid_C_PrevAndNext(
			long datasetCustomViewEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<DatasetCustomViewEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the dataset custom view entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of dataset custom view entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching dataset custom view entries
	 */
	public int countByUuid_C(String uuid, long companyId);

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
	public DatasetCustomViewEntry findByU_D_P_P(
			long userId, String datasetDisplayId, long plid, String portletId)
		throws NoSuchEntryException;

	/**
	 * Returns the dataset custom view entry where userId = &#63; and datasetDisplayId = &#63; and plid = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param datasetDisplayId the dataset display ID
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the matching dataset custom view entry, or <code>null</code> if a matching dataset custom view entry could not be found
	 */
	public DatasetCustomViewEntry fetchByU_D_P_P(
		long userId, String datasetDisplayId, long plid, String portletId);

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
	public DatasetCustomViewEntry fetchByU_D_P_P(
		long userId, String datasetDisplayId, long plid, String portletId,
		boolean useFinderCache);

	/**
	 * Removes the dataset custom view entry where userId = &#63; and datasetDisplayId = &#63; and plid = &#63; and portletId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param datasetDisplayId the dataset display ID
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the dataset custom view entry that was removed
	 */
	public DatasetCustomViewEntry removeByU_D_P_P(
			long userId, String datasetDisplayId, long plid, String portletId)
		throws NoSuchEntryException;

	/**
	 * Returns the number of dataset custom view entries where userId = &#63; and datasetDisplayId = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param userId the user ID
	 * @param datasetDisplayId the dataset display ID
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the number of matching dataset custom view entries
	 */
	public int countByU_D_P_P(
		long userId, String datasetDisplayId, long plid, String portletId);

	/**
	 * Caches the dataset custom view entry in the entity cache if it is enabled.
	 *
	 * @param datasetCustomViewEntry the dataset custom view entry
	 */
	public void cacheResult(DatasetCustomViewEntry datasetCustomViewEntry);

	/**
	 * Caches the dataset custom view entries in the entity cache if it is enabled.
	 *
	 * @param datasetCustomViewEntries the dataset custom view entries
	 */
	public void cacheResult(
		java.util.List<DatasetCustomViewEntry> datasetCustomViewEntries);

	/**
	 * Creates a new dataset custom view entry with the primary key. Does not add the dataset custom view entry to the database.
	 *
	 * @param datasetCustomViewEntryId the primary key for the new dataset custom view entry
	 * @return the new dataset custom view entry
	 */
	public DatasetCustomViewEntry create(long datasetCustomViewEntryId);

	/**
	 * Removes the dataset custom view entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param datasetCustomViewEntryId the primary key of the dataset custom view entry
	 * @return the dataset custom view entry that was removed
	 * @throws NoSuchEntryException if a dataset custom view entry with the primary key could not be found
	 */
	public DatasetCustomViewEntry remove(long datasetCustomViewEntryId)
		throws NoSuchEntryException;

	public DatasetCustomViewEntry updateImpl(
		DatasetCustomViewEntry datasetCustomViewEntry);

	/**
	 * Returns the dataset custom view entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param datasetCustomViewEntryId the primary key of the dataset custom view entry
	 * @return the dataset custom view entry
	 * @throws NoSuchEntryException if a dataset custom view entry with the primary key could not be found
	 */
	public DatasetCustomViewEntry findByPrimaryKey(
			long datasetCustomViewEntryId)
		throws NoSuchEntryException;

	/**
	 * Returns the dataset custom view entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param datasetCustomViewEntryId the primary key of the dataset custom view entry
	 * @return the dataset custom view entry, or <code>null</code> if a dataset custom view entry with the primary key could not be found
	 */
	public DatasetCustomViewEntry fetchByPrimaryKey(
		long datasetCustomViewEntryId);

	/**
	 * Returns all the dataset custom view entries.
	 *
	 * @return the dataset custom view entries
	 */
	public java.util.List<DatasetCustomViewEntry> findAll();

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
	public java.util.List<DatasetCustomViewEntry> findAll(int start, int end);

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
	public java.util.List<DatasetCustomViewEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DatasetCustomViewEntry>
			orderByComparator);

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
	public java.util.List<DatasetCustomViewEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DatasetCustomViewEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the dataset custom view entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of dataset custom view entries.
	 *
	 * @return the number of dataset custom view entries
	 */
	public int countAll();

}