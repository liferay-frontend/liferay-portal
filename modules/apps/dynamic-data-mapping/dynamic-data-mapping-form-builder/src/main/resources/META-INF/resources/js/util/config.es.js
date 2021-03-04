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

import {Config} from 'metal-state';

function isString(value) {
	return typeof value === 'string';
}

export const pageStructure = Config.shapeOf({
	description: {
		validator: isString,
	},
	rows: Config.arrayOf(
		Config.shapeOf({
			columns: Config.arrayOf(
				Config.shapeOf({
					fields: Config.array(),
					size: Config.number(),
				})
			),
		})
	),
	title: {
		validator: isString,
	},
});

export const focusedFieldStructure = Config.shapeOf({
	columnIndex: Config.number(),
	name: {
		required: true,
		validator: isString,
	},
	pageIndex: Config.number(),
	rowIndex: Config.number(),
});

export const ruleStructure = Config.shapeOf({
	actions: Config.arrayOf(
		Config.shapeOf({
			action: {
				validator: isString,
			},
			label: {
				validator: isString,
			},
			target: {
				validator: isString,
			},
		})
	),
	conditions: Config.arrayOf(
		Config.shapeOf({
			operands: Config.arrayOf(
				Config.shapeOf({
					label: {
						validator: isString,
					},
					repeatable: Config.bool(),
					type: {
						validator: isString,
					},
					value: {
						validator: isString,
					},
				})
			),
			operator: {
				validator: isString,
			},
		})
	),
	logicalOperator: {
		validator: isString,
	},
});
