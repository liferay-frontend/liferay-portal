/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.util;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class DDMFormFieldTypeUtil {

	public static List<DDMFormFieldType> getDDMFormFieldTypesAllowed(
		List<DDMFormFieldType> ddmFormFieldTypes,
		String[] ddmFormFieldTypesAllowed) {

		return ListUtil.filter(
			ddmFormFieldTypes,
			fieldType -> ArrayUtil.contains(
				ddmFormFieldTypesAllowed, fieldType.getName()));
	}

}