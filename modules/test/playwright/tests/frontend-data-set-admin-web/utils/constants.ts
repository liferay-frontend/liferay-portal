/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const DEFAULT_LABEL = {
	DATA_SET: 'Sample Data Set',
};

const ROOT_REST_APPLICATION = '/data-set-admin/data-sets';

enum ERESTApplication {
	ACTIONS = `${ROOT_REST_APPLICATION}/actions`,
	CARDS_SECTIONS = `${ROOT_REST_APPLICATION}/cards-sections`,
	CLIENT_EXTENSION_FILTERS = `${ROOT_REST_APPLICATION}/client-extension-filters`,
	DATA_SETS = ROOT_REST_APPLICATION,
	DATE_FILTERS = `${ROOT_REST_APPLICATION}/date-filters`,
	FDS_ENTRIES = `${ROOT_REST_APPLICATION}/entries`,
	LIST_SECTIONS = `${ROOT_REST_APPLICATION}/list-sections`,
	SELECTION_FILTERS = `${ROOT_REST_APPLICATION}/selection-filters`,
	SORTS = `${ROOT_REST_APPLICATION}/sorts`,
	TABLE_SECTIONS = `${ROOT_REST_APPLICATION}/table-sections`,
}

const REL_PREFIX = 'dataSetToDataSet';

enum EObjectRelationship {
	DATA_SET_TABLE_SECTIONS = `${REL_PREFIX}TableSections`,
}

const REL_ERC_PREFIX = `r_${REL_PREFIX}`;
const REL_ERC_SUFFIX = '_c_dataSetERC';

enum EObjectRelationshipERC {
	DATA_SET_CARDS_SECTIONS = `${REL_ERC_PREFIX}CardsSections${REL_ERC_SUFFIX}`,
	DATA_SET_CREATION_ACTIONS = `${REL_ERC_PREFIX}CreationActions${REL_ERC_SUFFIX}`,
	DATA_SET_ITEM_ACTIONS = `${REL_ERC_PREFIX}ItemActions${REL_ERC_SUFFIX}`,
	DATA_SET_LIST_SECTIONS = `${REL_ERC_PREFIX}ListSections${REL_ERC_SUFFIX}`,
	DATA_SET_TABLE_SECTIONS = `${REL_ERC_PREFIX}TableSections${REL_ERC_SUFFIX}`,
	DATA_SET_DATE_FILTERS = `${REL_ERC_PREFIX}DateFilters${REL_ERC_SUFFIX}`,
	DATA_SET_SELECTION_FILTERS = `${REL_ERC_PREFIX}SelectionFilters${REL_ERC_SUFFIX}`,
	DATA_SET_SORTS = `${REL_ERC_PREFIX}Sorts${REL_ERC_SUFFIX}`,
}

export {
	DEFAULT_LABEL,
	EObjectRelationship,
	EObjectRelationshipERC,
	ERESTApplication,
};
