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

import {fetch} from 'frontend-js-web';
import React, {useImperativeHandle, useEffect, useState} from 'react';

import Sidebar from './components/Sidebar';

const noop = () => {};

const SidebarPanel = React.forwardRef(
	({fetchURL, onClose = noop, resourcePrimKey}, ref) => {
		const [isOpen, setIsOpen] = useState(true);
		const [resourceData, setResourceData] = useState();

		const getData = (fetchURL, resourcePrimKey) => {
			if (resourcePrimKey !== resourceData?.resourcePrimKey) {
				const formData = new FormData();

				formData.append(`resourcePrimKey`, resourcePrimKey);

				fetch(fetchURL, {
					body: formData,
					method: 'POST',
				})
					.then((response) => response.json())
					.then((data) => {
						setResourceData(data);
					})
					.catch(() => {});
			}
		};

		useEffect(() => {
			getData(fetchURL, resourcePrimKey);
		}, [fetchURL, resourcePrimKey]);

		useImperativeHandle(ref, () => ({
			close: () => {
				setIsOpen(false);
			},
			open: (fetchURL, resourcePrimKey) => {
				getData(fetchURL, resourcePrimKey);

				setIsOpen(true);
			},
		}));

		return (
			<Sidebar onClose={onClose} open={isOpen}>
				{resourceData ? (
					<>
						<Sidebar.Header
							subtitlte={resourceData.type}
							title={resourceData.name}
						>
							Extra content
						</Sidebar.Header>

						<Sidebar.Body>Body Content</Sidebar.Body>
					</>
				) : (
					<div>Loading</div>
				)}
			</Sidebar>
		);
	}
);

export default SidebarPanel;
