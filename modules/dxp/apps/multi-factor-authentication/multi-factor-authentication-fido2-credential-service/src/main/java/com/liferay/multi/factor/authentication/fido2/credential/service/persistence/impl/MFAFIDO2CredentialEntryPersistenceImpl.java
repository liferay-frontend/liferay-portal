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

package com.liferay.multi.factor.authentication.fido2.credential.service.persistence.impl;

import com.liferay.multi.factor.authentication.fido2.credential.exception.NoSuchMFAFIDO2CredentialEntryException;
import com.liferay.multi.factor.authentication.fido2.credential.model.MFAFIDO2CredentialEntry;
import com.liferay.multi.factor.authentication.fido2.credential.model.MFAFIDO2CredentialEntryTable;
import com.liferay.multi.factor.authentication.fido2.credential.model.impl.MFAFIDO2CredentialEntryImpl;
import com.liferay.multi.factor.authentication.fido2.credential.model.impl.MFAFIDO2CredentialEntryModelImpl;
import com.liferay.multi.factor.authentication.fido2.credential.service.persistence.MFAFIDO2CredentialEntryPersistence;
import com.liferay.multi.factor.authentication.fido2.credential.service.persistence.impl.constants.MFAFIDOTwoCredentialPersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashSet;
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
 * The persistence implementation for the mfafido2 credential entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Arthur Chan
 * @generated
 */
@Component(service = MFAFIDO2CredentialEntryPersistence.class)
public class MFAFIDO2CredentialEntryPersistenceImpl
	extends BasePersistenceImpl<MFAFIDO2CredentialEntry>
	implements MFAFIDO2CredentialEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>MFAFIDO2CredentialEntryUtil</code> to access the mfafido2 credential entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		MFAFIDO2CredentialEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByUserId;
	private FinderPath _finderPathCountByUserId;

	/**
	 * Returns the mfafido2 credential entry where userId = &#63; or throws a <code>NoSuchMFAFIDO2CredentialEntryException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @return the matching mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a matching mfafido2 credential entry could not be found
	 */
	@Override
	public MFAFIDO2CredentialEntry findByUserId(long userId)
		throws NoSuchMFAFIDO2CredentialEntryException {

		MFAFIDO2CredentialEntry mfafido2CredentialEntry = fetchByUserId(userId);

		if (mfafido2CredentialEntry == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("userId=");
			sb.append(userId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchMFAFIDO2CredentialEntryException(sb.toString());
		}

		return mfafido2CredentialEntry;
	}

	/**
	 * Returns the mfafido2 credential entry where userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @return the matching mfafido2 credential entry, or <code>null</code> if a matching mfafido2 credential entry could not be found
	 */
	@Override
	public MFAFIDO2CredentialEntry fetchByUserId(long userId) {
		return fetchByUserId(userId, true);
	}

	/**
	 * Returns the mfafido2 credential entry where userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching mfafido2 credential entry, or <code>null</code> if a matching mfafido2 credential entry could not be found
	 */
	@Override
	public MFAFIDO2CredentialEntry fetchByUserId(
		long userId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {userId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUserId, finderArgs, this);
		}

		if (result instanceof MFAFIDO2CredentialEntry) {
			MFAFIDO2CredentialEntry mfafido2CredentialEntry =
				(MFAFIDO2CredentialEntry)result;

			if (userId != mfafido2CredentialEntry.getUserId()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_MFAFIDO2CREDENTIALENTRY_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				List<MFAFIDO2CredentialEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUserId, finderArgs, list);
					}
				}
				else {
					MFAFIDO2CredentialEntry mfafido2CredentialEntry = list.get(
						0);

					result = mfafido2CredentialEntry;

					cacheResult(mfafido2CredentialEntry);
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
			return (MFAFIDO2CredentialEntry)result;
		}
	}

	/**
	 * Removes the mfafido2 credential entry where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @return the mfafido2 credential entry that was removed
	 */
	@Override
	public MFAFIDO2CredentialEntry removeByUserId(long userId)
		throws NoSuchMFAFIDO2CredentialEntryException {

		MFAFIDO2CredentialEntry mfafido2CredentialEntry = findByUserId(userId);

		return remove(mfafido2CredentialEntry);
	}

	/**
	 * Returns the number of mfafido2 credential entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching mfafido2 credential entries
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_MFAFIDO2CREDENTIALENTRY_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

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

	private static final String _FINDER_COLUMN_USERID_USERID_2 =
		"mfafido2CredentialEntry.userId = ?";

	private FinderPath _finderPathFetchByCredentialId;
	private FinderPath _finderPathCountByCredentialId;

	/**
	 * Returns the mfafido2 credential entry where credentialId = &#63; or throws a <code>NoSuchMFAFIDO2CredentialEntryException</code> if it could not be found.
	 *
	 * @param credentialId the credential ID
	 * @return the matching mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a matching mfafido2 credential entry could not be found
	 */
	@Override
	public MFAFIDO2CredentialEntry findByCredentialId(String credentialId)
		throws NoSuchMFAFIDO2CredentialEntryException {

		MFAFIDO2CredentialEntry mfafido2CredentialEntry = fetchByCredentialId(
			credentialId);

		if (mfafido2CredentialEntry == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("credentialId=");
			sb.append(credentialId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchMFAFIDO2CredentialEntryException(sb.toString());
		}

		return mfafido2CredentialEntry;
	}

	/**
	 * Returns the mfafido2 credential entry where credentialId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param credentialId the credential ID
	 * @return the matching mfafido2 credential entry, or <code>null</code> if a matching mfafido2 credential entry could not be found
	 */
	@Override
	public MFAFIDO2CredentialEntry fetchByCredentialId(String credentialId) {
		return fetchByCredentialId(credentialId, true);
	}

	/**
	 * Returns the mfafido2 credential entry where credentialId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param credentialId the credential ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching mfafido2 credential entry, or <code>null</code> if a matching mfafido2 credential entry could not be found
	 */
	@Override
	public MFAFIDO2CredentialEntry fetchByCredentialId(
		String credentialId, boolean useFinderCache) {

		credentialId = Objects.toString(credentialId, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {credentialId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByCredentialId, finderArgs, this);
		}

		if (result instanceof MFAFIDO2CredentialEntry) {
			MFAFIDO2CredentialEntry mfafido2CredentialEntry =
				(MFAFIDO2CredentialEntry)result;

			if (!Objects.equals(
					credentialId, mfafido2CredentialEntry.getCredentialId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_MFAFIDO2CREDENTIALENTRY_WHERE);

			boolean bindCredentialId = false;

			if (credentialId.isEmpty()) {
				sb.append(_FINDER_COLUMN_CREDENTIALID_CREDENTIALID_3);
			}
			else {
				bindCredentialId = true;

				sb.append(_FINDER_COLUMN_CREDENTIALID_CREDENTIALID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindCredentialId) {
					queryPos.add(credentialId);
				}

				List<MFAFIDO2CredentialEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByCredentialId, finderArgs, list);
					}
				}
				else {
					MFAFIDO2CredentialEntry mfafido2CredentialEntry = list.get(
						0);

					result = mfafido2CredentialEntry;

					cacheResult(mfafido2CredentialEntry);
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
			return (MFAFIDO2CredentialEntry)result;
		}
	}

	/**
	 * Removes the mfafido2 credential entry where credentialId = &#63; from the database.
	 *
	 * @param credentialId the credential ID
	 * @return the mfafido2 credential entry that was removed
	 */
	@Override
	public MFAFIDO2CredentialEntry removeByCredentialId(String credentialId)
		throws NoSuchMFAFIDO2CredentialEntryException {

		MFAFIDO2CredentialEntry mfafido2CredentialEntry = findByCredentialId(
			credentialId);

		return remove(mfafido2CredentialEntry);
	}

	/**
	 * Returns the number of mfafido2 credential entries where credentialId = &#63;.
	 *
	 * @param credentialId the credential ID
	 * @return the number of matching mfafido2 credential entries
	 */
	@Override
	public int countByCredentialId(String credentialId) {
		credentialId = Objects.toString(credentialId, "");

		FinderPath finderPath = _finderPathCountByCredentialId;

		Object[] finderArgs = new Object[] {credentialId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_MFAFIDO2CREDENTIALENTRY_WHERE);

			boolean bindCredentialId = false;

			if (credentialId.isEmpty()) {
				sb.append(_FINDER_COLUMN_CREDENTIALID_CREDENTIALID_3);
			}
			else {
				bindCredentialId = true;

				sb.append(_FINDER_COLUMN_CREDENTIALID_CREDENTIALID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindCredentialId) {
					queryPos.add(credentialId);
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

	private static final String _FINDER_COLUMN_CREDENTIALID_CREDENTIALID_2 =
		"mfafido2CredentialEntry.credentialId = ?";

	private static final String _FINDER_COLUMN_CREDENTIALID_CREDENTIALID_3 =
		"(mfafido2CredentialEntry.credentialId IS NULL OR mfafido2CredentialEntry.credentialId = '')";

	public MFAFIDO2CredentialEntryPersistenceImpl() {
		setModelClass(MFAFIDO2CredentialEntry.class);

		setModelImplClass(MFAFIDO2CredentialEntryImpl.class);
		setModelPKClass(long.class);

		setTable(MFAFIDO2CredentialEntryTable.INSTANCE);
	}

	/**
	 * Caches the mfafido2 credential entry in the entity cache if it is enabled.
	 *
	 * @param mfafido2CredentialEntry the mfafido2 credential entry
	 */
	@Override
	public void cacheResult(MFAFIDO2CredentialEntry mfafido2CredentialEntry) {
		entityCache.putResult(
			MFAFIDO2CredentialEntryImpl.class,
			mfafido2CredentialEntry.getPrimaryKey(), mfafido2CredentialEntry);

		finderCache.putResult(
			_finderPathFetchByUserId,
			new Object[] {mfafido2CredentialEntry.getUserId()},
			mfafido2CredentialEntry);

		finderCache.putResult(
			_finderPathFetchByCredentialId,
			new Object[] {mfafido2CredentialEntry.getCredentialId()},
			mfafido2CredentialEntry);
	}

	/**
	 * Caches the mfafido2 credential entries in the entity cache if it is enabled.
	 *
	 * @param mfafido2CredentialEntries the mfafido2 credential entries
	 */
	@Override
	public void cacheResult(
		List<MFAFIDO2CredentialEntry> mfafido2CredentialEntries) {

		for (MFAFIDO2CredentialEntry mfafido2CredentialEntry :
				mfafido2CredentialEntries) {

			if (entityCache.getResult(
					MFAFIDO2CredentialEntryImpl.class,
					mfafido2CredentialEntry.getPrimaryKey()) == null) {

				cacheResult(mfafido2CredentialEntry);
			}
		}
	}

	/**
	 * Clears the cache for all mfafido2 credential entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(MFAFIDO2CredentialEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the mfafido2 credential entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MFAFIDO2CredentialEntry mfafido2CredentialEntry) {
		entityCache.removeResult(
			MFAFIDO2CredentialEntryImpl.class, mfafido2CredentialEntry);
	}

	@Override
	public void clearCache(
		List<MFAFIDO2CredentialEntry> mfafido2CredentialEntries) {

		for (MFAFIDO2CredentialEntry mfafido2CredentialEntry :
				mfafido2CredentialEntries) {

			entityCache.removeResult(
				MFAFIDO2CredentialEntryImpl.class, mfafido2CredentialEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				MFAFIDO2CredentialEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		MFAFIDO2CredentialEntryModelImpl mfafido2CredentialEntryModelImpl) {

		Object[] args = new Object[] {
			mfafido2CredentialEntryModelImpl.getUserId()
		};

		finderCache.putResult(
			_finderPathCountByUserId, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUserId, args, mfafido2CredentialEntryModelImpl,
			false);

		args = new Object[] {
			mfafido2CredentialEntryModelImpl.getCredentialId()
		};

		finderCache.putResult(
			_finderPathCountByCredentialId, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByCredentialId, args,
			mfafido2CredentialEntryModelImpl, false);
	}

	/**
	 * Creates a new mfafido2 credential entry with the primary key. Does not add the mfafido2 credential entry to the database.
	 *
	 * @param mfaFIDO2CredentialEntryId the primary key for the new mfafido2 credential entry
	 * @return the new mfafido2 credential entry
	 */
	@Override
	public MFAFIDO2CredentialEntry create(long mfaFIDO2CredentialEntryId) {
		MFAFIDO2CredentialEntry mfafido2CredentialEntry =
			new MFAFIDO2CredentialEntryImpl();

		mfafido2CredentialEntry.setNew(true);
		mfafido2CredentialEntry.setPrimaryKey(mfaFIDO2CredentialEntryId);

		mfafido2CredentialEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return mfafido2CredentialEntry;
	}

	/**
	 * Removes the mfafido2 credential entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mfaFIDO2CredentialEntryId the primary key of the mfafido2 credential entry
	 * @return the mfafido2 credential entry that was removed
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a mfafido2 credential entry with the primary key could not be found
	 */
	@Override
	public MFAFIDO2CredentialEntry remove(long mfaFIDO2CredentialEntryId)
		throws NoSuchMFAFIDO2CredentialEntryException {

		return remove((Serializable)mfaFIDO2CredentialEntryId);
	}

	/**
	 * Removes the mfafido2 credential entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the mfafido2 credential entry
	 * @return the mfafido2 credential entry that was removed
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a mfafido2 credential entry with the primary key could not be found
	 */
	@Override
	public MFAFIDO2CredentialEntry remove(Serializable primaryKey)
		throws NoSuchMFAFIDO2CredentialEntryException {

		Session session = null;

		try {
			session = openSession();

			MFAFIDO2CredentialEntry mfafido2CredentialEntry =
				(MFAFIDO2CredentialEntry)session.get(
					MFAFIDO2CredentialEntryImpl.class, primaryKey);

			if (mfafido2CredentialEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchMFAFIDO2CredentialEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(mfafido2CredentialEntry);
		}
		catch (NoSuchMFAFIDO2CredentialEntryException noSuchEntityException) {
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
	protected MFAFIDO2CredentialEntry removeImpl(
		MFAFIDO2CredentialEntry mfafido2CredentialEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(mfafido2CredentialEntry)) {
				mfafido2CredentialEntry = (MFAFIDO2CredentialEntry)session.get(
					MFAFIDO2CredentialEntryImpl.class,
					mfafido2CredentialEntry.getPrimaryKeyObj());
			}

			if (mfafido2CredentialEntry != null) {
				session.delete(mfafido2CredentialEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (mfafido2CredentialEntry != null) {
			clearCache(mfafido2CredentialEntry);
		}

		return mfafido2CredentialEntry;
	}

	@Override
	public MFAFIDO2CredentialEntry updateImpl(
		MFAFIDO2CredentialEntry mfafido2CredentialEntry) {

		boolean isNew = mfafido2CredentialEntry.isNew();

		if (!(mfafido2CredentialEntry instanceof
				MFAFIDO2CredentialEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(mfafido2CredentialEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					mfafido2CredentialEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in mfafido2CredentialEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom MFAFIDO2CredentialEntry implementation " +
					mfafido2CredentialEntry.getClass());
		}

		MFAFIDO2CredentialEntryModelImpl mfafido2CredentialEntryModelImpl =
			(MFAFIDO2CredentialEntryModelImpl)mfafido2CredentialEntry;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (mfafido2CredentialEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				mfafido2CredentialEntry.setCreateDate(now);
			}
			else {
				mfafido2CredentialEntry.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!mfafido2CredentialEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				mfafido2CredentialEntry.setModifiedDate(now);
			}
			else {
				mfafido2CredentialEntry.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(mfafido2CredentialEntry);
			}
			else {
				mfafido2CredentialEntry =
					(MFAFIDO2CredentialEntry)session.merge(
						mfafido2CredentialEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			MFAFIDO2CredentialEntryImpl.class, mfafido2CredentialEntryModelImpl,
			false, true);

		cacheUniqueFindersCache(mfafido2CredentialEntryModelImpl);

		if (isNew) {
			mfafido2CredentialEntry.setNew(false);
		}

		mfafido2CredentialEntry.resetOriginalValues();

		return mfafido2CredentialEntry;
	}

	/**
	 * Returns the mfafido2 credential entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the mfafido2 credential entry
	 * @return the mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a mfafido2 credential entry with the primary key could not be found
	 */
	@Override
	public MFAFIDO2CredentialEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchMFAFIDO2CredentialEntryException {

		MFAFIDO2CredentialEntry mfafido2CredentialEntry = fetchByPrimaryKey(
			primaryKey);

		if (mfafido2CredentialEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchMFAFIDO2CredentialEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return mfafido2CredentialEntry;
	}

	/**
	 * Returns the mfafido2 credential entry with the primary key or throws a <code>NoSuchMFAFIDO2CredentialEntryException</code> if it could not be found.
	 *
	 * @param mfaFIDO2CredentialEntryId the primary key of the mfafido2 credential entry
	 * @return the mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a mfafido2 credential entry with the primary key could not be found
	 */
	@Override
	public MFAFIDO2CredentialEntry findByPrimaryKey(
			long mfaFIDO2CredentialEntryId)
		throws NoSuchMFAFIDO2CredentialEntryException {

		return findByPrimaryKey((Serializable)mfaFIDO2CredentialEntryId);
	}

	/**
	 * Returns the mfafido2 credential entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param mfaFIDO2CredentialEntryId the primary key of the mfafido2 credential entry
	 * @return the mfafido2 credential entry, or <code>null</code> if a mfafido2 credential entry with the primary key could not be found
	 */
	@Override
	public MFAFIDO2CredentialEntry fetchByPrimaryKey(
		long mfaFIDO2CredentialEntryId) {

		return fetchByPrimaryKey((Serializable)mfaFIDO2CredentialEntryId);
	}

	/**
	 * Returns all the mfafido2 credential entries.
	 *
	 * @return the mfafido2 credential entries
	 */
	@Override
	public List<MFAFIDO2CredentialEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<MFAFIDO2CredentialEntry> findAll(int start, int end) {
		return findAll(start, end, null);
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
	@Override
	public List<MFAFIDO2CredentialEntry> findAll(
		int start, int end,
		OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
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
	@Override
	public List<MFAFIDO2CredentialEntry> findAll(
		int start, int end,
		OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator,
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

		List<MFAFIDO2CredentialEntry> list = null;

		if (useFinderCache) {
			list = (List<MFAFIDO2CredentialEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_MFAFIDO2CREDENTIALENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_MFAFIDO2CREDENTIALENTRY;

				sql = sql.concat(
					MFAFIDO2CredentialEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<MFAFIDO2CredentialEntry>)QueryUtil.list(
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
	 * Removes all the mfafido2 credential entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (MFAFIDO2CredentialEntry mfafido2CredentialEntry : findAll()) {
			remove(mfafido2CredentialEntry);
		}
	}

	/**
	 * Returns the number of mfafido2 credential entries.
	 *
	 * @return the number of mfafido2 credential entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_MFAFIDO2CREDENTIALENTRY);

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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "mfaFIDO2CredentialEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_MFAFIDO2CREDENTIALENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return MFAFIDO2CredentialEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the mfafido2 credential entry persistence.
	 */
	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new MFAFIDO2CredentialEntryModelArgumentsResolver(),
			MapUtil.singletonDictionary(
				"model.class.name", MFAFIDO2CredentialEntry.class.getName()));

		_finderPathWithPaginationFindAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathFetchByUserId = _createFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"}, true);

		_finderPathCountByUserId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"},
			false);

		_finderPathFetchByCredentialId = _createFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCredentialId",
			new String[] {String.class.getName()},
			new String[] {"credentialId"}, true);

		_finderPathCountByCredentialId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCredentialId",
			new String[] {String.class.getName()},
			new String[] {"credentialId"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(MFAFIDO2CredentialEntryImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();

		for (ServiceRegistration<FinderPath> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	@Override
	@Reference(
		target = MFAFIDOTwoCredentialPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = MFAFIDOTwoCredentialPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = MFAFIDOTwoCredentialPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_MFAFIDO2CREDENTIALENTRY =
		"SELECT mfafido2CredentialEntry FROM MFAFIDO2CredentialEntry mfafido2CredentialEntry";

	private static final String _SQL_SELECT_MFAFIDO2CREDENTIALENTRY_WHERE =
		"SELECT mfafido2CredentialEntry FROM MFAFIDO2CredentialEntry mfafido2CredentialEntry WHERE ";

	private static final String _SQL_COUNT_MFAFIDO2CREDENTIALENTRY =
		"SELECT COUNT(mfafido2CredentialEntry) FROM MFAFIDO2CredentialEntry mfafido2CredentialEntry";

	private static final String _SQL_COUNT_MFAFIDO2CREDENTIALENTRY_WHERE =
		"SELECT COUNT(mfafido2CredentialEntry) FROM MFAFIDO2CredentialEntry mfafido2CredentialEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"mfafido2CredentialEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No MFAFIDO2CredentialEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No MFAFIDO2CredentialEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		MFAFIDO2CredentialEntryPersistenceImpl.class);

	static {
		try {
			Class.forName(
				MFAFIDOTwoCredentialPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

	private FinderPath _createFinderPath(
		String cacheName, String methodName, String[] params,
		String[] columnNames, boolean baseModelResult) {

		FinderPath finderPath = new FinderPath(
			cacheName, methodName, params, columnNames, baseModelResult);

		if (!cacheName.equals(FINDER_CLASS_NAME_LIST_WITH_PAGINATION)) {
			_serviceRegistrations.add(
				_bundleContext.registerService(
					FinderPath.class, finderPath,
					MapUtil.singletonDictionary("cache.name", cacheName)));
		}

		return finderPath;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;
	private Set<ServiceRegistration<FinderPath>> _serviceRegistrations =
		new HashSet<>();

	private static class MFAFIDO2CredentialEntryModelArgumentsResolver
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

			MFAFIDO2CredentialEntryModelImpl mfafido2CredentialEntryModelImpl =
				(MFAFIDO2CredentialEntryModelImpl)baseModel;

			long columnBitmask =
				mfafido2CredentialEntryModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					mfafido2CredentialEntryModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						mfafido2CredentialEntryModelImpl.getColumnBitmask(
							columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					mfafido2CredentialEntryModelImpl, columnNames, original);
			}

			return null;
		}

		private Object[] _getValue(
			MFAFIDO2CredentialEntryModelImpl mfafido2CredentialEntryModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						mfafido2CredentialEntryModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] =
						mfafido2CredentialEntryModelImpl.getColumnValue(
							columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}