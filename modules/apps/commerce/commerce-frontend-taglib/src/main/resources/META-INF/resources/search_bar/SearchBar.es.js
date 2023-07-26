/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import 'clay-icon';
import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import template from './SearchBar.soy';

class SearchBar extends Component {
	created() {
		this._handleDocumentKeypress = this._handleDocumentKeypress.bind(this);
		this._handleClickOutside = this._handleClickOutside.bind(this);
		this.toggle = this.toggle.bind(this);
		document.addEventListener('keydown', this._handleDocumentKeypress);
		this._addOpenButtonListener();
	}

	detached() {
		document.removeEventListener('keydown', this._handleDocumentKeypress);
	}

	_addOpenButtonListener() {
		return Array.from(document.querySelectorAll('.js-toggle-search')).map(
			(element) => {
				return element.addEventListener('click', this.toggle);
			}
		);
	}

	_removeOpenButtonListener() {
		return Array.from(document.querySelectorAll('.js-toggle-search')).map(
			(element) => {
				return element.removeEventListener('click', this.toggle);
			}
		);
	}

	_handleClickOutside(event) {
		const suggestions = document.querySelector('.commerce-suggestions');
		if (
			!(
				this.element.contains(event.target) ||
				(suggestions && suggestions.contains(event.target))
			)
		) {
			this.toggle();
		}
	}

	_handleDocumentKeypress(event) {
		if (this.active && event.key === 'Escape') {
			this.close();
		}

		const noModalsActive = !Array.from(
			document.querySelectorAll('.modal.show')
		).length;

		if (
			!this.active &&
			event.key === '/' &&
			event.target.tagName !== 'INPUT' &&
			noModalsActive
		) {
			event.preventDefault();
			this.open();
		}
	}

	_handleResetQuery(_evt) {
		this._updateQuery('');
	}

	_handleKeyDown(event) {
		if (event.key === 'ArrowDown' || event.key === 'ArrowUp') {
			event.preventDefault();
		}
	}

	_handleKeyUp(event) {
		this._updateQuery(event.target.value);
	}

	_handleSubmit(event) {
		event.preventDefault();

		window.Liferay.fire('search-term-submit', {
			term: this.query,
		});
	}

	_updateQuery(query) {
		if (query !== this.query) {
			this.open();

			this.query = query;

			window.Liferay.fire('search-term-update', {
				term: query,
			});
		}
	}

	syncActive() {
		if (this.active) {
			window.addEventListener('click', this._handleClickOutside);
			setTimeout(() => {
				this._removeOpenButtonListener();
				this.refs.searchInput.focus();
			}, 0);
		}
		else {
			window.removeEventListener('click', this._handleClickOutside);
			setTimeout(() => {
				this._addOpenButtonListener();
				this.refs.searchInput.blur();
			}, 0);
		}
		this.emit('toggled', this.active);
	}

	open() {
		this.active = true;
	}

	close() {
		this.active = false;
	}

	toggle() {
		return this.active ? this.close() : this.open();
	}
}

Soy.register(SearchBar, template);

SearchBar.STATE = {
	active: Config.bool(),
	placeholder: Config.value(''),
	query: Config.string(),
};

export {SearchBar};
export default SearchBar;
