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

	var pluginName = 'ballooneditor';

	CKEDITOR.SELECTION_TOP_TO_BOTTOM = 0;
	CKEDITOR.SELECTION_BOTTOM_TO_TOP = 1;

	if (!CKEDITOR.plugins.get(pluginName)) {
		CKEDITOR.plugins.add(pluginName, {
			init(editor) {
				var eventListeners = [];

				editor.on('contentDom', () => {
					var document = editor.document;

					eventListeners.push(
						document.on('keyup', () => {
							editor.forceNextSelectionCheck();
						})
					);

					eventListeners.push(
						document.on('mouseup', () => {
							editor.forceNextSelectionCheck();
						})
					);
				});

				editor.on('destroy', () => {
					CKEDITOR.tools.array.forEach(eventListeners, (listener) => {
						listener.removeListener();
					});
				});

				editor.on('hideToolbars', () => {
					editor.balloonToolbars.hide();
				});

				editor.on('resize', () => {
					editor.balloonToolbars.hide();
				});

				editor.on('showToolbar', (event) => {
					var toolbarCommand = event.data.toolbarCommand;

					// Hide all toolbars

					editor.balloonToolbars.hide();

					editor.execCommand(toolbarCommand);
				});
			},

			onLoad() {

				// Extend and override the "balloonpanel" plugin

				CKEDITOR.ui.balloonPanel.DEFAULT_PANEL_PADDING = 14;

				CKEDITOR.tools.extend(CKEDITOR.ui.balloonPanel.prototype, {
					_getSelectionDirection() {
						var direction = CKEDITOR.SELECTION_TOP_TO_BOTTOM;
						var editor = this.editor;
						var selection = editor.getSelection();
						var nativeSelection = selection.getNative();

						if (!nativeSelection) {
							return CKEDITOR.SELECTION_TOP_TO_BOTTOM;
						}

						var anchorNode;

						if (
							(anchorNode = nativeSelection.anchorNode) &&
							anchorNode.compareDocumentPosition
						) {
							var position = anchorNode.compareDocumentPosition(
								nativeSelection.focusNode
							);
							if (
								(!position &&
									nativeSelection.anchorOffset >
										nativeSelection.focusOffset) ||
								position === Node.DOCUMENT_POSITION_PRECEDING
							) {
								direction = CKEDITOR.SELECTION_BOTTOM_TO_TOP;
							}
						}

						return direction;
					},
				});

				CKEDITOR.ui.balloonPanel.prototype.attach = (function () {
					return function (elementOrSelection, options) {
						var padding =
							CKEDITOR.ui.balloonPanel.DEFAULT_PANEL_PADDING;

						if (
							options instanceof CKEDITOR.dom.element ||
							!options
						) {
							options = {focusElement: options};
						}

						options = CKEDITOR.tools.extend(options, {
							show: true,
						});

						if (options.show === true) {
							this.show();
						}

						this.fire('attach');

						var editable = this.editor.editable();
						var editableClientRect = editable.getClientRect(true);
						var panelClientRect = this.parts.panel.getClientRect(
							true
						);
						var type = elementOrSelection.getType();
						var x = 0;
						var y = 0;

						if (type === CKEDITOR.SELECTION_TEXT) {
							var direction = this._getSelectionDirection();
							var ranges = elementOrSelection.getRanges();
							var range = ranges[0];
							var rangeClientRects = range.getClientRects(true);

							var firstClientRect = rangeClientRects[0];
							var lastClientRect =
								rangeClientRects[rangeClientRects.length - 1];

							if (firstClientRect === lastClientRect) {
								direction = CKEDITOR.SELECTION_BOTTOM_TO_TOP;
							}

							var rangeHeight =
								lastClientRect.bottom - firstClientRect.top;
							var clientRect = rangeClientRects[0];

							x =
								clientRect.x +
								clientRect.width / 2 -
								panelClientRect.width / 2;
							y = clientRect.y - panelClientRect.height - padding;

							if (
								direction === CKEDITOR.SELECTION_TOP_TO_BOTTOM
							) {
								y = clientRect.y + rangeHeight + padding;
							}
						}
						else if (type === CKEDITOR.SELECTION_ELEMENT) {
							var selectedElement = elementOrSelection.getSelectedElement();

							if (!selectedElement) {
								ranges = elementOrSelection.getRanges();
								selectedElement =
									ranges && ranges[0].startContainer;
							}

							var selectedElementClientRect = selectedElement.getClientRect(
								true
							);

							// Center toolbar horizontally and on top of the selected element

							x =
								selectedElementClientRect.x +
								selectedElementClientRect.width / 2 -
								panelClientRect.width / 2;
							y =
								selectedElementClientRect.y -
								panelClientRect.height -
								padding;
						}

						if (x < editableClientRect.x) {
							x = editableClientRect.x + padding;
						}
						if (
							x + panelClientRect.width >
							editableClientRect.width
						) {
							x -=
								x +
								panelClientRect.width -
								editableClientRect.width;
						}

						if (y < editableClientRect.y) {
							y = clientRect.bottom + padding;
						}

						this.move(y, x);

						// Set focus to proper element.

						if (options.focusElement !== false) {
							(options.focusElement || this.parts.panel).focus();
						}
					};
				})();

				// Override the "balloontoolbar" toolbar plugin

				CKEDITOR.plugins.balloontoolbar.context.prototype._loadButtons = function () {
					var buttons = this.options.buttons;

					if (buttons) {
						buttons = buttons.split(',');
						CKEDITOR.tools.array.forEach(
							buttons,
							function (name) {
								var newUiItem = this.editor.ui.create(name);

								if (newUiItem) {
									this.toolbar.addItem(name, newUiItem);
									if (
										Object.hasOwnProperty.call(
											newUiItem,
											'balloonToolbar'
										)
									) {
										newUiItem.balloonToolbar = this.toolbar;
									}
								}
							},
							this
						);
					}
				};

				CKEDITOR.plugins.balloontoolbar.contextManager.prototype.check = function (
					selection
				) {
					if (!selection) {
						selection = this.editor.getSelection();

						// Shrink the selection so that we're ensured innermost elements are available, so that path for
						// selection like `foo [<em>bar</em>] baz` also contains `em` element.

						CKEDITOR.tools.array.forEach(
							selection.getRanges(),
							(range) => {
								range.shrink(CKEDITOR.SHRINK_ELEMENT, true);
							}
						);
					}

					if (!selection) {
						return;
					}

					var forEach = CKEDITOR.tools.array.forEach,
						mainRange = selection.getRanges()[0],
						path = mainRange && mainRange.startPath(),
						contextMatched;

					// This function encapsulates matching algorithm.

					function matchEachContext(
						contexts,
						matchingFunction,
						matchingArg1
					) {
						forEach(contexts, (curContext) => {

							// Execute only if there's no picked context yet, or if probed context has a higher priority than
							// currently matched one.

							if (
								!contextMatched ||
								contextMatched.options.priority >
									curContext.options.priority
							) {
								var result = matchingFunction(
									curContext,
									matchingArg1
								);

								if (result instanceof CKEDITOR.dom.element) {
									contextMatched = curContext;
								}
							}
						});
					}

					function elementsMatcher(curContext, curElement) {
						return curContext._matchElement(curElement);
					}

					// Match callbacks.

					matchEachContext(this._contexts, (curContext) => {
						return curContext._matchRefresh(path, selection);
					});

					// Match widgets.

					matchEachContext(this._contexts, (curContext) => {
						return curContext._matchWidget();
					});

					// Match element selectors.

					if (path) {

						// First check the outermost element (if any was selected), since the selection got shrinked
						// it would be otherwise skipped (#1274).

						var selectedElem = selection.getSelectedElement();

						if (selectedElem && !selectedElem.isReadOnly()) {
							matchEachContext(
								this._contexts,
								elementsMatcher,
								selectedElem
							);
						}

						for (var i = 0; i < path.elements.length; i++) {
							var curElement = path.elements[i];

							// Skip non-editable elements (e.g. widget internal structure).

							if (!curElement.isReadOnly()) {
								matchEachContext(
									this._contexts,
									elementsMatcher,
									curElement
								);
							}
						}
					}

					this.hide();

					if (contextMatched) {
						CKEDITOR.tools.array.forEach(
							selection.getRanges(),
							(range) => {
								range.shrink(CKEDITOR.SHRINK_ELEMENT, true);
							}
						);

						if (
							!selection.getSelectedElement() &&
							!selection.getSelectedText()
						) {
							return;
						}

						contextMatched.show(selection);
					}
				};
			},

			requires: [
				'balloonpanel',
				'balloontoolbar',
				'imagealt',
				'toolbarbuttons',
				'linktoolbar',
				'plusbutton',
			],
		});
	}
})();
