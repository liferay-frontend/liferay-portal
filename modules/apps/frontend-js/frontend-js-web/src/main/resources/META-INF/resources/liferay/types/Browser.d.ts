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

export interface Browser {
	readonly acceptsGzip: () => boolean;
	readonly getMajorVersion: () => number;
	readonly getRevision: () => string;
	readonly getVersion: () => string;
	readonly isAir: () => boolean;
	readonly isChrome: () => boolean;
	readonly isEdge: () => boolean;
	readonly isFirefox: () => boolean;
	readonly isGecko: () => boolean;
	readonly isIe: () => boolean;
	readonly isIphone: () => boolean;
	readonly isLinux: () => boolean;
	readonly isMac: () => boolean;
	readonly isMobile: () => boolean;
	readonly isMozilla: () => boolean;
	readonly isOpera: () => boolean;
	readonly isRtf: () => boolean;
	readonly isSafari: () => boolean;
	readonly isSun: () => boolean;
	readonly isWebKit: () => boolean;
	readonly isWindows: () => boolean;
}
