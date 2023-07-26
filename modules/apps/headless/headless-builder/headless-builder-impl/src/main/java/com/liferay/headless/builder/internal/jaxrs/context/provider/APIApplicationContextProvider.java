/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.jaxrs.context.provider;

import com.liferay.headless.builder.application.APIApplication;

import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

/**
 * @author Luis Miguel Barcos
 */
@Provider
public class APIApplicationContextProvider
	implements ContextProvider<APIApplication> {

	public APIApplicationContextProvider(APIApplication apiApplication) {
		_apiApplication = apiApplication;
	}

	@Override
	public APIApplication createContext(Message message) {
		return _apiApplication;
	}

	public void setApiApplication(APIApplication apiApplication) {
		_apiApplication = apiApplication;
	}

	private APIApplication _apiApplication;

}