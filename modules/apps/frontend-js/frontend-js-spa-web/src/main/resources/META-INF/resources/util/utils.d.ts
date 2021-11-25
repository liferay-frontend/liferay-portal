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
declare type Style = HTMLLinkElement | HTMLStyleElement;
declare type DefaultFn = (...args: any[]) => any;
declare type AppendFn = (style: Style) => any;

/**
 * Removes all attributes form node.
 */
export declare function clearNodeAttributes(node: HTMLElement): void;

/**
 * Copies attributes form source node to target node.
 */
export declare function copyNodeAttributes(
	source: HTMLElement,
	target: HTMLElement
): void;

/**
 * Gets the current browser path including hashbang.
 */
export declare function getCurrentBrowserPath(): string;

/**
 * Gets the current browser path excluding hashbang.
 */
export declare function getCurrentBrowserPathWithoutHash(): string;

/**
 * Gets the given node offset coordinates.
 */
export declare function getNodeOffset(
	node: HTMLElement | null
): {
	offsetLeft: number;
	offsetTop: number;
};

/**
 * Given a portletId, returns the ID of the portlet's boundary DOM element
 */
export declare function getPortletBoundaryId(portletId: string): string;

/**
 * Given an array of portlet IDs, returns an array of portlet boundary IDs
 */
export declare function getPortletBoundaryIds(portletIds: string[]): string[];
export declare function getUid(): number;

/**
 * Extracts the path part of an url.
 */
export declare function getUrlPath(url: string): string;

/**
 * Extracts the path part of an url without hashbang.
 */
export declare function getUrlPathWithoutHash(url: string): string;

/**
 * Extracts the path part of an url without hashbang and query search.
 */
export declare function getUrlPathWithoutHashAndSearch(url: string): string;

/**
 * Checks if url is in the same browser current url excluding the hashbang.
 */
export declare function isCurrentBrowserPath(url?: string): boolean;

/**
 * Removes trailing slash in path.
 */
export declare function removePathTrailingSlash(path: string): string;

/**
 * Destroys all rendered portlets on the page
 */
export declare function resetAllPortlets(): void;

/**
 * Evaluates the code referenced by the given style/link element.
 */
export declare function runStyle(
	style: Style,
	defaultFn?: DefaultFn,
	appendFn?: AppendFn
): HTMLStyleElement | undefined;

/**
 * Evaluates any style present in the given element.
 * @param {!Element} element
 * @param {function()=} defaultFn Optional function to be called when the
 *   style has been run.
 * @param {function()=} appendFn Optional function to append the node
 *   into document.
 */
export declare function runStylesInElement(
	element: HTMLElement,
	defaultFn?: DefaultFn,
	appendFn?: AppendFn
): void;

/**
 * Overrides document referrer
 */
export declare function setReferrer(referrer: string): void;
export {};
