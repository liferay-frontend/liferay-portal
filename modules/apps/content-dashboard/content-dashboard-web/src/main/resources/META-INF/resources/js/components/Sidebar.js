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

import {ClayButtonWithIcon} from '@clayui/button';
import classNames from 'classnames';
import React, {useEffect, useImperativeHandle, useState} from 'react';

const noop = () => {};

export default React.forwardRef(({onClose = noop, open = true}, ref) => {
	const [isOpen, setIsOpen] = useState(false);

	// Wait until the component is rendered to show it so animation happens

	useEffect(() => {
		if (open !== false) {
			setTimeout(() => setIsOpen(true), 100);
		}
		else {
			setIsOpen(false);
		}
	}, [open]);

	useImperativeHandle(ref, () => ({
		close: () => {
			setIsOpen(false);
		},
		open: () => {
			setIsOpen(true);
		},
	}));

	return (
		<div
			className={classNames('sidebar-wrapper', {
				open: isOpen,
			})}
		>
			<div className="sidebar sidebar-light">
				<div className="sidebar-header">
					<div className="autofit-row sidebar-section">
						<div className="autofit-col autofit-col-expand">
							<div className="component-title">
								<span className="text-truncate-inline">
									Web Content Name
								</span>
							</div>

							<p className="component-subtitle">
								Basic Web Content
							</p>
						</div>

						<div className="autofit-col">
							<ClayButtonWithIcon
								onClick={onClose}
								symbol="times"
							/>
						</div>
					</div>
				</div>

				<div className="sidebar-body">Hello there!</div>
			</div>
		</div>
	);
});
