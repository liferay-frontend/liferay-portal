/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {VerticalNav as ClayVerticalNav} from '@clayui/core';
import React from 'react';

export default function VerticalNav({
	activation,
	active,
	additionalProps: _additionalProps,
	componentId: _componentId,
	cssClass,
	decorated,
	defaultExpandedKeys,
	items,
	large,
	locale: _locale,
	portletId: _portletId,
	portletNamespace: _portletNamespace,
	triggerLabel,
	...otherProps
}) {
	return (
		<ClayVerticalNav
			activation={activation}
			active={active}
			className={cssClass}
			decorated={decorated}
			defaultExpandedKeys={new Set(defaultExpandedKeys)}
			items={items}
			large={large}
			triggerLabel={triggerLabel}
			{...otherProps}
		>
			{(item) => (
				<ClayVerticalNav.Item
					active={active ? active === item.id : item.active}
					href={item.href}
					items={item.items}
					key={item.id}
				>
					{item.label}
				</ClayVerticalNav.Item>
			)}
		</ClayVerticalNav>
	);
}
