/**
 * SPDX-FileCopyrightText: (c) 20256 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import React, {useContext} from 'react';

// @ts-ignore

import DefaultContent from './DefaultRenderer';
import FrontendDataSetContext from '../FrontendDataSetContext';
import ClayIcon from '@clayui/icon';

const NavigationLinkRenderer = ({
	itemData,
	itemId,
	options,
	value
}: {
	itemData: any;
	itemId: number;
	options: any;
	value: string;
}) => {
	const {openFolder} = useContext(FrontendDataSetContext);
	const isFolder = itemData?.entryClassName.includes('Folder');
	return (
		<div className="table-list-title">
			<ClayLink
				href='#'
				onClick={(event) =>{
					event.preventDefault();

					openFolder({item: itemData});
				}}
				style={{cursor: 'pointer'}}
			>
				{isFolder && <span className='c-pr-2'>
					<ClayIcon symbol='folder' />
					</span>}
				{value}
			</ClayLink>
		</div>
	);
};

export default NavigationLinkRenderer;
