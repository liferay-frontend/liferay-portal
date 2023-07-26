/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import React from 'react';

export default function SectionLabel({section}) {
	return (
		<>
			{section && (
				<ClayLabel
					className="bg-light border-0 stretched-link-layer text-uppercase"
					displayType="secondary"
					large
				>
					{section.title}
				</ClayLabel>
			)}
		</>
	);
}
