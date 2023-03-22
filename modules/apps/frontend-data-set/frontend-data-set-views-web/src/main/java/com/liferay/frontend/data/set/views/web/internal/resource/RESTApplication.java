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

package com.liferay.frontend.data.set.views.web.internal.resource;

/**
 * @author Marko Cikos
 */
public class RESTApplication {

	public RESTApplication(String label, String path, String version) {
		_label = label;
		_path = path;
		_version = version;
	}

	public String getLabel() {
		return _label;
	}

	public String getPath() {
		return _path;
	}

	public String getVersion() {
		return _version;
	}

	private final String _label;
	private final String _path;
	private final String _version;

}