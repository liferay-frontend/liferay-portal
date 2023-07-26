/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {SidebarCategory} from '@liferay/object-js-components-web';
interface EditObjectValidationProps {
	objectValidationRule: ObjectValidation;
	objectValidationRuleElements: SidebarCategory[];
	readOnly: boolean;
}
export default function EditObjectValidation({
	objectValidationRule: initialValues,
	objectValidationRuleElements,
	readOnly,
}: EditObjectValidationProps): JSX.Element;
export {};
