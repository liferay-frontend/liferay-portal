/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {screen} from '@testing-library/react';

import {openToast} from '../../src/main/resources/META-INF/resources/util/openToast';

function getIconSymbol() {
	const alert = screen.getByRole('alert');

	const use = alert.querySelector('.alert-indicator use');

	return use.getAttribute('href').split('#')[1];
}

describe('openToast', () => {
	let toast;

	afterEach(() => {
		toast.dispose();
	});

	it('shows the times-circle-full icon for errors', () => {
		toast = openToast('error', 'Error', 'Something went wrong.');

		expect(getIconSymbol()).toBe('times-circle-full');
	});

	it('shows the warning-full icon for warnings', () => {
		toast = openToast('warn', 'Warning', 'This is taking too long.');

		expect(getIconSymbol()).toBe('warning-full');
	});
});
