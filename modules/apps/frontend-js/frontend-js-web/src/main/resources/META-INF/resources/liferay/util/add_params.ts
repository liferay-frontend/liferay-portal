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

/**
 * Appends given parameters to the given URL.
 */
export default function addParams(params: string | object, baseUrl: string) {
	if (!params || (typeof params !== 'object' && typeof params !== 'string')) {
		throw new TypeError('Parameter params must be an object or string');
	}

	if (typeof baseUrl !== 'string') {
		throw new TypeError('Parameter baseUrl must be a string');
	}

	const url = baseUrl.startsWith('/')
		? new URL(baseUrl, location.href)
		: new URL(baseUrl);

	if (typeof params === 'object') {
		Object.entries(params).forEach(([key, value]) => {
			url.searchParams.append(key, value);
		});
	}
	else {
		const searchParams = new URLSearchParams(params.trim());

		searchParams.forEach((value, key) => {
			if (value) {
				url.searchParams.append(key, value);
			}
			else {
				url.searchParams.append(key, '');
			}
		});
	}

	return url.toString();
}
