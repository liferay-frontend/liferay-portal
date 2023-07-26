/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useRef} from 'react';

interface ClientExtensionRendererProps<T> {
	args: T;
	renderer: (args: T) => HTMLElement;
}

export default function ClientExtensionRenderer<T>({
	args,
	renderer,
}: ClientExtensionRendererProps<T>) {
	const containerRef = useRef<HTMLDivElement>(null);

	useEffect(() => {
		if (containerRef.current) {
			containerRef.current.appendChild(renderer(args));
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return <div ref={containerRef}></div>;
}
