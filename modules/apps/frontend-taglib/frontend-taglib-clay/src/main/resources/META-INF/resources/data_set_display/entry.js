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

import {render, useThunk} from '@liferay/frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useReducer} from 'react';

import AppContext from './AppContext';
import DataSetDisplay from './DataSetDisplay';
import StatesContext, {statesReducer} from './contexts/StatesContext';
import ViewsContext, {viewsReducer} from './views/ViewsContext';

const App = ({
	activeViewSettings: activeState,
	apiURL,
	appURL,
	portletId,
	states,
	views,
	...props
}) => {

	// MOCK
	states = [
		{
			displayType: {
				name: 'table',
				settings: {
					visibleFieldNames: {
						name: true,
						url: true,
					},
				},
			},
			id: 'default-1',
			label: 'Default 1',
		},
		{
			displayType: {
				name: 'table',
				settings: {
					visibleFieldNames: {
						name: true,
						url: false,
					},
				},
			},
			id: 'default-2',
			label: 'Default 2',
		},
		{
			displayType: {
				name: 'table',
				settings: {
					visibleFieldNames: {
						name: false,
						url: true,
					},
				},
			},
			id: 'default-3',
			label: 'Default 3',
		},
	];

	activeState = {...states[0]};
	// END MOCK

	const activeViewName = activeState?.displayType?.name;

	const activeView = activeViewName
		? views.find(({name}) => name === activeViewName)
		: views[0];

	const [viewsState, viewsDispatch] = useThunk(
		useReducer(viewsReducer, {
			activeView,
			views,
			visibleFieldNames:
				activeState?.displayType?.settings?.visibleFieldNames || {},
		})
	);

	const [statesState, statesDispatch] = useThunk(
		useReducer(statesReducer, {
			activeState,
			states,
		})
	);

	return (
		<AppContext.Provider value={{apiURL, appURL, portletId}}>
			<StatesContext.Provider value={[statesState, statesDispatch]}>
				<ViewsContext.Provider value={[viewsState, viewsDispatch]}>
					<DataSetDisplay {...props} />
				</ViewsContext.Provider>
			</StatesContext.Provider>
		</AppContext.Provider>
	);
};

App.proptypes = {
	activeViewSettings: PropTypes.shape({
		displayType: PropTypes.shape({
			name: PropTypes.string, // id of the display view, must match with a view id
			settings: PropTypes.object,
		}),
		filters: PropTypes.array,
		itemsPerPage: PropTypes.number,
		label: PropTypes.string,
		name: PropTypes.string,
		visibleFieldNames: PropTypes.array,
	}),
	apiURL: PropTypes.string,
	appURL: PropTypes.string,
	portletId: PropTypes.string,
	states: PropTypes.arrayOf(
		PropTypes.shape({
			displayType: PropTypes.shape({
				name: PropTypes.string, // id of the display view, must match with a view id
				settings: PropTypes.object,
			}),
			filters: PropTypes.array,
			itemsPerPage: PropTypes.number,
			label: PropTypes.string,
			name: PropTypes.string,
		})
	),
	views: PropTypes.arrayOf(
		PropTypes.shape({
			component: PropTypes.any,
			contentRenderer: PropTypes.string,
			contentRendererModuleURL: PropTypes.string,
			label: PropTypes.string,
			schema: PropTypes.object,
			thumbnail: PropTypes.string,
		})
	).isRequired,
};

export default (...data) => render(App, ...data);
