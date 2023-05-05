/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.model.CompanyConstants;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Ivan Zaera Avellon
 */
public class CompanyPortletMap<T> {

	public void clear() {
		_map.clear();
	}

	public T get(long companyId, String portletId) {
		ConcurrentMap<String, T> companyMap = _getCompanyMap(companyId);

		T value = companyMap.get(portletId);

		if (value != null) {
			return value;
		}

		companyMap = _getCompanyMap(CompanyConstants.SYSTEM);

		return companyMap.get(portletId);
	}

	public Collection<T> values(long companyId) {
		ConcurrentMap<String, T> systemCompanyMap =
			_getCompanyMap(CompanyConstants.SYSTEM);

		Collection<T> systemValues = systemCompanyMap.values();

		if (companyId == CompanyConstants.SYSTEM) {
			return systemValues;
		}

		Iterator<T> systemIterator = systemValues.iterator();

		ConcurrentMap<String, T> companyMap = _getCompanyMap(companyId);

		Collection<T> companyValues = companyMap.values();

		Iterator<T> companyIterator = companyValues.iterator();

		return new AbstractCollection<T>() {
			@Override
			public Iterator<T> iterator() {
				return new Iterator<T>() {
					@Override
					public boolean hasNext() {
						return
							systemIterator.hasNext() ||
								companyIterator.hasNext();
					}

					@Override
					public T next() {
						if (systemIterator.hasNext()) {
							return systemIterator.next();
						}

						return companyIterator.next();
					}
				};
			}

			@Override
			public int size() {
				return systemValues.size() + companyValues.size();
			}
		};
	}

	public ConcurrentMap<String, T> getCompanyMap(long companyId) {
		return _map.get(companyId);
	}

	public boolean isEmpty() {
		return _map.isEmpty();
	}

	public void put(long companyId, String portletId, T value) {
		ConcurrentMap<String, T> companyMap = _getCompanyMap(companyId);

		companyMap.put(portletId, value);
	}

	public ConcurrentMap<String, T> putCompanyMap(
		long companyId, ConcurrentMap<String, T> companyMap) {

		return _map.put(companyId, companyMap);
	}

	public T remove(long companyId, String portletId) {
		ConcurrentMap<String, T> companyMap = _getCompanyMap(companyId);

		return companyMap.remove(portletId);
	}

	public ConcurrentMap<String, T> removeCompanyMap(long companyId) {
		return _map.remove(companyId);
	}

	private ConcurrentMap<String, T> _getCompanyMap(long companyId) {
		ConcurrentMap<String, T> companyMap = _map.get(companyId);

		// TODO: only create CompanyMap on put (not get)
		if (companyMap == null) {
			_map.putIfAbsent(companyId, new ConcurrentHashMap<>());

			companyMap = _map.get(companyId);
		}

		return companyMap;
	}

	private ConcurrentMap<Long, ConcurrentMap<String, T>> _map =
		new ConcurrentHashMap<>();

}
