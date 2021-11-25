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
    activePath: Nullable<string>;
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
    addRoutes(routes: any): this;
    /**
     * Adds one or more surfaces to the application.
     * @param {Surface|String|Array.<Surface|String>} surfaces
     *     Surface element id or surface instance. You can also pass an Array
     *     whichcontains surface instances or id. In case of ID, these should be
     *     the id of surface element.
     * @chainable
     */
    addSurfaces(surfaces: any): this;
    /**
     * Returns if can navigate to path.
     */
    canNavigate(url: string): boolean;
    /**
     * Clear screens cache.
     */
    clearScreensCache(): void;
    /**
     * Retrieves or create a screen instance to a path.
     * @param {!string} path Path containing the querystring part.
     * @return {Screen}
     */
    createScreenInstance(path: string, route: any): Nullable<Screen>;
    /**
     * @inheritDoc
     */
    disposeInternal(): void;
    /**
     * Dispatches to the first route handler that matches the current path, if
     * any.
     * @return {Promise} Returns a pending request cancellable promise.
     */
    dispatch(): Nullable<Promise<void>>;
    /**
     * Starts navigation to a path.
     * @param {!string} path Path containing the querystring part.
     * @param {boolean=} opt_replaceHistory Replaces browser history.
     * @return {Promise} Returns a pending request cancellable promise.
     */
    doNavigate_(path: any, opt_replaceHistory: any): Promise<void>;
    /**
     * Extracts params according to the given path and route.
     * @param {!Route} route
     * @param {string} path
     * @param {!Object}
     */
    extractParams(route: any, path: any): any;
    /**
     * Finalizes a screen navigation.
     * @param {!string} path Path containing the querystring part.
     * @param {!Screen} nextScreen
     * @protected
     */
    finalizeNavigate_(path: any, nextScreen: any): void;
    /**
     * Finds a route for the test path. Returns true if matches has a route,
     * otherwise returns null.
     * @param {!string} path Path containing the querystring part.
     * @return {?Object} Route handler if match any or <code>null</code> if the
     *     path is the same as the current url and the path contains a fragment.
     */
    findRoute(path: any): Route | null;
    /**
     * Gets allow prevent navigate.
     * @return {boolean}
     */
    getAllowPreventNavigate(): boolean;
    /**
     * Gets link base path.
     * @return {!string}
     */
    getBasePath(): string;
    /**
     * Gets the default page title.
     * @return {string} defaultTitle
     */
    getDefaultTitle(): string;
    /**
     * Gets the form selector.
     * @return {!string}
     */
    getFormSelector(): string;
    /**
     * Check if route matching is ignoring query string from the route path.
     * @return {boolean}
     */
    getIgnoreQueryStringFromRoutePath(): boolean;
    /**
     * Gets the link selector.
     * @return {!string}
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
    getRoutePath(path: any): string;
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
    handleNavigateError_(path: any, nextScreen: any, error: any): void;
    /**
     * Checks if app has routes.
     * @return {boolean}
     */
    hasRoutes(): boolean;
    /**
     * Tests if host is an offsite link.
     * @param {!string} host Link host to compare with
     *     <code>window.location.host</code>.
     * @return {boolean}
     * @protected
     */
    isLinkSameOrigin_(host: any): boolean;
    /**
     * Tests if link element has the same app's base path.
     * @param {!string} path Link path containing the querystring part.
     * @return {boolean}
     * @protected
     */
    isSameBasePath_(path: any): boolean;
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
     * @param {string} href Information about the link's href.
     * @param {Event} event Dom event that initiated the navigation.
     */
    maybeScheduleNavigation_(href: any, event: any): boolean;
    /**
     * Maybe navigate to a path.
     * @param {string} href Information about the link's href.
     * @param {Event} event Dom event that initiated the navigation.
     */
    maybeNavigate_(href: any, event: any): void;
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
    maybePreventActivate_(nextScreen: any): Promise<undefined>;
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
     * @param {!string} path Path before navigation.
     * @param {!string} redirectPath Path after navigation.
     * @param {!string} hash Hash to be added to the path.
     * @return {!string} Returns the path with the hash restored.
     */
    maybeRestoreRedirectPathHash_(path: any, redirectPath: any, hash: any): any;
    /**
     * Maybe update scroll position in history state to anchor on path.
     * @param {!string} path Path containing anchor
     */
    maybeUpdateScrollPositionState_(): void;
    /**
     * Navigates to the specified path if there is a route handler that matches.
     * @param {!string} path Path to navigate containing the base path.
     * @param {boolean=} opt_replaceHistory Replaces browser history.
     * @param {Event=} event Optional event object that triggered the navigation.
     * @return {Promise} Returns a pending request cancellable promise.
     */
    navigate(path: any, opt_replaceHistory?: any, opt_event?: any): Nullable<Promise<void>>;
    /**
     * Befores navigation to a path.
     * @param {!Event} event Event facade containing <code>path</code> and
     *     <code>replaceHistory</code>.
     * @protected
     */
    onBeforeNavigate_(event: any): void;
    /**
     * Befores navigation to a path. Runs after external listeners.
     * @param {!Event} event Event facade containing <code>path</code> and
     *     <code>replaceHistory</code>.
     * @protected
     */
    onBeforeNavigateDefault_(event: any): void;
    /**
     * Custom event handler that executes the original listener that has been
     * added by the client code and terminates the navigation accordingly.
     * @param {!Event} event original Event facade.
     * @protected
     */
    onBeforeUnloadDefault_(event: any): void;
    /**
     * Intercepts document clicks and test link elements in order to decide
     * whether Surface app can navigate.
     * @param {!Event} event Event facade
     * @protected
     */
    onDocClickDelegate_(event: any): void;
    /**
     * Intercepts document form submits and test action path in order to decide
     * whether Surface app can navigate.
     * @param {!Event} event Event facade
     * @protected
     */
    onDocSubmitDelegate_(event: any): void;
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
     * @param {!Event} event Event facade
     * @protected
     */
    onPopstate_(event: any): void;
    /**
     * Listens document scroll changes in order to capture the possible lock
     * scroll position for history scrolling.
     * @protected
     */
    onScroll_(): void;
    /**
     * Starts navigation to a path.
     * @param {!Event} event Event facade containing <code>path</code> and
     *     <code>replaceHistory</code>.
     * @protected
     */
    onStartNavigate_(event: any): void;
    /**
     * Prefetches the specified path if there is a route handler that matches.
     * @param {!string} path Path to navigate containing the base path.
     * @return {Promise} Returns a pending request cancellable promise.
     */
    prefetch(path: any): Promise<void>;
    /**
     * Prepares screen flip. Updates history state and surfaces content.
     * @param {!string} path Path containing the querystring part.
     * @param {!Screen} nextScreen
     * @param {boolean=} opt_replaceHistory Replaces browser history.
     */
    prepareNavigateHistory_(path: any, nextScreen: any, opt_replaceHistory: any): void;
    /**
     * Prepares screen flip. Updates history state and surfaces content.
     * @param {!Screen} nextScreen
     * @param {!Object} surfaces Map of surfaces to flip keyed by surface id.
     * @param {!Object} params Params extracted from the current path.
     */
    prepareNavigateSurfaces_(nextScreen: any, surfaces: any, params: any): void;
    /**
     * Reloads the page by performing `window.location.reload()`.
     */
    reloadPage(): void;
    /**
     * Removes route instance from app routes.
     * @param {Route} route
     * @return {boolean} True if an element was removed.
     */
    removeRoute(route: any): boolean;
    /**
     * Removes a screen.
     * @param {!string} path Path containing the querystring part.
     */
    removeScreen(path: any): void;
    /**
     * Saves given scroll position into history state.
     * @param {!number} scrollTop Number containing the top scroll position to be saved.
     * @param {!number} scrollLeft Number containing the left scroll position to be saved.
     */
    saveHistoryCurrentPageScrollPosition_(scrollTop: any, scrollLeft: any): void;
    /**
     * Sets allow prevent navigate.
     * @param {boolean} allowPreventNavigate
     */
    setAllowPreventNavigate(allowPreventNavigate: any): void;
    /**
     * Sets link base path.
     * @param {!string} path
     */
    setBasePath(basePath: any): void;
    /**
     * Sets the default page title.
     * @param {string} defaultTitle
     */
    setDefaultTitle(defaultTitle: any): void;
    /**
     * Sets the form selector.
     * @param {!string} formSelector
     */
    setFormSelector(formSelector: any): void;
    /**
     * Sets if route matching should ignore query string from the route path.
     * @param {boolean} ignoreQueryStringFromRoutePath
     */
    setIgnoreQueryStringFromRoutePath(ignoreQueryStringFromRoutePath: any): void;
    /**
     * Sets the link selector.
     * @param {!string} linkSelector
     */
    setLinkSelector(linkSelector: any): void;
    /**
     * Sets the loading css class.
     * @param {!string} loadingCssClass
     */
    setLoadingCssClass(loadingCssClass: any): void;
    /**
     * Sets the update scroll position value.
     * @param {boolean} updateScrollPosition
     */
    setUpdateScrollPosition(updateScrollPosition: any): void;
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
     * @param {?string} title Document title.
     * @param {!string} path Path containing the querystring part.
     * @param {!object} state
     * @param {boolean=} opt_replaceHistory Replaces browser history.
     * @protected
     */
    updateHistory_(title: any, path: any, state: any, opt_replaceHistory: any): void;
}
export default App;
