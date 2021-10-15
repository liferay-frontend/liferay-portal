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
import EventScreen from './EventScreen';
/**
 * ActionURLScreen
 *
 * Inherits from {@link EventScreen|EventScreen}. The screen used for all
 * requests made to ActionURLs.
 */
declare class ActionURLScreen extends EventScreen {
    httpMethod: string;
    constructor();
    /**
     * @inheritDoc
     * Ensures that an action request (form submission) redirect's final
     * URL has the lifecycle RENDER `p_p_lifecycle=0`
     */
    getRequestPath(): string | null;
}
export default ActionURLScreen;
