/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.definitions.web.internal.option;

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.option.CommerceOptionType;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
public abstract class BaseCommerceOptionTypeImpl implements CommerceOptionType {

	@Override
	public boolean isPriceContributor(long cpDefinitionOptionRelId) {
		CPDefinitionOptionRel cpDefinitionOptionRel =
			cpDefinitionOptionRelLocalService.fetchCPDefinitionOptionRel(
				cpDefinitionOptionRelId);

		if (cpDefinitionOptionRel == null) {
			return false;
		}

		return cpDefinitionOptionRel.isPriceContributor();
	}

	@Override
	public boolean isRequired(long cpDefinitionOptionRelId) {
		CPDefinitionOptionRel cpDefinitionOptionRel =
			cpDefinitionOptionRelLocalService.fetchCPDefinitionOptionRel(
				cpDefinitionOptionRelId);

		if (cpDefinitionOptionRel == null) {
			return false;
		}

		return cpDefinitionOptionRel.isRequired();
	}

	@Override
	public boolean isSKUContributor(long cpDefinitionOptionRelId) {
		CPDefinitionOptionRel cpDefinitionOptionRel =
			cpDefinitionOptionRelLocalService.fetchCPDefinitionOptionRel(
				cpDefinitionOptionRelId);

		if (cpDefinitionOptionRel == null) {
			return false;
		}

		return cpDefinitionOptionRel.isSkuContributor();
	}

	@Reference
	protected CPDefinitionOptionRelLocalService
		cpDefinitionOptionRelLocalService;

}