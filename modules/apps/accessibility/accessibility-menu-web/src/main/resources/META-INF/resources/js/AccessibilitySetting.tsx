/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayToggle} from '@clayui/form';
import React from 'react';

const KEY_EVENT = 'Enter';

type Props = {
	className: string;
	disabled: boolean;
	label: string;
	onChange: (value: boolean) => void;
	value: boolean;
};

const AccessibilitySetting = ({
	className,
	disabled,
	label,
	onChange,
	value,
}: Props) => (
	<li className={className}>
		<ClayToggle
			disabled={disabled}
			label={label}
			onKeyDown={(event) => {
				if (!disabled && event.key === KEY_EVENT) {
					onChange(!value);
				}
			}}
			onToggle={onChange}
			toggled={value}
		/>
	</li>
);

export default AccessibilitySetting;
