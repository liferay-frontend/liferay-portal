/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {forwardRef, useEffect, useRef} from 'react';

import richTextPreset from './presets/richTextPreset';

interface IAlloyEditor {
	ariaLabel: string;
	className: string;
	contents: string;
	editorConfig: any;
	name: string;
	preset?: 'rich-text';
}

const AlloyEditorComponent = forwardRef(
	(
		{
			ariaLabel,
			className,
			contents,
			editorConfig,
			name,
			preset,
		}: IAlloyEditor,
		ref
	) => {
		const editorRef = useRef(null);

		useEffect(() => {
			CKEDITOR.ADDITIONAL_RESOURCE_PARAMS = {
				languageId: Liferay.ThemeDisplay.getLanguageId(),
			};
			CKEDITOR.disableAutoInline = true;
			CKEDITOR.dtd.$removeEmpty.i = 0;
			CKEDITOR.dtd.$removeEmpty.span = 0;

			const editor = AlloyEditor.editable(editorRef.current, {
				...editorConfig,
				...(preset === 'rich-text' ? richTextPreset : {}),
			});

			const nativeEditor = editor.get('nativeEditor');

			nativeEditor.on('instanceReady', () => {
				if (contents) {
					nativeEditor.setData(contents);
				}

				nativeEditor.focus();
			});

			// eslint-disable-next-line react-compiler/react-compiler
			// eslint-disable-next-line react-hooks/exhaustive-deps
		}, []);

		return (
			<div
				aria-label={ariaLabel}
				className={`alloy-editor-container ${className}`}
				id={`${name}Container`}
				ref={editorRef}
			></div>
		);
	}
);

export default AlloyEditorComponent;
