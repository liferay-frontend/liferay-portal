/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPanel from '@clayui/panel';
import {Toggle} from '@liferay/object-js-components-web';
import React from 'react';

import './TranslationsContainer.scss';

interface TranslationsContainerProps {
	setValues: (values: Partial<ObjectDefinition>) => void;
	values: Partial<ObjectDefinition>;
}

export function TranslationsContainer({
	setValues,
	values,
}: TranslationsContainerProps) {
	return (
		<ClayPanel
			collapsable
			defaultExpanded
			displayTitle={Liferay.Language.get('translations')}
			displayType="unstyled"
		>
			<ClayPanel.Body>
				<div className="lfr-objects-translations-container">
					<Toggle
						disabled={values.active}
						label={Liferay.Language.get(
							'enable-entry-translations'
						)}
						onToggle={() =>
							setValues({
								enableLocalization: !values.enableLocalization,
							})
						}
						toggled={values.enableLocalization}
						tooltip={Liferay.Language.get(
							'enable-entry-translations-in-all-fields'
						)}
					/>
				</div>
			</ClayPanel.Body>
		</ClayPanel>
	);
}
