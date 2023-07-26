/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.live;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kevin Lee
 */
public class LiveUpgradeSchemaDiff {

	public LiveUpgradeSchemaDiff(Connection connection, String tableName)
		throws Exception {

		_dbInspector = new DBInspector(connection);

		try (ResultSet resultSet = _dbInspector.getColumnsResultSet(
				tableName)) {

			while (resultSet.next()) {
				String columnName = resultSet.getString("COLUMN_NAME");

				_resultColumnsMap.put(
					columnName, new Column(columnName, false));
			}
		}
	}

	public Map<String, String> getResultColumnNamesMap() {
		Map<String, String> resultColumnNamesMap = new HashMap<>();

		for (Column column : _resultColumnsMap.values()) {
			if (column.isAdded()) {
				continue;
			}

			resultColumnNamesMap.put(
				column.getOldColumnName(), column.getNewColumnName());
		}

		return resultColumnNamesMap;
	}

	public void recordAddColumns(String... columnDefinitions) throws Exception {
		for (String columnDefinition : columnDefinitions) {
			String columnName = _dbInspector.normalizeName(
				StringUtil.extractFirst(columnDefinition, StringPool.SPACE));

			if (_resultColumnsMap.containsKey(columnName)) {
				throw new IllegalArgumentException(
					"Column " + columnName + " already exists");
			}

			_resultColumnsMap.put(columnName, new Column(columnName, true));
		}
	}

	public void recordAlterColumnName(
			String oldColumnName, String newColumnDefinition)
		throws Exception {

		oldColumnName = _dbInspector.normalizeName(oldColumnName);

		String newColumnName = _dbInspector.normalizeName(
			StringUtil.extractFirst(newColumnDefinition, StringPool.SPACE));

		Column column = _resultColumnsMap.remove(oldColumnName);

		if (column == null) {
			throw new IllegalArgumentException(
				"Column " + oldColumnName + " does not exist");
		}
		else if (_resultColumnsMap.containsKey(newColumnName)) {
			throw new IllegalArgumentException(
				"Column " + newColumnName + " already exists");
		}

		column.setNewColumnName(newColumnName);

		_resultColumnsMap.put(newColumnName, column);
	}

	public void recordAlterColumnType(String columnName, String newColumnType)
		throws Exception {

		columnName = _dbInspector.normalizeName(columnName);

		Column column = _resultColumnsMap.get(columnName);

		if (column == null) {
			throw new IllegalArgumentException(
				"Column " + columnName + " does not exist");
		}

		// TODO Validate and record column type

	}

	public void recordDropColumns(String... columnNames) throws SQLException {
		for (String columnName : columnNames) {
			columnName = _dbInspector.normalizeName(columnName);

			Column column = _resultColumnsMap.remove(columnName);

			if (column == null) {
				throw new IllegalArgumentException(
					"Column " + columnName + " does not exist");
			}
		}
	}

	private final DBInspector _dbInspector;
	private final Map<String, Column> _resultColumnsMap = new HashMap<>();

	private static class Column {

		public Column(String columnName, boolean added) {
			_added = added;

			_newColumnName = columnName;
			_oldColumnName = columnName;
		}

		public String getNewColumnName() {
			return _newColumnName;
		}

		public String getOldColumnName() {
			return _oldColumnName;
		}

		public boolean isAdded() {
			return _added;
		}

		public void setNewColumnName(String newColumnName) {
			_newColumnName = newColumnName;
		}

		private final boolean _added;
		private String _newColumnName;
		private final String _oldColumnName;

	}

}