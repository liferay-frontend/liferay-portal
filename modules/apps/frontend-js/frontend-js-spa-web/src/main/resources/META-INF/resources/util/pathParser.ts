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

const REGEX = /([/])?(?:(?::(\w+)(?:\(((?:\\.|[^\\()])*)\))?|\(((?:\\.|[^\\()])+)\))([+*?])?)/g;

export interface Token {
	name: string;
	optional: boolean;
	partial: boolean | '';
	pattern: string;
	prefix: string;
	repeat: boolean;
}

export type RouteOrTokens = string | Token[];

/**
 * Converts the given array of regex matches to a more readable object format.
 */
function convertMatchesToObj(matches: string[]) {
	return {
		match: matches[0],
		modifier: matches[5],
		name: matches[2],
		paramPattern: matches[3],
		prefix: matches[1],
		unnamedPattern: matches[4],
	};
}

/**
 * Converts the given tokens parsed from a route format string to a regex.
 */
function convertTokensToRegex(tokens: (string | Token)[]) {
	let regex = '';

	for (let i = 0; i < tokens.length; i++) {
		const token = tokens[i];

		if (typeof token === 'string') {
			regex += escape(token);
		}
		else {
			let capture = encloseNonCapturingGroup(token.pattern);
			if (token.repeat) {
				capture += encloseNonCapturingGroup('\\/' + capture) + '*';
			}
			capture = escape(token.prefix) + `(${capture})`;
			if (token.optional) {
				if (!token.partial) {
					capture = encloseNonCapturingGroup(capture);
				}
				capture += '?';
			}
			regex += capture;
		}
	}

	return new RegExp('^' + makeTrailingSlashOptional(regex) + '$');
}

/**
 * Encloses the given regex pattern into a non capturing group.
 */
function encloseNonCapturingGroup(pattern: string) {
	return `(?:${pattern})`;
}

/**
 * Escapes the given string to show up in the path regex.
 */
function escape(str: string) {
	return str.replace(/([.+*?=^!:${}()[]|\/\\])/g, '\\$1');
}

/**
 * Makes trailing slash optional on paths.
 */
function makeTrailingSlashOptional(regex: string) {
	if (regex.endsWith('/')) {
		regex += '?';
	}
	else {
		regex += '\\/?';
	}

	return regex;
}

/**
 * Parses the given route format string into tokens representing its contents.
 */
export function parse(routeOrTokens: RouteOrTokens): (string | Token)[] {
	if (typeof routeOrTokens !== 'string') {
		return routeOrTokens;
	}

	const route = routeOrTokens;
	let unnamedCount = 0;
	const tokens = [];
	let currPath = '';
	let index = 0;

	let matches = REGEX.exec(route);
	while (matches) {
		const data = convertMatchesToObj(matches);

		currPath = route.slice(index, matches.index);
		index = matches.index + data.match.length;
		tokens.push(currPath);

		tokens.push({
			name: data.name ? data.name : '' + unnamedCount++,
			optional: data.modifier === '*' || data.modifier === '?',
			partial: route[index] && route[index] !== data.prefix,
			pattern: data.paramPattern || data.unnamedPattern || '[^\\/]+',
			prefix: data.prefix || '',
			repeat: data.modifier === '*' || data.modifier === '+',
		});

		matches = REGEX.exec(route);
	}

	if (index < route.length) {
		tokens.push(route.substr(index));
	}

	return tokens;
}

/**
 * Converts the given route format string to a regex that can extract param
 * data from paths matching it.
 */
export function toRegex(routeOrTokens: RouteOrTokens) {
	return convertTokensToRegex(parse(routeOrTokens));
}

/**
 * Extracts data from the given path according to the specified route format.
 */
export function extractData(routeOrTokens: RouteOrTokens, path: string) {
	const data: Record<string, string | string[]> = {};
	const tokens = parse(routeOrTokens);
	const match = path.match(convertTokensToRegex(tokens));

	if (!match) {
		return null;
	}

	let paramIndex = 1;

	for (let i = 0; i < tokens.length; i++) {
		const token = tokens[i];

		if (typeof token !== 'string') {
			let value: string | string[] = match[paramIndex++];

			if (value !== undefined) {
				if (token.repeat) {
					value = value.split('/');
				}

				data[token.name] = value;
			}
		}
	}

	return data;
}
