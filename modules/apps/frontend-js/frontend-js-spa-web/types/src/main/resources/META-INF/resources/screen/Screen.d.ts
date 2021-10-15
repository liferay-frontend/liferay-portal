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
import Cacheable from '../cacheable/Cacheable';
import type Surface from '../surface/Surface';
export interface State {
    form: boolean;
    path: string;
    redirectPath: string;
    scrollLeft: number;
    scrollTop: number;
    senna: boolean;
}
declare type Surfaces = {
    [key: string]: Surface;
};
declare type Nullable<T> = T | null;
declare type Metas = Nullable<HTMLMetaElement | NodeListOf<HTMLMetaElement>>;
declare abstract class Screen extends Cacheable {
    static isImplementedBy: (instance: any) => boolean;
    id: string;
    metas: Metas;
    title: Nullable<string>;
    /**
     * Screen class is a special type of route handler that provides helper
     * utilities that adds lifecycle and methods to provide content to each
     * registered surface.
     */
    constructor();
    /**
     * Fires when the screen is active. Allows a screen to perform any setup
     * that requires its DOM to be visible. Lifecycle.
     */
    activate(): void;
    /**
     * Gives the Screen a chance to cancel the navigation and stop itself from
     * activating. Can be used, for example, to prevent navigation if a user
     * is not authenticated. Lifecycle.
     * @return {boolean=|?Promise=} If returns or resolves to true,
     *     the current screen is locked and the next nagivation interrupted.
     */
    beforeActivate(): void;
    /**
     * Gives the Screen a chance to cancel the navigation and stop itself from
     * being deactivated. Can be used, for example, if the screen has unsaved
     * state. Lifecycle. Clean-up should not be preformed here, since the
     * navigation may still be cancelled. Do clean-up in deactivate.
     * @return {boolean=|?Promise=} If returns or resolves to true,
     *     the current screen is locked and the next nagivation interrupted.
     */
    beforeDeactivate(): void;
    /**
     * Gives the Screen a chance format the path before history update.
     */
    beforeUpdateHistoryPath(path: string): string;
    /**
     * Gives the Screen a chance format the state before history update.
     */
    beforeUpdateHistoryState(state: State): Nullable<State>;
    /**
     * Allows a screen to do any cleanup necessary after it has been
     * deactivated, for example cancelling outstanding requests or stopping
     * timers. Lifecycle.
     */
    deactivate(): void;
    /**
     * Dispose a screen, either after it is deactivated (in the case of a
     * non-cacheable view) or when the App is itself disposed for whatever
     * reason. Lifecycle.
     */
    disposeInternal(): void;
    /**
     * Allows a screen to evaluate scripts before the element is made visible.
     * Lifecycle.
     */
    evaluateScripts(surfaces: Surfaces): Promise<void>;
    /**
     * Allows a screen to evaluate styles before the element is made visible.
     * Lifecycle.
     */
    evaluateStyles(): Promise<void>;
    /**
     * Allows a screen to perform any setup immediately before the element is
     * made visible. Lifecycle.
     */
    flip(surfaces: Surfaces): Promise<void[]>;
    /**
     * Gets the screen id.
     */
    getId(): string;
    /**
     * Gets the screen meta tags.
     */
    getMetas(): Metas;
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
    getSurfaceContent(): void;
    /**
     * Gets the screen title.
     * @return {?string=}
     */
    getTitle(): Nullable<string>;
    /**
     * Returns all contents for the surfaces. This will pass the loaded content
     * to <code>Screen.load</code> with all information you
     * need to fulfill the surfaces. Lifecycle.
     */
    load(_path: string): Promise<void>;
    /**
     * Makes the id for the screen.
     */
    makeId_(id: number): string;
    /**
     * Sets the screen id.
     */
    setId(id: string): void;
    /**
     * Sets the screen meta tags.
     */
    setMetas(metas: Metas): void;
    /**
     * Sets the screen title.
     */
    setTitle(title: string): void;
    toString(): string;
}
export default Screen;
