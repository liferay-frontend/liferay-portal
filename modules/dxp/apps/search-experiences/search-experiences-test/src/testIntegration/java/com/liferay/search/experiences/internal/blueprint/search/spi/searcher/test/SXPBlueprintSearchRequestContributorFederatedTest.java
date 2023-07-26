/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.blueprint.search.spi.searcher.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.search.experiences.rest.dto.v1_0.Clause;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.QueryConfiguration;
import com.liferay.search.experiences.rest.dto.v1_0.QueryEntry;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;

import org.hamcrest.CoreMatchers;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class SXPBlueprintSearchRequestContributorFederatedTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void test() throws Exception {
		SearchRequestBuilder searchRequestBuilder1 = _getSearchRequestBuilder();
		SearchRequestBuilder searchRequestBuilder2 = _getSearchRequestBuilder();
		SearchRequestBuilder searchRequestBuilder3 = _getSearchRequestBuilder();

		SearchResponse searchResponse = _searcher.search(
			searchRequestBuilder1.withSearchContext(
				searchContext -> searchContext.setAttribute(
					"search.experiences.blueprint.json",
					_getSXPBlueprintJSON("R2D2"))
			).addFederatedSearchRequest(
				searchRequestBuilder2.federatedSearchKey(
					"jedi"
				).withSearchContext(
					searchContext -> searchContext.setAttribute(
						"search.experiences.blueprint.json",
						_getSXPBlueprintJSON("Yoda"))
				).build()
			).addFederatedSearchRequest(
				searchRequestBuilder3.federatedSearchKey(
					"sith"
				).withSearchContext(
					searchContext -> searchContext.setAttribute(
						"search.experiences.blueprint.json",
						_getSXPBlueprintJSON("Vader"))
				).build()
			).build());

		_assert(searchResponse, "R2D2");
		_assert(searchResponse.getFederatedSearchResponse("jedi"), "Yoda");
		_assert(searchResponse.getFederatedSearchResponse("sith"), "Vader");
	}

	private void _assert(SearchResponse searchResponse, String value) {
		Assert.assertThat(
			searchResponse.getRequestString(),
			CoreMatchers.containsString(value));
	}

	private SearchRequestBuilder _getSearchRequestBuilder() throws Exception {
		return _searchRequestBuilderFactory.builder(
		).companyId(
			TestPropsValues.getCompanyId()
		).emptySearchEnabled(
			true
		);
	}

	private String _getSXPBlueprintJSON(String value) {
		Clause clause = new Clause() {
			{
				field = "friend";
				type = "match";
			}
		};

		clause.setValue(value);

		SXPBlueprint sxpBlueprint = new SXPBlueprint() {
			{
				configuration = new Configuration() {
					{
						queryConfiguration = new QueryConfiguration() {
							{
								queryEntries = new QueryEntry[] {
									new QueryEntry() {
										{
											clauses = new Clause[] {clause};
										}
									}
								};
							}
						};
					}
				};
			}
		};

		return sxpBlueprint.toString();
	}

	@Inject
	private Searcher _searcher;

	@Inject
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

}