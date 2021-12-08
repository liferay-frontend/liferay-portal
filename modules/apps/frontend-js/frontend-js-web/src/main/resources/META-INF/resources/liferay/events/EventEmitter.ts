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
	preventDefault: () => void;
	preventedDefault?: boolean;
	target: EventEmitter;
	type: string;
}

interface ListenerObject {
	default?: boolean;
	fn: Function;
	origin?: Function;
}

type EventHandler = Function | ListenerObject;

export type EventOrEventsList = string | string[];

type ListenerHandler = Function;

const singleArray = [''];

/**
 * EventEmitter utility.
 */
class EventEmitter extends Disposable {
	private _events: Record<string, any[] | null>;
	private _shouldUseFacade: boolean;
	private _listenerHandlers: ListenerHandler[] | null;

	constructor() {
		super();

		/**
		 * Holds event listeners scoped by event type.
		 */
		this._events = {};

		/**
		 * Handlers that are triggered when an event is listened to.
		 */
		this._listenerHandlers = [];

		/**
		 * Configuration option which determines if an event facade should be sent
		 * as a param of listeners when emitting events. If set to true, the facade
		 * will be passed as the first argument of the listener.
		 */
		this._shouldUseFacade = false;
	}

	/**
	 * Adds a handler to given holder variable. If the holder doesn't have a
	 * value yet, it will receive the handler directly. If the holder is an array,
	 * the value will just be added to it. Otherwise, the holder will be set to a
	 * new array containing its previous value plus the new handler.
	 */
	_addHandler(holder: any[] | null, handler: EventHandler) {
		if (!holder) {
			holder = handler as any;
		} else {
			if (!Array.isArray(holder)) {
				holder = [holder];
			}
			holder.push(handler);
		}

		return holder;
	}

	/**
	 * Adds a listener to the end of the listeners array for the specified events.
	 *
	 * `defaultListener` is a flag indicating if this listener is a default action
	 * for this event. Default actions are run last, and only if no previous listener
	 * call `preventDefault()` on the received event facade.
	 */
	addListener(
		event: EventOrEventsList,
		listener: Function,
		defaultListener?: boolean
	): EventHandle {
		this._validateListener(listener);

		const events = this._toEventsArray(event);

		for (let i = 0; i < events.length; i++) {
			this._addSingleListener(events[i], listener, defaultListener);
		}

		// @ts-ignore
		return new EventHandle(this, event, listener);
	}

	/**
	 * Adds a listener to the end of the listeners array for a single event.
	 *
	 * `defaultListener` is a flag indicating if this listener is a default action
	 * for this event. Default actions are run last, and only if no previous listener
	 * call `preventDefault()` on the received event facade.
	 */
	_addSingleListener(
		event: string,
		listener: Function,
		defaultListener?: boolean,
		origin?: Function
	) {
		this._runListenerHandlers(event);

		this._events = this._events || {};

		this._events[event] = this._addHandler(
			this._events[event],
			defaultListener || origin
				? {
						default: defaultListener,
						fn: listener,
						origin,
				  }
				: listener
		);
	}

	/**
	 * Builds facade for the given event.
	 */
	_buildFacade(event: string): EventFacade | undefined {
		if (this.getShouldUseFacade()) {
			const facade: EventFacade = {
				preventDefault() {
					facade.preventedDefault = true;
				},
				target: this,
				type: event,
			};

			return facade;
		}
	}

	/**
	 * Disposes of this instance's object references.
	 * @override
	 */
	disposeInternal() {
		this._events = {};
	}

	/**
	 * Execute each of the listeners in order with the supplied arguments.
	 */
	emit(event: string, options?: object): boolean {
		const listeners = this._getRawListeners(event) || [];

		if (listeners.length === 0) {
			return false;
		}

		const args = Array.prototype.slice.call(arguments, 1);

		this._runListeners(listeners, args, this._buildFacade(event));

		return true;
	}

	/**
	 * Gets the listener objects for the given event, if there are any.
	 */
	_getRawListeners(event: string) {
		const directListeners = toArray(this._events && this._events[event]);

		return directListeners?.concat(
			toArray(this._events && this._events['*'])
		);
	}

	/**
	 * Gets the configuration option which determines if an event facade should
	 * be sent as a param of listeners when emitting events. If set to true, the
	 * facade will be passed as the first argument of the listener.
	 * @return {boolean}
	 */
	getShouldUseFacade() {
		return this._shouldUseFacade;
	}

	/**
	 * Returns an array of listeners for the specified event.
	 * @return {Array} Array of listeners.
	 */
	listeners(event: string) {
		return this._getRawListeners(event)?.map((listener) =>
			listener.fn ? listener.fn : listener
		);
	}

	/**
	 * Adds a listener that will be invoked a fixed number of times for the
	 * events. After each event is triggered the specified amount of times, the
	 * listener is removed for it.
	 */
	many(event: EventOrEventsList, amount: number, listener: Function) {
		const events = this._toEventsArray(event);

		for (let i = 0; i < events.length; i++) {
			this._many(events[i], amount, listener);
		}

		// @ts-ignore
		return new EventHandle(this, event, listener);
	}

	/**
	 * Adds a listener that will be invoked a fixed number of times for a single
	 * event. After the event is triggered the specified amount of times, the
	 * listener is removed.
	 */
	_many(event: string, amount: number, listener: Function) {
		const self = this;

		if (amount <= 0) {
			return;
		}

		/**
		 *
		 */
		function handlerInternal() {
			if (--amount === 0) {
				self.removeListener(event, handlerInternal);
			}
			listener.apply(self, arguments);
		}

		self._addSingleListener(event, handlerInternal, false, listener);
	}

	/**
	 * Checks if a listener object matches the given listener function. To match,
	 * it needs to either point to that listener or have it as its origin.
	 */
	_matchesListener(listenerObj: EventHandler, listener: Function) {
		return typeof listenerObj === 'function'
			? listenerObj === listener
			: listenerObj.origin && listenerObj.origin === listener;
	}

	/**
	 * Removes a listener for the specified events.
	 * Caution: changes array indices in the listener array behind the listener.
	 */
	off(event: EventOrEventsList, listener: Function) {
		this._validateListener(listener);
		if (!this._events) {
			return this;
		}

		const events = this._toEventsArray(event);
		for (let i = 0; i < events.length; i++) {
			this._events[events[i]] = this._removeMatchingListenerObjs(
				toArray(this._events[events[i]]) as any[],
				listener
			);
		}

		return this;
	}

	/**
	 * Adds a listener to the end of the listeners array for the specified events.
	 * @param {!(Array|string)} events
	 * @param {!Function} listener
	 * @return {!EventHandle} Can be used to remove the listener.
	 */
	on(events: EventOrEventsList, listener: Function) {
		return this.addListener(events, listener);
	}

	/**
	 * Adds handler that gets triggered when an event is listened to on this
	 * instance.
	 */
	onListener(handler: Function) {
		this._listenerHandlers = this._addHandler(
			this._listenerHandlers,
			handler
		);
	}

	/**
	 * Adds a one time listener for the events. This listener is invoked only the
	 * next time each event is fired, after which it is removed.
	 */
	once(events: EventOrEventsList, listener: Function) {
		return this.many(events, 1, listener);
	}

	/**
	 * Removes all listeners, or those of the specified events. It's not a good
	 * idea to remove listeners that were added elsewhere in the code,
	 * especially when it's on an emitter that you didn't create.
	 */
	removeAllListeners(event?: EventOrEventsList) {
		if (this._events) {
			if (event) {
				const events = this._toEventsArray(event);
				for (let i = 0; i < events.length; i++) {
					this._events[events[i]] = [];
				}
			} else {
				this._events = {};
			}
		}

		return this;
	}

	/**
	 * Removes all listener objects from the given array that match the given
	 * listener function.
	 */
	_removeMatchingListenerObjs(
		listenerObjs: EventHandler[],
		listener: Function
	) {
		const finalListeners = [];

		for (let i = 0; i < listenerObjs.length; i++) {
			if (!this._matchesListener(listenerObjs[i], listener)) {
				finalListeners.push(listenerObjs[i]);
			}
		}

		return finalListeners.length > 0 ? finalListeners : null;
	}

	/**
	 * Removes a listener for the specified events.
	 * Caution: changes array indices in the listener array behind the listener.
	 */
	removeListener(events: EventOrEventsList, listener: Function) {
		return this.off(events, listener);
	}

	/**
	 * Runs the handlers when an event is listened to.
	 */
	_runListenerHandlers(event: string) {
		let handlers = this._listenerHandlers;

		if (handlers) {
			handlers = toArray(handlers) as Function[];

			for (let i = 0; i < handlers.length; i++) {
				handlers[i](event);
			}
		}
	}

	/**
	 * Runs the given listeners.
	 */
	_runListeners(
		listeners: ListenerObject[],
		args: any[],
		facade?: EventFacade
	) {
		if (facade) {
			args.push(facade);
		}

		const defaultListeners = [];

		for (let i = 0; i < listeners.length; i++) {
			const listener = listeners[i].fn || listeners[i];

			if (listeners[i].default) {
				defaultListeners.push(listener);
			} else {
				listener.apply(this, args);
			}
		}
		if (!facade || !facade.preventedDefault) {
			for (let j = 0; j < defaultListeners.length; j++) {
				defaultListeners[j].apply(this, args);
			}
		}
	}

	/**
	 * Sets the configuration option which determines if an event facade should
	 * be sent as a param of listeners when emitting events. If set to true, the
	 * facade will be passed as the first argument of the listener.
	 */
	setShouldUseFacade(shouldUseFacade: boolean) {
		this._shouldUseFacade = shouldUseFacade;

		return this;
	}

	/**
	 * Converts the parameter to an array if only one event is given. Reuses the
	 * same array each time this conversion is done, to avoid using more memory
	 * than necessary.
	 */
	_toEventsArray(events: EventOrEventsList) {
		if (typeof events === 'string') {
			singleArray[0] = events;
			events = singleArray;
		}

		return events;
	}

	/**
	 * Checks if the given listener is valid, throwing an exception when it's not.
	 */
	_validateListener(listener: Function) {
		if (typeof listener !== 'function') {
			throw new TypeError('Listener must be a function');
		}
	}
}

/**
 * Converts to an array
 */
function toArray<T>(val: T) {
	if (!val) {
		return [];
	}

	return Array.isArray(val) ? (val as T) : [val];
}

export default EventEmitter;
