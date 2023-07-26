/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';
import {v4 as uuidv4} from 'uuid';

const PREFIX = uuidv4();

let nextId = 0;

export function useId() {
	return useMemo(() => `useId_${PREFIX}_${nextId++}`, []);
}
