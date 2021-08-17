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

package com.liferay.frontend.icons.admin.web.internal.contributor.extender;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Bryce Osterhaus
 */
public class IconResourcePackImpl implements IconResourcePack {

	public IconResourcePackImpl(String name) {
		_name = name;
	}

	public void addIconResource(String iconName, String iconContents) {
		IconResource iconResource = new IconResourceImpl(iconName, iconContents);
		
		_iconResources.add(iconResource);
	}

	@Override
	public Collection<IconResource> getIconResources() {
		return _iconResources;
	}

	@Override
	public String getName() {
		return _name;
	}

	private final List<IconResource> _iconResources = new ArrayList<>();
	private final String _name;

}