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
		'LPS-178052': true,
	}),
	loginTest(),
	dataSetFragmentPageTest,
	isolatedLayoutTest({publish: false})
);

let dataSetERC: string;

const dataSetLabel: string = getRandomString();

test.beforeEach(async ({dataSetAdminApiHelpers}) => {
	dataSetERC = getRandomString();

	await dataSetAdminApiHelpers.createDataSet({
		erc: dataSetERC,
		label: dataSetLabel,
	});
});

test.afterEach(async ({dataSetAdminApiHelpers}) => {
	await dataSetAdminApiHelpers.deleteDataSet({
		erc: dataSetERC,
	});
});

test.describe('Visualization Modes in Data Set fragment', () => {
	test('Show mapped fields in the fragment', async ({
		dataSetAdminApiHelpers,
		dataSetFragmentPage,
		layout,
		page,
	}) => {
		const SAMPLE_SCALAR_FIELD = 'id';
		const SAMPLE_OBJECT_FIELD = 'dataSetTableSections';
		const SAMPLE_OBJECT_CHILD_FIELD = 'label';

		await test.step('Create table fields', async () => {
			await dataSetAdminApiHelpers.createDataSetTableSection({
				dataSetERC,
				fieldName: `${SAMPLE_OBJECT_FIELD}.${SAMPLE_OBJECT_CHILD_FIELD}`,
				label_i18n: {en_US: 'Label'},
				type: 'string',
			});
			await dataSetAdminApiHelpers.createDataSetTableSection({
				dataSetERC,
				fieldName: `${SAMPLE_SCALAR_FIELD}`,
				label_i18n: {en_US: 'Id'},
				type: 'string',
			});
		});

		await test.step('Create cards section fields', async () => {
			await dataSetAdminApiHelpers.createDataSetCardsSection({
				dataSetERC,
				fieldName: `${SAMPLE_OBJECT_FIELD}.${SAMPLE_OBJECT_CHILD_FIELD}`,
				name: 'title',
			});
			await dataSetAdminApiHelpers.createDataSetCardsSection({
				dataSetERC,
				fieldName: `${SAMPLE_SCALAR_FIELD}`,
				name: 'description',
			});
		});

		await test.step('Create list section fields', async () => {
			await dataSetAdminApiHelpers.createDataSetListSection({
				dataSetERC,
				fieldName: `${SAMPLE_OBJECT_FIELD}.${SAMPLE_OBJECT_CHILD_FIELD}`,
				name: 'title',
			});
			await dataSetAdminApiHelpers.createDataSetListSection({
				dataSetERC,
				fieldName: `${SAMPLE_SCALAR_FIELD}`,
				name: 'description',
			});
		});

		await test.step('Configure Data Set in the page', async () => {
			await dataSetFragmentPage.configureDataSetFragment({
				dataSetLabel,
				layout,
			});
		});

		await test.step('Check Data Set Cards are present', async () => {
			await dataSetFragmentPage.cardsWrapper.waitFor({
				state: 'visible',
			});

			await expect(dataSetFragmentPage.cardsWrapper).toBeInViewport();

			await dataSetFragmentPage.page.locator('.card').first().waitFor();

			const firstCard = dataSetFragmentPage.page.locator('.card').first();

			await expect(firstCard.locator('.card-title')).toContainText(
				dataSetLabel
			);

			await expect(firstCard.locator('.card-subtitle')).not.toBeEmpty();
		});

		await test.step('Change visualization mode to List', async () => {
			await dataSetFragmentPage.changeVisualizationMode('List');
		});

		await test.step('Check Data Set List is present', async () => {
			await dataSetFragmentPage.listWrapper.waitFor({
				state: 'visible',
			});

			await expect(dataSetFragmentPage.listWrapper).toBeInViewport();

			await dataSetFragmentPage.page
				.locator('.list-group-item')
				.first()
				.waitFor();

			const firstListItem = dataSetFragmentPage.page
				.locator('.list-group-item')
				.first();

			await expect(
				firstListItem.locator('.list-group-title')
			).toContainText(dataSetLabel);

			await expect(
				firstListItem.locator('.list-group-text')
			).not.toBeEmpty();
		});

		await test.step('Change visualization mode to Table', async () => {
			await dataSetFragmentPage.changeVisualizationMode('Table');
		});

		await test.step('Data Set Table is in the page', async () => {
			await dataSetFragmentPage.tableWrapper.waitFor({
				state: 'visible',
			});

			await expect(
				await dataSetFragmentPage.tableWrapper
			).toBeInViewport();

			expect(
				await page
					.locator('.dnd-thead > div')
					.first()
					.locator('.dnd-th')
					.allInnerTexts()
			).toEqual(['Label', 'Id', '']);
		});
	});

	test('Show mapped scalar array field in the fragment @LPD-11769', async ({
		dataSetAdminApiHelpers,
		dataSetFragmentPage,
		layout,
		page,
	}) => {
		const SAMPLE_SCALAR_ARRAY_FIELD = 'keywords';
		const SAMPLE_SCALAR_ARRAY_CONTENT = ['one', 'two', 'three'];

		await test.step('Create table fields', async () => {
			await dataSetAdminApiHelpers.createDataSetTableSection({
				dataSetERC,
				extraBodyParams: {
					keywords: SAMPLE_SCALAR_ARRAY_CONTENT,
				},
				fieldName: SAMPLE_SCALAR_ARRAY_FIELD,
				label_i18n: {en_US: SAMPLE_SCALAR_ARRAY_FIELD},
				type: 'array',
			});
		});

		await test.step('Configure Data Set in the page', async () => {
			await dataSetFragmentPage.configureDataSetFragment({
				dataSetLabel,
				layout,
			});
		});

		await test.step('Data Set Table is in the page', async () => {
			await dataSetFragmentPage.tableWrapper.waitFor({
				state: 'visible',
			});

			await expect(dataSetFragmentPage.tableWrapper).toBeInViewport();

			expect(
				await page
					.locator('.dnd-thead > div')
					.first()
					.locator('.dnd-th')
					.allInnerTexts()
			).toEqual([SAMPLE_SCALAR_ARRAY_FIELD, '']);

			expect(
				await page
					.locator('.dnd-tbody > div')
					.first()
					.locator('.dnd-td')
					.allInnerTexts()
			).toEqual(['one, two, three', '']);
		});
	});
});
