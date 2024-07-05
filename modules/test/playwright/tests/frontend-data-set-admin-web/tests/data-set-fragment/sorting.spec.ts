/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {isolatedLayoutTest} from '../../../../fixtures/isolatedLayoutTest';
import {loginTest} from '../../../../fixtures/loginTest';
import getRandomString from '../../../../utils/getRandomString';
import {dataSetAdminApiHelpersTest} from '../../fixtures/dataSetAdminApiHelpersTest';
import {dataSetFragmentPageTest} from './fixtures/dataSetFragmentPageTest';

export const test = mergeTests(
	dataSetAdminApiHelpersTest,
	featureFlagsTest({
		'LPD-19465': true,
		'LPS-178052': true,
	}),
	isolatedLayoutTest({publish: false}),
	loginTest(),
	dataSetFragmentPageTest
);

let dataSetERC: string;
let dataSetLabel: string;

test.beforeEach(async ({dataSetAdminApiHelpers}) => {
	dataSetERC = getRandomString();
	dataSetLabel = getRandomString();

	await dataSetAdminApiHelpers.createDataSet({
		erc: dataSetERC,
		label: dataSetLabel,
	});
});

test.afterEach(async ({dataSetAdminApiHelpers}) => {
	await dataSetAdminApiHelpers.deleteDataSet({erc: dataSetERC});
});

test.describe('Sorting Dropdown in Data Set Fragment', () => {
	test('When sorting is configured with at least 1 sort, the dropdown is displayed in the fragment @LPD-19503', async ({
		dataSetAdminApiHelpers,
		dataSetFragmentPage,
		layout,
		page,
	}) => {
		await test.step('Create sorting', async () => {
			await dataSetAdminApiHelpers.createDataSetSort({
				dataSetERC,
				defaultValue: true,
				fieldName: 'id',
				label_i18n: {en_US: 'ID'},
				orderType: 'asc',
			});

			await dataSetAdminApiHelpers.createDataSetSort({
				dataSetERC,
				defaultValue: false,
				fieldName: 'fieldName',
				label_i18n: {en_US: 'Field Name'},
			});
		});

		await test.step('Add fields, so data is displayed', async () => {
			await dataSetAdminApiHelpers.createDataSetTableSection({
				dataSetERC,
				fieldName: 'id',
				label_i18n: {
					en_US: 'ID',
				},
				sortable: true,
				type: 'string',
			});

			await dataSetAdminApiHelpers.createDataSetTableSection({
				dataSetERC,
				fieldName: 'fieldName',
				label_i18n: {en_US: 'Field Name'},
				sortable: true,
				type: 'string',
			});
		});

		await test.step('Configure Data Set fragment', async () => {
			await dataSetFragmentPage.configureDataSetFragment({
				dataSetLabel,
				layout,
			});
		});

		await test.step('Check that the order dropdown is displayed', async () => {
			await expect(
				page.getByRole('button', {name: 'Order'})
			).toBeVisible();
		});

		await test.step('Check that default sorting is applied', async () => {
			const firstIDText = await dataSetFragmentPage.tableWrapper
				.locator('.dnd-tbody .dnd-tr:first-child .dnd-td:first-child')
				.textContent();

			const lastIDText = await dataSetFragmentPage.tableWrapper
				.locator('.dnd-tbody .dnd-tr:last-child .dnd-td:first-child')
				.textContent();

			expect(firstIDText < lastIDText).toBeTruthy();
		});

		await test.step('Check that sorting is displayed in the dropdown', async () => {
			await page.getByRole('button', {name: 'Order'}).click();

			await expect(
				page.getByRole('menuitem', {name: 'ID'})
			).toBeVisible();
			await expect(
				page.getByRole('menuitem', {name: 'Field Name'})
			).toBeVisible();
		});

		await test.step('Select "Descending" in the dropdown', async () => {
			await page.getByRole('menuitem', {name: 'Descending'}).click();
		});

		await test.step('Check that the first ID is greater than the last ID in the table', async () => {
			const firstIDText = await dataSetFragmentPage.tableWrapper
				.locator('.dnd-tbody .dnd-tr:first-child .dnd-td:first-child')
				.textContent();

			const lastIDText = await dataSetFragmentPage.tableWrapper
				.locator('.dnd-tbody .dnd-tr:last-child .dnd-td:first-child')
				.textContent();

			expect(firstIDText > lastIDText).toBeTruthy();
		});

		await test.step('Check that a different sort "Name" can be used', async () => {
			await page.getByRole('button', {name: 'Order'}).click();
			await page.getByRole('menuitem', {name: 'Field Name'}).click();

			const firstNameText = await dataSetFragmentPage.tableWrapper
				.locator('.dnd-tbody .dnd-tr:first-child .dnd-td:nth-child(2)')
				.textContent();

			const lastNameText = await dataSetFragmentPage.tableWrapper
				.locator('.dnd-tbody .dnd-tr:last-child .dnd-td:nth-child(2)')
				.textContent();

			expect(firstNameText > lastNameText).toBeTruthy();

			await page.getByRole('button', {name: 'Order'}).click();
			await page.getByRole('menuitem', {name: 'Ascending'}).click();

			const firstNameTextAscending =
				await dataSetFragmentPage.tableWrapper
					.locator(
						'.dnd-tbody .dnd-tr:first-child .dnd-td:nth-child(2)'
					)
					.textContent();

			const lastNameTextAscending = await dataSetFragmentPage.tableWrapper
				.locator('.dnd-tbody .dnd-tr:last-child .dnd-td:nth-child(2)')
				.textContent();

			expect(firstNameTextAscending < lastNameTextAscending).toBeTruthy();
		});
	});
});
