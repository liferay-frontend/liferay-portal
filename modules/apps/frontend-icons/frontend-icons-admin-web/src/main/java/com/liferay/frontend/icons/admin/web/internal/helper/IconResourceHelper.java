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

import com.liferay.frontend.icons.admin.web.internal.repository.IconResourcePackRepository;
import com.liferay.frontend.icons.admin.web.internal.util.SVGUtil;
import com.liferay.frontend.icons.model.IconResourcePack;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Bryce Osterhaus
 */
@Component(immediate = true, service = IconResourceHelper.class)
public class IconResourceHelper {

	public void addIconResourcePack(
			long companyId, IconResourcePack iconResourcePack)
		throws PortalException {

		Map<String, IconResourcePack> companyIconResourceMap =
			_iconResourcesMap.computeIfAbsent(
				companyId, k -> new HashMap<String, IconResourcePack>());

		companyIconResourceMap.putIfAbsent(
			iconResourcePack.getName(), iconResourcePack);

		_iconResourcePackRepository.addIconResourcePack(
			companyId, iconResourcePack);
	}

	public void deleteIconResourcePack(long companyId, String iconPack)
		throws PortalException {

		_iconResourcePackRepository.deleteIconResourcePack(companyId, iconPack);

		Lock lock = _readWriteLock.writeLock();

		lock.lock();

		Map<String, IconResourcePack> stringIconResourcePackMap =
			_iconResourcesMap.get(companyId);

		stringIconResourcePackMap.remove(iconPack);

		lock.unlock();
	}

	public String getGlobalSpriteContent() {
		Map<String, IconResourcePack> iconResourcePacks = _iconResourcesMap.get(
			_GLOBAL_ID);

		IconResourcePack clayIconResourcePack = iconResourcePacks.get("clay");

		return SVGUtil.getSVGSpritemap(clayIconResourcePack);
	}

	public String getIconPackSpriteContent(
		long companyId, String iconPackName) {

		HashMap<String, IconResourcePack> iconResourceMap = getIconResourceMaps(
			companyId);

		if (iconResourceMap == null) {
			return null;
		}

		IconResourcePack iconResourcePack = iconResourceMap.get(iconPackName);

		if (iconResourcePack == null) {
			return null;
		}

		return SVGUtil.getSVGSpritemap(iconResourcePack);
	}

	public HashMap<String, IconResourcePack> getIconResourceMaps(
		long companyId) {

		return HashMapBuilder.putAll(
			_iconResourcesMap.get(_GLOBAL_ID)
		).putAll(
			_iconResourcesMap.get(companyId)
		).build();
	}

	@Activate
	protected void activate() throws Exception {
		Map<Long, Map<String, IconResourcePack>> iconResourcePacks =
			_iconResourcePackRepository.getIconResourcePacks();

		Lock lock = _readWriteLock.writeLock();

		lock.lock();

		_iconResourcesMap.putAll(iconResourcePacks);

		lock.unlock();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void addIconResourcePack(IconResourcePack iconResourcePack) {
		Lock lock = _readWriteLock.writeLock();

		lock.lock();

		try {
			_addIconResourcePack(iconResourcePack);
		}
		finally {
			lock.unlock();
		}
	}

	protected void removeIconResourcePack(IconResourcePack iconResourcePack) {
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

		_iconResourcesMap.computeIfAbsent(
			_GLOBAL_ID, k -> new HashMap<String, IconResourcePack>());

		Map<String, IconResourcePack> iconResourcePackMap =
			_iconResourcesMap.get(_GLOBAL_ID);

		iconResourcePackMap.putIfAbsent(name, iconResourcePack);
	}

	private void _removeIconResourcePack(IconResourcePack iconResourcePack) {
		if (_iconResourcesMap.containsKey(_GLOBAL_ID)) {
			String name = iconResourcePack.getName();

			Map<String, IconResourcePack> iconResourceMap =
				_iconResourcesMap.get(_GLOBAL_ID);

			iconResourceMap.remove(name);
		}
	}

	private static final long _GLOBAL_ID = 0L;

	@Reference
	private IconResourcePackRepository _iconResourcePackRepository;

	private final Map<Long, Map<String, IconResourcePack>> _iconResourcesMap =
		new HashMap<>();
	private final ReadWriteLock _readWriteLock = new ReentrantReadWriteLock();

}