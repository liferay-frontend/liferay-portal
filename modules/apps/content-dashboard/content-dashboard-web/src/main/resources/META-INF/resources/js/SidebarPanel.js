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

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useImperativeHandle, useState} from 'react';

import Sidebar from './components/Sidebar';

const noop = () => {};

const SidebarPanel = React.forwardRef(
	({fetchURL, onClose = noop, View}, ref) => {
		const [isOpen, setIsOpen] = useState(true);
		const [resourceData, setResourceData] = useState();

		const getData = (fetchURL) => {
			fetch(fetchURL, {
				method: 'GET',
			})
				.then((response) => response.json())
				.then((data) => {
					setResourceData(data);
				})
				.catch(() => {});
		};

		useEffect(() => {
			getData(fetchURL);
		}, [fetchURL]);

		useImperativeHandle(ref, () => ({
			close: () => {
				setIsOpen(false);
			},
			open: (fetchURL) => {
				getData(fetchURL);

				setIsOpen(true);
			},
		}));

		const onCloseHandle = () => (onClose ? onClose() : setIsOpen(false));

		return (
			<Sidebar onClose={onCloseHandle} open={isOpen}>
				{resourceData ? (
					<View {...resourceData} />
				) : (
					<Sidebar.Body>
						<ClayLoadingIndicator />
					</Sidebar.Body>
				)}
			</Sidebar>
		);
	}
);

export default SidebarPanel;
