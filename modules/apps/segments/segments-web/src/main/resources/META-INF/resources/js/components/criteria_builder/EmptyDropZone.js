/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';
import {DropTarget as dropTarget} from 'react-dnd';

import {DragTypes} from '../../utils/drag-types';
import EmptyPlaceholder from './EmptyPlaceholder.es';

/**
 * Prevents items from being dropped from other contributors.
 * This method must be called `canDrop`.
 * @param {Object} props Component's current props.
 * @param {DropTargetMonitor} monitor
 * @returns {boolean} True if the target should accept the item.
 */
function canDrop(props, monitor) {
	const {propertyKey: destPropertyKey} = props;
	const {propertyKey: startPropertyKey} = monitor.getItem();

	return destPropertyKey === startPropertyKey;
}

/**
 * Implements the behavior of what will occur when an item is dropped.
 * Adds the criterion dropped.
 * This method must be called `drop`.
 * @param {Object} props Component's current props.
 * @param {DropTargetMonitor} monitor
 */
function drop(props, monitor) {
	const {criterion} = monitor.getItem();

	props.onCriterionAdd(0, criterion);
}

function EmptyDropZone({canDrop, connectDropTarget, emptyContributors, hover}) {
	const displayEmptyDropZone = canDrop || !emptyContributors;

	return (
		<div
			className={classNames('empty-drop-zone-root', {
				'empty-drop-zone-dashed border-primary rounded':
					displayEmptyDropZone && (!canDrop || !hover),
			})}
		>
			{connectDropTarget(
				displayEmptyDropZone ? (
					<div
						className={classNames(
							emptyContributors
								? 'empty-drop-zone-target'
								: 'drop-zone-target p-5',
							{
								'empty-drop-zone-target-solid dnd-hover border-primary rounded':
									canDrop && hover,
							}
						)}
					>
						<div className="empty-drop-zone-indicator w-100" />
					</div>
				) : (
					<div>
						<EmptyPlaceholder />
					</div>
				)
			)}
		</div>
	);
}
EmptyDropZone.propTypes = {
	canDrop: PropTypes.bool,
	connectDropTarget: PropTypes.func,
	emptyContributors: PropTypes.bool,
	hover: PropTypes.bool,
	onCriterionAdd: PropTypes.func.isRequired,
	propertyKey: PropTypes.string.isRequired,
};

export default dropTarget(
	DragTypes.PROPERTY,
	{
		canDrop,
		drop,
	},
	(connect, monitor) => ({
		canDrop: monitor.canDrop(),
		connectDropTarget: connect.dropTarget(),
		hover: monitor.isOver(),
	})
)(EmptyDropZone);
