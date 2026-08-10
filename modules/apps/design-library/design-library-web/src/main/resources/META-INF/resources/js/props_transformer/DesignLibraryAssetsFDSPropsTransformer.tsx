/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IFrontendDataSetProps} from '@liferay/frontend-data-set-web';
import {openModal, openToast} from 'frontend-js-components-web';
import {fetch} from 'frontend-js-web';
import React from 'react';

import {
	FRAGMENT_COLLECTION_ENTRY_CLASS_NAME,
	TableCellContentType,
} from '../constants';
import DesignLibraryService from '../services/DesignLibraryService';
import {
	AuthorRenderer,
	FromNowDateTimeRenderer,
	LinkRenderer,
	ResourceTypeRenderer,
} from './cell_renderers';
import getDesignAssetCreationItems, {
	DesignAssetCreationProps,
} from './getDesignAssetCreationItems';

interface DesignAssetItem {
	embedded?: {
		actions?: {
			delete?: {href: string; method: string};
		};
		externalReferenceCode?: string;
	};
	entryClassName?: string;
}

function isDeletableDesignAsset(
	item: DesignAssetItem,
	canManageFragments: boolean
): boolean {
	if (item.entryClassName === FRAGMENT_COLLECTION_ENTRY_CLASS_NAME) {
		return canManageFragments && !!item.embedded?.externalReferenceCode;
	}

	return !!item.embedded?.actions?.delete;
}

async function deleteDesignAssets({
	groupId,
	items,
}: {
	groupId: number;
	items: Array<DesignAssetItem>;
}) {
	const fragmentCollectionERCs: Array<string> = [];
	const promises: Array<Promise<unknown>> = [];

	for (const item of items) {
		if (item.entryClassName === FRAGMENT_COLLECTION_ENTRY_CLASS_NAME) {
			if (item.embedded?.externalReferenceCode) {
				fragmentCollectionERCs.push(
					item.embedded.externalReferenceCode
				);
			}
		}
		else if (item.embedded?.actions?.delete) {
			promises.push(
				DesignLibraryService.remove(item.embedded.actions.delete)
			);
		}
	}

	if (fragmentCollectionERCs.length) {
		const response = await fetch(
			'/api/jsonws/fragment.fragmentcollection/get-fragment-collections',
			{
				body: new URLSearchParams({
					end: '-1',
					groupId: String(groupId),
					p_auth: Liferay.authToken,
					start: '-1',
				}),
				method: 'POST',
			}
		);

		const fragmentCollections = await response.json();

		const fragmentCollectionIds = fragmentCollections
			.filter((fragmentCollection: any) =>
				fragmentCollectionERCs.includes(
					fragmentCollection.externalReferenceCode
				)
			)
			.map(
				(fragmentCollection: any) =>
					fragmentCollection.fragmentCollectionId
			);

		if (fragmentCollectionIds.length) {
			promises.push(
				fetch(
					'/api/jsonws/fragment.fragmentcollection/delete-fragment-collections',
					{
						body: new URLSearchParams({
							fragmentCollectionIds: `[${fragmentCollectionIds.join(',')}]`,
							p_auth: Liferay.authToken,
						}),
						method: 'POST',
					}
				)
			);
		}
	}

	const results = await Promise.allSettled(promises);

	if (results.some((result) => result.status === 'rejected')) {
		throw new Error('some-design-assets-could-not-be-deleted');
	}
}

export default function DesignLibraryAssetsFDSPropsTransformer(
	props: IFrontendDataSetProps & {
		additionalProps?: DesignAssetCreationProps & {
			canManageFragments?: boolean;
			groupId?: number;
		};
	}
): IFrontendDataSetProps {
	const canManageFragments = !!props.additionalProps?.canManageFragments;

	return {
		...props,
		bulkActions: ((props as any).bulkActions || []).map(
			(bulkAction: any) => {
				if (bulkAction.data?.id !== 'delete') {
					return bulkAction;
				}

				return {
					...bulkAction,
					isDisabled: ({
						allItemsSelectedActive,
						selectedItems,
					}: {
						allItemsSelectedActive: boolean;
						selectedItems: Array<DesignAssetItem>;
					}) =>
						allItemsSelectedActive ||
						selectedItems.some(
							(item) =>
								!isDeletableDesignAsset(
									item,
									canManageFragments
								)
						),
				};
			}
		),
		creationMenu: {
			primaryItems: getDesignAssetCreationItems(
				props.additionalProps || {}
			),
		},
		customRenderers: {
			tableCell: [
				{
					component: (rendererProps: any) => {
						const isFragmentCollection =
							rendererProps?.itemData?.entryClassName ===
							FRAGMENT_COLLECTION_ENTRY_CLASS_NAME;

						return (
							<LinkRenderer
								{...rendererProps}
								options={{
									actionId: isFragmentCollection
										? 'view'
										: 'edit',
								}}
								stickerClassName={
									isFragmentCollection
										? 'design-library-fds-sticker-fragment-set'
										: 'design-library-fds-sticker-stylebook'
								}
								symbol={getSymbol(
									rendererProps?.itemData?.entryClassName
								)}
							/>
						);
					},
					name: TableCellContentType.DESIGN_LIBRARY_LINK,
					type: 'internal',
				},
				{
					component: AuthorRenderer,
					name: TableCellContentType.AUTHOR,
					type: 'internal',
				},
				{
					component: ResourceTypeRenderer,
					name: TableCellContentType.RESOURCE_TYPE,
					type: 'internal',
				},
				{
					component: FromNowDateTimeRenderer,
					name: TableCellContentType.FROM_NOW_DATE_TIME,
					type: 'internal',
				},
			],
		},
		hideManagementBarInEmptyState: true,
		onBulkActionItemClick: ({
			action,
			loadData,
			selectedData,
		}: {
			action: {data: {id: string}};
			loadData: () => void;
			selectedData: {items: Array<DesignAssetItem>};
		}) => {
			if (action?.data?.id !== 'delete') {
				return;
			}

			const items = selectedData?.items || [];

			if (!items.length) {
				return;
			}

			openModal({
				bodyHTML: `<p>${Liferay.Language.get('are-you-sure-you-want-to-delete-the-selected-design-assets')}</p>`,
				buttons: [
					{
						autoFocus: true,
						displayType: 'secondary',
						label: Liferay.Language.get('cancel'),
						type: 'cancel',
					},
					{
						displayType: 'danger',
						label: Liferay.Language.get('delete'),
						onClick: async ({
							processClose,
						}: {
							processClose: () => void;
						}) => {
							processClose();

							try {
								await deleteDesignAssets({
									groupId:
										props.additionalProps?.groupId || 0,
									items,
								});

								openToast({
									message: Liferay.Language.get(
										'the-selected-design-assets-were-deleted'
									),
									type: 'success',
								});

								loadData?.();
							}
							catch (error) {
								openToast({
									message: Liferay.Language.get(
										'an-unexpected-error-occurred'
									),
									type: 'danger',
								});
							}
						},
					},
				],
				status: 'danger',
				title: Liferay.Language.get('delete-design-assets'),
			});
		},
		views: [
			{
				contentRenderer: 'table',
				default: true,
				label: Liferay.Language.get('table'),
				name: 'table',
				schema: {
					fields: [
						{
							actionId: 'edit',
							contentRenderer:
								TableCellContentType.DESIGN_LIBRARY_LINK,
							fieldName: 'embedded.name',
							label: Liferay.Language.get('title'),
							localizeLabel: true,
						},
						{
							contentRenderer: TableCellContentType.AUTHOR,
							fieldName: 'embedded.creator.name',
							label: Liferay.Language.get('author'),
							localizeLabel: true,
							truncate: true,
						},
						{
							contentRenderer: TableCellContentType.RESOURCE_TYPE,
							fieldName: 'type',
							label: Liferay.Language.get('type'),
							localizeLabel: true,
							truncate: true,
						},
						{
							contentRenderer:
								TableCellContentType.FROM_NOW_DATE_TIME,
							fieldName: 'dateModified',
							label: Liferay.Language.get('modified'),
							localizeLabel: true,
							sortable: true,
						},
					],
				},
				thumbnail: 'table',
			},
		],
	};
}

function getSymbol(entryClassName?: string): string {
	if (entryClassName === FRAGMENT_COLLECTION_ENTRY_CLASS_NAME) {
		return 'squares';
	}

	return 'book';
}
