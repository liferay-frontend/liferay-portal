/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * The History Utility, a utility for SPA.
 *
 * @deprecated As of Mueller (7.2.x), replaced by senna.js
 * @module liferay-history
 */

AUI.add(
	'liferay-history',
	(A) => {
		const Lang = A.Lang;
		const QueryString = A.QueryString;

		const isValue = Lang.isValue;

		const WIN = A.config.win;

		const LOCATION = WIN.location;

		const History = A.Component.create({
			EXTENDS: A.History,

			NAME: 'liferayhistory',

			PAIR_SEPARATOR: '&',

			VALUE_SEPARATOR: '=',

			prototype: {
				_parse: A.cached((str) => {
					return QueryString.parse(
						str,
						History.PAIR_SEPARATOR,
						History.VALUE_SEPARATOR
					);
				}),

				get(key) {
					const instance = this;

					let value = History.superclass.get.apply(this, arguments);

					if (!isValue(value) && isValue(key)) {
						const query = LOCATION.search;

						const queryMap = instance._parse(query.substr(1));

						if (
							Object.prototype.hasOwnProperty.call(queryMap, key)
						) {
							value = queryMap[key];
						}
					}

					return value;
				},
			},
		});

		Liferay.History = History;
	},
	'',
	{
		requires: ['querystring-parse-simple'],
	}
);
