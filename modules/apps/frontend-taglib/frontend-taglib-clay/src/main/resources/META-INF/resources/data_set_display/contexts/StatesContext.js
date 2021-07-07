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

import {ACTION_UPDATE_ACTIVE_DELTA} from '../actions/updateActiveDelta';
import {ACTION_UPDATE_ACTIVE_STATE} from '../actions/updateActiveState';

const dispatch = () => {};

export const statesReducer = (state, {type, value}) => {
	const {activeState, states} = state;

	if (type === ACTION_UPDATE_ACTIVE_STATE) {
		return {
			...state,
			activeState: states.find(({id}) => id === value),
		};
	}
	else if (type === ACTION_UPDATE_ACTIVE_DELTA) {
		return {
			...state,
			activeState: {
				...activeState,
				delta: value,
			},
		};
	}

	return state;
};

const StatesContext = React.createContext([
	{
		activeState: null,
		states: null,
	},
	dispatch,
]);

export default StatesContext;
