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

	var pluginName = 'uibutton';

	if (!CKEDITOR.plugins.get(pluginName)) {
		var template =
			'<a id="{id}"' +
			' class="cke_button cke_button__{name} {cssClass}"' +
			' title="{title}"' +
			' tabindex="-1"' +
			' hidefocus="true"' +
			' role="button"' +
			' onclick="CKEDITOR.tools.callFunction({clickFn},event,this);return false;">' +
			' <span class="cke_button_icon cke_button__{icon}_icon"' +
			' >&nbsp;</span>' +
			' </a>';

		var buttonTemplate = CKEDITOR.addTemplate(
			'balloonToolbarButton',
			template
		);

		CKEDITOR.ui.balloonToolbarButton = CKEDITOR.tools.createClass({
			/* eslint-disable */

			// prettier-ignore

			$: function(definition) {
				CKEDITOR.tools.extend(this, definition, {
					balloonToolbar: null,
					click: definition.click,
					command: definition.command,
					cssClass: definition.cssClass,
					icon: definition.icon,
					modes: {wysiwyg: 1},
					title: definition.title,
				});
			},

			/* eslint-enable */

			base: CKEDITOR.event,

			proto: {
				render(editor, output) {
					var id = (this._id = CKEDITOR.tools.getNextId());
					var button = this;

					this._editor = editor;

					var instance = {
						button,
						execute() {
							if (typeof button.click === 'function') {
								button.click();
							}
							else if (button.command) {
								editor.execCommand(button.command);
							}
						},
						id,
					};

					var clickFn = CKEDITOR.tools.addFunction(() => {
						instance.execute();
					});

					var params = {
						clickFn,
						command: this.command ? this.command : '',
						cssClass: this.cssClass ? this.cssClass : '',
						icon: this.icon,
						id,
						title: this.title || '',
					};

					buttonTemplate.output(params, output);

					return instance;
				},
			},
		});

		CKEDITOR.ui.balloonToolbarButton.handler = {
			create(definition) {
				return new CKEDITOR.ui.balloonToolbarButton(definition);
			},
		};

		CKEDITOR.UI_BALLOON_TOOLBAR_BUTTON = 'balloonToolbarButton';

		CKEDITOR.tools.extend(CKEDITOR.ui.prototype, {
			addBalloonToolbarButton(name, definition) {
				this.add(name, CKEDITOR.UI_BALLOON_TOOLBAR_BUTTON, definition);
			},
		});

		CKEDITOR.plugins.add(pluginName, {
			beforeInit(editor) {
				editor.ui.addHandler(
					CKEDITOR.UI_BALLOON_TOOLBAR_BUTTON,
					CKEDITOR.ui.balloonToolbarButton.handler
				);
			},
		});
	}
})();
