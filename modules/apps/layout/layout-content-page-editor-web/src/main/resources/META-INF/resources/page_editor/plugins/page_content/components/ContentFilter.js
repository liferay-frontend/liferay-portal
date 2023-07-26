/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import {SearchForm} from '@liferay/layout-js-components-web';
import React from 'react';

export default function ContentFilter({
	contentTypes,
	onChangeInput,
	onChangeSelect,
	selectedType,
}) {
	return (
		<div className="flex-shrink-0 page-editor__page-contents__content-filter px-3">
			<p className="mb-4 page-editor__page-contents__content-filter__help">
				{Liferay.Language.get('content-filtering-help')}
			</p>

			<ClayDropDown
				alignmentPosition={Align.BottomLeft}
				className="mb-2"
				closeOnClick
				menuElementAttrs={{
					containerProps: {
						className: 'cadmin',
					},
				}}
				trigger={
					<ClayButton
						aria-label={Liferay.Language.get(
							'filter-by-content-type'
						)}
						className="form-control form-control-select form-control-sm text-left"
						displayType="unstyled"
						size="sm"
						type="button"
					>
						<span>{selectedType}</span>
					</ClayButton>
				}
			>
				<ClayDropDown.ItemList role="listbox">
					{contentTypes?.map((type) => (
						<React.Fragment key={type}>
							<ClayDropDown.Item
								onClick={() => onChangeSelect(type)}
								symbolRight={
									selectedType === type ? 'check' : undefined
								}
							>
								{type}
							</ClayDropDown.Item>
						</React.Fragment>
					))}
				</ClayDropDown.ItemList>
			</ClayDropDown>

			<SearchForm
				className="mb-3"
				label={Liferay.Language.get('search-content')}
				onChange={onChangeInput}
			/>
		</div>
	);
}
