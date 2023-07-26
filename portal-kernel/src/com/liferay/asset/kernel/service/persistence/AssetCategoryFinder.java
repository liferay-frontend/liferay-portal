/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface AssetCategoryFinder {

	public int countByG_C_N(long groupId, long classNameId, String name);

	public int countByG_N_P(
		long groupId, String name, String[] categoryProperties);

	public int filterCountByC_C(long classNameId, long classPK);

	public java.util.List<com.liferay.asset.kernel.model.AssetCategory>
		filterFindByC_C(long classNameId, long classPK, int start, int end);

	public com.liferay.asset.kernel.model.AssetCategory findByG_N(
			long groupId, String name)
		throws com.liferay.asset.kernel.exception.NoSuchCategoryException;

	public java.util.List<com.liferay.asset.kernel.model.AssetCategory>
		findByC_C(long classNameId, long classPK);

	public java.util.List<com.liferay.asset.kernel.model.AssetCategory>
		findByG_N_P(long groupId, String name, String[] categoryProperties);

	public java.util.List<com.liferay.asset.kernel.model.AssetCategory>
		findByG_N_P(
			long groupId, String name, String[] categoryProperties, int start,
			int end);

}