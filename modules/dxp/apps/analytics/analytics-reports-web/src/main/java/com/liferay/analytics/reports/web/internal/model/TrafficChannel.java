/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.analytics.reports.web.internal.model;

import com.liferay.portal.kernel.json.JSONObject;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author David Arques
 */
public interface TrafficChannel {

	public String getHelpMessageKey();

	public String getName();

	public long getTrafficAmount();

	public double getTrafficShare();

	public JSONObject toJSONObject(
		Locale locale, ResourceBundle resourceBundle);

}