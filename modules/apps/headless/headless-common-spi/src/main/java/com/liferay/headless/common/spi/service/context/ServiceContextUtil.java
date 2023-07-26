/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.common.spi.service.context;

import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Map;

/**
 * @author     Víctor Galán
 * @deprecated As of Athanasius (7.3.x)
 */
@Deprecated
public class ServiceContextUtil {

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static ServiceContext createServiceContext(
		long groupId, String viewableBy) {

		return ServiceContextRequestUtil.createServiceContext(
			new Long[0], new String[0], null, groupId, null, viewableBy);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static ServiceContext createServiceContext(
		Long[] assetCategoryIds, String[] assetTagNames, Long groupId,
		String viewableBy) {

		return ServiceContextRequestUtil.createServiceContext(
			assetCategoryIds, assetTagNames, null, groupId, null, viewableBy);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static ServiceContext createServiceContext(
		Long[] assetCategoryIds, String[] assetTagNames,
		Map<String, Serializable> expandoBridgeAttributes, Long groupId,
		String viewableBy) {

		return ServiceContextRequestUtil.createServiceContext(
			assetCategoryIds, assetTagNames, expandoBridgeAttributes, groupId,
			null, viewableBy);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static ServiceContext createServiceContext(
		Map<String, Serializable> expandoBridgeAttributes, long groupId,
		String viewableBy) {

		return ServiceContextRequestUtil.createServiceContext(
			new Long[0], new String[0], expandoBridgeAttributes, groupId, null,
			viewableBy);
	}

}