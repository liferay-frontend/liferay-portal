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

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.remote.app.exception.NoSuchCustomElementPortletEntryException;
import com.liferay.remote.app.model.CustomElementPortletEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the custom element portlet entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CustomElementPortletEntryUtil
 * @generated
 */
@ProviderType
public interface CustomElementPortletEntryPersistence
	extends BasePersistence<CustomElementPortletEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CustomElementPortletEntryUtil} to access the custom element portlet entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the custom element portlet entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching custom element portlet entries
	 */
	public java.util.List<CustomElementPortletEntry> findByUuid(String uuid);

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
	public java.util.List<CustomElementPortletEntry> findByUuid(
		String uuid, int start, int end);

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
	public java.util.List<CustomElementPortletEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CustomElementPortletEntry> orderByComparator);

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
	public java.util.List<CustomElementPortletEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CustomElementPortletEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first custom element portlet entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom element portlet entry
	 * @throws NoSuchCustomElementPortletEntryException if a matching custom element portlet entry could not be found
	 */
	public CustomElementPortletEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CustomElementPortletEntry> orderByComparator)
		throws NoSuchCustomElementPortletEntryException;

	/**
	 * Returns the first custom element portlet entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom element portlet entry, or <code>null</code> if a matching custom element portlet entry could not be found
	 */
	public CustomElementPortletEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CustomElementPortletEntry> orderByComparator);

	/**
	 * Returns the last custom element portlet entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching custom element portlet entry
	 * @throws NoSuchCustomElementPortletEntryException if a matching custom element portlet entry could not be found
	 */
	public CustomElementPortletEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CustomElementPortletEntry> orderByComparator)
		throws NoSuchCustomElementPortletEntryException;

	/**
	 * Returns the last custom element portlet entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching custom element portlet entry, or <code>null</code> if a matching custom element portlet entry could not be found
	 */
	public CustomElementPortletEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CustomElementPortletEntry> orderByComparator);

	/**
	 * Returns the custom element portlet entries before and after the current custom element portlet entry in the ordered set where uuid = &#63;.
	 *
	 * @param customElementPortletEntryId the primary key of the current custom element portlet entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next custom element portlet entry
	 * @throws NoSuchCustomElementPortletEntryException if a custom element portlet entry with the primary key could not be found
	 */
	public CustomElementPortletEntry[] findByUuid_PrevAndNext(
			long customElementPortletEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CustomElementPortletEntry> orderByComparator)
		throws NoSuchCustomElementPortletEntryException;

	/**
	 * Removes all the custom element portlet entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of custom element portlet entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching custom element portlet entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the custom element portlet entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching custom element portlet entries
	 */
	public java.util.List<CustomElementPortletEntry> findByUuid_C(
		String uuid, long companyId);

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
	public java.util.List<CustomElementPortletEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

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
	public java.util.List<CustomElementPortletEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CustomElementPortletEntry> orderByComparator);

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
	public java.util.List<CustomElementPortletEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CustomElementPortletEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first custom element portlet entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom element portlet entry
	 * @throws NoSuchCustomElementPortletEntryException if a matching custom element portlet entry could not be found
	 */
	public CustomElementPortletEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CustomElementPortletEntry> orderByComparator)
		throws NoSuchCustomElementPortletEntryException;

	/**
	 * Returns the first custom element portlet entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom element portlet entry, or <code>null</code> if a matching custom element portlet entry could not be found
	 */
	public CustomElementPortletEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CustomElementPortletEntry> orderByComparator);

	/**
	 * Returns the last custom element portlet entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching custom element portlet entry
	 * @throws NoSuchCustomElementPortletEntryException if a matching custom element portlet entry could not be found
	 */
	public CustomElementPortletEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CustomElementPortletEntry> orderByComparator)
		throws NoSuchCustomElementPortletEntryException;

	/**
	 * Returns the last custom element portlet entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching custom element portlet entry, or <code>null</code> if a matching custom element portlet entry could not be found
	 */
	public CustomElementPortletEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CustomElementPortletEntry> orderByComparator);

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
	public CustomElementPortletEntry[] findByUuid_C_PrevAndNext(
			long customElementPortletEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CustomElementPortletEntry> orderByComparator)
		throws NoSuchCustomElementPortletEntryException;

	/**
	 * Removes all the custom element portlet entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of custom element portlet entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching custom element portlet entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Caches the custom element portlet entry in the entity cache if it is enabled.
	 *
	 * @param customElementPortletEntry the custom element portlet entry
	 */
	public void cacheResult(
		CustomElementPortletEntry customElementPortletEntry);

	/**
	 * Caches the custom element portlet entries in the entity cache if it is enabled.
	 *
	 * @param customElementPortletEntries the custom element portlet entries
	 */
	public void cacheResult(
		java.util.List<CustomElementPortletEntry> customElementPortletEntries);

	/**
	 * Creates a new custom element portlet entry with the primary key. Does not add the custom element portlet entry to the database.
	 *
	 * @param customElementPortletEntryId the primary key for the new custom element portlet entry
	 * @return the new custom element portlet entry
	 */
	public CustomElementPortletEntry create(long customElementPortletEntryId);

	/**
	 * Removes the custom element portlet entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param customElementPortletEntryId the primary key of the custom element portlet entry
	 * @return the custom element portlet entry that was removed
	 * @throws NoSuchCustomElementPortletEntryException if a custom element portlet entry with the primary key could not be found
	 */
	public CustomElementPortletEntry remove(long customElementPortletEntryId)
		throws NoSuchCustomElementPortletEntryException;

	public CustomElementPortletEntry updateImpl(
		CustomElementPortletEntry customElementPortletEntry);

	/**
	 * Returns the custom element portlet entry with the primary key or throws a <code>NoSuchCustomElementPortletEntryException</code> if it could not be found.
	 *
	 * @param customElementPortletEntryId the primary key of the custom element portlet entry
	 * @return the custom element portlet entry
	 * @throws NoSuchCustomElementPortletEntryException if a custom element portlet entry with the primary key could not be found
	 */
	public CustomElementPortletEntry findByPrimaryKey(
			long customElementPortletEntryId)
		throws NoSuchCustomElementPortletEntryException;

	/**
	 * Returns the custom element portlet entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param customElementPortletEntryId the primary key of the custom element portlet entry
	 * @return the custom element portlet entry, or <code>null</code> if a custom element portlet entry with the primary key could not be found
	 */
	public CustomElementPortletEntry fetchByPrimaryKey(
		long customElementPortletEntryId);

	/**
	 * Returns all the custom element portlet entries.
	 *
	 * @return the custom element portlet entries
	 */
	public java.util.List<CustomElementPortletEntry> findAll();

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
	public java.util.List<CustomElementPortletEntry> findAll(
		int start, int end);

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
	public java.util.List<CustomElementPortletEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CustomElementPortletEntry> orderByComparator);

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
	public java.util.List<CustomElementPortletEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CustomElementPortletEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the custom element portlet entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of custom element portlet entries.
	 *
	 * @return the number of custom element portlet entries
	 */
	public int countAll();

}