/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-web';

import openDeletePageTemplateModal from '../modal/openDeletePageTemplateModal';

const ACTIONS = {
	deleteLayoutPageTemplateCollection({
		deleteLayoutPageTemplateCollectionURL,
	}) {
		openDeletePageTemplateModal({
			onDelete: () => {
				submitForm(
					document.hrefFm,
					deleteLayoutPageTemplateCollectionURL
				);
			},
			title: Liferay.Language.get('page-template-set'),
		});
	},

	permissionsLayoutPageTemplateCollection({
		permissionsLayoutPageTemplateCollectionURL,
	}) {
		openModal({
			title: Liferay.Language.get('permissions'),
			url: permissionsLayoutPageTemplateCollectionURL,
		});
	},
};

export default function LayoutPageTemplateEntryPropsTransformer({
	items,
	portletNamespace,
	...otherProps
}) {
	return {
		...otherProps,
		items: items?.map((item) => {
			return {
				...item,
				items: item.items?.map((child) => {
					return {
						...child,
						onClick(event) {
							const action = child.data?.action;

							if (action) {
								event.preventDefault();

								ACTIONS[action](child.data, portletNamespace);
							}
						},
					};
				}),
			};
		}),
		portletNamespace,
	};
}
