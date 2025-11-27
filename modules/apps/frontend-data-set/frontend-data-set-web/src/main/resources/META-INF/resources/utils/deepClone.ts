/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Deep clones an object, including its function members (by reference).
 * Handles circular references, Dates, and RegExps.
 * @param sourceObj The object to clone.
 * @param hash WeakMap to handle circular references.
 * @returns A deep clone of the object.
 */
export default function deepClone<T>(sourceObj: T, hash = new WeakMap()): T {
	if (sourceObj === null || typeof sourceObj !== 'object') {

		// Primitives and functions are returned as is.
		// Functions are copied by reference.

		return sourceObj;
	}

	if (hash.has(sourceObj as unknown as object)) {

		// Handle circular references

		return hash.get(sourceObj as unknown as object) as T;
	}

	if (sourceObj instanceof Date) {
		return new Date(sourceObj.getTime()) as any as T;
	}

	if (sourceObj instanceof RegExp) {
		return new RegExp(sourceObj.source, sourceObj.flags) as any as T;
	}

	// Create a new object or array for the clone

	const clone = (Array.isArray(sourceObj) ? [] : {}) as T;

	// Store the clone in the hash map before recursively cloning properties
	// to handle circular references.

	hash.set(sourceObj as unknown as object, clone);

	for (const key in sourceObj) {
		if (Object.prototype.hasOwnProperty.call(sourceObj, key)) {
			(clone as any)[key] = deepClone((sourceObj as any)[key], hash);
		}
	}

	return clone;
}
