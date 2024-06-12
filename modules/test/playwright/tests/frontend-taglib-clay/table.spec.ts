/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {liferayConfig} from '../../liferay.config';
import getRandomString from '../../utils/getRandomString';
import getPageDefinition from '../layout-content-page-editor-web/utils/getPageDefinition';
import getWidgetDefinition from '../layout-content-page-editor-web/utils/getWidgetDefinition';
import hasOverflow from './utils/hasOverflow';

export const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest()
);

test.describe('Table component', () => {
	test('Check how text inside a table cell is wrapped.', async ({
		apiHelpers,
		page,
		site,
	}) => {
		let layout: Layout;

		await test.step('Create a content site and the frontend taglib clay widget', async () => {
			const widgetDefinition = getWidgetDefinition({
				id: getRandomString(),
				widgetName:
					'com_liferay_clay_sample_web_portlet_ClaySamplePortlet',
			});

			layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([widgetDefinition]),
				siteId: site.id,
				title: getRandomString(),
			});
		});

		await test.step('Select Table tab ', async () => {
			await page.goto(
				`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			const tabHeading = page.getByRole('tablist').getByText('Table');

			await expect(tabHeading).toBeInViewport();

			await tabHeading.click();

			await page
				.getByRole('heading', {name: 'Wrap / No wrap'})
				.waitFor({state: 'visible'});
		});

		await test.step('Default long text force line break', async () => {
			const textOverflowsCell = await page
				.getByTestId('longTextWrap')
				.evaluate(hasOverflow);
			expect(textOverflowsCell).toBe(false);
		});

		await test.step('Default long text without breaks force line break', async () => {
			const textOverflowsCell = await page
				.getByTestId('longUnbreakableTextWrap')
				.evaluate(hasOverflow);
			await expect(textOverflowsCell).toBe(false);
		});

		await test.step('Long text with "table-cell-ws-nowrap" does not force line break', async () => {
			const textOverflowsCell = await page
				.getByTestId('longTextNoWrap')
				.evaluate(hasOverflow);
			expect(textOverflowsCell).toBe(true);
		});

		await test.step('Long text with "text-truncate" trims line', async () => {
			const textOverflowsCell = await page
				.getByTestId('longTextTruncate')
				.evaluate(hasOverflow);
			await expect(textOverflowsCell).toBe(false);
		});

		await test.step('Default long link force line break', async () => {
			const textOverflowsCell = await page
				.getByTestId('longLinkWrap')
				.evaluate(hasOverflow);
			await expect(textOverflowsCell).toBe(false);
		});

		await test.step('Default long text without breaks force line break', async () => {
			const textOverflowsCell = await page
				.getByTestId('longUnbreakableLinkWrap')
				.evaluate(hasOverflow);
			await expect(textOverflowsCell).toBe(false);
		});

		await test.step('Long link with "table-cell-ws-nowrap" does not force line break', async () => {
			const textOverflowsCell = await page
				.getByTestId('longLinkNoWrap')
				.evaluate(hasOverflow);
			await expect(textOverflowsCell).toBe(true);
		});

		await test.step('Long link with "text-truncate" trims line', async () => {
			const textOverflowsCell = await page
				.getByTestId('longLinkTruncate')
				.evaluate(hasOverflow);
			await expect(textOverflowsCell).toBe(false);
		});
	});
});
