/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import launcher from '../../src/main/resources/META-INF/resources/components/gallery/entry';

import '../../src/main/resources/META-INF/resources/styles/main.scss';

function getImgUrl(image, width) {
	return `https://images.unsplash.com/${image}?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=${width}&q=80`;
}

const props = {
	images: [
		'flagged/photo-1556667885-a6e05b14f2eb',
		'photo-1503328427499-d92d1ac3d174',
		'photo-1505740420928-5e560c06d30e',
		'photo-1526434426615-1abe81efcb0b',
		'photo-1518131672697-613becd4fab5',
	].map((image) => ({
		URL: getImgUrl(image, 800),
		thumbnailURL: getImgUrl(image, 100),
		title: image,
	})),
};

launcher('galleryId', 'gallery-root-id', props);
