/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	'use strict';

	class CustomElementThirdPartyCookie extends HTMLElement {
		constructor() {
			super();

			const div = document.createElement('div');

			const link = document.createElement('link');
			link.setAttribute(
				'data-href',
				'https://liferay-3rd-party-cookie.vercel.app/api/set-cookie.css?type=link'
			);
			link.setAttribute('rel', 'stylesheet');
			link.setAttribute('type', 'text/css');
			link.setAttribute(
				'data-third-party-cookie',
				'CONSENT_TYPE_PERSONALIZATION'
			);
			document.head.appendChild(link);

			const script = document.createElement('script');
			script.setAttribute(
				'src',
				'https://liferay-3rd-party-cookie.vercel.app/api/set-cookie.js?type=script'
			);
			script.setAttribute('type', 'text/plain');
			script.setAttribute(
				'data-third-party-cookie',
				'CONSENT_TYPE_PERSONALIZATION'
			);
			document.head.appendChild(script);

			const img = document.createElement('img');
			img.setAttribute(
				'data-src',
				'https://liferay-3rd-party-cookie.vercel.app/api/set-cookie?type=img'
			);
			img.setAttribute(
				'data-third-party-cookie',
				'CONSENT_TYPE_PERSONALIZATION'
			);
			div.appendChild(img);

			const embed = document.createElement('embed');
			embed.setAttribute(
				'data-src',
				'https://liferay-3rd-party-cookie.vercel.app/api/set-cookie?type=embed'
			);
			embed.setAttribute(
				'data-third-party-cookie',
				'CONSENT_TYPE_PERSONALIZATION'
			);
			div.appendChild(embed);

			const iframe = document.createElement('iframe');
			iframe.setAttribute(
				'data-src',
				'https://liferay-3rd-party-cookie.vercel.app/api/set-cookie?type=iframe'
			);
			iframe.setAttribute(
				'data-third-party-cookie',
				'CONSENT_TYPE_PERSONALIZATION'
			);
			div.appendChild(iframe);

			this.root = div;
			this.rendered = false;
		}

		connectedCallback() {
			if (!this.rendered) {
				this.rendered = true;
				this.appendChild(this.root);
				this.root = this;
			}
		}
	}

	if (!customElements.get('third-party-cookie')) {
		customElements.define(
			'third-party-cookie',
			CustomElementThirdPartyCookie
		);
	}
})();
