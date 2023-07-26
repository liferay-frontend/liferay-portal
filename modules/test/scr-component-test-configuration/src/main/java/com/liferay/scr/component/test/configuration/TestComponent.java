/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scr.component.test.configuration;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Mariano Álvaro Sáiz
 */
@Component(
	configurationPid = {
		"com.liferay.scr.component.test.configuration.FirstConfiguration",
		"com.liferay.scr.component.test.configuration.SecondConfiguration"
	},
	enabled = false, service = TestComponent.class
)
public class TestComponent {

	public String getFirst() {
		return _firstConfiguration.first();
	}

	public String getSecond() {
		return _secondConfiguration.second();
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_firstConfiguration = ConfigurableUtil.createConfigurable(
			FirstConfiguration.class, properties);
		_secondConfiguration = ConfigurableUtil.createConfigurable(
			SecondConfiguration.class, properties);
	}

	private FirstConfiguration _firstConfiguration;
	private SecondConfiguration _secondConfiguration;

}