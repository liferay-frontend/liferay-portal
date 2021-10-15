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
import Disposable from './Disposable';
import EventEmitter from './EventEmitter';
import type EventOrEventsList from './EventEmitter';
export interface IEventHandle {
    removeListener(): void;
}
/**
 * EventHandle utility. Holds information about an event subscription, and
 * allows removing them easily.
 * EventHandle is a Disposable, but it's important to note that the
 * EventEmitter that created it is not the one responsible for disposing it.
 * That responsibility is for the code that holds a reference to it.
 */
declare class EventHandle extends Disposable implements IEventHandle {
    private _emitter;
    private _event;
    private _listener;
    /**
     * EventHandle constructor
     */
    constructor(emitter: EventEmitter, event: EventOrEventsList, listener: Function);
    /**
     * Disposes of this instance's object references.
     */
    disposeInternal(): void;
    /**
     * Removes the listener subscription from the emitter.
     */
    removeListener(): void;
}
export default EventHandle;
