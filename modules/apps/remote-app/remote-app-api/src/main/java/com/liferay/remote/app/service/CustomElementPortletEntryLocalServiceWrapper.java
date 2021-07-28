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

package com.liferay.remote.app.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CustomElementPortletEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see CustomElementPortletEntryLocalService
 * @generated
 */
public class CustomElementPortletEntryLocalServiceWrapper
	implements CustomElementPortletEntryLocalService,
			   ServiceWrapper<CustomElementPortletEntryLocalService> {

	public CustomElementPortletEntryLocalServiceWrapper(
		CustomElementPortletEntryLocalService
			customElementPortletEntryLocalService) {

		_customElementPortletEntryLocalService =
			customElementPortletEntryLocalService;
	}

	/**
	 * Adds the custom element portlet entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CustomElementPortletEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param customElementPortletEntry the custom element portlet entry
	 * @return the custom element portlet entry that was added
	 */
	@Override
	public com.liferay.remote.app.model.CustomElementPortletEntry
		addCustomElementPortletEntry(
			com.liferay.remote.app.model.CustomElementPortletEntry
				customElementPortletEntry) {

		return _customElementPortletEntryLocalService.
			addCustomElementPortletEntry(customElementPortletEntry);
	}

	/**
	 * Creates a new custom element portlet entry with the primary key. Does not add the custom element portlet entry to the database.
	 *
	 * @param customElementPortletEntryId the primary key for the new custom element portlet entry
	 * @return the new custom element portlet entry
	 */
	@Override
	public com.liferay.remote.app.model.CustomElementPortletEntry
		createCustomElementPortletEntry(long customElementPortletEntryId) {

		return _customElementPortletEntryLocalService.
			createCustomElementPortletEntry(customElementPortletEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _customElementPortletEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the custom element portlet entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CustomElementPortletEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param customElementPortletEntry the custom element portlet entry
	 * @return the custom element portlet entry that was removed
	 */
	@Override
	public com.liferay.remote.app.model.CustomElementPortletEntry
		deleteCustomElementPortletEntry(
			com.liferay.remote.app.model.CustomElementPortletEntry
				customElementPortletEntry) {

		return _customElementPortletEntryLocalService.
			deleteCustomElementPortletEntry(customElementPortletEntry);
	}

	/**
	 * Deletes the custom element portlet entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CustomElementPortletEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param customElementPortletEntryId the primary key of the custom element portlet entry
	 * @return the custom element portlet entry that was removed
	 * @throws PortalException if a custom element portlet entry with the primary key could not be found
	 */
	@Override
	public com.liferay.remote.app.model.CustomElementPortletEntry
			deleteCustomElementPortletEntry(long customElementPortletEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _customElementPortletEntryLocalService.
			deleteCustomElementPortletEntry(customElementPortletEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _customElementPortletEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _customElementPortletEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _customElementPortletEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _customElementPortletEntryLocalService.dynamicQuery();
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

		return _customElementPortletEntryLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.remote.app.model.impl.CustomElementPortletEntryModelImpl</code>.
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

		return _customElementPortletEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.remote.app.model.impl.CustomElementPortletEntryModelImpl</code>.
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

		return _customElementPortletEntryLocalService.dynamicQuery(
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

		return _customElementPortletEntryLocalService.dynamicQueryCount(
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

		return _customElementPortletEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.remote.app.model.CustomElementPortletEntry
		fetchCustomElementPortletEntry(long customElementPortletEntryId) {

		return _customElementPortletEntryLocalService.
			fetchCustomElementPortletEntry(customElementPortletEntryId);
	}

	/**
	 * Returns the custom element portlet entry with the matching UUID and company.
	 *
	 * @param uuid the custom element portlet entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching custom element portlet entry, or <code>null</code> if a matching custom element portlet entry could not be found
	 */
	@Override
	public com.liferay.remote.app.model.CustomElementPortletEntry
		fetchCustomElementPortletEntryByUuidAndCompanyId(
			String uuid, long companyId) {

		return _customElementPortletEntryLocalService.
			fetchCustomElementPortletEntryByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _customElementPortletEntryLocalService.
			getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the custom element portlet entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.remote.app.model.impl.CustomElementPortletEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of custom element portlet entries
	 * @param end the upper bound of the range of custom element portlet entries (not inclusive)
	 * @return the range of custom element portlet entries
	 */
	@Override
	public java.util.List
		<com.liferay.remote.app.model.CustomElementPortletEntry>
			getCustomElementPortletEntries(int start, int end) {

		return _customElementPortletEntryLocalService.
			getCustomElementPortletEntries(start, end);
	}

	/**
	 * Returns the number of custom element portlet entries.
	 *
	 * @return the number of custom element portlet entries
	 */
	@Override
	public int getCustomElementPortletEntriesCount() {
		return _customElementPortletEntryLocalService.
			getCustomElementPortletEntriesCount();
	}

	/**
	 * Returns the custom element portlet entry with the primary key.
	 *
	 * @param customElementPortletEntryId the primary key of the custom element portlet entry
	 * @return the custom element portlet entry
	 * @throws PortalException if a custom element portlet entry with the primary key could not be found
	 */
	@Override
	public com.liferay.remote.app.model.CustomElementPortletEntry
			getCustomElementPortletEntry(long customElementPortletEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _customElementPortletEntryLocalService.
			getCustomElementPortletEntry(customElementPortletEntryId);
	}

	/**
	 * Returns the custom element portlet entry with the matching UUID and company.
	 *
	 * @param uuid the custom element portlet entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching custom element portlet entry
	 * @throws PortalException if a matching custom element portlet entry could not be found
	 */
	@Override
	public com.liferay.remote.app.model.CustomElementPortletEntry
			getCustomElementPortletEntryByUuidAndCompanyId(
				String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _customElementPortletEntryLocalService.
			getCustomElementPortletEntryByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _customElementPortletEntryLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _customElementPortletEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _customElementPortletEntryLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _customElementPortletEntryLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the custom element portlet entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CustomElementPortletEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param customElementPortletEntry the custom element portlet entry
	 * @return the custom element portlet entry that was updated
	 */
	@Override
	public com.liferay.remote.app.model.CustomElementPortletEntry
		updateCustomElementPortletEntry(
			com.liferay.remote.app.model.CustomElementPortletEntry
				customElementPortletEntry) {

		return _customElementPortletEntryLocalService.
			updateCustomElementPortletEntry(customElementPortletEntry);
	}

	@Override
	public CustomElementPortletEntryLocalService getWrappedService() {
		return _customElementPortletEntryLocalService;
	}

	@Override
	public void setWrappedService(
		CustomElementPortletEntryLocalService
			customElementPortletEntryLocalService) {

		_customElementPortletEntryLocalService =
			customElementPortletEntryLocalService;
	}

	private CustomElementPortletEntryLocalService
		_customElementPortletEntryLocalService;

}