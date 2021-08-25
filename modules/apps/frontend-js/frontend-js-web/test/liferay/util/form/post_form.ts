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

import buildFragment from '../../../../src/main/resources/META-INF/resources/liferay/util/build_fragment';
import getFormElement from '../../../../src/main/resources/META-INF/resources/liferay/util/form/get_form_element';
import postForm from '../../../../src/main/resources/META-INF/resources/liferay/util/form/post_form';

declare global {
	namespace NodeJS {
		interface Global {
			submitForm: jest.Mock;
		}
	}
}

describe('Liferay.Util.postForm', () => {
	afterEach(() => {
		global.submitForm.mockRestore();
	});

	beforeEach(() => {
		global.submitForm = jest.fn();
	});

	it('does nothing if the form parameter is not a form node', () => {
		const fragment = buildFragment('<div />');

		postForm(undefined as any);
		postForm(fragment.firstElementChild as any);

		expect(global.submitForm.mock.calls.length).toBe(0);
	});

	it('submits form even if options parameter is not set', () => {
		const fragment = buildFragment('<form />');

		const form = fragment.firstElementChild as HTMLFormElement;

		postForm(form);

		expect(global.submitForm.mock.calls.length).toBe(1);
	});

	it('does nothing if the url optional parameter is not a string', () => {
		const fragment = buildFragment('<form />');

		const form = fragment.firstElementChild as HTMLFormElement;

		postForm(form, {url: undefined});
		postForm(form, {url: {} as any});

		expect(global.submitForm.mock.calls.length).toBe(0);
	});

	it('does nothing if the data optional parameter is not an object', () => {
		const fragment = buildFragment('<form />');

		const form = fragment.firstElementChild as HTMLFormElement;

		postForm(form, {data: undefined});
		postForm(form, {data: 'abc' as any});

		expect(global.submitForm.mock.calls.length).toBe(0);
	});

	it('sets given element values in data parameter, and submit form to a given url', () => {
		const fragment = buildFragment(`
					<form data-fm-namespace="_com_liferay_test_portlet_" id="fm">
						<input name="_com_liferay_test_portlet_foo" type="text" value="abc">
						<input name="_com_liferay_test_portlet_bar" type="text" value="123">
					</form>
				`);

		const form = fragment.firstElementChild as HTMLFormElement;

		postForm(form, {
			data: {
				bar: '456',
				foo: 'def',
			},
			url: 'http://sampleurl.com',
		});

		const barElement = getFormElement(form, 'bar') as HTMLInputElement;
		const fooElement = getFormElement(form, 'foo') as HTMLInputElement;

		expect(fooElement.value).toEqual('def');
		expect(barElement.value).toEqual('456');

		expect(global.submitForm.mock.calls.length).toBe(1);
	});
});
