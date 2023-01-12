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

module.exports = {
	build: {
		exports: [
			'clipboard',
			'cropperjs',
			'dagre',
			'date-fns',
			{
				format: 'esm',
				name: 'dom-align',
				symbols: ['alignElement', 'alignPoint'],
			},
			'fuzzy',
			'highlight.js',
			'image-promise',
			'path-to-regexp',
			'qrcode',
			'qs',
			'react-flow-renderer',
			'react-router-dom',
			'react-text-mask',
			'react-transition-group',
			'text-mask-addons',
			'text-mask-core',
			'uuid',
		],
	},
};
