/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClaySelect} from '@clayui/form';
import classnames from 'classnames';
import React, {useState} from 'react';

import Asterisk from './Asterisk';
import {getInitialOption} from './utils';

const ProductOptionSelect = ({
	id,
	label,
	name,
	onChange,
	productOptionValues,
	required,
}) => {
	const initialOption = getInitialOption(productOptionValues);

	const [selectedOption, setSelectedOption] = useState(initialOption);

	const [errors, setErrors] = useState({});

	const handleChange = (value) => {
		const updatedOption = productOptionValues.find(
			(option) => option.value === value
		);

		setSelectedOption(updatedOption);
		onChange(updatedOption);
	};

	const handleBlur = ({target: {selectedIndex}}) => {
		if (required && selectedIndex === 0) {
			setErrors({selectedPlaceholder: true});
		}
		else {
			setErrors({});
		}
	};

	return (
		<ClayForm.Group
			className={classnames({'has-error': errors.selectedPlaceholder})}
		>
			<label htmlFor={id}>
				{label}

				<Asterisk required={required} />
			</label>

			<ClaySelect
				id={id}
				name={name}
				onBlur={handleBlur}
				onChange={handleChange}
			>
				<ClaySelect.Option
					disabled={required}
					label={Liferay.Language.get('choose-an-option')}
					selected={!initialOption}
				/>

				{productOptionValues.map(({key, label, name, value}) => (
					<ClaySelect.Option
						key={key}
						label={label}
						name={name}
						selected={selectedOption?.value === value}
						value={value}
					/>
				))}
			</ClaySelect>

			{errors.selectedPlaceholder && (
				<ClayForm.FeedbackItem>
					<ClayForm.FeedbackIndicator symbol="exclamation-full" />

					{Liferay.Language.get('this-field-is-required')}
				</ClayForm.FeedbackItem>
			)}
		</ClayForm.Group>
	);
};

export default ProductOptionSelect;
