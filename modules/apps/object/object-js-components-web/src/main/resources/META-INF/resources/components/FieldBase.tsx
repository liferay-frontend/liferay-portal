/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';
import React, {ReactNode} from 'react';

import {FieldFeedback} from './FieldFeedback';

import './FieldBase.scss';

function RequiredMask() {
	return (
		<>
			<span className="ml-1 reference-mark text-warning">
				<ClayIcon symbol="asterisk" />
			</span>

			<span className="hide-accessible sr-only">
				{Liferay.Language.get('mandatory')}
			</span>
		</>
	);
}

export function FieldBase({
	children,
	className,
	disabled,
	errorMessage,
	helpMessage,
	hideFeedback,
	id,
	label,
	required,
	tooltip,
	warningMessage,
}: IProps) {
	return (
		<ClayForm.Group
			className={classNames(className, {
				'has-error': errorMessage,
				'has-warning': warningMessage && !errorMessage,
			})}
		>
			{label && (
				<label className={classNames({disabled})} htmlFor={id}>
					{label}

					{required && <RequiredMask />}
				</label>
			)}

			{tooltip && (
				<>
					&nbsp;
					<ClayTooltipProvider>
						<span data-tooltip-align="top" title={tooltip}>
							<ClayIcon
								className="lfr-objects__field-base-tooltip-icon"
								symbol="question-circle-full"
							/>
						</span>
					</ClayTooltipProvider>
				</>
			)}

			{children}

			{!hideFeedback && (
				<FieldFeedback
					errorMessage={errorMessage}
					helpMessage={helpMessage}
					warningMessage={warningMessage}
				/>
			)}
		</ClayForm.Group>
	);
}

interface IProps {
	children: ReactNode;
	className?: string;
	disabled?: boolean;
	errorMessage?: string;
	helpMessage?: string;
	hideFeedback?: boolean;
	id?: string;
	label?: string;
	required?: boolean;
	tooltip?: string;
	warningMessage?: string;
}
