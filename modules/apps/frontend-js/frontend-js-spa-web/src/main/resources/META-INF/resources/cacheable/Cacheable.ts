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

import {Disposable} from 'frontend-js-web';

class Cacheable extends Disposable {
	cache: string | null;
	cacheable: boolean;

	/**
	 * Abstract class for defining cacheable behavior.
	 */
	constructor() {
		super();

		/**
		 * Holds the cached data.
		 */
		this.cache = null;

		/**
		 * Holds whether class is cacheable.
		 */
		this.cacheable = false;
	}

	/**
	 * Adds content to the cache.
	 */
	addCache(content: string) {
		if (this.cacheable) {
			this.cache = content;
		}

		return this;
	}

	/**
	 * Clears the cache.
	 * @chainable
	 */
	clearCache() {
		this.cache = null;

		return this;
	}

	/**
	 * Disposes of this instance's object references.
	 * @override
	 */
	disposeInternal() {
		this.clearCache();
	}

	/**
	 * Gets the cached content.
	 */
	getCache() {
		return this.cache;
	}

	/**
	 * Whether the class is cacheable.
	 */
	isCacheable() {
		return this.cacheable;
	}

	/**
	 * Sets whether the class is cacheable.
	 */
	setCacheable(cacheable: boolean) {
		if (!cacheable) {
			this.clearCache();
		}

		this.cacheable = cacheable;
	}
}

export default Cacheable;
