/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useContext, useState} from 'react';

import FrontendDataSetContext from '../../../FrontendDataSetContext';
import {deactivateFilter} from '../../../utils/filters/deactivateFilter';
import ViewsContext from '../../../views/ViewsContext';
import Filter from './Filter';

interface IFilterResume {
	clientExtensionResolutionError?: string;
	disabled: boolean;
	id: string;
	label: string;
	moduleURL?: string;
	selectedItemsLabel: string;
	type: 'clientExtension' | 'dateRange' | 'selection';
}

function FilterResume(props: IFilterResume) {
	const {disabled, label, selectedItemsLabel} = props;

	const {onFilterChange} = useContext(FrontendDataSetContext);
	const [{filters}] = useContext(ViewsContext);

	const [open, setOpen] = useState(false);

	const button = (
		<ClayButton
			className={classNames(
				'c-ml-2',
				'component-label',
				'filter-resume',
				'tbar-label',
				open && 'active'
			)}
			disabled={disabled}
			displayType="secondary"
			size="sm"
		>
			<span className="inline-item inline-item-before">
				<ClayIcon symbol={open ? 'caret-top' : 'caret-bottom'} />
			</span>

			<span className="label-section">
				{label}: <strong>{selectedItemsLabel}</strong>
			</span>
		</ClayButton>
	);

	const dropDownButtonGroup = (
		<ClayButton.Group>
			<ClayDropDown
				active={open}
				className="d-inline-flex"
				onActiveChange={setOpen}
				trigger={button}
			>
				<li className="dropdown-subheader">{props.label}</li>

				<Filter {...props} onClose={() => setOpen(false)} />
			</ClayDropDown>

			<ClayButton
				aria-label={Liferay.Language.get('remove-filter')}
				className="filter-resume-close"
				disabled={props.disabled}
				displayType="secondary"
				monospaced
				onClick={() => {
					const filter = filters.find(
						(filter) => filter.id === props.id
					);

					if (!filter) {
						return;
					}

					onFilterChange({changedFilter: deactivateFilter(filter)});
				}}
				size="sm"
				title={Liferay.Language.get('remove-filter')}
			>
				<ClayIcon symbol="times-small" />
			</ClayButton>
		</ClayButton.Group>
	);

	return props.disabled ? button : dropDownButtonGroup;
}

export default FilterResume;
