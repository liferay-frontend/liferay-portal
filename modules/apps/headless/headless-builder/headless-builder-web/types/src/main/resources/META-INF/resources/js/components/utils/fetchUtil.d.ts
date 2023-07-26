/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export declare const headers: Headers;
export declare function fetchJSON<T>({
	init,
	input,
}: {
	init?: RequestInit;
	input: RequestInfo;
}): Promise<T>;
export declare function getItems<T>({url}: {url: string}): Promise<T[]>;
export declare function updateData({
	dataToUpdate,
	onError,
	onSuccess,
	url,
}: {
	dataToUpdate: Partial<APIApplicationItem>;
	onError: (error: string) => void;
	onSuccess: voidReturn;
	url: string;
}): Promise<void>;
