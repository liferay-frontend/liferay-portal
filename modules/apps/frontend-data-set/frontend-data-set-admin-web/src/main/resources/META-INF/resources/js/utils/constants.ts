/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IBaseVisualizationMode} from './types';

const API_URL = {
	ACTIONS: '/o/data-set-admin/data-sets/actions',
	CARDS_SECTIONS: '/o/data-set-admin/data-sets/cards-sections',
	CLIENT_EXTENSION_FILTERS:
		'/o/data-set-admin/data-sets/client-extension-filters',
	DATA_SETS: '/o/data-set-admin/data-sets',
	DATE_FILTERS: '/o/data-set-admin/data-sets/date-filters',
	FDS_ENTRIES: '/o/data-set-admin/data-sets/entries',
	LIST_SECTIONS: '/o/data-set-admin/data-sets/list-sections',
	SELECTION_FILTERS: '/o/data-set-admin/data-sets/selection-filters',
	SORTS: '/o/data-set-admin/data-sets/sorts',
	TABLE_SECTIONS: '/o/data-set-admin/data-sets/table-sections',
};

const FUZZY_OPTIONS = {
	post: '</strong>',
	pre: '<strong>',
};

const REL_PREFIX = 'dataSetToDataSet';

enum EObjectRelationship {
	DATA_SET_CARDS_SECTIONS = `${REL_PREFIX}CardsSections`,
	DATA_SET_CLIENT_EXTENSION_FILTERS = `${REL_PREFIX}ClientExtensionFilters`,
	DATA_SET_CREATION_ACTIONS = `${REL_PREFIX}CreationActions`,
	DATA_SET_DATE_FILTERS = `${REL_PREFIX}DateFilters`,
	DATA_SET_ITEM_ACTIONS = `${REL_PREFIX}ItemActions`,
	DATA_SET_LIST_SECTIONS = `${REL_PREFIX}ListSections`,
	DATA_SET_SORTS = `${REL_PREFIX}Sorts`,
	DATA_SET_SELECTION_FILTERS = `${REL_PREFIX}SelectionFilters`,
	DATA_SET_TABLE_SECTIONS = `${REL_PREFIX}TableSections`,
}

const REL_ERC_PREFIX = `r_${REL_PREFIX}`;
const REL_ERC_SUFFIX = '_c_dataSetERC';

enum EObjectRelationshipERC {
	DATA_SET_CARDS_SECTIONS = `${REL_ERC_PREFIX}CardsSections${REL_ERC_SUFFIX}`,
	DATA_SET_LIST_SECTIONS = `${REL_ERC_PREFIX}ListSections${REL_ERC_SUFFIX}`,
}

const REL_ID_PREFIX = `r_${REL_PREFIX}`;
const REL_ID_SUFFIX = '_c_dataSetId';

enum EObjectRelationshipID {
	DATA_SET_CLIENT_EXTENSION_FILTERS = `${REL_ID_PREFIX}ExtensionFilters${REL_ID_SUFFIX}`,
	DATA_SET_CREATION_ACTIONS = `${REL_ID_PREFIX}CreationActions${REL_ID_SUFFIX}`,
	DATA_SET_DATE_FILTERS = `${REL_ID_PREFIX}DateFilters${REL_ID_SUFFIX}`,
	DATA_SET_ITEM_ACTIONS = `${REL_ID_PREFIX}ItemActions${REL_ID_SUFFIX}`,
	DATA_SET_SORTS = `${REL_ID_PREFIX}Sorts${REL_ID_SUFFIX}`,
	DATA_SET_SELECTION_FILTERS = `${REL_ID_PREFIX}SelectionFilters${REL_ID_SUFFIX}`,
	DATA_SET_TABLE_SECTIONS = `${REL_ID_PREFIX}TableSections${REL_ID_SUFFIX}`,
}

const FDS_DEFAULT_PROPS = {
	pagination: {
		deltas: [{label: 4}, {label: 8}, {label: 20}, {label: 40}, {label: 60}],
		initialDelta: 8,
	},
	style: 'fluid' as const,
};

const DEFAULT_VISUALIZATION_MODES: Array<IBaseVisualizationMode<any>> = [
	{
		label: Liferay.Language.get('cards'),
		mode: 'cards',
		thumbnail: 'cards2',
		visualizationModeId: 'defaultCards',
	},
	{
		label: Liferay.Language.get('list'),
		mode: 'list',
		thumbnail: 'list',
		visualizationModeId: 'defaultList',
	},
	{
		label: Liferay.Language.get('table'),
		mode: 'table',
		thumbnail: 'table',
		visualizationModeId: 'defaultTable',
	},
];

const ALLOWED_ENDPOINTS_PARAMETERS = ['scopeKey', 'siteId', 'userId'];

export {
	API_URL,
	DEFAULT_VISUALIZATION_MODES,
	EObjectRelationship,
	EObjectRelationshipERC,
	EObjectRelationshipID,
	FDS_DEFAULT_PROPS,
	FUZZY_OPTIONS,
	ALLOWED_ENDPOINTS_PARAMETERS,
};
