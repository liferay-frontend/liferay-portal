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
export interface Token {
    name: string;
    optional: boolean;
    partial: boolean | '';
    pattern: string;
    prefix: string;
    repeat: boolean;
}
export declare type RouteOrTokens = string | Token[];
/**
 * Parses the given route format string into tokens representing its contents.
 */
export declare function parse(routeOrTokens: RouteOrTokens): (string | Token)[];
/**
 * Converts the given route format string to a regex that can extract param
 * data from paths matching it.
 */
export declare function toRegex(routeOrTokens: RouteOrTokens): RegExp;
/**
 * Extracts data from the given path according to the specified route format.
 */
export declare function extractData(routeOrTokens: RouteOrTokens, path: string): Record<string, string | string[]> | null;
