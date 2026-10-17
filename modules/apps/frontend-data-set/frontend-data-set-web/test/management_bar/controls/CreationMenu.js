/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// eslint-disable-next-line @liferay/portal/no-cross-module-deep-import
import {checkAccessibility} from '@liferay/layout-js-components-web/test/__lib__/index';
import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import FrontendDataSetContext from '../../../src/main/resources/META-INF/resources/FrontendDataSetContext';
import CreationMenu from '../../../src/main/resources/META-INF/resources/management_bar/controls/CreationMenu';

describe('CreationMenu', () => {
	beforeEach(() => {
		Object.defineProperty(document.body, 'clientWidth', {
			configurable: true,
			value: 1440,
		});
	});

	afterEach(() => {
		delete document.body.clientWidth;
	});

	const renderCreationMenu = (primaryItems) =>
		render(
			<FrontendDataSetContext.Provider value={{loadData: jest.fn()}}>
				<CreationMenu
					inEmptyState={false}
					primaryItems={primaryItems}
				/>
			</FrontendDataSetContext.Provider>
		);

	it('forwards the item className to the dropdown item', async () => {
		renderCreationMenu([
			{className: 'cms-generate-with-ai', label: 'Generate'},
			{label: 'Create Folder'},
		]);

		await userEvent.click(screen.getByTestId('fdsCreationActionButton'));

		expect(screen.getByText('Generate')).toHaveClass(
			'cms-generate-with-ai'
		);
	});

	it('opens a submenu with the nested items', async () => {
		const onClick = jest.fn();

		const {container} = renderCreationMenu([
			{
				items: [
					{label: 'New Basic Fragment', onClick},
					{label: 'New Fragment Set'},
				],
				label: 'Fragments',
			},
			{label: 'New Style Book'},
		]);

		await userEvent.click(screen.getByTestId('fdsCreationActionButton'));

		const fragmentsMenuItem = screen.getByRole('menuitem', {
			name: 'Fragments',
		});

		expect(fragmentsMenuItem).toHaveAttribute('aria-expanded', 'false');

		await userEvent.click(fragmentsMenuItem);

		expect(fragmentsMenuItem).toHaveAttribute('aria-expanded', 'true');

		await checkAccessibility({bestPractices: true, context: container});

		await userEvent.click(
			screen.getByRole('menuitem', {name: 'New Basic Fragment'})
		);

		expect(onClick).toHaveBeenCalledTimes(1);
	});

	it('opens a dropdown for a single group of items', async () => {
		renderCreationMenu([
			{
				items: [
					{label: 'New Basic Fragment'},
					{label: 'New Fragment Set'},
				],
				label: 'Fragments',
			},
		]);

		await userEvent.click(screen.getByTestId('fdsCreationActionButton'));

		expect(screen.getByRole('menuitem', {name: 'Fragments'})).toBeVisible();
	});

	it('forwards the item className to the single creation button', () => {
		renderCreationMenu([
			{className: 'cms-generate-with-ai', label: 'Generate'},
		]);

		expect(screen.getByText('Generate')).toHaveClass(
			'cms-generate-with-ai'
		);
	});
});
