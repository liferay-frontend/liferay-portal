/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import launcher from '../../src/main/resources/META-INF/resources/components/step_tracker/entry';

import '../../src/main/resources/META-INF/resources/styles/main.scss';

launcher('step_tracker', 'step-tracker', {
	steps: [
		{
			id: 'received',
			label: 'Received asd asd',
			state: 'completed',
		},
		{
			id: 'confirmed',
			label: 'Confirmed',
			state: 'active',
		},
		{
			id: 'trasmitted',
			label: 'Trasmitted',
			state: 'inactive',
		},
		{
			id: 'shipped',
			label: 'Shipped',
			state: 'inactive',
		},
		{
			id: 'completed',
			label: 'Completed',
			state: 'inactive',
		},
	],
});
