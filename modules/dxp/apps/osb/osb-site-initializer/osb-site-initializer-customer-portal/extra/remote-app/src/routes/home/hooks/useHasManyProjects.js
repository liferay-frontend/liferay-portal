/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

export default function useHasManyProjects(totalCount, limitTotalCount) {
	const [maxTotalCount, setMaxTotalCount] = useState(0);

	useEffect(() => {
		if (totalCount > maxTotalCount) {
			setMaxTotalCount(totalCount);
		}
	}, [totalCount, maxTotalCount]);

	return maxTotalCount > limitTotalCount;
}
