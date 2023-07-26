/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	FormError,
	Input,
	InputLocalized,
} from '@liferay/object-js-components-web';
import React from 'react';

import {NotificationTemplateError} from '../EditNotificationTemplate';

interface EmailNotificationSettingsProps {
	errors: FormError<NotificationTemplate & NotificationTemplateError>;
	selectedLocale: Locale;
	setValues: (values: Partial<NotificationTemplate>) => void;
	values: NotificationTemplate;
}

export function EmailNotificationSettings({
	errors,
	selectedLocale,
	setValues,
	values,
}: EmailNotificationSettingsProps) {
	return (
		<>
			<InputLocalized
				error={errors.to}
				label={Liferay.Language.get('to')}
				name="to"
				onChange={(translation) => {
					setValues({
						...values,
						recipients: [
							{
								...values.recipients[0],
								to: translation,
							},
						],
					});
				}}
				placeholder=""
				required
				selectedLocale={selectedLocale}
				translations={(values.recipients[0] as EmailRecipients).to}
			/>

			<div className="row">
				<div className="col-lg-6">
					<Input
						label={Liferay.Language.get('cc')}
						name="cc"
						onChange={({target}) =>
							setValues({
								...values,
								recipients: [
									{
										...values.recipients[0],
										cc: target.value,
									},
								],
							})
						}
						value={(values.recipients[0] as EmailRecipients).cc}
					/>
				</div>

				<div className="col-lg-6">
					<Input
						label={Liferay.Language.get('bcc')}
						name="bcc"
						onChange={({target}) =>
							setValues({
								...values,
								recipients: [
									{
										...values.recipients[0],
										bcc: target.value,
									},
								],
							})
						}
						value={(values.recipients[0] as EmailRecipients).bcc}
					/>
				</div>
			</div>

			<div className="row">
				<div className="col-lg-6">
					<Input
						error={errors.from}
						label={Liferay.Language.get('from-address')}
						name="fromAddress"
						onChange={({target}) =>
							setValues({
								...values,
								recipients: [
									{
										...values.recipients[0],
										from: target.value,
									},
								],
							})
						}
						required
						value={(values.recipients[0] as EmailRecipients).from}
					/>
				</div>

				<div className="col-lg-6">
					<InputLocalized
						error={errors.fromName}
						label={Liferay.Language.get('from-name')}
						name="fromName"
						onChange={(translation) => {
							setValues({
								...values,
								recipients: [
									{
										...values.recipients[0],
										fromName: translation,
									},
								],
							});
						}}
						placeholder=""
						required
						selectedLocale={selectedLocale}
						translations={
							(values.recipients[0] as EmailRecipients).fromName
						}
					/>
				</div>
			</div>
		</>
	);
}
