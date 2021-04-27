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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = DatasetCustomViewSettingsFactory.class)
public class DatasetCustomViewSettingsFactoryImpl
	implements DatasetCustomViewSettingsFactory {

	@Override
	public DatasetCustomViewSettings getActiveDatasetCustomViewSettings(
		HttpServletRequest httpServletRequest, String datasetDisplayId) {

		return getDatasetCustomViewSettings(
			httpServletRequest, datasetDisplayId, StringPool.BLANK);
	}

	@Override
	public DatasetCustomViewSettings getDatasetCustomViewSettings(
		HttpServletRequest httpServletRequest, String datasetDisplayId,
		String name) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		DatasetCustomViewEntry datasetCustomViewEntry =
			_datasetCustomViewEntryLocalService.fetchDatasetCustomViewEntry(
				themeDisplay.getUserId(), datasetDisplayId, name,
				themeDisplay.getPlid(), portletDisplay.getId());

		if ((datasetCustomViewEntry == null) && name.isEmpty()) {
			datasetCustomViewEntry = _upgradeDatasetCustomViewEntry(
				httpServletRequest, datasetDisplayId);
		}

		if (datasetCustomViewEntry == null) {
			datasetCustomViewEntry =
				_datasetCustomViewEntryLocalService.
					createDatasetCustomViewEntry(
						themeDisplay.getUserId(), datasetDisplayId, name, "{}",
						themeDisplay.getPlid(), portletDisplay.getId());
		}

		return new DatasetCustomViewSettingsImpl(datasetCustomViewEntry);
	}

	@Override
	public void storeDatasetCustomViewSettings(
		DatasetCustomViewSettings datasetCustomViewSettings) {

		DatasetCustomViewSettingsImpl datasetCustomViewSettingsImpl =
			(DatasetCustomViewSettingsImpl)datasetCustomViewSettings;

		_datasetCustomViewEntryLocalService.updateDatasetCustomViewEntry(
			datasetCustomViewSettingsImpl.getDatasetCustomViewEntry());
	}

	private String _getClayDataSetDisplaySettingsNamespace(
		String datasetDisplayId, long plid, String portletId) {

		StringBundler sb = new StringBundler(8);

		sb.append("com.liferay.frontend.taglib.clay.servlet.taglib.");
		sb.append("DataSetDisplayTag");
		sb.append(StringPool.POUND);
		sb.append(_portal.getPortletNamespace(portletId));
		sb.append(StringPool.POUND);
		sb.append(plid);
		sb.append(StringPool.POUND);
		sb.append(datasetDisplayId);

		return sb.toString();
	}

	private DatasetCustomViewEntry _upgradeDatasetCustomViewEntry(
		HttpServletRequest httpServletRequest, String datasetDisplayId) {

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				httpServletRequest);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String clayDataSetDisplaySettingsNamespace =
			_getClayDataSetDisplaySettingsNamespace(
				datasetDisplayId, themeDisplay.getPlid(),
				portletDisplay.getId());

		String jsonString = portalPreferences.getValue(
			clayDataSetDisplaySettingsNamespace, "activeViewSettingsJSON");

		if (Validator.isNotNull(jsonString)) {
			DatasetCustomViewEntry datasetCustomViewEntry =
				_datasetCustomViewEntryLocalService.
					createDatasetCustomViewEntry(
						portalPreferences.getUserId(), datasetDisplayId,
						StringPool.BLANK, jsonString, themeDisplay.getPlid(),
						portletDisplay.getId());

			_datasetCustomViewEntryLocalService.updateDatasetCustomViewEntry(
				datasetCustomViewEntry);

			portalPreferences.setValue(
				clayDataSetDisplaySettingsNamespace, "activeViewSettingsJSON",
				null);

			return datasetCustomViewEntry;
		}

		return null;
	}

	@Reference
	private DatasetCustomViewEntryLocalService
		_datasetCustomViewEntryLocalService;

	@Reference
	private Portal _portal;

}