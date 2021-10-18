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

import getElement from './get_element';

type Region = {
	bottom: number;
	left: number;
	right: number;
	top: number;
};

function normalizeNodeRegion(node: HTMLElement, nodeRegion?: Region): Region {
	if (nodeRegion) {
		return nodeRegion;
	}

	const boundingClientRect = node.getBoundingClientRect();

	const left = boundingClientRect.left + window.scrollX;
	const top = boundingClientRect.top + window.scrollY;

	return {
		bottom: top + node.offsetHeight,
		left,
		right: left + node.offsetWidth,
		top,
	};
}

export default function inBrowserView(
	node: HTMLElement,
	baseWindow: Window = window,
	nodeRegion?: Region
): boolean {
	let viewable = false;

	node = getElement(node) as HTMLElement;

	if (node) {
		nodeRegion = normalizeNodeRegion(node, nodeRegion);

		const left = baseWindow.scrollX;
		const top = baseWindow.scrollY;

		const winRegion: Region = {
			bottom: top + baseWindow.innerHeight,
			left,
			right: left + baseWindow.innerWidth,
			top,
		};

		viewable =
			nodeRegion.bottom <= winRegion.bottom &&
			nodeRegion.left >= winRegion.left &&
			nodeRegion.right <= winRegion.right &&
			nodeRegion.top >= winRegion.top;

		if (viewable) {
			const frameElement = baseWindow.frameElement;

			if (frameElement) {
				const frameOffset = frameElement.getBoundingClientRect();

				const correctedFrameOffset = {
					left: frameOffset.left + window.scrollX,
					top: frameOffset.top + window.scrollY,
				};

				const xOffset = correctedFrameOffset.left - winRegion.left;

				nodeRegion.left += xOffset;
				nodeRegion.right += xOffset;

				const yOffset = correctedFrameOffset.top - winRegion.top;

				nodeRegion.top += yOffset;
				nodeRegion.bottom += yOffset;

				viewable = inBrowserView(node, baseWindow.parent, nodeRegion);
			}
		}
	}

	return viewable;
}
