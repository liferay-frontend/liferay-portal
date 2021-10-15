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
import { Disposable } from 'frontend-js-web';
declare type Nullable<T> = T | null;
declare type Transition = (from?: Nullable<HTMLElement>, to?: Nullable<HTMLElement>) => void;
declare class Surface extends Disposable {
    static DEFAULT: string;
    static defaultTransition: Transition;
    activeChild: Nullable<HTMLElement>;
    defaultChild: Nullable<HTMLElement>;
    element: Nullable<HTMLElement>;
    id: string;
    transitionFn: Nullable<Transition>;
    /**
     * Surface class representing the references to elements on the page that
     * can potentially be updated by <code>App</code>.
     */
    constructor(id: string);
    /**
     * Adds screen content to a surface. If content hasn't been passed, see if
     * an element exists in the DOM that matches the id. By convention, the
     * element should already be nested in the right element and should have an
     * id that is a concatentation of the surface id + '-' + the screen id.
     */
    addContent(screenId: string, opt_content?: string | Element | DocumentFragment): Nullable<HTMLElement>;
    /**
     * Creates child node for the surface.
     */
    createChild(screenId: string): HTMLDivElement;
    /**
     * Gets child node of the surface.
     */
    getChild(screenId: string): HTMLElement | null;
    /**
     * Gets the surface element from element, and sets it to the el property of
     * the current instance.
     * <code>this.element</code> will be used.
     */
    getElement(): Nullable<HTMLElement>;
    /**
     * Gets the surface id.
     */
    getId(): string;
    /**
     * Gets the surface transition function.
     * See <code>Surface.defaultTransition</code>.
     */
    getTransitionFn(): Nullable<Transition>;
    /**
     * Makes the id for the element that holds content for a screen.
     */
    makeId_(screenId: string): string;
    /**
     * If default child is missing, wraps surface content as default child. If
     * surface have static content, make sure to place a
     * <code>surfaceId-default</code> element inside surface, only contents
     * inside the default child will be replaced by navigation.
     */
    maybeWrapContentAsDefault_(): void;
    /**
     * Sets the surface id.
     */
    setId(id: string): void;
    /**
     * Sets the surface transition function.
     * See <code>Surface.defaultTransition</code>.
     */
    setTransitionFn(transitionFn: Transition): void;
    /**
     * Shows screen content from a surface.
     */
    show(screenId: string): Promise<void>;
    /**
     * Removes screen content from a surface.
     */
    remove(screenId: string): void;
    toString(): string;
    /**
     * Invokes the transition function specified on <code>transition</code> attribute.
     */
    transition(from: Nullable<HTMLElement>, to: Nullable<HTMLElement>): Promise<void>;
}
export default Surface;
