/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.StagnantRowException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class DefaultPKMapper extends ValueMapperWrapper {

	public DefaultPKMapper(ValueMapper valueMapper) {
		super(valueMapper);
	}

	@Override
	public Object getNewValue(Object oldValue) throws Exception {
		String oldValueString = GetterUtil.getString(String.valueOf(oldValue));

		if (oldValueString.equals("-1") || oldValueString.equals("0") ||
			oldValueString.equals("")) {

			return Long.valueOf(0);
		}

		try {
			ValueMapper valueMapper = getValueMapper();

			if (oldValue instanceof String) {
				oldValue = StringUtil.toLowerCase(oldValueString);
			}

			return valueMapper.getNewValue(oldValue);
		}
		catch (StagnantRowException stagnantRowException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(stagnantRowException);
			}

			return Long.valueOf(0);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultPKMapper.class);

}