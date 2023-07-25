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

const DEFAULT_OPTIONS = {
	directories: 0,
	height: 480,
	left: 80,
	location: 1,
	menubar: 1,
	resizable: 1,
	scrollbars: 'yes',
	status: 0,
	toolbar: 0,
	top: 180,
	width: 640,
};

export default function printPage(url, options = DEFAULT_OPTIONS) {
	const nextOptions = {
		...DEFAULT_OPTIONS,
		...options,
	};

	let stringOptions = '';

	Object.keys(nextOptions).map((key) => {
		stringOptions += key + '=' + nextOptions[key] + ',';
	});

	window.open(url, '', stringOptions);
}
