/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.push.notifications.sender.apple.internal.messaging;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.push.notifications.constants.PushNotificationsDestinationNames;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Farache
 */
@Component(
	enabled = false, service = ApplePushNotificationsMessagingConfigurator.class
)
public class ApplePushNotificationsMessagingConfigurator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_applePushNotificationsResponseMessageListener =
			new ApplePushNotificationsResponseMessageListener();

		_destination.register(_applePushNotificationsResponseMessageListener);
	}

	@Deactivate
	protected void deactivate() {
		_destination.unregister(_applePushNotificationsResponseMessageListener);
	}

	private MessageListener _applePushNotificationsResponseMessageListener;

	@Reference(
		target = "(destination.name= " + PushNotificationsDestinationNames.PUSH_NOTIFICATION_RESPONSE + ")"
	)
	private Destination _destination;

}