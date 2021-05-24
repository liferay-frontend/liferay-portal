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

			requires: ['uibutton'],
		});
	}
})();
