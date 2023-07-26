/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {FormError} from '@liferay/object-js-components-web';
export declare function useListTypeForm({
	initialValues,
	onSubmit,
}: IUseListTypeForm): {
	errors: FormError<PickList>;
	handleChange: import('react').ChangeEventHandler<HTMLInputElement>;
	handleSubmit: import('react').FormEventHandler<HTMLFormElement>;
	setValues: (values: Partial<PickList>) => void;
	values: Partial<PickList>;
};
interface IUseListTypeForm {
	initialValues: Partial<PickList>;
	onSubmit: (picklist: PickList) => void;
}
export declare type ObjectValidationErrors = FormError<PickList>;
export {};
