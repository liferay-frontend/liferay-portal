/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dependency.manager;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Shuyang Zhou
 */
@ProviderType
public interface DependencyManagerSync {

	public void registerSyncCallable(Callable<Void> syncCallable);

	public void registerSyncFutureTask(
		FutureTask<Void> syncFutureTask, String taskName);

	public void sync();

}