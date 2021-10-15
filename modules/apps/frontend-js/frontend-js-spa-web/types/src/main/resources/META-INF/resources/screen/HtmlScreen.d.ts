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
import RequestScreen from './RequestScreen';
import type { Surfaces } from '../surface/Surface';
declare type Nullable<T> = T | null;
declare class HtmlScreen extends RequestScreen {
    metaTagsSelector: string;
    titleSelector: string;
    pendingStyles?: Nullable<HTMLStyleElement[]>;
    virtualDocument?: HTMLHtmlElement;
    /**
     * Screen class that perform a request and extracts surface contents from
     * the response content.
     */
    constructor();
    /**
     * @inheritDoc
     */
    activate(): void;
    /**
     * Allocates virtual document for content. After allocated virtual document
     * can be accessed by <code>this.virtualDocument</code>.
     */
    allocateVirtualDocumentForContent(htmlString: string): void;
    /**
     * Customizes logic to append styles into document. Relevant to when
     * tracking a style by id make sure to re-positions the new style in the
     * same dom order.
     */
    appendStyleIntoDocument_(newStyle: HTMLStyleElement): void;
    /**
     * If body is used as surface forces the requested documents to have same id
     * of the initial page.
     */
    assertSameBodyIdInVirtualDocument(): void;
    /**
     * Copies attributes from the <html> tag of content to the given node.
     */
    copyNodeAttributesFromContent_(content: string, node: HTMLElement): void;
    disposeInternal(): void;
    /**
     * Disposes pending styles if screen get disposed prior to its loading.
     */
    disposePendingStyles(): void;
    evaluateScripts(surfaces: Surfaces): Promise<void>;
    /**
     * @Override
     */
    evaluateStyles(surfaces: any): Promise<void>;
    /**
     * Allows a screen to evaluate the favicon style before the screen becomes visible.
     * @return {Promise}
     */
    evaluateFavicon_(): Promise<unknown>;
    /**
     * Evaluates tracked resources inside incoming fragment and remove existing
     * temporary resources.
     * @param {?function()} appendFn Function to append the node into document.
     * @param {!string} selector Selector used to find resources to track.
     * @param {!string} selectorTemporary Selector used to find temporary
     *     resources to track.
     * @param {!string} selectorPermanent Selector used to find permanent
     *     resources to track.
     * @param {!function} opt_appendResourceFn Optional function used to
     *     evaluate fragment containing resources.
     * @return {Promise} Deferred that waits resources evaluation to
     *     complete.
     * @private
     */
    evaluateTrackedResources_(evaluatorFn: any, selector: any, selectorTemporary: any, selectorPermanent: any, opt_appendResourceFn: any): Promise<unknown>;
    /**
     * @Override
     */
    flip(surfaces: any): Promise<void>;
    updateMetaTags_(): void;
    /**
     * Extracts a key to identify the resource based on its attributes.
     * @param {Element} resource
     * @return {string} Extracted key based on resource attributes in order of
     *     preference: id, href, src.
     */
    getResourceKey_(resource: any): any;
    /**
     * @inheritDoc
     */
    getSurfaceContent(surfaceId: any): string | undefined;
    /**
     * Gets the title selector.
     * @return {!string}
     */
    getTitleSelector(): string;
    /**
     * @inheritDoc
     */
    load(path: any): Promise<unknown>;
    /**
     * Adds the favicon elements to the document.
     * @param {!Array<Element>} elements
     * @private
     * @return {Promise}
     */
    runFaviconInElement_(elements: any): Promise<unknown>;
    /**
     * Queries elements from virtual document and returns an array of elements.
     * @param {!string} selector
     * @return {array.<Element>}
     */
    virtualQuerySelectorAll_(selector: any): any[];
    /**
     * Queries elements from document and returns an array of elements.
     * @param {!string} selector
     * @return {array.<Element>}
     */
    querySelectorAll_(selector: any): any[];
    /**
     * Releases virtual document allocated for content.
     */
    releaseVirtualDocument(): void;
    /**
     * Resolves title from allocated virtual document.
     */
    resolveTitleFromVirtualDocument(): void;
    resolveMetaTagsFromVirtualDocument(): void;
    /**
     * Sets the title selector.
     * @param {!string} titleSelector
     */
    setTitleSelector(titleSelector: any): void;
}
export default HtmlScreen;
