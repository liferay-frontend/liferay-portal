/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.messaging;

import com.liferay.portal.kernel.util.SetUtil;

import java.util.List;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class GlobalMessageBusEventListener implements MessageBusEventListener {

	@Override
	public void destinationAdded(Destination destination) {
		if (!_ignoredDestinations.contains(destination.getName())) {
			destination.register(_messageListener);
		}
	}

	@Override
	public void destinationRemoved(Destination destination) {
		if (!_ignoredDestinations.contains(destination.getName())) {
			destination.unregister(_messageListener);
		}
	}

	public void setIgnoredDestinations(List<String> ignoredDestinations) {
		_ignoredDestinations = SetUtil.fromList(ignoredDestinations);
	}

	public void setMessageListener(MessageListener messageListener) {
		_messageListener = messageListener;
	}

	private Set<String> _ignoredDestinations;
	private MessageListener _messageListener;

}