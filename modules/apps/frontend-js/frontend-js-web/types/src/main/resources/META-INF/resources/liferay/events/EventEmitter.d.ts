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
import EventHandle from './EventHandle';
interface EventFacade {
    preventedDefault?: boolean;
    preventDefault: () => void;
    target: EventEmitter;
    type: string;
}
interface ListenerObject {
    default?: boolean;
    fn: Function;
    origin?: Function;
}
declare type EventHandler = Function | ListenerObject;
export declare type EventOrEventsList = string | string[];
/**
 * EventEmitter utility.
 */
declare class EventEmitter extends Disposable {
    private _events;
    private _shouldUseFacade;
    private _listenerHandlers;
    constructor();
    /**
     * Adds a handler to given holder variable. If the holder doesn't have a
     * value yet, it will receive the handler directly. If the holder is an array,
     * the value will just be added to it. Otherwise, the holder will be set to a
     * new array containing its previous value plus the new handler.
     */
    _addHandler(holder: any[] | null, handler: EventHandler): any[] | null;
    /**
     * Adds a listener to the end of the listeners array for the specified events.
     *
     * `defaultListener` is a flag indicating if this listener is a default action
     * for this event. Default actions are run last, and only if no previous listener
     * call `preventDefault()` on the received event facade.
     */
    addListener(event: EventOrEventsList, listener: Function, defaultListener?: boolean): EventHandle;
    /**
     * Adds a listener to the end of the listeners array for a single event.
     *
     * `defaultListener` is a flag indicating if this listener is a default action
     * for this event. Default actions are run last, and only if no previous listener
     * call `preventDefault()` on the received event facade.
     */
    _addSingleListener(event: string, listener: Function, defaultListener?: boolean, origin?: Function): void;
    /**
     * Builds facade for the given event.
     */
    _buildFacade(event: string): EventFacade | undefined;
    /**
     * Disposes of this instance's object references.
     * @override
     */
    disposeInternal(): void;
    /**
     * Execute each of the listeners in order with the supplied arguments.
     */
    emit(event: string): boolean;
    /**
     * Gets the listener objects for the given event, if there are any.
     */
    _getRawListeners(event: string): any[] | undefined;
    /**
     * Gets the configuration option which determines if an event facade should
     * be sent as a param of listeners when emitting events. If set to true, the
     * facade will be passed as the first argument of the listener.
     * @return {boolean}
     */
    getShouldUseFacade(): boolean;
    /**
     * Returns an array of listeners for the specified event.
     * @return {Array} Array of listeners.
     */
    listeners(event: string): any[] | undefined;
    /**
     * Adds a listener that will be invoked a fixed number of times for the
     * events. After each event is triggered the specified amount of times, the
     * listener is removed for it.
     */
    many(event: EventOrEventsList, amount: number, listener: Function): EventHandle;
    /**
     * Adds a listener that will be invoked a fixed number of times for a single
     * event. After the event is triggered the specified amount of times, the
     * listener is removed.
     */
    _many(event: string, amount: number, listener: Function): void;
    /**
     * Checks if a listener object matches the given listener function. To match,
     * it needs to either point to that listener or have it as its origin.
     */
    _matchesListener(listenerObj: EventHandler, listener: Function): boolean | undefined;
    /**
     * Removes a listener for the specified events.
     * Caution: changes array indices in the listener array behind the listener.
     */
    off(event: EventOrEventsList, listener: Function): this;
    /**
     * Adds a listener to the end of the listeners array for the specified events.
     * @param {!(Array|string)} events
     * @param {!Function} listener
     * @return {!EventHandle} Can be used to remove the listener.
     */
    on(events: EventOrEventsList, listener: Function): EventHandle;
    /**
     * Adds handler that gets triggered when an event is listened to on this
     * instance.
     */
    onListener(handler: Function): void;
    /**
     * Adds a one time listener for the events. This listener is invoked only the
     * next time each event is fired, after which it is removed.
     */
    once(events: EventOrEventsList, listener: Function): EventHandle;
    /**
     * Removes all listeners, or those of the specified events. It's not a good
     * idea to remove listeners that were added elsewhere in the code,
     * especially when it's on an emitter that you didn't create.
     */
    removeAllListeners(event?: EventOrEventsList): this;
    /**
     * Removes all listener objects from the given array that match the given
     * listener function.
     */
    _removeMatchingListenerObjs(listenerObjs: EventHandler[], listener: Function): (Function | ListenerObject)[] | null;
    /**
     * Removes a listener for the specified events.
     * Caution: changes array indices in the listener array behind the listener.
     */
    removeListener(events: EventOrEventsList, listener: Function): this;
    /**
     * Runs the handlers when an event is listened to.
     */
    _runListenerHandlers(event: string): void;
    /**
     * Runs the given listeners.
     */
    _runListeners(listeners: ListenerObject[], args: any[], facade?: EventFacade): void;
    /**
     * Sets the configuration option which determines if an event facade should
     * be sent as a param of listeners when emitting events. If set to true, the
     * facade will be passed as the first argument of the listener.
     */
    setShouldUseFacade(shouldUseFacade: boolean): this;
    /**
     * Converts the parameter to an array if only one event is given. Reuses the
     * same array each time this conversion is done, to avoid using more memory
     * than necessary.
     */
    _toEventsArray(events: EventOrEventsList): string[];
    /**
     * Checks if the given listener is valid, throwing an exception when it's not.
     */
    _validateListener(listener: Function): void;
}
export default EventEmitter;
