/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FDSCellRenderer} from '@liferay/js-api/data-set';
import {ComponentType} from 'react';
import {InternalCellRenderer} from '../cell_renderers/InternalCellRenderer';
export declare function getInputRendererById(id: string): ComponentType;
export interface Renderer {
	type: 'clientExtension' | 'internal';
}
export interface ClientExtensionCellRenderer extends Renderer {
	label?: string;
	name?: string;
	renderer: FDSCellRenderer;
	type: 'clientExtension';
}
export declare type CellRenderer =
	| ClientExtensionCellRenderer
	| InternalCellRenderer;
export declare function getCellRendererByURL(
	url: string,
	type: 'clientExtension' | 'internal'
): Promise<CellRenderer>;
export interface InternalRenderer extends Renderer {
	component: React.ComponentType<any>;
	label: string;
	name: string;
	type: 'internal';
}
