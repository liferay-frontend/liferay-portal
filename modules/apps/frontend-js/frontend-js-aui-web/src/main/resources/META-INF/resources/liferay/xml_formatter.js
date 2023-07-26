/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * The XML Formatter Utility
 *
 * @deprecated As of Athanasius (7.3.x), replaced by Liferay.Util.formatXML
 * @module liferay-xml-formatter
 */

AUI.add(
	'liferay-xml-formatter',
	(A) => {
		const Lang = A.Lang;

		const XMLFormatter = A.Component.create({
			ATTRS: {
				lineIndent: {
					validator: Lang.isString,
					value: '\r\n',
				},

				tagIndent: {
					validator: Lang.isString,
					value: '\t',
				},
			},

			EXTENDS: A.Base,

			NAME: 'liferayxmlformatter',

			prototype: {
				format(content) {
					const instance = this;

					const tagIndent = instance.get('tagIndent');

					const lineIndent = instance.get('lineIndent');

					return Liferay.Util.formatXML(content, {
						lineIndent,
						tagIndent,
					});
				},
			},
		});

		Liferay.XMLFormatter = XMLFormatter;
	},
	'',
	{
		requires: ['aui-base'],
	}
);
