/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.index;

import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;

import java.util.List;

/**
 * @author Adam Brandizzi
 */
public interface DocumentToSynonymSetTranslator {

	public SynonymSet translate(Document document, String id);

	public SynonymSet translate(SearchHit searchHit);

	public List<SynonymSet> translateAll(List<SearchHit> searchHits);

	public List<SynonymSet> translateAll(SearchHits searchHits);

}