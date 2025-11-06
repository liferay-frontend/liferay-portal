/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {liferayConfig} from '../../../liferay.config';
import testImportMapsPattern from '../util/testImportMapsPattern';
import testScriptTagsPattern from '../util/testScriptTagsPattern';

test(
	`JavaScript URLs honor CDN, proxy and Portal's web context name`,
	{tag: '@LPD-66044'},
	async ({page}) => {
		await page.goto(`${liferayConfig.environment.baseUrl}/dxp`);

		const pattern = /^http:\/\/cdn:8080\/proxy\/dxp\//;

		await test.step(`CDN followed by proxy and Portal's web context name is referenced in import maps`, async () =>
			testImportMapsPattern(page, pattern));

		await test.step(`CDN followed by proxy and Portal's web context name is referenced in <script> tags`, async () =>
			testScriptTagsPattern(page, pattern));
	}
);
