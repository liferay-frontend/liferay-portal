/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const API_URL = {
	FDS_DATE_FILTERS: '/o/data-set-manager/date-filters',
	FDS_DYNAMIC_FILTERS: '/o/data-set-manager/dynamic-filters',
	FDS_ENTRIES: '/o/data-set-manager/entries',
	FDS_FIELDS: '/o/data-set-manager/fields',
	FDS_SORTS: '/o/data-set-manager/sorts',
	FDS_VIEWS: '/o/data-set-manager/views',
};

const FUZZY_OPTIONS = {
	post: '</strong>',
	pre: '<strong>',
};

const OBJECT_RELATIONSHIP = {
	FDS_ENTRY_FDS_VIEW: 'fdsEntryFDSViewRelationship',
	FDS_ENTRY_FDS_VIEW_ID: 'r_fdsEntryFDSViewRelationship_c_fdsEntryId',
	FDS_VIEW_FDS_DATE_FILTER: 'fdsViewFDSDateFilterRelationship',
	FDS_VIEW_FDS_DATE_FILTER_ID:
		'r_fdsViewFDSDateFilterRelationship_c_fdsViewId',
	FDS_VIEW_FDS_DYNAMIC_FILTER: 'fdsViewFDSDynamicFilterRelationship',
	FDS_VIEW_FDS_DYNAMIC_FILTER_ID:
		'r_fdsViewFDSDynamicFilterRelationship_c_fdsViewId',
	FDS_VIEW_FDS_FIELD: 'fdsViewFDSFieldRelationship',
	FDS_VIEW_FDS_FIELD_ID: 'r_fdsViewFDSFieldRelationship_c_fdsViewId',
	FDS_VIEW_FDS_SORT: 'fdsViewFDSSortRelationship',
	FDS_VIEW_FDS_SORT_ID: 'r_fdsViewFDSSortRelationship_c_fdsViewId',
} as const;

const FDS_DEFAULT_PROPS = {
	pagination: {
		deltas: [{label: 4}, {label: 8}, {label: 20}, {label: 40}, {label: 60}],
		initialDelta: 8,
	},
	style: 'fluid' as const,
};

const ALLOWED_ENDPOINTS_PARAMETERS = ['scopeKey', 'siteId', 'userId'];

const WALKTHROUGH_CONFIGURATION = {
	id: 'dataSetEditDescriptionV1',
	steps: [
		{
			content: 'Click here to go to the view page.',
			id: 'step1ViewPage',
			nodeToHighlight:
				'#portlet_com_liferay_frontend_data_set_views_web_internal_portlet_FDSViewsPortlet > div > div > div > div > div > div.container-fluid.container-xl.mt-3 > div.data-set-content-wrapper > ul > li > div.justify-content-center.autofit-col.autofit-col-expand > div > a',
			onNext(element: HTMLElement) {
				element.click();
			},
			title: 'Visit The View Page',
		},
		{
			content:
				'Enter any description and click outside of the input to continue.',
			id: 'step2Description',
			nodeToHighlight:
				'#portlet_com_liferay_frontend_data_set_views_web_internal_portlet_FDSViewsPortlet > div > div > div > div:nth-child(2) > div:nth-child(2)',
			pause: true,
			title: 'Fill Out A Description',
		},
		{
			content: 'Click here to save your changes.',
			id: 'step3Save',
			nodeToHighlight:
				'#portlet_com_liferay_frontend_data_set_views_web_internal_portlet_FDSViewsPortlet > div > div > div > div.sheet-footer > div > div:nth-child(1) > button',
			onNext(element: HTMLElement) {
				element.click();
			},
			title: 'Save The View',
		},
	],
};

export {
	API_URL,
	FDS_DEFAULT_PROPS,
	FUZZY_OPTIONS,
	OBJECT_RELATIONSHIP,
	ALLOWED_ENDPOINTS_PARAMETERS,
	WALKTHROUGH_CONFIGURATION,
};
