/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.internal.configuration;

import com.liferay.portal.kernel.redirect.RedirectURLSettings;
import com.liferay.redirect.internal.configuration.admin.service.RedirectURLManagedServiceFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(service = RedirectURLSettings.class)
public class RedirectURLSettingsImpl implements RedirectURLSettings {

	@Override
	public String[] getAllowedDomains(long companyId) {
		RedirectURLConfiguration redirectURLConfiguration =
			_redirectURLManagedServiceFactory.
				getCompanyRedirectURLConfiguration(companyId);

		return redirectURLConfiguration.allowedDomains();
	}

	@Override
	public String[] getAllowedIPs(long companyId) {
		RedirectURLConfiguration redirectURLConfiguration =
			_redirectURLManagedServiceFactory.
				getCompanyRedirectURLConfiguration(companyId);

		return redirectURLConfiguration.allowedIPs();
	}

	@Override
	public String getSecurityMode(long companyId) {
		RedirectURLConfiguration redirectURLConfiguration =
			_redirectURLManagedServiceFactory.
				getCompanyRedirectURLConfiguration(companyId);

		return redirectURLConfiguration.securityMode();
	}

	@Reference
	private RedirectURLManagedServiceFactory _redirectURLManagedServiceFactory;

}