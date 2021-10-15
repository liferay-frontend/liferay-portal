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

import type {AUI} from './liferay/types/AUI';
import type {Browser} from './liferay/types/Browser';
import type {DOMTaskRunner} from './liferay/types/DOMTaskRunner';
import type {Data} from './liferay/types/Data';
import type {Language} from './liferay/types/Language';
import type {Portlet} from './liferay/types/Portlet';
import type {PortletKeys} from './liferay/types/PortletKeys';
import type {PropsValues} from './liferay/types/PropsValues';
import type {Service} from './liferay/types/Service';
import type {ThemeDisplay} from './liferay/types/ThemeDisplay';
import type {SubmitForm, Util} from './liferay/types/Util';
import type {EventCallback, EventHandler} from './liferay/types/events';
declare global {
	export interface Liferay {
		AUI: AUI;
		Browser: Browser;
		Data: Data;
		DOMTaskRunner: DOMTaskRunner;
		Language: Language;
		Portlet: Portlet;
		PortletKeys: PortletKeys;
		PropsValues: PropsValues;
		Service: Service;
		ThemeDisplay: ThemeDisplay;
		Util: Util;

		readonly authToken: string;
		readonly currentURL: string;
		readonly currentURLEncoded: string;

		readonly after: EventHandler;
		readonly before: EventHandler;
		readonly destroyComponents: (
			filterFn: (component: any, componentConfig: any) => boolean
		) => void;
		readonly fire: (eventName: string, data?: any) => void;
		readonly initComponentCache: () => void;
		readonly on: EventHandler;
		readonly once: EventHandler;
		readonly onceAfter: (
			eventName: string,
			callback: EventCallback
		) => void;
	}

	const Liferay: Liferay;
	const submitForm: SubmitForm;
	const themeDisplay: ThemeDisplay;

	interface Window {
		Liferay: Liferay;
		themeDisplay: ThemeDisplay;
	}
}
