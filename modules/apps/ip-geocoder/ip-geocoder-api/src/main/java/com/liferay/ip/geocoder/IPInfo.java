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

package com.liferay.ip.geocoder;

import com.liferay.petra.string.StringBundler;

/**
 * @author Brian Wing Shun Chan
 */
public class IPInfo {

	public IPInfo(String countryCode, String ipAddress) {
		_countryCode = countryCode;
		_ipAddress = ipAddress;
	}

	public String getCountryCode() {
		return _countryCode;
	}

	public String getIPAddress() {
		return _ipAddress;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{countryCode=", _countryCode, ", ipAddress=", _ipAddress, "}");
	}

	private final String _countryCode;
	private final String _ipAddress;

}