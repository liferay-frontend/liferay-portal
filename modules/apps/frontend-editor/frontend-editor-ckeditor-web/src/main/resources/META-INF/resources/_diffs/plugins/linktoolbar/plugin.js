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

(function () {
	'use strict';

	var pluginName = 'linktoolbar';

	if (!CKEDITOR.plugins.get(pluginName)) {
		CKEDITOR.plugins.add(pluginName, {
			_createToolbar() {
				if (!this._toolbar) {
					var instance = this;

					var editor = instance.editor;

					instance._toolbar = new CKEDITOR.ui.balloonToolbar(editor);

					var unlinkButton = new CKEDITOR.ui.balloonToolbarButton({
						click() {
							var imageWidget =
								instance.editor.widgets.selected[0];
							if (!imageWidget || imageWidget.name !== 'image') {
								return;
							}

							if (
								Object.prototype.hasOwnProperty.call(
									imageWidget.data,
									'link'
								)
							) {
								imageWidget.setData('link', {url: ''});

								var linkInput = instance._toolbar.getItem(
									'linkInput'
								);
								linkInput.clear();
								instance._toolbar.hide();
							}
						},
						icon: 'unlink',
						title: editor.lang.undo.undo,
					});

					instance._toolbar.addItem('unlinkButton', unlinkButton);

					var targetSelect = new CKEDITOR.ui.balloonToolbarSelect({
						items: [
							{
								label: editor.lang.common.optionDefault,
								value: '',
							},
							{
								label: editor.lang.common.targetSelf,
								value: '_self',
							},
							{
								label: editor.lang.common.targetNew,
								value: '_blank',
							},
							{
								label: editor.lang.common.targetParent,
								value: '_parent',
							},
							{
								label: editor.lang.common.targetTop,
								value: '_top',
							},
						],
						name: editor.lang.common.target,
					});

					instance._toolbar.addItem('targetSelect', targetSelect);

					var linkInput = new CKEDITOR.ui.balloonToolbarTextInput({
						placeholder: editor.lang.link.title,
					});

					linkInput.on('cancel', () => {
						instance._toolbar.hide();

						instance._parentToolbar.show();
					});

					var changeOrClickHandler = instance._onOkButtonClick.bind(
						instance
					);

					linkInput.on('change', () => {
						changeOrClickHandler();
					});

					instance._toolbar.addItem('linkInput', linkInput);

					var okButton = new CKEDITOR.ui.balloonToolbarButton({
						click: changeOrClickHandler,
						icon: 'check',
						title: editor.lang.common.ok,
					});

					instance._toolbar.addItem('okButton', okButton);

					var folderButton = new CKEDITOR.ui.balloonToolbarButton({
						click() {
							instance._toolbar.hide();
							instance._parentToolbar.hide();

							instance.editor.execCommand('imageselector');
						},
						icon: 'folder',
						title: editor.lang.common.browseServer,
					});

					instance._toolbar.addItem('folderButton', folderButton);
				}

				return this._toolbar;
			},

			_onOkButtonClick() {
				var imageWidget = this.editor.widgets.selected[0];

				if (!imageWidget || imageWidget.name !== 'image') {
					return;
				}

				var linkInput = this._toolbar.getItem('linkInput');

				if (!linkInput.value) {

					// TODO: Warn the user about missing URL?

					this._toolbar.hide();

					return;
				}

				var newData = Object.assign(imageWidget.data, {
					link: {
						url: linkInput.value,
					},
				});

				var instance = this;

				imageWidget.shiftState({
					deflate() {},
					element: imageWidget.element,
					inflate() {

						// Insert target and rel attributes

						var wrapperElement = imageWidget.wrapper;
						var linkElement = wrapperElement.findOne('a');
						if (linkElement) {
							var targetSelect = instance._toolbar.getItem(
								'targetSelect'
							);

							if (targetSelect.value !== 'default') {
								linkElement.setAttribute(
									'target',
									targetSelect.value
								);
							}
							linkElement.setAttribute('rel', 'noopener');
						}
					},
					newData,
					oldData: imageWidget.oldData,
					widget: imageWidget,
				});

				this._toolbar.hide();

				imageWidget.focus();
				imageWidget.element.focus();
			},
			_onSelectionChange() {
				if (this._toolbar) {
					this._toolbar.hide();
				}
			},
			_parentToolbar: null,

			_toolbar: null,

			init(editor) {
				var instance = this;

				instance.editor = editor;

				editor.on(
					'selectionChange',
					this._onSelectionChange.bind(this)
				);

				editor.addCommand('linkToolbar', {
					exec(editor) {
						var toolbar = instance._createToolbar();
						var imageWidget = editor.widgets.selected[0];
						if (!imageWidget || imageWidget.name !== 'image') {
							return;
						}

						imageWidget.focus();
						instance._keepToolbar = true;

						var selection = editor.getSelection();
						toolbar.attach(selection);
					},
				});

				editor.ui.addBalloonToolbarButton('LinkToolbar', {
					click() {
						instance._parentToolbar = this.balloonToolbar;
						editor.execCommand('linkToolbar');
					},
					icon: 'link',
					title: editor.lang.link.title,
				});
			},

			requires: ['uibutton', 'uiselect', 'uitextinput'],
		});
	}
})();
