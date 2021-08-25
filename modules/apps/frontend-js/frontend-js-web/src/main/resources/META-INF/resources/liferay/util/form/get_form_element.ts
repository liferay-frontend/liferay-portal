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
 * Returns a DOM element or elements in a form.
 */

export default function getFormElement(
	form: HTMLFormElement,
	elementName: string
) {
	let formElement = null;

	if (
		form !== undefined &&
		form.nodeName === 'FORM' &&
		typeof elementName === 'string'
	) {
		const ns = form.dataset.fmNamespace || '';

		formElement = form.elements.namedItem(ns + elementName) || null;
	}

	return formElement;
}
