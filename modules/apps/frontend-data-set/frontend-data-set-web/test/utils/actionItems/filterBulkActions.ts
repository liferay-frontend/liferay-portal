/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import filterBulkActions from '../../../src/main/resources/META-INF/resources/utils/actionItems/filterBulkActions';
import {
	IBaseFilterState,
	IBulkActionItem,
	IFDSState,
} from '../../../src/main/resources/META-INF/resources/utils/types';

describe('filterBulkActions', () => {
	const globalFDSState: IFDSState = {
		filters: [] as Array<IBaseFilterState>,
		search: {query: ''},
	};
	const selectedItems = [
		{color: 'blue', id: 1, status: 'active'},
		{color: 'red', id: 2, status: 'inactive'},
	];

	describe('when bulkActions is missing or empty', () => {
		it('returns an empty array', () => {
			expect(
				filterBulkActions({
					allItemsSelectedActive: false,
					bulkActions: null as unknown as IBulkActionItem[],
					globalFDSState,
					selectedItems,
				})
			).toEqual([]);
		});
	});

	describe('when isVisible is not defined', () => {
		it('returns all actions', () => {
			const bulkActions: IBulkActionItem[] = [
				{href: '/action1'},
				{href: '/action2'},
			];

			const result = filterBulkActions({
				allItemsSelectedActive: false,
				bulkActions,
				globalFDSState,
				selectedItems,
			});

			expect(result).toHaveLength(2);
			expect(result).toEqual(bulkActions);
		});
	});

	describe('when isVisible is defined', () => {
		it('filters actions based on the isVisible callback', () => {
			const isVisibleFn = jest.fn(
				({selectedItems = []}: {selectedItems?: Array<any>}) => {
					return selectedItems.every((item: any) => {
						return item.status === 'inactive';
					});
				}
			);

			const bulkActions: IBulkActionItem[] = [
				{
					data: {id: 'hidden-action'},
					isVisible: isVisibleFn,
				},
			];

			const result = filterBulkActions({
				allItemsSelectedActive: false,
				bulkActions,
				globalFDSState,
				selectedItems,
			});

			expect(result).toHaveLength(0);
			expect(isVisibleFn).toHaveBeenCalledWith({
				activeFilters: [],
				activeSearch: {
					query: '',
				},
				allItemsSelectedActive: false,
				selectedItems,
			});
		});

		it('evaluates isVisible callback and includes the action when allItemsSelectedActive is true if it returns true', () => {
			const isVisibleFn = jest.fn().mockReturnValue(true);
			const bulkActions: IBulkActionItem[] = [
				{
					data: {id: 'visible-action-but-all-selected'},
					isVisible: isVisibleFn,
				},
			];

			const result = filterBulkActions({
				allItemsSelectedActive: true,
				bulkActions,
				globalFDSState,
				selectedItems,
			});

			expect(result).toHaveLength(1);
			expect(result[0].data?.id).toBe('visible-action-but-all-selected');
			expect(isVisibleFn).toHaveBeenCalledWith({
				activeFilters: [],
				activeSearch: {
					query: '',
				},
				allItemsSelectedActive: true,
				selectedItems,
			});
		});
	});

	describe('when permissionKey is set on the bulk action', () => {
		const deleteBulkAction: IBulkActionItem = {
			data: {id: 'delete', permissionKey: 'delete'},
		};

		it('hides the action when any selected item lacks the permission', () => {
			const itemsMixedPermissions = [
				{actions: {delete: {href: '/o/x/1', method: 'DELETE'}}, id: 1},
				{actions: {get: {href: '/o/x/2', method: 'GET'}}, id: 2},
			];

			const result = filterBulkActions({
				allItemsSelectedActive: false,
				bulkActions: [deleteBulkAction],
				globalFDSState,
				selectedItems: itemsMixedPermissions,
			});

			expect(result).toHaveLength(0);
		});

		it('hides the action when no selected item has the permission', () => {
			const itemsWithoutDelete = [
				{actions: {get: {href: '/o/x/1', method: 'GET'}}, id: 1},
				{actions: {get: {href: '/o/x/2', method: 'GET'}}, id: 2},
			];

			const result = filterBulkActions({
				allItemsSelectedActive: false,
				bulkActions: [deleteBulkAction],
				globalFDSState,
				selectedItems: itemsWithoutDelete,
			});

			expect(result).toHaveLength(0);
		});

		it('shows the action regardless of items actions map (backwards compatible)', () => {
			const bulkActions: IBulkActionItem[] = [
				{data: {id: 'no-perm-key'}},
			];

			const result = filterBulkActions({
				allItemsSelectedActive: false,
				bulkActions,
				globalFDSState,
				selectedItems: [{id: 1}],
			});

			expect(result).toHaveLength(1);
		});

		it('shows the action when every selected item has the permission in its actions map', () => {
			const itemsWithDelete = [
				{actions: {delete: {href: '/o/x/1', method: 'DELETE'}}, id: 1},
				{actions: {delete: {href: '/o/x/2', method: 'DELETE'}}, id: 2},
			];

			const result = filterBulkActions({
				allItemsSelectedActive: false,
				bulkActions: [deleteBulkAction],
				globalFDSState,
				selectedItems: itemsWithDelete,
			});

			expect(result).toHaveLength(1);
		});

		it('still hides the action when permissionKey passes but isVisible returns false', () => {
			const itemsWithDelete = [
				{actions: {delete: {href: '/o/x/1', method: 'DELETE'}}, id: 1},
			];

			const result = filterBulkActions({
				allItemsSelectedActive: false,
				bulkActions: [
					{
						...deleteBulkAction,
						isVisible: () => false,
					},
				],
				globalFDSState,
				selectedItems: itemsWithDelete,
			});

			expect(result).toHaveLength(0);
		});
	});
});
