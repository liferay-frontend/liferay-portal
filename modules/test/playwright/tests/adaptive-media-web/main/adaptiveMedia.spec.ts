/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../../fixtures/loginTest';
import {GlobalMenuPage} from '../../../pages/product-navigation-applications-menu/GlobalMenuPage';

const test = mergeTests(loginTest());

test('Warns against using Adaptive Media with AI-generated content', async ({
	page,
}) => {
	await new GlobalMenuPage(page).goToControlPanel('Adaptive Media');

	await expect(
		page
			.locator('.alert-warning')
			.filter({
				hasText: 'is not intended for use with AI-generated content',
			})
	).toBeVisible();
});
