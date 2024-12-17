/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.apiurl;

import com.liferay.frontend.data.set.apiurl.FDSAPIURLBuilder;
import com.liferay.frontend.data.set.resolver.FDSAPIURLResolver;
import com.liferay.frontend.data.set.resolver.FDSAPIURLResolverRegistry;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Daniel Sanz
 */
public class FDSAPIURLBuilderImpl implements FDSAPIURLBuilder {

	public FDSAPIURLBuilderImpl(
		String restApplication, String restEndpoint, String restSchema,
		FDSAPIURLResolverRegistry fdsAPIURLResolverRegistry,
		HttpServletRequest httpServletRequest) {

		_restApplication = restApplication;
		_restEndpoint = restEndpoint;
		_restSchema = restSchema;
		_fdsAPIURLResolverRegistry = fdsAPIURLResolverRegistry;
		_httpServletRequest = httpServletRequest;
	}

	@Override
	public FDSAPIURLBuilder addParameter(String name, String value) {
		_parameters.put(name, value);

		return this;
	}

	@Override
	public String build() {
		int size = 3;

		if (!_parameters.isEmpty()) {
			size += 1 + (_parameters.size() * 3);
		}

		StringBundler sb = new StringBundler(size);

		sb.append("/o");

		sb.append(
			StringUtil.replaceLast(
				_restApplication, "/v1.0", StringPool.BLANK));

		sb.append(_restEndpoint);

		_appendParameters(sb);

		return _interpolateURL(_resolveParameters(sb.toString()));
	}

	private void _appendParameters(StringBundler sb) {
		if (_parameters.isEmpty()) {
			return;
		}

		sb.append(StringPool.QUESTION);

		int count = 0;

		for (Map.Entry<String, String> entry : _parameters.entrySet()) {
			sb.append(entry.getKey());
			sb.append(StringPool.EQUAL);
			sb.append(entry.getValue());
			count++;

			if (count < _parameters.size()) {
				sb.append(StringPool.AMPERSAND);
			}
		}
	}

	private String _interpolateURL(String apiURL) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		apiURL = StringUtil.replace(
			apiURL, "{siteId}", String.valueOf(themeDisplay.getScopeGroupId()));
		apiURL = StringUtil.replace(
			apiURL, "{scopeKey}",
			String.valueOf(themeDisplay.getScopeGroupId()));
		apiURL = StringUtil.replace(
			apiURL, "{userId}", String.valueOf(themeDisplay.getUserId()));

		if (StringUtil.contains(apiURL, "{") && _log.isWarnEnabled()) {
			_log.warn("Unsupported parameter in API URL: " + apiURL);
		}

		return apiURL;
	}

	private String _resolveParameters(String apiURL) {
		FDSAPIURLResolver fdsAPIURLResolver =
			_fdsAPIURLResolverRegistry.getFDSAPIURLResolver(
				_restApplication, _restSchema);

		if (fdsAPIURLResolver != null) {
			try {
				return fdsAPIURLResolver.resolve(apiURL, _httpServletRequest);
			}
			catch (PortalException portalException) {
				_log.error(portalException);

				return apiURL;
			}
		}

		return apiURL;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FDSAPIURLBuilderImpl.class);

	private final FDSAPIURLResolverRegistry _fdsAPIURLResolverRegistry;
	private final HttpServletRequest _httpServletRequest;
	private final Map<String, String> _parameters = new LinkedHashMap<>();
	private final String _restApplication;
	private final String _restEndpoint;
	private final String _restSchema;

}