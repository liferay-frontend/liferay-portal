/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.webcache;

/**
 * @author Brian Wing Shun Chan
 */
public class WebCachePoolUtil {

	public static void clear() {
		_webCachePool.clear();
	}

	public static Object get(String key, WebCacheItem webCacheItem) {
		return _webCachePool.get(key, webCacheItem);
	}

	public static WebCachePool getWebCachePool() {
		return _webCachePool;
	}

	public static void remove(String key) {
		_webCachePool.remove(key);
	}

	public void setWebCachePool(WebCachePool webCachePool) {
		_webCachePool = webCachePool;
	}

	private static WebCachePool _webCachePool;

}