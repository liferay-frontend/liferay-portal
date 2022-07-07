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

package com.liferay.object.rest.internal.jaxrs.extension;

import com.liferay.object.rest.extension.BaseRelationshipResourceImpl;

import javax.ws.rs.Path;

import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import org.osgi.service.component.annotations.Component;

/**
 * @author Luis Miguel Barcos
 */
@Component(
	factory = "com.liferay.object.rest.extension.SystemObjectRelationshipsResource",
	property = {"api.version=v1.0", "osgi.jaxrs.resource=true"},
	service = BaseRelationshipResourceImpl.class
)
@Path("/v1.0/{element: [a-zA-Z0-9-]+}")
public class SystemObjectRelationshipsResourceImpl
	extends BaseRelationshipResourceImpl {
}