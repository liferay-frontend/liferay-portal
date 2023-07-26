/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	fetch,
	navigate,
	objectToFormData,
	openSelectionModal,
} from 'frontend-js-web';

import openDeleteVocabularyModal from './openDeleteVocabularyModal';

const ACTIONS = {
	deleteVocabularies({
		deleteVocabulariesURL,
		portletNamespace,
		viewVocabulariesURL,
	}) {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('delete'),
			multiple: true,
			onSelect: (selectedItems) => {
				if (selectedItems.length) {
					openDeleteVocabularyModal({
						multiple: true,
						onDelete: () => {
							fetch(deleteVocabulariesURL, {
								body: objectToFormData({
									[`${portletNamespace}rowIds`]: selectedItems
										.map((item) => item.value)
										.join(','),
								}),
								method: 'POST',
							}).then((response) => navigate(response.url));
						},
					});
				}
			},
			title: Liferay.Language.get('delete-vocabulary'),
			url: viewVocabulariesURL,
		});
	},
};

export default function propsTransformer({
	additionalProps: {deleteVocabulariesURL, viewVocabulariesURL},
	items,
	portletNamespace,
	...otherProps
}) {
	return {
		...otherProps,
		items: items.map((item) => {
			return {
				...item,
				onClick(event) {
					const action = item.data?.action;

					if (action) {
						event.preventDefault();

						ACTIONS[action]({
							deleteVocabulariesURL,
							portletNamespace,
							viewVocabulariesURL,
						});
					}
				},
			};
		}),
	};
}
