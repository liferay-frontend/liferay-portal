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

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.remote.app.model.CustomElementPortletEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * Provides the local service utility for CustomElementPortletEntry. This utility wraps
 * <code>com.liferay.remote.app.service.impl.CustomElementPortletEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see CustomElementPortletEntryLocalService
 * @generated
 */
public class CustomElementPortletEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.remote.app.service.impl.CustomElementPortletEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

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
	public static CustomElementPortletEntry addCustomElementPortletEntry(
		CustomElementPortletEntry customElementPortletEntry) {

		return getService().addCustomElementPortletEntry(
			customElementPortletEntry);
	}

	public static CustomElementPortletEntry addCustomElementPortletEntry(
			long userId, java.util.Collection<String> cssURLs,
			Map<java.util.Locale, String> nameMap,
			String portletDisplayCategory, Map<String, String> tagAttributes,
			String tagName,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCustomElementPortletEntry(
			userId, cssURLs, nameMap, portletDisplayCategory, tagAttributes,
			tagName, serviceContext);
	}

	/**
	 * Creates a new custom element portlet entry with the primary key. Does not add the custom element portlet entry to the database.
	 *
	 * @param customElementPortletEntryId the primary key for the new custom element portlet entry
	 * @return the new custom element portlet entry
	 */
	public static CustomElementPortletEntry createCustomElementPortletEntry(
		long customElementPortletEntryId) {

		return getService().createCustomElementPortletEntry(
			customElementPortletEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
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
	public static CustomElementPortletEntry deleteCustomElementPortletEntry(
		CustomElementPortletEntry customElementPortletEntry) {

		return getService().deleteCustomElementPortletEntry(
			customElementPortletEntry);
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
	public static CustomElementPortletEntry deleteCustomElementPortletEntry(
			long customElementPortletEntryId)
		throws PortalException {

		return getService().deleteCustomElementPortletEntry(
			customElementPortletEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static CustomElementPortletEntry fetchCustomElementPortletEntry(
		long customElementPortletEntryId) {

		return getService().fetchCustomElementPortletEntry(
			customElementPortletEntryId);
	}

	/**
	 * Returns the custom element portlet entry with the matching UUID and company.
	 *
	 * @param uuid the custom element portlet entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching custom element portlet entry, or <code>null</code> if a matching custom element portlet entry could not be found
	 */
	public static CustomElementPortletEntry
		fetchCustomElementPortletEntryByUuidAndCompanyId(
			String uuid, long companyId) {

		return getService().fetchCustomElementPortletEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
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
	public static List<CustomElementPortletEntry>
		getCustomElementPortletEntries(int start, int end) {

		return getService().getCustomElementPortletEntries(start, end);
	}

	/**
	 * Returns the number of custom element portlet entries.
	 *
	 * @return the number of custom element portlet entries
	 */
	public static int getCustomElementPortletEntriesCount() {
		return getService().getCustomElementPortletEntriesCount();
	}

	/**
	 * Returns the custom element portlet entry with the primary key.
	 *
	 * @param customElementPortletEntryId the primary key of the custom element portlet entry
	 * @return the custom element portlet entry
	 * @throws PortalException if a custom element portlet entry with the primary key could not be found
	 */
	public static CustomElementPortletEntry getCustomElementPortletEntry(
			long customElementPortletEntryId)
		throws PortalException {

		return getService().getCustomElementPortletEntry(
			customElementPortletEntryId);
	}

	/**
	 * Returns the custom element portlet entry with the matching UUID and company.
	 *
	 * @param uuid the custom element portlet entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching custom element portlet entry
	 * @throws PortalException if a matching custom element portlet entry could not be found
	 */
	public static CustomElementPortletEntry
			getCustomElementPortletEntryByUuidAndCompanyId(
				String uuid, long companyId)
		throws PortalException {

		return getService().getCustomElementPortletEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static List<CustomElementPortletEntry>
			searchCustomElementPortletEntries(
				long companyId, String keywords, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
		throws PortalException {

		return getService().searchCustomElementPortletEntries(
			companyId, keywords, start, end, sort);
	}

	public static int searchCustomElementPortletEntriesCount(
			long companyId, String keywords)
		throws PortalException {

		return getService().searchCustomElementPortletEntriesCount(
			companyId, keywords);
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
	public static CustomElementPortletEntry updateCustomElementPortletEntry(
		CustomElementPortletEntry customElementPortletEntry) {

		return getService().updateCustomElementPortletEntry(
			customElementPortletEntry);
	}

	public static CustomElementPortletEntry updateCustomElementPortletEntry(
			long customElementPortletEntryId,
			java.util.Collection<String> cssURLs,
			Map<java.util.Locale, String> nameMap,
			String portletDisplayCategory, Map<String, String> tagAttributes,
			String tagName,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCustomElementPortletEntry(
			customElementPortletEntryId, cssURLs, nameMap,
			portletDisplayCategory, tagAttributes, tagName, serviceContext);
	}

	public static CustomElementPortletEntryLocalService getService() {
		return _service;
	}

	private static volatile CustomElementPortletEntryLocalService _service;

}