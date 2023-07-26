/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * @deprecated As of Athanasius (7.3.x), replaced by `Liferay.Util.openToast`.
 */

AUI.add(
	'liferay-notification',
	(A) => {
		const Notification = A.Component.create({
			EXTENDS: Liferay.Alert,

			NAME: 'liferaynotification',

			prototype: {
				_getAlertsContainer(targetNode) {
					const instance = this;

					targetNode = targetNode || A.one('body');

					let alertsContainer = instance._alertsContainer;

					if (!alertsContainer) {
						const rootNode =
							targetNode || instance.get('rootNode') || A;

						alertsContainer =
							(targetNode &&
								targetNode.one(
									'.lfr-notification-container'
								)) ||
							rootNode.one('.lfr-notification-container');

						if (!alertsContainer) {
							alertsContainer = A.Node.create(
								instance.TPL_ALERTS_CONTAINER
							);

							targetNode.prepend(alertsContainer);
						}

						instance._alertsContainer = alertsContainer;
					}

					return alertsContainer;
				},

				TPL_ALERT_NODE: '<div class="lfr-notification-wrapper"></div>',

				TPL_ALERTS_CONTAINER:
					'<div class="lfr-notification-container"></div>',
			},
		});

		Liferay.Notification = Notification;
	},
	'',
	{
		requires: ['liferay-alert'],
	}
);
