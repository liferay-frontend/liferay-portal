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

package com.liferay.frontend.view.state.service.impl;

import com.liferay.frontend.view.state.model.FVSFrontendDataSet;
import com.liferay.frontend.view.state.service.base.FVSFrontendDataSetServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=fvs",
		"json.web.service.context.path=FVSFrontendDataSet"
	},
	service = AopService.class
)
public class FVSFrontendDataSetServiceImpl
	extends FVSFrontendDataSetServiceBaseImpl {

	@Override
	public FVSFrontendDataSet addUserFVSFrontendDataSet(
			long fvsEntryId, String fdsName, String name, long plid,
			String portletId)
		throws PortalException {

		User user = getUser();

		if ((user != null) && !user.isDefaultUser()) {
			return fvsFrontendDataSetLocalService.addUserFVSFrontendDataSet(
				user.getUserId(), fvsEntryId, fdsName, name, plid, portletId);
		}

		return null;
	}

}