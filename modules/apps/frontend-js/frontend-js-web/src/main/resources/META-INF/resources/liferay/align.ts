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

interface VendorPrefixedCSSStyleDeclaration extends CSSStyleDeclaration {
	msTransform?: string;
	mozTransform?: string;
}

type ClientProperty = 'clientHeight' | 'clientWidth';
type HeightOrWidth = 'Height' | 'Width';
type Node = HTMLElement | Document | Window;
type OffsetProperty = 'offsetHeight' | 'offsetWidth';
type Region = Omit<DOMRect, 'x' | 'y' | 'toJSON'>;
type ScrollProperty = 'scrollHeight' | 'scrollWidth';

/**
 * Returns true if value is a document.
 */
function isDocument(val: any): val is Document {
	return val && typeof val === 'object' && val.nodeType === 9;
}

/**
 * Returns true if value is a window.
 */
function isWindow(val: any): val is Window {
	return val && val === val.window;
}

/**
 * Gets the client height or width of the specified node. Scroll height is
 * not included.
 */
function getClientSize(node: Node, property: HeightOrWidth): number {
	const element = isWindow(node)
		? node.document.documentElement
		: isDocument(node)
		? node.documentElement
		: node;

	return element[`client${property}` as ClientProperty];
}

/**
 * Gets the region of the element, document or window.
 *
 * The returned value is a simulated DOMRect object which is the union of the
 * rectangles returned by getClientRects() for the element, i.e., the CSS
 * border-boxes associated with the element.
 */
function getDocumentRegion(element: Node) {
	const height = getHeight(element);
	const width = getWidth(element);

	return makeRegion(height, height, 0, width, 0, width);
}

/**
 * Gets the height of the specified node. Scroll height is included.
 */
function getHeight(node: Node) {
	return getSize(node, 'Height');
}

/**
 * Gets the top offset position of the given node. This fixes the `offsetLeft` value of
 * nodes that were translated, which don't take that into account at all. That makes
 * the calculation more expensive though, so if you don't want that to be considered
 * either pass `ignoreTransform` as true or call `offsetLeft` directly on the node.
 */
function getOffsetLeft(node: HTMLElement, ignoreTransform?: boolean) {
	return node.offsetLeft + (ignoreTransform ? 0 : getTranslation(node).left);
}

/**
 * Gets the top offset position of the given node. This fixes the `offsetTop` value of
 * nodes that were translated, which don't take that into account at all. That makes
 * the calculation more expensive though, so if you don't want that to be considered
 * either pass `ignoreTransform` as true or call `offsetTop` directly on the node.
 */
function getOffsetTop(node: HTMLElement, ignoreTransform?: boolean) {
	return node.offsetTop + (ignoreTransform ? 0 : getTranslation(node).top);
}

/**
 * Gets the size of an element and its position relative to the viewport.
 */
function getRegion(node: Node, includeScroll?: boolean) {
	if (!node) {
		return {bottom: 0, height: 0, left: 0, right: 0, top: 0, width: 0};
	}

	if (isDocument(node) || isWindow(node)) {
		return getDocumentRegion(node);
	}

	return makeRegionFromBoundingRect(
		node.getBoundingClientRect(),
		includeScroll
	);
}

/**
 * Gets the scroll left position of the specified node.
 */
function getScrollLeft(node: Node) {
	if (isWindow(node)) {
		return node.pageXOffset;
	}

	if (isDocument(node)) {
		return node.defaultView?.pageXOffset ?? 0;
	}

	return node.scrollLeft;
}

/**
 * Gets the scroll top position of the specified node.
 */
function getScrollTop(node: Node) {
	if (isWindow(node)) {
		return node.pageYOffset;
	}

	if (isDocument(node)) {
		return node.defaultView?.pageYOffset ?? 0;
	}

	return node.scrollTop;
}

/**
 * Gets the height or width of the specified node. Scroll height is
 * included.
 */
function getSize(node: Node, property: HeightOrWidth) {
	if (isWindow(node)) {
		return getClientSize(node, property);
	}

	const clientProperty = `client${property}` as ClientProperty;
	const offsetProperty = `offset${property}` as OffsetProperty;
	const scrollProperty = `scroll${property}` as ScrollProperty;

	if (isDocument(node)) {
		const documentElement = node.documentElement;

		return Math.max(
			node.body[scrollProperty],
			documentElement[scrollProperty],
			node.body[offsetProperty],
			documentElement[offsetProperty],
			documentElement[clientProperty]
		);
	}

	return Math.max(
		node[clientProperty],
		node[scrollProperty],
		node[offsetProperty]
	);
}

/**
 * Gets the transform matrix values for the given node.
 */
function getTransformMatrixValues(node: HTMLElement) {
	const style: VendorPrefixedCSSStyleDeclaration = getComputedStyle(node);

	const transform =
		style.mozTransform || style.msTransform || style.transform;

	if (transform !== 'none') {
		const values = [];
		const regex = /([\d-.\s]+)/g;
		let matches = regex.exec(transform);

		while (matches) {
			values.push(matches[1]);
			matches = regex.exec(transform);
		}

		return values;
	}
}

/**
 * Gets the number of translated pixels for the given node, for both the top and
 * left positions.
 */
function getTranslation(node: HTMLElement) {
	const values = getTransformMatrixValues(node);
	const translation = {
		left: 0,
		top: 0,
	};

	if (values) {
		translation.left = parseFloat(
			values.length === 6 ? values[4] : values[13]
		);
		translation.top = parseFloat(
			values.length === 6 ? values[5] : values[14]
		);
	}

	return translation;
}

/**
 * Gets the width of the specified node. Scroll width is included.
 */
function getWidth(node: Node) {
	return getSize(node, 'Width');
}

/**
 * Tests if a region intersects with another.
 */
function intersectRegion(sourceRectangle: Region, targetRectangle: Region) {
	return intersectRect(
		sourceRectangle.top,
		sourceRectangle.left,
		sourceRectangle.bottom,
		sourceRectangle.right,
		targetRectangle.top,
		targetRectangle.left,
		targetRectangle.bottom,
		targetRectangle.right
	);
}

/**
 * Tests if a rectangle intersects with another.
 *
 * Note that coordinates starts from top to down (y), left to right (x):
 */
function intersectRect(
	x0: number,
	y0: number,
	x1: number,
	y1: number,
	x2: number,
	y2: number,
	x3: number,
	y3: number
) {
	return !(x2 > x1 || x3 < x0 || y2 > y1 || y3 < y0);
}

/**
 * Tests if a region is inside another.
 */
function insideRegion(sourceRectangle: Region, targetRectangle: Region) {
	return (
		targetRectangle.top >= sourceRectangle.top &&
		targetRectangle.bottom <= sourceRectangle.bottom &&
		targetRectangle.right <= sourceRectangle.right &&
		targetRectangle.left >= sourceRectangle.left
	);
}

/**
 * Tests if a region is inside viewport region.
 */
function insideViewport(region: Region) {
	return insideRegion(getRegion(window), region);
}

/**
 * Computes the intersection region between two regions.
 */
function intersection(sourceRectangle: Region, targetRectangle: Region) {
	if (!intersectRegion(sourceRectangle, targetRectangle)) {
		return null;
	}

	const bottom = Math.min(sourceRectangle.bottom, targetRectangle.bottom);
	const right = Math.min(sourceRectangle.right, targetRectangle.right);
	const left = Math.max(sourceRectangle.left, targetRectangle.left);
	const top = Math.max(sourceRectangle.top, targetRectangle.top);

	return makeRegion(bottom, bottom - top, left, right, top, right - left);
}

/**
 * Makes a region object. It's a writable version of DOMRect.
 */
function makeRegion(
	bottom: number,
	height: number,
	left: number,
	right: number,
	top: number,
	width: number
): Region {
	return {
		bottom,
		height,
		left,
		right,
		top,
		width,
	};
}

/**
 * Makes a region from a DOMRect result from `getBoundingClientRect`.
 */
function makeRegionFromBoundingRect(
	rectangle: DOMRect,
	includeScroll: boolean = false
) {
	const deltaX = includeScroll ? getScrollLeft(document) : 0;
	const deltaY = includeScroll ? getScrollTop(document) : 0;

	return makeRegion(
		rectangle.bottom + deltaY,
		rectangle.height,
		rectangle.left + deltaX,
		rectangle.right + deltaX,
		rectangle.top + deltaY,
		rectangle.width
	);
}

/**
 * Align utility. Computes region or best region to align an element with
 * another. Regions are relative to viewport, make sure to use element with
 * position fixed, or position absolute when the element first positioned
 * parent is the body element.
 */

/**
 * Constants that represent the supported positions for `Align`.
 */
const ALIGN_POSITIONS = {
	Bottom: 4,
	BottomCenter: 4,
	BottomLeft: 5,
	BottomRight: 3,
	Left: 6,
	LeftCenter: 6,
	Right: 2,
	RightCenter: 2,
	Top: 0,
	TopCenter: 0,
	TopLeft: 7,
	TopRight: 1,
};

export {ALIGN_POSITIONS};

/**
 * Aligns the element with the best region around alignElement. The best
 * region is defined by clockwise rotation starting from the specified
 * `position`. The element is always aligned in the middle of alignElement
 * axis.
 */
export function align(
	element: HTMLElement,
	alignElement: HTMLElement,
	position: number,
	autoBestAlign: boolean = true
) {
	let bestRegion;

	if (autoBestAlign) {
		const suggestion = suggestAlignBestRegion(
			element,
			alignElement,
			position
		);
		position = suggestion.position;
		bestRegion = suggestion.region;
	}
	else {
		bestRegion = getAlignRegion(element, alignElement, position);
	}

	const computedStyle = window.getComputedStyle(element);
	if (computedStyle.getPropertyValue('position') !== 'fixed') {
		bestRegion.top += window.pageYOffset;
		bestRegion.left += window.pageXOffset;

		let offsetParent: HTMLElement | null = element;

		while (offsetParent !== null) {
			bestRegion.top -= getOffsetTop(offsetParent);
			bestRegion.left -= getOffsetLeft(offsetParent);

			offsetParent =
				offsetParent.offsetParent instanceof HTMLElement
					? offsetParent.offsetParent
					: null;
		}
	}

	element.style.top = bestRegion.top + 'px';
	element.style.left = bestRegion.left + 'px';

	return position;
}

/**
 * Returns the best region to align element with alignElement. This is similar
 * to `suggestAlignBestRegion`, but it only returns the region information,
 * while `suggestAlignBestRegion` also returns the chosen position.
 */
export function getAlignBestRegion(
	element: HTMLElement,
	alignElement: HTMLElement,
	position: number
) {
	return suggestAlignBestRegion(element, alignElement, position).region;
}

/**
 * Returns the region to align element with alignElement. The element is
 * always aligned in the middle of alignElement axis.
 */
export function getAlignRegion(
	element: HTMLElement,
	alignElement: HTMLElement,
	position: number
) {
	const targetRectangle = getRegion(alignElement);
	const sourceRectangle = getRegion(element);
	let top = 0;
	let left = 0;

	switch (position) {
		case ALIGN_POSITIONS.TopCenter:
			top = targetRectangle.top - sourceRectangle.height;
			left =
				targetRectangle.left +
				targetRectangle.width / 2 -
				sourceRectangle.width / 2;
			break;
		case ALIGN_POSITIONS.RightCenter:
			top =
				targetRectangle.top +
				targetRectangle.height / 2 -
				sourceRectangle.height / 2;
			left = targetRectangle.left + targetRectangle.width;
			break;
		case ALIGN_POSITIONS.BottomCenter:
			top = targetRectangle.bottom;
			left =
				targetRectangle.left +
				targetRectangle.width / 2 -
				sourceRectangle.width / 2;
			break;
		case ALIGN_POSITIONS.LeftCenter:
			top =
				targetRectangle.top +
				targetRectangle.height / 2 -
				sourceRectangle.height / 2;
			left = targetRectangle.left - sourceRectangle.width;
			break;
		case ALIGN_POSITIONS.TopRight:
			top = targetRectangle.top - sourceRectangle.height;
			left = targetRectangle.right - sourceRectangle.width;
			break;
		case ALIGN_POSITIONS.BottomRight:
			top = targetRectangle.bottom;
			left = targetRectangle.right - sourceRectangle.width;
			break;
		case ALIGN_POSITIONS.BottomLeft:
			top = targetRectangle.bottom;
			left = targetRectangle.left;
			break;
		case ALIGN_POSITIONS.TopLeft:
			top = targetRectangle.top - sourceRectangle.height;
			left = targetRectangle.left;
			break;
		default:
			break;
	}

	return {
		bottom: top + sourceRectangle.height,
		height: sourceRectangle.height,
		left,
		right: left + sourceRectangle.width,
		top,
		width: sourceRectangle.width,
	};
}

/**
 * Looks for the best region for aligning the given element. The best
 * region is defined by clockwise rotation starting from the specified
 * `position`. The element is always aligned in the middle of alignElement
 * axis.
 */
export function suggestAlignBestRegion(
	element: HTMLElement,
	alignElement: HTMLElement,
	position: number
) {
	let bestArea = 0;
	let bestPosition = position;
	let bestRegion = getAlignRegion(element, alignElement, bestPosition);
	let tryPosition = bestPosition;
	let tryRegion = bestRegion;
	const viewportRegion = getRegion(window);

	for (let i = 0; i < 8; ) {
		if (intersectRegion(viewportRegion, tryRegion)) {
			const visibleRegion = intersection(viewportRegion, tryRegion);

			if (visibleRegion) {
				const area = visibleRegion.width * visibleRegion.height;
				if (area > bestArea) {
					bestArea = area;
					bestRegion = tryRegion;
					bestPosition = tryPosition;
				}
				if (insideViewport(tryRegion)) {
					break;
				}
			}
		}
		tryPosition = (position + ++i) % 8;
		tryRegion = getAlignRegion(element, alignElement, tryPosition);
	}

	return {
		position: bestPosition,
		region: bestRegion,
	};
}
