/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	AUI().applyConfig({
		groups: {
			commercefrontend: {
				base: MODULE_PATH + '/js/',
				combine: Liferay.AUI.getCombine(),
				modules: {
					'liferay-commerce-frontend-asset-categories-selector': {
						path:
							'liferay_commerce_frontend_asset_categories_selector.js',
						requires: [
							'aui-tree',
							'liferay-commerce-frontend-asset-tag-selector',
						],
					},
					'liferay-commerce-frontend-asset-tag-selector': {
						path: 'liferay_commerce_frontend_asset_tag_selector.js',
						requires: [
							'aui-io-plugin-deprecated',
							'aui-live-search-deprecated',
							'aui-template-deprecated',
							'aui-textboxlist-deprecated',
							'datasource-cache',
							'liferay-service-datasource',
						],
					},
					'liferay-commerce-frontend-management-bar-state': {
						condition: {
							trigger: 'liferay-management-bar',
						},
						path: 'management_bar_state.js',
						requires: ['liferay-management-bar'],
					},
				},
				root: MODULE_PATH + '/js/',
			},
		},
	});
})();
