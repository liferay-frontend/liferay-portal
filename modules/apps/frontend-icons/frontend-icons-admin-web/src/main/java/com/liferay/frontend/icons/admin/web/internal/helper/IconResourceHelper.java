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

package com.liferay.frontend.icons.admin.web.internal.helper;

import com.liferay.frontend.icons.admin.web.internal.contributor.extender.IconResource;
import com.liferay.frontend.icons.admin.web.internal.contributor.extender.IconResourcesContributor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Bryce Osterhaus
 */
@Component(immediate = true, service = IconResourceHelper.class)
public class IconResourceHelper {

	public String getGlobalSpriteContent() {
		StringBuilder sb = new StringBuilder();

		sb.append(
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE svg PUBLIC " +
				"\"-//W3C//DTD SVG 1.1//EN\" " +
					"\"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">");

		sb.append(
			"<svg xmlns=\"http://www.w3.org/2000/svg\" " +
				"xmlns:xlink=\"http://www.w3.org/1999/xlink\">");

		Set<Map.Entry<String, List<IconResource>>> entries =
			_iconResourcesMap.entrySet();

		for (Map.Entry<String, List<IconResource>> entry : entries) {
			List<IconResource> iconResources = entry.getValue();

			IconResource iconResource = iconResources.get(0);

			sb.append(iconResource.getSVGContent());
		}

		sb.append("</svg>");

		return new String(sb);
	}

	public Map<String, List<IconResource>> getIconResourceMaps() {
		return _iconResourcesMap;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void addIconResourcesContributor(
		IconResourcesContributor iconResourcesContributor) {

		Lock lock = _readWriteLock.writeLock();

		lock.lock();

		try {
			for (IconResource iconResource :
					iconResourcesContributor.getIconResources()) {

				_addIconResource(iconResource);
			}
		}
		finally {
			lock.unlock();
		}
	}

	protected void removeIconResourcesContributor(
		IconResourcesContributor iconResourcesContributor) {

		Lock lock = _readWriteLock.writeLock();

		try {
			lock.lock();

			for (IconResource iconResource :
					iconResourcesContributor.getIconResources()) {

				_removeIconResource(iconResource);
			}
		}
		finally {
			lock.unlock();
		}
	}

	private void _addIconResource(IconResource iconResource) {
		String id = iconResource.getId();

		List<IconResource> iconResources = _iconResourcesMap.get(id);

		if (iconResources == null) {
			iconResources = new ArrayList<>();

			_iconResourcesMap.put(id, iconResources);
		}

		int i;

		for (i = 0; i < iconResources.size(); i++) {
			IconResource existingIconResource = iconResources.get(i);

			if (existingIconResource.getPriority() >
					iconResource.getPriority()) {

				break;
			}
		}

		iconResources.add(i, iconResource);
	}

	private void _removeIconResource(IconResource iconResource) {
		List<IconResource> iconResources = _iconResourcesMap.get(
			iconResource.getId());

		if (iconResources == null) {
			return;
		}

		iconResources.remove(iconResource);
	}

	private final Map<String, List<IconResource>> _iconResourcesMap =
		new HashMap<>();
	private final ReadWriteLock _readWriteLock = new ReentrantReadWriteLock();

}