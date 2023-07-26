/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package freemarker.ext.jsp.internal;

import java.io.CharArrayWriter;
import java.io.Writer;

/**
 * @author Preston Crary
 */
public class WriterFactoryUtil {

	public static Writer createWriter() {
		if (_writerFactory == null) {
			return new CharArrayWriter();
		}

		return _writerFactory.createWriter();
	}

	public static void setWriterFactory(WriterFactory writerFactory) {
		_writerFactory = writerFactory;
	}

	private static WriterFactory _writerFactory;

}
/* @generated */