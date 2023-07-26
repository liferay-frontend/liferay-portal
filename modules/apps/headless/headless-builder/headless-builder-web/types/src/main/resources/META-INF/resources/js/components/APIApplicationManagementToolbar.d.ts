/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface APIApplicationManagementToolbarProps {
	hideButtons: boolean;
	itemData: APIApplicationItem;
	onPublish: voidReturn;
	onSave: voidReturn;
	title: string;
}
export declare function APIApplicationManagementToolbar({
	hideButtons,
	itemData,
	onPublish,
	onSave,
	title,
}: APIApplicationManagementToolbarProps): JSX.Element;
export {};
