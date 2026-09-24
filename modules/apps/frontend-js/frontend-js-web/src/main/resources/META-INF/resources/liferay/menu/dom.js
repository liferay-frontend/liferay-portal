/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

let counter = 0;

/**
 * Returns every descendant of <code>element</code> matching
 * <code>selector</code> as a real array, so that it can be safely iterated
 * while the DOM is being mutated.
 */
export function all(element, selector) {
	return Array.from(element.querySelectorAll(selector));
}

/**
 * Builds a detached element out of an HTML string.
 */
export function create(html) {
	const template = document.createElement('template');

	template.innerHTML = html.trim();

	return template.content.firstElementChild;
}

/**
 * Reproduces AlloyUI's <code>Node.generateID()</code>: returns the element's
 * id, assigning a generated one first when it has none. These ids are not mere
 * bookkeeping, <code>aria-labelledby</code> and
 * <code>aria-activedescendant</code> point at them.
 */
export function guid(element) {
	if (!element.id) {
		element.id = 'lfr-menu-' + ++counter;
	}

	return element.id;
}

/**
 * AlloyUI's <code>Node.hide()</code> both adds the <code>hide</code> CSS class
 * and sets an inline <code>display: none</code>. Both are reproduced here
 * because the live search filters items by CSS class while the focus manager
 * skips them through <code>li:not(.hide)</code>.
 */
export function hide(element) {
	element.classList.add('hide');

	element.style.display = 'none';
}

export function show(element) {
	element.classList.remove('hide');

	element.style.display = '';
}

/**
 * Accepts a CSS selector, a DOM element, an AlloyUI node or an AlloyUI node
 * list. The AlloyUI branches are still needed because
 * <code>Liferay.Menu.handleFocus</code> is public API and the Dynamic Data
 * Mapping translation manager calls it with an AlloyUI node.
 */
export function toElement(elementOrSelector) {
	if (!elementOrSelector) {
		return null;
	}

	if (typeof elementOrSelector === 'string') {
		return document.querySelector(elementOrSelector);
	}

	if (elementOrSelector._node) {
		return elementOrSelector._node;
	}

	if (elementOrSelector._nodes) {
		return elementOrSelector._nodes[0] || null;
	}

	return elementOrSelector;
}
