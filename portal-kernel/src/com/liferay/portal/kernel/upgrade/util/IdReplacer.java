/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.StagnantRowException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Brian Wing Shun Chan
 */
public class IdReplacer {

	public static String replaceLongIds(
			String s, String begin, ValueMapper valueMapper)
		throws Exception {

		if ((s == null) || (begin == null) || (valueMapper == null) ||
			(valueMapper.size() == 0)) {

			return s;
		}

		char[] chars = s.toCharArray();

		StringBundler sb = new StringBundler();

		int pos = 0;

		while (true) {
			int x = s.indexOf(begin, pos);

			int y = _getEndPos(chars, x + begin.length());

			if ((x == -1) || (y == -1)) {
				sb.append(s.substring(pos));

				break;
			}

			sb.append(s.substring(pos, x + begin.length()));

			String oldString = s.substring(x + begin.length(), y);

			if (Validator.isNotNull(oldString)) {
				Long oldValue = Long.valueOf(GetterUtil.getLong(oldString));

				Long newValue = null;

				try {
					newValue = (Long)valueMapper.getNewValue(oldValue);
				}
				catch (StagnantRowException stagnantRowException) {
					if (_log.isWarnEnabled()) {
						_log.warn(stagnantRowException);
					}
				}

				if (newValue == null) {
					newValue = oldValue;
				}

				sb.append(newValue);
			}

			pos = y;
		}

		return sb.toString();
	}

	public String replaceLongIds(
			String s, String begin, String end, ValueMapper valueMapper)
		throws Exception {

		if ((s == null) || (begin == null) || (end == null) ||
			(valueMapper == null) || (valueMapper.size() == 0)) {

			return s;
		}

		StringBundler sb = new StringBundler();

		int pos = 0;

		while (true) {
			int x = s.indexOf(begin, pos);

			int y = s.indexOf(end, x + begin.length());

			if ((x == -1) || (y == -1)) {
				sb.append(s.substring(pos));

				break;
			}

			sb.append(s.substring(pos, x + begin.length()));

			Long oldValue = Long.valueOf(
				GetterUtil.getLong(s.substring(x + begin.length(), y)));

			Long newValue = null;

			try {
				newValue = (Long)valueMapper.getNewValue(oldValue);
			}
			catch (StagnantRowException stagnantRowException) {
				if (_log.isWarnEnabled()) {
					_log.warn(stagnantRowException);
				}
			}

			if (newValue == null) {
				newValue = oldValue;
			}

			sb.append(newValue);

			pos = y;
		}

		return sb.toString();
	}

	private static int _getEndPos(char[] chars, int pos) {
		while (true) {
			if ((pos >= chars.length) || !Character.isDigit(chars[pos])) {
				break;
			}

			pos++;
		}

		return pos;
	}

	private static final Log _log = LogFactoryUtil.getLog(IdReplacer.class);

}