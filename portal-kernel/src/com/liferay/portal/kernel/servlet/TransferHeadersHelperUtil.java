/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.servlet;

import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tina Tian
 */
public class TransferHeadersHelperUtil {

	public static RequestDispatcher getTransferHeadersRequestDispatcher(
		RequestDispatcher requestDispatcher) {

		return _transferHeadersHelper.getTransferHeadersRequestDispatcher(
			requestDispatcher);
	}

	public static void transferHeaders(
		Map<String, Object[]> headers,
		HttpServletResponse httpServletResponse) {

		_transferHeadersHelper.transferHeaders(headers, httpServletResponse);
	}

	public void setTransferHeadersHelper(
		TransferHeadersHelper transferHeadersHelper) {

		_transferHeadersHelper = transferHeadersHelper;
	}

	private static TransferHeadersHelper _transferHeadersHelper;

}