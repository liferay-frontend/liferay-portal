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

package com.liferay.remote.app.rest.client.serdes.v1_0;

import com.liferay.remote.app.rest.client.dto.v1_0.RemoteAppEntry;
import com.liferay.remote.app.rest.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Bruno Basto
 * @generated
 */
@Generated("")
public class RemoteAppEntrySerDes {

	public static RemoteAppEntry toDTO(String json) {
		RemoteAppEntryJSONParser remoteAppEntryJSONParser =
			new RemoteAppEntryJSONParser();

		return remoteAppEntryJSONParser.parseToDTO(json);
	}

	public static RemoteAppEntry[] toDTOs(String json) {
		RemoteAppEntryJSONParser remoteAppEntryJSONParser =
			new RemoteAppEntryJSONParser();

		return remoteAppEntryJSONParser.parseToDTOs(json);
	}

	public static String toJSON(RemoteAppEntry remoteAppEntry) {
		if (remoteAppEntry == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (remoteAppEntry.getCompanyId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"companyId\": ");

			sb.append(remoteAppEntry.getCompanyId());
		}

		if (remoteAppEntry.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					remoteAppEntry.getDateCreated()));

			sb.append("\"");
		}

		if (remoteAppEntry.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					remoteAppEntry.getDateModified()));

			sb.append("\"");
		}

		if (remoteAppEntry.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(remoteAppEntry.getId());
		}

		if (remoteAppEntry.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append(_toJSON(remoteAppEntry.getName()));
		}

		if (remoteAppEntry.getUrl() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"url\": ");

			sb.append("\"");

			sb.append(_escape(remoteAppEntry.getUrl()));

			sb.append("\"");
		}

		if (remoteAppEntry.getUserId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userId\": ");

			sb.append(remoteAppEntry.getUserId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		RemoteAppEntryJSONParser remoteAppEntryJSONParser =
			new RemoteAppEntryJSONParser();

		return remoteAppEntryJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(RemoteAppEntry remoteAppEntry) {
		if (remoteAppEntry == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (remoteAppEntry.getCompanyId() == null) {
			map.put("companyId", null);
		}
		else {
			map.put("companyId", String.valueOf(remoteAppEntry.getCompanyId()));
		}

		if (remoteAppEntry.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(
					remoteAppEntry.getDateCreated()));
		}

		if (remoteAppEntry.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(
					remoteAppEntry.getDateModified()));
		}

		if (remoteAppEntry.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(remoteAppEntry.getId()));
		}

		if (remoteAppEntry.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(remoteAppEntry.getName()));
		}

		if (remoteAppEntry.getUrl() == null) {
			map.put("url", null);
		}
		else {
			map.put("url", String.valueOf(remoteAppEntry.getUrl()));
		}

		if (remoteAppEntry.getUserId() == null) {
			map.put("userId", null);
		}
		else {
			map.put("userId", String.valueOf(remoteAppEntry.getUserId()));
		}

		return map;
	}

	public static class RemoteAppEntryJSONParser
		extends BaseJSONParser<RemoteAppEntry> {

		@Override
		protected RemoteAppEntry createDTO() {
			return new RemoteAppEntry();
		}

		@Override
		protected RemoteAppEntry[] createDTOArray(int size) {
			return new RemoteAppEntry[size];
		}

		@Override
		protected void setField(
			RemoteAppEntry remoteAppEntry, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "companyId")) {
				if (jsonParserFieldValue != null) {
					remoteAppEntry.setCompanyId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					remoteAppEntry.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					remoteAppEntry.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					remoteAppEntry.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					remoteAppEntry.setName(
						(Map)RemoteAppEntrySerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "url")) {
				if (jsonParserFieldValue != null) {
					remoteAppEntry.setUrl((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "userId")) {
				if (jsonParserFieldValue != null) {
					remoteAppEntry.setUserId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\":");

			Object value = entry.getValue();

			Class<?> valueClass = value.getClass();

			if (value instanceof Map) {
				sb.append(_toJSON((Map)value));
			}
			else if (valueClass.isArray()) {
				Object[] values = (Object[])value;

				sb.append("[");

				for (int i = 0; i < values.length; i++) {
					sb.append("\"");
					sb.append(_escape(values[i]));
					sb.append("\"");

					if ((i + 1) < values.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}