/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {NetworkStatus} from '@apollo/client';
import SearchBuilder from '../core/SearchBuilder';
import {useGetKoroneikiAccounts} from '../services/liferay/graphql/koroneiki-accounts';
import useSearchTerm from './useSearchTerm';

export default function useKoroneikiAccounts() {
	const {data, fetchMore, networkStatus, refetch} = useGetKoroneikiAccounts({
		notifyOnNetworkStatusChange: true,
	});

	const search = useSearchTerm((searchTerm: string) =>
		refetch({
			filter: searchTerm
				? new SearchBuilder()
						.contains('name', searchTerm)
						.or()
						.contains('code', searchTerm)
						.build()
				: undefined,
			page: 1,
		})
	);

	return {
		data,
		fetchMore,
		fetching: networkStatus === NetworkStatus.fetchMore,
		loading: networkStatus === NetworkStatus.loading,
		networkStatus,
		refetch,
		search,
		searching: networkStatus === NetworkStatus.setVariables,
	};
}
