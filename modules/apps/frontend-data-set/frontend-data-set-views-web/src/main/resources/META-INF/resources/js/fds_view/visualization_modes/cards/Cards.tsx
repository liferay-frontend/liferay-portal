/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayTable from '@clayui/table';
import classNames from 'classnames';
import {fetch, openModal} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import '../../../../css/CardsVisualizationMode.scss';
import {IFDSViewSectionProps} from '../../../FDSView';
import FieldSelectModalContent from '../../../components/FieldSelectModalContent';
import {API_URL, OBJECT_RELATIONSHIP} from '../../../utils/constants';
import openDefaultFailureToast from '../../../utils/openDefaultFailureToast';
import openDefaultSuccessToast from '../../../utils/openDefaultSuccessToast';
import {IField} from '../../../utils/types';
import {IBaseVisualizationMode} from '../VisualizationModes';

export interface ICards extends IBaseVisualizationMode<'cards'> {}
interface IFDSCardsSection {
	externalReferenceCode: string;
	fieldName: string;
	name: string;
	rendererName?: string;
}
interface ICardsSection {
	externalReferenceCode?: IFDSCardsSection['externalReferenceCode'];
	field?: IField;
	label: string;
	name: IFDSCardsSection['name'];
}

export default function Cards(props: IFDSViewSectionProps) {
	const {fdsView} = props;

	const [cardsSections, setCardsSections] = useState<Array<ICardsSection>>([
		{label: Liferay.Language.get('title'), name: 'title'},
		{label: Liferay.Language.get('description'), name: 'description'},
		{label: Liferay.Language.get('image'), name: 'image'},
		{label: Liferay.Language.get('symbol'), name: 'symbol'},
		{label: Liferay.Language.get('link'), name: 'link'},
		{label: Liferay.Language.get('sticker'), name: 'sticker'},
	]);
	const [saveButtonDisabled, setSaveButtonDisabled] = useState(false);

	const getFDSCardsSections = async () => {
		const response = await fetch(
			`${API_URL.FDS_CARDS_SECTIONS}?filter=(${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_CARDS_SECTION_ERC} eq '${fdsView.externalReferenceCode}')`
		);

		if (!response.ok) {
			openDefaultFailureToast();

			return null;
		}

		const responseJSON = await response.json();

		const fdsCardsSections = responseJSON?.items;

		if (!fdsCardsSections) {
			openDefaultFailureToast();

			return null;
		}

		setCardsSections(
			cardsSections.map((cardsSection) => {
				const fdsCardsSection = fdsCardsSections.find(
					(fdsCardsSection: IFDSCardsSection) =>
						fdsCardsSection.name === cardsSection.name
				);

				if (!fdsCardsSection) {
					return {label: cardsSection.label, name: cardsSection.name};
				}

				return {
					...cardsSection,
					externalReferenceCode:
						fdsCardsSection.externalReferenceCode,
					field: {
						name: fdsCardsSection.fieldName,
					},
				};
			})
		);
	};

	const clearFDSCardsSectionAssignment = async (
		cardsSection: ICardsSection
	) => {
		const method = 'DELETE';
		const url = `${API_URL.FDS_CARDS_SECTIONS}/by-external-reference-code/${cardsSection.externalReferenceCode}`;

		const response = await fetch(url, {
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json',
			},
			method,
		});

		if (!response.ok) {
			openDefaultFailureToast();

			return;
		}

		getFDSCardsSections();

		openDefaultSuccessToast();
	};

	const saveFDSCardsSection = async ({
		cardsSection,
		closeModal,
		field,
	}: {
		cardsSection: ICardsSection;
		closeModal: Function;
		field: IField;
	}) => {
		setSaveButtonDisabled(true);

		let method = 'POST';
		let url = API_URL.FDS_CARDS_SECTIONS;

		if (cardsSection.externalReferenceCode) {
			method = 'PATCH';
			url = `${API_URL.FDS_CARDS_SECTIONS}/by-external-reference-code/${cardsSection.externalReferenceCode}`;
		}

		const response = await fetch(url, {
			body: JSON.stringify({
				[OBJECT_RELATIONSHIP.FDS_VIEW_FDS_CARDS_SECTION_ERC]:
					fdsView.externalReferenceCode,
				fieldName: field.name,
				name: cardsSection.name,
			}),
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json',
			},
			method,
		});

		setSaveButtonDisabled(false);

		if (!response.ok) {
			openDefaultFailureToast();

			return;
		}

		closeModal();

		getFDSCardsSections();

		openDefaultSuccessToast();
	};

	useEffect(() => {
		getFDSCardsSections();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<ClayLayout.ContentCol className="c-gap-4 cards-visualization-mode">
			<ClayAlert
				displayType="info"
				title={`${Liferay.Language.get('info')}:`}
				variant="stripe"
			>
				{Liferay.Language.get(
					'this-visualization-mode-will-not-be-shown-until-you-assign-at-least-one-field-to-a-card-element'
				)}
			</ClayAlert>

			<ClayTable className="mb-0">
				<ClayTable.Head>
					<ClayTable.Row>
						<ClayTable.Cell
							className="cards-section-label"
							headingCell
						>
							{Liferay.Language.get('card-element')}
						</ClayTable.Cell>

						<ClayTable.Cell className="field-name" headingCell>
							{Liferay.Language.get('field')}
						</ClayTable.Cell>
					</ClayTable.Row>
				</ClayTable.Head>

				<ClayTable.Body>
					{cardsSections.map((cardsSection) => (
						<CardsSection
							cardsSection={cardsSection}
							key={cardsSection.name}
							modalProps={props}
							onClear={() =>
								clearFDSCardsSectionAssignment(cardsSection)
							}
							onSelect={({closeModal, selectedField}) => {
								saveFDSCardsSection({
									cardsSection,
									closeModal,
									field: selectedField,
								});
							}}
							saveButtonDisabled={saveButtonDisabled}
						/>
					))}
				</ClayTable.Body>
			</ClayTable>
		</ClayLayout.ContentCol>
	);
}

interface ICardsSectionProps {
	cardsSection: ICardsSection;
	modalProps: IFDSViewSectionProps;
	onClear: (cardsSection: ICardsSection) => void;
	onSelect: ({
		closeModal,
		selectedField,
	}: {
		closeModal: Function;
		selectedField: IField;
	}) => void;
	saveButtonDisabled: boolean;
}

function CardsSection({
	cardsSection,
	modalProps,
	onClear,
	onSelect,
	saveButtonDisabled,
}: ICardsSectionProps) {
	const {field, label} = cardsSection;

	const onClickAssign = () => {
		openModal({
			contentComponent: ({closeModal}: {closeModal: Function}) => (
				<FieldSelectModalContent
					{...modalProps}
					closeModal={closeModal}
					onSaveButtonClick={({
						selectedFields,
					}: {
						selectedFields: Array<IField>;
					}) => {
						onSelect({
							closeModal,
							selectedField: selectedFields[0],
						});
					}}
					saveButtonDisabled={saveButtonDisabled}
					selectedFields={field ? [field] : []}
				/>
			),
		});
	};

	const onClickClear = () => {
		onClear(cardsSection);
	};

	return (
		<ClayTable.Row>
			<ClayTable.Cell className="cards-section-label">
				<strong>{label}</strong>
			</ClayTable.Cell>

			<ClayTable.Cell className="field-name">
				<ClayInput.Group small>
					<ClayInput.GroupItem>
						<p
							className={classNames(
								'align-items-center d-flex mb-0',
								{'text-secondary': !field}
							)}
						>
							{field?.name ||
								Liferay.Language.get('not-assigned')}
						</p>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem shrink>
						{field?.name ? (
							<ClayDropDown
								trigger={
									<ClayButtonWithIcon
										aria-label={Liferay.Language.get(
											'change-assignment'
										)}
										displayType="secondary"
										size="sm"
										symbol="ellipsis-v"
										title={Liferay.Language.get(
											'change-assignment'
										)}
									/>
								}
							>
								<ClayDropDown.ItemList>
									<ClayDropDown.Item onClick={onClickAssign}>
										<span className="pr-2">
											<ClayIcon symbol="change" />
										</span>

										{Liferay.Language.get(
											'change-assignment'
										)}
									</ClayDropDown.Item>

									<ClayDropDown.Item onClick={onClickClear}>
										<span className="pr-2">
											<ClayIcon symbol="times-circle" />
										</span>

										{Liferay.Language.get(
											'clear-assignment'
										)}
									</ClayDropDown.Item>
								</ClayDropDown.ItemList>
							</ClayDropDown>
						) : (
							<ClayButtonWithIcon
								aria-label={Liferay.Language.get(
									'assign-field'
								)}
								displayType="secondary"
								onClick={onClickAssign}
								symbol="plus"
								title={Liferay.Language.get('assign-field')}
							/>
						)}
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayTable.Cell>
		</ClayTable.Row>
	);
}
