/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.a11y.web.internal.upgrade.v1_0_0;

import com.liferay.frontend.js.a11y.web.internal.configuration.A11yConfiguration;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Shuyang Zhou
 */
public class A11yConfigurationUpgradeProcess extends UpgradeProcess {

	public A11yConfigurationUpgradeProcess(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Override
	protected void doUpgrade() throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.pid=com.liferay.frontend.js.a11y.web.internal." +
				"configuration.FFA11yConfiguration)");

		if (ArrayUtil.isEmpty(configurations)) {
			return;
		}

		Configuration ffA11yConfiguration = configurations[0];

		Dictionary<String, Object> ffA11yProperties =
			ffA11yConfiguration.getProperties();

		if (GetterUtil.getBoolean(ffA11yProperties.get("enable"))) {
			Configuration a11yConfiguration =
				_configurationAdmin.getConfiguration(
					A11yConfiguration.class.getName(), "?");

			Dictionary<String, Object> a11yProperties =
				a11yConfiguration.getProperties();

			if (a11yProperties == null) {
				a11yProperties = new HashMapDictionary<>();
			}

			if (!GetterUtil.getBoolean(a11yProperties.get("enable"))) {
				a11yProperties.put("enable", true);

				a11yConfiguration.update(a11yProperties);
			}
		}

		ffA11yConfiguration.delete();
	}

	private final ConfigurationAdmin _configurationAdmin;

}