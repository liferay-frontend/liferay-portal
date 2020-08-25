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

import {useEventListener} from 'frontend-js-react-web';
import {debounce} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import {Editor} from './Editor';

const ClassicEditor = ({
	contents = '',
	initialEditorConfig,
	initialToolbarSet = 'simple',
	name,
	onChange,
	onChangeMethodName,
	title,
	...otherProps
}) => {
	const editorRef = useRef();

	const [editorConfig, setEditorConfig] = useState(initialEditorConfig);
	const [toolbarSet, setToolbarSet] = useState(initialToolbarSet);

	const getHTML = useCallback(() => {
		let data = contents;

		const editor = editorRef.current.editor;

		if (editor && editor.instanceReady) {
			data = editor.getData();

			if (CKEDITOR.env.gecko && CKEDITOR.tools.trim(data) === '<br />') {
				data = '';
			}
		}

		return data;
	}, [contents]);

	const initAutocomplete = (editor) => {
		const autocompleteConfig = editorConfig.autocomplete;

		let autocomplete = new CKEDITOR.plugins.autocomplete(editor, {
			textTestCallback: (range) => {
				if (!range.collapsed) {
					return null;
				}

				return CKEDITOR.plugins.textMatch.match(range, (text, offset) => {
					const match = text.slice(0, offset)
						.match(new RegExp(`${autocompleteConfig.term}+([A-z])+$`));
	
					if (!match) {
						return null;
					}
	
					return {
						start: match.index,
						end: offset
					};
				});
			},
			dataCallback: (matchInfo, callback) => {
				const RESULTS = [{
					id: 1,
					name: 'address',
					title: 'Address',
					mention: 'Address',
					description: 'Customer Support correspondence address.'
				}];

				var data = RESULTS.filter(function(item) {
					var itemName = '@' + item.name;
					return itemName.indexOf(matchInfo.query.toLowerCase()) == 0;
				});

				callback(data);
			},
			itemTemplate: `<li data-id="{id}">${autocompleteConfig.tplResults}</li>`,
			outputTemplate: autocompleteConfig.tplReplace
		});

		autocomplete.getHtmlToInsert = function(item) {
			return this.outputTemplate.output(item);
		}
	};

	const onChangeCallback = () => {
		if (!onChangeMethodName && !onChange) {
			return;
		}

		const editor = editorRef.current.editor;

		if (editor.checkDirty()) {
			if (onChangeMethodName) {
				window[onChangeMethodName](getHTML());
			}
			else {
				onChange(getHTML());
			}

			editor.resetDirty();
		}
	};

	useEffect(() => {
		const newEditorConfig = {
			toolbar: toolbarSet,
			...initialEditorConfig
		};

		if (initialEditorConfig.extraPlugins.includes('autocomplete') 
			&& !initialEditorConfig.extraPlugins.includes('textmatch')) {
			newEditorConfig.extraPlugins += ',textmatch';
		}

		setEditorConfig(newEditorConfig);
	}, [initialEditorConfig, toolbarSet]);

	useEffect(() => {
		setToolbarSet(initialToolbarSet);
	}, [initialToolbarSet]);

	useEffect(() => {
		window[name] = {
			getHTML,
			getText() {
				return contents;
			},
		};
	}, [contents, getHTML, name]);

	const onResize = debounce(() => {
		setToolbarSet(initialToolbarSet);
	}, 200);

	useEventListener('resize', onResize, true, window);

	return (
		<div id={`${name}Container`}>
			{title && (
				<label className="control-label" htmlFor={name}>
					{title}
				</label>
			)}
			<Editor
				className="lfr-editable"
				config={editorConfig}
				onBeforeLoad={(CKEDITOR) => {
					CKEDITOR.disableAutoInline = true;
					CKEDITOR.dtd.$removeEmpty.i = 0;
					CKEDITOR.dtd.$removeEmpty.span = 0;

					CKEDITOR.getNextZIndex = function () {
						return CKEDITOR.dialog._.currentZIndex
							? CKEDITOR.dialog._.currentZIndex + 10
							: Liferay.zIndex.WINDOW + 10;
					};

					CKEDITOR.on('instanceCreated', ({editor}) => {
						editor.name = name;

						editor.on('drop', (event) => {
							var data = event.data.dataTransfer.getData(
								'text/html'
							);

							if (data) {
								var fragment = CKEDITOR.htmlParser.fragment.fromHtml(
									data
								);

								var name = fragment.children[0].name;

								if (name) {
									return editor.pasteFilter.check(name);
								}
							}
						});

						editor.on('instanceReady', () => {
							editor.setData(contents);

							if (editorConfig.autocomplete) {
								initAutocomplete(editor);
							}				
						});
					});
				}}
				onChange={onChangeCallback}
				ref={editorRef}
				{...otherProps}
			/>
		</div>
	);
};

ClassicEditor.propTypes = {
	contents: PropTypes.string,
	editorConfig: PropTypes.object,
	initialToolbarSet: PropTypes.string,
	name: PropTypes.string,
	onChange: PropTypes.func,
	onChangeMethodName: PropTypes.string,
	title: PropTypes.string,
};

export {ClassicEditor};
export default ClassicEditor;
