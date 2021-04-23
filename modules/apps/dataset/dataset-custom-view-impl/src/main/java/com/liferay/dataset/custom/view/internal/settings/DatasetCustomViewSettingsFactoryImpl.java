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
	public DatasetCustomViewSettings getDatasetCustomViewSettings(
		HttpServletRequest httpServletRequest, String datasetDisplayId) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		DatasetCustomViewEntry datasetCustomViewEntry =
			_datasetCustomViewEntryLocalService.fetchDatasetCustomViewEntry(
				themeDisplay.getUserId(), datasetDisplayId,
				themeDisplay.getPlid(), portletDisplay.getId());

		if (datasetCustomViewEntry == null) {
			String jsonString = _getLegacyJSONString(
				httpServletRequest, datasetDisplayId);

			if (Validator.isNull(jsonString)) {
				jsonString = "{}";
			}

			return new DatasetCustomViewSettingsImpl(
				_datasetCustomViewEntryLocalService.
					createDatasetCustomViewEntry(jsonString));
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
		HttpServletRequest httpServletRequest, String datasetDisplayId) {

		StringBundler sb = new StringBundler(8);

		sb.append("com.liferay.frontend.taglib.clay.servlet.taglib.");
		sb.append("DataSetDisplayTag");
		sb.append(StringPool.POUND);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String portletNamespace = _portal.getPortletNamespace(
			portletDisplay.getId());

		sb.append(portletNamespace);

		sb.append(StringPool.POUND);
		sb.append(themeDisplay.getPlid());
		sb.append(StringPool.POUND);
		sb.append(datasetDisplayId);

		return sb.toString();
	}

	private String _getLegacyJSONString(
		HttpServletRequest httpServletRequest, String datasetDisplayId) {

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				httpServletRequest);

		String clayDataSetDisplaySettingsNamespace =
			_getClayDataSetDisplaySettingsNamespace(
				httpServletRequest, datasetDisplayId);

		return portalPreferences.getValue(
			clayDataSetDisplaySettingsNamespace, "activeViewSettingsJSON");
	}

	@Reference
	private DatasetCustomViewEntryLocalService
		_datasetCustomViewEntryLocalService;

	@Reference
	private Portal _portal;

}