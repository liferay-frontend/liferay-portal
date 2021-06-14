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

package com.liferay.portal.search.elasticsearch7.internal.filter;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.function.Function;

import org.apache.lucene.search.join.ScoreMode;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * @author Javier de Arcos
 */
public class NestedFieldArrayTranslatorUtil {

	public static boolean isNestedFieldArrayQuery(String filterField) {
		if (filterField.startsWith("nestedFieldArray") &&
			filterField.contains(StringPool.POUND)) {

			return true;
		}

		return false;
	}

	public static QueryBuilder translate(
		String filterField,
		Function<String, QueryBuilder> queryBuilderFunction) {

		String[] parts = StringUtil.split(filterField, StringPool.POUND);

		String nestedFieldName = parts[0];

		String fieldName = parts[1];

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

		return new NestedQueryBuilder(
			"nestedFieldArray",
			boolQueryBuilder.must(
				QueryBuilders.termQuery("nestedFieldArray.fieldName", fieldName)
			).must(
				queryBuilderFunction.apply(nestedFieldName)
			),
			ScoreMode.None);
	}

}