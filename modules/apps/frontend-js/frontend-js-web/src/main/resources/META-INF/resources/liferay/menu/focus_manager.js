/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {all, guid} from './dom';

const SELECTOR_DESCENDANTS = 'li:not(.hide) a,input';

const SELECTOR_LIST_ITEM = 'li';

/**
 * The subset of <code>A.Plugin.NodeFocusManager</code> the menu configured:
 * circular arrow key navigation over <code>li:not(.hide) a,input</code>, a
 * <code>focus</code> class on the active descendant, and
 * <code>aria-activedescendant</code> kept in step on the list.
 */
export default class FocusManager {
	constructor(containerElement, {onExit}) {
		this._activeIndex = -1;
		this._containerElement = containerElement;
		this._descendants = [];
		this._focused = false;
		this._onExit = onExit;

		this._onFocusIn = () => {
			this._focused = true;
		};
		this._onFocusOut = () => {
			this._focused = false;
		};
		this._onKeyDown = (event) => this._handleKeyDown(event);
		this._onMouseEnter = (event) => {
			const listItem = event.target.closest(SELECTOR_LIST_ITEM);

			if (!this._focused || !listItem) {
				return;
			}

			const index = this._descendants.indexOf(
				listItem.querySelector('a')
			);

			if (index > -1) {
				this.focus(index);
			}
		};

		containerElement.addEventListener('focusin', this._onFocusIn);
		containerElement.addEventListener('focusout', this._onFocusOut);
		containerElement.addEventListener('keydown', this._onKeyDown);
		containerElement.addEventListener('mouseover', this._onMouseEnter);

		this.refresh();
	}

	destroy() {
		const containerElement = this._containerElement;

		containerElement.removeEventListener('focusin', this._onFocusIn);
		containerElement.removeEventListener('focusout', this._onFocusOut);
		containerElement.removeEventListener('keydown', this._onKeyDown);
		containerElement.removeEventListener('mouseover', this._onMouseEnter);
	}

	focus(index) {
		const descendant = this._descendants[index];

		if (!descendant) {
			return;
		}

		for (const item of this._descendants) {
			item.classList.remove('focus');
		}

		descendant.classList.add('focus');

		descendant.focus();

		this._activeIndex = index;
		this._focused = true;

		const listElement = this._containerElement.querySelector('ul');

		if (listElement) {
			listElement.setAttribute('aria-activedescendant', guid(descendant));
		}
	}

	get focused() {
		return this._focused;
	}

	refresh() {
		this._descendants = all(this._containerElement, SELECTOR_DESCENDANTS);

		if (this._activeIndex >= this._descendants.length) {
			this._activeIndex = -1;
		}
	}

	_handleKeyDown(event) {
		const key = event.key;

		if (key === 'Escape' || key === 'Tab') {
			this._onExit();

			return;
		}

		if (key !== 'ArrowDown' && key !== 'ArrowUp') {
			return;
		}

		event.preventDefault();

		const total = this._descendants.length;

		if (!total) {
			return;
		}

		const offset = key === 'ArrowDown' ? 1 : -1;

		this.focus((this._activeIndex + offset + total) % total);
	}
}
