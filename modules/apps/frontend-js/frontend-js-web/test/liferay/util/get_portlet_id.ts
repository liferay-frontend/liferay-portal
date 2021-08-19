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

import getPortletId from '../../../src/main/resources/META-INF/resources/liferay/util/get_portlet_id';

describe('Liferay.Util.getPortletId', () => {
	it('returns the portlet id', () => {
		expect(getPortletId('p_p_id_fooBar_')).toEqual('fooBar');
	});

	it('returns the portlet id without a leading p_p_id', () => {
		expect(getPortletId('_fooBar_')).toEqual('fooBar');
	});

	it('returns the input as-is when no portlet ID can be extracted', () => {
		expect(getPortletId('not-a-well-formed-id')).toBe(
			'not-a-well-formed-id'
		);
	});
});
