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

	var pluginName = 'uitextinput';
	var stylesLoaded = false;

	if (!CKEDITOR.plugins.get(pluginName)) {
		var template =
			'<input class="ui-textinput" id="{id}" ' +
			'placeholder="{placeholder}" type="text" value="{value}" ' +
			'onchange="CKEDITOR.tools.callFunction({changeFn},this);return false;" ' +
			'onkeyup="CKEDITOR.tools.callFunction({keyupFn},event,this);">';

		var inputTemplate = CKEDITOR.addTemplate(
			'balloonToolbarTextInput',
			template
		);

		CKEDITOR.ui.balloonToolbarTextInput = CKEDITOR.tools.createClass({
			/* eslint-disable */

			// prettier-ignore

			$: function(definition) {
				CKEDITOR.tools.extend(this, definition, {
					modes: {wysiwyg: 1},
					name: definition.name,
					placeholder: definition.placeholder,
					value: definition.value,
				});
			},

			/* eslint-enable */

			base: CKEDITOR.event,

			proto: {
				clear() {
					this.value = '';
					if (this._element) {
						this._element.setAttribute('value', this.value);
					}
				},

				render(editor, output) {
					var id = (this._id = CKEDITOR.tools.getNextId());
					var input = this;

					this._editor = editor;

					var instance = {
						input,
					};

					var changeFn = (instance.changeFn = CKEDITOR.tools.addFunction(
						(element) => {
							input.value = element.value;
						}
					));

					var keyupFn = CKEDITOR.tools.addFunction(
						(event, element) => {
							if (event.keyCode === 13) {
								input.value = element.value;
								input.fire(
									'change',
									{
										value: element.value,
									},
									input._editor
								);
							}
							if (event.keyCode === 27) {
								input.fire('cancel');
							}
						}
					);

					var params = {
						changeFn,
						id,
						keyupFn,
						name: this.name,
						placeholder: this.placeholder || '',
						value: this.value || '',
					};

					inputTemplate.output(params, output);

					this._element = this._editor.document.findOne(
						'#' + this._id
					);

					return instance;
				},
			},
		});

		CKEDITOR.plugins.add(pluginName, {
			init() {
				if (!stylesLoaded) {
					CKEDITOR.document.appendStyleSheet(
						this.path +
							'skins/' +
							CKEDITOR.skin.name +
							'/ui-textinput.css'
					);
					stylesLoaded = true;
				}
			},
		});
	}
})();
