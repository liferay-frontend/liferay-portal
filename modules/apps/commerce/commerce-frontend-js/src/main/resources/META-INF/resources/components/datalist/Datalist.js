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

import {ClayInput} from '@clayui/form';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

const Datalist = (props) => {
	const name = props.options?.name || 'quantity_datalist';
	const gHash = (s) => {
		const h = 0;
		if (s.length === 0) {
			return h;
		}
		Array.from(s.length).forEach((h, i) => {
			var charCode = s.charCodeAt(i);
			h = (h << 7) - h + charCode;
			h = h & h;
		});

		return h;
	};
	const listId = `cs-` + gHash(name) + `-list`;

	return (
		<>
			<ClayInput
				aria-label={name + '_label'}
				className={classnames(
					'quantitySelect',
					'text-center',
					props.size === 'small' && 'form-control-sm',
					props.size === 'large' && 'form-control-lg'
				)}
				data-testid="datalist"
				disabled={props.disabled}
				id={listId + 'choice'}
				list={listId}
				onChange={(event) => {
					if (name === 'quantity_datalist') {
						props.updateQuantity(parseInt(event.target.value, 10));
					}
				}}
				pattern={props.pattern}
				type={props.type}
			></ClayInput>
			<datalist id={listId}>{props.children}</datalist>
		</>
	);
};

Datalist.defaultProps = {
	type: 'number',
};

Datalist.propTypes = {
	disabled: PropTypes.bool,
	items: PropTypes.arrayOf(
		PropTypes.shape({
			name: PropTypes.string,
			options: PropTypes.arrayOf(PropTypes.number),
			pattern: PropTypes.string,
			style: PropTypes.oneOf(['select', 'datalist']),
			type: PropTypes.string,
		})
	),
	size: PropTypes.string,
	type: PropTypes.string,
	updateQuantity: PropTypes.func,
};

export default Datalist;
