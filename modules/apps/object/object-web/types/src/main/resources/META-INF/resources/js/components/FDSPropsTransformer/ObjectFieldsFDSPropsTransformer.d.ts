/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface ObjectFieldSourceDataRenderer {
	value: boolean;
}
declare function ObjectFieldSourceDataRenderer({
	value,
}: ObjectFieldSourceDataRenderer): JSX.Element;
export default function ObjectFieldsFDSPropsTransformer({
	...otherProps
}: {
	[x: string]: any;
}): {
	customDataRenderers: {
		objectFieldSourceDataRenderer: typeof ObjectFieldSourceDataRenderer;
	};
	onActionDropdownItemClick({
		action,
		itemData,
	}: {
		action: {
			data: {
				id: string;
			};
		};
		itemData: {
			id: string;
		};
	}): void;
};
export {};
