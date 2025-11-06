/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {liferayConfig} from '../../../liferay.config';
import testImportMapsPattern from '../util/testImportMapsPattern';
import testScriptTagsPattern from '../util/testScriptTagsPattern';

test(
	'JavaScript URLs honor proxy',
	{tag: '@LPD-66044'},
	async ({page}) => {
		await page.goto(liferayConfig.environment.baseUrl);

		const pattern = /^\/proxy\//;

		await test.step('Proxy is referenced in import maps', async () =>
			testImportMapsPattern(page, pattern));

		await test.step('Proxy is referenced in <script> tags', async () =>
			testScriptTagsPattern(page, pattern));
	}
);
