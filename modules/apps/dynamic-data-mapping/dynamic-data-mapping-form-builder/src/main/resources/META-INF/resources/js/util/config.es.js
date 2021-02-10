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

import {
	validateArray,
	validateArrayOf,
	validateBoolean,
	validateNumber,
	validateShapeOf,
	validateString,
} from 'frontend-js-web';

export const pageStructure = {
	validator: validateShapeOf({
		description: {
			validator: validateString,
		},
		rows: {
			validator: validateArrayOf({
				validator: validateShapeOf({
					columns: {
						validator: validateArrayOf({
							validator: validateShapeOf({
								fields: {
									validator: validateArray,
								},
								size: {
									validator: validateNumber,
								},
							}),
						}),
					},
				}),
			}),
		},
		title: {
			validator: validateString,
		},
	}),
};

export const focusedFieldStructure = {
	validator: validateShapeOf({
		columnIndex: {
			validator: validateNumber,
		},
		name: {
			required: true,
			validator: validateString,
		},
		pageIndex: {
			validator: validateNumber,
		},
		rowIndex: {
			validator: validateNumber,
		},
	}),
};

export const ruleStructure = {
	validator: validateShapeOf({
		actions: {
			validator: validateArrayOf({
				validator: validateShapeOf({
					action: {
						validator: validateString,
					},
					label: {
						validator: validateString,
					},
					target: {
						validator: validateString,
					},
				}),
			}),
		},
		conditions: {
			validator: validateArrayOf({
				validator: validateShapeOf({
					operands: {
						validator: validateArrayOf({
							validator: validateShapeOf({
								label: {
									validator: validateString,
								},
								repeatable: {
									validator: validateBoolean,
								},
								type: {
									validator: validateString,
								},
								value: {
									validator: validateString,
								},
							}),
						}),
					},
					operator: {
						validator: validateString,
					},
				}),
			}),
		},
		logicalOperator: {
			validator: validateString,
		},
	}),
};
