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
import App from './App';
/**
 * LiferayApp
 *
 * Inherits from `senna/src/app/App` and adds the following Liferay specific
 * behavior to Senna's default App:
 * <ul>
 *   <li> Makes cache expiration time configurable from System Settings</li>
 *   <li>Lets you set valid status codes (Liferay's default valid status codes
 *   are listed in {@link https://docs.liferay.com/portal/7.1/javadocs/portal-kernel/com/liferay/portal/kernel/servlet/ServletResponseConstants.html|ServletResponseConstants.java})
 *   <li>Shows alert notifications when requests take too long or when they fail</li>
 *   <li>Adds a portletBlacklist option that lets you exclude specific portlets
 *   from the SPA lifecycle.</li>
 * </ul>
 */
declare class LiferayApp extends App {
    /**
     * @inheritDoc
     */
    constructor({ cacheExpirationTime, clearScreensCache, debugEnabled, navigationExceptionSelectors, portletsBlacklist, requestTimeout, userNotification, validStatusCodes, }: {
        cacheExpirationTime: any;
        clearScreensCache: any;
        debugEnabled: any;
        navigationExceptionSelectors: any;
        portletsBlacklist: any;
        requestTimeout: any;
        userNotification: any;
        validStatusCodes: any;
    });
    /**
     * Retrieves or create a screen instance to a path. This method overrides
     * the default one to avoid ActionURLScreens to be cached and reused across
     * navigations causing different lifecycle mechanisms to be called on live
     * documents instead of on inert fragments
     * @param {!string} path Path containing the querystring part.
     * @return {Screen}
     */
    createScreenInstance(path: any, route: any): import("../screen/Screen").default | null;
    /**
     * Returns the cache expiration time configuration. This value comes from
     * System Settings. The configuration is set upon App initialization
     * @See {@link https://github.com/liferay/liferay-portal/blob/7.1.x/modules/apps/frontend-js/frontend-js-spa-web/src/main/resources/META-INF/resources/init.tmpl|init.tmpl}
     * @return {!Number} The `cacheExpirationTime` value
     */
    getCacheExpirationTime(): any;
    /**
     * Returns the valid status codes accepted by Liferay. These values
     * come from {@link https://docs.liferay.com/portal/7.1/javadocs/portal-kernel/com/liferay/portal/kernel/servlet/ServletResponseConstants.html|ServletResponseConstants.java}.
     * @return {!Array} The `validStatusCodes` property
     */
    getValidStatusCodes(): any;
    /**
     * Returns whether the cache is enabled. Cache is considered enabled
     * when {@link LiferayApp#getCacheExpirationTime|getCacheExpirationTime} is
     * greater than zero.
     * @return {!Boolean} True if cache is enabled
     */
    isCacheEnabled(): boolean;
    /**
     * Returns whether a given portlet element is in a blacklisted portlet
     * that should not behave like a SPA
     * @param  {!String} element The portlet boundary DOM node
     * @return {!Boolean} True if portlet element is blacklisted
     */
    isInPortletBlacklist(element: any): boolean;
    /**
     * Returns whether a given Screen's cache is expired. The expiration timeframe
     * is based on the value returned by {@link LiferayApp#getCacheExpirationTime|getCacheExpirationTime}.
     * @param  {!Screen} screen The Senna Screen
     * @return {!Boolean} True if the cache has expired
     */
    isScreenCacheExpired(screen: any): boolean;
    /**
     * A callback for Senna's `beforeNavigate` event. The cache is cleared
     * for all screens when the flag `clearScreensCache` is set or when a form
     * submission is about to occur. This method also exposes the
     * `beforeNavigate` event to the Liferay global object so anyone can listen
     * to it.
     * @param  {!Object} data Data about the event
     * @param  {!Event} event The event object
     */
    onBeforeNavigate(data: any, event: any): void;
    /**
     * A private event handler function, called when the
     * `dataLayoutConfigReady` event is fired on the Liferay object,
     * that initializes `Liferay.Layout`
     * @param  {!Event} event The event object
     */
    onDataLayoutConfigReady_(): void;
    /**
     * @inheritDoc
     * Overrides Senna's default `onDocClickDelegate_ handler`. Halts
     * SPA behavior if the click target is inside a blacklisted portlet.
     * Reduces navigations from multiple clicks to a single navigation.
     *
     * @param  {!Event} event The event object
     */
    onDocClickDelegate_(event: any): void;
    /**
     * @inheritDoc
     * Overrides Senna's default `onDocSubmitDelegate_ handler` and
     * halts SPA behavior if the form is inside a blacklisted
     * portlet
     * @param  {!Event} event The event object
     */
    onDocSubmitDelegate_(event: any): void;
    /**
     * Callback for Senna's `endNavigate` event that exposes it
     * to the Liferay global object
     * @param  {!Event} event The event object
     */
    onEndNavigate(event: any): void;
    /**
     * Callback for Liferay's `io:complete` event that clears screens cache when
     * an async request occurs
     */
    onLiferayIOComplete(): void;
    /**
     * Callback for Senna's `navigationError` event that displays
     * an alert message to the user with information about the error
     * @param  {!Event} event The event object
     */
    onNavigationError(event: any): void;
    /**
     * Callback for Senna's `startNavigate` event that exposes it
     * to the Liferay global object
     * @param  {!Event} event The event object
     */
    onStartNavigate(event: any): void;
    /**
     * Sets the `portletsBlacklist` property
     * @param  {!Object} portletsBlacklist
     */
    setPortletsBlacklist(portletsBlacklist: any): void;
    /**
     * Sets the `validStatusCodes` property
     * @param  {!Array} validStatusCodes
     */
    setValidStatusCodes(validStatusCodes: any): void;
    /**
     * Clears and detaches event handlers for Liferay's `dataLayoutConfigReady`
     * event
     */
    _clearLayoutData(): void;
    /**
     * Clears the timer that notifies the user when the SPA request
     * takes longer than the thresshold time configured in the
     * `this.userNotification.timeout` System Settings property
     */
    _clearRequestTimer(): void;
    /**
     * Creates a user notification
     * @param  {!Object} configuration object that's passed to `Liferay.Notification`
     * @return {!Promise} A promise that renders a notification when
     * resolved
     */
    _createNotification(config: any): Promise<unknown>;
    /**
     * Hides the request timeout alert
     */
    _hideTimeoutAlert(): void;
    _propagateParams(data: any): string;
    /**
     * Starts the timer that shows the user a notification when the SPA
     * request takes longer than the threshold time configured in the
     * `userNotification.timeout` System Settings property
     * @param  {!String} path The path that may time out
     */
    _startRequestTimer(path: any): void;
    /**
     * @inheritDoc
     */
    updateHistory_(title: any, path: any, state: any, opt_replaceHistory: any): void;
}
export default LiferayApp;
