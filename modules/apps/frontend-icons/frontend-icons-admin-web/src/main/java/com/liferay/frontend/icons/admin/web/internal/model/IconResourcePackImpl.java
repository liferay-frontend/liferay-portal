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

package com.liferay.frontend.icons.admin.web.internal.model;

import com.liferay.frontend.icons.model.IconResource;
import com.liferay.frontend.icons.model.IconResourcePack;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Bryce Osterhaus
 */
public class IconResourcePackImpl implements IconResourcePack {

	public IconResourcePackImpl(String name) {
		this(name, true);
	}

	public IconResourcePackImpl(String name, boolean editable) {
		_name = name;
		_editable = editable;
	}

	public void addIconResource(IconResource iconResource) {
		_iconResources.put(iconResource.getId(), iconResource);
	}

	@Override
	public void addIconResources(List<IconResource> iconResources) {
		iconResources.forEach(this::addIconResource);
	}

	@Override
	public Optional<IconResource> getIconResourceOptional(String iconName) {
		return Optional.ofNullable(_iconResources.get(iconName));
	}

	@Override
	public Collection<IconResource> getIconResources() {
		return _iconResources.values();
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public boolean isEditable() {
		return _editable;
	}

	public void removeIconResource(String iconName) {
		_iconResources.remove(iconName);
	}

	private final boolean _editable;
	private final Map<String, IconResource> _iconResources = new HashMap<>();
	private final String _name;

}