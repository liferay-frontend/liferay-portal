/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.ehcache.internal.event;

import com.liferay.portal.kernel.cache.PortalCacheManagerListener;

import net.sf.ehcache.Status;
import net.sf.ehcache.event.CacheManagerEventListener;

/**
 * @author Shuyang Zhou
 */
public class PortalCacheManagerEventListener
	implements CacheManagerEventListener {

	public PortalCacheManagerEventListener(
		PortalCacheManagerListener portalCacheManagerListener) {

		_portalCacheManagerListener = portalCacheManagerListener;
	}

	@Override
	public void dispose() {
		_portalCacheManagerListener.dispose();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PortalCacheManagerEventListener)) {
			return false;
		}

		PortalCacheManagerEventListener portalCacheManagerEventListener =
			(PortalCacheManagerEventListener)object;

		return _portalCacheManagerListener.equals(
			portalCacheManagerEventListener._portalCacheManagerListener);
	}

	public PortalCacheManagerListener getCacheManagerListener() {
		return _portalCacheManagerListener;
	}

	@Override
	public Status getStatus() {
		return Status.STATUS_ALIVE;
	}

	@Override
	public int hashCode() {
		return _portalCacheManagerListener.hashCode();
	}

	@Override
	public void init() {
		_portalCacheManagerListener.init();
	}

	@Override
	public void notifyCacheAdded(String portalCacheName) {
		_portalCacheManagerListener.notifyPortalCacheAdded(portalCacheName);
	}

	@Override
	public void notifyCacheRemoved(String portalCacheName) {
		_portalCacheManagerListener.notifyPortalCacheRemoved(portalCacheName);
	}

	private final PortalCacheManagerListener _portalCacheManagerListener;

}