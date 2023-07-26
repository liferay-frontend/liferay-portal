/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-search-bar',
	(A) => {
		const SearchBar = function (form) {
			if (!form) {
				return;
			}

			const instance = this;

			instance.form = form;

			instance.form.on('submit', A.bind(instance._onSubmit, instance));

			const emptySearchInput = instance.form.one(
				'.search-bar-empty-search-input'
			);

			instance.emptySearchEnabled =
				emptySearchInput && emptySearchInput.val() === 'true';

			instance.keywordsInput = instance.form.one(
				'.search-bar-keywords-input'
			);

			instance.resetStartPage = instance.form.one(
				'.search-bar-reset-start-page'
			);

			instance.scopeSelect = instance.form.one(
				'.search-bar-scope-select'
			);
		};

		A.mix(SearchBar.prototype, {
			_onSubmit(event) {
				const instance = this;

				event.stopPropagation();

				instance.search();
			},

			getKeywords() {
				const instance = this;

				if (!instance.keywordsInput) {
					return '';
				}

				const keywords = instance.keywordsInput.val();

				return keywords.replace(/^\s+|\s+$/, '');
			},

			isSubmitEnabled() {
				const instance = this;

				return (
					instance.getKeywords() !== '' || instance.emptySearchEnabled
				);
			},

			search() {
				const instance = this;

				if (instance.isSubmitEnabled()) {
					const searchURL = instance.form.get('action');

					const queryString = instance.updateQueryString(
						document.location.search
					);

					document.location.href = searchURL + queryString;
				}
			},

			updateQueryString(queryString) {
				const instance = this;

				const searchParams = new URLSearchParams(queryString);

				if (instance.keywordsInput) {
					searchParams.set(
						instance.keywordsInput.get('name'),
						instance.getKeywords()
					);
				}

				if (instance.resetStartPage) {
					searchParams.delete(instance.resetStartPage.get('name'));
				}

				if (instance.scopeSelect) {
					searchParams.set(
						instance.scopeSelect.get('name'),
						instance.scopeSelect.val()
					);
				}

				searchParams.delete('p_p_id');
				searchParams.delete('p_p_state');
				searchParams.delete('start');

				return '?' + searchParams.toString();
			},
		});

		Liferay.namespace('Search').SearchBar = SearchBar;
	},
	''
);
