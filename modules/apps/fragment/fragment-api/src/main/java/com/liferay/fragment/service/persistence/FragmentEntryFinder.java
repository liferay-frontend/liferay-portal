/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface FragmentEntryFinder {

	public int countFC_FE_ByG_FCI(
		long groupId, long fragmentCollectionId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition);

	public int countFC_FE_ByG_FCI_N(
		long groupId, long fragmentCollectionId, String name,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition);

}