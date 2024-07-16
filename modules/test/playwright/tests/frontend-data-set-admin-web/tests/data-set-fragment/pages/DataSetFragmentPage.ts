/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {ApiHelpers} from '../../../../../helpers/ApiHelpers';
import {DEFAULT_LABEL} from '../../../utils/constants';
import {VisualizationMode} from '../../../utils/types';

export class DataSetFragmentPage {
	readonly activeViewSelector: Locator;
	readonly addFilterButton: Locator;
	readonly apiHelpers: ApiHelpers;
	readonly cardsWrapper: Locator;
	readonly creationMenuButton: Locator;
	readonly editPageButton: Locator;
	readonly emptyStateTitle: Locator;
	readonly filterButton: Locator;
	readonly filterResumeButton: Locator;
	readonly fragmentWidgetSearchInput: Locator;
	readonly listWrapper: Locator;
	readonly loadingIndicator: Locator;
	readonly page: Page;
	readonly paginationResults: Locator;
	readonly paginationWrapper: Locator;
	readonly publishPageButton: Locator;
	readonly resetFilterButton: Locator;
	readonly tableWrapper: Locator;

	fdsFilterItem: Locator;

	constructor(page: Page) {
		this.activeViewSelector = page.getByLabel('Show View Options');
		this.apiHelpers = new ApiHelpers(page);

		this.creationMenuButton = page.getByRole('button', {name: 'New'});
		this.emptyStateTitle = page.getByText('No Results Found');

		this.addFilterButton = page.getByRole('button', {
			exact: true,
			name: 'Add Filter',
		});
		this.filterButton = page.getByRole('button', {
			exact: true,
			name: 'Filter',
		});
		this.filterResumeButton = page.locator('.filter-resume');
		this.resetFilterButton = page.getByRole('button', {
			exact: true,
			name: 'Reset Filters',
		});

		this.cardsWrapper = page.locator('.cards-container');
		this.listWrapper = page.locator('.list-sheet');
		this.paginationWrapper = page.locator('.data-set-pagination-wrapper');
		this.paginationResults = page.locator('.pagination-results');
		this.tableWrapper = page.locator('.dnd-table');
		this.fragmentWidgetSearchInput = page.getByLabel(
			'Search Fragments and Widgets'
		);
		this.loadingIndicator = page.locator('.fds .loading-animation');
		this.page = page;
		this.publishPageButton = page.getByRole('button', {
			name: 'Publish',
		});
	}

	async goto() {
		await this.page.goto('/');
	}

	async selectFilter(filterLabel) {
		await this.filterButton.waitFor({state: 'visible'});
		const filterDropdownId = await this.filterButton.evaluate((node) =>
			node.getAttribute('aria-controls')
		);
		await this.filterButton.click();
		await this.page
			.locator(`#${filterDropdownId}`)
			.waitFor({state: 'visible'});
		this.fdsFilterItem = this.page.locator(`#${filterDropdownId}`);
		this.fdsFilterItem
			.getByRole('menuitem', {
				name: filterLabel,
			})
			.click();
	}

	async changeVisualizationMode(visualizationMode: VisualizationMode) {
		await this.activeViewSelector.waitFor({
			state: 'visible',
		});
		await this.activeViewSelector.click();

		await this.page
			.getByRole('listbox')
			.getByRole('option', {name: visualizationMode})
			.click();
	}

	async configureDataSetFragment({
		dataSetLabel = DEFAULT_LABEL.DATA_SET,
		layout,
	}: {
		dataSetLabel?: string;
		layout: Layout;
	}) {
		await this.setupPageAndFragment(layout);

		await this.page
			.frameLocator('iframe[title="Select"]')
			.locator('.fds-admin-item-selector')
			.waitFor({state: 'visible'});

		await this.page
			.frameLocator('iframe[title="Select"]')
			.locator('li')
			.filter({hasText: dataSetLabel})
			.first()
			.click();

		await this.page
			.frameLocator('iframe[title="Select"]')
			.getByRole('button', {name: 'Save'})
			.click();

		await this.publishPage();

		await this.goToPage({layout});

		await this.page
			.locator('.data-set-content-wrapper')
			.waitFor({state: 'visible'});
	}

	async configureEmptyDataSetFragment({layout}: {layout: Layout}) {
		await this.setupPageAndFragment(layout);

		await this.page
			.frameLocator('iframe[title="Select"]')
			.locator('.c-empty-state-title')
			.waitFor({state: 'visible'});
	}

	async editPage({layout}: {layout: Layout}) {
		await this.page.goto(`/web/guest${layout.friendlyURL}?p_l_mode=edit`);
	}

	async goToPage({layout}: {layout: Layout}) {
		await this.page.goto(`/web/guest${layout.friendlyURL}`);
	}

	async publishPage() {
		await this.publishPageButton.click();

		await this.publishPageButton.isEnabled();
	}

	async searchFragmentOrWidget(itemName: string) {
		await this.fragmentWidgetSearchInput.fill(itemName);
	}

	async setupPageAndFragment(layout: Layout) {
		await this.editPage({layout});

		await this.searchFragmentOrWidget('Data Set');

		const dataSetMenuItem = this.page.getByRole('menuitem', {
			exact: true,
			name: 'Data Set Add Data Set Mark Data Set as Favorite',
		});

		await dataSetMenuItem.dragTo(
			this.page.getByText('Place fragments or widgets here')
		);

		const fragmentSelectionArea = this.page.getByText('Select a data set');

		await expect(fragmentSelectionArea).toBeVisible();

		await fragmentSelectionArea.click();

		await this.page
			.getByLabel('Configuration Panel')
			.getByRole('button', {exact: true, name: 'Select Data Set'})
			.click();

		await this.page.getByRole('dialog').isVisible();

		await this.page.getByRole('heading', {name: 'Select'}).isVisible();
	}
}
