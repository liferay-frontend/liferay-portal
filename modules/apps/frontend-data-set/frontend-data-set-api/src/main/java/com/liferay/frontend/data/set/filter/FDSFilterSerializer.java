/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.filter;

import com.liferay.frontend.data.set.serializer.FDSSerializer;
import com.liferay.portal.kernel.json.JSONArray;

/**
 * @author Daniel Sanz
 */
public interface FDSFilterSerializer extends FDSSerializer<JSONArray> {

	@Override
	public default String getKey() {
		return "filters";
	}

}