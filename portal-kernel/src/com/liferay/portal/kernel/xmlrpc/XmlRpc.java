/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.xmlrpc;

/**
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
public interface XmlRpc {

	public Fault createFault(int code, String description);

	public Success createSuccess(String description);

	public Response executeMethod(
			String url, String methodName, Object[] arguments)
		throws XmlRpcException;

}