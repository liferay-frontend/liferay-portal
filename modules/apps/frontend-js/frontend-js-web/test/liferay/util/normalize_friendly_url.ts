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

import normalizeFriendlyURL from '../../../src/main/resources/META-INF/resources/liferay/util/normalize_friendly_url';

describe('Liferay.Util.normalizeFriendlyURL', () => {
	it('throws error if text parameter is not a string', () => {
		expect(() => normalizeFriendlyURL({} as any)).toThrow(
			'parameter text must be a string'
		);
	});

	it('returns modified text if text is in uppercase and contains spaces', () => {
		expect(normalizeFriendlyURL('TITLE WITH SPACES IN CAPS')).toEqual(
			'title-with-spaces-in-caps'
		);
	});

	it('returns modified text if text starts with dash', () => {
		expect(normalizeFriendlyURL('-startswithdash')).toEqual(
			'startswithdash'
		);
	});
});
