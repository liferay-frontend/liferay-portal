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

import com.liferay.frontend.view.state.model.FVSEntry;
import com.liferay.frontend.view.state.service.base.FVSEntryServiceBaseImpl;
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
		"json.web.service.context.path=FVSEntry"
	},
	service = AopService.class
)
public class FVSEntryServiceImpl extends FVSEntryServiceBaseImpl {

	@Override
	public FVSEntry addFVSEntry(String viewState) throws PortalException {
		User user = getUser();

		if ((user != null) && !user.isDefaultUser()) {
			return fvsEntryLocalService.addFVSEntry(
				user.getUserId(), viewState);
		}

		return null;
	}

}