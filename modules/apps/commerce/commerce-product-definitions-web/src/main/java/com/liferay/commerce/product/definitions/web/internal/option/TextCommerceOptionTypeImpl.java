/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.definitions.web.internal.option;

import com.liferay.commerce.product.option.CommerceOptionType;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"commerce.option.type.display.order:Integer=200",
		"commerce.option.type.key=" + TextCommerceOptionTypeImpl.KEY
	},
	service = CommerceOptionType.class
)
public class TextCommerceOptionTypeImpl
	extends BaseCommerceOptionTypeImpl implements CommerceOptionType {

	public static final String KEY = "text";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, KEY);
	}

	@Override
	public void render(
			long cpDefinitionOptionRelId, long cpDefinitionOptionValueRelId,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {
	}

	@Reference
	private Language _language;

}