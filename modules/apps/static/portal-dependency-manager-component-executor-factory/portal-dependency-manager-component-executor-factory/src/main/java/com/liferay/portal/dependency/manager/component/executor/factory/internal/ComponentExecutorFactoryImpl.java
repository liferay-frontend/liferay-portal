/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dependency.manager.component.executor.factory.internal;

import java.util.concurrent.Executor;

import org.apache.felix.dm.Component;
import org.apache.felix.dm.ComponentExecutorFactory;

/**
 * @author Shuyang Zhou
 */
public class ComponentExecutorFactoryImpl implements ComponentExecutorFactory {

	public ComponentExecutorFactoryImpl(Executor executor) {
		_executor = executor;
	}

	@Override
	public Executor getExecutorFor(Component component) {
		return _executor;
	}

	private final Executor _executor;

}