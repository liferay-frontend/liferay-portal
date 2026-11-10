/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getImageHTML from '../../../src/main/resources/META-INF/resources/js/ckeditor5/utils/getImageHTML';

describe('getImageHTML', () => {
	it('adds the file entry ID when Adaptive Media is enabled', () => {
		expect(
			getImageHTML(
				JSON.stringify({fileEntryId: 123, url: '/documents/image.png'}),
				'data-fileentryid'
			)
		).toBe('<img data-fileentryid="123" src="/documents/image.png">');
	});

	it('returns a plain image when Adaptive Media is not enabled', () => {
		expect(
			getImageHTML(
				JSON.stringify({fileEntryId: 123, url: '/documents/image.png'})
			)
		).toBe('<img src="/documents/image.png">');
	});

	it('returns a plain image for a URL value', () => {
		expect(
			getImageHTML(
				'https://www.liferay.com/image.png',
				'data-fileentryid'
			)
		).toBe('<img src="https://www.liferay.com/image.png">');
	});

	it('returns null when the value has no URL', () => {
		expect(getImageHTML(JSON.stringify({fileEntryId: 123}))).toBeNull();
	});
});
