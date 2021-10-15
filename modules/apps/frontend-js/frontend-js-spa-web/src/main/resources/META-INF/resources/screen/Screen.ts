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

import {runScriptsInElement} from 'frontend-js-web';

import Cacheable from '../cacheable/Cacheable';
import {getUid} from '../util/utils';

import type Surface from '../surface/Surface';

export interface State {
	form: boolean;
	path: string;
	redirectPath: string;
	scrollLeft: number;
	scrollTop: number;
	senna: boolean;
}

type Surfaces = {
	[key: string]: Surface;
};

type Nullable<T> = T | null;

type Metas = Nullable<HTMLMetaElement | NodeListOf<HTMLMetaElement>>;

abstract class Screen extends Cacheable {
	static isImplementedBy: (instance: any) => boolean;

	id: string;
	metas: Metas;
	title: Nullable<string>;

	/**
	 * Screen class is a special type of route handler that provides helper
	 * utilities that adds lifecycle and methods to provide content to each
	 * registered surface.
	 */
	constructor() {
		super();

		/**
		 * Holds the screen id.
		 */
		this.id = this.makeId_(getUid());

		/**
		 * Holds the screen meta tags. Relevant when the meta tags
		 * should be updated when screen is rendered.
		 */
		this.metas = null;

		/**
		 * Holds the screen title. Relevant when the page title should be
		 * upadated when screen is rendered.
		 */
		this.title = null;
	}

	/**
	 * Fires when the screen is active. Allows a screen to perform any setup
	 * that requires its DOM to be visible. Lifecycle.
	 */
	activate() {}

	/**
	 * Gives the Screen a chance to cancel the navigation and stop itself from
	 * activating. Can be used, for example, to prevent navigation if a user
	 * is not authenticated. Lifecycle.
	 * @return {boolean=|?Promise=} If returns or resolves to true,
	 *     the current screen is locked and the next nagivation interrupted.
	 */
	beforeActivate() {}

	/**
	 * Gives the Screen a chance to cancel the navigation and stop itself from
	 * being deactivated. Can be used, for example, if the screen has unsaved
	 * state. Lifecycle. Clean-up should not be preformed here, since the
	 * navigation may still be cancelled. Do clean-up in deactivate.
	 * @return {boolean=|?Promise=} If returns or resolves to true,
	 *     the current screen is locked and the next nagivation interrupted.
	 */
	beforeDeactivate() {}

	/**
	 * Gives the Screen a chance format the path before history update.
	 */
	beforeUpdateHistoryPath(path: string) {
		return path;
	}

	/**
	 * Gives the Screen a chance format the state before history update.
	 */
	beforeUpdateHistoryState(state: State): Nullable<State> {
		return state;
	}

	/**
	 * Allows a screen to do any cleanup necessary after it has been
	 * deactivated, for example cancelling outstanding requests or stopping
	 * timers. Lifecycle.
	 */
	deactivate() {}

	/**
	 * Dispose a screen, either after it is deactivated (in the case of a
	 * non-cacheable view) or when the App is itself disposed for whatever
	 * reason. Lifecycle.
	 */
	disposeInternal() {
		super.disposeInternal();
	}

	/**
	 * Allows a screen to evaluate scripts before the element is made visible.
	 * Lifecycle.
	 */
	evaluateScripts(surfaces: Surfaces) {
		Object.keys(surfaces).forEach((sId) => {
			if (surfaces[sId].activeChild) {
				runScriptsInElement(surfaces[sId].activeChild);
			}
		});

		return Promise.resolve();
	}

	/**
	 * Allows a screen to evaluate styles before the element is made visible.
	 * Lifecycle.
	 */
	evaluateStyles() {
		return Promise.resolve();
	}

	/**
	 * Allows a screen to perform any setup immediately before the element is
	 * made visible. Lifecycle.
	 */
	flip(surfaces: Surfaces) {
		const transitions: Promise<void>[] = [];

		Object.keys(surfaces).forEach((sId) => {
			var surface = surfaces[sId];
			var deferred = surface.show(this.id);

			transitions.push(deferred);
		});

		return Promise.all(transitions);
	}

	/**
	 * Gets the screen id.
	 */
	getId() {
		return this.id;
	}

	/**
	 * Gets the screen meta tags.
	 */
	getMetas() {
		return this.metas;
	}

	/**
	 * Returns the content for the given surface, or null if the surface isn't
	 * used by this screen. This will be called when a screen is initially
	 * constructed or, if a screen is non-cacheable, when navigated.
	 * @param {!string} surfaceId The id of the surface DOM element.
	 * @param {!Object} params Params extracted from the current path.
	 * @return {?string|Element=} This can return a string or node representing
	 *     the content of the surface. If returns falsy values surface default
	 *     content is restored.
	 */
	getSurfaceContent() {}

	/**
	 * Gets the screen title.
	 * @return {?string=}
	 */
	getTitle() {
		return this.title;
	}

	/**
	 * Returns all contents for the surfaces. This will pass the loaded content
	 * to <code>Screen.load</code> with all information you
	 * need to fulfill the surfaces. Lifecycle.
	 */
	load(_path: string) {
		return Promise.resolve();
	}

	/**
	 * Makes the id for the screen.
	 */
	makeId_(id: number) {
		return 'screen_' + id;
	}

	/**
	 * Sets the screen id.
	 */
	setId(id: string) {
		this.id = id;
	}

	/**
	 * Sets the screen meta tags.
	 */
	setMetas(metas: Metas) {
		this.metas = metas;
	}

	/**
	 * Sets the screen title.
	 */
	setTitle(title: string) {
		this.title = title;
	}

	toString() {
		return this.id;
	}
}

/**
 * Whether a given instance implements <code>Screen</code>.
 */
Screen.isImplementedBy = function (object) {
	return object instanceof Screen;
};

export default Screen;
