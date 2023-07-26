/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.startup.monitor.internal;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.util.ThreadUtil;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Matthew Tambara
 */
@Component(service = {})
public class PortalStartupMonitor {

	@Activate
	protected void activate(ComponentContext componentContext) {
		_serviceTracker = ServiceTrackerFactory.open(
			componentContext.getBundleContext(),
			StringBundler.concat(
				"(&", ModuleServiceLifecycle.PORTAL_INITIALIZED,
				"(objectClass=", ModuleServiceLifecycle.class.getName(), "))"),
			new ServiceTrackerCustomizer
				<ModuleServiceLifecycle, ModuleServiceLifecycle>() {

				@Override
				public ModuleServiceLifecycle addingService(
					ServiceReference<ModuleServiceLifecycle> serviceReference) {

					componentContext.disableComponent(
						PortalStartupMonitor.class.getName());

					return null;
				}

				@Override
				public void modifiedService(
					ServiceReference<ModuleServiceLifecycle> serviceReference,
					ModuleServiceLifecycle moduleServiceLifecycle) {
				}

				@Override
				public void removedService(
					ServiceReference<ModuleServiceLifecycle> serviceReference,
					ModuleServiceLifecycle moduleServiceLifecycle) {
				}

			});

		_thread = new Thread("Portal Startup Monitoring Thread") {

			@Override
			public void run() {
				StringBundler sb = new StringBundler(4);

				while (true) {
					try {
						Thread.sleep(_SLEEP);
					}
					catch (InterruptedException interruptedException) {
						if (_log.isDebugEnabled()) {
							_log.debug(interruptedException);
						}

						break;
					}

					sb.append("Thread dump for portal startup after waited ");
					sb.append(_SLEEP);
					sb.append("ms:\n");
					sb.append(ThreadUtil.threadDump());

					System.out.println(sb.toString());

					sb.setIndex(0);
				}
			}

		};

		_thread.setDaemon(true);

		_thread.start();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();

		_thread.interrupt();
	}

	private static final long _SLEEP = 600000;

	private static final Log _log = LogFactoryUtil.getLog(
		PortalStartupMonitor.class);

	private ServiceTracker<ModuleServiceLifecycle, ModuleServiceLifecycle>
		_serviceTracker;
	private Thread _thread;

}