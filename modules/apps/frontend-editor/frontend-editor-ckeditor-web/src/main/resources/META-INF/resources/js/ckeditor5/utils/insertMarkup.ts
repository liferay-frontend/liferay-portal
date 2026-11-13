/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Editor} from '@ckeditor/ckeditor5-core';

/**
 * Places markup in the content at the current selection.
 *
 * The editor holds content as a model of its own rather than as markup, so
 * what is written has to be read into that model before it can be placed,
 * which also drops whatever the editor is not configured to keep.
 */
export default function insertMarkup(editor: Editor, markup: string): void {
	const viewFragment = editor.data.processor.toView(markup);

	const modelFragment = editor.data.toModel(viewFragment);

	editor.model.insertContent(modelFragment);
}
