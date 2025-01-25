/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.filter;

import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilterContextContributor;
import com.liferay.frontend.data.set.filter.FDSFilterContextContributorRegistry;
import com.liferay.frontend.data.set.filter.FDSFilterRegistry;
import com.liferay.frontend.data.set.filter.FDSFilterSerializer;
import com.liferay.frontend.data.set.filter.SystemFDSFilterSerializer;
import com.liferay.frontend.data.set.serializer.FDSSerializer;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "frontend.data.set.serializer.type=" + FDSSerializer.SYSTEM,
	service = {FDSFilterSerializer.class, SystemFDSFilterSerializer.class}
)
public class SystemFDSFilterSerializerImpl
	implements SystemFDSFilterSerializer {

	@Override
	public JSONArray serialize(
		String fdsName, HttpServletRequest httpServletRequest) {

		return serialize(
			fdsName, Collections.emptyList(),
			_portal.getLocale(httpServletRequest));
	}

	@Override
	public JSONArray serialize(
		String fdsName, List<FDSFilter> fdsFilters, Locale locale) {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		_serialize(fdsFilters, jsonArray, locale);
		_serialize(
			_fdsFilterRegistry.getFDSFilters(fdsName), jsonArray, locale);

		return jsonArray;
	}

	private void _serialize(
		List<FDSFilter> fdsFilters, JSONArray jsonArray, Locale locale) {

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		for (FDSFilter fdsFilter : fdsFilters) {
			if (!fdsFilter.isEnabled()) {
				continue;
			}

			JSONObject jsonObject = JSONUtil.put(
				"entityFieldType", fdsFilter.getEntityFieldType()
			).put(
				"id", fdsFilter.getId()
			).put(
				"label", _language.get(resourceBundle, fdsFilter.getLabel())
			).put(
				"preloadedData", fdsFilter.getPreloadedData()
			).put(
				"type", fdsFilter.getType()
			);

			List<FDSFilterContextContributor> fdsFilterContextContributors =
				_fdsFilterContextContributorRegistry.
					getFDSFilterContextContributors(fdsFilter.getType());

			for (FDSFilterContextContributor fdsFilterContextContributor :
					fdsFilterContextContributors) {

				Map<String, Object> fdsFilterContext =
					fdsFilterContextContributor.getFDSFilterContext(
						fdsFilter, locale);

				if (fdsFilterContext == null) {
					continue;
				}

				for (Map.Entry<String, Object> entry :
						fdsFilterContext.entrySet()) {

					jsonObject.put(entry.getKey(), entry.getValue());
				}
			}

			jsonArray.put(jsonObject);
		}
	}

	@Reference
	private FDSFilterContextContributorRegistry
		_fdsFilterContextContributorRegistry;

	@Reference
	private FDSFilterRegistry _fdsFilterRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}