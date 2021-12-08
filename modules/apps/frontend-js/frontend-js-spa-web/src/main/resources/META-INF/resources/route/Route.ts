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
	RouteOrTokens,
	Token,
	extractData,
	parse,
	toRegex,
} from '../util/pathParser';

type Path = string | RegExp | Function;
type ParsedData = {
	regex: RegExp;
	tokens: (string | Token)[];
};

class Route {
	handler: Function;
	path: Path;
	parsedData_: ParsedData | undefined;

	/**
	 * Route class.
	 */
	constructor(path: Path, handler: Function) {
		if (!path) {
			throw new Error('Route path not specified.');
		}

		if (typeof handler !== 'function') {
			throw new Error('Route handler is not a function.');
		}

		/**
		 * Defines the handler which will execute once a URL in the application
		 * matches the path.
		 */
		this.handler = handler;

		/**
		 * Defines the path which will trigger the route handler.
		 */
		this.path = path;

		this.parsedData_ = undefined;
	}

	/**
	 * Builds parsed data (regex and tokens) for this route.
	 * @return {!Object}
	 * @protected
	 */
	buildParsedData_() {
		if (!this.parsedData_) {
			const tokens = parse(this.path as string);

			const regex = toRegex(tokens as string | Token[]);

			this.parsedData_ = {
				regex,
				tokens,
			};
		}

		return this.parsedData_;
	}

	/**
	 * Extracts param data from the given path, according to this route.
	 * @param {string} path The url path to extract params from.
	 * @return {Object} The extracted data, if the path matches this route, or
	 *     null otherwise.
	 */
	extractParams(path: string) {
		if (typeof this.path === 'string') {
			return extractData(
				this.buildParsedData_().tokens as RouteOrTokens,
				path
			);
		}

		return {};
	}

	/**
	 * Gets the route handler.
	 * @return {!Function}
	 */
	getHandler() {
		return this.handler;
	}

	/**
	 * Gets the route path.
	 * @return {!string|RegExp|Function}
	 */
	getPath() {
		return this.path;
	}

	/**
	 * Matches if the router can handle the tested path.
	 * @param {!string} value Path to test (may contain the querystring part).
	 * @return {boolean} Returns true if matches any route.
	 */
	matchesPath(value: string) {
		var path = this.path;

		if (typeof path === 'function') {
			return path(value);
		}
		if (typeof path === 'string') {
			path = this.buildParsedData_().regex;
		}
		if (path instanceof RegExp) {
			return value.search(path) > -1;
		}

		return false;
	}
}

export default Route;
