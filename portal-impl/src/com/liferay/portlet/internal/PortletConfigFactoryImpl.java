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

package com.liferay.portlet.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.CompanyPortletMap;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.PortletConfigFactory;
import com.liferay.portal.kernel.portlet.PortletContextFactory;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.service.impl.PortletLocalServiceImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletConfigFactoryImpl implements PortletConfigFactory {

	@Override
	public PortletConfig create(
		Portlet portlet, ServletContext servletContext) {

		Map<String, PortletConfig> portletConfigs = _pool.get(
			portlet.getCompanyId(), portlet.getRootPortletId());

		if (portletConfigs == null) {
			portletConfigs = new ConcurrentHashMap<>();

			_pool.put(
				portlet.getCompanyId(), portlet.getRootPortletId(),
				portletConfigs);
		}

		PortletConfig portletConfig = portletConfigs.get(
			portlet.getPortletId());

		if ((portletConfig == null) ||
			!_isSamePortletDeployedStatus(portlet, portletConfig)) {

			PortletContext portletContext = _portletContextFactory.create(
				portlet, servletContext);

			portletConfig = new PortletConfigImpl(portlet, portletContext);

			portletConfigs.put(portlet.getPortletId(), portletConfig);
		}

		return portletConfig;
	}

	@Override
	public void destroy(Portlet portlet) {
		_pool.remove(portlet.getCompanyId(), portlet.getRootPortletId());
	}

	@Override
	public PortletConfig get(Portlet portlet) {
		return get(portlet.getPortletId());
	}

	@Override
	public PortletConfig get(String portletId) {
		String rootPortletId = PortletIdCodec.decodePortletName(portletId);

		Map<String, PortletConfig> portletConfigs = _pool.get(
			_inferCompanyId("used in many places, indirectly"),
				rootPortletId);

		if (portletConfigs == null) {
			return null;
		}

		return portletConfigs.get(portletId);
	}

	public void setPortletContextFactory(
		PortletContextFactory portletContextFactory) {

		_portletContextFactory = portletContextFactory;
	}

	@Override
	public PortletConfig update(Portlet portlet) {
		Map<String, PortletConfig> portletConfigs = _pool.get(
			portlet.getCompanyId(),	portlet.getRootPortletId());

		if (portletConfigs == null) {
			return null;
		}

		PortletConfig portletConfig = portletConfigs.get(
			portlet.getPortletId());

		portletConfig = new PortletConfigImpl(
			portlet, portletConfig.getPortletContext());

		portletConfigs.put(portlet.getPortletId(), portletConfig);

		return portletConfig;
	}

	private long _inferCompanyId(String todoMessage) {
		long companyId = 0;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			companyId = serviceContext.getCompanyId();
		}
		else {
			companyId = CompanyThreadLocal.getCompanyId();

			if (companyId == 0 && _log.isWarnEnabled()) {
				_log.warn(
					new Exception(
						"ServiceContext is null and CompanyThreadLocal returned 0"));
			}
		}

		return companyId;
	}

	private boolean _isSamePortletDeployedStatus(
		Portlet portlet, PortletConfig portletConfig) {

		LiferayPortletConfig liferayPortletConfig =
			(LiferayPortletConfig)portletConfig;

		Portlet existingPortlet = liferayPortletConfig.getPortlet();

		if ((existingPortlet != null) &&
			(portlet.isUndeployedPortlet() ==
				existingPortlet.isUndeployedPortlet())) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletConfigFactoryImpl.class);


	private final CompanyPortletMap<Map<String, PortletConfig>> _pool =
		new CompanyPortletMap<>();
	private PortletContextFactory _portletContextFactory;

}