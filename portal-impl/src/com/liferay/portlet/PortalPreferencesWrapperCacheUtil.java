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

package com.liferay.portlet;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Shuyang Zhou
 */
public class PortalPreferencesWrapperCacheUtil {

	public static final String CACHE_NAME =
		PortalPreferencesWrapperCacheUtil.class.getName();

	public static PortalPreferencesWrapper get(long ownerId, int ownerType) {
		return _portalPreferencesWrapperPortalCache.get(
			_getCacheKey(ownerId, ownerType));
	}

	public static void put(
		long ownerId, int ownerType,
		PortalPreferencesWrapper portalPreferencesWrapper) {

		PortalCacheHelperUtil.putWithoutReplicator(
			_portalPreferencesWrapperPortalCache,
			_getCacheKey(ownerId, ownerType), portalPreferencesWrapper);
	}

	public static void remove(long ownerId, int ownerType) {
		_portalPreferencesWrapperPortalCache.remove(
			_getCacheKey(ownerId, ownerType));
	}

	private static String _getCacheKey(long ownerId, int ownerType) {
		String cacheKey = StringUtil.toHexString(ownerId);

		return cacheKey.concat(StringUtil.toHexString(ownerType));
	}

	private static final PortalCache<String, PortalPreferencesWrapper>
		_portalPreferencesWrapperPortalCache =
			PortalCacheHelperUtil.getPortalCache(
				PortalCacheManagerNames.MULTI_VM, CACHE_NAME, false, true);

}