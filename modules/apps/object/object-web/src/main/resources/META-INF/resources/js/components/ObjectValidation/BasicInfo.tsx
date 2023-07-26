/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	Card,
	Input,
	InputLocalized,
	Toggle,
} from '@liferay/object-js-components-web';
import React from 'react';

import {TriggerEventContainer} from './TriggerEventContainer';
import {TabProps} from './useObjectValidationForm';

interface BasicInfoProps extends TabProps {
	componentLabel: string;
}

export function BasicInfo({
	componentLabel,
	disabled,
	errors,
	setValues,
	values,
}: BasicInfoProps) {
	return (
		<>
			<Card title={componentLabel}>
				<InputLocalized
					disabled={disabled}
					error={errors.name}
					label={Liferay.Language.get('label')}
					onChange={(name) => setValues({name})}
					placeholder={Liferay.Language.get('add-a-label')}
					required
					translations={values.name!}
				/>

				<Input
					disabled
					label={Liferay.Language.get('type')}
					value={values.engineLabel}
				/>

				<Toggle
					disabled={disabled}
					label={Liferay.Language.get('active-validation')}
					onToggle={(active) => setValues({active})}
					toggled={values.active}
				/>
			</Card>

			<TriggerEventContainer
				disabled={disabled}
				eventTypes={[{label: Liferay.Language.get('on-submission')}]}
			/>
		</>
	);
}
