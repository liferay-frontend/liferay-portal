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

import type {YNode, YNodeList} from '../types/AUI';

type Elements = unknown | Element | YNode | YNodeList | null;

function isYNode(nodes: Elements): nodes is YNode {
	return Object.prototype.hasOwnProperty.call(nodes, '_node');
}

function isYNodeList(nodes: Elements): nodes is YNodeList {
	return Object.prototype.hasOwnProperty.call(nodes, '_nodes');
}

/**
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
export default function getDOM<T>(element: T | null): T | Element | null {
	if (isYNode(element)) {
		return element._node;
	}

	if (isYNodeList(element)) {
		return null;
	}

	return element;
}
