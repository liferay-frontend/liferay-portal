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

import {EventEmitter, EventHandler, debounce, delegate} from 'frontend-js-web';

import Route from '../route/Route';
import Screen from '../screen/Screen';
import Surface from '../surface/Surface';
import {
	getCurrentBrowserPath,
	getCurrentBrowserPathWithoutHash,
	getNodeOffset,
	getUrlPath,
	getUrlPathWithoutHash,
	getUrlPathWithoutHashAndSearch,
	isCurrentBrowserPath,
	removePathTrailingSlash,
	setReferrer,
} from '../util/utils';

const NavigationStrategy = {
	IMMEDIATE: 'immediate',
	SCHEDULE_LAST: 'scheduleLast',
} as const;

type Nullable<T> = T | null;

interface NavigationEvent {
	href: string;
	isScheduledNavigation: boolean;
}

interface AppConfig {
	navigationExceptionSelectors?: string;
}

class App extends EventEmitter {
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
	constructor({navigationExceptionSelectors}: AppConfig = {}) {
		super();

		/**
		 * Holds the active screen.
		 */
		this.activeScreen = null;

		/**
		 * Holds the active path containing the query parameters.
		 */
		this.activePath = null;

		/**
		 * Allows prevent navigate from dom prevented event.
		 */
		this.allowPreventNavigate = true;

		/**
		 * Holds link base path.
		 */
		this.basePath = '';

		/**
		 * Holds the value of the browser path before a navigation is performed.
		 */
		this.browserPathBeforeNavigate = getCurrentBrowserPathWithoutHash();

		/**
		 * Captures scroll position from scroll event.
		 */
		this.captureScrollPositionFromScrollEvent = true;

		/**
		 * Holds the default page title.
		 */
		this.defaultTitle = document.title;

		/**
		 * Holds the form selector to define forms that are routed.
		 */
		this.formSelector = navigationExceptionSelectors
			? `form${navigationExceptionSelectors}`
			: 'form[enctype="multipart/form-data"]:not([data-senna-off])';

		/**
		 * When enabled, the route matching ignores query string from the path.
		 */
		this.ignoreQueryStringFromRoutePath = false;

		/**
		 * Holds the link selector to define links that are routed.
		 */
		this.linkSelector = navigationExceptionSelectors
			? `a${navigationExceptionSelectors}`
			: 'a:not([data-senna-off]):not([target="_blank"])';

		/**
		 * Holds the loading css class.
		 */
		this.loadingCssClass = 'senna-loading';

		/**
		 * Using the History API to manage your URLs is awesome and, as it happens,
		 * a crucial feature of good web apps. One of its downsides, however, is
		 * that scroll positions are stored and then, more importantly, restored
		 * whenever you traverse the history. This often means unsightly jumps as
		 * the scroll position changes automatically, and especially so if your app
		 * does transitions, or changes the contents of the page in any way.
		 * Ultimately this leads to an horrible user experience. The good news is,
		 * however, that there's a potential fix: history.scrollRestoration.
		 * https://developers.google.com/web/updates/2015/09/history-api-scroll-restoration
		 */
		this.nativeScrollRestorationSupported =
			'scrollRestoration' in window.history;

		/**
		 * When set to NavigationStrategy.SCHEDULE_LAST means that the current navigation
		 * cannot be Cancelled to start another and will be queued in
		 * scheduledNavigationQueue. When NavigationStrategy.IMMEDIATE means that all
		 * navigation will be cancelled to start another.
		 */
		this.navigationStrategy = NavigationStrategy.IMMEDIATE;

		/**
		 * When set to true there is a pendingNavigate that has not yet been
		 * resolved or rejected.
		 */
		this.isNavigationPending = false;

		/**
		 * Holds a deferred with the current navigation.
		 */
		this.pendingNavigate = null;

		/**
		 * Holds the window horizontal scroll position when the navigation using
		 * back or forward happens to be restored after the surfaces are updated.
		 */
		this.popstateScrollLeft = 0;

		/**
		 * Holds the window vertical scroll position when the navigation using
		 * back or forward happens to be restored after the surfaces are updated.
		 */
		this.popstateScrollTop = 0;

		/**
		 * Holds the redirect path containing the query parameters.
		 */
		this.redirectPath = null;

		/**
		 * Holds the screen routes configuration.
		 */
		this.routes = [];

		/**
		 * Holds a queue that stores every DOM event that can initiate a navigation.
		 */
		this.scheduledNavigationQueue = [];

		/**
		 * Maps the screen instances by the url containing the parameters.
		 */
		this.screens = {};

		/**
		 * When set to true the first erroneous popstate fired on page load will be
		 * ignored, only if <code>window.history.state</code> is also
		 * <code>null</code>.
		 */
		this.skipLoadPopstate = false;

		/**
		 * Maps that index the surfaces instances by the surface id.
		 */
		this.surfaces = {};

		/**
		 * When set to true, moves the scroll position after popstate, or to the
		 * top of the viewport for new navigation. If false, the browser will
		 * take care of scroll restoration.
		 */
		this.updateScrollPosition = true;

		this.appEventHandlers_ = new EventHandler();

		this.appEventHandlers_.add(

			// @ts-ignore

			this.addDOMEventListener(
				window,
				'scroll',
				debounce(this.onScroll_.bind(this), 100)
			),
			this.addDOMEventListener(window, 'load', this.onLoad_.bind(this)),
			this.addDOMEventListener(
				window,
				'popstate',
				this.onPopstate_.bind(this)
			)
		);

		this.on('startNavigate', this.onStartNavigate_);
		this.on('beforeNavigate', this.onBeforeNavigate_);

		// @ts-ignore

		this.on('beforeNavigate', this.onBeforeNavigateDefault_, true);
		this.on('beforeUnload', this.onBeforeUnloadDefault_);

		this.setLinkSelector(this.linkSelector);
		this.setFormSelector(this.formSelector);

		this.maybeOverloadBeforeUnload_();
	}

	addDOMEventListener(
		element: EventTarget,
		eventName: string,
		callback: EventListenerOrEventListenerObject
	) {
		element.addEventListener(eventName, callback);

		return {
			removeListener() {
				element.removeEventListener(eventName, callback);
			},
		};
	}

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
	addRoutes(routes: Route | Route[]) {
		if (!Array.isArray(routes)) {
			routes = [routes];
		}

		(routes as Route[]).forEach((route) => this.routes.push(route));

		return this;
	}

	/**
	 * Adds one or more surfaces to the application.
	 * @param {Surface|String|Array.<Surface|String>} surfaces
	 *     Surface element id or surface instance. You can also pass an Array
	 *     whichcontains surface instances or id. In case of ID, these should be
	 *     the id of surface element.
	 * @chainable
	 */
	addSurfaces(surfaces: Surface | Surface[]) {
		if (!Array.isArray(surfaces)) {
			surfaces = [surfaces];
		}
		surfaces.forEach((surface) => {
			if (typeof surface === 'string') {
				surface = new Surface(surface);
			}

			this.surfaces[surface.getId()] = surface;
		});

		return this;
	}

	/**
	 * Returns if can navigate to path.
	 */
	canNavigate(url: string): Boolean {
		try {
			const uri = url.startsWith('/')
				? new URL(url, window.location.origin)
				: new URL(url);

			const path = getUrlPath(url);

			if (!this.isLinkSameOrigin_(uri.host)) {
				return false;
			}

			if (!this.isSameBasePath_(path)) {
				return false;
			}

			// Prevents navigation if it's a hash change on the same url.

			if ((uri.hash || url.endsWith('#')) && isCurrentBrowserPath(path)) {
				return false;
			}

			if (!this.findRoute(path)) {
				return false;
			}

			return true;
		}
		catch (error) {
			return false;
		}
	}

	/**
	 * Clear screens cache.
	 */
	clearScreensCache() {
		Object.keys(this.screens).forEach((path) => {
			if (path === this.activePath) {
				this.activeScreen?.clearCache();
			}
			else if (
				!(
					this.isNavigationPending &&

					// @ts-ignore

					this.pendingNavigate?.path === path
				)
			) {
				this.removeScreen(path);
			}
		});
	}

	/**
	 * Retrieves or create a screen instance to a path.
	 */
	createScreenInstance(path: string, route: Route): Screen {
		if (!this.pendingNavigate && path === this.activePath) {
			return this.activeScreen as Screen;
		}
		/* jshint newcap: false */
		let screen = this.screens[path];

		if (!screen) {
			const handler = route.getHandler();

			if (
				handler === Screen ||
				Screen.isImplementedBy(handler.prototype)
			) {
				screen = handler();
			}
			else {

				// @ts-ignore

				screen = handler(route) || Screen();
			}
		}

		return screen;
	}

	/**
	 * @inheritDoc
	 */
	disposeInternal() {
		if (this.activeScreen) {
			this.removeScreen(this.activePath!);
		}
		this.clearScreensCache();
		this.formEventHandler_.dispose();
		this.linkEventHandler_.dispose();
		this.appEventHandlers_.removeAllListeners();
		super.disposeInternal();
	}

	/**
	 * Dispatches to the first route handler that matches the current path, if
	 * any.
	 * @return {Promise} Returns a pending request cancellable promise.
	 */
	dispatch() {
		return this.navigate(getCurrentBrowserPath(), true);
	}

	/**
	 * Starts navigation to a path.
	 */
	doNavigate_(path: string, opt_replaceHistory: boolean): Promise<void> {
		const route = this.findRoute(path) as Route;

		if (!route) {
			return Promise.reject(new Error('No route for ' + path));
		}

		this.stopPendingNavigate_();
		this.isNavigationPending = true;

		const nextScreen = this.createScreenInstance(path, route);

		return this.maybePreventDeactivate_()
			.then(() => this.maybePreventActivate_(nextScreen))
			.then(() => nextScreen?.load(path))
			.then(() => {

				// At this point we cannot stop navigation and all received
				// navigate candidates will be queued at scheduledNavigationQueue.

				this.navigationStrategy = NavigationStrategy.SCHEDULE_LAST;

				if (this.activeScreen) {
					this.activeScreen.deactivate();
				}

				this.prepareNavigateHistory_(
					path,
					nextScreen,
					opt_replaceHistory
				);
			})
			.then(() => nextScreen?.evaluateStyles())
			.then(() => nextScreen?.flip(this.surfaces))
			.then(() => nextScreen?.evaluateScripts(this.surfaces))
			.then(() => this.maybeUpdateScrollPositionState_())
			.then(() => this.syncScrollPositionSyncThenAsync_())
			.then(() => this.finalizeNavigate_(path, nextScreen))
			.then(() => this.maybeOverloadBeforeUnload_())
			.catch((reason) => {
				this.isNavigationPending = false;
				this.handleNavigateError_(path, nextScreen, reason);
				throw reason;
			})
			.finally(() => {
				this.navigationStrategy = NavigationStrategy.IMMEDIATE;

				if (this.scheduledNavigationQueue.length) {
					const scheduledNavigation = this.scheduledNavigationQueue.shift();

					if (scheduledNavigation) {
						this.maybeNavigate_(
							scheduledNavigation.href,

							// @ts-ignore

							scheduledNavigation
						);
					}
				}
			});
	}

	/**
	 * Extracts params according to the given path and route.
	 */
	extractParams(
		route: Route,
		path: string
	): Record<string, string | string[]> | null {
		return route.extractParams(this.getRoutePath(path));
	}

	/**
	 * Finalizes a screen navigation.
	 * @param {!string} path Path containing the querystring part.
	 * @param {!Screen} nextScreen
	 * @protected
	 */
	finalizeNavigate_(path: string, nextScreen: Screen) {
		nextScreen.activate();

		if (this.activeScreen && !this.activeScreen.isCacheable()) {
			if (this.activeScreen !== nextScreen) {
				this.removeScreen(this.activePath!);
			}
		}

		this.activePath = path;
		this.activeScreen = nextScreen;
		this.browserPathBeforeNavigate = getCurrentBrowserPathWithoutHash();
		this.screens[path] = nextScreen;
		this.isNavigationPending = false;
		this.pendingNavigate = null;

		if (Liferay.SPA) {
			Liferay.SPA.__capturedFormElement__ = null;
			Liferay.SPA.__capturedFormButtonElement__ = null;
		}
	}

	/**
	 * Finds a route for the test path.
	 */
	findRoute(path: string): Route | null {
		path = this.getRoutePath(path);

		for (let i = 0; i < this.routes.length; i++) {
			const route = this.routes[i];

			if (route.matchesPath(path)) {
				return route;
			}
		}

		return null;
	}

	/**
	 * Gets allow prevent navigate.
	 */
	getAllowPreventNavigate(): boolean {
		return this.allowPreventNavigate;
	}

	/**
	 * Gets link base path.
	 */
	getBasePath(): string {
		return this.basePath;
	}

	/**
	 * Gets the default page title.
	 */
	getDefaultTitle(): string {
		return this.defaultTitle;
	}

	/**
	 * Gets the form selector.
	 */
	getFormSelector(): string {
		return this.formSelector;
	}

	/**
	 * Check if route matching is ignoring query string from the route path.
	 */
	getIgnoreQueryStringFromRoutePath(): boolean {
		return this.ignoreQueryStringFromRoutePath;
	}

	/**
	 * Gets the link selector.
	 */
	getLinkSelector(): string {
		return this.linkSelector;
	}

	/**
	 * Gets the loading css class.
	 * @return {!string}
	 */
	getLoadingCssClass() {
		return this.loadingCssClass;
	}

	/**
	 * Returns the given path formatted to be matched by a route. This will,
	 * for example, remove the base path from it, but make sure it will end
	 * with a '/'.
	 * @param {string} path
	 * @return {string}
	 */
	getRoutePath(path: string): string {
		if (this.getIgnoreQueryStringFromRoutePath()) {
			path = getUrlPathWithoutHashAndSearch(path);

			return getUrlPathWithoutHashAndSearch(
				path.substr(this.basePath.length)
			);
		}

		path = getUrlPathWithoutHash(path);

		return getUrlPathWithoutHash(path.substr(this.basePath.length));
	}

	/**
	 * Gets the update scroll position value.
	 * @return {boolean}
	 */
	getUpdateScrollPosition() {
		return this.updateScrollPosition;
	}

	/**
	 * Handle navigation error.
	 * @param {!string} path Path containing the querystring part.
	 * @param {!Screen} nextScreen
	 * @param {!Error} error
	 * @protected
	 */
	handleNavigateError_(path: string, nextScreen: Screen, error: Error) {
		this.emit('navigationError', {
			error,
			nextScreen,
			path,
		});

		if (!isCurrentBrowserPath(path)) {
			if (this.isNavigationPending && this.pendingNavigate) {
				this.pendingNavigate.finally(
					() => this.removeScreen(path),

					// @ts-ignore

					this
				);
			}
			else {
				this.removeScreen(path);
			}
		}
	}

	/**
	 * Checks if app has routes.
	 * @return {boolean}
	 */
	hasRoutes() {
		return this.routes.length > 0;
	}

	/**
	 * Tests if host is an offsite link.
	 * @protected
	 */
	isLinkSameOrigin_(host: string): boolean {
		return host === window.location.host;
	}

	/**
	 * Tests if link element has the same app's base path.
	 * @protected
	 */
	isSameBasePath_(path: string): boolean {
		return path.indexOf(this.basePath) === 0;
	}

	/**
	 * Lock the document scroll in order to avoid the browser native back and
	 * forward navigation to change the scroll position. In the end of
	 * navigation lifecycle scroll is repositioned.
	 * @protected
	 */
	lockHistoryScrollPosition_() {
		const state = window.history.state;

		if (!state) {
			return;
		}

		// Browsers are inconsistent when re-positioning the scroll history on
		// popstate. At some browsers, history scroll happens before popstate, then
		// lock the scroll on the last known position as soon as possible after the
		// current JS execution context and capture the current value. Some others,
		// history scroll happens after popstate, in this case, we bind an once
		// scroll event to lock the las known position. Lastly, the previous two
		// behaviors can happen even on the same browser, hence the race will decide
		// the winner.

		var winner = false;
		var switchScrollPositionRace = function () {
			document.removeEventListener(
				'scroll',
				switchScrollPositionRace,
				false
			);
			if (!winner) {
				window.scrollTo(state.scrollLeft, state.scrollTop);
				winner = true;
			}
		};
		setTimeout(switchScrollPositionRace);
		document.addEventListener('scroll', switchScrollPositionRace, false);
	}

	/**
	 * If supported by the browser, disables native scroll restoration and
	 * stores current value.
	 */
	maybeDisableNativeScrollRestoration() {
		if (this.nativeScrollRestorationSupported) {

			// @ts-ignore

			this.nativeScrollRestoration_ = window.history.scrollRestoration;
			window.history.scrollRestoration = 'manual';
		}
	}

	/**
	 * This method is used to evaluate if is possible to queue received
	 *  dom event to scheduleNavigationQueue and enqueue it.
	 */
	maybeScheduleNavigation_(href: string, event: Event) {
		if (
			this.isNavigationPending &&
			this.navigationStrategy === NavigationStrategy.SCHEDULE_LAST
		) {
			this.scheduledNavigationQueue = [
				{
					href,
					isScheduledNavigation: true,
				},

				// @ts-ignore

				...event,
			];

			return true;
		}

		return false;
	}

	/**
	 * Maybe navigate to a path.
	 * @param {string} href Information about the link's href.
	 * @param {Event} event Dom event that initiated the navigation.
	 */
	maybeNavigate_(href: string, event: Event) {
		if (!this.canNavigate(href)) {
			return;
		}

		const isNavigationScheduled = this.maybeScheduleNavigation_(
			href,
			event
		);

		if (isNavigationScheduled) {
			event.preventDefault();

			return;
		}

		let navigateFailed = false;

		try {
			this.navigate(getUrlPath(href), false, event);
		}
		catch (error) {

			// Do not prevent link navigation in case some synchronous error occurs

			navigateFailed = true;
		}

		// @ts-ignore

		if (!navigateFailed && !event.isScheduledNavigation) {
			event.preventDefault();
		}
	}

	/**
	 * Checks whether the onbeforeunload global event handler is overloaded
	 * by client code. If so, it replaces with a function that halts the normal
	 * event flow in relation with the client onbeforeunload function.
	 * This can be in most part used to prematurely terminate navigation to other pages
	 * according to the given constrait(s).
	 * @protected
	 */
	maybeOverloadBeforeUnload_() {
		if ('function' === typeof window.onbeforeunload) {

			// @ts-ignore

			window._onbeforeunload = window.onbeforeunload;

			window.onbeforeunload = (event) => {
				this.emit('beforeUnload', event);
				if (event && event.defaultPrevented) {
					return true;
				}
			};

			// mark the updated handler due unwanted recursion

			// @ts-ignore

			window.onbeforeunload._overloaded = true;
		}
	}

	/**
	 * Cancels navigation if nextScreen's beforeActivate lifecycle method
	 * resolves to true.
	 * @param {!Screen} nextScreen
	 * @return {!Promise}
	 */
	maybePreventActivate_(nextScreen: Screen) {
		return Promise.resolve()
			.then(() => {
				return nextScreen.beforeActivate();
			})
			.then((prevent) => {
				if (prevent !== undefined) {
					return Promise.reject(
						new Error('Cancelled by next screen')
					);
				}
			});
	}

	/**
	 * Cancels navigation if activeScreen's beforeDeactivate lifecycle
	 * method resolves to true.
	 * @return {!Promise}
	 */
	maybePreventDeactivate_() {
		return Promise.resolve()
			.then(() => {
				if (this.activeScreen) {
					return this.activeScreen.beforeDeactivate();
				}
			})
			.then((prevent) => {
				if (prevent !== undefined) {
					return Promise.reject(
						new Error('Cancelled by active screen')
					);
				}
			});
	}

	/**
	 * Maybe reposition scroll to hashed anchor.
	 */
	maybeRepositionScrollToHashedAnchor() {
		const hash = window.location.hash;
		if (hash) {
			const anchorElement = document.getElementById(hash.substring(1));
			if (anchorElement) {
				const {offsetLeft, offsetTop} = getNodeOffset(anchorElement);
				window.scrollTo(offsetLeft, offsetTop);
			}
		}
	}

	/**
	 * If supported by the browser, restores native scroll restoration to the
	 * value captured by `maybeDisableNativeScrollRestoration`.
	 */
	maybeRestoreNativeScrollRestoration() {
		if (
			this.nativeScrollRestorationSupported &&

			// @ts-ignore

			this.nativeScrollRestoration_
		) {

			// @ts-ignore

			window.history.scrollRestoration = this.nativeScrollRestoration_;
		}
	}

	/**
	 * Maybe restore redirected path hash in case both the current path and
	 * the given path are the same.
	 */
	maybeRestoreRedirectPathHash_(
		path: string,
		redirectPath: string,
		hash: string
	): string {
		if (redirectPath === getUrlPathWithoutHash(path)) {
			return redirectPath + hash;
		}

		return redirectPath;
	}

	/**
	 * Maybe update scroll position in history state to anchor on path.
	 */
	maybeUpdateScrollPositionState_() {
		const hash = window.location.hash;

		const anchorElement = document.getElementById(hash.substring(1));

		if (anchorElement) {
			const {offsetLeft, offsetTop} = getNodeOffset(anchorElement);

			this.saveHistoryCurrentPageScrollPosition_(offsetTop, offsetLeft);
		}
	}

	/**
	 * Navigates to the specified path if there is a route handler that matches.
	 */
	navigate(
		path: string,
		opt_replaceHistory?: boolean,
		opt_event?: Event
	): Promise<void> {
		if (opt_event) {
			if (Liferay.SPA) {
				Liferay.SPA.__capturedFormElement__ =

					// @ts-ignore

					opt_event.capturedFormElement;
				Liferay.SPA.__capturedFormButtonElement__ =

					// @ts-ignore

					opt_event.capturedFormButtonElement;
			}
		}

		// When reloading the same path do replaceState instead of pushState to
		// avoid polluting history with states with the same path.

		if (path === this.activePath) {
			opt_replaceHistory = true;
		}

		this.emit('beforeNavigate', {
			event: opt_event,
			path,
			replaceHistory: !!opt_replaceHistory,
		});

		return this.pendingNavigate as Promise<void>;
	}

	/**
	 * Befores navigation to a path.
	 * @param {!Event} event Event facade containing <code>path</code> and
	 *     <code>replaceHistory</code>.
	 * @protected
	 */
	onBeforeNavigate_(event: Event) {
		if (Liferay.SPA && Liferay.SPA.__capturedFormElement__) {

			// @ts-ignore

			event.form = Liferay.SPA.__capturedFormElement__;
		}
	}

	/**
	 * Befores navigation to a path. Runs after external listeners.
	 * @protected
	 */
	onBeforeNavigateDefault_(event: Event) {
		if (this.pendingNavigate) {
			if (

				// @ts-ignore

				this.pendingNavigate.path === event.path ||
				this.navigationStrategy === NavigationStrategy.SCHEDULE_LAST
			) {
				return;
			}
		}

		this.emit('beforeUnload', event);

		this.emit('startNavigate', {

			// @ts-ignore

			form: event.form,

			// @ts-ignore

			path: event.path,

			// @ts-ignore

			replaceHistory: event.replaceHistory,
		});
	}

	/**
	 * Custom event handler that executes the original listener that has been
	 * added by the client code and terminates the navigation accordingly.
	 * @protected
	 */
	onBeforeUnloadDefault_(event: Event) {

		// @ts-ignore

		const func = window._onbeforeunload;

		if (func && !func._overloaded && func()) {
			event.preventDefault();
		}
	}

	/**
	 * Intercepts document clicks and test link elements in order to decide
	 * whether Surface app can navigate.
	 * @protected
	 */
	onDocClickDelegate_(event: Event) {
		if (

			// @ts-ignore

			event.altKey ||

			// @ts-ignore

			event.ctrlKey ||

			// @ts-ignore

			event.metaKey ||

			// @ts-ignore

			event.shiftKey ||

			// @ts-ignore

			event.button
		) {
			return;
		}

		// @ts-ignore

		this.maybeNavigate_(event.delegateTarget.href, event);
	}

	/**
	 * Intercepts document form submits and test action path in order to decide
	 * whether Surface app can navigate.
	 * @protected
	 */
	onDocSubmitDelegate_(event: Event) {

		// @ts-ignore

		const form = event.delegateTarget;

		if (form.method === 'get') {
			return;
		}

		// @ts-ignore

		event.capturedFormElement = form;

		const buttonSelector =
			'button:not([type]),button[type=submit],input[type=submit]';

		// @ts-ignore

		if (document.activeElement.matches(buttonSelector)) {

			// @ts-ignore

			event.capturedFormButtonElement = document.activeElement;
		}
		else {

			// @ts-ignore

			event.capturedFormButtonElement = form.querySelector(
				buttonSelector
			);
		}

		this.maybeNavigate_(form.action, event);
	}

	/**
	 * Listens to the window's load event in order to avoid issues with some browsers
	 * that trigger popstate calls on the first load. For more information see
	 * http://stackoverflow.com/questions/6421769/popstate-on-pages-load-in-chrome.
	 * @protected
	 */
	onLoad_() {
		this.skipLoadPopstate = true;

		setTimeout(() => {

			// The timeout ensures that popstate events will be unblocked right
			// after the load event occured, but not in the same event-loop cycle.

			this.skipLoadPopstate = false;
		});

		// Try to reposition scroll to the hashed anchor when page loads.

		this.maybeRepositionScrollToHashedAnchor();
	}

	/**
	 * Handles browser history changes and fires app's navigation if the state
	 * belows to us. If we detect a popstate and the state is <code>null</code>,
	 * assume it is navigating to an external page or to a page we don't have
	 * route, then <code>window.location.reload()</code> is invoked in order to
	 * reload the content to the current url.
	 * @protected
	 */
	onPopstate_(event: Event) {
		if (this.skipLoadPopstate) {
			return;
		}

		// Do not navigate if the popstate was triggered by a hash change.

		if (isCurrentBrowserPath(this.browserPathBeforeNavigate)) {
			this.maybeRepositionScrollToHashedAnchor();

			return;
		}

		// @ts-ignore

		const state = event.state;

		if (!state) {
			if (window.location.hash) {

				// If senna is on an redirect path and a hash popstate happens
				// to a different url, reload the browser. This behavior doesn't
				// require senna to route hashed links and is closer to native
				// browser behavior.

				if (
					this.redirectPath &&
					!isCurrentBrowserPath(this.redirectPath as string)
				) {
					this.reloadPage();
				}

				// Always try to reposition scroll to the hashed anchor when
				// hash popstate happens.

				this.maybeRepositionScrollToHashedAnchor();
			}
			else {
				this.reloadPage();
			}

			return;
		}

		if (state.senna) {
			this.popstateScrollTop = state.scrollTop;
			this.popstateScrollLeft = state.scrollLeft;

			if (!this.nativeScrollRestorationSupported) {
				this.lockHistoryScrollPosition_();
			}

			this.once('endNavigate', () => {
				if (state.referrer) {
					setReferrer(state.referrer);
				}
			});

			const uri = state.path.startsWith('/')
				? new URL(state.path, window.location.origin)
				: new URL(state.path);

			uri.hostname = window.location.hostname;
			uri.port = window.location.port;

			const isNavigationScheduled = this.maybeScheduleNavigation_(
				uri.toString(),

				// @ts-ignore

				new Map()
			);

			if (isNavigationScheduled) {
				return;
			}

			this.navigate(state.path, true);
		}
	}

	/**
	 * Listens document scroll changes in order to capture the possible lock
	 * scroll position for history scrolling.
	 * @protected
	 */
	onScroll_() {
		if (this.captureScrollPositionFromScrollEvent) {
			this.saveHistoryCurrentPageScrollPosition_(
				window.pageYOffset,
				window.pageXOffset
			);
		}
	}

	/**
	 * Starts navigation to a path.
	 * @protected
	 */
	onStartNavigate_(event: Event) {
		this.maybeDisableNativeScrollRestoration();

		this.captureScrollPositionFromScrollEvent = false;

		document.documentElement.classList.add(this.loadingCssClass);

		const endNavigatePayload = {

			// @ts-ignore

			form: event.form,

			// @ts-ignore

			path: event.path,
		};

		this.pendingNavigate = this.doNavigate_(

			// @ts-ignore

			event.path,

			// @ts-ignore

			event.replaceHistory
		)
			.catch((reason) => {

				// @ts-ignore

				endNavigatePayload.error = reason;
				throw reason;
			})
			.finally(() => {
				if (
					!this.pendingNavigate &&
					!this.scheduledNavigationQueue.length
				) {
					document.documentElement.classList.remove(
						this.loadingCssClass
					);

					this.maybeRestoreNativeScrollRestoration();

					this.captureScrollPositionFromScrollEvent = true;
				}

				this.emit('endNavigate', endNavigatePayload);
			});

		// @ts-ignore

		this.pendingNavigate.path = event.path;
	}

	/**
	 * Prefetches the specified path if there is a route handler that matches.
	 */
	prefetch(path: string): Promise<void> {
		const route = this.findRoute(path);

		if (!route) {
			return Promise.reject(new Error('No route for ' + path));
		}

		const nextScreen = this.createScreenInstance(path, route as Route);

		return nextScreen
			.load(path)
			.then(() => {
				this.screens[path] = nextScreen;
			})
			.catch((reason) => {
				this.handleNavigateError_(path, nextScreen, reason);
				throw reason;
			});
	}

	/**
	 * Prepares screen flip. Updates history state and surfaces content.
	 */
	prepareNavigateHistory_(
		path: string,
		nextScreen: Screen,
		opt_replaceHistory?: boolean
	) {
		let title = nextScreen.getTitle();

		if (typeof title !== 'string') {
			title = this.getDefaultTitle();
		}

		let redirectPath = nextScreen.beforeUpdateHistoryPath(path);

		const hash = path.startsWith('/')
			? new URL(path, window.location.origin).hash
			: new URL(path).hash;

		redirectPath = this.maybeRestoreRedirectPathHash_(
			path,
			redirectPath,
			hash
		);

		const historyState = {
			form: !!Liferay?.SPA?.__capturedFormElement__,
			path,
			redirectPath,
			scrollLeft: 0,
			scrollTop: 0,
			senna: true,
		};

		if (opt_replaceHistory) {
			historyState.scrollTop = this.popstateScrollTop;
			historyState.scrollLeft = this.popstateScrollLeft;
		}

		this.updateHistory_(
			title,
			redirectPath,
			nextScreen.beforeUpdateHistoryState(historyState),
			opt_replaceHistory
		);

		this.redirectPath = redirectPath;
	}

	/**
	 * Reloads the page by performing `window.location.reload()`.
	 */
	reloadPage() {
		window.location.reload();
	}

	/**
	 * Removes route instance from app routes.
	 */
	removeRoute(route: Route): boolean {
		const routeIndex = this.routes.indexOf(route);

		if (routeIndex >= 0) {
			this.routes.splice(routeIndex, 1);
		}

		return routeIndex >= 0;
	}

	/**
	 * Removes a screen.
	 */
	removeScreen(path: string) {
		const screen = this.screens[path];

		if (screen) {
			Object.keys(this.surfaces).forEach((surfaceId) =>
				this.surfaces[surfaceId].remove(screen.getId())
			);

			screen.dispose();

			delete this.screens[path];
		}
	}

	/**
	 * Saves given scroll position into history state.
	 */
	saveHistoryCurrentPageScrollPosition_(
		scrollTop: number,
		scrollLeft: number
	) {
		const state = window.history.state;

		if (state && state.senna) {
			[state.scrollTop, state.scrollLeft] = [scrollTop, scrollLeft];

			window.history.replaceState(state, 'null', null);
		}
	}

	/**
	 * Sets allow prevent navigate.
	 */
	setAllowPreventNavigate(allowPreventNavigate: boolean) {
		this.allowPreventNavigate = allowPreventNavigate;
	}

	/**
	 * Sets link base path.
	 */
	setBasePath(basePath: string) {
		this.basePath = removePathTrailingSlash(basePath);
	}

	/**
	 * Sets the default page title.
	 */
	setDefaultTitle(defaultTitle: string) {
		this.defaultTitle = defaultTitle;
	}

	/**
	 * Sets the form selector.
	 * @param {!string} formSelector
	 */
	setFormSelector(formSelector: string) {
		this.formSelector = formSelector;

		if (this.formEventHandler_) {
			this.formEventHandler_.dispose();
		}

		this.formEventHandler_ = delegate(
			document,
			'submit',
			this.formSelector,
			this.onDocSubmitDelegate_.bind(this),
			this.allowPreventNavigate
		);
	}

	/**
	 * Sets if route matching should ignore query string from the route path.
	 * @param {boolean} ignoreQueryStringFromRoutePath
	 */
	setIgnoreQueryStringFromRoutePath(ignoreQueryStringFromRoutePath: boolean) {
		this.ignoreQueryStringFromRoutePath = ignoreQueryStringFromRoutePath;
	}

	/**
	 * Sets the link selector.
	 */
	setLinkSelector(linkSelector: string) {
		this.linkSelector = linkSelector;

		if (this.linkEventHandler_) {
			this.linkEventHandler_.dispose();
		}

		this.linkEventHandler_ = delegate(
			document,
			'click',
			this.linkSelector,
			this.onDocClickDelegate_.bind(this),
			this.allowPreventNavigate
		);
	}

	/**
	 * Sets the loading css class.
	 */
	setLoadingCssClass(loadingCssClass: string) {
		this.loadingCssClass = loadingCssClass;
	}

	/**
	 * Sets the update scroll position value.
	 */
	setUpdateScrollPosition(updateScrollPosition: boolean) {
		this.updateScrollPosition = updateScrollPosition;
	}

	/**
	 * Cancels pending navigate with <code>Cancel pending navigation</code> error.
	 * @protected
	 */
	stopPendingNavigate_() {
		this.pendingNavigate = null;
	}

	/**
	 * Sync document scroll position twice, the first one synchronous and then
	 * one inside <code>setTimeout(cb, 0)</code>. Relevant to browsers that fires
	 * scroll restoration asynchronously after popstate.
	 * @protected
	 * @return {?Promise=}
	 */
	syncScrollPositionSyncThenAsync_() {
		const state = window.history.state;

		if (!state) {
			return;
		}

		const scrollTop = state.scrollTop;
		const scrollLeft = state.scrollLeft;

		const sync = () => {
			if (this.updateScrollPosition) {
				window.scrollTo(scrollLeft, scrollTop);
			}
		};

		return new Promise((resolve) => {
			sync();

			setTimeout(() => {
				sync();

				// @ts-ignore

				resolve();
			});
		});
	}

	/**
	 * Updates or replace browser history.
	 * @protected
	 */
	updateHistory_(
		title: string,
		path: string,
		state: any,
		opt_replaceHistory?: boolean
	) {
		const referrer = window.location.href;

		if (state) {

			// @ts-ignore

			state.referrer = referrer;
		}

		if (opt_replaceHistory) {
			window.history.replaceState(state, title, path);
		}
		else {
			window.history.pushState(state, title, path);
		}

		setReferrer(referrer);

		const titleNode = document.querySelector('title');

		if (titleNode) {
			titleNode.innerHTML = title;
		}
		else {
			document.title = title;
		}
	}
}

export default App;
