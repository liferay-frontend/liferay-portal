/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.inventory;

import com.liferay.commerce.inventory.CPDefinitionInventoryEngine;
import com.liferay.commerce.inventory.CommerceInventoryChecker;
import com.liferay.commerce.inventory.engine.CommerceInventoryEngine;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Beslic
 */
public abstract class BaseCommerceInventoryChecker<T>
	implements CommerceInventoryChecker<T> {

	protected boolean isAvailable(CPInstance cpInstance, int quantity) {
		if (cpInstance == null) {
			return false;
		}

		if (isBackOrderAllowed(cpInstance) ||
			commerceInventoryEngine.hasStockQuantity(
				cpInstance.getCompanyId(), cpInstance.getGroupId(),
				cpInstance.getSku(), quantity)) {

			return true;
		}

		return false;
	}

	protected boolean isBackOrderAllowed(CPInstance cpInstance) {
		try {
			if (cpDefinitionInventoryEngine.isBackOrderAllowed(cpInstance)) {
				return true;
			}
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to check is back order allowed", portalException);
		}

		return false;
	}

	@Reference
	protected CommerceInventoryEngine commerceInventoryEngine;

	@Reference
	protected CPDefinitionInventoryEngine cpDefinitionInventoryEngine;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseCommerceInventoryChecker.class);

}