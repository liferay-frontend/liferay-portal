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

package com.liferay.dataset.custom.view.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;DatasetCustomViewEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DatasetCustomViewEntry
 * @generated
 */
public class DatasetCustomViewEntryTable
	extends BaseTable<DatasetCustomViewEntryTable> {

	public static final DatasetCustomViewEntryTable INSTANCE =
		new DatasetCustomViewEntryTable();

	public final Column<DatasetCustomViewEntryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<DatasetCustomViewEntryTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DatasetCustomViewEntryTable, Long>
		datasetCustomViewEntryId = createColumn(
			"datasetCustomViewEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<DatasetCustomViewEntryTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DatasetCustomViewEntryTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DatasetCustomViewEntryTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DatasetCustomViewEntryTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DatasetCustomViewEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DatasetCustomViewEntryTable, String> datasetDisplayId =
		createColumn(
			"datasetDisplayId", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<DatasetCustomViewEntryTable, Long> plid = createColumn(
		"plid", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DatasetCustomViewEntryTable, String> portletId =
		createColumn(
			"portletId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DatasetCustomViewEntryTable, Clob> settingsJSON =
		createColumn(
			"settingsJSON", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);

	private DatasetCustomViewEntryTable() {
		super("DatasetCustomViewEntry", DatasetCustomViewEntryTable::new);
	}

}