/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '..';
import useSWR from 'swr';

import LiferayAccountBrief from '../../../interfaces/liferayAccountBrief';
import Role from '../../../interfaces/role';
import {LiferayAPIs} from '../common/enums/apis';
import liferayFetcher from '../common/utils/fetcher';

interface Telephone {
	id: number;
	phoneNumber: string;
}

interface UserAccountContactInformation {
	telephones: Telephone[];
}

interface UserAccount {
	accountBriefs: LiferayAccountBrief[];
	emailAddress: string;
	familyName: string;
	givenName: string;
	id: number;
	roleBriefs: Role[];
	userAccountContactInformation: UserAccountContactInformation;
}

export default function useGetMyUserAccount(skip?: boolean) {
	return useSWR(
		skip
			? null
			: [
					`/o/${LiferayAPIs.HEADERLESS_ADMIN_USER}/my-user-account`,
					Liferay.authToken,
			  ],
		(url, token) => liferayFetcher<UserAccount>(url, token)
	);
}
