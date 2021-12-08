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
import { EventEmitter, EventHandler } from 'frontend-js-web';
import Route from '../route/Route';
import Screen from '../screen/Screen';
import Surface from '../surface/Surface';
declare const NavigationStrategy: {
    readonly IMMEDIATE: "immediate";
    readonly SCHEDULE_LAST: "scheduleLast";
};
declare type Nullable<T> = T | null;
interface NavigationEvent {
    href: string;
    isScheduledNavigation: boolean;
}
interface AppConfig {
    navigationExceptionSelectors?: string;
}
declare class App extends EventEmitter {
    activeScreen: Nullable<Screen>;
    activePath: string | null;
    allowPreventNavigate: boolean;
    basePath: string;
    browserPathBeforeNavigate: string;
    captureScrollPositionFromScrollEvent: boolean;
    defaultTitle: string;
    formSelector: string;
    ignoreQueryStringFromRoutePath: boolean;
    linkSelector: string;
    loadingCssClass: string;
    nativeScrollRestorationSupported: boolean;
    navigationStrategy: typeof NavigationStrategy[keyof typeof NavigationStrategy];
    isNavigationPending: boolean;
    pendingNavigate: Nullable<Promise<void>>;
    popstateScrollLeft: number;
    popstateScrollTop: number;
    redirectPath: Nullable<String>;
    routes: Route[];
    scheduledNavigationQueue: NavigationEvent[];
    screens: Record<string, Screen>;
    skipLoadPopstate: boolean;
    surfaces: Record<string, Surface>;
    updateScrollPosition: boolean;
    appEventHandlers_: EventHandler;
    formEventHandler_: any;
    linkEventHandler_: any;
    /**
     * App class that handle routes and screens lifecycle.
     */
    constructor({ navigationExceptionSelectors }?: AppConfig);
    addDOMEventListener(element: EventTarget, eventName: string, callback: EventListenerOrEventListenerObject): {
        removeListener(): void;
    };
    /**
     * Adds one or more screens to the application.
     *
     * Example:
     *
     * <code>
     *   app.addRoutes({ path: '/foo', handler: FooScreen });
     *   or
     *   app.addRoutes([{ path: '/foo', handler: function(route) { return new FooScreen(); } }]);
     * </code>
     *
     * @param {Object} or {Array} routes Single object or an array of object.
     *     Each object should contain <code>path</code> and <code>screen</code>.
     *     The <code>path</code> should be a string or a regex that maps the
     *     navigation route to a screen class definition (not an instance), e.g:
     *         <code>{ path: "/home:param1", handler: MyScreen }</code>
     *         <code>{ path: /foo.+/, handler: MyScreen }</code>
     * @chainable
     */
    addRoutes(routes: Route | Route[]): this;
    /**
     * Adds one or more surfaces to the application.
     * @param {Surface|String|Array.<Surface|String>} surfaces
     *     Surface element id or surface instance. You can also pass an Array
     *     whichcontains surface instances or id. In case of ID, these should be
     *     the id of surface element.
     * @chainable
     */
    addSurfaces(surfaces: Surface | Surface[]): this;
    /**
     * Returns if can navigate to path.
     */
    canNavigate(url: string): Boolean;
    /**
     * Clear screens cache.
     */
    clearScreensCache(): void;
    /**
     * Retrieves or create a screen instance to a path.
     */
    createScreenInstance(path: string, route: Route): Screen;
    /**
     * @inheritDoc
     */
    disposeInternal(): void;
    /**
     * Dispatches to the first route handler that matches the current path, if
     * any.
     * @return {Promise} Returns a pending request cancellable promise.
     */
    dispatch(): Promise<void>;
    /**
     * Starts navigation to a path.
     */
    doNavigate_(path: string, opt_replaceHistory: boolean): Promise<void>;
    /**
     * Extracts params according to the given path and route.
     */
    extractParams(route: Route, path: string): Record<string, string | string[]> | null;
    /**
     * Finalizes a screen navigation.
     * @param {!string} path Path containing the querystring part.
     * @param {!Screen} nextScreen
     * @protected
     */
    finalizeNavigate_(path: string, nextScreen: Screen): void;
    /**
     * Finds a route for the test path.
     */
    findRoute(path: string): Route | null;
    /**
     * Gets allow prevent navigate.
     */
    getAllowPreventNavigate(): boolean;
    /**
     * Gets link base path.
     */
    getBasePath(): string;
    /**
     * Gets the default page title.
     */
    getDefaultTitle(): string;
    /**
     * Gets the form selector.
     */
    getFormSelector(): string;
    /**
     * Check if route matching is ignoring query string from the route path.
     */
    getIgnoreQueryStringFromRoutePath(): boolean;
    /**
     * Gets the link selector.
     */
    getLinkSelector(): string;
    /**
     * Gets the loading css class.
     * @return {!string}
     */
    getLoadingCssClass(): string;
    /**
     * Returns the given path formatted to be matched by a route. This will,
     * for example, remove the base path from it, but make sure it will end
     * with a '/'.
     * @param {string} path
     * @return {string}
     */
    getRoutePath(path: string): string;
    /**
     * Gets the update scroll position value.
     * @return {boolean}
     */
    getUpdateScrollPosition(): boolean;
    /**
     * Handle navigation error.
     * @param {!string} path Path containing the querystring part.
     * @param {!Screen} nextScreen
     * @param {!Error} error
     * @protected
     */
    handleNavigateError_(path: string, nextScreen: Screen, error: Error): void;
    /**
     * Checks if app has routes.
     * @return {boolean}
     */
    hasRoutes(): boolean;
    /**
     * Tests if host is an offsite link.
     * @protected
     */
    isLinkSameOrigin_(host: string): boolean;
    /**
     * Tests if link element has the same app's base path.
     * @protected
     */
    isSameBasePath_(path: string): boolean;
    /**
     * Lock the document scroll in order to avoid the browser native back and
     * forward navigation to change the scroll position. In the end of
     * navigation lifecycle scroll is repositioned.
     * @protected
     */
    lockHistoryScrollPosition_(): void;
    /**
     * If supported by the browser, disables native scroll restoration and
     * stores current value.
     */
    maybeDisableNativeScrollRestoration(): void;
    /**
     * This method is used to evaluate if is possible to queue received
     *  dom event to scheduleNavigationQueue and enqueue it.
     */
    maybeScheduleNavigation_(href: string, event: Event): boolean;
    /**
     * Maybe navigate to a path.
     * @param {string} href Information about the link's href.
     * @param {Event} event Dom event that initiated the navigation.
     */
    maybeNavigate_(href: string, event: Event): void;
    /**
     * Checks whether the onbeforeunload global event handler is overloaded
     * by client code. If so, it replaces with a function that halts the normal
     * event flow in relation with the client onbeforeunload function.
     * This can be in most part used to prematurely terminate navigation to other pages
     * according to the given constrait(s).
     * @protected
     */
    maybeOverloadBeforeUnload_(): void;
    /**
     * Cancels navigation if nextScreen's beforeActivate lifecycle method
     * resolves to true.
     * @param {!Screen} nextScreen
     * @return {!Promise}
     */
    maybePreventActivate_(nextScreen: Screen): Promise<undefined>;
    /**
     * Cancels navigation if activeScreen's beforeDeactivate lifecycle
     * method resolves to true.
     * @return {!Promise}
     */
    maybePreventDeactivate_(): Promise<undefined>;
    /**
     * Maybe reposition scroll to hashed anchor.
     */
    maybeRepositionScrollToHashedAnchor(): void;
    /**
     * If supported by the browser, restores native scroll restoration to the
     * value captured by `maybeDisableNativeScrollRestoration`.
     */
    maybeRestoreNativeScrollRestoration(): void;
    /**
     * Maybe restore redirected path hash in case both the current path and
     * the given path are the same.
     */
    maybeRestoreRedirectPathHash_(path: string, redirectPath: string, hash: string): string;
    /**
     * Maybe update scroll position in history state to anchor on path.
     */
    maybeUpdateScrollPositionState_(): void;
    /**
     * Navigates to the specified path if there is a route handler that matches.
     */
    navigate(path: string, opt_replaceHistory?: boolean, opt_event?: Event): Promise<void>;
    /**
     * Befores navigation to a path.
     * @param {!Event} event Event facade containing <code>path</code> and
     *     <code>replaceHistory</code>.
     * @protected
     */
    onBeforeNavigate_(event: Event): void;
    /**
     * Befores navigation to a path. Runs after external listeners.
     * @protected
     */
    onBeforeNavigateDefault_(event: Event): void;
    /**
     * Custom event handler that executes the original listener that has been
     * added by the client code and terminates the navigation accordingly.
     * @protected
     */
    onBeforeUnloadDefault_(event: Event): void;
    /**
     * Intercepts document clicks and test link elements in order to decide
     * whether Surface app can navigate.
     * @protected
     */
    onDocClickDelegate_(event: Event): void;
    /**
     * Intercepts document form submits and test action path in order to decide
     * whether Surface app can navigate.
     * @protected
     */
    onDocSubmitDelegate_(event: Event): void;
    /**
     * Listens to the window's load event in order to avoid issues with some browsers
     * that trigger popstate calls on the first load. For more information see
     * http://stackoverflow.com/questions/6421769/popstate-on-pages-load-in-chrome.
     * @protected
     */
    onLoad_(): void;
    /**
     * Handles browser history changes and fires app's navigation if the state
     * belows to us. If we detect a popstate and the state is <code>null</code>,
     * assume it is navigating to an external page or to a page we don't have
     * route, then <code>window.location.reload()</code> is invoked in order to
     * reload the content to the current url.
     * @protected
     */
    onPopstate_(event: Event): void;
    /**
     * Listens document scroll changes in order to capture the possible lock
     * scroll position for history scrolling.
     * @protected
     */
    onScroll_(): void;
    /**
     * Starts navigation to a path.
     * @protected
     */
    onStartNavigate_(event: Event): void;
    /**
     * Prefetches the specified path if there is a route handler that matches.
     */
    prefetch(path: string): Promise<void>;
    /**
     * Prepares screen flip. Updates history state and surfaces content.
     */
    prepareNavigateHistory_(path: string, nextScreen: Screen, opt_replaceHistory?: boolean): void;
    /**
     * Reloads the page by performing `window.location.reload()`.
     */
    reloadPage(): void;
    /**
     * Removes route instance from app routes.
     */
    removeRoute(route: Route): boolean;
    /**
     * Removes a screen.
     */
    removeScreen(path: string): void;
    /**
     * Saves given scroll position into history state.
     */
    saveHistoryCurrentPageScrollPosition_(scrollTop: number, scrollLeft: number): void;
    /**
     * Sets allow prevent navigate.
     */
    setAllowPreventNavigate(allowPreventNavigate: boolean): void;
    /**
     * Sets link base path.
     */
    setBasePath(basePath: string): void;
    /**
     * Sets the default page title.
     */
    setDefaultTitle(defaultTitle: string): void;
    /**
     * Sets the form selector.
     * @param {!string} formSelector
     */
    setFormSelector(formSelector: string): void;
    /**
     * Sets if route matching should ignore query string from the route path.
     * @param {boolean} ignoreQueryStringFromRoutePath
     */
    setIgnoreQueryStringFromRoutePath(ignoreQueryStringFromRoutePath: boolean): void;
    /**
     * Sets the link selector.
     */
    setLinkSelector(linkSelector: string): void;
    /**
     * Sets the loading css class.
     */
    setLoadingCssClass(loadingCssClass: string): void;
    /**
     * Sets the update scroll position value.
     */
    setUpdateScrollPosition(updateScrollPosition: boolean): void;
    /**
     * Cancels pending navigate with <code>Cancel pending navigation</code> error.
     * @protected
     */
    stopPendingNavigate_(): void;
    /**
     * Sync document scroll position twice, the first one synchronous and then
     * one inside <code>setTimeout(cb, 0)</code>. Relevant to browsers that fires
     * scroll restoration asynchronously after popstate.
     * @protected
     * @return {?Promise=}
     */
    syncScrollPositionSyncThenAsync_(): Promise<unknown> | undefined;
    /**
     * Updates or replace browser history.
     * @protected
     */
    updateHistory_(title: string, path: string, state: any, opt_replaceHistory?: boolean): void;
}
export default App;
