/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.builder.internal.configuration;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Renato Rego
 */
@Component(
	configurationPid = "com.liferay.dynamic.data.mapping.form.builder.internal.configuration.FFDDMFormSidebarConfiguration",
	service = FFDDMFormSidebarConfigurationActivator.class
)
public class FFDDMFormSidebarConfigurationActivator {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ffDDMFormSidebarConfiguration = ConfigurableUtil.createConfigurable(
			FFDDMFormSidebarConfiguration.class, properties);
	}

	private volatile FFDDMFormSidebarConfiguration
		_ffDDMFormSidebarConfiguration;

}