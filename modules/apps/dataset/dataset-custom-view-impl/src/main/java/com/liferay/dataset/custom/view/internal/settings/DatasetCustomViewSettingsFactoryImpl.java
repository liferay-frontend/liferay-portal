/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.dataset.custom.view.internal.settings;

import com.liferay.dataset.custom.view.model.DatasetCustomViewEntry;
import com.liferay.dataset.custom.view.service.DatasetCustomViewEntryLocalService;
import com.liferay.dataset.custom.view.settings.DatasetCustomViewSettings;
import com.liferay.dataset.custom.view.settings.DatasetCustomViewSettingsFactory;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = DatasetCustomViewSettingsFactory.class)
public class DatasetCustomViewSettingsFactoryImpl
	implements DatasetCustomViewSettingsFactory {

	@Override
	public DatasetCustomViewSettings getDatasetCustomViewSettings(
		ThemeDisplay themeDisplay, String datasetDisplayId) {

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		DatasetCustomViewEntry datasetCustomViewEntry =
			_datasetCustomViewEntryLocalService.fetchDatasetCustomViewEntry(
				themeDisplay.getUserId(), datasetDisplayId,
				themeDisplay.getPlid(), portletDisplay.getId());

		if (datasetCustomViewEntry == null) {
			return null;
		}

		return new DatasetCustomViewSettingsImpl(
			datasetCustomViewEntry.getSettingsJSON());
	}

	@Reference
	private DatasetCustomViewEntryLocalService
		_datasetCustomViewEntryLocalService;

}