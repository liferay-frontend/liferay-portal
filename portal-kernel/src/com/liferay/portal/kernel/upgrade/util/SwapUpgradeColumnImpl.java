/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade.util;

/**
 * @author Brian Wing Shun Chan
 */
public class SwapUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public SwapUpgradeColumnImpl(
		String name, Integer oldColumnType, ValueMapper valueMapper) {

		super(name, oldColumnType);

		_valueMapper = valueMapper;
	}

	public SwapUpgradeColumnImpl(String name, ValueMapper valueMapper) {
		this(name, null, valueMapper);
	}

	@Override
	public Object getNewValue(Object oldValue) throws Exception {
		return _valueMapper.getNewValue(oldValue);
	}

	private final ValueMapper _valueMapper;

}