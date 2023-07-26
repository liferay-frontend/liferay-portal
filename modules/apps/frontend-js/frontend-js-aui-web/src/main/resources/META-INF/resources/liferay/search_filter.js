/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-search-filter',
	(A) => {
		const Lang = A.Lang;

		const SearchImpl = A.Component.create({
			AUGMENTS: [A.AutoCompleteBase],

			EXTENDS: A.Base,

			NAME: 'searchimpl',

			prototype: {
				initializer() {
					this._bindUIACBase();
					this._syncUIACBase();
				},
			},
		});

		const SearchFilter = A.Component.create({
			ATTRS: {
				minQueryLength: {
					validator: Lang.isNumber,
					value: 0,
				},

				nodeList: {
					setter: A.one,
				},

				nodeSelector: {
					validator: Lang.isString,
				},

				queryDelay: {
					validator: Lang.isNumber,
					value: 300,
				},

				resultFilters: {
					setter: '_setResultFilters',
					value: 'phraseMatch',
				},

				resultTextLocator: {
					setter: '_setLocator',
					value: 'search',
				},

				searchDataLocator: {
					value: 'data-search',
				},
			},

			EXTENDS: SearchImpl,

			NAME: 'searchfilter',

			prototype: {
				initializer() {
					const instance = this;

					const nodeList = instance.get('nodeList');

					if (nodeList) {
						const nodeSelector = instance.get('nodeSelector');

						const nodes = nodeList.all(nodeSelector);

						const searchDataLocator = instance.get(
							'searchDataLocator'
						);

						const searchData = [];

						nodes.each((item) => {
							searchData.push({
								node: item,
								search: item.attr(searchDataLocator),
							});
						});

						instance.set('source', searchData);

						instance._nodes = nodes;
						instance._searchData = searchData;
					}
				},
			},
		});

		Liferay.SearchFilter = SearchFilter;
	},
	'',
	{
		requires: ['aui-base', 'autocomplete-base', 'autocomplete-filters'],
	}
);
