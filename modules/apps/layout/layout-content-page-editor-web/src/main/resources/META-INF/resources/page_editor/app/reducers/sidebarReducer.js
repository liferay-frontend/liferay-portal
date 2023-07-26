/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isNullOrUndefined} from '@liferay/layout-js-components-web';

import {SWITCH_SIDEBAR_PANEL} from '../actions/types';

const DEFAULT_PANEL_ID = 'fragments_and_widgets';

export const INITIAL_STATE = {
	hidden: false,
	itemConfigurationOpen: false,
	open: false,
	panelId: DEFAULT_PANEL_ID,
};

export default function sidebarReducer(sidebarStatus = INITIAL_STATE, action) {
	if (action.type === SWITCH_SIDEBAR_PANEL) {
		return {
			hidden: action.hidden,
			itemConfigurationOpen: action.itemConfigurationOpen,
			open: isNullOrUndefined(action.sidebarOpen)
				? sidebarStatus.open
				: action.sidebarOpen,
			panelId:
				action.sidebarPanelId === undefined
					? sidebarStatus.panelId
					: action.sidebarPanelId,
		};
	}

	return sidebarStatus;
}
