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

import {render} from 'frontend-js-react-web';
import React from 'react';

import Sidebar from './components/Sidebar';

export default function propsTransformer({
	items,
	sidebarContainerSelector,
	...otherProps
}) {
	const sidebarRef = React.createRef();

	const actions = {
		showInfo() {
			showSidebar();
		},
	};

	const hideSidebar = () => {
		sidebarRef.current.close();
	};

	const setSidebarRef = (element) => {
		sidebarRef.current = element;
	};

	const showSidebar = () => {
		if (!sidebarRef.current) {
			render(
				Sidebar,
				{
					onClose: hideSidebar,
					ref: setSidebarRef,
				},
				document.querySelector(sidebarContainerSelector)
			);
		}
		else {
			sidebarRef.current.open();
		}
	};

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
