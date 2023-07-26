/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.storage;

import com.liferay.dynamic.data.mapping.exception.StorageException;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author     Eduardo Lundgren
 * @author     Brian Wing Shun Chan
 * @author     Marcellus Tavares
 * @deprecated As of Judson (7.1.x), replaced by {@link DDMStorageAdapter}
 */
@Deprecated
public interface StorageAdapter {

	public long create(
			long companyId, long ddmStructureId, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws StorageException;

	public void deleteByClass(long classPK) throws StorageException;

	public void deleteByDDMStructure(long ddmStructureId)
		throws StorageException;

	public DDMFormValues getDDMFormValues(long classPK) throws StorageException;

	public String getStorageType();

	public void update(
			long classPK, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws StorageException;

}