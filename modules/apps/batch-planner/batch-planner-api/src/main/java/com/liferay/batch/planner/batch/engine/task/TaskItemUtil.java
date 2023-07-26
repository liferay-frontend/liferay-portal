/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.batch.engine.task;

import com.liferay.petra.string.StringPool;

/**
 * @author Igor Beslic
 */
public class TaskItemUtil {

	public static String getDelegateName(String internalClassName) {
		int idx = internalClassName.indexOf(StringPool.POUND);

		if (idx < 0) {
			return "DEFAULT";
		}

		return internalClassName.substring(idx + 1);
	}

	public static String getInternalClassName(String internalClassName) {
		int idx = internalClassName.indexOf(StringPool.POUND);

		if (idx < 0) {
			return internalClassName;
		}

		return internalClassName.substring(0, idx);
	}

}