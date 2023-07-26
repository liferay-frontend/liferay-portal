/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React from 'react';

interface ObjectFieldSourceDataRenderer {
	value: boolean;
}

function ObjectFieldSourceDataRenderer({value}: ObjectFieldSourceDataRenderer) {
	return (
		<strong
			className={classNames(
				value ? 'label-info' : 'label-warning',
				'label'
			)}
		>
			{value
				? Liferay.Language.get('system')
				: Liferay.Language.get('custom')}
		</strong>
	);
}

export default function ObjectFieldsFDSPropsTransformer({...otherProps}) {
	return {
		...otherProps,
		customDataRenderers: {
			objectFieldSourceDataRenderer: ObjectFieldSourceDataRenderer,
		},
		onActionDropdownItemClick({
			action,
			itemData,
		}: {
			action: {data: {id: string}};
			itemData: {id: string};
		}) {
			if (action.data.id === 'deleteObjectField') {
				Liferay.fire('deleteObjectField', {itemData});
			}
		},
	};
}
