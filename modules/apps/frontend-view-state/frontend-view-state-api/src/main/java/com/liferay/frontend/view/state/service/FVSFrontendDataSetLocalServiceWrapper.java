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

package com.liferay.frontend.view.state.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link FVSFrontendDataSetLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see FVSFrontendDataSetLocalService
 * @generated
 */
public class FVSFrontendDataSetLocalServiceWrapper
	implements FVSFrontendDataSetLocalService,
			   ServiceWrapper<FVSFrontendDataSetLocalService> {

	public FVSFrontendDataSetLocalServiceWrapper() {
		this(null);
	}

	public FVSFrontendDataSetLocalServiceWrapper(
		FVSFrontendDataSetLocalService fvsFrontendDataSetLocalService) {

		_fvsFrontendDataSetLocalService = fvsFrontendDataSetLocalService;
	}

	/**
	 * Adds the fvs frontend data set to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FVSFrontendDataSetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fvsFrontendDataSet the fvs frontend data set
	 * @return the fvs frontend data set that was added
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSFrontendDataSet
		addFVSFrontendDataSet(
			com.liferay.frontend.view.state.model.FVSFrontendDataSet
				fvsFrontendDataSet) {

		return _fvsFrontendDataSetLocalService.addFVSFrontendDataSet(
			fvsFrontendDataSet);
	}

	/**
	 * Creates a new fvs frontend data set with the primary key. Does not add the fvs frontend data set to the database.
	 *
	 * @param fvsFrontendDataSetId the primary key for the new fvs frontend data set
	 * @return the new fvs frontend data set
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSFrontendDataSet
		createFVSFrontendDataSet(long fvsFrontendDataSetId) {

		return _fvsFrontendDataSetLocalService.createFVSFrontendDataSet(
			fvsFrontendDataSetId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fvsFrontendDataSetLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the fvs frontend data set from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FVSFrontendDataSetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fvsFrontendDataSet the fvs frontend data set
	 * @return the fvs frontend data set that was removed
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSFrontendDataSet
		deleteFVSFrontendDataSet(
			com.liferay.frontend.view.state.model.FVSFrontendDataSet
				fvsFrontendDataSet) {

		return _fvsFrontendDataSetLocalService.deleteFVSFrontendDataSet(
			fvsFrontendDataSet);
	}

	/**
	 * Deletes the fvs frontend data set with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FVSFrontendDataSetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fvsFrontendDataSetId the primary key of the fvs frontend data set
	 * @return the fvs frontend data set that was removed
	 * @throws PortalException if a fvs frontend data set with the primary key could not be found
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSFrontendDataSet
			deleteFVSFrontendDataSet(long fvsFrontendDataSetId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fvsFrontendDataSetLocalService.deleteFVSFrontendDataSet(
			fvsFrontendDataSetId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fvsFrontendDataSetLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _fvsFrontendDataSetLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _fvsFrontendDataSetLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _fvsFrontendDataSetLocalService.dynamicQuery();
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

		return _fvsFrontendDataSetLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.frontend.view.state.model.impl.FVSFrontendDataSetModelImpl</code>.
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

		return _fvsFrontendDataSetLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.frontend.view.state.model.impl.FVSFrontendDataSetModelImpl</code>.
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

		return _fvsFrontendDataSetLocalService.dynamicQuery(
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

		return _fvsFrontendDataSetLocalService.dynamicQueryCount(dynamicQuery);
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

		return _fvsFrontendDataSetLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.frontend.view.state.model.FVSFrontendDataSet
		fetchFVSFrontendDataSet(long fvsFrontendDataSetId) {

		return _fvsFrontendDataSetLocalService.fetchFVSFrontendDataSet(
			fvsFrontendDataSetId);
	}

	/**
	 * Returns the fvs frontend data set with the matching UUID and company.
	 *
	 * @param uuid the fvs frontend data set's UUID
	 * @param companyId the primary key of the company
	 * @return the matching fvs frontend data set, or <code>null</code> if a matching fvs frontend data set could not be found
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSFrontendDataSet
		fetchFVSFrontendDataSetByUuidAndCompanyId(String uuid, long companyId) {

		return _fvsFrontendDataSetLocalService.
			fetchFVSFrontendDataSetByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _fvsFrontendDataSetLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _fvsFrontendDataSetLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	/**
	 * Returns the fvs frontend data set with the primary key.
	 *
	 * @param fvsFrontendDataSetId the primary key of the fvs frontend data set
	 * @return the fvs frontend data set
	 * @throws PortalException if a fvs frontend data set with the primary key could not be found
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSFrontendDataSet
			getFVSFrontendDataSet(long fvsFrontendDataSetId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fvsFrontendDataSetLocalService.getFVSFrontendDataSet(
			fvsFrontendDataSetId);
	}

	/**
	 * Returns the fvs frontend data set with the matching UUID and company.
	 *
	 * @param uuid the fvs frontend data set's UUID
	 * @param companyId the primary key of the company
	 * @return the matching fvs frontend data set
	 * @throws PortalException if a matching fvs frontend data set could not be found
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSFrontendDataSet
			getFVSFrontendDataSetByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fvsFrontendDataSetLocalService.
			getFVSFrontendDataSetByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the fvs frontend data sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.frontend.view.state.model.impl.FVSFrontendDataSetModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs frontend data sets
	 * @param end the upper bound of the range of fvs frontend data sets (not inclusive)
	 * @return the range of fvs frontend data sets
	 */
	@Override
	public java.util.List
		<com.liferay.frontend.view.state.model.FVSFrontendDataSet>
			getFVSFrontendDataSets(int start, int end) {

		return _fvsFrontendDataSetLocalService.getFVSFrontendDataSets(
			start, end);
	}

	/**
	 * Returns the number of fvs frontend data sets.
	 *
	 * @return the number of fvs frontend data sets
	 */
	@Override
	public int getFVSFrontendDataSetsCount() {
		return _fvsFrontendDataSetLocalService.getFVSFrontendDataSetsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _fvsFrontendDataSetLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _fvsFrontendDataSetLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fvsFrontendDataSetLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the fvs frontend data set in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FVSFrontendDataSetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fvsFrontendDataSet the fvs frontend data set
	 * @return the fvs frontend data set that was updated
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSFrontendDataSet
		updateFVSFrontendDataSet(
			com.liferay.frontend.view.state.model.FVSFrontendDataSet
				fvsFrontendDataSet) {

		return _fvsFrontendDataSetLocalService.updateFVSFrontendDataSet(
			fvsFrontendDataSet);
	}

	@Override
	public FVSFrontendDataSetLocalService getWrappedService() {
		return _fvsFrontendDataSetLocalService;
	}

	@Override
	public void setWrappedService(
		FVSFrontendDataSetLocalService fvsFrontendDataSetLocalService) {

		_fvsFrontendDataSetLocalService = fvsFrontendDataSetLocalService;
	}

	private FVSFrontendDataSetLocalService _fvsFrontendDataSetLocalService;

}