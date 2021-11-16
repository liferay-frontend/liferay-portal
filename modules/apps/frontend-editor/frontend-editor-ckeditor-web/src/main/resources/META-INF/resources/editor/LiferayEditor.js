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

class LiferayEditor {
	constructor(
		contents,

		// editor,

		initMethod,
		name,

		// onBlurMethod,
		onChangeMethod,
		// onFocusMethod,
		// onInitMethod

	) {
		this.contents = contents;

		// this.editor = editor;

		this.initMethod = initMethod;
		this.name = name;

		// this.onBlurMethod = onBlurMethod;

		this.onChangeMethod = onChangeMethod;

		// this.onFocusMethod = onFocusMethod;
		// this.onInitMethod = onInitMethod;

		this.instanceReady = false;

		this.ckEditor = CKEDITOR.instances[this.name];

		CKEDITOR.on('instanceReady', this.handleInstanceReady.bind(this));
	}

	handleInstanceReady({editor}) {
		this.instanceReady = true;

		this.ckEditor = editor;
	}

	getCkData() {
		if (!this.instanceReady) {
			return this.getInitialContent();
		}

		let data = this.ckEditor.getData();

		if (CKEDITOR.env.gecko && CKEDITOR.tools.trim(data) == '<br />') {
			data = '';
		}

		return data;
	}

	getHTML() {
		return this.getCkData();
	}

	getInitialContent() {
		if (this.initMethod) {
			return this.initMethod();
		}

		return this.contents;
	}

	// static onChangeContentEditor(callback) {

	// 	/**
	//      * @TODO - Needs to test
	//      * window[`${namespace}onChangeContentEditor`] = (html) => {
	//             this.setDescription(html);
	//         };
	//      */

	// 	callback(this);
	// }

	getText() {
		if (!this.instanceReady) {
			return this.getInitialContent();
		}

		return this.ckEditor.editable().getText();
	}
}

export default LiferayEditor;
