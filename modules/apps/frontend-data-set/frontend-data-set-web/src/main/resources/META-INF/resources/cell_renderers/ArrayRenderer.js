/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

function ArrayRenderer({value}) {
	return (
		<div>
			{value.map((item, index) => (
				<span key={item.id}>
					{item.name}

					{index < value.length - 1 ? ', ' : ''}
				</span>
			))}
		</div>
	);
}

ArrayRenderer.propTypes = {
	value: PropTypes.arrayOf({
		id: PropTypes.string.isRequired,
		name: PropTypes.string.isRequired,
	}),
};

export default ArrayRenderer;
