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

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import React, {useContext, useState} from 'react';

import DataSetContext from '../../DataSetContext';
import buildCustomViewDefinition from '../../utils/buildCustomViewDefinition';
import ViewsContext from '../../views/ViewsContext';

const CustomViewDropdown = () => {
	const [{activeView, visibleFieldNames}] = useContext(ViewsContext);
	const dataSetContext = useContext(DataSetContext);

	const [active, setActive] = useState(false);

	const saveCustomView = () => {

		// placeholder, until we implement backend API

	};

	return (
		<ClayDropDown
			active={active}
			onActiveChange={setActive}
			trigger={
				<ClayButton borderless displayType="secondary">
					<span className="navbar-text-truncate">
						{Liferay.Language.get('default-view')}
					</span>

					<ClayIcon symbol={active ? 'caret-top' : 'caret-bottom'} />
				</ClayButton>
			}
		>
			<ClayDropDown.ItemList>
				<ClayDropDown.Item
					onClick={() => {
						const customViewDefinition = buildCustomViewDefinition({
							activeView,
							componentId: dataSetContext.id,
							customViewLabel: 'New Custom View',
							visibleFieldNames,
						});

						saveCustomView(customViewDefinition);
					}}
				>
					{Liferay.Language.get('save')}
				</ClayDropDown.Item>
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
};

export default CustomViewDropdown;
