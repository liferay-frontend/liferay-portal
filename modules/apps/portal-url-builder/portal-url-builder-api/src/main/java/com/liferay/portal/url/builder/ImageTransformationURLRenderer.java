/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.url.builder;

import java.util.Map;

/**
 * Renders image transformation instructions using the URL vocabulary of a
 * concrete image optimization provider (typically a CDN).
 *
 * <p>
 * The {@link ImageTransformationAbsolutePortalURLBuilder} calls this renderer
 * to turn the image transformations it collected into whatever URL based
 * contract the provider understands. The renderer also decides which of those
 * image transformations the provider understands at all.
 * </p>
 *
 * <p>
 * Implementations must be deterministic: calls with equal image transformations
 * must produce byte identical URLs regardless of map insertion order. Otherwise
 * every render creates a new CDN cache object for the same image.
 * </p>
 *
 * @author Daniel Sanz
 */
public interface ImageTransformationURLRenderer {

	/**
	 * Returns the name of the provider this renderer speaks for, for example
	 * <code>fastly</code>.
	 */
	public String getName();

	/**
	 * Returns the URL with the given image transformations applied.
	 */
	public String render(String url, Map<String, String> imageTransformations);

}