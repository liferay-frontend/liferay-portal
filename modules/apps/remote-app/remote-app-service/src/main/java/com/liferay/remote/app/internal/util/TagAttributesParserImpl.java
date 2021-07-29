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
import com.liferay.remote.app.util.TagAttributesParser;

import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
import java.io.IOException;

import java.nio.charset.StandardCharsets;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = TagAttributesParser.class)
public class TagAttributesParserImpl implements TagAttributesParser {

	@Override
	public Map<String, String> parse(String tagAttributesString) {
		Properties properties = new Properties();

		try {
			properties.load(
				new ByteArrayInputStream(
					tagAttributesString.getBytes(StandardCharsets.UTF_8)));
		}
		catch (IOException ioException) {
			throw new IllegalArgumentException(
				tagAttributesString, ioException);
		}

		return (Map)properties;
	}

	@Override
	public String serialize(Map<String, String> tagAttributes) {
		Properties properties = new Properties();

		properties.putAll(tagAttributes);

		CharArrayWriter charArrayWriter = new CharArrayWriter();

		try {
			properties.store(charArrayWriter, null);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		List<String> tagAttributesLines = StringUtil.split(
			charArrayWriter.toString(), CharPool.NEW_LINE);

		return StringUtil.merge(
			tagAttributesLines.subList(1, tagAttributesLines.size()),
			StringPool.NEW_LINE);
	}

}