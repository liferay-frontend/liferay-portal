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
import HtmlScreen from './HtmlScreen';
/**
 * EventScreen
 *
 * Inherits from Senna's `HtmlScreen`. It performs logic that is
 * common to both {@link ActionURLScreen|ActionURLScreen} and
 * {@link RenderURLScreen|RenderURLScreen}.
 */
declare class EventScreen extends HtmlScreen {
    cacheable: boolean;
    cacheLastModified: number;
    timeout: number;
    constructor();
    /**
     * @inheritDoc
     * Exposes the `screenDispose` event to the Liferay global object
     */
    dispose(): void;
    /**
     * @inheritDoc
     * Exposes the `screenActivate` event to the Liferay global object
     */
    activate(): void;
    addCache(content: string): void;
    /**
     * Attempts a regular navigation to the given path, if a form is not being
     * submitted and the redirect Path can't be matched to a known route
     */
    checkRedirectPath(redirectPath: string): void;
    deactivate(): void;
    /**
     * @inheritDoc
     */
    beforeScreenFlip(): void;
    /**
     * Copies the classes and onload event from the virtual document to the actual
     * document on the page
     */
    copyBodyAttributes(): void;
    /**
     * @inheritDoc
     * Temporarily makes all permanent styles temporary when a language change is
     * detected, so that they are disposed, re-downloaded, and re-parsed before
     * the screen flips. This is important because the content of the portal and
     * theme styles are dynamic and may depend on the displayed language.
     * Right-to-left (RTL) languages, for instance, have diffrent styles.
     * @param  {!Array} surfaces The surfaces to evaluate styles from
     */
    evaluateStyles(surfaces: any): Promise<void>;
    /**
     * @inheritDoc
     * Adds the `beforeScreenFlip` event to the lifecycle and exposes the
     * `screenFlip` event to the Liferay global object
     * @param  {!Array} surfaces The surfaces to flip
     */
    flip(surfaces: any): Promise<void>;
    /**
     * @inheritDoc
     * Returns the cache if it's not expired or if the cache
     * feature is not disabled
     * @return {!String} The cache contents
     */
    getCache(): string | null;
    /**
     * Returns the timestamp the cache was last modified
     * @return {!Number} `cacheLastModified` time
     */
    getCacheLastModified(): number;
    /**
     * Returns whether a given status code is considered valid
     * @param  {!Number} The status code to check
     * @return {!Boolean} True if the given status code is valid
     */
    isValidResponseStatusCode(statusCode: any): boolean;
    /**
     * @inheritDoc
     * @return {!String} The cache contents
     */
    load(path: any): Promise<unknown>;
    /**
     * The method used by {@link EventScreen#evaluateStyles|evaluateStyles}. This
     * changes the static properties `HtmlScreen.selectors.stylesTemporary` and
     * `HtmlScreen.selectors.stylesPermanent` temporarily. The action can be
     * undone by {@link EventScreen#restoreSelectors_|restoreSelectors_}
     * @param  {!String} currentLanguageId
     * @param  {!String} languageId
     */
    makePermanentSelectorsTemporary_(currentLanguageId: any, languageId: any): void;
    /**
     * The method used by {@link EventScreen#evaluateStyles|evaluateStyles}. This
     * restores the permanent and temporary selectors changed by
     * {@link EventScreen#makePermanentSelectorsTemporary_|makePermanentSelectorsTemporary_}.
     */
    restoreSelectors_(): void;
    /**
     * Executes the `document.body.onload` event every time a navigation occurs
     */
    runBodyOnLoad(): void;
    /**
     * Adds the type attribute with 'image/x-icon' when the favicon is an icon,
     * this ensures that it works fine in IE 11.
     * @param {!Array<Element>} elements
     * @private
     * @return {Promise}
     */
    runFaviconInElement_(elements: any): Promise<void>;
}
export default EventScreen;
