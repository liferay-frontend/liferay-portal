/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.util;

import com.liferay.portal.kernel.upgrade.StagnantRowException;
import com.liferay.portal.kernel.upgrade.util.ValueMapper;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
public class MemoryValueMapper implements ValueMapper {

	public MemoryValueMapper() {
		this(new LinkedHashSet<Object>());
	}

	public MemoryValueMapper(Set<Object> exceptions) {
		_exceptions = exceptions;

		_map = new LinkedHashMap<>();
	}

	@Override
	public void appendException(Object exception) {
		_exceptions.add(exception);
	}

	public Map<Object, Object> getMap() {
		return _map;
	}

	@Override
	public Object getNewValue(Object oldValue) throws Exception {
		Object value = _map.get(oldValue);

		if (value == null) {
			if (_exceptions.contains(oldValue)) {
				value = oldValue;
			}
			else {
				throw new StagnantRowException(String.valueOf(oldValue));
			}
		}

		return value;
	}

	@Override
	public Iterator<Object> iterator() throws Exception {
		Set<Object> keySet = _map.keySet();

		return keySet.iterator();
	}

	@Override
	public void mapValue(Object oldValue, Object newValue) throws Exception {
		_map.put(oldValue, newValue);
	}

	@Override
	public int size() throws Exception {
		return _map.size();
	}

	private final Set<Object> _exceptions;
	private final Map<Object, Object> _map;

}