/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetCategoryFinderUtil {

	public static int countByG_C_N(
		long groupId, long classNameId, String name) {

		return getFinder().countByG_C_N(groupId, classNameId, name);
	}

	public static int countByG_N_P(
		long groupId, String name, String[] categoryProperties) {

		return getFinder().countByG_N_P(groupId, name, categoryProperties);
	}

	public static int filterCountByC_C(long classNameId, long classPK) {
		return getFinder().filterCountByC_C(classNameId, classPK);
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetCategory>
		filterFindByC_C(long classNameId, long classPK, int start, int end) {

		return getFinder().filterFindByC_C(classNameId, classPK, start, end);
	}

	public static com.liferay.asset.kernel.model.AssetCategory findByG_N(
			long groupId, String name)
		throws com.liferay.asset.kernel.exception.NoSuchCategoryException {

		return getFinder().findByG_N(groupId, name);
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetCategory>
		findByC_C(long classNameId, long classPK) {

		return getFinder().findByC_C(classNameId, classPK);
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetCategory>
		findByG_N_P(long groupId, String name, String[] categoryProperties) {

		return getFinder().findByG_N_P(groupId, name, categoryProperties);
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetCategory>
		findByG_N_P(
			long groupId, String name, String[] categoryProperties, int start,
			int end) {

		return getFinder().findByG_N_P(
			groupId, name, categoryProperties, start, end);
	}

	public static AssetCategoryFinder getFinder() {
		if (_finder == null) {
			_finder = (AssetCategoryFinder)PortalBeanLocatorUtil.locate(
				AssetCategoryFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(AssetCategoryFinder finder) {
		_finder = finder;
	}

	private static AssetCategoryFinder _finder;

}