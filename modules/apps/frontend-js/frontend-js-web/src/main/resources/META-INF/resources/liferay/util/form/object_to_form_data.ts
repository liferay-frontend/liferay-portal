/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import isObject from '../is_object';

/**
 * Returns a FormData containing serialized object.
 */

export default function objectToFormData(
	object: any = {},
	formData = new FormData(),
	namespace = ''
) {
	Object.entries(object).forEach(([key, value]) => {
		const formKey = namespace ? `${namespace}[${key}]` : key;

		if (Array.isArray(value)) {
			value.forEach((item) => {
				objectToFormData(
					{
						[formKey]: item,
					},
					formData
				);
			});
		}
		else if (isObject(value) && !(value instanceof File)) {
			objectToFormData(value, formData, formKey);
		}
		else {
			formData.append(formKey, value as string | Blob);
		}
	});

	return formData;
}
