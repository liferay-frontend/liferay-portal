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

package com.liferay.frontend.view.state.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;FVSFrontendDataSet&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see FVSFrontendDataSet
 * @generated
 */
public class FVSFrontendDataSetTable
	extends BaseTable<FVSFrontendDataSetTable> {

	public static final FVSFrontendDataSetTable INSTANCE =
		new FVSFrontendDataSetTable();

	public final Column<FVSFrontendDataSetTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<FVSFrontendDataSetTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FVSFrontendDataSetTable, Long> fvsFrontendDataSetId =
		createColumn(
			"fvsFrontendDataSetId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<FVSFrontendDataSetTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FVSFrontendDataSetTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FVSFrontendDataSetTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FVSFrontendDataSetTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<FVSFrontendDataSetTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<FVSFrontendDataSetTable, Long> fvsEntryId =
		createColumn(
			"fvsEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FVSFrontendDataSetTable, String> fdsName = createColumn(
		"fdsName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FVSFrontendDataSetTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FVSFrontendDataSetTable, Long> plid = createColumn(
		"plid", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FVSFrontendDataSetTable, String> portletId =
		createColumn(
			"portletId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FVSFrontendDataSetTable, Boolean> system = createColumn(
		"system_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private FVSFrontendDataSetTable() {
		super("FVSFrontendDataSet", FVSFrontendDataSetTable::new);
	}

}