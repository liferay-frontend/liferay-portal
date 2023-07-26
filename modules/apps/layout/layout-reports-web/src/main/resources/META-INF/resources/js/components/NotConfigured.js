/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import React, {useContext} from 'react';

import {StoreStateContext} from '../context/StoreContext';

export default function NotConfigured() {
	const {data} = useContext(StoreStateContext);

	const {configureGooglePageSpeedURL, imagesPath} = data;

	const defaultIllustration = `${imagesPath}/issues_configure.svg`;

	return (
		<div className="pb-3 px-3 text-center">
			<img
				alt={Liferay.Language.get(
					'default-page-audit-image-alt-description'
				)}
				className="my-4"
				src={defaultIllustration}
				width="120px"
			/>

			<p className="font-weight-semi-bold mb-2">
				{Liferay.Language.get(
					"check-issues-that-impact-on-your-page's-accessibility-and-seo"
				)}
			</p>

			{configureGooglePageSpeedURL ? (
				<>
					<p className="mb-3 text-secondary">
						{Liferay.Language.get(
							'configure-google-pagespeed-to-run-a-page-audit'
						)}
					</p>

					<ClayLink
						className="btn btn-secondary"
						href={configureGooglePageSpeedURL}
					>
						{Liferay.Language.get('configure')}
					</ClayLink>
				</>
			) : (
				<p className="text-secondary">
					{Liferay.Language.get(
						'connect-with-google-pagespeed-from-site-settings-pages-google-pagespeed'
					)}
				</p>
			)}
		</div>
	);
}
