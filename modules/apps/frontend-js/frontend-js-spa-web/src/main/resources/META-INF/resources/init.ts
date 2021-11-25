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

import App from './app/LiferayApp';
import ActionURLScreen from './screen/ActionURLScreen';
import RenderURLScreen from './screen/RenderURLScreen';
import {getUrlPath} from './util/utils';

import type {LiferayAppConfig} from './app/LiferayApp';

interface Config extends LiferayAppConfig {
	excludedPaths: string[];
	loginRedirect: string;
}

/**
 * Initializes a Senna App with routes that match both ActionURLs and RenderURLs.
 * It also overrides Liferay's default Liferay.Util.submitForm to makes sure
 * forms are properly submitted using SPA.
 */
const initSPA = function (config: Config) {
	const app = new App(config);

	app.addRoutes([
		{
			handler: ActionURLScreen,
			path(url: string) {
				let match = false;

				const uri = new URL(url, window.location.origin);

				const loginRedirectURL = new URL(
					config.loginRedirect,
					window.location.origin
				);

				const host = loginRedirectURL.host || window.location.host;

				if (app.isLinkSameOrigin_(host)) {
					match = uri.searchParams.get('p_p_lifecycle') === '1';
				}

				return match;
			},
		},
		{
			handler: RenderURLScreen,
			path(url: string) {
				let match = false;

				if (
					(url + '/').indexOf(themeDisplay.getPathMain() + '/') !== 0
				) {
					const excluded = config.excludedPaths.some(
						(excludedPath: string) =>
							url.indexOf(excludedPath) === 0
					);

					if (!excluded) {
						const uri = new URL(url, window.location.origin);

						const lifecycle = uri.searchParams.get('p_p_lifecycle');

						match = lifecycle === '0' || !lifecycle;
					}
				}

				return match;
			},
		},
	]);

	Liferay.Util.submitForm = function (
		form: HTMLFormElement | {getDOM: () => HTMLFormElement}
	) {
		setTimeout(() => {
			const formElement =
				form instanceof HTMLFormElement ? form : form.getDOM();

			const formSelector = 'form' + config.navigationExceptionSelectors;
			const url = formElement.action;

			if (
				formElement.matches(formSelector) &&
				app.canNavigate(url) &&
				formElement.method !== 'get' &&
				!app.isInPortletBlacklist(formElement)
			) {
				Liferay.Util._submitLocked = false;

				Liferay.SPA!.__capturedFormElement__ = formElement;

				const buttonSelector =
					'button:not([type]),button[type=submit],input[type=submit]';

				Liferay.SPA!.__capturedFormButtonElement__ = document.activeElement?.matches(
					buttonSelector
				)
					? (document.activeElement as HTMLButtonElement)
					: formElement.querySelector(buttonSelector)!;

				app.navigate(getUrlPath(url));
			}
			else {
				formElement.submit();
			}
		});
	};

	Liferay.initComponentCache();

	Liferay.SPA = {app};

	Liferay.fire('SPAReady');

	return app;
};

export default function init(config: Config) {
	if (document.readyState === 'loading') {
		document.addEventListener('DOMContentLoaded', () => {
			initSPA(config);
		});
	}
	else {
		initSPA(config);
	}
}
