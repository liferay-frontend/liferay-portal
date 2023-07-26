/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.blueprint.search.request.body.contributor;

import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterData;
import com.liferay.search.experiences.rest.dto.v1_0.AdvancedConfiguration;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.Source;

/**
 * @author Gustavo Lima
 */
public class AdvancedSXPSearchRequestBodyContributor
	implements SXPSearchRequestBodyContributor {

	@Override
	public void contribute(
		Configuration configuration, SearchRequestBuilder searchRequestBuilder,
		SXPParameterData sxpParameterData) {

		AdvancedConfiguration advancedConfiguration =
			configuration.getAdvancedConfiguration();

		if (advancedConfiguration == null) {
			return;
		}

		Source source = advancedConfiguration.getSource();

		if (source == null) {
			return;
		}

		if (source.getExcludes() != null) {
			searchRequestBuilder.fetchSourceExcludes(source.getExcludes());
		}

		if (source.getFetchSource() != null) {
			searchRequestBuilder.fetchSource(source.getFetchSource());
		}

		if (source.getIncludes() != null) {
			searchRequestBuilder.fetchSourceIncludes(source.getIncludes());
		}
	}

	@Override
	public String getName() {
		return "advancedConfiguration";
	}

}