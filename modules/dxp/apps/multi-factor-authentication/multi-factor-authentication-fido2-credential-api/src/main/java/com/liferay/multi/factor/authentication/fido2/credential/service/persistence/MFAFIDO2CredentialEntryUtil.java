/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.multi.factor.authentication.fido2.credential.service.persistence;

import com.liferay.multi.factor.authentication.fido2.credential.model.MFAFIDO2CredentialEntry;
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
 * The persistence utility for the mfafido2 credential entry service. This utility wraps <code>com.liferay.multi.factor.authentication.fido2.credential.service.persistence.impl.MFAFIDO2CredentialEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Arthur Chan
 * @see MFAFIDO2CredentialEntryPersistence
 * @generated
 */
public class MFAFIDO2CredentialEntryUtil {

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
		MFAFIDO2CredentialEntry mfafido2CredentialEntry) {

		getPersistence().clearCache(mfafido2CredentialEntry);
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
	public static Map<Serializable, MFAFIDO2CredentialEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<MFAFIDO2CredentialEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<MFAFIDO2CredentialEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<MFAFIDO2CredentialEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static MFAFIDO2CredentialEntry update(
		MFAFIDO2CredentialEntry mfafido2CredentialEntry) {

		return getPersistence().update(mfafido2CredentialEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static MFAFIDO2CredentialEntry update(
		MFAFIDO2CredentialEntry mfafido2CredentialEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(mfafido2CredentialEntry, serviceContext);
	}

	/**
	 * Returns the mfafido2 credential entry where userId = &#63; or throws a <code>NoSuchMFAFIDO2CredentialEntryException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @return the matching mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a matching mfafido2 credential entry could not be found
	 */
	public static MFAFIDO2CredentialEntry findByUserId(long userId)
		throws com.liferay.multi.factor.authentication.fido2.credential.
			exception.NoSuchMFAFIDO2CredentialEntryException {

		return getPersistence().findByUserId(userId);
	}

	/**
	 * Returns the mfafido2 credential entry where userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @return the matching mfafido2 credential entry, or <code>null</code> if a matching mfafido2 credential entry could not be found
	 */
	public static MFAFIDO2CredentialEntry fetchByUserId(long userId) {
		return getPersistence().fetchByUserId(userId);
	}

	/**
	 * Returns the mfafido2 credential entry where userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching mfafido2 credential entry, or <code>null</code> if a matching mfafido2 credential entry could not be found
	 */
	public static MFAFIDO2CredentialEntry fetchByUserId(
		long userId, boolean useFinderCache) {

		return getPersistence().fetchByUserId(userId, useFinderCache);
	}

	/**
	 * Removes the mfafido2 credential entry where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @return the mfafido2 credential entry that was removed
	 */
	public static MFAFIDO2CredentialEntry removeByUserId(long userId)
		throws com.liferay.multi.factor.authentication.fido2.credential.
			exception.NoSuchMFAFIDO2CredentialEntryException {

		return getPersistence().removeByUserId(userId);
	}

	/**
	 * Returns the number of mfafido2 credential entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching mfafido2 credential entries
	 */
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	 * Returns the mfafido2 credential entry where credentialId = &#63; or throws a <code>NoSuchMFAFIDO2CredentialEntryException</code> if it could not be found.
	 *
	 * @param credentialId the credential ID
	 * @return the matching mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a matching mfafido2 credential entry could not be found
	 */
	public static MFAFIDO2CredentialEntry findByCredentialId(
			String credentialId)
		throws com.liferay.multi.factor.authentication.fido2.credential.
			exception.NoSuchMFAFIDO2CredentialEntryException {

		return getPersistence().findByCredentialId(credentialId);
	}

	/**
	 * Returns the mfafido2 credential entry where credentialId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param credentialId the credential ID
	 * @return the matching mfafido2 credential entry, or <code>null</code> if a matching mfafido2 credential entry could not be found
	 */
	public static MFAFIDO2CredentialEntry fetchByCredentialId(
		String credentialId) {

		return getPersistence().fetchByCredentialId(credentialId);
	}

	/**
	 * Returns the mfafido2 credential entry where credentialId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param credentialId the credential ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching mfafido2 credential entry, or <code>null</code> if a matching mfafido2 credential entry could not be found
	 */
	public static MFAFIDO2CredentialEntry fetchByCredentialId(
		String credentialId, boolean useFinderCache) {

		return getPersistence().fetchByCredentialId(
			credentialId, useFinderCache);
	}

	/**
	 * Removes the mfafido2 credential entry where credentialId = &#63; from the database.
	 *
	 * @param credentialId the credential ID
	 * @return the mfafido2 credential entry that was removed
	 */
	public static MFAFIDO2CredentialEntry removeByCredentialId(
			String credentialId)
		throws com.liferay.multi.factor.authentication.fido2.credential.
			exception.NoSuchMFAFIDO2CredentialEntryException {

		return getPersistence().removeByCredentialId(credentialId);
	}

	/**
	 * Returns the number of mfafido2 credential entries where credentialId = &#63;.
	 *
	 * @param credentialId the credential ID
	 * @return the number of matching mfafido2 credential entries
	 */
	public static int countByCredentialId(String credentialId) {
		return getPersistence().countByCredentialId(credentialId);
	}

	/**
	 * Caches the mfafido2 credential entry in the entity cache if it is enabled.
	 *
	 * @param mfafido2CredentialEntry the mfafido2 credential entry
	 */
	public static void cacheResult(
		MFAFIDO2CredentialEntry mfafido2CredentialEntry) {

		getPersistence().cacheResult(mfafido2CredentialEntry);
	}

	/**
	 * Caches the mfafido2 credential entries in the entity cache if it is enabled.
	 *
	 * @param mfafido2CredentialEntries the mfafido2 credential entries
	 */
	public static void cacheResult(
		List<MFAFIDO2CredentialEntry> mfafido2CredentialEntries) {

		getPersistence().cacheResult(mfafido2CredentialEntries);
	}

	/**
	 * Creates a new mfafido2 credential entry with the primary key. Does not add the mfafido2 credential entry to the database.
	 *
	 * @param mfaFIDO2CredentialEntryId the primary key for the new mfafido2 credential entry
	 * @return the new mfafido2 credential entry
	 */
	public static MFAFIDO2CredentialEntry create(
		long mfaFIDO2CredentialEntryId) {

		return getPersistence().create(mfaFIDO2CredentialEntryId);
	}

	/**
	 * Removes the mfafido2 credential entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mfaFIDO2CredentialEntryId the primary key of the mfafido2 credential entry
	 * @return the mfafido2 credential entry that was removed
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a mfafido2 credential entry with the primary key could not be found
	 */
	public static MFAFIDO2CredentialEntry remove(long mfaFIDO2CredentialEntryId)
		throws com.liferay.multi.factor.authentication.fido2.credential.
			exception.NoSuchMFAFIDO2CredentialEntryException {

		return getPersistence().remove(mfaFIDO2CredentialEntryId);
	}

	public static MFAFIDO2CredentialEntry updateImpl(
		MFAFIDO2CredentialEntry mfafido2CredentialEntry) {

		return getPersistence().updateImpl(mfafido2CredentialEntry);
	}

	/**
	 * Returns the mfafido2 credential entry with the primary key or throws a <code>NoSuchMFAFIDO2CredentialEntryException</code> if it could not be found.
	 *
	 * @param mfaFIDO2CredentialEntryId the primary key of the mfafido2 credential entry
	 * @return the mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a mfafido2 credential entry with the primary key could not be found
	 */
	public static MFAFIDO2CredentialEntry findByPrimaryKey(
			long mfaFIDO2CredentialEntryId)
		throws com.liferay.multi.factor.authentication.fido2.credential.
			exception.NoSuchMFAFIDO2CredentialEntryException {

		return getPersistence().findByPrimaryKey(mfaFIDO2CredentialEntryId);
	}

	/**
	 * Returns the mfafido2 credential entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param mfaFIDO2CredentialEntryId the primary key of the mfafido2 credential entry
	 * @return the mfafido2 credential entry, or <code>null</code> if a mfafido2 credential entry with the primary key could not be found
	 */
	public static MFAFIDO2CredentialEntry fetchByPrimaryKey(
		long mfaFIDO2CredentialEntryId) {

		return getPersistence().fetchByPrimaryKey(mfaFIDO2CredentialEntryId);
	}

	/**
	 * Returns all the mfafido2 credential entries.
	 *
	 * @return the mfafido2 credential entries
	 */
	public static List<MFAFIDO2CredentialEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the mfafido2 credential entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFAFIDO2CredentialEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mfafido2 credential entries
	 * @param end the upper bound of the range of mfafido2 credential entries (not inclusive)
	 * @return the range of mfafido2 credential entries
	 */
	public static List<MFAFIDO2CredentialEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the mfafido2 credential entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFAFIDO2CredentialEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mfafido2 credential entries
	 * @param end the upper bound of the range of mfafido2 credential entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of mfafido2 credential entries
	 */
	public static List<MFAFIDO2CredentialEntry> findAll(
		int start, int end,
		OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the mfafido2 credential entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFAFIDO2CredentialEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mfafido2 credential entries
	 * @param end the upper bound of the range of mfafido2 credential entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of mfafido2 credential entries
	 */
	public static List<MFAFIDO2CredentialEntry> findAll(
		int start, int end,
		OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the mfafido2 credential entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of mfafido2 credential entries.
	 *
	 * @return the number of mfafido2 credential entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static MFAFIDO2CredentialEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<MFAFIDO2CredentialEntryPersistence, MFAFIDO2CredentialEntryPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			MFAFIDO2CredentialEntryPersistence.class);

		ServiceTracker
			<MFAFIDO2CredentialEntryPersistence,
			 MFAFIDO2CredentialEntryPersistence> serviceTracker =
				new ServiceTracker
					<MFAFIDO2CredentialEntryPersistence,
					 MFAFIDO2CredentialEntryPersistence>(
						 bundle.getBundleContext(),
						 MFAFIDO2CredentialEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}