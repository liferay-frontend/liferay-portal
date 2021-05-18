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

	var pluginName = 'uinumberinput';
	var stylesLoaded = false;

	if (!CKEDITOR.plugins.get(pluginName)) {
		var template = new CKEDITOR.template((data) => {
			var output = '<div class="ui-numberinput">';

			if (data.label) {
				output += '<label for="{id}">{label}</label>';
			}

			output +=
				'<input class="ui-numberinput" id="{id}" ' +
				'min="{min}" max="{max}" step="{step}" type="number" value="{value}" ' +
				'onchange="CKEDITOR.tools.callFunction({changeFn},event);return false;">';

			output += '</div>';

			return output;
		});

		CKEDITOR.ui.balloonToolbarNumberInput = CKEDITOR.tools.createClass({
			/* eslint-disable */

			// prettier-ignore

			$: function(definition) {
				CKEDITOR.tools.extend(this, definition, {
					change: definition.change || function () {},
					label: definition.label || '',
					max: definition.max,
					min: definition.min || 0,
					modes: {wysiwyg: 1},
					step: definition.step || 1,
					value: definition.value || 0,
				});
			},

			/* eslint-enable */

			base: CKEDITOR.event,

			proto: {
				render(editor, output) {
					var id = (this._id = CKEDITOR.tools.getNextId());
					var input = this;

					this._editor = editor;

					var instance = {
						input,
					};

					var changeFn = CKEDITOR.tools.addFunction((event) => {
						event.preventDefault();

						var value = event.target.valueAsNumber;

						input.value = value;
						input.change(value);

						input.fire(
							'change',
							{
								value,
							},
							input._editor
						);
					});

					var params = {
						changeFn,
						id,
						label: this.label,
						max: this.max,
						min: this.min,
						step: this.step,
						value: this.value,
					};

					template.output(params, output);

					this._element = this._editor.document.findOne(
						'#' + this._id
					);

					return instance;
				},
			},
		});

		CKEDITOR.ui.balloonToolbarNumberInput.handler = {
			create(definition) {
				return new CKEDITOR.ui.balloonToolbarNumberInput(definition);
			},
		};

		CKEDITOR.UI_BALLOON_NUMBER_INPUT = 'balloonToolbarNumberInput';

		CKEDITOR.tools.extend(CKEDITOR.ui.prototype, {
			addBalloonToolbarNumberInput(name, definition) {
				this.add(name, CKEDITOR.UI_BALLOON_NUMBER_INPUT, definition);
			},
		});

		CKEDITOR.plugins.add(pluginName, {
			init(editor) {
				if (!stylesLoaded) {
					CKEDITOR.document.appendStyleSheet(
						this.path +
							'skins/' +
							CKEDITOR.skin.name +
							'/ui-numberinput.css'
					);
					stylesLoaded = true;
				}

				editor.ui.addHandler(
					CKEDITOR.UI_BALLOON_NUMBER_INPUT,
					CKEDITOR.ui.balloonToolbarNumberInput.handler
				);
			},
		});
	}
})();
