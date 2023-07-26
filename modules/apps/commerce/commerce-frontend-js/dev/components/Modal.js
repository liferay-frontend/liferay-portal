/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import modalLauncher from '../../src/main/resources/META-INF/resources/components/modal/entry';
import {OPEN_MODAL} from '../../src/main/resources/META-INF/resources/utilities/eventsDefinitions';
import launcher from '../../src/main/resources/META-INF/resources/utilities/launcher';

const props = {
	actions: [
		{
			definition: 'save',
		},
	],
	closeOnSubmit: true,
	id: 'test-modal-id',
	showCancel: true,
	size: 'full-screen',
	submitLabel: 'Create',
	title: 'Title',
	url: 'http://localhost:9000/modal-content.html',
};

modalLauncher('modal', 'modal-root', props);

launcher(
	() => (
		<button
			className="btn btn-primary"
			onClick={() => Liferay.fire(OPEN_MODAL, {id: 'test-modal-id'})}
		>
			Open modal
		</button>
	),
	'modal-trigger',
	'modal-trigger-root'
);
