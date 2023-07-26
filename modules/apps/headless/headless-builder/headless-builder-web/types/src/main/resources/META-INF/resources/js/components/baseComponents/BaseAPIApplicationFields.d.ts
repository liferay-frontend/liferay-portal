/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch, SetStateAction} from 'react';
declare type DataError = {
	baseURL: boolean;
	title: boolean;
};
interface BaseAPIApplicationFieldsProps {
	basePath: string;
	data: Partial<APIApplicationItem>;
	displayError: DataError;
	setData: Dispatch<SetStateAction<Partial<APIApplicationItem>>>;
	urlAutoFill?: boolean;
}
export default function BaseAPIApplicationFields({
	basePath,
	data,
	displayError,
	setData,
	urlAutoFill,
}: BaseAPIApplicationFieldsProps): JSX.Element;
export {};
