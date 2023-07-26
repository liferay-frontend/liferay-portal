/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.selector.web.internal.portlet;

import com.liferay.asset.categories.selector.web.internal.constants.AssetCategoriesSelectorPortletKeys;
import com.liferay.portal.kernel.portlet.BasePortletProvider;
import com.liferay.portal.kernel.portlet.PortletProvider;

import org.osgi.service.component.annotations.Component;

/**
 * @author Antonio Pol
 */
@Component(
	property = "model.class.name=com.liferay.asset.kernel.model.AssetCategory",
	service = PortletProvider.class
)
public class AssetCategoriesSelectorBrowserPortletProvider
	extends BasePortletProvider {

	@Override
	public String getPortletName() {
		return AssetCategoriesSelectorPortletKeys.ASSET_CATEGORIES_SELECTOR;
	}

	@Override
	public Action[] getSupportedActions() {
		return _supportedActions;
	}

	private final Action[] _supportedActions = {Action.BROWSE};

}