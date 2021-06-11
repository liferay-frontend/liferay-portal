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

	var pluginName = 'toolbarbuttons';

	if (!CKEDITOR.plugins.get(pluginName)) {
		CKEDITOR.plugins.add(pluginName, {
			init(editor) {

				// Image buttons

				editor.ui.addBalloonToolbarButton('ImageAlignLeft', {
					click() {
						var imageWidget = editor.widgets.selected[0];

						if (imageWidget.name !== 'image') {
							return;
						}

						imageWidget.focus();

						var alignValue = imageWidget.data.align;

						if (!alignValue || alignValue !== 'left') {
							imageWidget.setData('align', 'left');
						}
						else {
							imageWidget.setData('align', 'none');
						}
					},
					icon: 'align-image-left',
					title: editor.lang.common.alignLeft,
				});

				editor.ui.addBalloonToolbarButton('ImageAlignCenter', {
					click() {
						var imageWidget = editor.widgets.selected[0];

						if (imageWidget.name !== 'image') {
							return;
						}

						imageWidget.focus();

						var alignValue = imageWidget.data.align;

						if (!alignValue || alignValue !== 'center') {
							imageWidget.setData('align', 'center');
						}
						else if (alignValue === 'center') {
							imageWidget.setData('align', 'none');
						}
					},
					icon: 'align-image-center',
					title: editor.lang.common.alignCenter,
				});

				editor.ui.addBalloonToolbarButton('ImageAlignRight', {
					click() {
						var imageWidget = editor.widgets.selected[0];

						if (imageWidget.name !== 'image') {
							return;
						}

						imageWidget.focus();

						var alignValue = imageWidget.data.align;

						if (!alignValue || alignValue !== 'right') {
							imageWidget.setData('align', 'right');
						}
						else {
							imageWidget.setData('align', 'none');
						}
					},
					icon: 'align-image-right',
					title: editor.lang.common.alignRight,
				});

				editor.ui.addBalloonToolbarButton('LinkToolbar', {
					click() {
						editor.fire('showToolbar', {
							toolbarCommand: 'linkToolbar',
						});
					},
					icon: 'link',
					title: editor.lang.link.title,
				});

				// Link buttons

				editor.ui.addBalloonToolbarButton('LinkAddOrEdit', {
					click() {
						editor.fire('showToolbar', {
							toolbarCommand: 'linkToolbar',
						});
					},
					icon: 'link',
					title: editor.lang.link.title,
				});

				editor.ui.addBalloonToolbarButton('LinkRemove', {
					click() {
						editor.fire('unlinkTextOrImage', {
							selection: editor.getSelection(),
						});
					},
					icon: 'unlink',
					title: editor.lang.link.unlink,
				});

				// Table buttons (and combo)

				editor.ui.addRichCombo('TableHeaders', {
					init() {
						var headersPrefix = editor.lang.table.headers;
						var headersNone =
							headersPrefix +
							': ' +
							editor.lang.table.headersNone;
						var headersRow =
							headersPrefix + ': ' + editor.lang.table.headersRow;
						var headersColumn =
							headersPrefix +
							': ' +
							editor.lang.table.headersColumn;
						var headersBoth =
							headersPrefix +
							': ' +
							editor.lang.table.headersBoth;

						this.add(headersNone, headersNone, headersNone);
						this.add(headersRow, headersRow, headersRow);
						this.add(headersColumn, headersColumn, headersColumn);
						this.add(headersBoth, headersBoth, headersBoth);
					},

					label: editor.lang.table.headers,

					panel: {
						attributes: {'aria-label': editor.lang.table.title},
						css: [CKEDITOR.skin.getPath('editor')].concat(
							editor.config.contentsCss
						),
						multiSelect: false,
					},

					title: editor.lang.table.title,
				});

				editor.ui.addBalloonToolbarButton('TableRow', {
					icon: 'add-row',
					title: editor.lang.table.row.menu,
				});

				editor.ui.addBalloonToolbarButton('TableColumn', {
					icon: 'add-column',
					title: editor.lang.table.column.menu,
				});

				editor.ui.addBalloonToolbarButton('TableCell', {
					icon: 'add-cell',
					title: editor.lang.table.cell.menu,
				});

				editor.ui.addBalloonToolbarButton('TableDelete', {
					click() {
						var selection = editor.getSelection();
						var startElement = selection.getStartElement();
						var tableElement = startElement.getAscendant('table');

						if (tableElement) {
							tableElement.remove();
							editor.fire('hideToolbars');
						}
					},
					icon: 'trash',
					title: editor.lang.table.deleteTable,
				});

				// Text buttons

				editor.ui.addBalloonToolbarButton('TextLink', {
					click() {
						editor.fire('showToolbar', {
							toolbarCommand: 'linkToolbar',
						});
					},
					icon: 'link',
					title: editor.lang.link.title,
				});

				editor.ui.addBalloonToolbarSelect('LineHeight', {
					_applyStyle(className) {
						var styleConfig = {
							attributes: {
								class: className,
							},
							element: 'div',
						};

						var selection = editor.getSelection();

						var startElement = selection.getStartElement();

						var style = new CKEDITOR.style(styleConfig);

						editor.getSelection().lock();

						style.applyToObject(startElement, this.editor);

						editor.getSelection().unlock();
					},

					_checkActive({elementPath, styleConfig}) {
						var active = true;

						if (elementPath && elementPath.lastElement) {
							styleConfig.attributes.class
								.split(' ')
								.forEach((className) => {
									active =
										active &&
										elementPath.lastElement.hasClass(
											className
										);
								});
						}
						else {
							active = false;
						}

						return active;
					},

					_getSelectedIndex(key) {
						return this.items.findIndex((item) => {
							return item.value === key;
						});
					},

					_stylesFactory: {
						'1.0x': {
							style: {attributes: {class: ''}, element: 'div'},
						},
						'1.5x': {
							style: {
								attributes: {class: 'mt-1 mb-1'},
								element: 'div',
							},
						},
						'2.0x': {
							style: {
								attributes: {class: 'mt-2 mb-2'},
								element: 'div',
							},
						},
						'3.0x': {
							style: {
								attributes: {class: 'mt-3 mb-3'},
								element: 'div',
							},
						},
						'4.0x': {
							style: {
								attributes: {class: 'mt-4 mb-4'},
								element: 'div',
							},
						},
						'5.0x': {
							style: {
								attributes: {class: 'mt-5 mb-5'},
								element: 'div',
							},
						},
					},

					icon: 'horizontalrule',

					items: [
						{label: '1.0x', value: '1.0x'},
						{label: '1.5x', value: '1.5x'},
						{label: '2.0x', value: '2.0x'},
						{label: '3.0x', value: '3.0x'},
						{label: '4.0x', value: '4.0x'},
						{label: '5.0x', value: '5.0x'},
					],

					name: Liferay.Language.get('line-height'),

					onChange(key) {
						this._applyStyle(
							this._stylesFactory[key].style.attributes.class
						);
					},

					onRender() {
						editor.on(
							'selectionChange',
							function (event) {
								this._editor.focusManager.add(
									this._editor.document.getById(this._id),
									1
								);

								Object.keys(this._stylesFactory).forEach(
									(spacingKey) => {
										if (
											this._checkActive({
												elementPath: event.data.path,
												styleConfig: this
													._stylesFactory[spacingKey]
													.style,
											})
										) {
											var newSelectedIndex = this._getSelectedIndex(
												spacingKey
											);
											document.getElementById(
												this._id
											).selectedIndex = newSelectedIndex;
										}
									}
								);
							},
							this
						);
					},

					title: Liferay.Language.get('line-height'),
				});

				// Video Toolbar Buttons

				editor.ui.addBalloonToolbarButton('VideoAlignLeft', {
					command: 'justifyleft',
					icon: 'align-image-left',
					title: editor.lang.common.alignLeft,
				});

				editor.ui.addBalloonToolbarButton('VideoAlignCenter', {
					command: 'justifycenter',
					icon: 'align-image-center',
					title: editor.lang.common.alignCenter,
				});

				editor.ui.addBalloonToolbarButton('VideoAlignRight', {
					command: 'justifyright',
					icon: 'align-image-right',
					title: editor.lang.common.alignRight,
				});
			},

			requires: ['uibutton', 'uiselect', 'uitextinput'],
		});
	}
})();
