/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
interface DefaultRendererOptions {
	fieldName?: string;
	truncate?: boolean;
}
declare type DefaultRendererValue =
	| any
	| string
	| number
	| boolean
	| null
	| undefined
	| {
			icon?: string;
			iconSymbol?: string;
			label?: string;
			label_i18n?: string;
			text?: string;
	  };
declare const DefaultRenderer: React.FC<{
	itemData: any;
	options: DefaultRendererOptions;
	value: DefaultRendererValue;
}>;
export default DefaultRenderer;
