/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import compareCheckboxLauncher from '../../src/main/resources/META-INF/resources/components/compare_checkbox/entry';
import miniCompareLauncher from '../../src/main/resources/META-INF/resources/components/mini_compare/entry';

import '../../src/main/resources/META-INF/resources/styles/main.scss';

miniCompareLauncher('miniCompare', 'mini-compare-root', {
	commerceChannelGroupId: 1234,
	compareProductsURL: '#',
	itemsLimit: 4,
	portletNamespace: 'portletNamespace',
});

compareCheckboxLauncher('miniCompare1', 'compare-checkbox-root-1', {
	itemId: '001',
	label: 'Compare',
	pictureUrl: 'https://via.placeholder.com/150',
});

compareCheckboxLauncher('miniCompare2', 'compare-checkbox-root-2', {
	itemId: '002',
	label: 'Compare',
	pictureUrl: 'https://via.placeholder.com/150',
});

compareCheckboxLauncher('miniCompare3', 'compare-checkbox-root-3', {
	itemId: '003',
	label: 'Compare',
	pictureUrl: 'https://via.placeholder.com/150',
});

compareCheckboxLauncher('miniCompare4', 'compare-checkbox-root-4', {
	itemId: '004',
	label: 'Compare',
	pictureUrl: 'https://via.placeholder.com/150',
});

compareCheckboxLauncher('miniCompare5', 'compare-checkbox-root-5', {
	itemId: '005',
	label: 'Compare',
	pictureUrl: 'https://via.placeholder.com/150',
});

compareCheckboxLauncher('miniCompare6', 'compare-checkbox-root-6', {
	itemId: '006',
	label: 'Compare',
	pictureUrl: 'https://via.placeholder.com/150',
});
