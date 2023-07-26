/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Raymond Augé
 */
public class SharedSessionUtil {

	public static HttpSession getSharedSessionWrapper(
		HttpSession portalHttpSession, HttpServletRequest httpServletRequest) {

		return _sharedSession.getSharedSessionWrapper(
			portalHttpSession, httpServletRequest);
	}

	public void setSharedSession(SharedSession sharedSession) {
		_sharedSession = sharedSession;
	}

	private static SharedSession _sharedSession;

}