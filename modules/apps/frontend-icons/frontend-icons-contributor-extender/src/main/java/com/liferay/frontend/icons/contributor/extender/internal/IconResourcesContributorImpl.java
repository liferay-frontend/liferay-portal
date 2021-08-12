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
import com.liferay.frontend.icons.contributor.extender.IconResourcesContributor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Iván Zaera Avellón
 */
public class IconResourcesContributorImpl implements IconResourcesContributor {

	public void addIconResource(IconResource iconResource) {
		_iconResources.add(iconResource);
	}

	@Override
	public Collection<IconResource> getIconResources() {
		return _iconResources;
	}

	private final List<IconResource> _iconResources = new ArrayList<>();

}