/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface APIURLPaths {
	applications: string;
	endpoints: string;
	filters: string;
	properties: string;
	schemas: string;
	sorts: string;
}

interface HTTPMethod {
	href: string;
	method: string;
}

interface Actions {
	delete: HTTPMethod;
	get: HTTPMethod;
	permissions: HTTPMethod;
	update: HTTPMethod;
}

type voidReturn = () => void;

interface FDSItem<T> {
	action: {id: string};
	id: number;
	itemData: T;
	loadData: voidReturn;
	value: string;
}

interface BaseItem {
	actions: Actions;
	createDate: string;
	creator: string;
	dateCreated: string;
	dateModified: string;
	description: string;
	externalReferenceCode: string;
	id: number;
	keywords: string[];
	modifiedDate: string;
	scopeKey: string;
	status: string;
}

interface ApplicationStatus {
	key: 'published' | 'unpublished';
	name?: 'Published' | 'Unpublished';
}
interface APIApplicationItem extends BaseItem {
	applicationStatus: ApplicationStatus;
	baseURL: string;
	title: string;
	version: string;
}

interface APIApplicationEndpointItem extends BaseItem {
	name: string;
	path: string;
}

interface APIApplicationSchemaItem extends BaseItem {
	mainObjectDefinitionERC: string;
	name: string;
}
