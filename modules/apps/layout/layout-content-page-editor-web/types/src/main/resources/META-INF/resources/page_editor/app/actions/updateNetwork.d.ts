/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {ServiceNetworkStatusType} from '../config/constants/serviceNetworkStatusTypes';
export default function updateNetwork(network: {
	error?: string | null;
	status: ServiceNetworkStatusType;
}): {
	readonly network: {
		error?: string | null | undefined;
		status: ServiceNetworkStatusType;
	};
	readonly type: 'UPDATE_NETWORK';
};
