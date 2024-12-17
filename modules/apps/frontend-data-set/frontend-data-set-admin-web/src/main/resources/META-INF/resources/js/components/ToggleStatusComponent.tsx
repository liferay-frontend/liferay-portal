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
}: {
	item: any;
	toggleChange: Function;
}) => {
	const label = item.inactive
		? Liferay.Language.get('inactive')
		: Liferay.Language.get('active');

	return (
		<ClayToggle
			label={label}
			onToggle={() => toggleChange(item)}
			sizing="sm"
			toggled={!item.inactive}
		/>
	);
};

export default ToggleStatusComponent;
