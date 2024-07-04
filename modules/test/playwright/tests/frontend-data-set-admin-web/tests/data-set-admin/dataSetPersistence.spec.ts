/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../fixtures/loginTest';
import getRandomString from '../../../../utils/getRandomString';
import {dataSetAdminApiHelpersTest} from '../../fixtures/dataSetAdminApiHelpersTest';
import {
	ACTION_DATA_SET_RELATIONSHIP,
	CREATION_ACTION_DATA_SET_RELATIONSHIP,
	ITEM_ACTION_DATA_SET_RELATIONSHIP,
} from '../../utils/dataSetAdminConstants';

export const test = mergeTests(
	dataSetAdminApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginTest()
);

let dataSetERC: string;

const dataSetLabel: string = getRandomString();
let dataSet: any;

test.beforeEach(async ({dataSetAdminApiHelpers}) => {
	dataSetERC = getRandomString();

	dataSet = await dataSetAdminApiHelpers.createDataSet({
		erc: dataSetERC,
		label: dataSetLabel,
	});
});

test.afterEach(async ({dataSetAdminApiHelpers}) => {
	await dataSetAdminApiHelpers.deleteDataSet({
		erc: dataSet.externalReferenceCode,
	});
});

test.describe('Ensure Root Model relationship constraints', async () => {
	test('Try to create invalid data set actions', async ({
		dataSetAdminApiHelpers,
		page,
	}) => {
		await test.step('Try to add creation Data Set Action with no data set associated', async () => {
			let result: any;

			result = await dataSetAdminApiHelpers.createDataSetCreationAction({
				dataSet: {...dataSet, externalReferenceCode: ''},
				label_i18n: {en_US: 'Invalid Creation Action'},
			});

			expect(result?.status).toBe('BAD_REQUEST');
			expect(result?.type).toBe('ObjectValidationRuleEngineException');
		});

		await test.step('Try to add creation Data Set Action with no data set associated', async () => {
			const erc = dataSet.externalReferenceCode;

			delete dataSet.externalReferenceCode;

			const result = await dataSetAdminApiHelpers.createDataSetItemAction(
				{
					dataSet,
					label_i18n: {en_US: 'Invalid dangling creation action'},
				}
			);

			dataSet.externalReferenceCode = erc;
			expect(result?.status).toBe('BAD_REQUEST');
			expect(result?.type).toBe('ObjectValidationRuleEngineException');
		});

		await test.step('Create invalid item and creation Data Set Action simultaneously', async () => {
			const result = await dataSetAdminApiHelpers.postDataSetBaseAction({
				[ACTION_DATA_SET_RELATIONSHIP.id]: dataSet.id,
				[CREATION_ACTION_DATA_SET_RELATIONSHIP.erc]:
					dataSet.externalReferenceCode,
				[ITEM_ACTION_DATA_SET_RELATIONSHIP.erc]:
					dataSet.externalReferenceCode,
				label_i18n: {
					en_US: 'Invalid simultaneous creation and item action',
				},
				type: 'link',
			});

			expect(result?.status).toBe('BAD_REQUEST');
			expect(result?.type).toBe('ObjectValidationRuleEngineException');
		});
	});
});
