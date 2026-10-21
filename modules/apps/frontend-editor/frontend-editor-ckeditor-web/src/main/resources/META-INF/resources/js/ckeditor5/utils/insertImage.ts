/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Editor} from '@ckeditor/ckeditor5-core';
import {GHSViewAttributes} from '@ckeditor/ckeditor5-html-support';
import {ImageUtils} from '@ckeditor/ckeditor5-image';
import {Config} from '@ckeditor/ckeditor5-utils';

import getFileEntryIdAttribute from './getFileEntryIdAttribute';
import getImageMarkup from './getImageMarkup';
import insertMarkup from './insertMarkup';
import {LiferayEditorConfig} from './types';

/**
 * Places an image at the current selection, or in place of the selected image.
 *
 * An image placed in place of another keeps the size the author gave the old
 * one and takes the aspect ratio of its own file. It names its own file entry,
 * or none, since Adaptive Media would otherwise go on serving the variants of
 * the image it replaced.
 */
export default function insertImage(
	editor: Editor,
	fileEntryId: number | string | undefined,
	url: string
): void {
	const config: Config<LiferayEditorConfig> = editor.config;

	const image = editor.model.document.selection.getSelectedElement();

	if (
		!editor.plugins.has(ImageUtils) ||
		!editor.plugins.get(ImageUtils).isImage(image)
	) {
		insertMarkup(editor, getImageMarkup(config, fileEntryId, url));

		return;
	}

	editor.model.change((writer) => {
		editor.execute('replaceImageSource', {source: url});

		writer.removeAttribute('resizedHeight', image);

		const fileEntryIdAttribute = getFileEntryIdAttribute(
			config,
			fileEntryId
		);

		if (!fileEntryIdAttribute) {
			return;
		}

		const htmlImgAttributes: GHSViewAttributes = {
			...(image.getAttribute('htmlImgAttributes') as GHSViewAttributes),
		};

		const attributes = {...htmlImgAttributes.attributes};

		delete attributes[fileEntryIdAttribute.name];

		if (fileEntryIdAttribute.value) {
			attributes[fileEntryIdAttribute.name] = fileEntryIdAttribute.value;
		}

		if (Object.keys(attributes).length) {
			htmlImgAttributes.attributes = attributes;
		}
		else {
			delete htmlImgAttributes.attributes;
		}

		if (Object.keys(htmlImgAttributes).length) {
			writer.setAttribute('htmlImgAttributes', htmlImgAttributes, image);
		}
		else {
			writer.removeAttribute('htmlImgAttributes', image);
		}
	});
}
