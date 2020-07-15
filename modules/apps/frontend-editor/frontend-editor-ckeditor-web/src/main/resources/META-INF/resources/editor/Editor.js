/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import CKEditor from 'ckeditor4-react';
import React, {useEffect, useState} from 'react';

const CKEDITOR_BASEPATH = '/o/frontend-editor-ckeditor-web/ckeditor/';
const CKEDITOR_PATH = `${CKEDITOR_BASEPATH}ckeditor.js`;

const Editor = React.forwardRef((props, ref) => {
	const [initialized, setInitialized] = useState(!!window.CKEDITOR);

	useEffect(() => {
		let script;

		const destroyGlobalCkEditor = () => {
			if (
				window.CKEDITOR &&
				Object.keys(window.CKEDITOR.instances).length === 0
			) {
				window.CKEDITOR = undefined;

				Liferay.detach('beforeScreenFlip', destroyGlobalCkEditor);
			}
		};

		if (!initialized) {
			script = document.createElement('script');

			script.setAttribute('data-senna-track', 'temporary');
			script.src = CKEDITOR_PATH;

			script.onload = () => {
				setInitialized(true);
			};

			document.head.appendChild(script);

			Liferay.on('beforeScreenFlip', destroyGlobalCkEditor);
		}

		return () => {
			if (script) {
				document.head.removeChild(script);
			}
		};
	}, [initialized]);

	return initialized && <CKEditor ref={ref} {...props} />;
});

CKEditor.editorUrl = CKEDITOR_PATH;
window.CKEDITOR_BASEPATH = CKEDITOR_BASEPATH;

export {Editor};
