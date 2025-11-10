/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {ISearchQuery} from '@liferay/frontend-data-set-web';
import {useLiferayState} from '@liferay/frontend-js-state-web/react';
import React, {useEffect, useState} from 'react';

import {advancedSearchQueryAtom} from '../utils/atoms';

const Search = () => {
	const [advancedSearchQueryValue, setAdvancedSearchQueryValue] =
		useLiferayState<ISearchQuery>(advancedSearchQueryAtom);

	const [advancedQuery, setAdvancedQuery] = useState(
		advancedSearchQueryValue.query ?? ''
	);

	useEffect(() => {
		setAdvancedQuery(advancedSearchQueryValue.query);
	}, [advancedSearchQueryValue]);

	return (
		<ClayLayout.ContainerFluid>
			<ClayInput.Group className="pt-2">
				<ClayInput.GroupItem>
					<ClayInput
						className="form-control"
						component="input"
						onChange={({target: {value}}) =>
							setAdvancedQuery(value)
						}
						placeholder="Search in Advanced tab of Frontend Data Set Sample"
						value={advancedQuery}
					/>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem>
					<ClayButton
						data-qa-id="searchFDSSampleButton"
						onClick={() => {
							setAdvancedSearchQueryValue({query: advancedQuery});
						}}
					>
						Search
					</ClayButton>
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</ClayLayout.ContainerFluid>
	);
};

export default Search;
