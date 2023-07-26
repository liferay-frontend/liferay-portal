/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-web';

import SampleCustomDataRenderer from './SampleCustomDataRenderer';

export default function propsTransformer({
	additionalProps: {greeting},
	selectedItemsKey,
	...otherProps
}) {
	return {
		...otherProps,
		customDataRenderers: {
			sampleCustomDataRenderer: SampleCustomDataRenderer,
		},
		onActionDropdownItemClick({action, itemData, loadData, openSidePanel}) {
			if (action.data.id === 'openSidePanel') {
				openSidePanel({url: 'about:blank'});
			}
			else if (action.data.id === 'reload') {
				loadData();
			}
			else if (action.data.id === 'sampleMessage') {
				alert(`${greeting} ${itemData.title}!`);
			}
		},
		onBulkActionItemClick({
			action,
			loadData,
			selectedData: {items, keyValues},
		}) {
			if (action.data.id === 'sampleBulkAction') {
				openModal({
					bodyHTML: `
						<ol>
							${items.map((item) => `<li>${item.title}</li>`).join('')}
						</ol>

						<p>
							Key field: <code>${selectedItemsKey}</code> <br>
							Values of key fields of selected items:
							<ol>
								${keyValues.map((keyValue) => `<li>${keyValue}</li>`).join('')}
							</ol>
						</p>
					`,
					buttons: [
						{
							label: 'OK',
							onClick: ({processClose}) => {
								processClose();

								loadData();
							},
						},
					],
					size: 'md',
					title: 'You selected:',
				});
			}
		},
	};
}
