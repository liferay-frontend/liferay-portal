/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.upgrade.registry;

import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.search.tuning.synonyms.storage.SynonymSetsDatabaseImporter;
import com.liferay.portal.search.tuning.synonyms.web.internal.upgrade.v1_0_0.SynonymSetsDatabaseImporterUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = UpgradeStepRegistrator.class)
public class SynonymsWebUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"0.0.0", "1.0.0",
			new SynonymSetsDatabaseImporterUpgradeProcess(
				_companyLocalService, _synonymSetsDatabaseImporter));
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private SynonymSetsDatabaseImporter _synonymSetsDatabaseImporter;

}