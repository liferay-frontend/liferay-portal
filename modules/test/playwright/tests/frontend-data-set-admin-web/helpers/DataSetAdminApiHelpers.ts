/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from '../../../helpers/ApiHelpers';
import {liferayConfig} from '../../../liferay.config';
import {
	ACTION_DATA_SET_RELATIONSHIP,
	CARDS_SECTION_DATA_SET_RELATIONSHIP,
	CREATION_ACTION_DATA_SET_RELATIONSHIP,
	DATE_FILTER_DATA_SET_RELATIONSHIP,
	DEFAULT_LABEL,
	ITEM_ACTION_DATA_SET_RELATIONSHIP,
	LIST_SECTION_DATA_SET_RELATIONSHIP,
	SELECTION_FILTER_DATA_SET_RELATIONSHIP,
	SORT_DATA_SET_RELATIONSHIP,
	TABLE_SECTION_DATA_SET_RELATIONSHIP,
} from '../utils/dataSetAdminConstants';
import {
	AsyncActionMethod,
	CreationActionTypes,
	ItemActionTypes,
	ModalVariantTypes,
} from '../utils/types';

const DEFAULT_DATA_SET_ERC = 'sampleDataSetERC';

export class DataSetAdminApiHelpers extends ApiHelpers {
	restContextPathPrefix = 'data-set-admin/data-sets/';
	baseUrlPath = this.baseUrl + this.restContextPathPrefix;

	async createDataSet({
		defaultItemsPerPage = 20,
		defaultVisualizationMode,
		description = 'Sample description',
		erc = 'sampleDataSetERC',
		label = DEFAULT_LABEL.DATA_SET,
		listOfItemsPerPage = '4, 8, 20, 40, 60',
		restApplication = `${this.baseUrlPath}table-sections`,
		restEndpoint = '/',
		restSchema = 'FDSField',
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
		const url = `${this.baseUrlPath}`;

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

		return this.post(url, {data});
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
		const url = `${this.baseUrlPath}cards-sections`;

		const data = {
			[CARDS_SECTION_DATA_SET_RELATIONSHIP.erc]: dataSetERC,
			fieldName,
			name,
		};

		return this.post(url, {data});
	}

	async createDataSetCreationAction({
		dataSet,
		icon,
		label_i18n = {en_US: 'Default Creation Action'},
		modalSize = 'full-screen',
		permissionKey,
		title_i18n,
		type = 'link',
		url = liferayConfig.environment.baseUrl,
	}: {
		dataSet: any;
		icon?: string;
		label_i18n?: {[key: string]: string};
		modalSize?: ModalVariantTypes;
		permissionKey?;
		title_i18n?: {[key: string]: string};
		type?: CreationActionTypes;
		url?: string;
	}) {
		return this.postDataSetBaseAction({
			[ACTION_DATA_SET_RELATIONSHIP.id]: dataSet.id,
			[CREATION_ACTION_DATA_SET_RELATIONSHIP.erc]:
				dataSet.externalReferenceCode,
			icon,
			label_i18n,
			modalSize,
			permissionKey,
			title_i18n,
			type,
			url,
		});
	}

	async createDataSetField({
		dataSet,
		extraBodyParams = {},
		label_i18n = {en_US: 'Title'},
		name = 'title',
		renderer = 'default',
		rendererType = 'internal',
		sortable = false,
		type = 'string',
	}: {
		dataSet: any;
		extraBodyParams?: any;
		label_i18n?: {[key: string]: string};
		name?: string;
		renderer?: string;
		rendererType?: string;
		sortable?: boolean;
		type?: string;
	}) {
		const url = `${this.baseUrlPath}table-sections`;

		const data = {
			[TABLE_SECTION_DATA_SET_RELATIONSHIP.erc]:
				dataSet.externalReferenceCode,
			label_i18n,
			name,
			renderer,
			rendererType,
			sortable,
			type,
			...extraBodyParams,
		};

		return this.post(url, {data});
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
		const url = `${this.baseUrlPath}date-filters`;

		const data = {
			[DATE_FILTER_DATA_SET_RELATIONSHIP.erc]: dataSetERC,
			fieldName,
			fromDate,
			label_i18n,
			toDate,
			type,
		};

		return this.post(url, {data});
	}

	async createDataSetSelectionFilter({
		dataSetERC = DEFAULT_DATA_SET_ERC,
		fieldName,
		include = true,
		label_i18n,
		multiple = false,
		preselectedValues = '[]',
		source,
		sourceType,
	}: {
		dataSetERC?: string;
		fieldName: string;
		include?: boolean;
		label_i18n?: {[key: string]: string};
		multiple?: boolean;
		preselectedValues?: string;
		source: string;
		sourceType: string;
	}) {
		const url = `${this.baseUrlPath}selection-filters`;

		const data = {
			[SELECTION_FILTER_DATA_SET_RELATIONSHIP.erc]: dataSetERC,
			fieldName,
			include,
			label_i18n,
			multiple,
			preselectedValues,
			source,
			sourceType,
		};

		return this.post(url, {data});
	}

	async createDataSetItemAction({
		confirmationMessage_i18n,
		confirmationMessageType,
		dataSet,
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
		dataSet: any;
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
		return this.postDataSetBaseAction({
			[ACTION_DATA_SET_RELATIONSHIP.id]: dataSet.id,
			[ITEM_ACTION_DATA_SET_RELATIONSHIP.erc]:
				dataSet.externalReferenceCode,
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
		});
	}

	async createDataSetSort({
		dataSetERC = DEFAULT_DATA_SET_ERC,
		defaultSort = false,
		fieldName = 'dateCreated',
		label_i18n = {en_US: 'Date Created'},
		orderType = 'asc',
	}: {
		dataSetERC?: string;
		defaultSort?: boolean;
		fieldName?: string;
		label_i18n?: {[key: string]: string};
		orderType?: string;
	}) {
		const url = `${this.baseUrlPath}sorts`;

		const data = {
			[SORT_DATA_SET_RELATIONSHIP.erc]: dataSetERC,
			defaultSort,
			fieldName,
			label: label_i18n[Object.keys(label_i18n)[0]],
			label_i18n,
			orderType,
		};

		return this.post(url, {data});
	}

	async createDataSetListSection({
		dataSetERC = DEFAULT_DATA_SET_ERC,
		fieldName = 'name',
		name = 'title',
	}: {
		dataSetERC?: string;
		fieldName?: string;
		name?: string;
		r_fdsViewFDSListSectionRelationship_c_fdsViewERC?: string;
	}) {
		const url = `${this.baseUrlPath}list-sections`;

		const data = {
			[LIST_SECTION_DATA_SET_RELATIONSHIP.erc]: dataSetERC,
			fieldName,
			name,
		};

		return this.post(url, {data});
	}

	async deleteDataSet({erc = DEFAULT_DATA_SET_ERC}: {erc?: string}) {
		const url = `${this.baseUrlPath}by-external-reference-code/${erc}`;

		return this.delete(url);
	}

	async postDataSetBaseAction(data: Object) {
		const endpointUrl = `${this.baseUrlPath}actions`;

		return this.post(endpointUrl, {data});
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
		const url = `${this.baseUrlPath}by-external-reference-code/${erc}`;

		const data = {
			defaultItemsPerPage,
			defaultVisualizationMode,
			label,
			listOfItemsPerPage,
		};

		return this.patch(url, data);
	}
}
