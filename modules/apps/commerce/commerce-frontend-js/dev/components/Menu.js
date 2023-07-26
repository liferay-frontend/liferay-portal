/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import ClayList from '@clayui/list';
import React from 'react';

import launcher from '../../src/main/resources/META-INF/resources/utilities/launcher';
import components from './index';

function getCurrentPage() {
	return (
		window.location.hash &&
		components.find((c) => c.entry === window.location.hash.slice(1)).page
	);
}

function Menu({spritemap}) {
	const [current, setComponent] = React.useState(getCurrentPage());

	return (
		<div className="container-fluid">
			<div className="row">
				<div className="col-3">
					<ClayList>
						<ClayList.Header>Components</ClayList.Header>

						{components
							.filter((c) => c.page)
							.map((component) => (
								<ClayList.Item flex key={component.entry}>
									<ClayList.ItemField>
										<ClayIcon
											className="mt-1"
											spritemap={spritemap}
											symbol="caret-right"
										/>
									</ClayList.ItemField>

									<ClayList.ItemField>
										<ClayLink
											href={`#${component.entry}`}
											onClick={() =>
												setComponent(component.page)
											}
										>
											{component.name}
										</ClayLink>
									</ClayList.ItemField>
								</ClayList.Item>
							))}
					</ClayList>
				</div>

				<div className="col-9">
					{current && <iframe frameBorder="0" src={current}></iframe>}
				</div>
			</div>
		</div>
	);
}

launcher(Menu, 'menu', 'menu-root', {
	spritemap: './assets/clay/icons.svg',
});
