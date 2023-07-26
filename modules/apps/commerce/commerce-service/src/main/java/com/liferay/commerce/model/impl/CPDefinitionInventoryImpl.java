/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionInventoryImpl extends CPDefinitionInventoryBaseImpl {

	@Override
	public int[] getAllowedOrderQuantitiesArray() {
		String allowedOrderQuantitiesString = getAllowedOrderQuantities();

		if (Validator.isNull(allowedOrderQuantitiesString)) {
			return new int[0];
		}

		allowedOrderQuantitiesString = allowedOrderQuantitiesString.replaceAll(
			" *(, *)|(\\. *)|( +)", StringPool.COMMA);

		int[] allowedOrderQuantities = StringUtil.split(
			allowedOrderQuantitiesString, 0);

		Arrays.sort(allowedOrderQuantities);

		return allowedOrderQuantities;
	}

}