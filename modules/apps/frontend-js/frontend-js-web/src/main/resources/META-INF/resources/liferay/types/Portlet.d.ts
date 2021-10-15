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

export type Node = Element | String;

export interface Portlet {
	add: (options: any) => void;
	addHTML: (options: any) => void;
	close: (portlet: Node, skipConfirm: boolean, options?: any) => void;
	destroy: (portlet: Node, options?: any) => void;
	destroyComponents: (portletId: string) => void;
	isStatic: (portletId: string) => boolean;
	list: string[];
	onLoad: (options: any) => void;
	ready: (callback: Function) => void;
	readyCounter: number;
	refresh: (
		portlet: Node,
		data: any,
		mergeWithRefreshURLData: boolean
	) => void;
	refreshLayout: (portletBoundary: string) => void;
	register: (portletId: string) => void;
	registerStatic: (portletId: string) => void;
}
