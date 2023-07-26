/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SWITCH_VIEWPORT_SIZE} from '../actions/types';

export const INITIAL_STATE = 'desktop';

export default function selectedViewportSizeReducer(
	viewportStatus = INITIAL_STATE,
	action
) {
	if (action.type === SWITCH_VIEWPORT_SIZE) {
		return action.size;
	}

	return viewportStatus;
}
