/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import React, {forwardRef, useCallback, useEffect, useState} from 'react';

import {useObserveRect} from '../hooks/useObserveRect';
import {doAlign} from '../utils';

export const Hotspot = forwardRef(({nodeToHighlight, onClick}, ref) => {

	/**
	 * Checks if the hotspot has been aligned. Used to hide the hotspot until
	 * it is aligned so there isn't a flash of a mispositioned hotspot.
	 *
	 * The CSS style `visibility: hidden` is being used to hide the hotspot
	 * since `doAlign` needs the element to be part of the document otherwise
	 * it won't properly align.
	 */
	const [aligned, setAligned] = useState(false);

	const align = useCallback(() => {
		if (nodeToHighlight && ref?.current) {
			doAlign({
				points: ['cc', 'tl'],
				sourceElement: ref.current,
				targetElement: nodeToHighlight,
			});

			setAligned(true);
		}
	}, [ref, nodeToHighlight, setAligned]);

	useEffect(() => {
		align();
	}, [align]);

	useObserveRect(align, nodeToHighlight);

	return (
		<div
			aria-label={Liferay.Language.get('start-the-walkthrough')}
			className={getCN('lfr-walkthrough-hotspot', {invisible: !aligned})}
			onClick={onClick}
			ref={ref}
		>
			<div className="lfr-walkthrough-hotspot-inner" />
		</div>
	);
});
