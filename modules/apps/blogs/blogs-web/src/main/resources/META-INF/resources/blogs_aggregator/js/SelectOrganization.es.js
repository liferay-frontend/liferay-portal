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

import {openSelectionModal, toggleDisabled} from 'frontend-js-web';

export default function selectOrganization({namespace, title, url}) {
	const selectEventName = `${namespace}selectOrganization`;

	const selectOrganizationButton = document.getElementById(
		`${namespace}selectOrganizationButton`
	);

	selectOrganizationButton.addEventListener('click', () =>
		openSelectionModal({
			onSelect: (event) => {
				const form = document.getElementById(`${namespace}fm`);

				if (form) {
					const organizationId = form.querySelector(
						`#${namespace}organizationId`
					);

					if (organizationId) {
						organizationId.setAttribute('value', event.entityid);
					}

					const organizationName = form.querySelector(
						`#${namespace}organizationName`
					);

					if (organizationName) {
						organizationName.setAttribute(
							'value',
							event.entityname
						);
					}
				}

				toggleDisabled(`#${namespace}removeOrganizationButton`, false);
			},
			selectEventName,
			title,
			url,
		})
	);
}
