/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {forwardRef} from 'react';

// @ts-ignore

import BaseEditor from './BaseEditor';
import DefaultPreset from './presets/balloon/DefaultPreset';
import RichEditorPreset from './presets/balloon/RichEditorPreset';

const EMPTY_OBJECT = {};

interface IBaseEditor {
	contents: string;
	editorConfig: any;
	name: string;
	preset?: 'rich-text';
}

const BalloonEditor = forwardRef(
	(
		{
			contents,
			editorConfig = EMPTY_OBJECT,
			name,
			preset,
			...otherProps
		}: IBaseEditor,
		ref
	) => {
		const config: any = {
			...DefaultPreset,
			...editorConfig,
			...(preset === 'rich-text' ? RichEditorPreset : {}),
		};

		if (!config.balloonEditorEnabled) {
			return null;
		}

		return (
			<BaseEditor
				config={config}
				data={contents}
				name={name}
				onBeforeLoad={(CKEDITOR: any) => {
					CKEDITOR.ADDITIONAL_RESOURCE_PARAMS = {
						languageId: Liferay.ThemeDisplay.getLanguageId(),
					};

					CKEDITOR.disableAutoInline = true;

					CKEDITOR.getNextZIndex = function () {
						return CKEDITOR.dialog._.currentZIndex
							? CKEDITOR.dialog._.currentZIndex + 10
							: Liferay.zIndex.WINDOW + 10;
					};
				}}
				onInstanceReady={(event: any) => {
					const editor = event.editor;

					editor.setData(contents, {
						callback: () => {
							editor.resetUndo();
						},
						noSnapshot: true,
					});

					const editable = editor.editable();

					// `floatPanel` plugin requires `id` to be `cke_${editor.name}`

					editable.setAttribute('id', `cke_${editor.name}`);

					editable.attachClass('liferay-editable');

					const balloonToolbars = editor.balloonToolbars;

					if (config.toolbarText) {
						balloonToolbars.create({
							buttons: config.toolbarText,
							cssSelector: '*',
						});
					}

					if (config.toolbarImage) {
						balloonToolbars.create({
							buttons: config.toolbarImage,
							priority:
								window.CKEDITOR.plugins.balloontoolbar.PRIORITY
									.HIGH,
							widgets: 'image,image2',
						});
					}

					if (config.toolbarVideo) {
						balloonToolbars.create({
							buttons: config.toolbarVideo,
							priority:
								window.CKEDITOR.plugins.balloontoolbar.PRIORITY
									.HIGH,
							widgets: 'videoembed',
						});
					}
				}}
				ref={ref}
				type="inline"
				{...otherProps}
			/>
		);
	}
);

export default BalloonEditor;
