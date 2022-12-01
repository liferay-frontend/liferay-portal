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

import ClayAutocomplete from '@clayui/autocomplete';
import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {ClayCheckbox, ClayRadio, ClayToggle} from '@clayui/form';
import ClayLabel from '@clayui/label';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import {fetch} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import {getValueFromItem, isValuesArrayChanged} from '../../../utils/index';
import {logError} from '../../../utils/logError';

const DEFAULT_PAGE_SIZE = 10;

function fetchData(apiURL, searchParam, currentPage = 1) {
	const url = new URL(apiURL, themeDisplay.getPortalURL());

	url.searchParams.append('page', currentPage);
	url.searchParams.append('pageSize', DEFAULT_PAGE_SIZE);

	if (searchParam) {
		url.searchParams.append('search', encodeURIComponent(searchParam));
	}

	return fetch(url.pathname + url.search, {
		headers: {
			'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
		},
	}).then((response) => response.json());
}

function Item({multiple, ...props}) {
	const Input = multiple ? ClayCheckbox : ClayRadio;

	return (
		<li>
			<Input {...props} />
		</li>
	);
}

Item.propTypes = {
	checked: PropTypes.bool.isRequired,
	label: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
	multiple: PropTypes.bool,
	onChange: PropTypes.func.isRequired,
	value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
};

const getSelectedItemsLabel = ({selectedData}) => {
	const {exclude, itemsValues} = selectedData;

	return (
		(exclude ? `(${Liferay.Language.get('exclude')}) ` : '') +
		itemsValues.map((itemsValue) => itemsValue.label).join(', ')
	);
};

const getOdataString = ({entityFieldType, id, selectedData}) => {
	const {exclude, itemsValues} = selectedData;

	if (!itemsValues?.length) {
		return null;
	}

	const quotedItemValues = itemsValues.map((itemValue) => 
		 typeof itemValue.value === 'string' ? `'${itemValue.value}'` : itemValue.value
	);

	if (entityFieldType === 'collection') {
		return `${id}/any(x:${quotedItemValues
			.map((value) => `(x ${exclude ? 'ne' : 'eq'} ${value})`)
			.join(exclude ? ' and ' : ' or ')})`;
	}
	else if (itemsValues.length === 1) {
		return `${id} ${exclude ? 'ne' : 'eq'} ${quotedItemValues[0]}`;
	}
	else {
		const expression = `${id} in (${quotedItemValues.join(', ')})`;

		if (exclude) {
			return 'not (' + expression + ')';
		}

		return expression;
	}
};

function SelectionFilter({
	apiURL,
	autocomplete,
	entityFieldType,
	id,
	inputPlaceholder,
	itemKey,
	itemLabel: itemLabelProp,
	items: initialItems,
	multiple,
	selectedData,
	setFilter,
}) {
	const [query, setQuery] = useState('');
	const [search, setSearch] = useState('');
	const [selectedItems, setSelectedItems] = useState(
		selectedData?.itemsValues || []
	);
	const [items, setItems] = useState(apiURL ? null : initialItems);
	const [loading, setLoading] = useState(false);
	const [currentPage, setCurrentPage] = useState(1);
	const [total, setTotal] = useState(apiURL ? 0 : initialItems?.length);
	const scrollingAreaRef = useRef(null);
	const [scrollingAreaRendered, setScrollingAreaRendered] = useState(false);
	const infiniteLoaderRef = useRef(null);
	const [infiniteLoaderRendered, setInfiniteLoaderRendered] = useState(false);
	const [exclude, setExclude] = useState(!!selectedData?.exclude);

	const loaderVisible = items?.length < total;

	useEffect(() => {
		setSelectedItems(selectedData?.itemsValues || []);
	}, [selectedData]);

	useEffect(() => {
		if (query === search) {
			return;
		}
		setCurrentPage(1);
		setSearch(query);
	}, [query, search]);

	const isMounted = useIsMounted();

	useEffect(() => {
		if (apiURL) {
			setLoading(true);
			fetchData(apiURL, search, currentPage)
				.then((data) => {
					if (!isMounted()) {
						return;
					}

					setLoading(false);
					if (currentPage === 1) {
						setItems(data.items);
					} else {
						setItems((items) => [...items, ...data.items]);
					}
					setTotal(data.totalCount);
				})
				.catch((error) => {
					logError(error);

					if (isMounted()) {
						setLoading(false);
					}
				});
		}
	}, [autocomplete, currentPage, isMounted, search, apiURL]);

	const setScrollingArea = useCallback((node) => {
		scrollingAreaRef.current = node;
		setScrollingAreaRendered(true);
	}, []);

	const setInfiniteLoader = useCallback((node) => {
		infiniteLoaderRef.current = node;
		setInfiniteLoaderRendered(true);
	}, []);

	const setObserver = useCallback(() => {
		if (
			!scrollingAreaRef.current ||
			!infiniteLoaderRef.current ||
			!IntersectionObserver
		) {
			return;
		}

		const options = {
			root: scrollingAreaRef.current,
			rootMargin: '0px',
			threshold: 1.0,
		};

		const observer = new IntersectionObserver((entries) => {
			if (entries[0].intersectionRatio <= 0) {
				return;
			}
			setCurrentPage((page) => page + 1);
		}, options);

		observer.observe(infiniteLoaderRef.current);
	}, []);

	useEffect(() => {
		if (scrollingAreaRendered && infiniteLoaderRendered && loaderVisible) {
			setObserver();
		}
	}, [
		scrollingAreaRendered,
		infiniteLoaderRendered,
		loaderVisible,
		setObserver,
	]);

	let actionType = 'edit';

	if (selectedData?.itemsValues && !selectedItems.length) {
		actionType = 'delete';
	}

	if (!selectedData) {
		actionType = 'add';
	}

	let submitDisabled = true;

	if (
		actionType === 'delete' ||
		(!selectedData && selectedItems.length) ||
		(selectedData &&
			isValuesArrayChanged(selectedData.itemsValues, selectedItems)) ||
		(selectedData &&
			selectedItems.length &&
			selectedData.exclude !== exclude)
	) {
		submitDisabled = false;
	}

	return (
		<>
			{autocomplete && (
				<>
					<ClayDropDown.Caption>
						<ClayAutocomplete>
							<ClayAutocomplete.Input
								onChange={(event) =>
									setQuery(event.target.value)
								}
								placeholder={inputPlaceholder}
							/>

							{loading && <ClayAutocomplete.LoadingIndicator />}
						</ClayAutocomplete>

						{selectedItems.length ? (
							<div className="mt-2 selected-elements-wrapper">
								{selectedItems.map((selectedItem) => (
									<ClayLabel
										closeButtonProps={{
											onClick: () =>
												setSelectedItems((items) =>
													items.filter(
														(item) =>
															item.value !==
															selectedItem.value
													)
												),
										}}
										key={selectedItem.value}
									>
										{selectedItem.label}
									</ClayLabel>
								))}
							</div>
						) : null}
					</ClayDropDown.Caption>
					<ClayDropDown.Divider />
				</>
			)}
			<ClayDropDown.Caption className="py-0">
				<div className="row">
					<div className="col">
						<label htmlFor={`autocomplete-exclude-${id}`}>
							{Liferay.Language.get('exclude')}
						</label>
					</div>

					<div className="col-auto">
						<ClayToggle
							id={`autocomplete-exclude-${id}`}
							onToggle={() => setExclude(!exclude)}
							toggled={exclude}
						/>
					</div>
				</div>
			</ClayDropDown.Caption>
			<ClayDropDown.Divider />
			<ClayDropDown.Caption>
				<div className="form-group">
					{items && !!items.length ? (
						<ul
							className="inline-scroller mb-n2 mx-n2 px-2"
							ref={setScrollingArea}
						>
							{items.map((item) => {
								const itemValue = itemKey
									? item[itemKey]
									: item.value;
								const itemLabel = itemLabelProp
									? getValueFromItem(item, itemLabelProp)
									: item.label;
								const newValue = {
									label: itemLabel,
									value: itemValue,
								};

								return (
									<Item
										aria-label={itemLabel}
										checked={Boolean(
											selectedItems.find(
												(element) =>
													element.value === itemValue
											)
										)}
										key={itemValue}
										label={itemLabel}
										multiple={multiple}
										onChange={() => {
											setSelectedItems(
												selectedItems.find(
													(element) =>
														element.value ===
														itemValue
												)
													? selectedItems.filter(
															(element) =>
																element.value !==
																itemValue
													  )
													: multiple
													? [
															...selectedItems,
															newValue,
													  ]
													: [newValue]
											);
										}}
										value={itemValue}
									/>
								);
							})}

							{loaderVisible && (
								<ClayLoadingIndicator
									ref={setInfiniteLoader}
									small
								/>
							)}
						</ul>
					) : (
						<div className="mt-2 p-2 text-muted">
							{Liferay.Language.get('no-items-were-found')}
						</div>
					)}
				</div>
			</ClayDropDown.Caption>
			<ClayDropDown.Divider />
			<ClayDropDown.Caption>
				<ClayButton
					disabled={submitDisabled}
					onClick={() => {
						if (actionType === 'delete') {
							setFilter({active: false, id});
						} else {
							const newSelectedData = {
								exclude,
								itemsValues: selectedItems,
							};

							setFilter({
								active: true,
								id,
								odataFilterString: getOdataString({
									entityFieldType,
									id,
									multiple,
									selectedData: newSelectedData,
								}),
								selectedData: newSelectedData,
								selectedItemsLabel: getSelectedItemsLabel({
									selectedData: newSelectedData,
								}),
							});
						}
					}}
					small
				>
					{actionType === 'add' && Liferay.Language.get('add-filter')}

					{actionType === 'edit' &&
						Liferay.Language.get('edit-filter')}

					{actionType === 'delete' &&
						Liferay.Language.get('delete-filter')}
				</ClayButton>
			</ClayDropDown.Caption>
		</>
	);
}

SelectionFilter.propTypes = {
	apiURL: PropTypes.string,
	autocomplete: PropTypes.bool,
	entityFieldType: PropTypes.string,
	id: PropTypes.string.isRequired,
	inputPlaceholder: PropTypes.string,
	itemKey: PropTypes.string.isRequired,
	itemLabel: PropTypes.oneOfType([PropTypes.string, PropTypes.array])
		.isRequired,
	items: PropTypes.arrayOf(
		PropTypes.shape({
			label: PropTypes.string,
			value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
		})
	),
	multiple: PropTypes.bool,
	selectedData: PropTypes.shape({
		exclude: PropTypes.bool,
		itemsValues: PropTypes.arrayOf(
			PropTypes.shape({
				label: PropTypes.oneOfType([
					PropTypes.string,
					PropTypes.number,
				]),
				value: PropTypes.oneOfType([
					PropTypes.string,
					PropTypes.number,
				]),
			})
		),
	}),
	setFilter: PropTypes.func.isRequired,
};

export {getSelectedItemsLabel, getOdataString};
export default SelectionFilter;
