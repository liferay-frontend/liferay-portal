/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Plugin} from '@ckeditor/ckeditor5-core/dist/index.js';
import {Link, LinkUI} from '@ckeditor/ckeditor5-link/dist/index.js';
import {Config} from '@ckeditor/ckeditor5-utils/dist/index.js';
import {openModal, openSelectionModal} from 'frontend-js-components-web';
import React, {useState} from 'react';

import {LiferayEditorConfig} from '../utils/types';

interface LinkDialogValues {
	linkText: string;
	url: string;
}

/**
 * Decorates a method once, so that several listeners can hook into it without
 * wrapping it more than once.
 */
function decorateOnce(linkUI: any, methodName: string) {
	const flag = `_linkItemSelectorDecorated${methodName}`;

	if (linkUI[flag]) {
		return;
	}

	linkUI[flag] = true;

	linkUI.decorate(methodName);
}

/**
 * Replicates the CKEditor 4 link dialog: a modal owning the fields, plus a
 * button that browses the Item Selector portlet in a nested iframe modal.
 */
function openLinkDialog({
	browseURL,
	initialValues,
	itemSelectorEventName,
	onSubmit,
}: {
	browseURL: string;
	initialValues: LinkDialogValues;
	itemSelectorEventName?: string;
	onSubmit: (values: LinkDialogValues) => void;
}) {
	const values: LinkDialogValues = {...initialValues};

	const LinkDialogBody = () => {
		const [linkText, setLinkText] = useState(values.linkText);
		const [url, setURL] = useState(values.url);

		const updateLinkText = (value: string) => {
			values.linkText = value;

			setLinkText(value);
		};

		const updateURL = (value: string) => {
			values.url = value;

			setURL(value);
		};

		return (
			<>
				<div className="form-group">
					<label htmlFor="lfr-ck-link-text">
						{Liferay.Language.get('link-text')}
					</label>

					<input
						className="form-control"
						id="lfr-ck-link-text"
						onChange={(event) => updateLinkText(event.target.value)}
						type="text"
						value={linkText}
					/>
				</div>

				<div className="form-group">
					<label htmlFor="lfr-ck-link-url">
						{Liferay.Language.get('url')}
					</label>

					<div className="input-group">
						<div className="input-group-item">
							<input
								className="form-control"
								id="lfr-ck-link-url"
								onChange={(event) =>
									updateURL(event.target.value)
								}
								type="text"
								value={url}
							/>
						</div>

						<div className="input-group-item input-group-item-shrink">
							<button
								className="btn btn-secondary"
								onClick={() =>
									openSelectionModal<{value: string}>({
										onSelect: ({value}) => updateURL(value),
										selectEventName: itemSelectorEventName,
										title: Liferay.Language.get(
											'select-item'
										),
										url: browseURL,
										zIndex: Liferay.zIndex.WINDOW + 20,
									})
								}
								type="button"
							>
								{Liferay.Language.get('browse')}
							</button>
						</div>
					</div>
				</div>
			</>
		);
	};

	openModal({
		bodyComponent: LinkDialogBody,
		buttons: [
			{
				displayType: 'secondary',
				label: Liferay.Language.get('cancel'),
				type: 'cancel',
			},
			{
				label: Liferay.Language.get('ok'),
				onClick: ({processClose}: {processClose: () => void}) => {
					if (values.url) {
						onSubmit(values);
					}

					processClose();
				},
			},
		],
		size: 'md',
		title: Liferay.Language.get('link'),
		zIndex: Liferay.zIndex.WINDOW + 10,
	});
}

class LinkItemSelector extends Plugin {
	static get pluginName() {
		return 'LinkItemSelector' as const;
	}

	static get requires() {
		return [Link];
	}

	afterInit() {
		const editor = this.editor;

		const config: Config<LiferayEditorConfig> = editor.config;

		const browseURL = config.get('filebrowserBrowseUrl');

		if (!browseURL) {
			return;
		}

		const linkCommand = editor.commands.get('link')!;
		const linkUI = editor.plugins.get(LinkUI) as any;

		const openDialog = () =>
			openLinkDialog({
				browseURL,
				initialValues: {
					linkText: linkUI.selectedLinkableText || '',
					url: (linkCommand.value as string) || '',
				},
				itemSelectorEventName: config.get('itemSelectorEventName'),
				onSubmit: ({linkText, url}) =>
					editor.execute(
						'link',
						url,
						{},
						linkText === linkUI.selectedLinkableText
							? undefined
							: linkText
					),
			});

		// Creating a link never reaches the balloon form.

		decorateOnce(linkUI, '_showUI');

		this.listenTo(
			linkUI,
			'_showUI',
			(event: any) => {
				if (linkCommand.value) {
					return;
				}

				event.stop();

				openDialog();
			},
			{priority: 'high'}
		);

		// An existing link keeps the native preview toolbar, so that it can
		// still be opened and removed, but "Edit link" opens the dialog too.

		decorateOnce(linkUI, '_addFormView');

		this.listenTo(
			linkUI,
			'_addFormView',
			(event: any) => {
				event.stop();

				openDialog();
			},
			{priority: 'high'}
		);
	}
}

export default LinkItemSelector;
