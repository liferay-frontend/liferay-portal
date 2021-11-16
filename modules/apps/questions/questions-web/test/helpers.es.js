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

import React from 'react';

import '@testing-library/jest-dom/extend-expect';
import {render} from '@testing-library/react';
import {ClientContext, GraphQLClient} from 'graphql-hooks';
import {createMemoryHistory} from 'history';
import {Router} from 'react-router-dom';

import {AppContext} from '../src/main/resources/META-INF/resources/js/AppContext.es';

export function renderComponent({
	contextValue = {},
	fetch,
	ui,
	route = '/',
	history = createMemoryHistory({initialEntries: [route]}),
}) {
	window.scrollTo = jest.fn();

	const client = new GraphQLClient({
		fetch,
		method: 'POST',
		url: '/o/graphql',
	});

	return {
		...render(
			<ClientContext.Provider value={client}>
				<Router history={history}>
					<AppContext.Provider value={contextValue}>
						{ui}
					</AppContext.Provider>
				</Router>
			</ClientContext.Provider>
		),
		history,
	};
}
