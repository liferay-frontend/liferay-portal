/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {fetch, navigate, openModal} from 'frontend-js-web';
import React from 'react';

import '../css/DataSets.scss';
import {DEFAULT_FETCH_HEADERS, FDS_DEFAULT_PROPS} from './utils/constants';
import openDefaultFailureToast from './utils/openDefaultFailureToast';
import openDefaultSuccessToast from './utils/openDefaultSuccessToast';

export interface IDataSet {
	actions: {
		delete: {
			href: string;
			method: string;
		};
		update: {
			href: string;
			method: string;
		};
	};
	additionalAPIURLParameters?: string;
	creationActionsOrder?: string;
	defaultItemsPerPage: number;
	defaultVisualizationMode?: string;
	description?: string;
	externalReferenceCode: string;
	filtersOrder?: string;
	id: string;
	itemActionsOrder?: string;
	label: string;
	listOfItemsPerPage: string;
	restApplication: string;
	restEndpoint: string;
	restSchema: string;
	sortsOrder?: string;
	tableSectionsOrder?: string;
}

const SystemDataSets = ({
	editDataSetURL,
	namespace,
	permissionsURL,
}: {
	editDataSetURL: string;
	namespace: string;
	permissionsURL: string;
}) => {
	const getEditURL = (itemData: IDataSet) => {
		const url = new URL(editDataSetURL);

		url.searchParams.set(
			`${namespace}dataSetERC`,
			itemData.externalReferenceCode
		);
		url.searchParams.set(`${namespace}dataSetLabel`, itemData.label);

		return url;
	};

	const onEditClick = ({itemData}: {itemData: IDataSet}) => {
		navigate(getEditURL(itemData));
	};

	const onDeleteClick = ({
		itemData,
		loadData,
	}: {
		itemData: IDataSet;
		loadData: Function;
	}) => {
		openModal({
			bodyHTML: Liferay.Language.get(
				'deleting-a-data-set-is-an-action-that-cannot-be-reversed'
			),
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
					onClick: ({processClose}: {processClose: Function}) => {
						processClose();

						fetch(itemData.actions.delete.href, {
							headers: DEFAULT_FETCH_HEADERS,
							method: itemData.actions.delete.method,
						})
							.then(() => {
								openDefaultSuccessToast();

								loadData();
							})
							.catch(openDefaultFailureToast);
					},
				},
			],
			status: 'danger',
			title: Liferay.Language.get('delete-data-set'),
		});
	};

	const views = [
		{
			contentRenderer: 'table',
			name: 'table',
			schema: {
				fields: [
					{
						actionId: 'edit',
						contentRenderer: 'actionLink',
						fieldName: 'label',
						label: Liferay.Language.get('name'),
						sortable: true,
					},
					{
						fieldName: 'restApplication',
						label: Liferay.Language.get('rest-application'),
						sortable: true,
					},
					{
						fieldName: 'restSchema',
						label: Liferay.Language.get('rest-schema'),
						sortable: true,
					},
					{
						fieldName: 'restEndpoint',
						label: Liferay.Language.get('rest-endpoint'),
						sortable: true,
					},
					{
						contentRenderer: 'dateTime',
						fieldName: 'dateModified',
						label: Liferay.Language.get('modified-date'),
						sortable: true,
					},
				],
			},
		},
	];

	return (
		<div className="data-sets">
			<FrontendDataSet
				{...FDS_DEFAULT_PROPS}
				emptyState={{
					description: Liferay.Language.get(
						'start-creating-one-to-show-your-data'
					),
					image: '/states/empty_state.svg',
					title: Liferay.Language.get('no-system-data-sets-created'),
				}}
				id={`${namespace}DataSets`}
				itemsActions={[
					{
						data: {
							id: 'edit',
							permissionKey: 'get',
						},
						icon: 'pencil',
						label: Liferay.Language.get('edit'),
						onClick: onEditClick,
					},
					{
						separator: true,
						type: 'group',
					},
					{
						data: {
							permissionKey: 'permissions',
							size: 'full-screen',
							title: Liferay.Language.get('permissions'),
						},
						href: permissionsURL,
						icon: 'password-policies',
						label: Liferay.Language.get('permissions'),
						target: 'modal',
					},
					{
						separator: true,
						type: 'group',
					},
					{
						data: {
							permissionKey: 'delete',
						},
						icon: 'trash',
						label: Liferay.Language.get('delete'),
						onClick: onDeleteClick,
					},
				]}
				sorts={[{direction: 'desc', key: 'dateCreated'}]}
				views={views}
			/>
		</div>
	);
};

export default SystemDataSets;
