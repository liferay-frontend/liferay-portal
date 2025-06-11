/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.frontend.resource.handler;

import com.liferay.frontend.js.web.internal.configuration.FrontendCachingConfiguration;
import com.liferay.frontend.js.web.internal.frontend.resource.FrontendResource;
import com.liferay.frontend.js.web.internal.frontend.resource.LanguageFrontendResource;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.util.Portal;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

/**
 * @author Iván Zaera Avellón
 */
public class LanguageFrontendResourceRequestHandler
	implements FrontendResourceRequestHandler {

	public static final String LANGUAGE_MODULE_PREFIX = "@liferay/language/";

	public static final String LANGUAGE_URI_PREFIX = "/o/js/language/";

	public LanguageFrontendResourceRequestHandler(
		ConfigurationProvider configurationProvider, JSONFactory jsonFactory,
		Language language, Portal portal,
		ServiceTrackerMap<String, ServletContext> serviceTrackerMap) {

		_configurationProvider = configurationProvider;
		_jsonFactory = jsonFactory;
		_language = language;
		_portal = portal;
		_serviceTrackerMap = serviceTrackerMap;
	}

	@Override
	public boolean canHandleRequest(HttpServletRequest httpServletRequest) {
		String requestURI = httpServletRequest.getRequestURI();

		return requestURI.startsWith(LANGUAGE_URI_PREFIX);
	}

	@Override
	public FrontendResource handleRequest(HttpServletRequest httpServletRequest)
		throws IOException, ServletException {

		String requestURI = httpServletRequest.getRequestURI();

		requestURI = requestURI.substring(LANGUAGE_URI_PREFIX.length());

		String[] requestURIParts = requestURI.split(StringPool.SLASH);

		if ((requestURIParts.length != 3) ||
			!requestURIParts[2].equals("all.js")) {

			return null;
		}

		long maxAge = 3600;
		boolean sendNoCache = false;

		long companyId = _portal.getCompanyId(httpServletRequest);

		try {
			FrontendCachingConfiguration frontendCachingConfiguration =
				_configurationProvider.getCompanyConfiguration(
					FrontendCachingConfiguration.class, companyId);

			maxAge = frontendCachingConfiguration.labelsModulesMaxAge();
			sendNoCache =
				frontendCachingConfiguration.sendNoCacheForLabelsModules();
		}
		catch (ConfigurationException configurationException) {
			_log.error(
				"Unable to get frontend caching configuration: will use " +
					"reasonable defaults instead",
				configurationException);
		}

		return new LanguageFrontendResource(
			_jsonFactory, _language, requestURIParts[0], maxAge, sendNoCache,
			_serviceTrackerMap.getService(
				Portal.PATH_MODULE + StringPool.SLASH + requestURIParts[1]));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LanguageFrontendResourceRequestHandler.class);

	private final ConfigurationProvider _configurationProvider;
	private final JSONFactory _jsonFactory;
	private final Language _language;
	private final Portal _portal;
	private final ServiceTrackerMap<String, ServletContext> _serviceTrackerMap;

}