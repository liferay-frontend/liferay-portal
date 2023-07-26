/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayCheckbox} from '@clayui/form';
import React, {useState} from 'react';

import Asterisk from './Asterisk';

const ProductOptionCheckbox = ({
	id,
	label,
	onChange,
	productOptionValues,
	required,
}) => {
	const [option, setOption] = useState(productOptionValues[0]);

	const handleChange = ({target: {checked}}) => {
		const updatedOption = {
			...option,
			selected: checked,
		};

		setOption(updatedOption);
		onChange(updatedOption);
	};

	return (
		<ClayForm.Group>
			<label htmlFor={id}>
				{label}

				<Asterisk required={required} />
			</label>

			<ClayCheckbox
				checked={option.selected}
				id={id}
				label={option.label}
				name={option.name}
				onChange={handleChange}
			/>
		</ClayForm.Group>
	);
};

export default ProductOptionCheckbox;
