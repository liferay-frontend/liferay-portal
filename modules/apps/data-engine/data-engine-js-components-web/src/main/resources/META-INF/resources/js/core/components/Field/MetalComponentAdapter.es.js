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

import React, {useEffect, useRef} from 'react';

import {EVENT_TYPES} from '../../actions/eventTypes.es';
import {useForm} from '../../hooks/useForm.es';
import {usePage} from '../../hooks/usePage.es';
import MetalFieldAdapter from './MetalFieldAdapter.es';

class NoRender extends React.Component {
	shouldComponentUpdate() {
		return false;
	}

	render() {
		const {forwardRef, ...otherProps} = this.props;

		return <div ref={forwardRef} {...otherProps} />;
	}
}

export function MetalComponentAdapter({
	onBlur,
	onChange,
	onFocus,
	type,
	...field
}) {
	const {activePage, editable, pageIndex, spritemap} = usePage();
	const dispatch = useForm();

	const componentRef = useRef(null);
	const containerRef = useRef(null);

	useEffect(() => {
		if (!componentRef.current && containerRef.current) {
			componentRef.current = new MetalFieldAdapter(
				{
					activePage,
					editable,
					field,
					onBlur,
					onChange,
					onFocus,
					onRemoved: (_, event) =>
						dispatch({
							payload: event,
							type: EVENT_TYPES.FIELD.REMOVED,
						}),
					onRepeated: (_, event) =>
						dispatch({
							payload: event,
							type: EVENT_TYPES.FIELD.REPEATED,
						}),
					pageIndex,
					spritemap,
					type,
				},
				containerRef.current
			);
		}

		return () => {
			if (componentRef.current) {
				componentRef.current.dispose();
			}
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		if (componentRef.current) {
			componentRef.current.setState({
				activePage,
				editable,
				field,
				onChange,
				pageIndex,
				spritemap,
			});
		}
	}, [activePage, editable, onChange, pageIndex, spritemap, field]);

	return <NoRender forwardRef={containerRef} />;
}
