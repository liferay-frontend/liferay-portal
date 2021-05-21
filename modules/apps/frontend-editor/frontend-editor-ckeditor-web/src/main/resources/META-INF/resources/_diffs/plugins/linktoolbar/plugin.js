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

				// If we don't have a link, there's nothing to do

				var linkInput = this._toolbar.getItem('linkInput');

				if (!linkInput.value) {
					this._toolbar.hide();

					return;
				}

				var targetSelect = this._toolbar.getItem('targetSelect');

				var hasTarget = targetSelect.value !== 'Default';

				if (this.editor.widgets.selected[0]) {

					// Deal with image widget

					var imageWidget = this.editor.widgets.selected[0];

					if (!imageWidget || imageWidget.name !== 'image') {
						return;
					}

					var newData = Object.assign(imageWidget.data, {
						link: {
							url: linkInput.value,
						},
					});

					imageWidget.shiftState({
						deflate() {},
						element: imageWidget.element,
						inflate() {

							// Insert target and rel attributes

							var wrapperElement = imageWidget.wrapper;

							var linkElement = wrapperElement.findOne('a');

							if (linkElement) {
								linkElement.setAttribute('rel', 'noopener');

								if (hasTarget) {
									linkElement.setAttribute(
										'target',
										targetSelect.value
									);
								}
							}
						},
						newData,
						oldData: imageWidget.oldData,
						widget: imageWidget,
					});

					this._toolbar.hide();

					imageWidget.focus();
					imageWidget.element.focus();
				}
				else {
					var selection = this.options.selection;

					var startElement = selection.getStartElement();

					var linkElement;

					if (startElement.getName() === 'a') {

						// We've selected a link so we just need to update it

						linkElement = startElement;

						linkElement.setAttributes({

							// CKEditor processes links, so we also
							// need to update the "data-cke-saved-href" attribute

							'data-cke-saved-href': linkInput.value,
							href: linkInput.value,
							rel: 'noopener',
						});

						if (hasTarget) {
							linkElement.setAttribute(
								'target',
								targetSelect.value
							);
						}

						linkInput.clear();

						this._toolbar.hide();
					}
					else if (selection.getSelectedText()) {
						selection = this.editor.getSelection();
						var selectedText = selection.getSelectedText();

						var ranges = selection.getRanges();
						var range = ranges[0];

						var bookmark = range.createBookmark();

						linkElement = new CKEDITOR.dom.element('a');

						linkElement.setAttributes({
							href: linkInput.value,
							rel: 'noopener',
						});

						if (hasTarget) {
							linkElement.setAttribute(
								'target',
								targetSelect.value
							);
						}

						linkElement.setText(selectedText);

						linkElement.insertAfter(bookmark.endNode);

						// Remove selected text

						range.moveToBookmark(bookmark);
						range.deleteContents();

						// Reset input

						linkInput.clear();

						this._toolbar.hide();

						selection = this.editor.getSelection();

						selection.unlock(true);
					}
				}
			},

			_onSelectionChange() {
				if (this._toolbar) {
					this._toolbar.hide();
				}
			},

			_toolbar: null,

			init(editor) {
				var instance = this;

				instance.editor = editor;

				instance.options = {
					selection: null,
				};

				editor.on(
					'selectionChange',
					this._onSelectionChange.bind(this)
				);

				editor.addCommand('linkToolbar', {
					exec(editor) {
						instance._createToolbar();

						if (editor.widgets.selected[0]) {
							var imageWidget = editor.widgets.selected[0];

							if (!imageWidget || imageWidget.name !== 'image') {
								return;
							}

							imageWidget.focus();
						}

						var selection = editor.getSelection();

						selection.lock();

						instance.options.selection = selection;

						var isLink =
							selection.getStartElement().getName() === 'a';

						instance._toolbar.attach(selection);

						var linkInput = instance._toolbar.getItem('linkInput');

						if (isLink) {
							linkInput.setValue(
								selection.getStartElement().getAttribute('href')
							);
						}

						setTimeout(() => {
							var linkInputElement = linkInput.getInputElement();

							if (linkInputElement) {
								linkInputElement.focus();
							}
						}, 0);
					},
				});
			},

			requires: ['uibutton', 'uiselect', 'uitextinput'],
		});
	}
})();
