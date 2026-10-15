/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {journalPagesTest} from '../../journal-web/main/fixtures/journalPagesTest';
import {aiCreatorPagesTest} from './fixtures/aiCreatorPagesTest';

const test = mergeTests(
	aiCreatorPagesTest,
	dataApiHelpersTest,
	isolatedSiteTest,
	journalPagesTest,
	loginTest()
);

test(
	'Access the OpenAI configuration page from the Instance Settings and the Site Settings',
	{tag: '@LPS-179484'},
	async ({aiCreatorInstanceSettingsPage, page, site, siteSettingsPage}) => {

		// The OpenAI settings are reachable from the Instance Settings

		await aiCreatorInstanceSettingsPage.goto();

		await expect(aiCreatorInstanceSettingsPage.dalleCheckbox).toBeChecked();
		await expect(
			page.getByLabel('Enable ChatGPT to Create Content')
		).toHaveCount(0);
		await expect(aiCreatorInstanceSettingsPage.apiKeyInput).toHaveValue('');

		// The OpenAI settings are reachable from the Site Settings

		await siteSettingsPage.goToSiteSetting(
			'AI Creator',
			'OpenAI',
			site.friendlyUrlPath
		);

		await expect(aiCreatorInstanceSettingsPage.dalleCheckbox).toBeChecked();
		await expect(
			page.getByLabel('Enable ChatGPT to Create Content')
		).toHaveCount(0);
		await expect(aiCreatorInstanceSettingsPage.apiKeyInput).toHaveValue('');

		// The How do I get an API key link points to the OpenAI docs

		await expect(
			page.getByRole('link', {name: 'How do I get an API key?'})
		).toHaveAttribute(
			'href',
			'https://platform.openai.com/docs/api-reference/authentication'
		);
	}
);

test(
	'The AI Creator button is not available in the Web Content editor',
	{tag: '@LPD-108694'},
	async ({journalPage, page, site}) => {
		await journalPage.goto(site.friendlyUrlPath);
		await journalPage.goToCreateArticle('Basic Web Content');

		await journalPage.articleContentTextBox.click();

		await expect(
			page.getByRole('button', {name: 'Bold'}).first()
		).toBeVisible();
		await expect(
			page.getByRole('button', {name: 'Create AI Content'})
		).toHaveCount(0);
	}
);

test(
	'Cannot enable OpenAI from the Site Settings after disabling it from the Instance Settings',
	{tag: '@LPS-179484'},
	async ({aiCreatorInstanceSettingsPage, page, site, siteSettingsPage}) => {
		try {
			await aiCreatorInstanceSettingsPage.disableDalleCreateImages();

			await expect(async () => {
				await siteSettingsPage.goToSiteSetting(
					'AI Creator',
					'OpenAI',
					site.friendlyUrlPath
				);

				await expect(
					aiCreatorInstanceSettingsPage.dalleCheckbox
				).toBeDisabled({timeout: 3000});
			}).toPass({timeout: 30000});

			await expect(
				page.getByText(
					'To enable DALL-E for this site, first enable it for your instance.'
				)
			).toBeVisible();
		}
		finally {
			await aiCreatorInstanceSettingsPage.enableDalleCreateImages();
		}
	}
);

test(
	'Configure the API key and see an error message when a generic error happens',
	{tag: ['@LPS-179485', '@LPS-188490']},
	async ({
		aiCreatorInstanceSettingsPage,
		enableMockAICreatorOpenAIClient,
		page,
	}) => {
		await enableMockAICreatorOpenAIClient();

		try {

			// A valid API key is saved successfully

			await aiCreatorInstanceSettingsPage.addApiKey();

			await expect(aiCreatorInstanceSettingsPage.apiKeyInput).toHaveValue(
				'VALID_API_KEY'
			);

			// A generic error while validating the API key is surfaced

			await aiCreatorInstanceSettingsPage.apiKeyInput.fill(
				'OPENAI_API_IOEXCEPTION'
			);
			await aiCreatorInstanceSettingsPage.saveButton.click();

			await expect(
				page.getByText(
					'An unexpected error occurred while validating the API key.'
				)
			).toBeVisible();

			await expect(aiCreatorInstanceSettingsPage.apiKeyInput).toHaveValue(
				'OPENAI_API_IOEXCEPTION'
			);
		}
		finally {
			await aiCreatorInstanceSettingsPage.removeApiKey();
		}
	}
);

test(
	'View an error message when providing a wrong API key',
	{tag: '@LPS-188490'},
	async ({
		aiCreatorInstanceSettingsPage,
		enableMockAICreatorOpenAIClient,
		page,
	}) => {
		await enableMockAICreatorOpenAIClient();

		await aiCreatorInstanceSettingsPage.goto();

		// A wrong API key surfaces the OpenAI incorrect-key error

		const apiKey =
			'OPENAI_API_Incorrect API key provided: INVALID_KEY. You can find your API key at https://platform.openai.com/account/api-keys._ERROR_MESSAGE';

		await aiCreatorInstanceSettingsPage.apiKeyInput.fill(apiKey);
		await aiCreatorInstanceSettingsPage.saveButton.click();

		await expect(
			page.getByText(
				'Incorrect API key provided: INVALID_KEY. You can find your API key at https://platform.openai.com/account/api-keys. Check this link for further information about OpenAI issues.'
			)
		).toBeVisible();

		await expect(aiCreatorInstanceSettingsPage.apiKeyInput).toHaveValue(
			apiKey
		);
	}
);
