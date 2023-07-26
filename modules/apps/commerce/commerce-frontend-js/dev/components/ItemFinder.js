/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

import launcher from '../../src/main/resources/META-INF/resources/components/item_finder/entry';
import slugify from '../../src/main/resources/META-INF/resources/utilities/slugify';

import '../../src/main/resources/META-INF/resources/styles/main.scss';
import {showErrorNotification} from '../../src/main/resources/META-INF/resources/utilities/notifications';

const headers = new Headers({
	'Accept': 'application/json',
	'Authorization': 'Basic ' + btoa('test@liferay.com' + ':' + 'test'),
	'Content-Type': 'application/json',
});

const id = 40077;
const productId = 40078;

function selectItem(specification) {
	return fetch(
		'/o/headless-commerce-admin-catalog/v1.0/products/' +
			id +
			'/productSpecifications/',
		{
			body: JSON.stringify({
				productId,
				specificationId: specification.id,
				specificationKey: specification.key,
				value: {
					[Liferay.ThemeDisplay.getLanguageId()]: name,
				},
			}),
			headers,
			method: 'POST',
		}
	).then(() => specification.id);
}

function addNewItem(name) {
	return fetch('/o/headless-commerce-admin-catalog/v1.0/specifications', {
		body: JSON.stringify({
			key: slugify(name),
			title: {
				[Liferay.ThemeDisplay.getLanguageId()]: name,
			},
		}),
		headers,
		method: 'POST',
	})
		.then((response) => {
			return response.json();
		})
		.then(selectItem);
}

function getSelectedItems() {
	return fetch(
		'/o/headless-commerce-admin-catalog/v1.0/products/' +
			productId +
			'/productSpecifications/',
		{
			credentials: 'include',
			headers,
		}
	)
		.then((response) => response.json())
		.then((jsonResponse) => {
			if (!jsonResponse.items && jsonResponse.title) {
				return showErrorNotification(jsonResponse.title);
			}

			return jsonResponse.items.map(
				(specification) => specification.specificationId
			);
		});
}

launcher('itemFinder', 'item-finder-root-id', {
	apiUrl: '/o/headless-commerce-admin-catalog/v1.0/specifications',
	createNewItemLabel: 'Create new specification',
	getSelectedItems,
	itemCreation: false,
	itemsKey: 'id',
	linkedDataSetsId: ['test'],
	onItemCreated: addNewItem,
	onItemSelected: selectItem,
	pageSize: 5,
	panelHeaderLabel: 'Add new specification',
	schema: [
		{
			fieldName: ['title', 'LANG'],
		},
		{
			fieldName: 'id',
		},
		{
			fieldName: 'key',
		},
	],
	titleLabel: 'Select an existing specification',
});
