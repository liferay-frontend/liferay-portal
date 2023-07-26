/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayManagementToolbar from '@clayui/management-toolbar';
import {openModal} from 'frontend-js-web';
import React, {useMemo} from 'react';

import StatusLabel from './StatusLabel';
import {CancelEditAPIApplicationModalContent} from './modals/CancelEditAPIApplicationModalContent';

interface APIApplicationManagementToolbarProps {
	hideButtons: boolean;
	itemData: APIApplicationItem;
	onPublish: voidReturn;
	onSave: voidReturn;
	title: string;
}

export function APIApplicationManagementToolbar({
	hideButtons,
	itemData,
	onPublish,
	onSave,
	title,
}: APIApplicationManagementToolbarProps) {
	const initialFieldData = useMemo(
		() => ({
			baseURL: itemData.baseURL,
			description: itemData.description,
			title: itemData.title,
		}),
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[title]
	);

	const hasDataChanged = () => {
		for (const [key, value] of Object.entries(initialFieldData)) {
			if (itemData[key as keyof Partial<APIApplicationItem>] !== value) {
				return true;
			}
		}

		return false;
	};

	const handleCancel = () => {
		if (hasDataChanged()) {
			openModal({
				center: true,
				contentComponent: ({closeModal}: {closeModal: voidReturn}) =>
					CancelEditAPIApplicationModalContent({
						closeModal,
					}),
				id: 'confirmCancelEditModal',
				size: 'md',
				status: 'warning',
			});
		}
		else {
			history.back();
		}
	};

	return (
		<>
			<ClayManagementToolbar>
				<ClayManagementToolbar.ItemList>
					<div className="ml-sm-2 mr-3">
						<h2 className="mb-0 text-truncate toolbar-title">
							{title}
						</h2>
					</div>

					<StatusLabel statusKey={itemData.applicationStatus.key} />
				</ClayManagementToolbar.ItemList>

				{!hideButtons && (
					<ClayManagementToolbar.ItemList>
						<ClayButton.Group key={1} spaced>
							<ClayButton
								borderless={
									itemData.applicationStatus?.key ===
									'unpublished'
								}
								displayType="secondary"
								id="apiAppManagementToolbarCancelButton"
								name="cancel"
								onClick={handleCancel}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							{itemData.applicationStatus?.key !==
								'published' && (
								<ClayButton
									displayType="secondary"
									id="apiAppManagementToolbarSaveButton"
									name="save"
									onClick={() =>
										(onSave() as unknown) as voidReturn
									}
								>
									{Liferay.Language.get('save')}
								</ClayButton>
							)}

							<ClayButton
								displayType="primary"
								id="apiAppManagementToolbarPublishButton"
								name="publish"
								onClick={() => onPublish()}
							>
								{Liferay.Language.get('publish')}
							</ClayButton>
						</ClayButton.Group>
					</ClayManagementToolbar.ItemList>
				)}
			</ClayManagementToolbar>
		</>
	);
}
