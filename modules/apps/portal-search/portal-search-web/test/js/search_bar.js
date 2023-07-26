/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable @liferay/aui/no-node */

const FORM_TEMPLATE = `
	<form id="_NAMESPACE_fm">
		<input class="search-bar-empty-search-input" type="hidden" value="{emptySearchEnabled}">
		<input class="search-bar-keywords-input" name="q" type="text" value="{keywords}">
		<button type="submit"></button>
	</form>
`;

function getFormTemplate(keywords, emptySearchEnabled) {
	let template = FORM_TEMPLATE.replace('{keywords}', keywords || '');

	template = template.replace('{emptySearchEnabled}', !!emptySearchEnabled);

	return template;
}

describe('liferay-search-bar', () => {
	let A;

	beforeEach((done) => {
		require('../../src/main/resources/META-INF/resources/js/search_bar');

		AUI().use(['aui-base', 'aui-node', 'liferay-search-bar'], (Alloy) => {
			A = Alloy;

			done();
		});
	});

	describe('getKeywords()', () => {
		it('returns the keywords', () => {
			const form = A.Node.create(getFormTemplate('example'));

			const searchBar = new Liferay.Search.SearchBar(form);

			expect(searchBar.getKeywords()).toBe('example');
		});
	});

	describe('isSubmitEnabled', () => {
		it('is false with no keywords', () => {
			const form = A.Node.create(getFormTemplate());

			const searchBar = new Liferay.Search.SearchBar(form);

			expect(searchBar.isSubmitEnabled()).toBe(false);
		});

		it('is true with keywords', () => {
			const form = A.Node.create(getFormTemplate('example'));

			const searchBar = new Liferay.Search.SearchBar(form);

			expect(searchBar.isSubmitEnabled()).toBe(true);
		});

		it('is true if no keyword but keyword-free search enabled', () => {
			const form = A.Node.create(getFormTemplate('', true));

			const searchBar = new Liferay.Search.SearchBar(form);

			expect(searchBar.isSubmitEnabled()).toBe(true);
		});
	});

	describe('updateQueryString()', () => {
		it('removes p_p_id, p_p_state, start and add query keyword', () => {
			const form = A.Node.create(getFormTemplate('example'));

			const searchBar = new Liferay.Search.SearchBar(form);

			const queryString =
				'?p_p_lifecycle=0&p_p_mode=view&p_p_id=com_liferay_portal_search_web_search_bar_portlet_SearchBarPortlet&p_p_state=maximized&start=1';

			const updatedQueryString = searchBar.updateQueryString(queryString);

			expect(updatedQueryString).toBe(
				'?p_p_lifecycle=0&p_p_mode=view&q=example'
			);
		});
	});
});
