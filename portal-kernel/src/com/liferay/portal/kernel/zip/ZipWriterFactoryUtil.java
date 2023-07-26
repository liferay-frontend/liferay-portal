/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.zip;

import java.io.File;

/**
 * @author Raymond Augé
 */
public class ZipWriterFactoryUtil {

	public static ZipWriter getZipWriter() {
		return _zipWriterFactory.getZipWriter();
	}

	public static ZipWriter getZipWriter(File file) {
		return _zipWriterFactory.getZipWriter(file);
	}

	public static ZipWriterFactory getZipWriterFactory() {
		return _zipWriterFactory;
	}

	public void setZipWriterFactory(ZipWriterFactory zipWriterFactory) {
		_zipWriterFactory = zipWriterFactory;
	}

	private static ZipWriterFactory _zipWriterFactory;

}