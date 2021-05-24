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

				// Image Toolbar buttons

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

				// Link toolbar buttons

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

				// Text toolbar buttons

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
