/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from '../../../helpers/ApiHelpers';
import {liferayConfig} from '../../../liferay.config';
import {
	DEFAULT_LABEL,
	EObjectRelationshipERC,
	ERESTApplication,
} from '../utils/constants';
import {
	AsyncActionMethod,
	CreationActionTypes,
	ItemActionTypes,
	ModalVariantTypes,
} from '../utils/types';

const DEFAULT_DATA_SET_ERC = 'sampleDataSetERC';
export class DataSetAdminApiHelpers extends ApiHelpers {
	async createDataSet({
		defaultItemsPerPage = 20,
		defaultVisualizationMode,
		description = 'Sample description',
		erc = 'sampleDataSetERC',
		label = DEFAULT_LABEL.DATA_SET,
		listOfItemsPerPage = '4, 8, 20, 40, 60',
		restApplication = ERESTApplication.TABLE_SECTIONS,
		restEndpoint = '/',
		restSchema = 'DataSetTableSection',
	}: {
		defaultItemsPerPage?: number;
		defaultVisualizationMode?: string;
		description?: string;
		erc?: string;
		label?: string;
		listOfItemsPerPage?: string;
		restApplication?: string;
		restEndpoint?: string;
		restSchema?: string;
	}) {
		const apiURL = `${this.baseUrl}${ERESTApplication.DATA_SETS}`;

		const data = {
			defaultItemsPerPage,
			defaultVisualizationMode,
			description,
			externalReferenceCode: erc,
			label,
			listOfItemsPerPage,
			restApplication,
			restEndpoint,
			restSchema,
		};

		return this.post(apiURL, {data});
	}

	async createDataSetCardsSection({
		dataSetERC = DEFAULT_DATA_SET_ERC,
		fieldName = 'name',
		name = 'title',
	}: {
		dataSetERC?: string;
		fieldName?: string;
		name?: string;
	}) {
		const apiURL = `${this.baseUrl}${ERESTApplication.CARDS_SECTIONS}`;

		const data = {
			[EObjectRelationshipERC.DATA_SET_CARDS_SECTIONS]: dataSetERC,
			fieldName,
			name,
		};

		return this.post(apiURL, {data});
	}

	async createDataSetCreationAction({
		dataSetERC = DEFAULT_DATA_SET_ERC,
		icon,
		label_i18n = {en_US: 'Default Creation Action'},
		modalSize = 'full-screen',
		permissionKey,
		title_i18n,
		type = 'link',
		url = liferayConfig.environment.baseUrl,
	}: {
		dataSetERC?: string;
		icon?: string;
		label_i18n?: {[key: string]: string};
		modalSize?: ModalVariantTypes;
		permissionKey?;
		title_i18n?: {[key: string]: string};
		type?: CreationActionTypes;
		url?: string;
	}) {
		const apiURL = `${this.baseUrl}${ERESTApplication.ACTIONS}`;

		const data = {
			[EObjectRelationshipERC.DATA_SET_CREATION_ACTIONS]: dataSetERC,
			icon,
			label_i18n,
			modalSize,
			permissionKey,
			title_i18n,
			type,
			url,
		};

		return this.post(apiURL, {data});
	}

	async createDataSetTableSection({
		dataSetERC = DEFAULT_DATA_SET_ERC,
		extraBodyParams = {},
		fieldName = 'title',
		label_i18n = {en_US: 'Title'},
		renderer = 'default',
		rendererType = 'internal',
		sortable = false,
		type = 'string',
	}: {
		dataSetERC?: string;
		extraBodyParams?: any;
		fieldName?: string;
		label_i18n?: {[key: string]: string};
		renderer?: string;
		rendererType?: string;
		sortable?: boolean;
		type?: string;
	}) {
		const apiURL = `${this.baseUrl}${ERESTApplication.TABLE_SECTIONS}`;

		const data = {
			[EObjectRelationshipERC.DATA_SET_TABLE_SECTIONS]: dataSetERC,
			fieldName,
			label_i18n,
			renderer,
			rendererType,
			sortable,
			type,
			...extraBodyParams,
		};

		return this.post(apiURL, {data});
	}

	async createDataSetDateFilter({
		dataSetERC = DEFAULT_DATA_SET_ERC,
		fieldName,
		fromDate = '',
		label_i18n = {en_US: 'Title'},
		toDate = '',
		type,
	}: {
		dataSetERC?: string;
		fieldName: string;
		fromDate?: string;
		label_i18n?: {[key: string]: string};
		toDate?: string;
		type: 'date' | 'date-time';
	}) {
		const apiURL = `${this.baseUrl}${ERESTApplication.DATE_FILTERS}`;

		const data = {
			[EObjectRelationshipERC.DATA_SET_DATE_FILTERS]: dataSetERC,
			fieldName,
			fromDate,
			label_i18n,
			toDate,
			type,
		};

		return this.post(apiURL, {data});
	}

	async createDataSetSelectionFilter({
		dataSetERC = DEFAULT_DATA_SET_ERC,
		fieldName,
		include = true,
		itemKey,
		itemLabel,
		label_i18n,
		multiple = false,
		preselectedValues = '[]',
		source,
		sourceType,
	}: {
		dataSetERC?: string;
		fieldName: string;
		include?: boolean;
		itemKey?: string;
		itemLabel?: string;
		label_i18n?: {[key: string]: string};
		multiple?: boolean;
		preselectedValues?: string;
		source: string;
		sourceType: string;
	}) {
		const apiURL = `${this.baseUrl}${ERESTApplication.SELECTION_FILTERS}`;

		const data = {
			[EObjectRelationshipERC.DATA_SET_SELECTION_FILTERS]: dataSetERC,
			fieldName,
			include,
			itemKey,
			itemLabel,
			label_i18n,
			multiple,
			preselectedValues,
			source,
			sourceType,
		};

		return this.post(apiURL, {data});
	}

	async createDataSetItemAction({
		confirmationMessage_i18n,
		confirmationMessageType,
		dataSetERC = DEFAULT_DATA_SET_ERC,
		errorMessage_i18n,
		icon,
		label_i18n = {en_US: 'Default Item Action'},
		method,
		modalSize = 'full-screen',
		permissionKey,
		successMessage_i18n,
		title_i18n,
		type = 'link',
		url = liferayConfig.environment.baseUrl,
	}: {
		confirmationMessageType?: string;
		confirmationMessage_i18n?: {[key: string]: string};
		dataSetERC?: string;
		errorMessage_i18n?: {[key: string]: string};
		icon?: string;
		label_i18n?: {[key: string]: string};
		method?: AsyncActionMethod;
		modalSize?: ModalVariantTypes;
		permissionKey?;
		successMessage_i18n?: {[key: string]: string};
		title_i18n?: {[key: string]: string};
		type?: ItemActionTypes;
		url?: string;
	}) {
		const apiURL = `${this.baseUrl}${ERESTApplication.ACTIONS}`;

		const data = {
			[EObjectRelationshipERC.DATA_SET_ITEM_ACTIONS]: dataSetERC,
			confirmationMessage_i18n,
			confirmationMessageType,
			errorMessage_i18n,
			icon,
			label_i18n,
			method,
			modalSize,
			permissionKey,
			successMessage_i18n,
			title_i18n,
			type,
			url,
		};

		return this.post(apiURL, {data});
	}

	async createDataSetSort({
		dataSetERC = DEFAULT_DATA_SET_ERC,
		defaultValue = false,
		fieldName = 'dateCreated',
		label_i18n = {en_US: 'Date Created'},
		orderType = 'asc',
	}: {
		dataSetERC?: string;
		defaultValue?: boolean;
		fieldName?: string;
		label_i18n?: {[key: string]: string};
		orderType?: string;
	}) {
		const apiURL = `${this.baseUrl}${ERESTApplication.SORTS}`;

		const data = {
			[EObjectRelationshipERC.DATA_SET_SORTS]: dataSetERC,
			default: defaultValue,
			fieldName,
			label: label_i18n[Object.keys(label_i18n)[0]],
			label_i18n,
			orderType,
		};

		return this.post(apiURL, {data});
	}

	async createDataSetListSection({
		dataSetERC = DEFAULT_DATA_SET_ERC,
		fieldName = 'name',
		name = 'title',
	}: {
		dataSetERC?: string;
		fieldName?: string;
		name?: string;
	}) {
		const apiURL = `${this.baseUrl}${ERESTApplication.LIST_SECTIONS}`;

		const data = {
			[EObjectRelationshipERC.DATA_SET_LIST_SECTIONS]: dataSetERC,
			fieldName,
			name,
		};

		return this.post(apiURL, {data});
	}

	async deleteDataSet({erc = DEFAULT_DATA_SET_ERC}: {erc?: string}) {
		const url = `${this.baseUrl}${ERESTApplication.DATA_SETS}/by-external-reference-code/${erc}`;

		return this.delete(url);
	}

	async updateDataSet({
		defaultItemsPerPage,
		defaultVisualizationMode,
		erc = DEFAULT_DATA_SET_ERC,
		label,
		listOfItemsPerPage,
	}: {
		defaultItemsPerPage?: number;
		defaultVisualizationMode?: string;
		erc?: string;
		label?: string;
		listOfItemsPerPage?: string;
	}) {
		const apiURL = `${this.baseUrl}${ERESTApplication.DATA_SETS}/by-external-reference-code/${erc}`;

		const data = {
			defaultItemsPerPage,
			defaultVisualizationMode,
			label,
			listOfItemsPerPage,
		};

		return this.patch(apiURL, data);
	}
}
