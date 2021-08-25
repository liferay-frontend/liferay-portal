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
import setFormValues from './set_form_values';

interface Options {
	url?: string;
	data?: Record<string, string>;
}

/**
 * Submits the form, with optional setting of form elements.
 */

export default function postForm(
	form: string | HTMLFormElement,
	options?: Options
) {
	if (typeof form === 'string') {
		form = document.querySelector(form) as HTMLFormElement;
	}

	if (form && form.nodeName === 'FORM') {
		form.setAttribute('method', 'post');

		if (isObject(options)) {
			const {data, url} = options;

			if (isObject(data)) {
				setFormValues(form, data);
			}
			else {
				return;
			}

			if (url === undefined) {
				submitForm(form);
			}
			else if (typeof url === 'string') {
				submitForm(form, url);
			}
		}
		else {
			submitForm(form);
		}
	}
}
