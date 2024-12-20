/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayToggle} from '@clayui/form';
import React from 'react';

import '../../css/components/ToggleStatusComponent.scss';

const ToggleStatusComponent = ({
	item,
	toggleChange,
	value,
}: {
	item: any;
	sizing?: string;
	toggleChange: Function;
	value: boolean;
}) => {
	const label = value
		? Liferay.Language.get('active')
		: Liferay.Language.get('inactive');

	return (
		<ClayToggle
			label={label}
			onToggle={() => toggleChange(item, value)}
			sizing='sm'
			toggled={value}
		/>
	);
};

export default ToggleStatusComponent;
