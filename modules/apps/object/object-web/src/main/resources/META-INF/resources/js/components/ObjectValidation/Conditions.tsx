/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import 'codemirror/mode/groovy/groovy';
import {
	Card,
	CodeEditor,
	InputLocalized,
	SidebarCategory,
} from '@liferay/object-js-components-web';
import React from 'react';

import {TabProps} from './useObjectValidationForm';

interface ConditionsProps extends TabProps {
	objectValidationRuleElements: SidebarCategory[];
}

export function Conditions({
	disabled,
	errors,
	objectValidationRuleElements,
	setValues,
	values,
}: ConditionsProps) {
	const engine = values.engine;
	const ddmTooltip = {
		content: Liferay.Language.get(
			'use-the-expression-builder-to-define-the-format-of-a-valid-object-entry'
		),
		symbol: 'question-circle-full',
	};
	let placeholder;

	if (engine === 'groovy') {
		placeholder = Liferay.Language.get(
			'insert-a-groovy-script-to-define-your-validation'
		);
	}
	else if (engine === 'ddm') {
		placeholder = Liferay.Language.get(
			'add-elements-from-the-sidebar-to-define-your-validation'
		);
	}
	else {
		placeholder = '';
	}

	return (
		<>
			<Card
				title={values.engineLabel!}
				tooltip={engine === 'ddm' ? ddmTooltip : null}
			>
				<CodeEditor
					error={errors.script}
					mode={engine}
					onChange={(script?: string, lineCount?: number) =>
						setValues({lineCount, script})
					}
					placeholder={placeholder}
					readOnly={disabled}
					sidebarElements={objectValidationRuleElements}
					value={values.script ?? ''}
				/>
			</Card>

			<Card title={Liferay.Language.get('error-message')}>
				<InputLocalized
					disabled={disabled}
					error={errors.errorLabel}
					label={Liferay.Language.get('message')}
					onChange={(errorLabel) => setValues({errorLabel})}
					placeholder={Liferay.Language.get('add-an-error-message')}
					required
					translations={values.errorLabel!}
				/>
			</Card>
		</>
	);
}
