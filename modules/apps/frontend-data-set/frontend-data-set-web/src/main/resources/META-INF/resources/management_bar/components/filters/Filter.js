/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import React, {useContext, useEffect, useState} from 'react';

import ClientExtensionRenderer from '../../../components/ClientExtensionRenderer';
import {getComponentByModuleURL} from '../../../utils/modules';
import ViewsContext from '../../../views/ViewsContext';
import {VIEWS_ACTION_TYPES} from '../../../views/viewsReducer';
import DateRangeFilter, {
	getOdataString as getDateRangeFilterOdataString,
	getSelectedItemsLabel as getDateRangeFilterSelectedItemsLabel,
} from './DateRangeFilter';
import SelectionFilter, {
	getOdataString as getSelectionFilterOdataString,
	getSelectedItemsLabel as getSelectionFilterSelectedItemsLabel,
} from './SelectionFilter';

const FILTER_TYPE_COMPONENT = {
	dateRange: DateRangeFilter,
	selection: SelectionFilter,
};

const getFilterSelectedItemsLabel = (filter) => {
	switch (filter.type) {
		case 'dateRange':
			return getDateRangeFilterSelectedItemsLabel(filter);
		case 'selection':
			return getSelectionFilterSelectedItemsLabel(filter);
		default:
			return '';
	}
};

const getOdataFilterString = (filter) => {
	switch (filter.type) {
		case 'dateRange':
			return getDateRangeFilterOdataString(filter);
		case 'selection':
			return getSelectionFilterOdataString(filter);
		default:
			return '';
	}
};

/**
 * @typedef {Object} Props
 * @prop {import("@liferay/js-api/data-set").FDSFilterArgs} args
 * @prop {import("@liferay/js-api/data-set").FDSFilter} renderer
 */

/**
 * @param {Props} props
 */
const ClientExtensionRendererWrapper = (props) => {

	// This wrapper exists so that we can keep TS consistent

	return <ClientExtensionRenderer {...props} />;
};

const Filter = ({moduleURL, type, ...otherProps}) => {
	const [{filters}, viewsDispatch] = useContext(ViewsContext);

	const [Component, setComponent] = useState(() => {
		if (!moduleURL) {
			const Matched = FILTER_TYPE_COMPONENT[type];

			if (!Matched) {
				throw new Error(`Filter type '${type}' not found.`);
			}

			return Matched;
		}
		else {
			return null;
		}
	});

	useEffect(() => {
		if (moduleURL) {
			if (type === 'client-extension') {
				const getModule = async () => {
					const cetModule = await import(
						/* webpackIgnore: true */ moduleURL
					);

					setComponent(() => cetModule['default']);
				};

				getModule();
			}
			else {
				getComponentByModuleURL(moduleURL).then((FetchedComponent) =>
					setComponent(() => FetchedComponent)
				);
			}
		}
	}, [moduleURL, type]);

	const setFilter = ({id, ...otherProps}) => {
		viewsDispatch({
			type: VIEWS_ACTION_TYPES.UPDATE_FILTERS,
			value: filters.map((filter) => ({
				...filter,
				...(filter.id === id ? {...otherProps} : {}),
			})),
		});
	};

	return Component ? (
		<div className="data-set-filter">
			{type === 'client-extension' ? (
				<ClientExtensionRendererWrapper
					args={{
						filter: otherProps,
						setFilter: (filter) =>
							setFilter({
								active: true,
								id: otherProps.id,
								...filter,
							}),
					}}
					renderer={Component}
				/>
			) : (
				<Component setFilter={setFilter} {...otherProps} />
			)}
		</div>
	) : (
		<ClayLoadingIndicator size="sm" />
	);
};

export {getFilterSelectedItemsLabel, getOdataFilterString};
export default Filter;
