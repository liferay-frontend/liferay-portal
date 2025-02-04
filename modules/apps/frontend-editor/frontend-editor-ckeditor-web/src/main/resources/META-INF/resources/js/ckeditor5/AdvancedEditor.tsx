/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Alignment} from '@ckeditor/ckeditor5-alignment/dist/index.js';
import {
	Bold,
	Italic,
	Strikethrough,
	Underline,
} from '@ckeditor/ckeditor5-basic-styles/dist/index.js';
import {BlockQuote} from '@ckeditor/ckeditor5-block-quote/dist/index.js';
import {EditorConfig} from '@ckeditor/ckeditor5-core/src/index.js';
import {ClassicEditor as BaseClassicEditor} from '@ckeditor/ckeditor5-editor-classic/dist/index.js';
import {Essentials} from '@ckeditor/ckeditor5-essentials/dist/index.js';
import {Font} from '@ckeditor/ckeditor5-font/dist/index.js';
import {Heading} from '@ckeditor/ckeditor5-heading/dist/index.js';
import {HorizontalLine} from '@ckeditor/ckeditor5-horizontal-line/dist/index.js';
import {GeneralHtmlSupport} from '@ckeditor/ckeditor5-html-support/dist/index.js';
import {Indent} from '@ckeditor/ckeditor5-indent/dist/index.js';
import {Link} from '@ckeditor/ckeditor5-link/dist/index.js';
import {List} from '@ckeditor/ckeditor5-list/dist/index.js';
import {MediaEmbed} from '@ckeditor/ckeditor5-media-embed/dist/index.js';
import {Paragraph} from '@ckeditor/ckeditor5-paragraph/dist/index.js';
import {CKEditor} from '@ckeditor/ckeditor5-react';
import {RemoveFormat} from '@ckeditor/ckeditor5-remove-format/dist/index.js';
import {SourceEditing} from '@ckeditor/ckeditor5-source-editing/dist/index.js';
import {Style} from '@ckeditor/ckeditor5-style/dist/index.js';
import {
	Table,
	TableCaption,
	TableProperties,
	TableToolbar,
} from '@ckeditor/ckeditor5-table/dist/index.js';
import {sub} from 'frontend-js-web';
import React from 'react';

import '../../css/ckeditor5/editor.scss';

const AdvancedEditor = ({config}: {config?: EditorConfig}) => {
	const defaultConfig: EditorConfig = {
		alignment: {
			options: ['left', 'center', 'right'],
		},
		heading: {
			options: [
				{
					class: 'lfr-editor-heading_paragraph',
					model: 'paragraph',
					title: Liferay.Language.get('normal'),
				},
				{
					class: 'lfr-editor-heading_heading1',
					model: 'heading1',
					title: sub(Liferay.Language.get('heading-x'), 1),
					view: 'h1',
				},
				{
					class: 'lfr-editor-heading_heading2',
					model: 'heading2',
					title: sub(Liferay.Language.get('heading-x'), 2),
					view: 'h2',
				},
				{
					class: 'lfr-editor-heading_heading3',
					model: 'heading3',
					title: sub(Liferay.Language.get('heading-x'), 3),
					view: 'h3',
				},
			],
		},
		htmlSupport: {
			allow: [
				{
					classes: true,
					name: /.*/,
				},
			],
		},
		plugins: [
			Alignment,
			BlockQuote,
			Bold,
			Essentials,
			Font,
			GeneralHtmlSupport,
			Heading,
			HorizontalLine,
			Indent,
			Italic,
			Link,
			List,
			MediaEmbed,
			Paragraph,
			RemoveFormat,
			SourceEditing,
			Strikethrough,
			Style,
			Table,
			TableCaption,
			TableProperties,
			TableToolbar,
			Underline,
		],
		style: {
			definitions: [
				{
					classes: ['alert', 'alert-info'],
					element: 'p',
					name: Liferay.Language.get('info-message'),
				},
				{
					classes: ['alert', 'alert-warning'],
					element: 'p',
					name: Liferay.Language.get('alert-message'),
				},
				{
					classes: ['alert', 'alert-danger'],
					element: 'p',
					name: Liferay.Language.get('error-message'),
				},
				{
					classes: ['cite'],
					element: 'cite',
					name: Liferay.Language.get('cited-work'),
				},
				{
					classes: ['code'],
					element: 'code',
					name: Liferay.Language.get('computer-code'),
				},
			],
		},
		table: {
			contentToolbar: [
				'tableColumn',
				'tableRow',
				'tableProperties',
				'toggleTableCaption',
			],
		},
		toolbar: [
			'undo',
			'redo',
			'|',
			'style',
			'|',
			'heading',
			'bold',
			'italic',
			'underline',
			'strikethrough',
			'|',
			'fontColor',
			'fontBackgroundColor',
			'|',
			'removeFormat',
			'|',
			'numberedList',
			'bulletedList',
			'|',
			'indent',
			'outdent',
			'|',
			'blockQuote',
			'|',
			'link',
			'|',
			'insertTable',
			'|',
			'mediaEmbed',
			'|',
			'horizontalLine',
			'|',
			'alignment',
			'sourceEditing',
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

export default AdvancedEditor;
