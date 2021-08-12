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

package com.liferay.frontend.icons.contributor.extender.internal;

import com.liferay.frontend.icons.contributor.extender.IconResource;

/**
 * @author Iván Zaera Avellón
 */
public class IconResourceImpl implements IconResource {

	public IconResourceImpl(String id, String svgContent) {
		this(id, svgContent, 0);
	}

	public IconResourceImpl(String id, String svgContent, int priority) {
		_id = id;
		_svgContent = svgContent;
		_priority = priority;
	}

	@Override
	public String getId() {
		return _id;
	}

	@Override
	public int getPriority() {
		return _priority;
	}

	@Override
	public String getSVGContent() {
		return _svgContent;
	}

	private String _id;
	private int _priority;
	private String _svgContent;

}