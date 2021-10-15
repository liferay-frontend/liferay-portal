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
import type IEventHandle from './EventHandle';
/**
 * EventHandler utility. It's useful for easily removing a group of
 * listeners from different EventEmitter instances.
 */
declare class EventHandler extends Disposable {
    private _eventHandles;
    /**
     * EventHandler constructor
     */
    constructor();
    /**
     * Adds event handles to be removed later through the `removeAllListeners`
     * method.
     */
    add(...args: IEventHandle[]): void;
    /**
     * Disposes of this instance's object references.
     * @override
     */
    disposeInternal(): void;
    /**
     * Removes all listeners that have been added through the `add` method.
     */
    removeAllListeners(): void;
}
export default EventHandler;
