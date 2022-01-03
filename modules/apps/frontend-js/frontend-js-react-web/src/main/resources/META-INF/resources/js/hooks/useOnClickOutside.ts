/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {useEffect} from 'react';

/**
 * Hook for excuting a function callback when clicking outside
 * some DOM elements (for example, a ClayPopover)
 */
export default function useOnClickOutside(
	elements: Array<any>,
	handler: (...args: any[]) => any
) {
	useEffect(() => {
		const listener = (event: any) => {
			const {target} = event;

			/**
			 * Detect clicks on elements or their descendent elements.
			 */
			const filtered = elements.filter((element: any) => {
				if (typeof element === 'string') {
					return !!target.closest(element);
				}

				return element && element.contains(target);
			});

			if (!filtered.length) {
				handler(event);
			}
		};

		document.addEventListener('mousedown', listener);
		document.addEventListener('touchstart', listener);

		return () => {
			document.removeEventListener('mousedown', listener);
			document.removeEventListener('touchstart', listener);
		};
	}, [elements, handler]);
}
