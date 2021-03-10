/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {openModal} from 'frontend-js-web';

import {getReviewsHTML} from './Reviews';

export default function propsTransformer({actions, ...otherProps}) {
	return {
		...otherProps,
		actions: actions?.map((item) => {
			return {
				...item,
				onClick() {
					const action = item.data.action;

					if (action === 'showInfo') {
						openModal({
							title: Liferay.Language.get('info'),
							url: item.data.url,
						});
					}
					else if (action === 'showReviews') {
						openModal({
							bodyHTML: getReviewsHTML(),
							title: Liferay.Language.get('reviews'),
						});
					}
				},
			};
		}),
	};
}
