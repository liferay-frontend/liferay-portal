/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.configuration.helper;

import com.liferay.change.tracking.configuration.CTSettingsConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(
	configurationPid = "com.liferay.change.tracking.configuration.CTSettingsConfiguration",
	service = CTSettingsConfigurationHelper.class
)
public class CTSettingsConfigurationHelper {

	public CTSettingsConfiguration getCTSettingsConfiguration(long companyId) {
		return _getCTSettingsConfiguration(companyId);
	}

	public long getDefaultCTCollectionTemplateId(long companyId) {
		CTSettingsConfiguration ctSettingsConfiguration =
			_getCTSettingsConfiguration(companyId);

		return ctSettingsConfiguration.defaultCTCollectionTemplateId();
	}

	public long getDefaultSandboxCTCollectionTemplateId(long companyId) {
		CTSettingsConfiguration ctSettingsConfiguration =
			_getCTSettingsConfiguration(companyId);

		return ctSettingsConfiguration.defaultSandboxCTCollectionTemplateId();
	}

	public boolean isDefaultCTCollectionTemplate(
		long companyId, long ctCollectionTemplateId) {

		CTSettingsConfiguration ctSettingsConfiguration =
			_getCTSettingsConfiguration(companyId);

		if (ctSettingsConfiguration.defaultCTCollectionTemplateId() ==
				ctCollectionTemplateId) {

			return true;
		}

		return false;
	}

	public boolean isDefaultSandboxCTCollectionTemplate(
		long companyId, long ctCollectionTemplateId) {

		CTSettingsConfiguration ctSettingsConfiguration =
			_getCTSettingsConfiguration(companyId);

		if (ctSettingsConfiguration.defaultSandboxCTCollectionTemplateId() ==
				ctCollectionTemplateId) {

			return true;
		}

		return false;
	}

	public boolean isEnabled(long companyId) {
		CTSettingsConfiguration ctSettingsConfiguration =
			_getCTSettingsConfiguration(companyId);

		return ctSettingsConfiguration.enabled();
	}

	public boolean isSandboxEnabled(long companyId) {
		CTSettingsConfiguration ctSettingsConfiguration =
			_getCTSettingsConfiguration(companyId);

		return ctSettingsConfiguration.sandboxEnabled();
	}

	public boolean isUnapprovedChangesAllowed(long companyId) {
		CTSettingsConfiguration ctSettingsConfiguration =
			_getCTSettingsConfiguration(companyId);

		return ctSettingsConfiguration.unapprovedChangesAllowed();
	}

	public void save(
			long companyId, long defaultCTCollectionTemplateId,
			long defaultSandboxCTCollectionTemplateId, boolean enabled,
			boolean sandboxEnabled, boolean unapprovedChangesAllowed)
		throws PortalException {

		_configurationProvider.saveCompanyConfiguration(
			CTSettingsConfiguration.class, companyId,
			HashMapDictionaryBuilder.<String, Object>put(
				"defaultCTCollectionTemplateId", defaultCTCollectionTemplateId
			).put(
				"defaultSandboxCTCollectionTemplateId",
				defaultSandboxCTCollectionTemplateId
			).put(
				"enabled", enabled
			).put(
				"sandboxEnabled", sandboxEnabled
			).put(
				"unapprovedChangesAllowed", unapprovedChangesAllowed
			).build());
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_defaultCTSettingsConfiguration = ConfigurableUtil.createConfigurable(
			CTSettingsConfiguration.class, properties);
	}

	private CTSettingsConfiguration _getCTSettingsConfiguration(
		long companyId) {

		try {
			return _configurationProvider.getCompanyConfiguration(
				CTSettingsConfiguration.class, companyId);
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException);
		}

		return _defaultCTSettingsConfiguration;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTSettingsConfigurationHelper.class.getName());

	@Reference
	private ConfigurationProvider _configurationProvider;

	private volatile CTSettingsConfiguration _defaultCTSettingsConfiguration;

}