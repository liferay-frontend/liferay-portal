/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.url.builder;

import com.liferay.portal.url.builder.facet.BuildableAbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.facet.CDNAwareAbsolutePortalURLBuilder;

/**
 * Builds a URL for an image that an external image optimization provider may
 * transform on the fly.
 *
 * @author Daniel Sanz
 */
public interface ImageTransformationAbsolutePortalURLBuilder
	extends BuildableAbsolutePortalURLBuilder,
			CDNAwareAbsolutePortalURLBuilder
				<ImageTransformationAbsolutePortalURLBuilder> {

	/**
	 * Sets an image transformation, by the name the renderer's provider gives
	 * it. Setting the same name twice replaces the previous value.
	 */
	public ImageTransformationAbsolutePortalURLBuilder setImageTransformation(
		String name, String value);

}