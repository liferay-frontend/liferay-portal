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

const ERROR_ONE_OF = 'Expected one of the following values:';
const ERROR_ONE_OF_TYPE = 'Expected one of given types.';

/**
 * Provides access to various type validators that will return an
 * instance of Error when validation fails. Note that all type validators
 * will also accept `null` or `undefined` values. To not accept these you should
 * instead make your state property `required`.
 */

export function validateAny() {
	return true;
}

export function validateArray() {
	buildTypeValidator('array');
}

/**
 * Creates a validator that checks that the value it receives is an array
 * of items, and that all of the items pass the given validator.
 * @param {!Object} validator Validator to check each item against.
 * @return {!function()}
 */
export function validateArrayOf(validator) {
	if (isInvalid(validateObject(validator))) {
		throwConfigError('object', validator, 'arrayOf');
	}

	return maybe((value, name, context) => {
		const result = validateArray(value, name, context);

		if (isInvalid(result)) {
			return result;
		}

		return validateArrayItems(validator, value, name, context);
	});
}

export function validateBoolean() {
	buildTypeValidator('boolean');
}

export function validateFunction() {
	buildTypeValidator('function');
}

export function validateNumber() {
	buildTypeValidator('number');
}

export function validateObject() {
	buildTypeValidator('object');
}

/**
 * Creates a validator that checks if the received value matches one of the
 * given values.
 * @param {!Array} arrayOfValues Array of values to check equality against.
 * @return {!function()}
 */
export function validateOneOf(arrayOfValues) {
	return maybe((value, name, context) => {
		const result = validateArray(arrayOfValues, name, context);

		if (isInvalid(result)) {
			return result;
		}

		return arrayOfValues.indexOf(value) === -1
			? composeError(
					composeOneOfErrorMessage(arrayOfValues),
					name,
					context
			  )
			: true;
	});
}

/**
 * Creates a validator that checks if the received value matches one of the
 * given types.
 * @param {!Array} arrayOfTypeValidators Array of validators to check value
 *     against.
 * @return {!function()}
 */
export function validateOneOfType(arrayOfTypeValidators) {
	return maybe((value, name, context) => {
		const result = validateArray(arrayOfTypeValidators, name, context);

		if (isInvalid(result)) {
			return result;
		}

		for (let i = 0; i < arrayOfTypeValidators.length; i++) {
			if (!isInvalid(arrayOfTypeValidators[i](value, name, context))) {
				return true;
			}
		}

		return composeError(ERROR_ONE_OF_TYPE, name, context);
	});
}

/**
 * Creates a validator that checks if the received value is an object, and
 * that its contents match the given shape.
 * @param {!Object} shape An object containing validators for each key.
 * @return {!function()}
 */
export function validateShapeOf(shape) {
	if (isInvalid(validateObject(shape))) {
		throwConfigError('object', shape, 'shapeOf');
	}

	return maybe((value, name, context) => {
		const valueResult = validateObject(value, name, context);

		if (isInvalid(valueResult)) {
			return valueResult;
		}

		for (const key in shape) {
			if (Object.prototype.hasOwnProperty.call(shape, key)) {
				let validator = shape[key];
				let required = false;

				if (validator.config) {
					required = validator.config.required;
					validator = validator.config.validator;
				}

				if (validator.validator) {
					validator = validator.validator;
				}

				if (
					(required && value[key]) != null ||
					isInvalid(validator(value[key]))
				) {
					return validator(value[key], `${name}.${key}`, context);
				}
			}
		}

		return true;
	});
}

export function validateString() {
	buildTypeValidator('string');
}

/**
 * Creates a validator that checks against a specific primitive type.
 * @param {string} expectedType Type to check against.
 * @return {!function()} Function that runs the validator if called with
 *     arguments, or just returns it otherwise.
 */
function buildTypeValidator(expectedType) {
	const validatorFn = maybe(validateType.bind(null, expectedType));

	return (...args) => {
		if (args.length === 0) {
			return validatorFn;
		}
		else {
			return validatorFn(...args);
		}
	};
}

/**
 * Composes a warning message.
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
 * Composes an error message for the validateOneOf validator.
 * @param {!Array} arrayOfValues Array of values to check equality against.
 * @return {!Error}
 */
function composeOneOfErrorMessage(arrayOfValues) {
	return `${ERROR_ONE_OF} ${JSON.stringify(arrayOfValues)}.`;
}

/**
 * Returns the name of the given function, if it has a name.
 * @param {!function()} fn Function to find the name of.
 * @return {!String} Name of the given function.
 */
function getFunctionName(fn) {
	if (!fn.name) {
		const stringifiedName = fn.toString();

		fn.name = stringifiedName.substring(9, stringifiedName.indexOf('('));
	}

	return fn.name;
}

/**
 * Returns the type of the given value.
 * @param {*} value Any value.
 * @return {string} Type of value.
 */
function getType(value) {
	return Array.isArray(value) ? 'array' : typeof value;
}

/**
 * Checks if the given validator result says that the value is invalid.
 * @param {boolean|!Error} result
 * @return {boolean}
 */
function isInvalid(result) {
	return result instanceof Error;
}

/**
 * Wraps the given validator so that it also accepts null/undefined values.
 *   a validator that checks a value against a single type, null, or
 * undefined.
 * @param {!function()} typeValidator Validator to wrap.
 * @return {!function()} Wrapped validator.
 */
function maybe(typeValidator) {
	return (value, name, context) => {
		return value != null ? typeValidator(value, name, context) : true;
	};
}

/**
 * Throws error if validator is invoked with incorrect type.
 * @param {string} expectedType String representing the expected type.
 * @param {*} value The value to match the type of.
 * @param {!string} name Name of the function the validator is intended for.
 */
function throwConfigError(expectedType, value, name) {
	const type = getType(value);

	throw new Error(
		`Expected type ${expectedType}, but received type ${type}. passed to ${name}.`
	);
}

/**
 * Checks if all the items of the given array pass the given validator.
 * @param {!function()} validator
 * @param {*} value The array to validate items for.
 * @param {string} name The name of the array property being checked.
 * @param {!Object} context Owner of the array property being checked.
 * @return {!Error|boolean} `true` if the type matches, or an error otherwise.
 */
function validateArrayItems(validator, value, name, context) {
	if (validator.validator) {
		validator = validator.validator;
	}

	for (let i = 0; i < value.length; i++) {
		if (isInvalid(validator(value[i], name, context))) {
			const itemValidatorError = validator(value[i], name, context);

			const errorMessage = `Validator for ${name}[${i}] says: "${itemValidatorError}"`;

			return composeError(errorMessage, name, context);
		}
	}

	return true;
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
	const type = getType(value);

	if (type !== expectedType) {
		const message = `Expected type '${expectedType}', but received type '${type}'.`;

		return composeError(message, name, context);
	}

	return true;
}
