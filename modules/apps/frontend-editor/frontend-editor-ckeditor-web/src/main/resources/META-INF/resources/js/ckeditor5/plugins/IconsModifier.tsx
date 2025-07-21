/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {AlignmentCommand, Plugin} from 'ckeditor5';

import getIcon from '../utils/getIcon';

/**
 * Mapping between names of CKEditor components and Clay icon symbols
 */
const COMPONENT_SYMBOL: Record<string, string | null> = {
	accessibilityHelp: 'accessibility',
	alignment: 'align-left',
	blockQuote: 'quote-left',
	bold: 'bold',
	bulletedList: 'list-ul',
	fontBackgroundColor: 'textbox',
	fontColor: 'text-color',
	fontFamily: 'font-family',
	fontSize: 'font-size',
	horizontalLine: 'hr',
	indent: 'indent-more',
	insertTable: 'table2',
	italic: 'italic',
	link: 'link',
	numberedList: 'list-ol',
	outdent: 'indent-less',
	redo: 'redo',
	removeFormat: 'remove-style',
	sourceEditing: 'code',
	strikethrough: 'strikethrough',
	undo: 'undo',
};

class IconsModifier extends Plugin {
	init() {
		const editor = this.editor;

		const componentFactory = editor.ui.componentFactory;

		const componentNames = Array.from(componentFactory.names());

		componentNames.forEach((componentName: string) => {
			const symbol = COMPONENT_SYMBOL[componentName];

			if (!symbol) {
				return;
			}

			// @ts-ignore

			const component = componentFactory._components.get(
				componentName.toLowerCase()
			);

			if (!component) {
				return;
			}

			componentFactory.add(componentName, (locale) => {
				const componentInstance = component.callback(locale);

				const componentType = componentInstance.constructor.name;

				if (componentName === 'alignment') {
					const command: AlignmentCommand =
						editor.commands.get('alignment')!;

					const alignIcons: Record<string, string> = {
						center: getIcon({symbol: 'align-center'}),
						justify: getIcon({symbol: 'align-justify'}),
						left: getIcon({symbol: 'align-left'}),
						right: getIcon({symbol: 'align-right'}),
					};

					componentInstance.buttonView.unbind('icon');

					componentInstance.buttonView
						.bind('icon')
						.to(
							command,
							'value',
							(value: string) =>
								alignIcons[value] ||
								getIcon({symbol: 'align-left'})
						);

					componentInstance.on('change:isOpen', () => {
						const buttons =
							componentInstance.panelView.children.first.items
								._items;

						const alignmentOptions =
							editor.config.get('alignment.options')!;

						for (let i = 0; i < alignmentOptions.length; i++) {
							buttons[i].set({
								icon: alignIcons[alignmentOptions[i] as string],
							});
						}
					});
				}
				else if (componentType === 'ButtonView') {
					componentInstance.set({
						icon: getIcon({symbol}),
					});
				}
				else if (componentType === '_DropdownView') {
					componentInstance.buttonView.set({
						icon: getIcon({symbol}),
					});

					componentInstance.buttonView.arrowView.set({
						content: getIcon({symbol: 'angle-down'}),
					});
				}

				return componentInstance;
			});

			componentFactory.create(componentName);
		});
	}
}

export default IconsModifier;
