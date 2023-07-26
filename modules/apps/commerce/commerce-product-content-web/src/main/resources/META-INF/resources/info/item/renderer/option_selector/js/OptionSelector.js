/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CommerceFrontendUtils} from 'commerce-frontend-js';

import {updateProductFields} from './util/index';

const {Events, FormUtils} = CommerceFrontendUtils;
const {DDMFormHandler} = FormUtils;

export default function ({
	accountId,
	channelId,
	cpDefinitionId,
	namespace,
	productId,
	quantity,
}) {
	Liferay.componentReady('ProductOptions' + cpDefinitionId).then(
		(DDMFormInstance) => {
			if (DDMFormInstance) {
				new DDMFormHandler({
					DDMFormInstance,
					accountId,
					channelId,
					namespace,
					productId,
					quantity,
				});

				Liferay.on(
					`${namespace}${Events.CP_INSTANCE_CHANGED}`,
					updateProductFields
				);
			}
		}
	);
}
