/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * The Autocomplete Input Caretoffset Sel Component.
 *
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 * @module liferay-autocomplete-input-caretoffset-sel
 */

AUI.add(
	'liferay-autocomplete-input-caretoffset-sel',
	(A) => {
		const Lang = A.Lang;

		const DOC = A.config.doc;

		const AutcompleteInputCaretOffset = function () {};

		AutcompleteInputCaretOffset.prototype = {
			_getCaretOffset(node) {
				const instance = this;

				node = node || instance.get('inputNode');

				node.focus();

				const range = DOC.selection.createRange();

				const xy = node.getXY();

				return {
					x: range.boundingLeft - xy[0],
					y:
						Lang.toInt(range.boundingTop) -
						xy[1] +
						node.get('scrollTop') +
						DOC.documentElement.scrollTop,
				};
			},
		};

		A.Base.mix(Liferay.AutoCompleteTextarea, [AutcompleteInputCaretOffset]);
	},
	'',
	{
		requires: ['liferay-autocomplete-textarea'],
	}
);
