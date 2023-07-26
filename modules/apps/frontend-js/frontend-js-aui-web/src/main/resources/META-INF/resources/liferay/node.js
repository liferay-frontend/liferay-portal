/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * The Liferay Node Utility
 *
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @module liferay-node
 */

AUI.add(
	'liferay-node',
	(A) => {
		const getRegExp = A.DOM._getRegExp;

		const prefix = A.Lang.String.prefix;

		const formatSelectorNS = function (ns, selector) {
			return selector.replace(
				getRegExp('(#|\\[id=(\\"|\\\'))(?!' + ns + ')', 'g'),
				'$1' + ns
			);
		};

		A.mix(A.Node.prototype, {
			allNS(ns, selector) {
				const instance = this;

				return instance.all(formatSelectorNS(ns, selector));
			},

			oneNS(ns, selector) {
				const instance = this;

				return instance.one(formatSelectorNS(ns, selector));
			},
		});

		A.Node.formatSelectorNS = formatSelectorNS;

		A.queryNS = function (ns, selector, methodName) {
			return A[methodName || 'one'](formatSelectorNS(ns, selector));
		};

		A.oneNS = A.queryNS;

		A.allNS = function (ns, selector) {
			return A.queryNS(ns, selector, 'all');
		};

		A.byIdNS = function (ns, id) {
			return A.one('#' + prefix(ns, id));
		};
	},
	'',
	{
		requires: ['aui-node-base'],
	}
);
