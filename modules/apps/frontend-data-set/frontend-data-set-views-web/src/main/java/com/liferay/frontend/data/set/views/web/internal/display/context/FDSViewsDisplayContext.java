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

package com.liferay.frontend.data.set.views.web.internal.display.context;

import com.liferay.frontend.data.set.views.web.internal.constants.FDSViewsPortletKeys;
import com.liferay.frontend.data.set.views.web.internal.resource.FDSHeadlessResource;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

/**
 * @author Marko Cikos
 */
public class FDSViewsDisplayContext {

	public FDSViewsDisplayContext(
		PortletRequest portletRequest, Map<String, String> openapiResourcePaths,
		ServiceTrackerList<FDSHeadlessResource> serviceTrackerList) {

		_portletRequest = portletRequest;
		_openapiResourcePaths = openapiResourcePaths;
		_serviceTrackerList = serviceTrackerList;
	}

	public String getFDSEntriesAPIURL() {
		return "/o/c/fdsentries";
	}

	public String getFDSEntriesURL() {
		return PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				_portletRequest, FDSViewsPortletKeys.FDS_VIEWS,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/fds_entries.jsp"
		).buildString();
	}

	public String getFDSViewsAPIURL() {
		return "/o/c/fdsviews";
	}

	public String getFDSViewsURL() {
		return PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				_portletRequest, FDSViewsPortletKeys.FDS_VIEWS,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/fds_views.jsp"
		).buildString();
	}

	public String getFDSViewsURL(String fdsEntryId, String fdsEntryLabel) {
		return PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				_portletRequest, FDSViewsPortletKeys.FDS_VIEWS,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/fds_views.jsp"
		).setParameter(
			"fdsEntryId", fdsEntryId
		).setParameter(
			"fdsEntryLabel", fdsEntryLabel
		).buildString();
	}

	public String getFDSViewURL() {
		return PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				_portletRequest, FDSViewsPortletKeys.FDS_VIEWS,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/fds_view.jsp"
		).buildString();
	}

	public JSONArray getHeadlessResourcesJSONArray() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<FDSHeadlessResource> fdsHeadlessResources =
			_serviceTrackerList.toList();

		fdsHeadlessResources.sort(
			Comparator.comparing(FDSHeadlessResource::getBundleLabel));

		fdsHeadlessResources.sort(
			Comparator.comparing(FDSHeadlessResource::getSchema));

		for (FDSHeadlessResource fdsHeadlessResource : fdsHeadlessResources) {
			String schema = fdsHeadlessResource.getSchema();

			boolean objectResource = schema.startsWith("ObjectEntry");

			jsonArray.put(
				JSONUtil.put(
					"bundleLabel", fdsHeadlessResource.getBundleLabel()
				).put(
					"objectResource", objectResource
				).put(
					"openapiResourcePath",
					_openapiResourcePaths.get(
						fdsHeadlessResource.getOsgiJaxrsApplicationSelect())
				).put(
					"schema", _getSchema(fdsHeadlessResource, objectResource)
				).put(
					"version", fdsHeadlessResource.getVersion()
				));
		}

		return jsonArray;
	}

	private String _getSchema(
		FDSHeadlessResource fdsHeadlessResource, boolean objectResource) {

		String schema = fdsHeadlessResource.getSchema();

		if (objectResource) {
			String[] schemaParts = StringUtil.split(schema, "#C_");

			if (schemaParts.length == 2) {
				return schemaParts[1];
			}
		}

		return schema;
	}

	private final Map<String, String> _openapiResourcePaths;
	private final PortletRequest _portletRequest;
	private final ServiceTrackerList<FDSHeadlessResource> _serviceTrackerList;

}