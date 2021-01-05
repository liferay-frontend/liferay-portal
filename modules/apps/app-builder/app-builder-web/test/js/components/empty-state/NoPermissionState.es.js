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

import {act, cleanup, render} from '@testing-library/react';
import React from 'react';

import NoPermissionState from '../../../../src/main/resources/META-INF/resources/js/components/empty-state/NoPermissionState.es';
import {FORM_VIEW} from '../../constants.es';
const {getDataLayoutBuilderProps} = FORM_VIEW;

const noPermissionInfo = {
	description: 'You do not have access to this app. Sign in to access it.',
	title: 'No Permissions',
};

describe('NoPermissionState', () => {
	let dataLayoutBuilderProps;

	beforeEach(() => {
		jest.useFakeTimers();

		dataLayoutBuilderProps = getDataLayoutBuilderProps();

		window.Liferay = {
			...window.Liferay,
			componentReady: () =>
				new Promise((resolve) => resolve(dataLayoutBuilderProps)),
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
		const {asFragment} = render(<NoPermissionState />);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders with the given description and title', async () => {
		const {queryByText} = render(
			<NoPermissionState
				description={noPermissionInfo.description}
				title={noPermissionInfo.title}
			/>
		);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(queryByText('No Permissions')).toBeTruthy();

		expect(
			queryByText(
				'You do not have access to this app. Sign in to access it.'
			)
		).toBeTruthy();
	});
});
