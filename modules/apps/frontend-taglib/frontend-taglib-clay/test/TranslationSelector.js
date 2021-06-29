/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import TranslationSelector from '../src/main/resources/META-INF/resources/translation/TranslationSelector';

const locales = [
	{
		label: 'en-US',
		symbol: 'en-us',
	},
	{
		label: 'es-ES',
		symbol: 'es-es',
	},
	{
		label: 'fr-FR',
		symbol: 'fr-fr',
	},
	{
		label: 'hr-HR',
		symbol: 'hr-hr',
	},
];

const translations = {
	'en-US': 'Apple',
	'es-ES': 'Manzana',
};

describe('TranslationSelector', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<TranslationSelector
				defaultLocale={locales[0]}
				locales={locales}
				onSelectedLocaleChange={() => {}}
				selectedLocale={locales[0]}
				translations={translations}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders with a small button', () => {
		const {container} = render(
			<TranslationSelector
				defaultLocale={locales[0]}
				locales={locales}
				onSelectedLocaleChange={() => {}}
				selectedLocale={locales[0]}
				small={true}
				translations={translations}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders as a language selector', () => {
		const {container} = render(
			<TranslationSelector
				defaultLocale={locales[0]}
				locales={locales}
				onSelectedLocaleChange={() => {}}
				selectedLocale={locales[0]}
				translations={translations}
				variant="language"
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders as a translation selector', () => {
		const {container} = render(
			<TranslationSelector
				defaultLocale={locales[0]}
				locales={locales}
				onSelectedLocaleChange={() => {}}
				selectedLocale={locales[0]}
				translations={translations}
				variant="translation"
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders as a translation selector', () => {
		const onSelectedLocaleChange = jest.fn();

		const {container} = render(
			<TranslationSelector
				defaultLocale={locales[0]}
				locales={locales}
				onSelectedLocaleChange={onSelectedLocaleChange}
				selectedLocale={locales[0]}
				translations={translations}
				variant="translation"
			/>
		);

		fireEvent.click(container.querySelector('.dropdown-toggle'), {});

		fireEvent.click(document.querySelectorAll('.dropdown-item')[1], {});

		expect(onSelectedLocaleChange).toBeCalledWith({
			label: 'es-ES',
			symbol: 'es-es',
		});

		expect(container).toMatchSnapshot();
	});
});
