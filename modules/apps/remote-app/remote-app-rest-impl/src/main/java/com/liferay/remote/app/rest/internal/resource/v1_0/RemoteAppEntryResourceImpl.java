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

package com.liferay.remote.app.rest.internal.resource.v1_0;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.remote.app.rest.dto.v1_0.RemoteAppEntry;
import com.liferay.remote.app.rest.internal.dto.v1_0.converter.RemoteAppEntryDTOConveter;
import com.liferay.remote.app.rest.resource.v1_0.RemoteAppEntryResource;
import com.liferay.remote.app.service.RemoteAppEntryLocalService;

import java.util.Map;

import javax.validation.ValidationException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Bruno Basto
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/remote-app-entry.properties",
	scope = ServiceScope.PROTOTYPE, service = RemoteAppEntryResource.class
)
public class RemoteAppEntryResourceImpl extends BaseRemoteAppEntryResourceImpl {

	@Override
	public Page<RemoteAppEntry> getRemoteAppEntriesPage(
			String keywords, Pagination pagination, Sort[] sorts)
		throws Exception {

		if (pagination.getPageSize() > 250) {
			throw new ValidationException(
				LanguageUtil.format(
					contextAcceptLanguage.getPreferredLocale(),
					"page-size-is-greater-than-x", 250));
		}

		if (ArrayUtil.isEmpty(sorts)) {
			sorts = new Sort[] {
				new Sort(
					Field.getSortableFieldName(Field.MODIFIED_DATE),
					Sort.STRING_TYPE, true)
			};
		}

		return Page.of(
			transform(
				_remoteAppEntryLocalService.searchRemoteAppEntries(
					contextCompany.getCompanyId(), keywords,
					pagination.getStartPosition(), pagination.getEndPosition(),
					sorts[0]),
				this::_toRemoteAppEntry));
	}

	private Map<String, Map<String, String>> _getActions(
		com.liferay.remote.app.model.RemoteAppEntry remoteAppEntry) {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"UPDATE", remoteAppEntry.getRemoteAppEntryId(),
				"deleteRemoteAppEntry", _remoteAppEntryModelResourcePermission)
		).put(
			"get",
			addAction(
				"VIEW", remoteAppEntry.getRemoteAppEntryId(),
				"getRemoteAppEntry", _remoteAppEntryModelResourcePermission)
		).put(
			"update",
			addAction(
				"UPDATE", remoteAppEntry.getRemoteAppEntryId(),
				"patchRemoteAppEntry", _remoteAppEntryModelResourcePermission)
		).build();
	}

	private RemoteAppEntry _toRemoteAppEntry(
			com.liferay.remote.app.model.RemoteAppEntry remoteAppEntry)
		throws Exception {

		return _remoAppEntryDTOConveter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(remoteAppEntry), _dtoConverterRegistry,
				remoteAppEntry.getRemoteAppEntryId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private RemoteAppEntryDTOConveter _remoAppEntryDTOConveter;

	@Reference
	private RemoteAppEntryLocalService _remoteAppEntryLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.remote.app.model.RemoteAppEntry)"
	)
	private ModelResourcePermission<com.liferay.remote.app.model.RemoteAppEntry>
		_remoteAppEntryModelResourcePermission;

}