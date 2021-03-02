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

export function validateAny() {
	return true;
}

export function validateArray(value, name, context) {
	return validateType('array', value, name, context);
}

export function validateString(value, name, context) {
	return validateType('string', value, name, context);
}

export function validateObject(value, name, context) {
	return validateType('object', value, name, context);
}

export function validateBoolean(value, name, context) {
	return validateType('boolean', value, name, context);
}

export function validateFunction(value, name, context) {
	return validateType('function', value, name, context);
}

export function validateNumber(value, name, context) {
	return validateType('number', value, name, context);
}

/**
 * Checks if the given value matches the expected type.
 * @param {string} expectedType String representing the expected type.
 * @param {*} value The value to match the type of.
 * @param {string} name The name of the property being checked.
 * @param {!Object} context Owner of the property being checked.
 * @return {!Error|boolean} `true` if the type matches, or an error otherwise.
 */
function validateType(expectedType, value, name, context) {
	const type = typeof value;

	if (type !== expectedType) {
		const errorMessage = `Expected type '${expectedType}', but received type '${type}'.`;

		return composeError(errorMessage, name, context);
	}

	return true;
}

/**
 * Composes a warning a warning message.
 * @param {string} error Error message to display to console.
 * @param {?string} name Name of state property that is giving the error.
 * @param {Object} context The property's owner.
 * @return {!Error}
 */
function composeError(error, name, context) {
	const componentName = context ? getFunctionName(context.constructor) : null;
	const renderer = context?.getRenderer && context.getRenderer();

	const parent = renderer?.getParent && renderer.getParent();

	const parentName = parent ? getFunctionName(parent.constructor) : null;

	const location = parentName
		? `Check render method of '${parentName}'.`
		: '';

	return new Error(
		`Invalid state passed to '${name}'.` +
			` ${error} Passed to '${componentName}'. ${location}`
	);
}

/**
 * Gets the name of the given function. If the current browser doesn't
 * support the `name` property, like IE11, this will calculate it from the function's
 * content string.
 * @param {!function()} fn
 * @return {string}
 */
function getFunctionName(fn) {
	if (!fn.name) {
		const stringifiedName = fn.toString();

		fn.name = stringifiedName.substring(9, stringifiedName.indexOf('('));
	}

	return fn.name;
}
