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

export default function transformInlineCSS(data) {
	const parser = new DOMParser();

	const htmlDoc = parser.parseFromString(data, 'text/html');

	const imageNodes = htmlDoc.getElementsByTagName('img');

	for (let i = 0; i < imageNodes.length; i++) {
		const imageNode = imageNodes[i];

		if (
			imageNode.getAttribute('width') !== null &&
			imageNode.getAttribute('height') !== null
		) {
			continue;
		}

		const style = imageNode.getAttribute('style');

		if (style === null || style === '') {
			continue;
		}

		const styleHeight = imageNode.style.removeProperty('height');

		if (styleHeight) {
			imageNode.setAttribute('height', styleHeight.replace('px', ''));
		}

		const styleWidth = imageNode.style.removeProperty('width');

		if (styleWidth) {
			imageNode.setAttribute('width', styleWidth.replace('px', ''));
		}
	}

	return htmlDoc.body.innerHTML;
}
