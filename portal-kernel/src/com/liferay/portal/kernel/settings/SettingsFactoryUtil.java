/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.settings;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.List;

/**
 * @author Raymond Augé
 * @author Jorge Ferrer
 */
public class SettingsFactoryUtil {

	public static ArchivedSettings getPortletInstanceArchivedSettings(
			long groupId, String portletId, String name)
		throws SettingsException {

		return _settingsFactory.getPortletInstanceArchivedSettings(
			groupId, portletId, name);
	}

	public static List<ArchivedSettings> getPortletInstanceArchivedSettingsList(
		long groupId, String portletId) {

		return _settingsFactory.getPortletInstanceArchivedSettingsList(
			groupId, portletId);
	}

	public static Settings getSettings(SettingsLocator settingsLocator)
		throws SettingsException {

		return _settingsFactory.getSettings(settingsLocator);
	}

	public static SettingsFactory getSettingsFactory() {
		return _settingsFactory;
	}

	private static volatile SettingsFactory _settingsFactory =
		ServiceProxyFactory.newServiceTrackedInstance(
			SettingsFactory.class, SettingsFactoryUtil.class,
			"_settingsFactory", true);

}