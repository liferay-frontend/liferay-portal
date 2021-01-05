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

import {act, cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import {AppContextProvider} from '../../../../src/main/resources/META-INF/resources/js/AppContext.es';
import EditEntry from '../../../../src/main/resources/META-INF/resources/js/pages/entry/EditEntry.es';
import {DATA_DEFINITION_RESPONSES, FORM_VIEW} from '../../constants.es';
const {getDataLayoutBuilderProps} = FORM_VIEW;

describe('EditEntry', () => {
	beforeEach(() => {
		jest.useFakeTimers();

		const dataLayoutBuilderProps = getDataLayoutBuilderProps();

		window.Liferay = {
			...window.Liferay,
			Util: {
				...window.Liferay.Util,
				navigate: jest.fn(),
			},
			componentReady: () =>
				new Promise((resolve) =>
					resolve({
						...dataLayoutBuilderProps,
						reactComponentRef: {
							current: {
								getFormNode: jest
									.fn()
									.mockImplementation(() => ({
										addEventListener: jest.fn(),
										removeEventListener: jest.fn(),
									})),
								updateEditingLanguageId: jest.fn(),
							},
						},
					})
				),
		};
	});

	afterEach(() => {
		cleanup();
		jest.clearAllTimers();
		jest.restoreAllMocks();
	});

	afterAll(() => {
		jest.useRealTimers();
	});

	it('renders', async () => {
		fetch.mockResponse(JSON.stringify(DATA_DEFINITION_RESPONSES.ONE_ITEM));

		const {asFragment} = render(
			<AppContextProvider appId={1}>
				<div className="tools-control-group">
					<div className="control-menu-level-1-heading"> </div>
				</div>
				<EditEntry
					dataDefinitionId={1}
					dataRecordId="1"
					redirect="/"
					userLanguageId="en_US"
				/>
			</AppContextProvider>
		);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders as edit-entry and hits save button', async () => {
		fetch.mockResponse(JSON.stringify(DATA_DEFINITION_RESPONSES.ONE_ITEM));

		const {queryByText} = render(
			<AppContextProvider appId={1}>
				<div className="tools-control-group">
					<div className="control-menu-level-1-heading"> </div>
				</div>
				<EditEntry
					dataDefinitionId={1}
					dataRecordId="1"
					redirect="/"
					userLanguageId="en_US"
				/>
			</AppContextProvider>
		);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(queryByText('edit-entry')).toBeTruthy();

		const save = queryByText('save');

		await act(async () => {
			fireEvent.click(save);
		});
	});

	it('renders as add-entry and hits cancel button', async () => {
		fetch.mockResponse(JSON.stringify(DATA_DEFINITION_RESPONSES.ONE_ITEM));

		const {queryByText} = render(
			<AppContextProvider appId={0}>
				<div className="tools-control-group">
					<div className="control-menu-level-1-heading"> </div>
				</div>
				<EditEntry
					dataDefinitionId={0}
					dataRecordId="0"
					redirect="/"
					userLanguageId="en_US"
				/>
			</AppContextProvider>
		);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(queryByText('add-entry')).toBeTruthy();

		const cancel = queryByText('cancel');

		await act(async () => {
			fireEvent.click(cancel);
		});
	});
});
