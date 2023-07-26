/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.jaxrs.whiteboard.internal.servlet.filter;

import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.concurrent.CountDownLatch;

import javax.servlet.Filter;
import javax.servlet.ServletException;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Shuyang Zhou
 */
@Component(service = {})
public class JAXRSActivationFilterTracker {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_countDownLatch = new CountDownLatch(1);

		_filterServiceRegistration = bundleContext.registerService(
			Filter.class, new JAXRSActivationFilter(bundleContext, this),
			HashMapDictionaryBuilder.<String, Object>put(
				"dispatcher", new String[] {"FORWARD", "INCLUDE", "REQUEST"}
			).put(
				"servlet-context-name", ""
			).put(
				"servlet-filter-name", "Activation Filter"
			).put(
				"url-pattern", "/o/*"
			).build());

		_countDownLatch.countDown();
	}

	@Deactivate
	protected synchronized void deactivate() {
		_unregister();

		if (_readyServiceRegistration != null) {
			_readyServiceRegistration.unregister();

			_readyServiceRegistration = null;
		}
	}

	protected synchronized void setReady() throws ServletException {
		try {
			_countDownLatch.await();
		}
		catch (InterruptedException interruptedException) {
			throw new ServletException(interruptedException);
		}

		if (_readyServiceRegistration == null) {
			_readyServiceRegistration = _bundleContext.registerService(
				Object.class, new Object(),
				MapUtil.singletonDictionary(
					"liferay.jaxrs.whiteboard.ready", true));

			_unregister();
		}
	}

	private void _unregister() {
		if (_filterServiceRegistration != null) {
			_filterServiceRegistration.unregister();

			_filterServiceRegistration = null;
		}
	}

	private BundleContext _bundleContext;
	private CountDownLatch _countDownLatch;
	private ServiceRegistration<Filter> _filterServiceRegistration;
	private ServiceRegistration<?> _readyServiceRegistration;

}