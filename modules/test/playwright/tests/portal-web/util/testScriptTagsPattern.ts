/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, test} from '@playwright/test';

export default async function testScriptTagsPattern(
	page: Page,
	pattern: RegExp
) {
	const scripts = await page.locator('script[src]').all();

	for (const script of scripts) {
		const src = await script.getAttribute('src');

		await test.step(`Check <script src="${src}">`, () =>
			expect(src).toMatch(pattern));
	}
}
