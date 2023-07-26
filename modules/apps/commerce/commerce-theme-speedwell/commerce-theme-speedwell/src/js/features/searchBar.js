/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function (w) {
	const searchToggles = w.document.querySelectorAll('.js-toggle-search');
	const HAS_SEARCH_CLASS = 'has-search';
	const IS_OPEN_CLASS = 'is-open';
	const IS_ACTIVE_CLASS = 'is-active';
	const SEARCHBAR_SELECTOR = '.speedwell-search';

	let searchBarElement;

	const searchBar = w.Liferay.component('search-bar');

	if (searchBar) {
		searchBarElement = w.document.querySelector(SEARCHBAR_SELECTOR);

		searchBar.on('toggled', (status) => {
			searchToggles.forEach((element) => {
				element.classList.toggle(IS_ACTIVE_CLASS, status);
			});

			w.document
				.getElementById('speedwell')
				.classList.toggle(HAS_SEARCH_CLASS, status);

			if (searchBarElement) {
				searchBarElement.classList.toggle(IS_OPEN_CLASS, status);
			}
		});
	}
})(window);
