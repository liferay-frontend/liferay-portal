/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ISearchQuery} from '@liferay/frontend-data-set-web';
import {State} from '@liferay/frontend-js-state-web';

const advancedSearchQueryAtom = State.atom<ISearchQuery>(
	'advancedSearchQuery',
	{
		query: '',
	}
);

export {advancedSearchQueryAtom};
