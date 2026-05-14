/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IBaseFilterState, IBulkActionItem, IFDSState} from '../types';

const hasPermission = (
	bulkAction: IBulkActionItem,
	selectedItems: Array<any>
): boolean => {
	if (!bulkAction?.data?.permissionKey) {
		return true;
	}

	if (!selectedItems?.length) {
		return true;
	}

	const permissionKey = bulkAction.data.permissionKey.toLowerCase();

	return selectedItems.every((item) => {
		if (!item?.actions) {
			return false;
		}

		return Object.keys(item.actions).some(
			(itemAction) => itemAction.toLowerCase() === permissionKey
		);
	});
};

const filterBulkActions = ({
	allItemsSelectedActive,
	bulkActions,
	globalFDSState,
	selectedItems,
}: {
	allItemsSelectedActive: boolean;
	bulkActions: Array<IBulkActionItem>;
	globalFDSState: IFDSState;
	selectedItems: Array<any>;
}): Array<IBulkActionItem> => {
	if (!bulkActions) {
		return [];
	}

	return bulkActions.filter((bulkAction) => {
		if (!hasPermission(bulkAction, selectedItems)) {
			return false;
		}

		return (
			!bulkAction.isVisible ||
			bulkAction.isVisible({
				activeFilters: globalFDSState?.filters.filter(
					(filter: IBaseFilterState) => filter?.active
				),
				activeSearch: globalFDSState.search,
				allItemsSelectedActive,
				selectedItems,
			})
		);
	});
};

export default filterBulkActions;
