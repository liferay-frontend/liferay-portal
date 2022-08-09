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
import com.liferay.frontend.view.state.service.base.FVSFrontendDataSetLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.frontend.view.state.model.FVSFrontendDataSet",
	service = AopService.class
)
public class FVSFrontendDataSetLocalServiceImpl
	extends FVSFrontendDataSetLocalServiceBaseImpl {

	@Override
	public FVSFrontendDataSet addFVSFrontendDataSet(
			long userId, long fvsEntryId, String fdsName, String name,
			long plid, String portletId, boolean system)
		throws PortalException {

		FVSFrontendDataSet fvsFrontendDataSet =
			fvsFrontendDataSetPersistence.create(
				counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		fvsFrontendDataSet.setCompanyId(user.getCompanyId());
		fvsFrontendDataSet.setUserId(user.getUserId());
		fvsFrontendDataSet.setUserName(user.getFullName());

		fvsFrontendDataSet.setFVSEntryId(fvsEntryId);
		fvsFrontendDataSet.setFDSName(fdsName);
		fvsFrontendDataSet.setName(name);
		fvsFrontendDataSet.setPlid(plid);
		fvsFrontendDataSet.setPortletId(portletId);
		fvsFrontendDataSet.setSystem(system);

		return fvsFrontendDataSetPersistence.update(fvsFrontendDataSet);
	}

	public FVSFrontendDataSet addSystemFVSFrontendDataSet(
			long userId, long fvsEntryId, String fdsName, String name)
		throws PortalException {

		return addFVSFrontendDataSet(
			userId, fvsEntryId, fdsName, name, 0, null, true);
	}

	public FVSFrontendDataSet addUserFVSFrontendDataSet(
			long userId, long fvsEntryId, String fdsName, String name,
			long plid, String portletId)
		throws PortalException {

		return addFVSFrontendDataSet(
			userId, fvsEntryId, fdsName, name, plid, portletId, false);
	}

	public List<FVSFrontendDataSet> getSystemFVSFrontendDataSets(
		String fdsName) {

		return fvsFrontendDataSetPersistence.findByF_S(fdsName, true);
	}

	public List<FVSFrontendDataSet> getUserFVSFrontendDataSets(
		String fdsName, long plid, String portletId, long userId) {

		return fvsFrontendDataSetPersistence.findByU_F_P_P_S(
			userId, fdsName, plid, portletId, false);
	}

	@Reference
	private UserLocalService _userLocalService;

}