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
class EventHandler extends Disposable {
	private _eventHandles: IEventHandle[];

	/**
	 * EventHandler constructor
	 */
	constructor() {
		super();

		/**
		 * An array that holds the added event handles, so the listeners can be
		 * removed later.
		 */
		this._eventHandles = [];
	}

	/**
	 * Adds event handles to be removed later through the `removeAllListeners`
	 * method.
	 */
	add(...args: IEventHandle[]) {
		for (let i = 0; i < args.length; i++) {
			this._eventHandles?.push(args[i]);
		}
	}

	/**
	 * Disposes of this instance's object references.
	 * @override
	 */
	disposeInternal() {
		this._eventHandles = [];
	}

	/**
	 * Removes all listeners that have been added through the `add` method.
	 */
	removeAllListeners() {
		for (let i = 0; i < this._eventHandles.length; i++) {
			this._eventHandles[i].removeListener();
		}

		this._eventHandles = [];
	}
}

export default EventHandler;
