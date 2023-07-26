/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Luca Pellizzon
 * @generated
 */
@ProviderType
public interface CommerceInventoryWarehouseFinder {

	public java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouse>
			findByG_S(long groupId, String sku);

	public java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouse>
			findByC_G_A(long companyId, long groupId, boolean active);

}