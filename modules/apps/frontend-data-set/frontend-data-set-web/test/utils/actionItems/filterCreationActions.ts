/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import filterCreationActions from '../../../src/main/resources/META-INF/resources/utils/actionItems/filterCreationActions';

describe('filterCreationActions', () => {
	it('filters the nested items by permission', () => {
		expect(
			filterCreationActions({
				customActions: [
					{
						items: [
							{
								data: {permissionKey: 'create'},
								label: 'New Basic Fragment',
							},
							{
								data: {permissionKey: 'createBatch'},
								label: 'New Fragment Set',
							},
						],
						label: 'Fragments',
					},
					{
						items: [
							{
								data: {permissionKey: 'replace'},
								label: 'New Master Page',
							},
						],
						label: 'Master Pages',
					},
					{
						data: {permissionKey: 'update'},
						items: [{label: 'New Content Page Template'}],
						label: 'Page Templates',
					},
					{label: 'New Style Book'},
				],
				globalCollectionActions: {create: {}},
			})
		).toEqual([
			{
				items: [
					{
						data: {permissionKey: 'create'},
						label: 'New Basic Fragment',
					},
				],
				label: 'Fragments',
			},
			{label: 'New Style Book'},
		]);
	});
});
