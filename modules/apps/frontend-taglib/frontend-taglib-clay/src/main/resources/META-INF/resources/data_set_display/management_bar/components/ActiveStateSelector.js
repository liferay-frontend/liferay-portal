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

import StatesContext from '../../contexts/StatesContext';
import selectActiveState from '../../thunks/selectActiveState';

function ActiveStateSelector() {
	const [{activeState, states}, dispatch] = useContext(StatesContext);

	const [active, setActive] = useState(false);

	return (
		<ClayDropDown
			active={active}
			onActiveChange={setActive}
			trigger={
				<ClayButton className="nav-link" displayType="unstyled">
					{activeState.label}

					<span className="inline-item inline-item-after">
						<ClayIcon symbol="caret-double-l" />
					</span>
				</ClayButton>
			}
		>
			<ClayDropDown.ItemList>
				{states.map(({id, label}) => (
					<ClayDropDown.Item
						key={id}
						onClick={(event) => {
							event.preventDefault();
							setActive(false);
							dispatch(
								selectActiveState({
									activeStateId: id,
								})
							);
						}}
					>
						{label}
					</ClayDropDown.Item>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}

export default ActiveStateSelector;
