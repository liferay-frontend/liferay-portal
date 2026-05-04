/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../../../fixtures/pageEditorPagesTest';
import {waitForFDS} from '../../../../../utils/waitFor';
import {fdsSamplePageTest} from '../../fixtures/fdsSamplePageTest';

const test = mergeTests(
	apiHelpersTest,
	fdsSamplePageTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest
);

test(
	'Liferay Sample Custom Element 7 syncs search with FDS Sample (Classic)',
	{
		tag: ['@LPD-86378'],
	},
	async ({fdsSamplePage, page, pageEditorPage, site}) => {
		const customElement = page.locator('liferay-sample-custom-element-7');
		const customElementInput = customElement.locator('input');
		const customElementSearchButton = customElement.getByRole('button', {
			name: 'Search',
		});

		const fdsSearchInput = fdsSamplePage.managementToolbar.searchInput;
		const fdsSearchButton = fdsSamplePage.managementToolbar.searchButton;

		const {url} = await test.step(
			'Create a page with the FDS Sample widget',
			async () => fdsSamplePage.setupFDSSampleWidget({site})
		);

		await test.step(
			'Add the Custom Element 7 widget to the page',
			async () => {
				await page.goto(`${url}?p_l_mode=edit`);

				await pageEditorPage.addWidget(
					'Client Extensions',
					'Liferay Sample Custom Element 7'
				);

				await pageEditorPage.publishPage();
			}
		);

		await test.step('Switch to the Classic FDS tab', async () => {
			await page.goto(url);

			await fdsSamplePage.selectTab('Classic');

			await waitForFDS({page});
		});

		await test.step(
			'Custom Element becomes ready once the FDS atom is registered',
			async () => {
				await expect(customElementInput).toBeEnabled();
				await expect(customElementSearchButton).toBeEnabled();
			}
		);

		await test.step(
			'Searching from the Custom Element filters the FDS',
			async () => {
				await customElementInput.fill('Sample55');
				await customElementSearchButton.click();

				await expect(fdsSearchInput).toHaveValue('Sample55');
			}
		);

		await test.step(
			'Searching from the FDS reflects in the Custom Element input',
			async () => {
				await fdsSearchInput.fill('Sample22');
				await fdsSearchButton.click();

				await expect(customElementInput).toHaveValue('Sample22');
			}
		);
	}
);
