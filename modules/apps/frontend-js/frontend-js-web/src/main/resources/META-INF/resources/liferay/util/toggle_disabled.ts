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

type Nodes = Element | Element[] | NodeListOf<Element> | YNode | YNodeList;

function isYNode(nodes: Nodes): nodes is YNode {
	return Object.prototype.hasOwnProperty.call(nodes, '_node');
}

function isYNodeList(nodes: Nodes): nodes is YNodeList {
	return Object.prototype.hasOwnProperty.call(nodes, '_nodes');
}

function isElement(nodes: Nodes): nodes is Element {
	return (
		Object.prototype.hasOwnProperty.call(nodes, 'nodeType') &&
		(nodes as Element).nodeType === Node.ELEMENT_NODE
	);
}

function normalizeNodes(nodes: string | Nodes) {
	if (typeof nodes === 'string') {
		return document.querySelectorAll(nodes);
	}

	if (isYNode(nodes)) {
		return [nodes._node];
	}

	if (isYNodeList(nodes)) {
		return nodes._nodes;
	}

	if (isElement(nodes)) {
		return [nodes];
	}

	return nodes;
}

/**
 * Toggles disabled class on received element
 */
export default function toggleDisabled(nodes: string | Nodes, state: string) {
	normalizeNodes(nodes).forEach((node) => {
		node.setAttribute('disabled', state);

		if (state) {
			node.classList.add('disabled');
		}
		else {
			node.classList.remove('disabled');
		}
	});
}
