/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import FrontendDataSetContext from '../FrontendDataSetContext';
import {getItemLabel} from '../utils/getItemLabel';
import recentlyVisited from '../utils/recentlyVisited';
import ViewsContext from '../views/ViewsContext';
import DefaultContent from './DefaultRenderer';

interface ILinkRendererProps {
	itemData?: any;
	options?: {
		decoration?: React.ComponentProps<typeof ClayLink>['decoration'];
		displayType?: React.ComponentProps<typeof ClayLink>['displayType'];
	};
	value: {
		href: string;
		label: string;
	};
}

function LinkRenderer({itemData, options, value}: ILinkRendererProps) {
	const {id, searchSuggestionsEnabled} = useContext(FrontendDataSetContext);

	const [{activeView}]: any = useContext(ViewsContext);

	return (
		<div
			className={classNames({'table-list-title': !options?.displayType})}
		>
			<ClayLink
				decoration={options?.decoration}
				displayType={options?.displayType}
				href={value?.href}
				onClick={() => {
					if (searchSuggestionsEnabled) {
						recentlyVisited.add(id, {
							href: value?.href,
							label: getItemLabel(itemData, {
								accessibleNameField:
									activeView?.schema?.accessibleNameField,
								fallback: value?.label,
							}),
						});
					}
				}}
			>
				<DefaultContent value={value?.label} />
			</ClayLink>
		</div>
	);
}

LinkRenderer.propTypes = {
	value: PropTypes.shape({
		href: PropTypes.string,
		label: PropTypes.string,
	}),
};

export default LinkRenderer;
