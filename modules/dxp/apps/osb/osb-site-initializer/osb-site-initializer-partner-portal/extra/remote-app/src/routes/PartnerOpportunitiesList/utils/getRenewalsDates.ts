/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PartnerOpportunitiesColumnKey} from '../../../common/enums/partnerOpportunitiesColumnKey';
import {customFormatDateOptions} from '../../../common/utils/constants/customFormatDateOptions';
import getDateCustomFormat from '../../../common/utils/getDateCustomFormat';

export default function getRenewalsDates(closeDate?: string) {
	if (closeDate) {
		const closeDateCustomFormat = getDateCustomFormat(
			closeDate,
			customFormatDateOptions.SHORT_MONTH
		);

		return {
			[PartnerOpportunitiesColumnKey.CLOSE_DATE]: closeDateCustomFormat,
		};
	}
}
