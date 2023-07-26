/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.storage;

import com.liferay.dynamic.data.mapping.exception.StorageException;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageAdapter;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pablo Carvalho
 */
@Component(service = StorageAdapter.class)
public class JSONStorageAdapter implements StorageAdapter {

	@Override
	public long create(
			long companyId, long ddmStructureId, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws StorageException {

		if (_log.isWarnEnabled()) {
			_log.warn(
				"JSON storage adapter is deprecated, using default storage " +
					"adapter");
		}

		return _defaultStorageAdapter.create(
			companyId, ddmStructureId, ddmFormValues, serviceContext);
	}

	@Override
	public void deleteByClass(long classPK) throws StorageException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"JSON storage adapter is deprecated, using default storage " +
					"adapter");
		}

		_defaultStorageAdapter.deleteByClass(classPK);
	}

	@Override
	public void deleteByDDMStructure(long ddmStructureId)
		throws StorageException {

		if (_log.isWarnEnabled()) {
			_log.warn(
				"JSON storage adapter is deprecated, using default storage " +
					"adapter");
		}

		_defaultStorageAdapter.deleteByDDMStructure(ddmStructureId);
	}

	@Override
	public DDMFormValues getDDMFormValues(long classPK)
		throws StorageException {

		if (_log.isWarnEnabled()) {
			_log.warn(
				"JSON storage adapter is deprecated, using default storage " +
					"adapter");
		}

		return _defaultStorageAdapter.getDDMFormValues(classPK);
	}

	@Override
	public String getStorageType() {
		return StorageType.JSON.toString();
	}

	@Override
	public void update(
			long classPK, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws StorageException {

		if (_log.isWarnEnabled()) {
			_log.warn(
				"JSON storage adapter is deprecated, using default storage " +
					"adapter");
		}

		_defaultStorageAdapter.update(classPK, ddmFormValues, serviceContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JSONStorageAdapter.class);

	@Reference(
		target = "(component.name=com.liferay.dynamic.data.mapping.internal.storage.DefaultStorageAdapter)"
	)
	private StorageAdapter _defaultStorageAdapter;

}