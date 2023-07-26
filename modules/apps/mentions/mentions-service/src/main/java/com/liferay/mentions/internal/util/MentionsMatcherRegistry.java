/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mentions.internal.util;

import com.liferay.mentions.matcher.MentionsMatcher;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Adolfo Pérez
 */
@Component(service = MentionsMatcherRegistry.class)
public class MentionsMatcherRegistry {

	public MentionsMatcher getMentionsMatcher(String className) {
		MentionsMatcher mentionsMatcher = _serviceTrackerMap.getService(
			className);

		if (mentionsMatcher != null) {
			return mentionsMatcher;
		}

		MentionsMatcher defaultMentionsMatcher = _serviceTrackerMap.getService(
			"*");

		if (defaultMentionsMatcher == null) {
			throw new IllegalStateException(
				"Unable to get default mentions matcher");
		}

		return defaultMentionsMatcher;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, MentionsMatcher.class, "model.class.name");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, MentionsMatcher> _serviceTrackerMap;

}