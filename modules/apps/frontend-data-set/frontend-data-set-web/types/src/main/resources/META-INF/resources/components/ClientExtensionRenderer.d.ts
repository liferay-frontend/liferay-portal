/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface ClientExtensionRendererProps<T> {
	args: T;
	renderer: (args: T) => HTMLElement;
}
export default function ClientExtensionRenderer<T>({
	args,
	renderer,
}: ClientExtensionRendererProps<T>): JSX.Element;
export {};
