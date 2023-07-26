/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getCheckedCheckboxes, postForm} from 'frontend-js-web';

export default function propsTransformer({
	additionalProps: {
		deleteNotificationsURL,
		markNotificationsAsReadURL,
		markNotificationsAsUnreadURL,
	},
	portletNamespace,
	...otherProps
}) {
	const deleteNotifications = () => {
		const form = document.getElementById(`${portletNamespace}fm`);

		if (form) {
			postForm(form, {
				data: {
					deleteEntryIds: getCheckedCheckboxes(
						form,
						`${portletNamespace}allRowIds`
					),
				},
				url: deleteNotificationsURL,
			});
		}
	};

	const markNotificationsAsRead = () => {
		const form = document.getElementById(`${portletNamespace}fm`);

		if (form) {
			form.setAttribute('method', 'post');

			submitForm(form, markNotificationsAsReadURL);
		}
	};

	const markNotificationsAsUnread = () => {
		const form = document.getElementById(`${portletNamespace}fm`);

		if (form) {
			form.setAttribute('method', 'post');

			submitForm(form, markNotificationsAsUnreadURL);
		}
	};

	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			const action = item?.data?.action;

			if (action === 'deleteNotifications') {
				deleteNotifications();
			}
			else if (action === 'markNotificationsAsRead') {
				markNotificationsAsRead();
			}
			else if (action === 'markNotificationsAsUnread') {
				markNotificationsAsUnread();
			}
		},
	};
}
