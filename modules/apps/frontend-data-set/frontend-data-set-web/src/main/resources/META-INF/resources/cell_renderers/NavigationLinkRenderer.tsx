/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import React from 'react';

import DefaultContent from './DefaultRenderer';

const NavigationLinkRenderer = ({
	itemData,
	openFolder,
	options = {},
	value,
}: {
	itemData: any;
	openFolder: ({item}: {item: any}) => void;
	options: any;
	value: string;
}) => {
	const isFolder = itemData?.entryClassName.includes('Folder');

	return (
		<div className="table-list-title">
			{isFolder ? (
				<ClayLink
					href="#"
					onClick={(event) => {
						event.preventDefault();

						openFolder({item: itemData});
					}}
					style={{cursor: 'pointer'}}
				>
					{isFolder && (
						<span className="c-pr-2">
							<ClayIcon symbol="folder" />
						</span>
					)}

					{value}
				</ClayLink>
			) : (
				<DefaultContent options={options} value={value} />
			)}
		</div>
	);
};

export default NavigationLinkRenderer;
