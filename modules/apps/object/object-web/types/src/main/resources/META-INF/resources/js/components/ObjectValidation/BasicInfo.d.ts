/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {TabProps} from './useObjectValidationForm';
interface BasicInfoProps extends TabProps {
	componentLabel: string;
}
export declare function BasicInfo({
	componentLabel,
	disabled,
	errors,
	setValues,
	values,
}: BasicInfoProps): JSX.Element;
export {};
