/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function getImageHTML(
	value: string,
	fileEntryAttributeName?: string
): string | null {
	let fileEntryId;
	let url;

	try {
		({fileEntryId, url} = JSON.parse(value));
	}
	catch (error) {
		url = value;
	}

	if (!url) {
		return null;
	}

	if (fileEntryAttributeName && fileEntryId) {
		return `<img ${fileEntryAttributeName}="${fileEntryId}" src="${url}">`;
	}

	return `<img src="${url}">`;
}
