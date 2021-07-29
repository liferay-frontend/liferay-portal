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

package com.liferay.remote.app.internal.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.remote.app.util.CSSURLsParser;

import java.util.Collection;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = CSSURLsParser.class)
public class CSSURLsParserImpl implements CSSURLsParser {

	@Override
	public Collection<String> parse(String cssURLsString) {
		return StringUtil.split(cssURLsString, CharPool.NEW_LINE);
	}

	@Override
	public String serialize(Collection<String> cssURLs) {
		return StringUtil.merge(cssURLs, StringPool.NEW_LINE);
	}

}