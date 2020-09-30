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

function deleteImages({constantsCMD, constantsDELETE, namespace}) {
	if (
		confirm(
			Liferay.Language.get(
				'are-you-sure-you-want-to-delete-the-selected-images'
			)
		)
	) {
		const form = document.getElementById(`${namespace}fm`);

		if (form) {
			const cmd = form.querySelector(`#${namespace}${constantsCMD}`);

			if (cmd) {
				cmd.setAttribute('value', constantsDELETE);
			}

			const deleteFileEntryIds = form.querySelector(
				`#${namespace}deleteFileEntryIds`
			);

			if (deleteFileEntryIds) {
				deleteFileEntryIds.setAttribute(
					'value',
					Liferay.Util.listCheckedExcept(
						form,
						`${namespace}allRowIds`
					)
				);
			}

			submitForm(form);
		}
	}
}

const ACTIONS = {
	deleteImages,
};

export default function BlogImagesManagementToolbarHandler(props) {
	Liferay.componentReady('blogImagesManagementToolbar').then(
		(managementToolbar) => {
			managementToolbar.on('actionItemClicked', (event) => {
				const itemData = event?.data?.item?.data;

				if (itemData && itemData.action && ACTIONS[itemData.action]) {
					ACTIONS[itemData.action](props);
				}
			});
		}
	);
}
