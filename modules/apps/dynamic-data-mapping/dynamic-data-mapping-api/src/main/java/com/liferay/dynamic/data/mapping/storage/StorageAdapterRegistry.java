/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.storage;

import java.util.Set;

/**
 * @author     Marcellus Tavares
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public interface StorageAdapterRegistry {

	public StorageAdapter getDefaultStorageAdapter();

	public StorageAdapter getStorageAdapter(String storageType);

	public Set<String> getStorageTypes();

}