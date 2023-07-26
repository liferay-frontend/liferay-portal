/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.application.publisher;

import com.liferay.headless.builder.application.APIApplication;

/**
 * @author Luis Miguel Barcos
 */
public interface APIApplicationPublisher {

	public void publish(APIApplication apiApplication) throws Exception;

	public void unpublish(String baseURL, long companyId);

}