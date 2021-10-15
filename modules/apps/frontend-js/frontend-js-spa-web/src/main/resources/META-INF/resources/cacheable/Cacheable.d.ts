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
declare class Cacheable extends Disposable {
    cache: string | null;
    cacheable: boolean;
    /**
     * Abstract class for defining cacheable behavior.
     */
    constructor();
    /**
     * Adds content to the cache.
     */
    addCache(content: string): this;
    /**
     * Clears the cache.
     * @chainable
     */
    clearCache(): this;
    /**
     * Disposes of this instance's object references.
     * @override
     */
    disposeInternal(): void;
    /**
     * Gets the cached content.
     */
    getCache(): string | null;
    /**
     * Whether the class is cacheable.
     */
    isCacheable(): boolean;
    /**
     * Sets whether the class is cacheable.
     */
    setCacheable(cacheable: boolean): void;
}
export default Cacheable;
