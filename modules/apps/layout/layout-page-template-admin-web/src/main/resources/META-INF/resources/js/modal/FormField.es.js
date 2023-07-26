/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

const FormField = ({children, error, id, name}) => {
	const hasError = error !== null && error !== undefined;

	return (
		<div
			className={classNames({'form-group': true, 'has-error': hasError})}
		>
			<label htmlFor={id}>
				{name}

				<span className="reference-mark">
					<ClayIcon symbol="asterisk" />
				</span>
			</label>

			{children}

			{hasError && (
				<div className="form-feedback-item">
					<span className="form-feedback-indicator mr-1">
						<ClayIcon symbol="exclamation-full" />
					</span>

					{error}
				</div>
			)}
		</div>
	);
};

FormField.propTypes = {
	error: PropTypes.string,
	id: PropTypes.string.isRequired,
	name: PropTypes.string.isRequired,
};

export {FormField};
export default FormField;
