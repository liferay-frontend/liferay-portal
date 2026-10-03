/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
(function () {
	const PATH_JAVASCRIPT = '/o/frontend-js-aui-web';

	AUI().applyConfig({
		groups: {
			liferaydeprecated: {
				base:
					Liferay.ThemeDisplay.getCDNBaseURL() +
					Liferay.ThemeDisplay.getPathContext() +
					PATH_JAVASCRIPT +
					'/liferay/',
				combine: Liferay.AUI.getCombine(),
				filter: Liferay.AUI.getFilterConfig(),
				modules: {
					'liferay-auto-fields': {
						path: 'auto_fields.js',
						requires: [
							'aui-base',
							'aui-data-set-deprecated',
							'aui-parse-content',
							'base',
							'liferay-form',
							'liferay-menu',
							'liferay-portlet-base',
							'sortable',
						],
					},
					'liferay-menu': {
						path: 'menu.js',
						requires: ['aui-debounce', 'aui-node'],
					},
					'liferay-menu-filter': {
						path: 'menu_filter.js',
						requires: [
							'autocomplete-base',
							'autocomplete-filters',
							'autocomplete-highlighters',
						],
					},
				},
				root: PATH_JAVASCRIPT + '/liferay/',
			},
		},
	});

	// Only liferay-menu is preloaded, because it is the only one of these the
	// portal ever preloaded: bottom_js.jspf ran an unconditional
	// <aui:script use="liferay-menu"> on every page, so Liferay.Menu was there
	// for inline scripts that never called AUI().use(). liferay-menu-filter
	// was pulled lazily by the menu itself, the first time a
	// max-display-items-N list opened, and liferay-auto-fields was reached
	// through <aui:script use="liferay-auto-fields"> at each call site.
	// Neither was ever a global anyone could count on without asking for it,
	// so preloading them would invent a guarantee rather than restore one, and
	// the two of them together cost several times what the menu does.
	//
	// This is only safe now that no portal code reads Liferay.Menu. The
	// AlloyUI Menu constructor claims Menu._INSTANCE and binds nothing at all;
	// every listener comes from Menu.register(), which nothing but customer
	// code calls any more. Attaching the module can therefore neither
	// double-bind a trigger nor open a menu twice.

	// Wait for the document rather than calling use() here. aui_sandbox.js
	// wraps every use() so that its callback is dropped when Liferay.currentURL
	// has changed since the call, which is how it keeps a pending callback from
	// firing against the page an SPA navigation already replaced. This file
	// runs in the top head, where Liferay.currentURL is still undefined: the
	// portal assigns it near the end of the body. The guard would therefore
	// compare undefined against the real URL and swallow the callback, leaving
	// Liferay.Menu unset with nothing logged.

	const preloadMenu = () =>
		AUI().use('liferay-menu', () => new Liferay.Menu());

	if (document.readyState === 'loading') {
		document.addEventListener('DOMContentLoaded', preloadMenu);
	}
	else {
		preloadMenu();
	}
})();
