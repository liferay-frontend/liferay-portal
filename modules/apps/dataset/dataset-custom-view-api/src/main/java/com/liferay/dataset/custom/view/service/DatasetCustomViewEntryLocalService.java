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

package com.liferay.dataset.custom.view.service;

import com.liferay.dataset.custom.view.model.DatasetCustomViewEntry;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for DatasetCustomViewEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see DatasetCustomViewEntryLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface DatasetCustomViewEntryLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.dataset.custom.view.service.impl.DatasetCustomViewEntryLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the dataset custom view entry local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link DatasetCustomViewEntryLocalServiceUtil} if injection and service tracking are not available.
	 */

	/**
	 * Adds the dataset custom view entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DatasetCustomViewEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param datasetCustomViewEntry the dataset custom view entry
	 * @return the dataset custom view entry that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public DatasetCustomViewEntry addDatasetCustomViewEntry(
		DatasetCustomViewEntry datasetCustomViewEntry);

	public DatasetCustomViewEntry createDatasetCustomViewEntry();

	/**
	 * Creates a new dataset custom view entry with the primary key. Does not add the dataset custom view entry to the database.
	 *
	 * @param datasetCustomViewEntryId the primary key for the new dataset custom view entry
	 * @return the new dataset custom view entry
	 */
	@Transactional(enabled = false)
	public DatasetCustomViewEntry createDatasetCustomViewEntry(
		long datasetCustomViewEntryId);

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Deletes the dataset custom view entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DatasetCustomViewEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param datasetCustomViewEntry the dataset custom view entry
	 * @return the dataset custom view entry that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public DatasetCustomViewEntry deleteDatasetCustomViewEntry(
		DatasetCustomViewEntry datasetCustomViewEntry);

	/**
	 * Deletes the dataset custom view entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DatasetCustomViewEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param datasetCustomViewEntryId the primary key of the dataset custom view entry
	 * @return the dataset custom view entry that was removed
	 * @throws PortalException if a dataset custom view entry with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public DatasetCustomViewEntry deleteDatasetCustomViewEntry(
			long datasetCustomViewEntryId)
		throws PortalException;

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> T dslQuery(DSLQuery dslQuery);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DynamicQuery dynamicQuery();

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dataset.custom.view.model.impl.DatasetCustomViewEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end);

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dataset.custom.view.model.impl.DatasetCustomViewEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DatasetCustomViewEntry fetchDatasetCustomViewEntry(
		long datasetCustomViewEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DatasetCustomViewEntry fetchDatasetCustomViewEntry(
		long userId, String datasetDisplayId, long plid, String portletId);

	/**
	 * Returns the dataset custom view entry with the matching UUID and company.
	 *
	 * @param uuid the dataset custom view entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching dataset custom view entry, or <code>null</code> if a matching dataset custom view entry could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DatasetCustomViewEntry fetchDatasetCustomViewEntryByUuidAndCompanyId(
		String uuid, long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	 * Returns a range of all the dataset custom view entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dataset.custom.view.model.impl.DatasetCustomViewEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dataset custom view entries
	 * @param end the upper bound of the range of dataset custom view entries (not inclusive)
	 * @return the range of dataset custom view entries
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DatasetCustomViewEntry> getDatasetCustomViewEntries(
		int start, int end);

	/**
	 * Returns the number of dataset custom view entries.
	 *
	 * @return the number of dataset custom view entries
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDatasetCustomViewEntriesCount();

	/**
	 * Returns the dataset custom view entry with the primary key.
	 *
	 * @param datasetCustomViewEntryId the primary key of the dataset custom view entry
	 * @return the dataset custom view entry
	 * @throws PortalException if a dataset custom view entry with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DatasetCustomViewEntry getDatasetCustomViewEntry(
			long datasetCustomViewEntryId)
		throws PortalException;

	/**
	 * Returns the dataset custom view entry with the matching UUID and company.
	 *
	 * @param uuid the dataset custom view entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching dataset custom view entry
	 * @throws PortalException if a matching dataset custom view entry could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DatasetCustomViewEntry getDatasetCustomViewEntryByUuidAndCompanyId(
			String uuid, long companyId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	/**
	 * @throws PortalException
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Updates the dataset custom view entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DatasetCustomViewEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param datasetCustomViewEntry the dataset custom view entry
	 * @return the dataset custom view entry that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public DatasetCustomViewEntry updateDatasetCustomViewEntry(
		DatasetCustomViewEntry datasetCustomViewEntry);

}