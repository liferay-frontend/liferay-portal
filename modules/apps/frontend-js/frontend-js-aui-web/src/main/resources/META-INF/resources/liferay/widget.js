/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

window.Liferay = window.Liferay || {};

/**
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
Liferay.Widget = function (options) {
	options = options || {};

	const height = options.height || '100%';
	const width = options.width || '100%';

	const id =
		options.id ||
		'_Liferay_widget' + Math.ceil(Math.random() * new Date().getTime());
	const url =
		options.url ||
		'http://www.liferay.com/widget/web/guest/community/forums/-/message_boards';

	const html =
		'<iframe frameborder="0" height="' +
		height +
		'" id="' +
		id +
		'" src="' +
		url +
		'" width="' +
		width +
		'"></iframe>';

	document.write(html);
};
