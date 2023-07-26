/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.results.builder;

import com.liferay.portal.search.filter.ComplexQueryPartBuilderFactory;
import com.liferay.portal.search.query.IdsQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;

/**
 * @author André de Oliveira
 * @author Bryan Engler
 */
public class RankingSearchRequestBuilder {

	public RankingSearchRequestBuilder(
		ComplexQueryPartBuilderFactory complexQueryPartBuilderFactory,
		Queries queries,
		SearchRequestBuilderFactory searchRequestBuilderFactory) {

		_complexQueryPartBuilderFactory = complexQueryPartBuilderFactory;
		_queries = queries;
		_searchRequestBuilderFactory = searchRequestBuilderFactory;
	}

	public SearchRequestBuilder build() {
		return _searchRequestBuilderFactory.builder(
		).addComplexQueryPart(
			_complexQueryPartBuilderFactory.builder(
			).additive(
				true
			).query(
				getIdsQuery(_queryString)
			).occur(
				"should"
			).build()
		).from(
			_from
		).queryString(
			_queryString
		).size(
			_size
		).withSearchContext(
			searchContext -> searchContext.setCompanyId(_companyId)
		);
	}

	public RankingSearchRequestBuilder companyId(long companyId) {
		_companyId = companyId;

		return this;
	}

	public RankingSearchRequestBuilder from(int from) {
		_from = from;

		return this;
	}

	public RankingSearchRequestBuilder queryString(String queryString) {
		_queryString = queryString;

		return this;
	}

	public RankingSearchRequestBuilder size(int size) {
		_size = size;

		return this;
	}

	protected Query getIdsQuery(String id) {
		IdsQuery idsQuery = _queries.ids();

		idsQuery.addIds(id);

		return idsQuery;
	}

	private long _companyId;
	private final ComplexQueryPartBuilderFactory
		_complexQueryPartBuilderFactory;
	private int _from;
	private final Queries _queries;
	private String _queryString;
	private final SearchRequestBuilderFactory _searchRequestBuilderFactory;
	private int _size;

}