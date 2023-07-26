/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.rest.repository.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Adolfo Pérez
 */
@ExtendedObjectClassDefinition(
	category = "documents-and-media", factoryInstanceLabelAttribute = "name"
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.sharepoint.rest.repository.internal.configuration.SharepointSearchConfiguration",
	localization = "content/Language",
	name = "sharepoint-search-configuration-name"
)
public interface SharepointSearchConfiguration {

	@Meta.AD(
		deflt = "8413cd39-2156-4e00-b54d-11efd9abdb89",
		name = "sharepoint-results-source-id", required = false
	)
	public String sharepointResultsSourceId();

}