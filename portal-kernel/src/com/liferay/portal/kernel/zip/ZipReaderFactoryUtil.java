/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.zip;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Raymond Augé
 */
public class ZipReaderFactoryUtil {

	public static ZipReader getZipReader(File file) {
		return _zipReaderFactory.getZipReader(file);
	}

	public static ZipReader getZipReader(InputStream inputStream)
		throws IOException {

		return _zipReaderFactory.getZipReader(inputStream);
	}

	public static ZipReaderFactory getZipReaderFactory() {
		return _zipReaderFactory;
	}

	public void setZipReaderFactory(ZipReaderFactory zipReaderFactory) {
		_zipReaderFactory = zipReaderFactory;
	}

	private static ZipReaderFactory _zipReaderFactory;

}