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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal from '@clayui/modal';
import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {ManagementToolbar} from 'frontend-js-components-web';
import {fetch, openModal, openToast} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {API_URL, OBJECT_RELATIONSHIP, PAGINATION_PROPS} from '../Constants';
import {FDSViewSectionInterface} from '../FDSView';
import {FDSViewType} from '../FDSViews';

interface AddFDSFieldsModalContentInterface {
	closeModal: Function;
	fdsView: FDSViewType;
	loadData: Function;
}

type FieldType = {
	name: string;
	selected: boolean;
	type: string;
	visible: boolean;
};

const AddFDSFieldsModalContent = ({
	closeModal,
	fdsView,
	loadData,
}: AddFDSFieldsModalContentInterface) => {
	const [fields, setFields] = useState<Array<FieldType> | null>(null);
	const [query, setQuery] = useState('');

	const onSearch = (query: string) => {
		setQuery(query);

		if (!fields) {
			return;
		}

		const regexp = new RegExp(query, 'i');

		setFields(
			fields.map((field) => ({
				...field,
				visible: Boolean(field.name.match(regexp)),
			}))
		);
	};

	useEffect(() => {
		const getFields = async () => {
			const {restApplication, restSchema} = fdsView[
				OBJECT_RELATIONSHIP.FDS_ENTRY_FDS_VIEW
			];

			const response = await fetch(`/o${restApplication}/openapi.json`);

			if (!response.ok) {
				openToast({
					message: Liferay.Language.get(
						'your-request-failed-to-complete'
					),
					type: 'danger',
				});

				return null;
			}

			const responseJSON = await response.json();

			const properties =
				responseJSON?.components?.schemas[restSchema]?.properties;

			if (!properties) {
				openToast({
					message: Liferay.Language.get(
						'your-request-failed-to-complete'
					),
					type: 'danger',
				});

				return null;
			}

			const fieldsArray: Array<FieldType> = [];

			const isObjectSchema =
				responseJSON.components.schemas[restSchema].xml.name ===
				'ObjectEntry';

			Object.keys(properties).forEach((propertyKey) => {
				const propertyValue = properties[propertyKey];

				if (isObjectSchema && !propertyValue.extensions) {
					return;
				}

				if (propertyKey === 'x-class-name') {
					return;
				}

				const type = propertyValue.type;

				if (type === 'object' || type === 'array') {
					return;
				}

				fieldsArray.push({
					name: propertyKey,
					selected: false,
					type,
					visible: true,
				});
			});

			setFields(fieldsArray);
		};

		getFields();
	}, [fdsView]);

	const isSelectAllChecked = () => {
		if (!fields) {
			return false;
		}

		const selectedFieldsCount =
			fields.filter((field) => field.selected)?.length || 0;

		return selectedFieldsCount === fields.length;
	};

	const isSelectAllIndeterminate = () => {
		if (!fields) {
			return false;
		}

		const selectedFieldsCount =
			fields.filter((field) => field.selected)?.length || 0;

		return selectedFieldsCount > 0 && selectedFieldsCount < fields.length;
	};

	const visibleFields = fields?.filter((field) => field.visible) ?? [];

	return (
		<div className="fds-view-fields-modal">
			<ClayModal.Header>
				{Liferay.Language.get('add-fields')}
			</ClayModal.Header>

			<ClayModal.Body>
				{fields === null ? (
					<ClayLoadingIndicator />
				) : (
					<>
						<ManagementToolbar.Container>
							<ManagementToolbar.ItemList expand>
								<ManagementToolbar.Item className="pr-2">
									<ClayCheckbox
										checked={isSelectAllChecked()}
										indeterminate={isSelectAllIndeterminate()}
										onChange={({target: {checked}}) =>
											setFields(
												fields.map((field) => ({
													...field,
													selected: checked,
												}))
											)
										}
									/>
								</ManagementToolbar.Item>

								<ManagementToolbar.Item className="nav-item-expand">
									<ClayInput.Group>
										<ClayInput.GroupItem>
											<ClayInput
												insetAfter
												onChange={(event) =>
													onSearch(event.target.value)
												}
												placeholder={Liferay.Language.get(
													'search'
												)}
												type="text"
												value={query}
											/>

											<ClayInput.GroupInsetItem
												after
												tag="span"
											>
												<ClayButtonWithIcon
													aria-label={Liferay.Language.get(
														'search'
													)}
													displayType="unstyled"
													symbol="search"
												/>
											</ClayInput.GroupInsetItem>
										</ClayInput.GroupItem>
									</ClayInput.Group>
								</ManagementToolbar.Item>
							</ManagementToolbar.ItemList>
						</ManagementToolbar.Container>

						<div className="fields pb-2 pt-2">
							{visibleFields.length > 0 ? (
								visibleFields.map(({name, selected}) => (
									<div
										className="pb-2 pl-3 pr-3 pt-2"
										key={name}
									>
										<ClayCheckbox
											checked={selected}
											label={name}
											onChange={({target: {checked}}) => {
												setFields(
													fields.map((field) =>
														field.name === name
															? {
																	...field,
																	selected: checked,
															  }
															: field
													)
												);
											}}
										/>
									</div>
								))
							) : (
								<div className="pb-2 pl-3 pr-3 pt-2 text-3">
									{Liferay.Language.get('no-results-found')}
								</div>
							)}
						</div>
					</>
				)}
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							onClick={() => {
								closeModal();

								loadData();
							}}
						>
							{Liferay.Language.get('save')}
						</ClayButton>

						<ClayButton
							displayType="secondary"
							onClick={() => closeModal()}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</div>
	);
};

const Fields = ({fdsView, namespace}: FDSViewSectionInterface) => {
	const creationMenu = {
		primaryItems: [
			{
				label: Liferay.Language.get('new-dataset'),
				onClick: ({loadData}: {loadData: Function}) =>
					openModal({
						contentComponent: ({
							closeModal,
						}: {
							closeModal: Function;
						}) => (
							<AddFDSFieldsModalContent
								closeModal={closeModal}
								fdsView={fdsView}
								loadData={loadData}
							/>
						),
					}),
			},
		],
	};

	const views = [
		{
			contentRenderer: 'table',
			name: 'table',
			schema: {
				fields: [
					{fieldName: 'name', label: Liferay.Language.get('name')},
					{
						fieldName: 'label',
						label: Liferay.Language.get('column-label'),
					},
					{
						fieldName: 'type',
						label: Liferay.Language.get('type'),
					},
					{
						fieldName: 'renderer',
						label: Liferay.Language.get('renderer'),
					},
					{
						fieldName: 'sortable',
						label: Liferay.Language.get('sortable'),
					},
				],
			},
		},
	];

	return (
		<FrontendDataSet
			apiURL={`${API_URL.FDS_FIELDS}?nestedFields=${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_FIELD}`}
			creationMenu={creationMenu}
			id={`${namespace}FDSFields`}
			style="fluid"
			views={views}
			{...PAGINATION_PROPS}
		/>
	);
};

export default Fields;
