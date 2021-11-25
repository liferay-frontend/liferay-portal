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
declare type Path = string | RegExp | Function;
declare class Route {
	handler: Function;
	path: Path;

	/**
	 * Route class.
	 * @param {!string|RegExp|Function} path
	 * @param {!Function} handler
	 */
	constructor(path: Path, handler: any);

	/**
	 * Builds parsed data (regex and tokens) for this route.
	 * @return {!Object}
	 * @protected
	 */
	buildParsedData_(): any;

	/**
	 * Extracts param data from the given path, according to this route.
	 * @param {string} path The url path to extract params from.
	 * @return {Object} The extracted data, if the path matches this route, or
	 *     null otherwise.
	 */
	extractParams(path: string): Record<string, string | string[]> | null;

	/**
	 * Gets the route handler.
	 * @return {!Function}
	 */
	getHandler(): Function;

	/**
	 * Gets the route path.
	 * @return {!string|RegExp|Function}
	 */
	getPath(): Path;

	/**
	 * Matches if the router can handle the tested path.
	 * @param {!string} value Path to test (may contain the querystring part).
	 * @return {boolean} Returns true if matches any route.
	 */
	matchesPath(value: string): any;
}
export default Route;
