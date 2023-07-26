/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.util;

import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.image.processor.AMImageProcessor;

import java.io.InputStream;

import java.util.function.Supplier;

/**
 * @author Adolfo Pérez
 */
public interface AMImageSerializer {

	public AdaptiveMedia<AMImageProcessor> deserialize(
		String s, Supplier<InputStream> inputStreamSupplier);

	public String serialize(AdaptiveMedia<AMImageProcessor> adaptiveMedia);

}