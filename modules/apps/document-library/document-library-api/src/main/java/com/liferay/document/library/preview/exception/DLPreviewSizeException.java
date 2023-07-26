/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.preview.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alejandro Tardín
 */
public class DLPreviewSizeException extends PortalException {

	public DLPreviewSizeException() {
	}

	public DLPreviewSizeException(String msg) {
		super(msg);
	}

	public DLPreviewSizeException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DLPreviewSizeException(Throwable throwable) {
		super(throwable);
	}

}