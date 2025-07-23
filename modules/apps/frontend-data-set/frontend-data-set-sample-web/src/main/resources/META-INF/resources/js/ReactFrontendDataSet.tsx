/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import Icon from '@clayui/icon';
import {
	DisplayType,
	FDS_EVENT,
	FrontendDataSet,
	IFrontendDataSetProps,
} from '@liferay/frontend-data-set-web';
import React from 'react';

const ReactFrontendDataSet = (props: IFrontendDataSetProps) => {
	const cardsView = {
		_key: 'cards',
		contentRenderer: 'cards',
		default: false,
		label: 'Cards',
		name: 'cards',
		schema: {
			description: 'description',
			href: '',
			image: '',
			labels: [
				{
					displayType: DisplayType.INFO,
					value: 'color',
				},
				{
					displayTypeKey: 'status.label',
					displayTypeValues: {
						approved: DisplayType.SUCCESS,
						expired: DisplayType.DANGER,
					},
					value: 'status.label_i18n',
				},
			],
			sticker: '',
			symbol: '',
			title: 'title',
		},
		thumbnail: 'cards2',
	};

	props.views.push(cardsView);

	const listView = {
		contentRenderer: 'list',
		default: false,
		label: 'List',
		name: 'list',
		schema: {
			description: 'description',
			image: '',
			sticker: '',
			symbol: '',
			title: 'title',
		},
		thumbnail: 'list',
	};

	props.views.push(listView);

	return (
		<>
			<Button
				displayType="danger"
				onClick={() => {
					Liferay.fire(FDS_EVENT.CLEAR_SELECTION);
				}}
				size="sm"
			>
				<span className="inline-item inline-item-before">
					<Icon symbol="trash" />
				</span>
				Clear selection
			</Button>

			<FrontendDataSet {...props} />
		</>
	);
};

export default ReactFrontendDataSet;
