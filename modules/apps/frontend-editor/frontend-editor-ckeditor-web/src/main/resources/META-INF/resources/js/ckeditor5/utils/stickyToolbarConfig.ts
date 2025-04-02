/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface IStickyToolbarConfig {
	[key: string]: {
		viewportOffset: {
			bottom?: number;
			left?: number;
			right?: number;
			top?: number;
		};
	};
}

/**
 * Map of viewportOffset configuration for different
 * portlet namespaces
 */

const StickyToolbarConfig: IStickyToolbarConfig = {
	_com_liferay_journal_web_portlet_JournalPortlet_: {
		viewportOffset: {
			top: 120,
		},
	},
};

export default StickyToolbarConfig;
