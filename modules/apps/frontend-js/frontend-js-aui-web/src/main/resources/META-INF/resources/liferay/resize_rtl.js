/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * The Resize RTL Component.
 *
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 * @module liferay-resize-rtl
 */

AUI.add(
	'liferay-resize-rtl',
	(A) => {
		const RULES = A.Resize.RULES;

		const tmpL = RULES.l;

		RULES.l = RULES.r;
		RULES.r = tmpL;
	},
	'',
	{
		requires: ['resize-base'],
	}
);
