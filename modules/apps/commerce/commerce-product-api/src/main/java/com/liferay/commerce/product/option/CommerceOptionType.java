/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.option;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alessio Antonio Rendina
 */
public interface CommerceOptionType {

	public String getKey();

	public String getLabel(Locale locale);

	public boolean isPriceContributor(long cpDefinitionOptionRelId);

	public boolean isRequired(long cpDefinitionOptionRelId);

	public boolean isSKUContributor(long cpDefinitionOptionRelId);

	public void render(
			long cpDefinitionOptionRelId, long cpDefinitionOptionValueRelId,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception;

}