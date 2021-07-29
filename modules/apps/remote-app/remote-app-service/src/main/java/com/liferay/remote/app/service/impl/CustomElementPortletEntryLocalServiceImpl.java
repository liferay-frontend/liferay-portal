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

package com.liferay.remote.app.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.remote.app.model.CustomElementPortletEntry;
import com.liferay.remote.app.service.base.CustomElementPortletEntryLocalServiceBaseImpl;
import com.liferay.remote.app.util.CSSURLsParser;
import com.liferay.remote.app.util.TagAttributesParser;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The implementation of the custom element portlet entry local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.remote.app.service.CustomElementPortletEntryLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CustomElementPortletEntryLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=com.liferay.remote.app.model.CustomElementPortletEntry",
	service = AopService.class
)
public class CustomElementPortletEntryLocalServiceImpl
	extends CustomElementPortletEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CustomElementPortletEntry addCustomElementPortletEntry(
			long userId, Collection<String> cssURLs,
			Map<Locale, String> nameMap, String portletDisplayCategory,
			Map<String, String> tagAttributes, String tagName,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long companyId = user.getCompanyId();

		long customElementPortletEntryId = counterLocalService.increment();

		CustomElementPortletEntry customElementPortletEntry =
			customElementPortletEntryPersistence.create(
				customElementPortletEntryId);

		customElementPortletEntry.setUuid(serviceContext.getUuid());
		customElementPortletEntry.setCompanyId(companyId);
		customElementPortletEntry.setUserId(user.getUserId());
		customElementPortletEntry.setUserName(user.getFullName());
		customElementPortletEntry.setCssURLs(_cssURLsParser.serialize(cssURLs));
		customElementPortletEntry.setNameMap(nameMap);
		customElementPortletEntry.setTagAttributes(
			_tagAttributesParser.serialize(tagAttributes));
		customElementPortletEntry.setPortletDisplayCategory(
			portletDisplayCategory);
		customElementPortletEntry.setTagName(tagName);

		return customElementPortletEntryPersistence.update(
			customElementPortletEntry);
	}

	@Override
	public List<CustomElementPortletEntry> searchCustomElementPortletEntries(
			long companyId, String keywords, int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, keywords, start, end, sort);

		return searchCustomElementPortletEntries(searchContext);
	}

	@Override
	public int searchCustomElementPortletEntriesCount(
			long companyId, String keywords)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, keywords, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		return searchCustomElementPortletEntriesCount(searchContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CustomElementPortletEntry updateCustomElementPortletEntry(
			long customElementPortletEntryId, Collection<String> cssURLs,
			Map<Locale, String> nameMap, String portletDisplayCategory,
			Map<String, String> tagAttributes, String tagName,
			ServiceContext serviceContext)
		throws PortalException {

		CustomElementPortletEntry customElementPortletEntry =
			customElementPortletEntryPersistence.findByPrimaryKey(
				customElementPortletEntryId);

		customElementPortletEntry.setCssURLs(_cssURLsParser.serialize(cssURLs));
		customElementPortletEntry.setNameMap(nameMap);
		customElementPortletEntry.setTagAttributes(
			_tagAttributesParser.serialize(tagAttributes));
		customElementPortletEntry.setPortletDisplayCategory(
			portletDisplayCategory);
		customElementPortletEntry.setTagName(tagName);

		return customElementPortletEntryPersistence.update(
			customElementPortletEntry);
	}

	protected SearchContext buildSearchContext(
		long companyId, String keywords, int start, int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				Field.NAME, keywords
			).put(
				Field.SUBTITLE, keywords
			).build());
		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setKeywords(keywords);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		searchContext.setStart(start);

		return searchContext;
	}

	protected List<CustomElementPortletEntry> getCustomElementPortletEntries(
			Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<CustomElementPortletEntry> customElementPortletEntries =
			new ArrayList<>(documents.size());

		for (Document document : documents) {
			long customElementPortletsEntryId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			CustomElementPortletEntry customElementPortletEntry =
				customElementPortletEntryPersistence.fetchByPrimaryKey(
					customElementPortletsEntryId);

			if (customElementPortletEntry == null) {
				customElementPortletEntries = null;

				Indexer<CustomElementPortletEntry> indexer =
					IndexerRegistryUtil.getIndexer(
						CustomElementPortletEntry.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else {
				customElementPortletEntries.add(customElementPortletEntry);
			}
		}

		return customElementPortletEntries;
	}

	protected List<CustomElementPortletEntry> searchCustomElementPortletEntries(
			SearchContext searchContext)
		throws PortalException {

		Indexer<CustomElementPortletEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(
				CustomElementPortletEntry.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext);

			List<CustomElementPortletEntry> customElementEntries =
				getCustomElementPortletEntries(hits);

			if (customElementEntries != null) {
				return customElementEntries;
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	protected int searchCustomElementPortletEntriesCount(
			SearchContext searchContext)
		throws PortalException {

		Indexer<CustomElementPortletEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(
				CustomElementPortletEntry.class);

		return GetterUtil.getInteger(indexer.searchCount(searchContext));
	}

	@Reference
	private CSSURLsParser _cssURLsParser;

	@Reference
	private TagAttributesParser _tagAttributesParser;

}