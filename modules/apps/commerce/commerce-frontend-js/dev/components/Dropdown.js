/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import launcher from '../../src/main/resources/META-INF/resources/components/dropdown/entry';

import '../../src/main/resources/META-INF/resources/styles/main.scss';

const props = {
	items: [
		{
			href: '/side-panel/delete',
			icon: 'plus',
			label: 'Delete',
		},
		{
			href: '/side-panel/email',
			icon: 'plus',
			label: 'Add',
			order: 20,
			target: 'modal',
		},
		{
			href: '/test',
			icon: 'view',
			label: 'View',
			order: 1,
		},
	],
};

launcher('dropdownId', 'dropdown-root-id', props);
