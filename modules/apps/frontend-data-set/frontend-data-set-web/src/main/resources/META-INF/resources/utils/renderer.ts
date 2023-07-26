/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FDSCellRenderer} from '@liferay/js-api/data-set';
import {ComponentType} from 'react';

// @ts-ignore

import InputCheckboxRenderer from '../cell_renderers/InputCheckboxRenderer';

// @ts-ignore

import InputDateTimeRenderer from '../cell_renderers/InputDateTimeRenderer';

// @ts-ignore

import InputTextRenderer from '../cell_renderers/InputTextRenderer';
import {InternalCellRenderer} from '../cell_renderers/InternalCellRenderer';

// @ts-ignore

import {getJsModule} from './modules';

const INPUT_RENDERERS: {[key: string]: ComponentType} = {
	checkbox: InputCheckboxRenderer,
	dateTime: InputDateTimeRenderer,
	text: InputTextRenderer,
};

export function getInputRendererById(id: string): ComponentType {
	const inputRenderer = INPUT_RENDERERS[id];

	if (!inputRenderer) {
		throw new Error(`No input renderer found with id "${id}"`);
	}

	return inputRenderer;
}

export interface Renderer {
	type: 'clientExtension' | 'internal';
}

export interface ClientExtensionCellRenderer extends Renderer {
	label?: string;
	name?: string;
	renderer: FDSCellRenderer;
	type: 'clientExtension';
}

export type CellRenderer = ClientExtensionCellRenderer | InternalCellRenderer;

const fetchedDataRenderers: Array<{
	cellRenderer: CellRenderer;
	url: string;
}> = [];

export async function getCellRendererByURL(
	url: string,
	type: 'clientExtension' | 'internal'
): Promise<CellRenderer> {
	const addedCellRenderer = fetchedDataRenderers.find(
		(contentRenderer) => contentRenderer.url === url
	);

	if (addedCellRenderer) {
		return addedCellRenderer.cellRenderer;
	}

	let cellRenderer: CellRenderer;

	if (url.includes(' from ')) {
		const [moduleName, symbolName] = getModuleAndSymbolNames(url);

		// @ts-ignore
		const module = await import(/* webpackIgnore: true */ moduleName);

		if (type === 'clientExtension') {
			cellRenderer = {
				renderer: module[symbolName],
				type,
			};
		}
		else {
			cellRenderer = {
				component: module[symbolName],
				type,
			};
		}
	}
	else {
		cellRenderer = {
			component: await getJsModule(url),
			type: 'internal',
		};
	}

	fetchedDataRenderers.push({
		cellRenderer,
		url,
	});

	return cellRenderer;
}

function getModuleAndSymbolNames(url: string): [string, string] {
	const parts = url.split(' from ');

	const moduleName = parts[1].trim();
	let symbolName = parts[0].trim();

	if (
		symbolName !== 'default' &&
		(!symbolName.startsWith('{') || !symbolName.endsWith('}'))
	) {
		throw new Error(`Invalid data renderer URL: ${url}`);
	}

	if (symbolName.startsWith('{')) {
		symbolName = symbolName.substring(1, symbolName.length - 1).trim();
	}

	return [moduleName, symbolName];
}

export interface InternalRenderer extends Renderer {
	component: React.ComponentType<any>;
	label: string;
	name: string;
	type: 'internal';
}
