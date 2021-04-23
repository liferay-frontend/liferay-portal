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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DatasetCustomViewEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DatasetCustomViewEntryLocalService
 * @generated
 */
public class DatasetCustomViewEntryLocalServiceWrapper
	implements DatasetCustomViewEntryLocalService,
			   ServiceWrapper<DatasetCustomViewEntryLocalService> {

	public DatasetCustomViewEntryLocalServiceWrapper(
		DatasetCustomViewEntryLocalService datasetCustomViewEntryLocalService) {

		_datasetCustomViewEntryLocalService =
			datasetCustomViewEntryLocalService;
	}

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
	@Override
	public com.liferay.dataset.custom.view.model.DatasetCustomViewEntry
		addDatasetCustomViewEntry(
			com.liferay.dataset.custom.view.model.DatasetCustomViewEntry
				datasetCustomViewEntry) {

		return _datasetCustomViewEntryLocalService.addDatasetCustomViewEntry(
			datasetCustomViewEntry);
	}

	@Override
	public com.liferay.dataset.custom.view.model.DatasetCustomViewEntry
		createDatasetCustomViewEntry() {

		return _datasetCustomViewEntryLocalService.
			createDatasetCustomViewEntry();
	}

	/**
	 * Creates a new dataset custom view entry with the primary key. Does not add the dataset custom view entry to the database.
	 *
	 * @param datasetCustomViewEntryId the primary key for the new dataset custom view entry
	 * @return the new dataset custom view entry
	 */
	@Override
	public com.liferay.dataset.custom.view.model.DatasetCustomViewEntry
		createDatasetCustomViewEntry(long datasetCustomViewEntryId) {

		return _datasetCustomViewEntryLocalService.createDatasetCustomViewEntry(
			datasetCustomViewEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _datasetCustomViewEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

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
	@Override
	public com.liferay.dataset.custom.view.model.DatasetCustomViewEntry
		deleteDatasetCustomViewEntry(
			com.liferay.dataset.custom.view.model.DatasetCustomViewEntry
				datasetCustomViewEntry) {

		return _datasetCustomViewEntryLocalService.deleteDatasetCustomViewEntry(
			datasetCustomViewEntry);
	}

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
	@Override
	public com.liferay.dataset.custom.view.model.DatasetCustomViewEntry
			deleteDatasetCustomViewEntry(long datasetCustomViewEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _datasetCustomViewEntryLocalService.deleteDatasetCustomViewEntry(
			datasetCustomViewEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _datasetCustomViewEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _datasetCustomViewEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _datasetCustomViewEntryLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _datasetCustomViewEntryLocalService.dynamicQuery(dynamicQuery);
	}

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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _datasetCustomViewEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _datasetCustomViewEntryLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _datasetCustomViewEntryLocalService.dynamicQueryCount(
			dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _datasetCustomViewEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.dataset.custom.view.model.DatasetCustomViewEntry
		fetchDatasetCustomViewEntry(long datasetCustomViewEntryId) {

		return _datasetCustomViewEntryLocalService.fetchDatasetCustomViewEntry(
			datasetCustomViewEntryId);
	}

	@Override
	public com.liferay.dataset.custom.view.model.DatasetCustomViewEntry
		fetchDatasetCustomViewEntry(
			long userId, String datasetDisplayId, long plid, String portletId) {

		return _datasetCustomViewEntryLocalService.fetchDatasetCustomViewEntry(
			userId, datasetDisplayId, plid, portletId);
	}

	/**
	 * Returns the dataset custom view entry with the matching UUID and company.
	 *
	 * @param uuid the dataset custom view entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching dataset custom view entry, or <code>null</code> if a matching dataset custom view entry could not be found
	 */
	@Override
	public com.liferay.dataset.custom.view.model.DatasetCustomViewEntry
		fetchDatasetCustomViewEntryByUuidAndCompanyId(
			String uuid, long companyId) {

		return _datasetCustomViewEntryLocalService.
			fetchDatasetCustomViewEntryByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _datasetCustomViewEntryLocalService.getActionableDynamicQuery();
	}

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
	@Override
	public java.util.List
		<com.liferay.dataset.custom.view.model.DatasetCustomViewEntry>
			getDatasetCustomViewEntries(int start, int end) {

		return _datasetCustomViewEntryLocalService.getDatasetCustomViewEntries(
			start, end);
	}

	/**
	 * Returns the number of dataset custom view entries.
	 *
	 * @return the number of dataset custom view entries
	 */
	@Override
	public int getDatasetCustomViewEntriesCount() {
		return _datasetCustomViewEntryLocalService.
			getDatasetCustomViewEntriesCount();
	}

	/**
	 * Returns the dataset custom view entry with the primary key.
	 *
	 * @param datasetCustomViewEntryId the primary key of the dataset custom view entry
	 * @return the dataset custom view entry
	 * @throws PortalException if a dataset custom view entry with the primary key could not be found
	 */
	@Override
	public com.liferay.dataset.custom.view.model.DatasetCustomViewEntry
			getDatasetCustomViewEntry(long datasetCustomViewEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _datasetCustomViewEntryLocalService.getDatasetCustomViewEntry(
			datasetCustomViewEntryId);
	}

	/**
	 * Returns the dataset custom view entry with the matching UUID and company.
	 *
	 * @param uuid the dataset custom view entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching dataset custom view entry
	 * @throws PortalException if a matching dataset custom view entry could not be found
	 */
	@Override
	public com.liferay.dataset.custom.view.model.DatasetCustomViewEntry
			getDatasetCustomViewEntryByUuidAndCompanyId(
				String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _datasetCustomViewEntryLocalService.
			getDatasetCustomViewEntryByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _datasetCustomViewEntryLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _datasetCustomViewEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _datasetCustomViewEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _datasetCustomViewEntryLocalService.getPersistedModel(
			primaryKeyObj);
	}

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
	@Override
	public com.liferay.dataset.custom.view.model.DatasetCustomViewEntry
		updateDatasetCustomViewEntry(
			com.liferay.dataset.custom.view.model.DatasetCustomViewEntry
				datasetCustomViewEntry) {

		return _datasetCustomViewEntryLocalService.updateDatasetCustomViewEntry(
			datasetCustomViewEntry);
	}

	@Override
	public DatasetCustomViewEntryLocalService getWrappedService() {
		return _datasetCustomViewEntryLocalService;
	}

	@Override
	public void setWrappedService(
		DatasetCustomViewEntryLocalService datasetCustomViewEntryLocalService) {

		_datasetCustomViewEntryLocalService =
			datasetCustomViewEntryLocalService;
	}

	private DatasetCustomViewEntryLocalService
		_datasetCustomViewEntryLocalService;

}