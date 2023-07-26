/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.util;

import com.liferay.petra.lang.CentralizedThreadLocal;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * See https://issues.liferay.com/browse/LPS-7864.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class ThirdPartyThreadLocalRegistry {

	public static void registerThreadLocal(ThreadLocal<?> threadLocal) {
		Set<ThreadLocal<?>> threadLocalSet = _threadLocalSet.get();

		threadLocalSet.add(threadLocal);
	}

	public static void resetThreadLocals() {
		Set<ThreadLocal<?>> threadLocalSet = _threadLocalSet.get();

		if (threadLocalSet == null) {
			return;
		}

		for (ThreadLocal<?> threadLocal : threadLocalSet) {
			threadLocal.remove();
		}
	}

	private static final ThreadLocal<Set<ThreadLocal<?>>> _threadLocalSet =
		new CentralizedThreadLocal<>(
			ThirdPartyThreadLocalRegistry.class + "._threadLocalSet",
			HashSet::new, false);

}