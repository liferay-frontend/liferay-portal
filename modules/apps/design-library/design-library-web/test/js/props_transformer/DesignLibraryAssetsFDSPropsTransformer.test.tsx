/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal, openToast} from 'frontend-js-components-web';

import DesignLibraryAssetsFDSPropsTransformer from '../../../src/main/resources/META-INF/resources/js/props_transformer/DesignLibraryAssetsFDSPropsTransformer';

jest.mock('frontend-js-components-web', () => ({
	openModal: jest.fn(),
	openToast: jest.fn(),
}));

const BASE_PROPS = {
	id: 'fds-design-library-resources',
	items: [],
} as any;

const DELETE_BULK_ACTION = {
	data: {id: 'delete'},
	icon: 'trash',
	label: 'delete-design-assets',
};

const FRAGMENT_SET_ITEM = {
	embedded: {externalReferenceCode: 'fragment-set-erc'},
	entryClassName: 'com.liferay.fragment.model.FragmentCollection',
};

const STYLE_BOOK_ITEM = {
	embedded: {
		actions: {
			delete: {href: '/o/style-books/1', method: 'DELETE'},
		},
		externalReferenceCode: 'style-book-erc',
	},
	entryClassName: 'com.liferay.style.book.model.StyleBookEntry',
};

describe('DesignLibraryAssetsFDSPropsTransformer', () => {
	beforeEach(() => {
		jest.clearAllMocks();
	});

	it('builds the creation menu from the design asset creation items', () => {
		const {creationMenu} = DesignLibraryAssetsFDSPropsTransformer({
			...BASE_PROPS,
			additionalProps: {
				addStyleBookEntryURL: '/style-book',
				canAddStyleBook: true,
			},
		});

		expect(creationMenu?.primaryItems.map((item) => item.label)).toEqual([
			'new-style-book',
		]);
	});

	it('adds an empty creation menu when creation is not allowed', () => {
		expect(
			DesignLibraryAssetsFDSPropsTransformer(BASE_PROPS).creationMenu
				?.primaryItems
		).toEqual([]);
	});

	it('exposes the table view', () => {
		expect(
			DesignLibraryAssetsFDSPropsTransformer(BASE_PROPS).views?.map(
				(view) => view.name
			)
		).toEqual(['table']);
	});

	it('disables the bulk delete action when the selection is not deletable', () => {
		const {bulkActions} = DesignLibraryAssetsFDSPropsTransformer({
			...BASE_PROPS,
			additionalProps: {canManageFragments: false},
			bulkActions: [DELETE_BULK_ACTION],
		}) as any;

		const isDisabled = bulkActions[0].isDisabled;

		expect(
			isDisabled({
				allItemsSelectedActive: true,
				selectedItems: [STYLE_BOOK_ITEM],
			})
		).toBe(true);
		expect(
			isDisabled({
				allItemsSelectedActive: false,
				selectedItems: [FRAGMENT_SET_ITEM],
			})
		).toBe(true);
		expect(
			isDisabled({
				allItemsSelectedActive: false,
				selectedItems: [STYLE_BOOK_ITEM],
			})
		).toBe(false);
	});

	it('enables the bulk delete action for manageable fragment sets', () => {
		const {bulkActions} = DesignLibraryAssetsFDSPropsTransformer({
			...BASE_PROPS,
			additionalProps: {canManageFragments: true},
			bulkActions: [DELETE_BULK_ACTION],
		}) as any;

		expect(
			bulkActions[0].isDisabled({
				allItemsSelectedActive: false,
				selectedItems: [FRAGMENT_SET_ITEM, STYLE_BOOK_ITEM],
			})
		).toBe(false);
	});

	it('opens the confirmation modal on the bulk delete action', () => {
		const {onBulkActionItemClick} = DesignLibraryAssetsFDSPropsTransformer({
			...BASE_PROPS,
			additionalProps: {canManageFragments: true, groupId: 123},
		}) as any;

		onBulkActionItemClick({
			action: {data: {id: 'delete'}},
			loadData: jest.fn(),
			selectedData: {items: [STYLE_BOOK_ITEM]},
		});

		expect(openModal).toHaveBeenCalledWith(
			expect.objectContaining({title: 'delete-design-assets'})
		);
	});

	it('deletes the selected assets when the bulk delete is confirmed', async () => {
		(fetch as any).mockResponseOnce('');

		const loadData = jest.fn();

		const {onBulkActionItemClick} = DesignLibraryAssetsFDSPropsTransformer({
			...BASE_PROPS,
			additionalProps: {canManageFragments: true, groupId: 123},
		}) as any;

		onBulkActionItemClick({
			action: {data: {id: 'delete'}},
			loadData,
			selectedData: {items: [STYLE_BOOK_ITEM]},
		});

		const {buttons} = (openModal as jest.Mock).mock.calls[0][0];

		await buttons[1].onClick({processClose: jest.fn()});

		expect(fetch).toHaveBeenCalledWith(
			'/o/style-books/1',
			expect.objectContaining({method: 'DELETE'})
		);
		expect(openToast).toHaveBeenCalledWith(
			expect.objectContaining({
				message: 'the-selected-design-assets-were-deleted',
				type: 'success',
			})
		);
		expect(loadData).toHaveBeenCalled();
	});
});
