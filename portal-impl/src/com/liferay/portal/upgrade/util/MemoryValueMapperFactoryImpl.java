/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.util;

import com.liferay.portal.kernel.upgrade.util.ValueMapper;
import com.liferay.portal.kernel.upgrade.util.ValueMapperFactory;

/**
 * @author Brian Wing Shun Chan
 */
public class MemoryValueMapperFactoryImpl implements ValueMapperFactory {

	@Override
	public ValueMapper getValueMapper() {
		return new MemoryValueMapper();
	}

}