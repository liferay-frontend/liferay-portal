/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {IInlineEditingSettings, IItemsActions, IModalConfig, ISchema} from '.';

export interface IFrontendDataSetContext {
	actionParameterName?: string | null;
	apiURL?: string;
	appURL?: string;
	applyItemInlineUpdates: (itemKey: number) => Promise<any> | void;
	createInlineItem: () => Promise<void> | void;
	customDataRenderers?: Array<any>;
	customRenderers?: {
		tableCell?: Array<TRenderer>;
		views?: Array<TRenderer>;
	};
	executeAsyncItemAction: ({
		errorMessage,
		method,
		requestBody,
		setActionItemLoading,
		successMessage,
		url,
	}: {
		errorMessage: string;
		method: string;
		requestBody?: string;
		setActionItemLoading?: Function;
		successMessage?: string;
		url: string;
	}) => Promise<void> | void;
	formId?: string;
	formName?: string;
	highlightItems: (value: any) => void;
	highlightedItemsValue?: Array<string>;
	id?: string;
	inlineAddingSettings?: {
		apiURL?: string;
		defaultBodyContent?: object;
	};
	inlineEditingSettings?: IInlineEditingSettings;
	itemsActions?: Array<IItemsActions>;
	itemsChanges?: any;
	loadData: () => Promise<any> | void;
	modalId?: string;
	namespace?: string;
	nestedItemsKey?: string;
	nestedItemsReferenceKey?: string;
	onActionDropdownItemClick: Function;
	onBulkActionItemClick: Function;
	onItemsChange: ({
		itemKey,
		items,
	}: {
		itemKey: string;
		items: Array<any>;
	}) => void;
	onSearch: ({query}: {query: string}) => void;
	onSelect?: ({selectedItems}: {selectedItems: Array<any>}) => void;
	openFolder: ({item}: {item: any}) => void;
	openModal: (config: IModalConfig) => void;
	openSidePanel: (config: IModalConfig) => void;
	portletId?: string;
	searchParam?: string;
	selectItems: (value: any) => void;
	selectable?: boolean;
	selectedItemsKey?: string;
	selectedItemsValue?: Array<any>;
	selectionType?: string;
	showBulkActionsManagementBar?: boolean;
	showBulkActionsManagementBarActions?: boolean;
	sidePanelId?: string;
	sorts?: Array<TRenderer>;
	style?: string;
	toggleItemInlineEdit: (itemKey: number) => void;
	uniformActionsDisplay?: boolean;
	updateDataSetItems: ({
		items,
		lastPage,
		page,
		pageSize,
		totalCount,
	}: IDataSetData) => void;
	updateItem: (
		itemKey: string,
		property: string,
		valuePath: string,
		value: any
	) => void;
}

export interface IDataSetData {
	items: Array<any>;
	lastPage: number;
	page: number;
	pageSize?: number;
	totalCount: number;
}

export interface IHTMLElementBuilder {
	(args: any): HTMLElement;
}

export interface IClientExtensionRenderer {
	externalReferenceCode?: string;
	htmlElementBuilder?: IHTMLElementBuilder;
	name?: string;
	type: 'clientExtension';
	url?: string;
}

export interface IInternalRenderer {
	component: React.ComponentType<any>;
	default?: boolean;
	label?: string;
	name?: string;
	schema?: ISchema;
	symbol?: string;
	type: 'internal';
	url?: string;
}

export type TRenderer = IClientExtensionRenderer | IInternalRenderer;

const FrontendDataSetContext = React.createContext<IFrontendDataSetContext>({
	applyItemInlineUpdates: () => {},
	createInlineItem: () => {},
	executeAsyncItemAction: () => {},
	highlightItems: () => {},
	loadData: () => {},
	onActionDropdownItemClick: () => {},
	onBulkActionItemClick: () => {},
	onItemsChange: () => {},
	onSearch: () => {},
	onSelect: () => {},
	openFolder: () => {},
	openModal: () => {},
	openSidePanel: () => {},
	selectItems: () => {},
	selectable: false,
	selectedItemsValue: [],
	toggleItemInlineEdit: () => {},
	updateDataSetItems: () => {},
	updateItem: () => {},
});

export default FrontendDataSetContext;
