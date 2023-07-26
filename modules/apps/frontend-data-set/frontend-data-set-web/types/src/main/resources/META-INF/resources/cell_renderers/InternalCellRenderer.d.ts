/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {Renderer} from '../utils/renderer';
export interface InternalCellRenderer extends Renderer {
	component: React.ComponentType<any>;
	label?: string;
	name?: string;
	type: 'internal';
}
export declare const INTERNAL_CELL_RENDERERS: Array<InternalCellRenderer>;
