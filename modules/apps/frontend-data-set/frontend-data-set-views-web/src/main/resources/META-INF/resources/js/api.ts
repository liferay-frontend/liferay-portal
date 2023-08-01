/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {Renderer} from '@liferay/frontend-data-set-web/src/main/resources/META-INF/resources/utils/renderer';
import {FDSCellRenderer} from '@liferay/js-api/data-set';
import {fetch, openToast} from 'frontend-js-web';

import {OBJECT_RELATIONSHIP} from './Constants';
import {FDSViewType} from './FDSViews';

const LOCALIZABLE_PROPERTY_SUFFIX = '_i18n';

const NOT_ALLOWED_KEYS_AS_FIELD_NAME = [
	'actions',
	'scopeKey',
	'x-class-name',
	'x-schema-name',
];

interface IField {
	format: string;
	label: string;
	name: string;
	type: string;
}

function getValidFields(
	properties: any,
	isObjectSchema: boolean
): Array<IField> {
	const fields: Array<IField> = [];

	Object.keys(properties).map((propertyKey) => {
		const propertyValue = properties[propertyKey];

		if (isObjectSchema && !propertyValue.extensions) {
			return;
		}

		if (NOT_ALLOWED_KEYS_AS_FIELD_NAME.includes(propertyKey)) {
			return;
		}

		if (propertyKey.includes(LOCALIZABLE_PROPERTY_SUFFIX)) {
			return;
		}

		const type = propertyValue.type;

		if (type === 'array') {
			return;
		}

		if (propertyValue.$ref) {
			return;
		}

		fields.push({
			format: properties[propertyKey].format || type,
			label: propertyKey,
			name: propertyKey,
			type,
		});
	});

	return fields;
}

export async function getFields(fdsView: FDSViewType) {
	const {restApplication, restSchema} = fdsView[
		OBJECT_RELATIONSHIP.FDS_ENTRY_FDS_VIEW
	];

	const response = await fetch(`/o${restApplication}/openapi.json`);

	if (!response.ok) {
		openToast({
			message: Liferay.Language.get('your-request-failed-to-complete'),
			type: 'danger',
		});

		return [];
	}

	const responseJSON = await response.json();

	const properties =
		responseJSON?.components?.schemas[restSchema]?.properties;

	if (!properties) {
		openToast({
			message: Liferay.Language.get('your-request-failed-to-complete'),
			type: 'danger',
		});

		return [];
	}

	const isObjectSchema =
		responseJSON.components.schemas[restSchema].xml.name === 'ObjectEntry';

	const fieldsArray: Array<IField> = getValidFields(
		properties,
		isObjectSchema
	);

	return fieldsArray;
}

export interface IPickList {
	externalReferenceCode: string;
	id: string;
	listTypeEntries: IListTypeEntry[];
	name: string;
	name_i18n: {
		[key: string]: string;
	};
}

interface IListTypeEntry {
	externalReferenceCode: string;
	id: number;
	key: string;
	name: string;
	name_i18n: {
		[key: string]: string;
	};
}

export async function getAllPicklists(
	page: number = 1,
	items: IPickList[] = []
) {
	const response = await fetch(
		`/o/headless-admin-list-type/v1.0/list-type-definitions?pageSize=100&page=${page}`
	);

	if (!response.ok) {
		openToast({
			message: Liferay.Language.get('your-request-failed-to-complete'),
			type: 'danger',
		});

		return [];
	}

	const responseJSON = await response.json();

	items = [...items, ...responseJSON.items];

	if (responseJSON.lastPage > page) {
		items = await getAllPicklists(page + 1, items);
	}

	return items;
}

export interface IClientExtensionRenderer extends Renderer {
	erc?: string;
	label?: string;
	name?: string;
	type: 'clientExtension';
}

export interface IClientExtensionCellRenderer extends IClientExtensionRenderer {
	renderer: FDSCellRenderer;
}
