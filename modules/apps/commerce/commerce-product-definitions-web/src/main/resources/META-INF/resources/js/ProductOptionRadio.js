/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayRadio, ClayRadioGroup} from '@clayui/form';
import React, {useState} from 'react';

import Asterisk from './Asterisk';
import {getInitialOption} from './utils';

const ProductOptionRadio = ({
	id,
	label,
	name,
	onChange,
	productOptionValues,
	required,
}) => {
	const initialOption = getInitialOption(productOptionValues);

	const [selectedOption, setSelectedOption] = useState(initialOption);

	const handleChange = (value) => {
		const updatedOption = productOptionValues.find(
			(option) => option.value === value
		);

		setSelectedOption(updatedOption);
		onChange(updatedOption);
	};

	return (
		<ClayForm.Group>
			<label htmlFor={id}>
				{label}

				<Asterisk required={required} />
			</label>

			<ClayRadioGroup
				id={id}
				name={name}
				onChange={handleChange}
				value={selectedOption.value}
			>
				{productOptionValues.map(({key, label, value}) => (
					<ClayRadio key={key} label={label} value={value} />
				))}
			</ClayRadioGroup>
		</ClayForm.Group>
	);
};

export default ProductOptionRadio;
