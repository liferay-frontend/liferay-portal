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
import com.liferay.frontend.icons.admin.web.internal.contributor.extender.IconResourcePack;

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

	public String generateXmlSvg(String content) {
		StringBuilder sb = new StringBuilder();

		sb.append(
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE svg PUBLIC " +
			"\"-//W3C//DTD SVG 1.1//EN\" " +
			"\"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">");

		sb.append(
			"<svg xmlns=\"http://www.w3.org/2000/svg\" " +
			"xmlns:xlink=\"http://www.w3.org/1999/xlink\">");

		sb.append(content);

		sb.append("</svg>");

		return new String(sb);
	}

	public String getIconPackSpriteContent(String iconPackName) {
		StringBuilder sb = new StringBuilder();

		for (IconResource iconResource: _iconResourcesMap.get(iconPackName).getIconResources()) {
			sb.append(iconResource.getSVGContent());
		}

		return generateXmlSvg(new String(sb));
	}

	public String getGlobalSpriteContent() {
		StringBuilder sb = new StringBuilder();

		for (Map.Entry<String, IconResourcePack> entry: _iconResourcesMap.entrySet()) {
			IconResourcePack iconResourcePack = entry.getValue();

			for (IconResource iconResource :
				iconResourcePack.getIconResources()) {

				sb.append(iconResource.getSVGContent());
			}
		}

		return generateXmlSvg(new String(sb));
	}

	public Map<String, IconResourcePack> getIconResourceMaps() {
		return _iconResourcesMap;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void addIconResourcePack(
		IconResourcePack iconResourcePack) {

		Lock lock = _readWriteLock.writeLock();

		lock.lock();

		try {
			_addIconResourcePack(iconResourcePack);
		}
		finally {
			lock.unlock();
		}
	}

	protected void removeIconResourcePack(
		IconResourcePack iconResourcePack) {

		Lock lock = _readWriteLock.writeLock();
			
		lock.lock();

		try {
			_removeIconResourcePack(iconResourcePack);
		}
		finally {
			lock.unlock();
		}
	}

	private void _addIconResourcePack(IconResourcePack iconResourcePack) {
		String name = iconResourcePack.getName();

		_iconResourcesMap.putIfAbsent(name, iconResourcePack);
	}

	private void _removeIconResourcePack(IconResourcePack iconResourcePack) {
		String name = iconResourcePack.getName();

		_iconResourcesMap.remove(name);
	}

	private final Map<String, IconResourcePack> _iconResourcesMap = new HashMap<>();
	private final ReadWriteLock _readWriteLock = new ReentrantReadWriteLock();

}