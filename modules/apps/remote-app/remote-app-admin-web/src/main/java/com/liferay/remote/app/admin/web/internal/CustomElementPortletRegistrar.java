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

package com.liferay.remote.app.admin.web.internal;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.remote.app.admin.web.internal.portlet.CustomElementPortlet;
import com.liferay.remote.app.model.CustomElementPortletEntry;
import com.liferay.remote.app.service.CustomElementPortletEntryLocalService;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(immediate = true, service = CustomElementPortletRegistrar.class)
public class CustomElementPortletRegistrar {

	public void registerPortlet(
		CustomElementPortletEntry customElementPortletEntry) {

		_registerPortlet(customElementPortletEntry);
	}

	public void unregisterPortlet(
		CustomElementPortletEntry customElementPortletEntry) {

		_unregisterPortlet(
			customElementPortletEntry.getCustomElementPortletEntryId());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		if (_log.isInfoEnabled()) {
			_log.info("Starting remote app entries");
		}

		for (CustomElementPortletEntry customElementPortletEntry :
				remoteAppEntryLocalService.getCustomElementPortletEntries(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {

			registerPortlet(customElementPortletEntry);
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_log.isInfoEnabled()) {
			_log.info("Stopping custom element portlet entries");
		}

		for (long customElementPortletEntryId :
				_customElementPortlets.keySet()) {

			_unregisterPortlet(customElementPortletEntryId);
		}
	}

	@Reference
	protected CustomElementPortletEntryLocalService remoteAppEntryLocalService;

	private void _registerPortlet(
		CustomElementPortletEntry customElementPortletEntry) {

		CustomElementPortlet customElementPortlet = new CustomElementPortlet(
			customElementPortletEntry);

		long customElementPortletEntryId =
			customElementPortletEntry.getCustomElementPortletEntryId();

		CustomElementPortlet existingCustomElementPortlet =
			_customElementPortlets.putIfAbsent(
				customElementPortletEntryId, customElementPortlet);

		if (existingCustomElementPortlet != null) {
			throw new IllegalStateException(
				"Custom element portlet entry " + customElementPortletEntryId +
					" is already registered");
		}

		customElementPortlet.register(_bundleContext);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Started custom element portlet entry " +
					customElementPortlet.getName());
		}
	}

	private void _unregisterPortlet(long customElementPortletEntryId) {
		CustomElementPortlet customElementPortlet =
			_customElementPortlets.remove(customElementPortletEntryId);

		if (customElementPortlet != null) {
			customElementPortlet.unregister();

			if (_log.isInfoEnabled()) {
				_log.info(
					"Stopped custom element portlet entry " +
						customElementPortlet.getName());
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CustomElementPortletRegistrar.class);

	private BundleContext _bundleContext;
	private final ConcurrentMap<Long, CustomElementPortlet>
		_customElementPortlets = new ConcurrentHashMap<>();

}