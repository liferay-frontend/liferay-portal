/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {
	DisplayType,
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

	const [selectedItems, setSelectedItems] = React.useState<any[]>([]);
	const [showAlert, setShowAlert] = React.useState(false);
	const [fdsProps, setFdsProps] = React.useState(props);

	const onAlertActionClick = () =>
		setFdsProps({
			...props,
			additionalAPIURLParameters: `sort=dateCreated:desc&t=${Date.now()}`,
			filters: [],
			sorts: [],
		});

	const alertProps: IFrontendDataSetProps['alertProps'] = {
		buttons: (
			<ClayButton.Group className="pl-3" spaced>
				<ClayButton
					displayType="info"
					onClick={() => {
						onAlertActionClick();
						setShowAlert(false);
					}}
					size="sm"
				>
					{Liferay.Language.get('reload')}
				</ClayButton>

				<ClayButton alert onClick={() => setShowAlert(false)} size="sm">
					{Liferay.Language.get('dismiss')}
				</ClayButton>
			</ClayButton.Group>
		),
		content: 'This is the info message',
		displayType: 'info',
		onClose: () => setShowAlert(false),
		title: Liferay.Language.get('info'),
	};

	return (
		<>
			<button onClick={() => setSelectedItems([])}>clear</button>

			<button onClick={() => setShowAlert(true)}>
				Show info message
			</button>

			<FrontendDataSet
				{...fdsProps}
				alertProps={alertProps}
				onSelectedItemsChange={setSelectedItems}
				selectedItems={selectedItems}
				selectionType="multiple"
				showAlert={showAlert}
			/>
		</>
	);
};

export default ReactFrontendDataSet;
