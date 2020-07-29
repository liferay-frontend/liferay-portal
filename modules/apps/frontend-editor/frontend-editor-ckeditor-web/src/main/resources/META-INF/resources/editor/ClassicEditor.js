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

import classNames from 'classnames';
import {useEventListener} from 'frontend-js-react-web';
import {debounce, isPhone, isTablet} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import './ClassicEditor.scss';
import {Editor} from './Editor';

const getToolbarSet = (toolbarSet) => {
	if (isPhone()) {
		toolbarSet = 'phone';
	}
	else if (isTablet()) {
		toolbarSet = 'tablet';
	}

	return toolbarSet;
};

const ClassicEditor = ({
	contents = '',
	editorConfig,
	initialToolbarSet,
	name,
	onChangeMethodName,
	title,
}) => {
	const editorRef = useRef();
	const labeledToolbarsWidth = useRef();

	const [toolbarSet, setToolbarSet] = useState(initialToolbarSet);
	const [toolbarLabelsVisible, setToolbarLabelsVisible] = useState(true);

	const getConfig = () => {
		return {
			toolbar: toolbarSet,
			...editorConfig,
		};
	};

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

	const getToolbarsWidth = () => {
		const container = editorRef.current.editor.container.$;

		const toolbars = Array.from(container.querySelectorAll('.cke_toolbar'));

		return toolbars.reduce((acc, toolbar) => acc + toolbar.offsetWidth, 0);
	};

	const isToolbarOverflowing = () => {
		const toolbarsContainer = editorRef.current.editor.container.$.querySelector(
			'.cke_top'
		);

		let toolbarsContainerWidth = Infinity;

		if (toolbarsContainer) {
			toolbarsContainerWidth =
				parseInt(getComputedStyle(toolbarsContainer).width, 10) || 0;
		}

		return labeledToolbarsWidth.current >= toolbarsContainerWidth;
	};

	const onChangeCallback = () => {
		if (!onChangeMethodName) {
			return;
		}

		const editor = editorRef.current.editor;

		if (editor.checkDirty()) {
			window[onChangeMethodName](getHTML());

			editor.resetDirty();
		}
	};

	useEffect(() => {
		setToolbarSet(getToolbarSet(initialToolbarSet));
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
		setToolbarSet(getToolbarSet(initialToolbarSet));

		setToolbarLabelsVisible(!isToolbarOverflowing());
	}, 200);

	useEventListener('resize', onResize, true, window);

	return (
		<div
			className={classNames('classic-editor', {
				'hide-toolbar-labels': !toolbarLabelsVisible,
			})}
			id={`${name}Container`}
		>
			<label className="control-label" htmlFor={name}>
				{title}
			</label>
			<Editor
				config={getConfig()}
				onBeforeLoad={(CKEDITOR) => {
					CKEDITOR.disableAutoInline = true;
					CKEDITOR.dtd.$removeEmpty.i = 0;
					CKEDITOR.dtd.$removeEmpty.span = 0;

					CKEDITOR.on('instanceCreated', ({editor}) => {
						editor.name = name;

						editor.on('instanceReady', () => {
							editor.setData(contents);

							labeledToolbarsWidth.current = getToolbarsWidth();

							setToolbarLabelsVisible(!isToolbarOverflowing());
						});
					});
				}}
				onChange={onChangeCallback}
				ref={editorRef}
			/>
		</div>
	);
};

ClassicEditor.propTypes = {
	contents: PropTypes.string,
	editorConfig: PropTypes.object,
	initialToolbarSet: PropTypes.string,
	name: PropTypes.string,
	onChangeMethodName: PropTypes.string,
	title: PropTypes.string,
};

export default ClassicEditor;
