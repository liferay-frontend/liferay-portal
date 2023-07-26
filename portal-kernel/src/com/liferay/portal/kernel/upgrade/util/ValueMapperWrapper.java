/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade.util;

import java.util.Iterator;

/**
 * @author Brian Wing Shun Chan
 */
public class ValueMapperWrapper implements ValueMapper {

	public ValueMapperWrapper(ValueMapper valueMapper) {
		_valueMapper = valueMapper;
	}

	@Override
	public void appendException(Object exception) {
		_valueMapper.appendException(exception);
	}

	@Override
	public Object getNewValue(Object oldValue) throws Exception {
		return _valueMapper.getNewValue(oldValue);
	}

	public ValueMapper getValueMapper() {
		return _valueMapper;
	}

	@Override
	public Iterator<Object> iterator() throws Exception {
		return _valueMapper.iterator();
	}

	@Override
	public void mapValue(Object oldValue, Object newValue) throws Exception {
		_valueMapper.mapValue(oldValue, newValue);
	}

	@Override
	public int size() throws Exception {
		return _valueMapper.size();
	}

	private final ValueMapper _valueMapper;

}