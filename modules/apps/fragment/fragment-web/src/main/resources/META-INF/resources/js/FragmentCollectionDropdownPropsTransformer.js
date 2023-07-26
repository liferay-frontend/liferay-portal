/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@liferay/frontend-js-react-web';
import {openModal} from 'frontend-js-web';
import {unmountComponentAtNode} from 'react-dom';

import ImportModal from './ImportModal';
import openDeleteFragmentCollectionModal from './openDeleteFragmentCollectionModal';

const ACTIONS = {
	deleteFragmentCollection({deleteFragmentCollectionURL}) {
		openDeleteFragmentCollectionModal({
			onDelete: () => {
				submitForm(document.hrefFm, deleteFragmentCollectionURL);
			},
		});
	},
	openImportCollectionView({importURL, portletNamespace, viewImportURL}) {
		if (Liferay.FeatureFlags['LPS-174939']) {
			const modalContainer = document.createElement('div');
			modalContainer.classList.add('cadmin');
			document.body.appendChild(modalContainer);

			const disposeModal = () => {
				if (modalContainer) {
					unmountComponentAtNode(modalContainer);
					document.body.removeChild(modalContainer);
				}
			};

			render(
				ImportModal,
				{
					disposeModal,
					importURL,
					portletNamespace,
				},
				modalContainer
			);
		}
		else {
			openModal({
				buttons: [
					{
						displayType: 'secondary',
						label: Liferay.Language.get('cancel'),
						type: 'cancel',
					},
					{
						label: Liferay.Language.get('import'),
						type: 'submit',
					},
				],
				onClose: () => {
					window.location.reload();
				},
				title: Liferay.Language.get('import'),
				url: viewImportURL,
			});
		}
	},
};

export default function propsTransformer({items, ...props}) {
	return {
		...props,
		items: items.map((item) => {
			return {
				...item,
				items: item.items?.map((child) => {
					return {
						...child,
						onClick(event) {
							const action = child.data?.action;

							if (action) {
								event.preventDefault();

								ACTIONS[action](child.data);
							}
						},
					};
				}),
			};
		}),
	};
}
