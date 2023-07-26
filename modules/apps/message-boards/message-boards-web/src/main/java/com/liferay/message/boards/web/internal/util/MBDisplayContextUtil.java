/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.web.internal.util;

import com.liferay.message.boards.web.internal.display.context.MBDisplayContextProvider;
import com.liferay.osgi.util.service.Snapshot;

/**
 * @author Iván Zaera
 */
public class MBDisplayContextUtil {

	public static MBDisplayContextProvider getMBDisplayContextProvider() {
		return _mbDisplayContextProviderSnapshot.get();
	}

	private static final Snapshot<MBDisplayContextProvider>
		_mbDisplayContextProviderSnapshot = new Snapshot<>(
			MBDisplayContextUtil.class, MBDisplayContextProvider.class);

}