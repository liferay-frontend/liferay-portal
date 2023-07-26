/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.search.spi.model.query.contributor;

import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CommerceCatalogService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	property = {
		"indexer.class.name=com.liferay.commerce.product.model.CPDefinition",
		"indexer.clauses.mandatory=true"
	},
	service = ModelPreFilterContributor.class
)
public class CPDefinitionModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		if (_isIndexersSuppressed(searchContext)) {
			_addCommerceCatalogIdFilterClauses(booleanFilter, searchContext);
		}
	}

	private void _addCommerceCatalogIdFilterClauses(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		TermsFilter termsFilter = new TermsFilter("commerceCatalogId");

		long[] commerceCatalogIds = _getCommerceCatalogIds(searchContext);

		if (commerceCatalogIds.length == 0) {
			termsFilter.addValue("-1");
		}
		else {
			termsFilter.addValues(ArrayUtil.toStringArray(commerceCatalogIds));
		}

		booleanFilter.add(termsFilter, BooleanClauseOccur.MUST);
	}

	private long[] _getCommerceCatalogIds(SearchContext searchContext) {
		return TransformUtil.transformToLongArray(
			_commerceCatalogService.getCommerceCatalogs(
				searchContext.getCompanyId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS),
			CommerceCatalog::getCommerceCatalogId);
	}

	private boolean _isIndexersSuppressed(SearchContext searchContext) {
		return GetterUtil.getBoolean(
			searchContext.getAttribute(
				"search.full.query.suppress.indexer.provided.clauses"));
	}

	@Reference
	private CommerceCatalogService _commerceCatalogService;

}