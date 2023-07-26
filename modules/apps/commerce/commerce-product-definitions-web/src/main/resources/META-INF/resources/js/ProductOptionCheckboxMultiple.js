/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayCheckbox} from '@clayui/form';
import React, {useState} from 'react';

import Asterisk from './Asterisk';

const ProductOptionCheckboxMultiple = ({
	label,
	onChange,
	productOptionValues,
	required,
}) => {
	const [options, setOptions] = useState(
		productOptionValues.reduce((acc, cur) => {
			acc[cur.id] = cur;

			return acc;
		}, {})
	);

	const handleChange = ({target: {checked}}, id) => {
		const updatedOptions = {
			...options,
			[id]: {...options[id], selected: checked},
		};

		setOptions(updatedOptions);
		onChange(updatedOptions);
	};

	return (
		<ClayForm.Group>
			<label>
				{label}

				<Asterisk required={required} />
			</label>

			{Object.values(options).map(({id, key, label, name, selected}) => (
				<ClayCheckbox
					checked={selected}
					key={key}
					label={label}
					name={name}
					onChange={(event) => handleChange(event, id)}
				/>
			))}
		</ClayForm.Group>
	);
};

export default ProductOptionCheckboxMultiple;
