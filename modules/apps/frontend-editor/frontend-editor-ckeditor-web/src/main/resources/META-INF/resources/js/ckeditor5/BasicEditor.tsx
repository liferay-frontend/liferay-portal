/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	Bold,
	Italic,
	Underline,
} from '@ckeditor/ckeditor5-basic-styles/dist/index.js';
import {EditorConfig} from '@ckeditor/ckeditor5-core/src/index.js';
import {ClassicEditor as BaseClassicEditor} from '@ckeditor/ckeditor5-editor-classic/dist/index.js';
import {Essentials} from '@ckeditor/ckeditor5-essentials/dist/index.js';
import {Link} from '@ckeditor/ckeditor5-link/dist/index.js';
import {List} from '@ckeditor/ckeditor5-list/dist/index.js';
import {Paragraph} from '@ckeditor/ckeditor5-paragraph/dist/index.js';
import {CKEditor} from '@ckeditor/ckeditor5-react';
import React from 'react';

import '../../css/ckeditor5/editor.scss';

const BasicEditor = ({config}: {config?: EditorConfig}) => {
	const defaultConfig: EditorConfig = {
		plugins: [Bold, Essentials, Italic, Link, List, Paragraph, Underline],
		toolbar: [
			'undo',
			'redo',
			'|',
			'bold',
			'italic',
			'underline',
			'|',
			'numberedList',
			'bulletedList',
			'|',
			'link',
		],
	};

	if (!Liferay.FeatureFlags['LPD-11235']) {
		return <></>;
	}

	return (
		<CKEditor
			config={{
				...defaultConfig,
				...config,
			}}
			editor={BaseClassicEditor}
		/>
	);
};

export default BasicEditor;
