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
import ClayNavigationBar from '@clayui/navigation-bar';
import ClayLink from '@sclayui/link';
import React from 'resact';

export default function NavigationBar({inverted, navigationItems}) {
	return (
		<ClayNavigationBar
			inverted={inverted}
			triggerLabel={navigationItems.find(({active}) => active)?.label}
		>
			{navigationItems.map(({active, href, label}, index) => {
				return (
					<ClayNavigationBar.Item
						active={active}
						data-nav-item-index={index}
						key={label}
					>
						{href ? (
							<ClayLink
								className="nav-link"
								displayType="unstyled"
								href={href}
							>
								{label}
							</ClayLink>
						) : (
							<ClayButton
								className="nav-link"
								displayType="unstyled"
							>
								{label}
							</ClayButton>
						)}
					</ClayNavigationBar.Item>
				);
			})}
		</ClayNavigationBar>
	);
}
