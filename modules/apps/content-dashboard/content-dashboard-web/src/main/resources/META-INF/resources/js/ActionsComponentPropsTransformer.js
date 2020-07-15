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

import React from 'react';
import ReactDOM from 'react-dom';

import BridgeComponent from './utils/BridgeComponent';
import Sidebar from './components/Sidebar';

export default function propsTransformer({
	items,
	sidebarContainerSelector,
	...otherProps
}) {
	const actions = {
		showInfo() {
			showSidebar();
		}
	}

	const hideSidebar = () => {
		sidebarRef.setState({open: false})
	};

	const showSidebar = () => {
		sidebarRef.setState({open: true})
	};

	const sidebarRef = ReactDOM.render(
		<BridgeComponent 
			bridgedComponent={Sidebar}
			onClose={hideSidebar}
		/>,
		document.querySelector(sidebarContainerSelector)
	);

	return {
		...otherProps,
		items: items.map((item) => {
			return {
				...item,
				onClick(event) {
					const action = item.data?.action;

					if (action) {
						event.preventDefault();

						actions[action](sidebarRef);
					}
				},
			};
		}),
	};
}