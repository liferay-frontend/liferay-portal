/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {getLocalizableLabel} from '../../utils/string';
import {SingleSelect} from '../Select/SingleSelect';

type SelectedPicklistOption = {
	label: string;
	value: string;
};

interface PickListItem {
	externalReferenceCode: string;
	id: number;
	key: string;
	name: string;
	name_i18n: LocalizedValue<string>;
}
interface PicklistEntryBaseFieldProps {
	creationLanguageId?: Liferay.Language.Locale;
	error?: string;
	label: string;
	onChange: (selected: PickListItem | undefined) => void;
	picklistItems: PickListItem[];
	placeholder?: string;
	required?: boolean;
	selectedPicklistItemKey?: string;
}

export function PicklistEntryBaseField({
	creationLanguageId,
	error,
	label,
	onChange,
	picklistItems,
	placeholder,
	required,
	selectedPicklistItemKey,
}: PicklistEntryBaseFieldProps) {
	const handleChange = (selectedPicklistOption: SelectedPicklistOption) => {
		onChange(
			picklistItems.find(
				(item) => item.key === selectedPicklistOption.value
			)
		);
	};

	return (
		<>
			{picklistItems.length ? (
				<SingleSelect
					error={error}
					label={label}
					onChange={handleChange}
					options={picklistItems.map((item) => ({
						label: creationLanguageId
							? getLocalizableLabel(
									creationLanguageId,
									item.name_i18n
							  )
							: item.name,
						value: item.key,
					}))}
					placeholder={placeholder}
					required={required}
					value={
						picklistItems.find(
							(item) => item.key === selectedPicklistItemKey
						)?.name ?? ''
					}
				/>
			) : null}
		</>
	);
}
