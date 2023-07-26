/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.creator.openai.web.internal.configuration.admin.display;

import com.liferay.configuration.admin.display.ConfigurationVisibilityController;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;

import java.io.Serializable;

import org.osgi.service.component.annotations.Component;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = {
		"configuration.pid=com.liferay.ai.creator.openai.configuration.AICreatorOpenAICompanyConfiguration",
		"configuration.pid=com.liferay.ai.creator.openai.configuration.AICreatorOpenAIGroupConfiguration"
	},
	service = ConfigurationVisibilityController.class
)
public class AICreatorOpenAIConfigurationVisibilityController
	implements ConfigurationVisibilityController {

	@Override
	public boolean isVisible(
		ExtendedObjectClassDefinition.Scope scope, Serializable scopePK) {

		if (FeatureFlagManagerUtil.isEnabled("LPS-179483")) {
			return true;
		}

		return false;
	}

}