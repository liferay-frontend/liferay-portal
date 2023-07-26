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
public interface CommerceInventoryWarehouseItemFinder {

	public int countItemsByCompanyId(long companyId, String sku);

	public int countStockQuantityByC_S(long companyId, String sku);

	public int countStockQuantityByC_S(
		long companyId, String sku, boolean inlineSQLHelper);

	public int countStockQuantityByC_G_S(
		long companyId, long commerceChannelGroupId, String sku);

	public int countStockQuantityByC_G_S(
		long companyId, long commerceChannelGroupId, String sku,
		boolean inlineSQLHelper);

	public int countUpdatedItemsByC_M(
		long companyId, java.util.Date startDate, java.util.Date endDate);

	public java.util.List<Object[]> findItemsByCompanyId(
		long companyId, String sku, int start, int end);

	public java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem>
			findUpdatedItemsByC_M(
				long companyId, java.util.Date startDate,
				java.util.Date endDate, int start, int end);

}