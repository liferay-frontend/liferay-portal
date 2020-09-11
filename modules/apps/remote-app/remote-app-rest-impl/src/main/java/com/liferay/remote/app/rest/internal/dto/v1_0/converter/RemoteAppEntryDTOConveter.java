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

package com.liferay.remote.app.rest.internal.dto.v1_0.converter;

import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.remote.app.rest.dto.v1_0.RemoteAppEntry;
import com.liferay.remote.app.rest.internal.resource.v1_0.util.LocalizedValueUtil;
import com.liferay.remote.app.service.RemoteAppEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(
	property = "model.class.name=com.liferay.remote.app.model.RemoteAppEntry",
	service = {DTOConverter.class, RemoteAppEntryDTOConveter.class}
)
public class RemoteAppEntryDTOConveter
	implements DTOConverter
		<com.liferay.remote.app.model.RemoteAppEntry, RemoteAppEntry> {

	@Override
	public String getContentType() {
		return RemoteAppEntry.class.getSimpleName();
	}

	@Override
	public RemoteAppEntry toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		com.liferay.remote.app.model.RemoteAppEntry remoteAppEntry =
			_remoteAppEntryLocalService.getRemoteAppEntry(
				(Long)dtoConverterContext.getId());

		return new RemoteAppEntry() {
			{
				companyId = remoteAppEntry.getCompanyId();
				dateCreated = remoteAppEntry.getCreateDate();
				dateModified = remoteAppEntry.getModifiedDate();
				id = remoteAppEntry.getRemoteAppEntryId();
				name = LocalizedValueUtil.toStringObjectMap(
					remoteAppEntry.getNameMap());
				url = remoteAppEntry.getUrl();
				userId = remoteAppEntry.getUserId();
			}
		};
	}

	@Reference
	private RemoteAppEntryLocalService _remoteAppEntryLocalService;

}