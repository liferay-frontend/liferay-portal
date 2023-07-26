/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.search.tuning.rankings.storage.RankingsDatabaseImporter;

/**
 * @author Bryan Engler
 */
public class RankingsDatabaseImporterUpgradeProcess extends UpgradeProcess {

	public RankingsDatabaseImporterUpgradeProcess(
		CompanyLocalService companyLocalService,
		RankingsDatabaseImporter rankingsDatabaseImporter) {

		_companyLocalService = companyLocalService;
		_rankingsDatabaseImporter = rankingsDatabaseImporter;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompany(
			company -> _rankingsDatabaseImporter.populateDatabase(
				company.getCompanyId()));
	}

	private final CompanyLocalService _companyLocalService;
	private final RankingsDatabaseImporter _rankingsDatabaseImporter;

}