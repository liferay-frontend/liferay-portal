/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as TYPES from '../actions/types';

export const INITIAL_STATE = {masterLayoutData: null, masterLayoutPlid: 0};

export default function languageReducer(masterLayout = INITIAL_STATE, action) {
	switch (action.type) {
		case TYPES.CHANGE_MASTER_LAYOUT: {
			return {
				masterLayoutData: action.masterLayoutData,
				masterLayoutPlid: action.masterLayoutPlid,
			};
		}

		default:
			return masterLayout;
	}
}
