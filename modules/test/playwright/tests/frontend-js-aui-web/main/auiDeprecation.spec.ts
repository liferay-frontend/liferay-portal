/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {auiSamplePageTest} from './fixtures/auiSamplePageTest';

const testWithDeprecatedAUIModules = mergeTests(
	featureFlagsTest({
		'LPD-57347': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	loginTest(),
	auiSamplePageTest
);

const testWithoutDeprecatedAUIModules = mergeTests(
	featureFlagsTest({
		'LPD-57347': {enabled: false},
		'LPS-178052': {enabled: true},
	}),
	loginTest(),
	auiSamplePageTest
);

testWithDeprecatedAUIModules(
	'Deprecated AlloyUI modules load while the feature flag is enabled',
	{
		tag: ['@LPD-104702', '@LPD-51552'],
	},
	async ({auiSamplePage}) => {
		await expect(auiSamplePage.getChecker()).toHaveAttribute(
			'data-status',
			'pass'
		);

		await expect(auiSamplePage.getSummary()).toHaveText(
			/behaved as expected/
		);

		const autoFields = auiSamplePage.getModule('liferay-auto-fields');

		await expect(autoFields).toHaveAttribute(
			'data-feature-flag-enabled',
			'true'
		);
		await expect(autoFields).toHaveAttribute('data-status', 'pass');

		const menu = auiSamplePage.getModule('liferay-menu');

		await expect(menu).toHaveAttribute('data-feature-flag-enabled', 'true');
		await expect(menu).toHaveAttribute('data-status', 'pass');

		const menuFilter = auiSamplePage.getModule('liferay-menu-filter');

		await expect(menuFilter).toHaveAttribute(
			'data-feature-flag-enabled',
			'true'
		);
		await expect(menuFilter).toHaveAttribute('data-status', 'pass');
	}
);

testWithoutDeprecatedAUIModules(
	'Deprecated AlloyUI modules are gone while the feature flag is disabled',
	{
		tag: ['@LPD-104702', '@LPD-51552'],
	},
	async ({auiSamplePage, page}) => {
		await expect(auiSamplePage.getChecker()).toHaveAttribute(
			'data-status',
			'pass'
		);

		await expect(
			page.getByTestId('auiDeprecationCheckerOverlay')
		).toHaveText('no');

		const autoFields = auiSamplePage.getModule('liferay-auto-fields');

		await expect(autoFields).toHaveAttribute(
			'data-feature-flag-enabled',
			'false'
		);
		await expect(autoFields).toHaveAttribute('data-status', 'pass');

		const menu = auiSamplePage.getModule('liferay-menu');

		await expect(menu).toHaveAttribute(
			'data-feature-flag-enabled',
			'false'
		);
		await expect(menu).toHaveAttribute('data-status', 'pass');

		const menuFilter = auiSamplePage.getModule('liferay-menu-filter');

		await expect(menuFilter).toHaveAttribute(
			'data-feature-flag-enabled',
			'false'
		);
		await expect(menuFilter).toHaveAttribute('data-status', 'pass');
	}
);
