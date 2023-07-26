/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedWriter;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.FileWriter;

import java.util.Iterator;

/**
 * @author Brian Wing Shun Chan
 */
public class ValueMapperUtil {

	public static void persist(
			ValueMapper valueMapper, String tmpDir, String fileName)
		throws Exception {

		FileUtil.mkdirs(tmpDir);

		try (UnsyncBufferedWriter unsyncBufferedWriter =
				new UnsyncBufferedWriter(
					new FileWriter(
						StringBundler.concat(tmpDir, "/", fileName, ".txt")))) {

			Iterator<Object> iterator = valueMapper.iterator();

			while (iterator.hasNext()) {
				Object oldValue = iterator.next();

				Object newValue = valueMapper.getNewValue(oldValue);

				unsyncBufferedWriter.write(
					oldValue + StringPool.EQUAL + newValue);

				if (iterator.hasNext()) {
					unsyncBufferedWriter.write(StringPool.NEW_LINE);
				}
			}
		}
	}

}