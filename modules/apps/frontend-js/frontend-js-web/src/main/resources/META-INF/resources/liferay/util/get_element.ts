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

import getDOM from './get_dom';

interface JQueryElement extends Record<number, Element> {
	jquery: string;
}

function isJQueryElement(element: unknown): element is JQueryElement {
	return Object.prototype.hasOwnProperty.call(element, 'jquery');
}

/**
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
export default function getElement<T>(element: T): T | Element | null {
	const currentElement = getDOM(element);

	return typeof currentElement === 'string'
		? (document.querySelector(currentElement) as Element)
		: isJQueryElement(currentElement)
		? currentElement[0]
		: currentElement;
}
