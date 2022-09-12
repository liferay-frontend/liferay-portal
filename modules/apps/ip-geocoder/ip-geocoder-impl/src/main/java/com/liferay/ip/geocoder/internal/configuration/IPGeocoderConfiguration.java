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

package com.liferay.ip.geocoder.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedAttributeDefinition;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Brian Wing Shun Chan
 */
@ExtendedObjectClassDefinition(category = "third-party")
@Meta.OCD(
	id = "com.liferay.ip.geocoder.internal.configuration.IPGeocoderConfiguration",
	localization = "content/Language", name = "ip-geocoder-configuration-name"
)
public interface IPGeocoderConfiguration {

	@ExtendedAttributeDefinition(
		descriptionArguments = "maxmind.com", requiredInput = true
	)
	@Meta.AD(
		description = "file-path-description[ip-geocoder]", name = "file-path",
		required = false
	)
	public String filePath();

}