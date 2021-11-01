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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayLink from '@clayui/link';
import React, {useContext, useState} from 'react';

import {
	PAGE_SPEED_API_KEY_ERROR_CODE,
	PAGE_SPEED_API_KEY_INVALID_STATUS,
	PAGE_SPEED_SERVER_ERROR_CODE,
} from '../../constants/googlePageSpeed';
import {StoreStateContext} from '../../context/StoreContext';
import ErrorAlertExtendedInfo from './ErrorAlertExtendedInfo';

const ErrorAlert = () => {
	const [showErrorInfo, setShowErrorInfo] = useState(false);
	const expandInfoHandler = () =>
		setShowErrorInfo((currentValue) => !currentValue);

	const {data, error} = useContext(StoreStateContext);

	const apiKeyError =
		error?.code === PAGE_SPEED_API_KEY_ERROR_CODE &&
		error?.status === PAGE_SPEED_API_KEY_INVALID_STATUS;

	const serverError = error?.code === PAGE_SPEED_SERVER_ERROR_CODE;
	const pageCanNotBeAudited = serverError || data?.privateLayout;

	const unknownError = !apiKeyError && !pageCanNotBeAudited;

	const userHasNotPrivileges = !data?.configureGooglePageSpeedURL;

	const title = apiKeyError
		? Liferay.Language.get('the-api-key-is-invalid')
		: pageCanNotBeAudited
		? Liferay.Language.get('this-page-cannot-be-audited')
		: Liferay.Language.get('an-unexpected-error-occurred');

	const actionTitle = showErrorInfo
		? Liferay.Language.get('hide-details')
		: Liferay.Language.get('show-details');

	return (
		<ClayAlert
			displayType={apiKeyError || unknownError ? 'danger' : 'warning'}
			title={title}
			variant="stripe"
		>
			{pageCanNotBeAudited && (
				<span>
					{`${Liferay.Language.get(
						'private-local-or-intranet-pages-cannot-be-audited-as-they-are-not-accessible-from-the-internet'
					)} `}
				</span>
			)}

			{userHasNotPrivileges && apiKeyError && (
				<span>
					{`${Liferay.Language.get(
						'you-can-set-the-api-key-from-site-settings-pages-google-pagespeed-insights'
					)} `}
				</span>
			)}

			{(apiKeyError || serverError) && (
				<ClayButton
					className="link-primary"
					displayType="unstyled"
					onClick={expandInfoHandler}
				>
					{actionTitle}
				</ClayButton>
			)}

			{showErrorInfo && <ErrorAlertExtendedInfo error={error} />}

			{apiKeyError && data?.configureGooglePageSpeedURL && (
				<ClayAlert.Footer>
					<ClayLink
						className="alert-btn btn btn-outline-danger"
						href={data?.configureGooglePageSpeedURL}
					>
						{Liferay.Language.get('set-api-key')}
					</ClayLink>
				</ClayAlert.Footer>
			)}
		</ClayAlert>
	);
};

export default ErrorAlert;
