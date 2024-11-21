/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {rolesPagesTest} from '../../../../fixtures/rolesPagesTest';
import {DataApiHelpers} from '../../../../helpers/ApiHelpers';
import {RoleDefinePermissionsPage} from '../../../../pages/roles-admin-web/RoleDefinePermissionsPage';
import {RolePage} from '../../../../pages/roles-admin-web/RolePage';
import {RolesPage} from '../../../../pages/roles-admin-web/RolesPage';
import getRandomString from '../../../../utils/getRandomString';
import performLogin, {
	performLogout,
	userData,
} from '../../../../utils/performLogin';
import {waitForAlert} from '../../../../utils/waitForAlert';
import {dataSetManagerApiHelpersTest} from '../../fixtures/dataSetManagerApiHelpersTest';
import {API_ENDPOINT_PATH} from '../../utils/constants';
import {dataSetsPageTest} from './fixtures/dataSetsPageTest';

export const test = mergeTests(
	dataApiHelpersTest,
	dataSetManagerApiHelpersTest,
	dataSetsPageTest,
	featureFlagsTest({
		'LPD-37531': false,
		'LPS-164563': true,
	}),
	rolesPagesTest,
	loginTest()
);

const dataSetsTabsTest = mergeTests(
	test,
	featureFlagsTest({
		'LPD-37531': true,
	})
);

const createdDataSetERCs = [];
const createdRoleIds = [];
const createdUserIds = [];

let loggedInAsAdmin = true;

const blogPostsDataSetConfig = {
	name: 'BlogPosting',
	restApplication: '/headless-delivery/v1.0',
	restEndpoint: '/v1.0/sites/{siteId}/blog-postings',
	restSchema: 'BlogPosting',
};

const catalogsDataSetConfig = {
	name: 'Catalog',
	restApplication: '/headless-commerce-admin-catalog/v1.0',
	restEndpoint: '/v1.0/catalog',
	restSchema: 'Catalog',
};

const productsDataSetConfig = {
	name: 'Product',
	restApplication: '/headless-commerce-admin-catalog/v1.0',
	restEndpoint: '/v1.0/products',
	restSchema: 'Product',
};

const skusDataSetConfig = {
	name: 'Sku',
	restApplication: '/headless-commerce-admin-catalog/v1.0',
	restEndpoint: '/v1.0/skus',
	restSchema: 'Sku',
};

const tableSectionsDataSetConfig = {
	name: getRandomString(),
	restApplication: `${API_ENDPOINT_PATH}/table-sections`,
	restEndpoint: '/',
	restSchema: 'DataSetTableSection',
};

const tableSectionsWithSpecialCharactersDataSetConfig = {
	name: 'Data Set ~!@#$%^&*(){}[].<>/? name',
	restApplication: `${API_ENDPOINT_PATH}/table-sections`,
	restEndpoint: '/',
	restSchema: 'DataSetTableSection',
};

async function assertTableActionLabels(page) {
	await test.step('Assert table action labels', async () => {
		await page.locator('.dnd-td.item-actions').first().waitFor();

		await page
			.locator('.dnd-td.item-actions')
			.first()
			.locator('.dropdown-toggle')
			.click();

		const tableItemActions = await page
			.locator('.dropdown-menu')
			.filter({has: page.locator('span.pr-2')})
			.first()
			.locator('.dropdown-item')
			.allInnerTexts();

		const expectedLabels = ['Edit', 'Permissions', 'Delete'];

		await expect(tableItemActions).toEqual(expectedLabels);
	});
}

async function assertTableCellContent({dataSetConfig, page, rowIndex = 0}) {
	await test.step('Assert table cell content', async () => {
		await page
			.locator('.dnd-table > .dnd-tbody > .dnd-tr')
			.first()
			.waitFor();

		const tableRowContent = await page
			.locator('.dnd-tbody > .dnd-tr')
			.nth(rowIndex)
			.locator('.dnd-td');

		const expectedRowContent = [
			dataSetConfig.name,
			dataSetConfig.restApplication,
			dataSetConfig.restSchema,
			dataSetConfig.restEndpoint,
		];

		await expect(tableRowContent).toContainText(expectedRowContent);
	});
}

async function assertTableColumnLabels(page) {
	await test.step('Assert table column labels', async () => {
		await page.locator('.dnd-table > .dnd-thead > .dnd-tr').waitFor();

		const tableColumnLabels = await page
			.locator('.dnd-thead > .dnd-tr')
			.first()
			.locator('.dnd-th')
			.allInnerTexts();

		const expectedLabels = [
			'Name',
			'REST Application',
			'REST Schema',
			'REST Endpoint',
			'Modified Date',
			'',
		];

		expect(tableColumnLabels).toEqual(expectedLabels);
	});
}

async function assertTableRowsCount(page, rowsCount) {
	await test.step(`Assert table has ${rowsCount} rows`, async () => {
		const rows = await page.locator('.dnd-table > .dnd-tbody > .dnd-tr');

		expect(rows).toHaveCount(rowsCount);
	});
}

async function setupUserRoleAndLoginAsUser({
	apiHelpers,
	dataSetResourcePermissions,
	page,
	roleDefinePermissionsPage,
	rolePage,
	rolesPage,
}: {
	apiHelpers: DataApiHelpers;
	dataSetResourcePermissions?: {
		actions: string[];
		name: string;
	}[];
	page: Page;
	roleDefinePermissionsPage: RoleDefinePermissionsPage;
	rolePage: RolePage;
	rolesPage: RolesPage;
}) {
	const roleName = `ds_user_${getRandomString()}`;

	let dataSetUserRole;
	let userAccount: TUserAccount;

	await test.step('Create Data Set user role', async () => {
		const companyId = await page.evaluate(() => {
			return Liferay.ThemeDisplay.getCompanyId();
		});

		dataSetUserRole = await apiHelpers.headlessAdminUser.postRole({
			name: roleName,
			rolePermissions: [
				{
					actionIds: ['VIEW_CONTROL_PANEL'],
					primaryKey: companyId,
					resourceName: '90',
					scope: 1,
				},
				{
					actionIds: ['ACCESS_IN_CONTROL_PANEL', 'VIEW'],
					primaryKey: companyId,
					resourceName:
						'com_liferay_frontend_data_set_admin_web_internal_portlet_FDSAdminPortlet',
					scope: 1,
				},
			],
			roleType: 'regular',
		});

		createdRoleIds.push(dataSetUserRole.id);
	});

	await test.step('Create a new user', async () => {
		userAccount = await apiHelpers.headlessAdminUser.postUserAccount();

		userData[userAccount.alternateName] = {
			name: userAccount.givenName,
			password: 'test',
			surname: userAccount.familyName,
		};

		createdUserIds.push(userAccount.id);
	});

	await test.step('Assign new role to user', async () => {
		await apiHelpers.headlessAdminUser.postRoleUserAccountAssociation(
			dataSetUserRole.id,
			Number(userAccount.id)
		);

		apiHelpers.data.push({
			id: `${dataSetUserRole.id}_${userAccount.id}`,
			type: 'roleUserAccountAssociation',
		});
	});

	// Enable Data Set roles through the UI since the Data Set Object created
	// is given a random resource name (For example: com.liferay.object.model.ObjectDefinition#E0X3).

	if (dataSetResourcePermissions) {
		await test.step('Go to roles admin page', async () => {
			await rolesPage.goto();
		});

		await test.step('Navigate to role edit page', async () => {
			await page.getByRole('link', {exact: true, name: roleName}).click();
		});

		await test.step('Navigate to "Define Permissions" > "Data Set" section', async () => {
			await rolePage.definePermissionsLink.click();
			await roleDefinePermissionsPage.searchInput.click();
			await roleDefinePermissionsPage.searchInput.fill('Data Set');

			await page
				.getByRole('menuitem', {exact: true, name: 'Data Set'})
				.click();
		});

		for (const dataSetResourcePermission of dataSetResourcePermissions) {
			await test.step('Enable role checkboxes', async () => {
				const dataSetRolesTable = page
					.locator('.sheet-tertiary-title')
					.getByText(dataSetResourcePermission.name, {exact: true})
					.locator('~ .lfr-search-container');

				for (const action of dataSetResourcePermission.actions) {
					await dataSetRolesTable
						.getByRole('row', {name: action})
						.getByRole('checkbox')
						.setChecked(true);
				}
			});
		}

		await test.step('Save roles', async () => {
			await page.getByRole('button', {name: 'Save'}).click();

			await waitForAlert(
				page,
				'Success:The role permissions were updated.'
			);
		});
	}

	await test.step('Do login with the new user', async () => {
		await performLogout(page);
		await performLogin(page, userAccount.alternateName);

		loggedInAsAdmin = false;
	});
}

test.afterEach(async ({apiHelpers, dataSetManagerApiHelpers, page}) => {
	if (!loggedInAsAdmin) {
		await performLogout(page);
		await performLogin(page, 'test');
	}

	for (const erc of createdDataSetERCs) {
		await dataSetManagerApiHelpers.deleteDataSet({
			erc,
		});
	}

	createdDataSetERCs.length = 0;

	for (const id of createdRoleIds) {
		await apiHelpers.headlessAdminUser.deleteRole(id);
	}

	createdRoleIds.length = 0;

	for (const id of createdUserIds) {
		await apiHelpers.headlessAdminUser.deleteUserAccount(id);
	}

	createdUserIds.length = 0;
});

test(
	'Create data set via UI',
	{tag: '@LPS-178858'},
	async ({dataSetsPage, page}) => {
		await test.step('Navigate to Data Set page', async () => {
			await dataSetsPage.goto();
			await expect(
				dataSetsPage.dataSetsEmptyState.locator('.c-empty-state-title')
			).toContainText('No Data Sets Created');
		});

		await test.step('Create Data Set', async () => {
			await dataSetsPage.createDataSet(tableSectionsDataSetConfig);
		});

		await assertTableColumnLabels(page);

		await assertTableCellContent({
			dataSetConfig: tableSectionsDataSetConfig,
			page,
		});

		await assertTableActionLabels(page);

		await test.step('Delete Data Set', async () => {
			await dataSetsPage.deleteDataSet(tableSectionsDataSetConfig.name);
		});
	}
);

test('Create parameterized data set', async ({dataSetsPage, page}) => {
	await test.step('Create Data Set', async () => {
		await dataSetsPage.goto();
		await dataSetsPage.createDataSet(blogPostsDataSetConfig);
	});

	await assertTableColumnLabels(page);

	await assertTableCellContent({dataSetConfig: blogPostsDataSetConfig, page});

	await assertTableActionLabels(page);

	await test.step('Delete Data Set', async () => {
		await dataSetsPage.deleteDataSet(blogPostsDataSetConfig.name);
	});
});

test(
	'Assert endpoint with resolved paramater is available as an option',
	{tag: '@LPD-31177'},
	async ({dataSetsPage}) => {
		const cartDataSetConfig = {
			name: 'Carts',
			restApplication: '/headless-commerce-delivery-cart/v1.0',
			restEndpoint:
				'/v1.0/channels/{channelId}/account/{accountId}/carts',
			restSchema: 'Cart',
		};

		const modal = dataSetsPage.newDataSetModal;

		await test.step('Go to Data Sets page and open "New" modal', async () => {
			await dataSetsPage.goto();

			await dataSetsPage.newDataSetButton.click();

			await expect(modal.nameInput).toBeVisible();
		});

		await test.step('Assert endpoint with resolved paramater is available', async () => {
			await modal.restApplicationField.click();

			await modal.restApplicationOptions
				.getByRole('option', {name: cartDataSetConfig.restApplication})
				.click();

			await expect(modal.restSchemaField).toBeVisible();

			await modal.restSchemaField.click();

			await modal.restSchemaOptions
				.getByRole('option', {name: cartDataSetConfig.restSchema})
				.click();

			await expect(modal.restEndpointField).toBeVisible();

			await modal.restEndpointField.click();

			await expect(
				modal.restEndpointOptions.getByRole('option', {
					name: cartDataSetConfig.restEndpoint,
				})
			).toBeVisible();
		});
	}
);

test('Can paginate created Data Sets', async ({
	dataSetManagerApiHelpers,
	dataSetsPage,
	page,
}) => {
	const testDataSetERCs = Array.from(Array(5).keys()).map(() =>
		getRandomString()
	);

	await test.step('Create collection of Data Sets', async () => {
		for (const DATA_SET_ERC of testDataSetERCs) {
			createdDataSetERCs.push(DATA_SET_ERC);
			await dataSetManagerApiHelpers.createDataSet({
				...tableSectionsDataSetConfig,
				erc: DATA_SET_ERC,
				label: tableSectionsDataSetConfig.name,
			});
		}
	});

	await test.step('Navigate to Data Sets page', async () => {
		await dataSetsPage.goto();
	});

	await assertTableRowsCount(page, 5);

	await test.step('Change page size', async () => {
		const itemsPerPageButton = page.getByLabel('Items Per Page');

		await expect(itemsPerPageButton).toContainText('8 Items');

		await itemsPerPageButton.click();

		const dropdownId = await itemsPerPageButton.evaluate((node) =>
			node.getAttribute('aria-controls')
		);

		await page.locator(`#${dropdownId}`).waitFor();

		await page
			.locator(`#${dropdownId}`)
			.getByRole('option', {name: '4 Items'})
			.click();

		await expect(itemsPerPageButton).toContainText('4 Items');
	});

	await assertTableRowsCount(page, 4);

	await test.step('Navigate to Data Set page 2', async () => {
		await page.getByLabel('Go to page, 2').click();

		await page.getByText('Showing 5 to 5 of 5 entries.').isVisible();
	});

	await assertTableRowsCount(page, 1);

	await test.step('Delete Data Set from current page', async () => {
		const dataSetActionsButton = await page.getByRole('button', {
			name: 'Actions',
		});

		dataSetActionsButton.click();

		const actionsDropdownId = await dataSetActionsButton.evaluate((node) =>
			node.getAttribute('aria-controls')
		);

		await page.locator(`#${actionsDropdownId}`).waitFor();

		await page
			.locator(`#${actionsDropdownId}`)
			.getByRole('menuitem', {name: 'Delete'})
			.click();

		await page.getByRole('dialog').waitFor({state: 'visible'});

		await page.getByRole('button', {name: 'Delete'}).click();

		await page.getByRole('dialog').waitFor({state: 'hidden'});
	});

	await assertTableRowsCount(page, 4);
});

test('Sort data sets by different columns', async ({
	dataSetManagerApiHelpers,
	dataSetsPage,
	page,
}) => {
	const productsDataSetERC = getRandomString();

	await test.step('Create collection of Data Sets', async () => {
		const blogPostDataSetERC = getRandomString();
		createdDataSetERCs.push(blogPostDataSetERC);

		await dataSetManagerApiHelpers.createDataSet({
			...blogPostsDataSetConfig,
			erc: blogPostDataSetERC,
			label: blogPostsDataSetConfig.name,
		});

		const catalogsDataSetERC = getRandomString();
		createdDataSetERCs.push(catalogsDataSetERC);

		await dataSetManagerApiHelpers.createDataSet({
			...catalogsDataSetConfig,
			erc: catalogsDataSetERC,
			label: catalogsDataSetConfig.name,
		});

		createdDataSetERCs.push(productsDataSetERC);

		await dataSetManagerApiHelpers.createDataSet({
			...productsDataSetConfig,
			erc: productsDataSetERC,
			label: productsDataSetConfig.name,
		});

		const skuDataSetERC = getRandomString();
		createdDataSetERCs.push(skuDataSetERC);

		await dataSetManagerApiHelpers.createDataSet({
			...skusDataSetConfig,
			erc: skuDataSetERC,
			label: skusDataSetConfig.name,
		});
	});

	await test.step('Go to Data Sets', async () => {
		await dataSetsPage.goto();
	});

	await assertTableRowsCount(page, 4);

	await test.step('Check data sets default sort is by creation date, in descending order', async () => {
		await assertTableCellContent({
			dataSetConfig: skusDataSetConfig,
			page,
			rowIndex: 0,
		});
		await assertTableCellContent({
			dataSetConfig: productsDataSetConfig,
			page,
			rowIndex: 1,
		});
		await assertTableCellContent({
			dataSetConfig: catalogsDataSetConfig,
			page,
			rowIndex: 2,
		});
		await assertTableCellContent({
			dataSetConfig: blogPostsDataSetConfig,
			page,
			rowIndex: 3,
		});
	});

	await test.step('Sort data sets by "Name" column', async () => {
		await dataSetsPage.sortBy('Name');

		await assertTableCellContent({
			dataSetConfig: blogPostsDataSetConfig,
			page,
			rowIndex: 0,
		});
		await assertTableCellContent({
			dataSetConfig: catalogsDataSetConfig,
			page,
			rowIndex: 1,
		});
		await assertTableCellContent({
			dataSetConfig: productsDataSetConfig,
			page,
			rowIndex: 2,
		});
		await assertTableCellContent({
			dataSetConfig: skusDataSetConfig,
			page,
			rowIndex: 3,
		});

		await dataSetsPage.sortBy('Name');

		await assertTableCellContent({
			dataSetConfig: skusDataSetConfig,
			page,
			rowIndex: 0,
		});
		await assertTableCellContent({
			dataSetConfig: productsDataSetConfig,
			page,
			rowIndex: 1,
		});
		await assertTableCellContent({
			dataSetConfig: catalogsDataSetConfig,
			page,
			rowIndex: 2,
		});
		await assertTableCellContent({
			dataSetConfig: blogPostsDataSetConfig,
			page,
			rowIndex: 3,
		});
	});

	await test.step('Sort data sets by "REST Endpoint" column', async () => {

		// Reload to start with default sort

		await page.reload();

		await dataSetsPage.sortBy('REST Endpoint');

		await assertTableCellContent({
			dataSetConfig: catalogsDataSetConfig,
			page,
			rowIndex: 0,
		});
		await assertTableCellContent({
			dataSetConfig: productsDataSetConfig,
			page,
			rowIndex: 1,
		});
		await assertTableCellContent({
			dataSetConfig: blogPostsDataSetConfig,
			page,
			rowIndex: 2,
		});
		await assertTableCellContent({
			dataSetConfig: skusDataSetConfig,
			page,
			rowIndex: 3,
		});

		await dataSetsPage.sortBy('REST Endpoint');

		await assertTableCellContent({
			dataSetConfig: skusDataSetConfig,
			page,
			rowIndex: 0,
		});
		await assertTableCellContent({
			dataSetConfig: blogPostsDataSetConfig,
			page,
			rowIndex: 1,
		});
		await assertTableCellContent({
			dataSetConfig: productsDataSetConfig,
			page,
			rowIndex: 2,
		});
		await assertTableCellContent({
			dataSetConfig: catalogsDataSetConfig,
			page,
			rowIndex: 3,
		});
	});

	await test.step('Sort data sets by "REST Schema" column', async () => {

		// Reload to start with default sort

		await page.reload();

		await dataSetsPage.sortBy('REST Schema');

		await assertTableCellContent({
			dataSetConfig: blogPostsDataSetConfig,
			page,
			rowIndex: 0,
		});
		await assertTableCellContent({
			dataSetConfig: catalogsDataSetConfig,
			page,
			rowIndex: 1,
		});
		await assertTableCellContent({
			dataSetConfig: productsDataSetConfig,
			page,
			rowIndex: 2,
		});
		await assertTableCellContent({
			dataSetConfig: skusDataSetConfig,
			page,
			rowIndex: 3,
		});

		await dataSetsPage.sortBy('REST Schema');

		await assertTableCellContent({
			dataSetConfig: skusDataSetConfig,
			page,
			rowIndex: 0,
		});
		await assertTableCellContent({
			dataSetConfig: productsDataSetConfig,
			page,
			rowIndex: 1,
		});
		await assertTableCellContent({
			dataSetConfig: catalogsDataSetConfig,
			page,
			rowIndex: 2,
		});
		await assertTableCellContent({
			dataSetConfig: blogPostsDataSetConfig,
			page,
			rowIndex: 3,
		});
	});

	await test.step('Sort data sets by "Modified Date" column', async () => {

		// Reload to start with default sort

		await page.reload();

		await dataSetsPage.sortBy('Modified Date');

		await assertTableCellContent({
			dataSetConfig: blogPostsDataSetConfig,
			page,
			rowIndex: 0,
		});
		await assertTableCellContent({
			dataSetConfig: catalogsDataSetConfig,
			page,
			rowIndex: 1,
		});
		await assertTableCellContent({
			dataSetConfig: productsDataSetConfig,
			page,
			rowIndex: 2,
		});
		await assertTableCellContent({
			dataSetConfig: skusDataSetConfig,
			page,
			rowIndex: 3,
		});

		await dataSetManagerApiHelpers.updateDataSet({
			defaultItemsPerPage: 8,
			erc: productsDataSetERC,
		});

		await page.reload();

		await dataSetsPage.sortBy('Modified Date');

		await assertTableCellContent({
			dataSetConfig: blogPostsDataSetConfig,
			page,
			rowIndex: 0,
		});
		await assertTableCellContent({
			dataSetConfig: catalogsDataSetConfig,
			page,
			rowIndex: 1,
		});
		await assertTableCellContent({
			dataSetConfig: skusDataSetConfig,
			page,
			rowIndex: 2,
		});
		await assertTableCellContent({
			dataSetConfig: productsDataSetConfig,
			page,
			rowIndex: 3,
		});
	});
});

test(
	'Check cancel in Data Set',
	{tag: ['@LPS-175990', '@LPS-172398']},
	async ({dataSetsPage, page}) => {
		await test.step('Navigate to Data Set page', async () => {
			await dataSetsPage.goto();
		});

		await test.step('Cannot create a Data Set without a name', async () => {
			await dataSetsPage.newDataSetButton.click();
			await dataSetsPage.newDataSetModal.nameInput.waitFor();

			await dataSetsPage.newDataSetModal.nameInput.fill('');
			await dataSetsPage.newDataSetModal.saveButton.click();

			await expect(
				page.getByText('This field is required.', {exact: true})
			).toBeVisible();

			await dataSetsPage.newDataSetModal.cancel.click();

			await expect(
				dataSetsPage.dataSetsEmptyState.locator('.c-empty-state-title')
			).toContainText('No Data Sets Created');
		});

		await test.step('Can create a Data Set using special characters', async () => {
			await dataSetsPage.createDataSet(
				tableSectionsWithSpecialCharactersDataSetConfig
			);
		});

		await assertTableCellContent({
			dataSetConfig: tableSectionsWithSpecialCharactersDataSetConfig,
			page,
		});

		await test.step('Select the Delete Data Set action, then click Cancel button', async () => {
			const datasetTestRow = await page
				.locator('.data-set-content-wrapper .dnd-tbody .dnd-tr')
				.filter({
					hasText:
						tableSectionsWithSpecialCharactersDataSetConfig.name,
				});

			await datasetTestRow
				.first()
				.getByRole('button', {name: 'Actions'})
				.click();

			await page.getByRole('menuitem', {name: 'Delete'}).click();

			const deleteModal = await page.getByRole('dialog');

			await deleteModal.getByRole('button', {name: 'Cancel'}).click();

			await assertTableCellContent({
				dataSetConfig: tableSectionsWithSpecialCharactersDataSetConfig,
				page,
			});
		});

		await test.step('Select the Delete Data Set action, then click X button', async () => {
			const datasetTestRow = await page
				.locator('.data-set-content-wrapper .dnd-tbody .dnd-tr')
				.filter({
					hasText:
						tableSectionsWithSpecialCharactersDataSetConfig.name,
				});

			await datasetTestRow
				.first()
				.getByRole('button', {name: 'Actions'})
				.click();

			await page.getByRole('menuitem', {name: 'Delete'}).click();

			const deleteModal = await page.getByRole('dialog');

			await deleteModal.getByRole('button', {name: 'Close'}).click();

			await assertTableCellContent({
				dataSetConfig: tableSectionsWithSpecialCharactersDataSetConfig,
				page,
			});
		});

		await test.step('Delete Data Set', async () => {
			await dataSetsPage.deleteDataSet(
				tableSectionsWithSpecialCharactersDataSetConfig.name
			);
		});
	}
);

test('A user with "View" and "Permissions" permission', async ({
	apiHelpers,
	dataSetManagerApiHelpers,
	dataSetsPage,
	page,
	roleDefinePermissionsPage,
	rolePage,
	rolesPage,
}) => {
	await test.step('Create a data set', async () => {
		const blogPostDataSetERC = getRandomString();
		createdDataSetERCs.push(blogPostDataSetERC);

		await dataSetManagerApiHelpers.createDataSet({
			...blogPostsDataSetConfig,
			erc: blogPostDataSetERC,
			label: blogPostsDataSetConfig.name,
		});
	});

	await test.step('Setup user role and login as user', async () => {
		await setupUserRoleAndLoginAsUser({
			apiHelpers,
			dataSetResourcePermissions: [
				{
					actions: ['Permissions', 'View'],
					name: 'Data Set',
				},
			],
			page,
			roleDefinePermissionsPage,
			rolePage,
			rolesPage,
		});
	});

	await test.step('Go to Data Sets', async () => {
		await dataSetsPage.goto({checkTabVisibility: false});
	});

	await test.step('Open actions dropdown', async () => {
		const dataSetRows = await page
			.locator('.data-set-content-wrapper .dnd-tbody .dnd-tr')
			.filter({
				hasText: blogPostsDataSetConfig.name,
			});

		await dataSetRows
			.first()
			.getByRole('button', {name: 'Actions'})
			.click();
	});

	await test.step('Check that "Permissions" is visible', async () => {
		await expect(
			page.getByRole('menuitem', {name: 'Permissions'})
		).toBeVisible();
	});

	await test.step('Open Permissions modal', async () => {
		await page.getByRole('menuitem', {name: 'Permissions'}).click();
	});

	await test.step('Enable "View" permission for "User" role', async () => {
		await page
			.frameLocator('iframe[title="Permissions"]')
			.locator('#user_ACTION_VIEW')
			.setChecked(true);
	});

	await test.step('Save Permissions modal', async () => {
		await page
			.frameLocator('iframe[title="Permissions"]')
			.getByRole('button', {name: 'Save'})
			.click();
	});

	await test.step('Click "Cancel" in the Permissions modal', async () => {
		await page
			.frameLocator('iframe[title="Permissions"]')
			.getByRole('button', {name: 'Cancel'})
			.click();
	});
	await test.step('Check that the Permissions modal is closed', async () => {
		await expect(
			page.getByRole('heading', {name: 'Permissions'})
		).not.toBeVisible();
	});

	await test.step('Open actions dropdown', async () => {
		const dataSetRows = await page
			.locator('.data-set-content-wrapper .dnd-tbody .dnd-tr')
			.filter({
				hasText: blogPostsDataSetConfig.name,
			});

		await dataSetRows
			.first()
			.getByRole('button', {name: 'Actions'})
			.click();
	});

	await test.step('Open Permissions modal', async () => {
		await page.getByRole('menuitem', {name: 'Permissions'}).click();
	});

	await test.step('Confirm "View" permission is persisted', async () => {
		await expect(
			page
				.frameLocator('iframe[title="Permissions"]')
				.locator('#user_ACTION_VIEW')
		).toBeChecked();
	});
});

test('A user with only "View" permission', async ({
	apiHelpers,
	dataSetManagerApiHelpers,
	dataSetsPage,
	page,
	roleDefinePermissionsPage,
	rolePage,
	rolesPage,
}) => {
	await test.step('Create a data set', async () => {
		const blogPostDataSetERC = getRandomString();
		createdDataSetERCs.push(blogPostDataSetERC);

		await dataSetManagerApiHelpers.createDataSet({
			...blogPostsDataSetConfig,
			erc: blogPostDataSetERC,
			label: blogPostsDataSetConfig.name,
		});
	});

	await test.step('Setup user role and login as user', async () => {
		await setupUserRoleAndLoginAsUser({
			apiHelpers,
			dataSetResourcePermissions: [
				{
					actions: ['View'],
					name: 'Data Set',
				},
			],
			page,
			roleDefinePermissionsPage,
			rolePage,
			rolesPage,
		});
	});

	await test.step('Go to Data Sets', async () => {
		await dataSetsPage.goto({checkTabVisibility: false});
	});

	await test.step('Open actions dropdown', async () => {
		const dataSetRows = await page
			.locator('.data-set-content-wrapper .dnd-tbody .dnd-tr')
			.filter({
				hasText: blogPostsDataSetConfig.name,
			});

		await dataSetRows
			.first()
			.getByRole('button', {name: 'Actions'})
			.click();
	});

	await test.step('Check that "Permissions" is not visible', async () => {
		await expect(
			page.getByRole('menuitem', {name: 'Permissions'})
		).not.toBeVisible();
	});
});

test('A user without "View" permission on Data Set items', async ({
	apiHelpers,
	dataSetManagerApiHelpers,
	dataSetsPage,
	page,
	roleDefinePermissionsPage,
	rolePage,
	rolesPage,
}) => {
	await test.step('Create a data set', async () => {
		const blogPostDataSetERC = getRandomString();
		createdDataSetERCs.push(blogPostDataSetERC);

		await dataSetManagerApiHelpers.createDataSet({
			...blogPostsDataSetConfig,
			erc: blogPostDataSetERC,
			label: blogPostsDataSetConfig.name,
		});
	});

	await test.step('Setup user role and login as user', async () => {
		await setupUserRoleAndLoginAsUser({
			apiHelpers,
			page,
			roleDefinePermissionsPage,
			rolePage,
			rolesPage,
		});
	});

	await test.step('Go to Data Sets', async () => {
		await dataSetsPage.goto({checkTabVisibility: false});
	});

	await test.step('Assert that no data sets appear on the table', async () => {
		assertTableRowsCount(page, 0);
	});
});

dataSetsTabsTest(
	'Check that there are two different tabs to navigate between Custom and System Data Sets',
	{tag: '@LPD-37431'},
	async ({dataSetsPage, page}) => {
		await test.step('Navigate to Data Sets page, Custom Data Sets tab', async () => {
			await dataSetsPage.goto();
			await expect(
				dataSetsPage.dataSetsEmptyState.locator('.c-empty-state-title')
			).toContainText('No Data Sets Created');
		});

		await test.step('Create Data Set', async () => {
			await dataSetsPage.createDataSet(tableSectionsDataSetConfig);
		});

		await assertTableColumnLabels(page);

		await assertTableCellContent({
			dataSetConfig: tableSectionsDataSetConfig,
			page,
		});

		await assertTableActionLabels(page);

		await test.step('Delete Data Set', async () => {
			await dataSetsPage.deleteDataSet(tableSectionsDataSetConfig.name);
		});

		await test.step('Navigate to System Data Sets tab', async () => {
			await dataSetsPage.goto({dataSetsType: 'System Data Sets'});

			await expect(
				dataSetsPage.dataSetsEmptyState.locator('.c-empty-state-title')
			).toContainText('No System Data Sets Created');
		});
	}
);
