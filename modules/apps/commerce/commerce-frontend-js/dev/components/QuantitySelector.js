/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import launcher from '../../src/main/resources/META-INF/resources/components/quantity_selector/entry';

import '../../src/main/resources/META-INF/resources/styles/main.scss';

launcher('quantity-selector', 'quantity-selector', {
	onUpdate: (quantity) =>
		new Promise((resolve) => {
			setTimeout(() => {
				resolve(quantity);
			}, 0);
		}),
});
