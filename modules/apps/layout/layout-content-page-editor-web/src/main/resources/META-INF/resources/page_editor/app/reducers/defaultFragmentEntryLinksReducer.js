/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {INIT} from '../actions/types';
import getFragmentEntryLinkIdsFromItemId from '../utils/getFragmentEntryLinkIdsFromItemId';

export default function defaultFragmentEntryLinksReducer(state, action) {
	switch (action.type) {
		case INIT: {
			const {fragmentEntryLinks, layoutData} = state;
			const deletedItemIds = layoutData.deletedItems.map(
				(deletedItem) => deletedItem.itemId
			);
			const newFragmentEntryLinks = {};

			const deletedFragments = deletedItemIds.reduce(
				(acc, deleteItemId) => [
					...acc,
					...getFragmentEntryLinkIdsFromItemId({
						itemId: deleteItemId,
						layoutData,
					}),
				],
				[]
			);

			deletedFragments.forEach((deletedFragment) => {
				newFragmentEntryLinks[deletedFragment] = {
					...fragmentEntryLinks[deletedFragment],
					removed: true,
				};
			});

			return {
				...state,
				fragmentEntryLinks: {
					...fragmentEntryLinks,
					...newFragmentEntryLinks,
				},
			};
		}

		default:
			return state;
	}
}
