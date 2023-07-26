/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import launcher from '../../src/main/resources/META-INF/resources/components/account_selector/entry';

import '../../src/main/resources/META-INF/resources/styles/main.scss';

launcher('account_selector', 'account-selector', {
	commerceChannelId: 24324,
	createNewOrderURL: '/asdasdasd',
	currentCommerceAccount: {
		id: 42332,
		name: 'My AccountName',
	},
	currentCommerceOrder: {
		id: 34234,
		orderStatusInfo: {
			label_i18n: 'Completed',
		},
	},
	selectOrderURL: '/test-url/{id}',
	setCurrentAccountURL: '/account-selector/setCurrentAccounts',
});
