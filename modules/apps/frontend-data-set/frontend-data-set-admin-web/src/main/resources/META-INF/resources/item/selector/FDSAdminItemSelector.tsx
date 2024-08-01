/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import React, {useState} from 'react';

import {API_URL, FDS_DEFAULT_PROPS} from '../../js/utils/constants';

import './FDSAdminItemSelector.scss';

interface ISelectedItem {
	externalReferenceCode: string;
	id: string;
	label: string;
}

const views = [
	{
		contentRenderer: 'list',
		name: 'list',
		schema: {
			description: 'description',
			symbol: 'symbol',
			title: 'label',
		},
	},
];

const FDSAdminItemSelector = (
	{
	className,
	classNameId,
	classPK,
	namespace,
}: {
	className: string;
	classNameId: string;
	classPK: string;
	namespace: string;
}
) => {
	const selectedId = window.frameElement && window.frameElement?.getAttribute('classPK');

	const preselectedItem = {
		externalReferenceCode: window.frameElement?.getAttribute('externalReferenceCode') || '',
		id: selectedId || '',
		label: window.frameElement?.getAttribute('label') || '',
	};
	const [selectedItem, setSelectedItem] = useState<ISelectedItem>(preselectedItem);

	console.log(
		className,
		classNameId,
		classPK,
		namespace
	);

	return (
		<div className="fds-admin-item-selector">
			<ClayModal.Body>
				<FrontendDataSet
					{...FDS_DEFAULT_PROPS}
					apiURL={API_URL.DATA_SETS}
					id={`${namespace}FDSAdminItemSelector`}
					onSelect={({
						selectedItems,
					}: {
						selectedItems: Array<ISelectedItem>;
					}) => {
						setSelectedItem({
							externalReferenceCode:
								selectedItems[0].externalReferenceCode,
							id: selectedItems[0].id,
							label: selectedItems[0].label,
						});
					}}
					selectedItemsKey="id"
					selectedItems={[selectedId]}
					selectionType="single"
					views={views}
				/>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							className="btn-cancel"
							displayType="secondary"
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							className="item-preview selector-button"
							data-value={`{
								"className": "${className}",
								"classNameId": "${classNameId}",
								"classPK": "${selectedItem?.id || selectedId}",
								"externalReferenceCode": "${selectedItem?.externalReferenceCode}",
								"title": "${selectedItem?.label}"}`}
						>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</div>
	);
};

export default FDSAdminItemSelector;
