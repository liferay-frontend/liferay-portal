/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {all, create, hide, show, toElement} from './dom';

const TPL_INPUT_FILTER =
	'<li class="btn-toolbar search-panel">' +
	'<div class="form-group">' +
	'<input class="col-md-12 field focus menu-item-filter search-query" ' +
	'type="text" />' +
	'</div>' +
	'</li>';

/**
 * The live search a menu grows once it holds more items than its
 * <code>max-display-items-N</code> class allows.
 *
 * AlloyUI built this out of <code>A.AutoCompleteBase</code> configured with
 * <code>resultFilters: 'phraseMatch'</code> over the text of every item, which
 * is all this reimplements. The <code>minQueryLength</code>,
 * <code>queryDelay</code>, <code>resultFilters</code>,
 * <code>resultTextLocator</code> and <code>source</code> options the
 * constructor used to accept are still tolerated, so that existing callers of
 * the public <code>Liferay.MenuFilter</code> keep working, but they no longer
 * have an effect: the source is always the list the filter is attached to.
 */
export default class MenuFilter {
	constructor({content, menu}) {
		this._listElement = toElement(content);
		this._menu = menu;

		this._items = all(this._listElement, 'li').map((element) => {
			const textElement = element.querySelector('.taglib-text-icon');

			return {
				element,
				name: (textElement
					? textElement.textContent
					: element.textContent
				)
					.trim()
					.toLowerCase(),
			};
		});

		const filterElement = create(TPL_INPUT_FILTER);

		this._inputElement = filterElement.querySelector('input');

		this._inputElement.setAttribute(
			'placeholder',
			Liferay.Language.get('search')
		);

		// The document level listener that closes the active menu would
		// otherwise fire the moment the field is clicked. AlloyUI called this
		// `swallowEvent`.

		this._inputElement.addEventListener('click', (event) =>
			event.stopPropagation()
		);

		this._inputElement.addEventListener('input', () => this._filterMenu());

		this._listElement.prepend(filterElement);
	}

	reset() {
		this._inputElement.value = '';

		for (const item of this._items) {
			show(item.element);
		}
	}

	_filterMenu() {
		const query = this._inputElement.value.trim().toLowerCase();

		for (const item of this._items) {
			if (!query || item.name.indexOf(query) > -1) {
				show(item.element);
			}
			else {
				hide(item.element);
			}
		}

		if (this._menu) {
			this._menu.refreshFocusManager();
		}
	}
}
