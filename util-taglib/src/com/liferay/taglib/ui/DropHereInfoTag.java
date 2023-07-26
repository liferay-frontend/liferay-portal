/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.taglib.ui;

import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class DropHereInfoTag extends IncludeTag {

	public String getMessage() {
		return _message;
	}

	public void setMessage(String message) {
		_message = message;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_message = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-ui:drop-here-info:message", _message);
	}

	private static final String _PAGE =
		"/html/taglib/ui/drop_here_info/page.jsp";

	private String _message;

}