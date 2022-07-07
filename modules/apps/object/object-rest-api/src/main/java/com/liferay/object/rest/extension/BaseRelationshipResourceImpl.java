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

package com.liferay.object.rest.extension;

import com.liferay.object.exception.NoSuchObjectRelationshipException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerTracker;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.net.URI;

import java.util.List;

import javax.validation.constraints.NotNull;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Miguel Barcos
 */
public abstract class BaseRelationshipResourceImpl
	implements RelationshipsResource {

	@GET
	@Override
	@Parameters(
		{
			@Parameter(in = ParameterIn.PATH, name = "currentObjectEntryId"),
			@Parameter(in = ParameterIn.PATH, name = "objectRelationshipName"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path(
		"/{currentObjectEntryId: \\d+}/{objectRelationshipName: [a-zA-Z0-9-]+}"
	)
	@Produces({"application/json", "application/xml"})
	@Tags({@Tag(name = "ObjectEntry")})
	public Page<?> getCurrentObjectEntriesObjectRelationshipNamePage(
			@NotNull @Parameter(hidden = true)
			@PathParam("currentObjectEntryId")
			Long currentObjectEntryId,
			@NotNull @Parameter(hidden = true)
			@PathParam("objectRelationshipName")
			String objectRelationshipName,
			@Context Pagination pagination)
		throws Exception {

		ObjectDefinition currentObjectDefinition = getCurrentObjectDefinition(
			currentObjectEntryId, objectRelationshipName, uriInfo);

		ObjectDefinition relatedObjectDefinition = getRelatedObjectDefinition(
			getObjectRelationshipByName(objectRelationshipName),
			currentObjectDefinition);

		ObjectEntryManager objectEntryManager =
			objectEntryManagerTracker.getObjectEntryManager(
				currentObjectDefinition.getStorageType());

		if (relatedObjectDefinition.isSystem()) {
			return objectEntryManager.getRelatedSystemObjectEntries(
				currentObjectDefinition, currentObjectEntryId,
				objectRelationshipName, pagination);
		}

		return objectEntryManager.getObjectEntryRelatedObjectEntries(
			getDefaultDTOConverterContext(
				currentObjectDefinition, currentObjectEntryId, uriInfo),
			currentObjectDefinition, currentObjectEntryId,
			objectRelationshipName, pagination);
	}

	protected ObjectDefinition getCurrentObjectDefinition(
			long currentObjectEntryId, String objectRelationshipName,
			UriInfo uriInfo)
		throws Exception {

		ObjectEntry objectEntry = objectEntryLocalService.fetchObjectEntry(
			currentObjectEntryId);

		if (objectEntry != null) {
			return objectDefinitionLocalService.getObjectDefinition(
				objectEntry.getObjectDefinitionId());
		}

		ObjectRelationship currentObjectRelationship =
			getObjectRelationshipByName(objectRelationshipName);

		ObjectDefinition currentObjectDefinition =
			objectDefinitionLocalService.getObjectDefinition(
				currentObjectRelationship.getObjectDefinitionId1());

		String currentObjectDefinitionRESTContextPath =
			currentObjectDefinition.getRESTContextPath();

		URI baseURI = uriInfo.getBaseUri();

		String path = baseURI.getPath();

		String endpointBasePath = path.split("/")[2];

		if (!currentObjectDefinitionRESTContextPath.equals(endpointBasePath)) {
			currentObjectDefinition =
				objectDefinitionLocalService.getObjectDefinition(
					currentObjectRelationship.getObjectDefinitionId2());
		}

		return currentObjectDefinition;
	}

	protected DefaultDTOConverterContext getDefaultDTOConverterContext(
		ObjectDefinition objectDefinition, Long objectEntryId,
		UriInfo uriInfo) {

		return new DefaultDTOConverterContext(
			dtoConverterRegistry, objectEntryId,
			LocaleUtil.fromLanguageId(
				objectDefinition.getDefaultLanguageId(), true, false),
			uriInfo, null);
	}

	protected ObjectRelationship getObjectRelationshipByName(
			String objectRelationshipName)
		throws Exception {

		List<ObjectRelationship> objectRelationships = ListUtil.filter(
			objectRelationshipLocalService.getObjectRelationships(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS),
			objectRelationship ->
				!objectRelationship.isReverse() &&
				objectRelationship.getName(
				).equals(
					objectRelationshipName
				));

		if(objectRelationships.isEmpty()) {
			throw new NoSuchObjectRelationshipException(
				"No relationship exists with name " + objectRelationshipName);
		}

		return objectRelationships.get(0);
	}

	protected ObjectDefinition getRelatedObjectDefinition(
			ObjectRelationship objectRelationship,
			ObjectDefinition currentObjectDefinition)
		throws Exception {

		long objectDefinitionId1 = objectRelationship.getObjectDefinitionId1();
		long currentObjectDefinitionId =
			currentObjectDefinition.getObjectDefinitionId();

		if (objectDefinitionId1 != currentObjectDefinitionId) {
			return objectDefinitionLocalService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId1());
		}

		return objectDefinitionLocalService.getObjectDefinition(
			objectRelationship.getObjectDefinitionId2());
	}

	@Reference
	protected DTOConverterRegistry dtoConverterRegistry;

	@Reference
	protected ObjectDefinitionLocalService objectDefinitionLocalService;

	@Reference
	protected ObjectEntryLocalService objectEntryLocalService;

	@Reference
	protected ObjectEntryManagerTracker objectEntryManagerTracker;

	@Reference
	protected ObjectRelationshipLocalService objectRelationshipLocalService;

	@Context
	protected UriInfo uriInfo;

}