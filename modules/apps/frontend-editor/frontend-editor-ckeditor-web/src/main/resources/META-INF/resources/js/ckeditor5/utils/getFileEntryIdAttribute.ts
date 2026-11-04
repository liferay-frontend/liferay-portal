/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Config} from '@ckeditor/ckeditor5-utils';

import {LiferayEditorConfig} from './types';

interface FileEntryIdAttribute {
	name: string;
	value: string | null;
}

/**
 * Returns the attribute that names the file entry an image came from, with no
 * value when there is no file entry to name, or nothing at all when the editor
 * is not configured to name one.
 *
 * Adaptive Media reads this attribute from the stored content to know which
 * file entry to serve the variants of, so an image inserted without it is
 * never adapted. The attribute's own name is configured rather than spelled
 * here, since the one that writes it and the one that reads it have to agree.
 */
export default function getFileEntryIdAttribute(
	config: Config<LiferayEditorConfig>,
	fileEntryId: number | string | undefined
): FileEntryIdAttribute | null {
	const name = config.get('adaptiveMediaFileEntryAttributeName');

	if (!name) {
		return null;
	}

	const id = Number(fileEntryId);

	if (
		fileEntryId === null ||
		fileEntryId === undefined ||
		!Number.isInteger(id) ||
		id <= 0
	) {
		return {name, value: null};
	}

	return {name, value: String(id)};
}
