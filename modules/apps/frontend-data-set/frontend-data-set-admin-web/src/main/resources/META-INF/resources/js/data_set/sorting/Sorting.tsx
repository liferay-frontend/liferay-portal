/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {ClayCheckbox, ClaySelectWithOption} from '@clayui/form';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal from '@clayui/modal';
import {InputLocalized} from 'frontend-js-components-web';
import {fetch, openModal} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {IDataSet} from '../../DataSets';
import OrderableTable from '../../components/OrderableTable';
import RequiredMark from '../../components/RequiredMark';
import {
	API_URL,
	EObjectRelationship,
	EObjectRelationshipID,
} from '../../utils/constants';
import openDefaultFailureToast from '../../utils/openDefaultFailureToast';
import openDefaultSuccessToast from '../../utils/openDefaultSuccessToast';
import sortItems from '../../utils/sortItems';
import {IField, IOrderable} from '../../utils/types';
import {IDataSetSectionProps} from '../DataSet';

const SORTS_ORDER_OBJECT_FIELD_NAME = 'sortsOrder';

interface IContentRendererProps {
	item: ISort;
	query: string;
}

interface ISort extends IOrderable {
	[EObjectRelationship.DATA_SET_SORTS]?: any;
	defaultSort: boolean;
	externalReferenceCode: string;
	fieldName: string;
	label: string;
	label_i18n: Liferay.Language.LocalizedValue<string>;
	orderType: string;
}

const ORDER_TYPE = {
	ASCENDING: {
		label: Liferay.Language.get('ascending'),
		value: 'asc',
	},
	DESCENDING: {
		label: Liferay.Language.get('descending'),
		value: 'desc',
	},
};

const ORDER_TYPE_OPTIONS = [ORDER_TYPE.ASCENDING, ORDER_TYPE.DESCENDING];

const DefaultComponent = ({item}: IContentRendererProps) => {
	return (
		<ClayLabel displayType={item.defaultSort ? 'success' : 'secondary'}>
			{item.defaultSort
				? Liferay.Language.get('yes')
				: Liferay.Language.get('no')}
		</ClayLabel>
	);
};

const AddSortModalContent = ({
	closeModal,
	dataSet,
	fields,
	namespace,
	onSave,
}: {
	closeModal: Function;
	dataSet: IDataSet;
	fields: IField[];
	namespace: string;
	onSave: (newSort: ISort) => void;
}) => {
	const [labelI18n, setLabelI18n] = useState<
		Liferay.Language.LocalizedValue<string>
	>({});
	const [saveButtonDisabled, setSaveButtonDisabled] = useState(false);
	const [selectedFieldName, setSelectedFieldName] = useState<string>('');
	const [selectedOrderType, setSelectedOrderType] = useState<string>(
		ORDER_TYPE.ASCENDING.value
	);
	const [useAsDefaultSorting, setUseAsDefaultSorting] = useState(false);

	const handleSave = async () => {
		setSaveButtonDisabled(true);

		const field = fields.find(
			(item: IField) => item.name === selectedFieldName
		);

		if (!field) {
			openDefaultFailureToast();

			return;
		}

		const response = await fetch(API_URL.SORTS, {
			body: JSON.stringify({
				[EObjectRelationshipID.DATA_SET_SORTS]: dataSet.id,
				defaultSort: useAsDefaultSorting,
				fieldName: selectedFieldName,
				label_i18n: labelI18n,
				orderType: selectedOrderType,
			}),
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json',
			},
			method: 'POST',
		});

		if (!response.ok) {
			setSaveButtonDisabled(false);

			openDefaultFailureToast();

			return;
		}

		const responseJSON = await response.json();

		openDefaultSuccessToast();

		onSave(responseJSON);

		closeModal();
	};

	const sortLabelInput = `${namespace}sortLabelInput`;
	const sortFieldNameInputId = `${namespace}sortFieldNameInput`;
	const sortOrderTypeInputId = `${namespace}sortOrderTypeInput`;

	return (
		<>
			<ClayModal.Header>
				{Liferay.Language.get('new-sorting-option')}
			</ClayModal.Header>

			<ClayModal.Body>
				<p className="text-secondary">
					{Liferay.Language.get(
						'create-a-sorting-option-for-the-dataset-fragment'
					)}
				</p>

				<InputLocalized
					id={sortLabelInput}
					label={Liferay.Language.get('label')}
					name="label"
					onChange={setLabelI18n}
					placeholder={Liferay.Language.get('add-a-label')}
					required
					translations={labelI18n}
				/>

				<ClayForm.Group>
					<label htmlFor={sortFieldNameInputId}>
						{Liferay.Language.get('sort-by')}

						<RequiredMark />
					</label>

					<ClaySelectWithOption
						aria-label={Liferay.Language.get('sort-by')}
						name={sortFieldNameInputId}
						onChange={(event) => {
							setSelectedFieldName(event.target.value);
						}}
						options={[
							{
								disabled: true,
								label: Liferay.Language.get('choose-an-option'),
								value: '',
							},
							...fields.map((item) => ({
								label: item.label,
								value: item.name,
							})),
						]}
						title={Liferay.Language.get('sort-by')}
						value={selectedFieldName}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<ClayCheckbox
						aria-label={Liferay.Language.get(
							'use-as-default-sorting'
						)}
						checked={useAsDefaultSorting}
						inline
						label={Liferay.Language.get('use-as-default-sorting')}
						onChange={() =>
							setUseAsDefaultSorting((value: boolean) => !value)
						}
					/>
				</ClayForm.Group>

				{useAsDefaultSorting && (
					<ClayForm.Group>
						<label htmlFor={sortOrderTypeInputId}>
							{Liferay.Language.get('order-type')}

							<RequiredMark />
						</label>

						<ClaySelectWithOption
							aria-label={Liferay.Language.get('order-type')}
							id={sortOrderTypeInputId}
							onChange={(event) =>
								setSelectedOrderType(event.target.value)
							}
							options={ORDER_TYPE_OPTIONS}
						/>
					</ClayForm.Group>
				)}
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							disabled={
								saveButtonDisabled ||
								!selectedFieldName ||
								!labelI18n[
									Liferay.ThemeDisplay.getDefaultLanguageId()
								]
							}
							onClick={handleSave}
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
		</>
	);
};

const EditSortModalContent = ({
	closeModal,
	fields,
	namespace,
	onSave,
	sort,
}: {
	closeModal: Function;
	fields: IField[];
	namespace: string;
	onSave: Function;
	sort: ISort;
}) => {
	const [labelI18n, setLabelI18n] = useState<
		Liferay.Language.LocalizedValue<string>
	>(sort.label_i18n);
	const [saveButtonDisabled, setSaveButtonDisabled] = useState(false);
	const [selectedFieldName, setSelectedFieldName] = useState<string>(
		sort.fieldName
	);
	const [selectedOrderType, setSelectedOrderType] = useState(sort.orderType);
	const [useAsDefaultSorting, setUseAsDefaultSorting] = useState(
		sort.defaultSort
	);

	const handleSave = async () => {
		setSaveButtonDisabled(true);

		const response = await fetch(
			`${API_URL.SORTS}/by-external-reference-code/${sort.externalReferenceCode}`,
			{
				body: JSON.stringify({
					defaultSort: useAsDefaultSorting,
					fieldName: selectedFieldName,
					label_i18n: labelI18n,
					orderType: selectedOrderType,
				}),
				headers: {
					'Accept': 'application/json',
					'Content-Type': 'application/json',
				},
				method: 'PATCH',
			}
		);

		if (!response.ok) {
			setSaveButtonDisabled(false);

			openDefaultFailureToast();

			return;
		}

		const editedSort = await response.json();

		closeModal();

		openDefaultSuccessToast();

		onSave({editedSort});
	};

	const sortLabelInput = `${namespace}sortLabelInput`;
	const sortFieldNameInputId = `${namespace}sortFieldNameInput`;
	const sortOrderTypeInputId = `${namespace}sortOrderTypeInput`;

	return (
		<>
			<ClayModal.Header>
				{Liferay.Util.sub(
					Liferay.Language.get('edit-x-sorting'),
					sort.label
				)}
			</ClayModal.Header>

			<ClayModal.Body>
				<ClayForm.Group>
					<p className="text-secondary">
						{Liferay.Language.get(
							'create-a-sorting-option-for-the-dataset-fragment.-add-a-label-name-and-choose-a-field-to-be-displayed-in-the-sorting-dropdown'
						)}
					</p>

					<InputLocalized
						id={sortLabelInput}
						label={Liferay.Language.get('label')}
						name="label"
						onChange={setLabelI18n}
						placeholder={Liferay.Language.get('add-a-label')}
						required
						translations={labelI18n}
					/>

					<label htmlFor={sortFieldNameInputId}>
						{Liferay.Language.get('sort-by')}

						<RequiredMark />
					</label>

					<ClaySelectWithOption
						aria-label={Liferay.Language.get('sort-by')}
						name={sortFieldNameInputId}
						onChange={(event) => {
							setSelectedFieldName(event.target.value);
						}}
						options={[
							{
								disabled: true,
								label: Liferay.Language.get('choose-an-option'),
								value: '',
							},
							...fields.map((item) => ({
								label: item.label,
								value: item.name,
							})),
						]}
						title={Liferay.Language.get('sort-by')}
						value={selectedFieldName}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<ClayCheckbox
						aria-label={Liferay.Language.get(
							'use-as-default-sorting'
						)}
						checked={useAsDefaultSorting}
						inline
						label={Liferay.Language.get('use-as-default-sorting')}
						onChange={() =>
							setUseAsDefaultSorting((value: boolean) => !value)
						}
					/>
				</ClayForm.Group>

				{useAsDefaultSorting && (
					<ClayForm.Group>
						<label htmlFor={sortOrderTypeInputId}>
							{Liferay.Language.get('order-type')}

							<RequiredMark />
						</label>

						<ClaySelectWithOption
							aria-label={Liferay.Language.get('order-type')}
							id={sortOrderTypeInputId}
							onChange={(event) =>
								setSelectedOrderType(event.target.value)
							}
							options={ORDER_TYPE_OPTIONS}
							value={selectedOrderType}
						/>
					</ClayForm.Group>
				)}
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							disabled={
								saveButtonDisabled ||
								!selectedFieldName ||
								!labelI18n[
									Liferay.ThemeDisplay.getDefaultLanguageId()
								]
							}
							onClick={handleSave}
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
		</>
	);
};

const Sorting = ({
	dataSet,
	fieldTreeItems,
	namespace,
}: IDataSetSectionProps) => {
	const fields = fieldTreeItems.filter((field) => field.sortable);
	const [sorts, setSorts] = useState<Array<ISort>>([]);
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		const getSort = async () => {
			const response = await fetch(
				`${API_URL.SORTS}?filter=(${EObjectRelationshipID.DATA_SET_SORTS} eq '${dataSet.id}')&nestedFields=${EObjectRelationship.DATA_SET_SORTS}&sort=dateCreated:asc`
			);

			const responseJSON = await response.json();

			const storedSorts: ISort[] = responseJSON.items;

			setSorts(
				sortItems(
					storedSorts,
					storedSorts?.[0]?.[EObjectRelationship.DATA_SET_SORTS]?.[
						SORTS_ORDER_OBJECT_FIELD_NAME
					] as string
				) as ISort[]
			);

			setLoading(false);
		};

		getSort();
	}, [dataSet]);

	const handleCreation = () =>
		openModal({
			contentComponent: ({closeModal}: {closeModal: Function}) => (
				<AddSortModalContent
					closeModal={closeModal}
					dataSet={dataSet}
					fields={fields}
					namespace={namespace}
					onSave={(newSort) => setSorts([...sorts, newSort])}
				/>
			),
		});

	const handleDelete = ({item}: {item: ISort}) => {
		openModal({
			bodyHTML: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-this-sorting?-fragments-using-it-will-be-affected'
			),
			buttons: [
				{
					autoFocus: true,
					displayType: 'secondary',
					label: Liferay.Language.get('cancel'),
					type: 'cancel',
				},
				{
					displayType: 'danger',
					label: Liferay.Language.get('delete'),
					onClick: async ({
						processClose,
					}: {
						processClose: Function;
					}) => {
						processClose();

						const url = `${API_URL.SORTS}/${item.id}`;

						const response = await fetch(url, {
							method: 'DELETE',
						});

						if (!response.ok) {
							openDefaultFailureToast();

							return;
						}

						openDefaultSuccessToast();

						setSorts(
							sorts?.filter(
								(sort: ISort) => sort.id !== item.id
							) || []
						);
					},
				},
			],
			status: 'warning',
			title: Liferay.Language.get('delete-filter'),
		});
	};

	const handleEdit = ({item}: {item: ISort}) => {
		openModal({
			contentComponent: ({closeModal}: {closeModal: Function}) => (
				<EditSortModalContent
					closeModal={closeModal}
					fields={fields}
					namespace={namespace}
					onSave={({editedSort}: {editedSort: ISort}) => {
						setSorts(
							sorts?.map((sort) => {
								if (sort.id === editedSort.id) {
									return editedSort;
								}

								return sort;
							}) || []
						);
					}}
					sort={item}
				/>
			),
		});
	};

	const updateSortsOrder = async ({sortsOrder}: {sortsOrder: string}) => {
		const response = await fetch(
			`${API_URL.DATA_SETS}/by-external-reference-code/${dataSet.externalReferenceCode}`,
			{
				body: JSON.stringify({
					[SORTS_ORDER_OBJECT_FIELD_NAME]: sortsOrder,
				}),
				headers: {
					'Accept': 'application/json',
					'Content-Type': 'application/json',
				},
				method: 'PATCH',
			}
		);

		if (!response.ok) {
			openDefaultFailureToast();

			return;
		}

		const responseJSON = await response.json();

		const storedSortsOrder = responseJSON?.[SORTS_ORDER_OBJECT_FIELD_NAME];

		if (sorts && storedSortsOrder && storedSortsOrder === sortsOrder) {
			setSorts(sortItems(sorts, storedSortsOrder) as ISort[]);

			openDefaultSuccessToast();
		}
		else {
			openDefaultFailureToast();
		}
	};

	return (
		<ClayLayout.ContainerFluid>
			{loading ? (
				<ClayLoadingIndicator />
			) : (
				<>
					<ClayAlert className="c-mt-5" displayType="info">
						{Liferay.Language.get(
							'the-hierarchy-of-the-sorting-options-will-be-defined-by-the-vertical-order-of-the-fields'
						)}
					</ClayAlert>

					<OrderableTable
						actions={[
							{
								icon: 'pencil',
								label: Liferay.Language.get('edit'),
								onClick: handleEdit,
							},
							{
								icon: 'trash',
								label: Liferay.Language.get('delete'),
								onClick: handleDelete,
							},
						]}
						creationMenuItems={[
							{
								label: Liferay.Language.get('new-sort'),
								onClick: handleCreation,
							},
						]}
						fields={[
							{
								headingTitle: true,
								label: Liferay.Language.get('label'),
								name: 'label',
							},
							{
								label: Liferay.Language.get('sort-by'),
								name: 'fieldName',
							},
							{
								contentRenderer: {
									component: DefaultComponent,
								},
								label: Liferay.Language.get('default'),
								name: 'defaultSort',
							},
						]}
						items={sorts}
						noItemsButtonLabel={Liferay.Language.get(
							'new-sorting-option'
						)}
						noItemsDescription={Liferay.Language.get(
							'create-a-sorting-option-to-order-the-data-in-the-fragment'
						)}
						noItemsTitle={Liferay.Language.get(
							'no-sorting-created-yet'
						)}
						onOrderChange={({order}: {order: string}) => {
							updateSortsOrder({sortsOrder: order});
						}}
						title={Liferay.Language.get('sorting')}
					/>
				</>
			)}
		</ClayLayout.ContainerFluid>
	);
};

export default Sorting;
