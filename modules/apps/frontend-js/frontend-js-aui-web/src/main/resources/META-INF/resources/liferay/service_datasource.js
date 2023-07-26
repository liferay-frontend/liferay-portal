/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-service-datasource',
	(A) => {
		const ServiceDataSource = A.Component.create({
			EXTENDS: A.DataSource.Local,
			NAME: 'servicedatasource',
			prototype: {
				_defRequestFn(event) {
					const instance = this;

					const source = instance.get('source');

					source(
						event.request,
						A.rbind('_serviceCallbackFn', instance, event)
					);
				},

				_serviceCallbackFn(object, xHR, event) {
					const instance = this;

					instance.fire(
						'data',
						A.mix(
							{
								data: object,
							},
							event
						)
					);
				},
			},
		});

		Liferay.Service.DataSource = ServiceDataSource;
	},
	'',
	{
		requires: ['aui-base', 'datasource-local'],
	}
);
