/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FDSCellRendererArgs} from '@liferay/js-api/data-set';
import React, {ComponentType, useContext, useEffect, useState} from 'react';

// @ts-ignore

import FrontendDataSetContext from '../../FrontendDataSetContext';
import {getInternalCellRenderer} from '../../cell_renderers/getInternalCellRenderer';
import ClientExtensionRenderer from '../../components/ClientExtensionRenderer';
import {
	CellRenderer,
	getCellRendererByURL,
	getInputRendererById,
} from '../../utils/renderer';

// @ts-ignore

import DndTableCell from './dnd_table/Cell';

function InlineEditInputRenderer({
	itemId,
	options,
	rootPropertyName,
	type,
	value,
	valuePath,
	...otherProps
}: any) {
	const {itemsChanges, updateItem} = useContext(
		FrontendDataSetContext as React.Context<any>
	);

	const [InputRenderer, setInputRenderer] = useState<ComponentType>(() =>
		getInputRendererById(type)
	);

	useEffect(() => {
		setInputRenderer(() => getInputRendererById(type));
	}, [type]);

	let inputValue = value;

	if (
		itemsChanges[itemId] &&
		typeof itemsChanges[itemId][rootPropertyName] !== 'undefined'
	) {
		inputValue = itemsChanges[itemId][rootPropertyName].value;
	}

	return (
		<InputRenderer
			{...otherProps}
			itemId={itemId}
			options={options}
			updateItem={(newValue: any) =>
				updateItem(itemId, rootPropertyName, valuePath, newValue)
			}
			value={inputValue}
		/>
	);
}

function TableCell({
	actions,
	inlineEditSettings,
	itemData,
	itemId,
	itemInlineChanges,
	options,
	rootPropertyName,
	value,
	valuePath,
	view,
}: any) {
	const {
		customDataRenderers,
		inlineEditingSettings,
		loadData,
		openSidePanel,
	} = useContext(FrontendDataSetContext as React.Context<any>);

	const [loading, setLoading] = useState(false);

	const contentRenderer = view.contentRenderer || 'default';

	const [cellRenderer, setCellRenderer] = useState<CellRenderer | null>(
		() => {
			if (view.contentRendererModuleURL) {
				return null;
			}

			if (customDataRenderers && customDataRenderers[contentRenderer]) {
				return {
					component: customDataRenderers[contentRenderer],
					type: 'internal',
				};
			}

			return getInternalCellRenderer(contentRenderer);
		}
	);

	useEffect(() => {
		if (!loading && view.contentRendererModuleURL && !cellRenderer) {
			setLoading(true);

			getCellRendererByURL(
				view.contentRendererModuleURL,
				view.contentRendererClientExtension
					? 'clientExtension'
					: 'internal'
			)
				.then((cellRenderer) => {
					setCellRenderer(() => cellRenderer);

					setLoading(false);
				})
				.catch((error) => {
					console.error(
						`Unable to load FDS cell renderer at ${view.contentRendererModuleURL}:`,
						error
					);

					setCellRenderer(() => getInternalCellRenderer('default'));

					setLoading(false);
				});
		}
	}, [view, loading, cellRenderer]);

	if (
		inlineEditSettings &&
		(itemInlineChanges || inlineEditingSettings?.alwaysOn)
	) {
		return (
			<DndTableCell columnName={String(options.fieldName)}>
				<InlineEditInputRenderer
					actions={actions}
					itemData={itemData}
					itemId={itemId}
					options={options}
					rootPropertyName={rootPropertyName}
					type={inlineEditSettings.type}
					value={value}
					valuePath={valuePath}
				/>
			</DndTableCell>
		);
	}

	if (!cellRenderer || loading) {
		return (
			<DndTableCell columnName={String(options.fieldName)}>
				<span
					aria-hidden="true"
					className="loading-animation loading-animation-sm"
				/>
			</DndTableCell>
		);
	}

	if (cellRenderer.type === 'clientExtension') {
		return (
			<DndTableCell columnName={String(options.fieldName)}>
				<ClientExtensionRenderer<FDSCellRendererArgs>
					args={{value}}
					renderer={cellRenderer.renderer}
				/>
			</DndTableCell>
		);
	}

	const CellRendererComponent = cellRenderer.component;

	return (
		<DndTableCell columnName={String(options.fieldName)}>
			<CellRendererComponent
				actions={actions}
				itemData={itemData}
				itemId={itemId}
				loadData={loadData}
				openSidePanel={openSidePanel}
				options={options}
				rootPropertyName={rootPropertyName}
				value={value}
				valuePath={valuePath}
			/>
		</DndTableCell>
	);
}

export default TableCell;
