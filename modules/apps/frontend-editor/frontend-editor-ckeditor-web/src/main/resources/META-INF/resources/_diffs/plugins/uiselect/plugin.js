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

	var pluginName = 'uiselect';
	var stylesLoaded = false;

	if (!CKEDITOR.plugins.get(pluginName)) {
		var template = new CKEDITOR.template((data) => {
			var output;

			if (data.icon) {
				output =
					'<a class="cke_button"' +
					'tabindex="-1"' +
					'title="{title}"' +
					'hidefocus="true"' +
					'role="button"' +
					'"><span class="cke_button_icon cke_button__{icon}_icon"' +
					' >&nbsp;</span>';
			}

			output += '<select class="';

			if (data.icon) {
				output += 'ui-select-wrapper';
			}

			output +=
				' ui-select"' +
				'id="{id}" ' +
				'name="{name}" ' +
				'onchange="CKEDITOR.tools.callFunction({changeFn},event,this);return false;">';

			if (data.items) {
				CKEDITOR.tools.array.forEach(data.items, (item) => {
					var value = item.value
						? item.value
						: item.label
						? item.label
						: '';
					output +=
						'<option value="' +
						String(value) +
						'">' +
						item.label +
						'</option>';
				});
			}

			output += '</select>';

			if (data.icon) {
				output += '</a>';
			}

			return output;
		});

		CKEDITOR.ui.balloonToolbarSelect = CKEDITOR.tools.createClass({
			/* eslint-disable */

			// prettier-ignore

			$: function(definition) {
				var items = Array.isArray(definition.items)
					? definition.items
					: [{label: ''}];

				var value = items[0].value
					? items[0].value
					: items[0].label
					? items[0].label
					: '';

				CKEDITOR.tools.extend(this, definition, {
					icon: definition.icon,
					items,
					modes: {wysiwyg: 1},
					name: definition.name,
					title: definition.title,
					value,
				});
			},

			/* eslint-enable */

			base: CKEDITOR.event,

			proto: {
				render(editor, output) {
					var id = (this._id = CKEDITOR.tools.getNextId());
					var select = this;

					this._editor = editor;

					var instance = {
						execute(value) {
							this.select.value = value;
						},
						id,
						select,
					};

					var changeFn = (instance.changeFn = CKEDITOR.tools.addFunction(
						function (event, element) {
							event.preventDefault();
							var option = element.options[element.selectedIndex];
							var value = option.value;

							instance.execute(value);
							select.fire(
								'change',
								{
									value,
								},
								this._editor
							);

							if (select.onChange) {
								select.onChange(value);
							}
						}
					));

					var params = {
						changeFn,
						icon: this.icon,
						id,
						items: this.items,
						name: this.name,
						title: this.title,
					};

					template.output(params, output);

					if (this.onRender) {
						this.onRender(instance);
					}

					return instance;
				},
			},
		});

		CKEDITOR.ui.balloonToolbarSelect.handler = {
			create(definition) {
				return new CKEDITOR.ui.balloonToolbarSelect(definition);
			},
		};

		CKEDITOR.UI_BALLOON_TOOLBAR_SELECT = 'balloonToolbarSelect';

		CKEDITOR.tools.extend(CKEDITOR.ui.prototype, {
			addBalloonToolbarSelect(name, definition) {
				this.add(name, CKEDITOR.UI_BALLOON_TOOLBAR_SELECT, definition);
			},
		});

		CKEDITOR.plugins.add(pluginName, {
			init(editor) {
				if (!stylesLoaded) {
					CKEDITOR.document.appendStyleSheet(
						this.path +
							'skins/' +
							CKEDITOR.skin.name +
							'/ui-select.css'
					);
					stylesLoaded = true;
				}

				editor.ui.addHandler(
					CKEDITOR.UI_BALLOON_TOOLBAR_SELECT,
					CKEDITOR.ui.balloonToolbarSelect.handler
				);
			},
		});
	}
})();
