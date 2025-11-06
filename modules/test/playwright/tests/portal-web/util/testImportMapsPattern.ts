/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, test} from "@playwright/test";

export default async function testImportMapsPattern(page: Page, pattern: RegExp) {
    const json = JSON.parse(
        await page.locator('script[type="importmap"]').textContent()
    );

    for (const url of Object.values(json.imports)) {
        await test.step(
            `Check URL "${url}"`,
            () => expect(url).toMatch(pattern)
        );
    }
}