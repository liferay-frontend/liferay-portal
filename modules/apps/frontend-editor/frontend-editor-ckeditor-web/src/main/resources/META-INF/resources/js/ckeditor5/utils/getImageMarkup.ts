/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Config} from '@ckeditor/ckeditor5-utils';

import getFileEntryIdAttribute from './getFileEntryIdAttribute';
import {LiferayEditorConfig} from './types';

/**
 * Returns the markup for an image placed in the content.
 *
 * This is the contract Adaptive Media reads the content by: an image names
 * the file entry it came from, and the content transformer serves the variant
 * the company asks for in its place. An image written without it is served as
 * it was uploaded, for as long as the content lives, so every selector that
 * places an image is to build its markup here rather than spell it out.
 */
export default function getImageMarkup(
	config: Config<LiferayEditorConfig>,
	fileEntryId: number | string | undefined,
	url: string
): string {
	const fileEntryIdAttribute = getFileEntryIdAttribute(config, fileEntryId);

	if (!fileEntryIdAttribute?.value) {
		return `<img src="${url}">`;
	}

	return `<img src="${url}" ${fileEntryIdAttribute.name}="${fileEntryIdAttribute.value}">`;
}
