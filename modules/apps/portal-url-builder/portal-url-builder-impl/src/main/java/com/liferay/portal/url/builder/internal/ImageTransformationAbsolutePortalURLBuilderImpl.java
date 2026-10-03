/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.url.builder.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.url.builder.ImageTransformationAbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.ImageTransformationURLRenderer;
import com.liferay.portal.url.builder.internal.util.URLUtil;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Daniel Sanz
 */
public class ImageTransformationAbsolutePortalURLBuilderImpl
	implements ImageTransformationAbsolutePortalURLBuilder {

	public ImageTransformationAbsolutePortalURLBuilderImpl(
		String cdnHost,
		ImageTransformationURLRenderer imageTransformationURLRenderer,
		String pathProxy, String resourcePath) {

		_cdnHost = cdnHost;
		_imageTransformationURLRenderer = imageTransformationURLRenderer;
		_pathProxy = pathProxy;
		_resourcePath = resourcePath;

		_ignoreCDNHost = false;
	}

	@Override
	public String build() {
		StringBundler sb = new StringBundler();

		URLUtil.appendURL(
			sb, _cdnHost, _ignoreCDNHost, StringPool.BLANK, _pathProxy,
			_resourcePath);

		String url = sb.toString();

		if (_imageTransformations.isEmpty() ||
			(_imageTransformationURLRenderer == null)) {

			return url;
		}

		return _imageTransformationURLRenderer.render(
			url, _imageTransformations);
	}

	@Override
	public ImageTransformationAbsolutePortalURLBuilder ignoreCDNHost() {
		_ignoreCDNHost = true;

		return this;
	}

	@Override
	public ImageTransformationAbsolutePortalURLBuilder setImageTransformation(
		String name, String value) {

		if ((name == null) || (value == null)) {
			return this;
		}

		_imageTransformations.put(name, value);

		return this;
	}

	private final String _cdnHost;
	private boolean _ignoreCDNHost;
	private final ImageTransformationURLRenderer
		_imageTransformationURLRenderer;
	private final Map<String, String> _imageTransformations = new TreeMap<>();
	private final String _pathProxy;
	private final String _resourcePath;

}