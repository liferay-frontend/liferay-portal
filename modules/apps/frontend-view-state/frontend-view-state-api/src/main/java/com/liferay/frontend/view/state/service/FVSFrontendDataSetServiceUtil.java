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

import com.liferay.frontend.view.state.model.FVSFrontendDataSet;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * Provides the remote service utility for FVSFrontendDataSet. This utility wraps
 * <code>com.liferay.frontend.view.state.service.impl.FVSFrontendDataSetServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see FVSFrontendDataSetService
 * @generated
 */
public class FVSFrontendDataSetServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.frontend.view.state.service.impl.FVSFrontendDataSetServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static FVSFrontendDataSet addUserFVSFrontendDataSet(
			long fvsEntryId, String fdsName, String name, long plid,
			String portletId)
		throws PortalException {

		return getService().addUserFVSFrontendDataSet(
			fvsEntryId, fdsName, name, plid, portletId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static FVSFrontendDataSetService getService() {
		return _service;
	}

	private static volatile FVSFrontendDataSetService _service;

}