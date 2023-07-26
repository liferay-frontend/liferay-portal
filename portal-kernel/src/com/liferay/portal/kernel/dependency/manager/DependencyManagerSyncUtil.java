/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dependency.manager;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author Shuyang Zhou
 */
public class DependencyManagerSyncUtil {

	public static void registerSyncCallable(Callable<Void> syncCallable) {
		_dependencyManagerSync.registerSyncCallable(syncCallable);
	}

	public static void registerSyncFutureTask(
		FutureTask<Void> syncFutureTask, String taskName) {

		_dependencyManagerSync.registerSyncFutureTask(syncFutureTask, taskName);
	}

	public static void sync() {
		_dependencyManagerSync.sync();
	}

	private static volatile DependencyManagerSync _dependencyManagerSync =
		ServiceProxyFactory.newServiceTrackedInstance(
			DependencyManagerSync.class, DependencyManagerSyncUtil.class,
			"_dependencyManagerSync", true);

}