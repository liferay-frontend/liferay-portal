/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as TYPES from '../actions/types';

export default function languageReducer(draft, action) {
	switch (action.type) {
		case TYPES.UPDATE_DRAFT:
			return action.draft;

		default:
			return draft;
	}
}
