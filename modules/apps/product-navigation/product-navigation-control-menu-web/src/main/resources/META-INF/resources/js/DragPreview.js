/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useRef} from 'react';
import {useDragLayer} from 'react-dnd';

const getItemStyles = (currentOffset, ref, rtl) => {
	if (!currentOffset || !ref.current) {
		return {
			display: 'none',
		};
	}

	const rect = ref.current.getBoundingClientRect();
	const x = rtl
		? currentOffset.x + rect.width * 0.5 - window.innerWidth
		: currentOffset.x - rect.width * 0.5;
	const y = currentOffset.y - rect.height * 0.5;

	const transform = `translate(${x}px, ${y}px)`;

	return {
		WebkitTransform: transform,
		transform,
	};
};

const DragPreview = ({rtl}) => {
	const ref = useRef();

	const {currentOffset, isDragging, item} = useDragLayer((monitor) => ({
		currentOffset: monitor.getClientOffset(),
		isDragging: monitor.isDragging(),
		item: monitor.getItem(),
	}));

	if (!isDragging) {
		return null;
	}

	return (
		<div className="sidebar__drag-preview">
			<div
				className="sidebar__drag-preview__content text-truncate"
				ref={ref}
				style={getItemStyles(currentOffset, ref, rtl)}
			>
				{item && item.icon && (
					<ClayIcon className="mr-3" symbol={item.icon} />
				)}

				{item && item.name
					? item.name
					: Liferay.Language.get('element')}
			</div>
		</div>
	);
};

DragPreview.propTypes = {
	rtl: PropTypes.bool,
};

export default DragPreview;
