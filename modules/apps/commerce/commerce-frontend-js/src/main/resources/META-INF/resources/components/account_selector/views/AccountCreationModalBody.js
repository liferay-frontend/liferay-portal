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

import ClayForm, {ClayInput, ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayMultiSelect from '@clayui/multi-select';
import {fetch} from 'frontend-js-web';
import React, {useState} from 'react';

const ORGANIZATIONS_ROOT_ENDPOINT = '/o/headless-admin-user/v1.0/organizations';
const ACCOUNTS_ROOT_ENDPOINT = '/o/headless-admin-user/v1.0/accounts';

const orgUrl = new URL(
	`${themeDisplay.getPathContext()}${ORGANIZATIONS_ROOT_ENDPOINT}`,
	themeDisplay.getPortalURL()
);

fetch(orgUrl.toString())
	.then((response) => response.json())
	.then((data) => {
		organizationData = data.items;
	});

function capitalizeWord(word) {
	return word[0].toUpperCase() + word.substr(1).toLowerCase();
}

let organizationData;

const filterOrganizations = (organizations, accountOrganizations) => {
	if (!accountOrganizations.length) {
		return organizations;
	}

	const accountValues = accountOrganizations.map((item) => item.value);

	return organizations.filter((org) => !accountValues.includes(org.value));
};

export default function AccountCreationModalBody({accountTypes}) {
	const [organizationQuery, setOrganizationQuery] = useState('');
	const [accountOrganizations, setAccountOrganizations] = useState([]);
	const [accountDescription, setAccountDescription] = useState('');
	const [accountName, setAccountName] = useState('');
	const [accountType, setAccountType] = useState(accountTypes[0]);
	const [accountTaxId, setAccountTaxId] = useState('');
	const [accountERC, setAccountERC] = useState('');

	const organizations = organizationData.map(({id, name}) => {
		return {label: name, value: id};
	});

	const createAccount = (event) => {
		event.preventDefault();

		const organizationIds = accountOrganizations.map(({value}) =>
			Number.parseInt(value, 10)
		);

		fetch(ACCOUNTS_ROOT_ENDPOINT, {
			body: JSON.stringify({
				description: accountDescription,
				externalReferenceCode: accountERC,
				id: accountTaxId,
				name: accountName,
				organizationIds,
				type: accountType,
			}),
			headers: {
				'Content-Type': 'application/json',
			},
			method: 'POST',
		})
			.then((response) => response.json())
			.catch((error) => {
				console.error(error);

				throw error;
			});
	};

	return (
		<ClayForm onSubmit={createAccount}>
			<ClayForm.Group>
				<label htmlFor="accountName">
					{Liferay.Language.get('account-name')}

					<span className="inline-item inline-item-after reference-mark">
						<ClayIcon symbol="asterisk" />

						<span className="hide-accessible sr-only">
							{Liferay.Language.get('required')}
						</span>
					</span>
				</label>

				<ClayInput
					name="accountName"
					onChange={(event) => setAccountName(event.target.value)}
					required
					type="text"
					value={accountName}
				/>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor="accountOrganizations">
					{Liferay.Language.get('organizations')}
				</label>

				<ClayMultiSelect
					closeButtonAriaLabel={Liferay.Language.get('remove')}
					inputName="accountOrganizations"
					items={accountOrganizations}
					onChange={setOrganizationQuery}
					onItemsChange={(newItems) => {
						setAccountOrganizations(newItems);
					}}
					sourceItems={filterOrganizations(
						organizations,
						accountOrganizations
					)}
					value={organizationQuery}
				/>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor="accountType">
					{Liferay.Language.get('type')}
				</label>

				<ClaySelect name="accountType">
					{accountTypes.map((type) => (
						<ClaySelect.Option
							key={type}
							label={capitalizeWord(type)}
							onChange={(event) =>
								setAccountType(event.target.value)
							}
							value={accountType}
						/>
					))}
				</ClaySelect>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor="accountTaxId">
					<span>{Liferay.Language.get('tax-id')}</span>

					<span
						className="label-icon lfr-portal-tooltip ml-2"
						data-tooltip-align="top"
						title={Liferay.Language.get('tax-id-help')}
					>
						<ClayIcon symbol="question-circle-full" />
					</span>
				</label>

				<ClayInput
					name="accountTaxId"
					onChange={(event) => setAccountTaxId(event.target.value)}
					type="text"
					value={accountTaxId}
				/>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor="accountERC">
					{Liferay.Language.get('external-reference-code')}
				</label>

				<ClayInput
					name="accountERC"
					onChange={(event) => setAccountERC(event.target.value)}
					type="text"
					value={accountERC}
				/>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor="accountDescription">
					{Liferay.Language.get('description')}
				</label>

				<ClayInput
					component="textarea"
					name="accountDescription"
					onChange={(event) =>
						setAccountDescription(event.target.value)
					}
					type="text"
					value={accountDescription}
				/>
			</ClayForm.Group>
		</ClayForm>
	);
}
